package consolidationMpdrDb.fetcher.oracle;

import consolidationMpdrDb.fetcher.DbFetcher;
import connections.ConConnection;
import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mpdr.*;
import mpdr.oracle.*;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.oracle.MPDROraclePackage;
import mpdr.tapis.oracle.MPDROracleTrigger;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbFetcherOracle extends DbFetcher {

    private final Connection connection;
    private final ConConnection conConnection;
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private String databaseName;
    private MPDROracleModel dbModel;
    private List<String> sequencesNotInTable = new ArrayList<>();
    private List<String> packagesNotInTable = new ArrayList<>();
    private List<String> triggersNotInTable = new ArrayList<>();
    private Preferences pref = PreferencesManager.instance().getProjectPref();

    public DbFetcherOracle(ConConnection conConnection, Connection connection) throws SQLException {
        this.connection = connection;
        this.conConnection = conConnection;
        this.databaseMetaData = this.connection.getMetaData();
        this.databaseName = this.conConnection.getDbName();
        this.schemaDB = this.conConnection.getUserName();
        this.dbModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
        this.dbModel.setName(Preferences.FETCHER_ORACLE_MPDR_NAME);
    }

    public void fetch() throws SQLException {
        fetchTables();
        fetchTriggers(dbModel.getMPDRTables());
        fetchPackages(dbModel.getMPDRTables());
        //fetchIndex();
    }


    private void fetchTables() throws SQLException {
        String[] types = {Preferences.FETCHER_ORACLE_TYPE_TABLE};
        try (ResultSet result = databaseMetaData.getTables(databaseName, schemaDB, "%", types)) {
            while (result.next()) {
                MPDROracleTable dbTable = MVCCDElementFactory.instance()
                        .createMPDROracleTable(this.dbModel.getMPDRContTables(), null);
                dbTable.setName(result.getString(Preferences.FETCHER_ORACLE_TABLE_NAME));
                //On crée un box pour les packages et triggers pour éviter d'avoir un nullpointeur lors des comparaisons quand on boucle sur les box
                MVCCDElementFactory.instance().createMPDROracleBoxPackages(dbTable.getMPDRContTAPIs(), null);
                MVCCDElementFactory.instance().createMPDROracleBoxTriggers(dbTable.getMPDRContTAPIs(), null);
                fetchColumns(dbTable);
                fetchPk(dbTable);
                fetchUnique(dbTable);
                fetchCheck(dbTable);
                fetchFk(dbTable);
                fetchSequences(dbTable);
            }
        }
    }

    private void fetchColumns(MPDROracleTable dbTable) {
        try (ResultSet result = this.databaseMetaData.getColumns(this.databaseName, this.schemaDB, dbTable.getName(), null)) {
            while (result.next()) {
                MPDROracleColumn dbColumn = MVCCDElementFactory.instance().createMPDROracleColumn(dbTable.getMDRContColumns(), null);
                dbColumn.setName(result.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME));
                dbColumn.setDatatypeLienProg(result.getString(Preferences.FETCHER_ORACLE_TYPE_NAME));
                dbColumn.setMandatory(!result.getBoolean(Preferences.FETCHER_ORACLE_NULLABLE)); //Si nullable -> non obligatoire
                dbColumn.setSize(result.getInt(Preferences.FETCHER_ORACLE_COLUMN_SIZE));
                dbColumn.setScale(result.getInt(Preferences.FETCHER_ORACLE_DECIMAL_DIGITS));
                dbColumn.setInitValue(result.getString(Preferences.FETCHER_ORACLE_COLUMN_DEF));
                dbColumn.setOrder(result.getInt(Preferences.FETCHER_ORACLE_ORDINAL_POSITION));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void fetchPk(MPDROracleTable dbTable) throws SQLException {
        MPDROraclePK dbPK = null;
        try (ResultSet result = this.databaseMetaData.getPrimaryKeys(this.databaseName, this.schemaDB, dbTable.getName())) {
            while (result.next()) {
                if (dbPK == null) {
                    dbPK = MVCCDElementFactory.instance().createMPDROraclePK(dbTable.getMDRContConstraints(), null);
                    dbPK.setName(result.getString(Preferences.FETCHER_ORACLE_PK_NAME));
                }
                for (MPDRColumn dbColumn : dbTable.getMPDRColumns()) {
                    if (dbColumn.getName().equals(result.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME))) {
                        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(dbPK, (MLDRParameter) null);
                        //Ajout de la méthode getId() dans la classe "ProjectElement"
                        mpdrParameter.setTargetId(dbColumn.getId());
                        dbColumn.setPk(true);
                    }
                }
            }
        }
    }

    private void fetchUnique(MPDROracleTable dbTable) throws SQLException {
        MPDROracleUnique dbUnique = null;
        //On ne récupère que les contraintes Unique
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        pStmt.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_UNIQUE);
        pStmt.setString(2, dbTable.getName());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            //Si le nom de la contrainte n'existe pas, on en crée une
            if (!dbTable.getMPDRUniques().contains(rsCurseur.getString((Preferences.FETCHER_ORACLE_CONSTRAINT_NAME)))) {
                dbUnique = MVCCDElementFactory.instance().createMPDROracleUnique(dbTable.getMDRContConstraints(), null);
                dbUnique.setName(rsCurseur.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME));
            }
        }
        pStmt.close();
        rsCurseur.close();
        //On récupère toutes les contraintes et les colonnes auquelles elles sont rattachées
        String requeteSQLUserConsColumns = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONS_COLUMNS;
        PreparedStatement pStmtUserConsColumns = connection.prepareStatement(requeteSQLUserConsColumns);
        pStmtUserConsColumns.setString(1, dbTable.getName());
        ResultSet rsCurseurUserConsColumns = pStmtUserConsColumns.executeQuery();
        while (rsCurseurUserConsColumns.next()) {
            String constraintName = rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
            String columnName = rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME);
            for (MPDRUnique dbUniqueConst : dbTable.getMPDRUniques()) {
                if (dbUniqueConst instanceof MPDROracleUnique) {
                    //si la position est null, il s'agit d'une contrainte qui doit être mise que sur une seule colonne (par exemple NOT NULL)
                    if (constraintName.equals(dbUniqueConst.getName())) {
                        MPDRParameter dbParameter = MVCCDElementFactory.instance().createMPDROracleParameter((IMPDROracleElement) dbUniqueConst, (MLDRParameter) null);
                        for (MPDRColumn dbColumn : dbTable.getMPDRColumns()) {
                            if (dbColumn.getName().equals(columnName)) {
                                dbParameter.setTargetId(dbColumn.getId());
                            }
                        }
                    }
                }
            }
        }
        pStmtUserConsColumns.close();
        rsCurseurUserConsColumns.close();
    }

    private void fetchCheck(MPDRTable dbTable) throws SQLException {
        MPDROracleCheckSpecific dbCheckSpecific;
        MPDROracleParameter dbParameter;
        StringBuilder requeteSQL = new StringBuilder();
        //Utilisation de la colonne search_condition_vc (introduit à la version 12c) qui est de type varchar car search_condition est de type long
        //Lors de la création d'une contrainte sans nom, la DB en crée une avec le nom qui commence par SYS_
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS);
        requeteSQL.append(Preferences.FETCHER_ORACLE_AND);
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SEARCH_CONDITION_VC);
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        pStmt.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_CHECK);
        pStmt.setString(2, dbTable.getName());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            String constraintName = rsCurseur.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
            String condition = rsCurseur.getString(Preferences.FETCHER_ORACLE_SEARCH_CONDITION_VC);
            dbCheckSpecific = MVCCDElementFactory.instance().createMPDROracleCheckSpecific(dbTable.getMDRContConstraints(), null);
            dbCheckSpecific.setName(constraintName);
            dbParameter = (MPDROracleParameter) MVCCDElementFactory.instance().createMPDROracleParameter(dbCheckSpecific, (MLDRParameter) null);
            dbParameter.setValue(condition);
        }
        pStmt.close();
        rsCurseur.close();
    }

    private void fetchFk(MPDRTable dbTable) throws SQLException {
        MPDROracleFK dbFK = null;
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        pStmt.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_FK);
        pStmt.setString(2, dbTable.getName());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            if (!dbTable.getMPDRFKs().contains(rsCurseur.getString((Preferences.FETCHER_ORACLE_CONSTRAINT_NAME)))) {
                dbFK = MVCCDElementFactory.instance().createMPDROracleFK(dbTable.getMDRContConstraints(), null);
                dbFK.setName(rsCurseur.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME));
                if (rsCurseur.getString("DELETE_RULE").equals("CASCADE")) {
                    dbFK.setDeleteCascade(true);
                }
            }

            String requeteSQLUserConsColumns = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONS_COLUMNS;
            PreparedStatement pStmtUserConsColumns = connection.prepareStatement(requeteSQLUserConsColumns);
            pStmtUserConsColumns.setString(1, dbTable.getName());
            ResultSet rsCurseurUserConsColumns = pStmtUserConsColumns.executeQuery();
            while (rsCurseurUserConsColumns.next()) {
                String constraintName = rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
                String columnName = rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME);
                for (MPDRColumn dbColumn : dbTable.getMPDRColumns()) {
                    if (constraintName.equals(dbFK.getName())) {
                        if (dbColumn.getName().equals(rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME))) {
                            MPDRParameter dbParameter = MVCCDElementFactory.instance().createMPDROracleParameter(dbFK, (MLDRParameter) null);
                            dbParameter.setTargetId(dbColumn.getId());
                            //TODO VINCENT
                            //A VOIR POUR AFFECTER LA COLONNE PK setmdrColumnPK
                        }
                    }
                }
            }
            pStmtUserConsColumns.close();
            rsCurseurUserConsColumns.close();
        }
        pStmt.close();
        rsCurseur.close();
    }



    private void fetchSequences(MPDROracleTable dbTable) throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_SEQUENCES;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            String sequenceName = rsCurseur.getString(Preferences.FETCHER_ORACLE_SEQUENCE_NAME);
            //TODO il y a toutes les séquence dans cette liste actuellement car si elle est supprimée,
            // lors du prochain tour dans une autre table, elle sera à nouveau rajoutée...
            if (!sequencesNotInTable.contains(sequenceName)) {
                sequencesNotInTable.add(sequenceName);
            }
            if (findTableAccueilleSequence(dbTable, sequenceName) != null) {
                MPDROracleSequence mpdrOracleSequence;
                for (MPDRColumn mpdrColumn : dbTable.getMPDRColumns()) {
                    if (mpdrColumn.getIsPk()){
                        mpdrOracleSequence = MVCCDElementFactory.instance().createMPDROracleSequence(mpdrColumn, null);
                        mpdrOracleSequence.setName(sequenceName);
                        mpdrOracleSequence.setMinValue(rsCurseur.getInt(Preferences.FETCHER_ORACLE_MIN_VALUE));
                        mpdrOracleSequence.setMinValue(rsCurseur.getInt(Preferences.FETCHER_ORACLE_INCREMENT_BY));
                        break;
                    }
                }
                sequencesNotInTable.remove(sequenceName);
            }
        }
    }

    private void fetchTriggers(List<MPDRTable> dbTables) throws SQLException {
        List<String> triggers = new ArrayList<>();
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_TRIGGERS;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            triggers.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_TRIGGER_NAME));
            triggersNotInTable.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_TRIGGER_NAME));
        }
        for (MPDRTable dbTable : dbTables) {
            for (String trigger : triggers) {
                if (findTableAccueilleTrigger(dbTable, trigger) != null) {
                    MPDROracleTrigger dbTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(dbTable.getMPDRContTAPIs().getMPDRBoxTriggers(), null);
                    dbTrigger.setName(trigger);
                    triggersNotInTable.remove(trigger);
                }
            }
        }
    }

    private void fetchPackages(List<MPDRTable> dbTables) throws SQLException {
        List<String> packages = new ArrayList<>();
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_PROCEDURES);
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        pStmt.setString(1, Preferences.FETCHER_ORACLE_PACKAGE);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            packages.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_OBJECT_NAME));
            packagesNotInTable.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_OBJECT_NAME));
        }
        for (MPDRTable dbTable : dbTables) {
            //package est un mot réservé donc utilisation du nom de variable "paquet"
            for (String paquet : packages) {
                if (findTableAccueillePackage(dbTable, paquet) != null) {
                    MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(dbTable.getMPDRContTAPIs().getMPDRBoxPackages(), null);
                    mpdrOraclePackage.setName(paquet);
                    packagesNotInTable.remove(paquet);
                }
            }
        }
    }

    //Une séquence est nommée par défaut {tableShortName}{_SEQPK} = QUAL_SEQPK
    //ATTENTION: le format peut être modifié par l'utilisateur dans ses préférences de projet
    //Preferences.MPDRORACLE_SEQPK_NAME_FORMAT_DEFAULT;
    //-> Cette méthode gère uniquement le cas où le nom de la table est au début
    //ATTENTION, si deux tables ont un nom similaire comme COLLABORATEURS ET COLLABORATEURS_JN, on les mettra dans les deux tables
    public MPDRTable findTableAccueilleSequence(MPDROracleTable dbTable, String sequenceName) {
        String[] splitterString = sequenceName.split("_");
        for (String str : splitterString) {
            if (dbTable.getName().startsWith(str)) {
                return dbTable;
            }
            if (dbTable.getName().endsWith(str)){
                return dbTable;
            }
        }
        return null;

    }

    //Si on veut pour une table spécifique uniquement
    //Un trigger est nommée {tableShortName}{tableSep}{typeTriggerMarker} = QUAL_BIR
    //Un package est nommé {tableShortName}{tableSep}{typePackageMarker} = QUAL_TAPIs
    //ATTENTION: le format peut être modifié par l'utilisateur dans ses préférences de projet
    //-> Cette méthode gère uniquement le cas où le nom de la table est au début
    //Preferences.MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT_DEFAULT; //"{tableShortName}{tableSep}{typeTriggerMarker}"
    //ATTENTION, si deux tables ont un nom similaire comme COLLABORATEURS ET COLLABORATEURS_JN, on les mettra dans les deux tables
    private MPDRTable findTableAccueilleTrigger(MPDRTable dbTable, String triggerName) {
        String[] splitterString = triggerName.split(pref.getMDR_TABLE_SEP_FORMAT());
        for (String str : splitterString) {
            if (dbTable.getName().startsWith(str)){
                return dbTable;
            }
        }
        return null;
    }

    //ATTENTION, si deux tables ont un nom similaire comme COLLABORATEURS ET COLLABORATEURS_JN, on les mettra dans les deux tables
    //Preferences.MPDRORACLE_PACKAGE_NAME_FORMAT_DEFAULT; //"{tableShortName}{tableSep}{typePackageMarker}"
    private MPDRTable findTableAccueillePackage(MPDRTable dbTable, String packageName) {
        String[] splitterString = packageName.split(pref.getMDR_TABLE_SEP_FORMAT());
        for (String str : splitterString) {
            if(str.startsWith(dbTable.getName())){
                return dbTable;
            }
        }
        return null;
    }

    public MPDROracleModel getDbModel() {
        return dbModel;
    }

    //Attention, car il peut être normale que des séquences ne soient pas dans une table
    public List<String> getDbSequencesNotInTable() {
        return sequencesNotInTable;
    }

    //Attention, car il peut être normale que des packages ne soient pas dans une table
    public List<String> getDbPackagesNotInTable() {
        return packagesNotInTable;
    }

    //Attention, car il peut être normale que des triggers ne soient pas dans une table
    public List<String> getDbTriggersNotInTable() {
        return triggersNotInTable;
    }

    public void fetchIndex() {
        // A développer ultérieurement
    }

    private List<String> findColumns(String constraintName, int position) {
        List<String> dbColumns = new ArrayList<>();
        if (position > 0) {
            String columnName = constraintName.split("_")[position + 1];
            dbColumns.add(columnName);
        }
        return dbColumns;
    }
}
/*
    private MPDRTable findTableAccueilleTrigger(MPDRTable dbTable, String triggerName) {
        //String triggerOrPackageTableName = triggerOrPackageName.split("_")[0];
        String triggerTableName = triggerName.split(pref.getMDR_TABLE_SEP_FORMAT())[0];
        if (dbTable.getName().startsWith(triggerTableName)) {
            return dbTable;
        }
        return null;
    }
*/