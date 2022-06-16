package consolidationMpdrDb.fetcher.oracle;

import consolidationMpdrDb.RecuperationConnectionDB;
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

    public DbFetcherOracle() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.databaseName = this.conConnection.getDbName();
        this.schemaDB = this.conConnection.getUserName();
        this.dbModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
        this.dbModel.setName(Preferences.FETCHER_ORACLE_MPDR_NAME);
    }

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
    }


    public void fetchTables() throws SQLException {
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

    public void fetchColumns(MPDROracleTable dbTable) {
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


    public void fetchPk(MPDROracleTable dbTable) throws SQLException {
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
                    }
                }
            }
        }
    }

    public void fetchUnique(MPDROracleTable dbTable) throws SQLException {
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
        //On récupère toutes les contraintes et les colonnes auquelles elles sont rattachées
        String requeteSQLUserConsColumns = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONS_COLUMNS;
        PreparedStatement pStmtUserConsColumns = connection.prepareStatement(requeteSQLUserConsColumns);
        pStmtUserConsColumns.setString(1, dbTable.getName());
        ResultSet rsCurseurUserConsColumns = pStmtUserConsColumns.executeQuery();
        while (rsCurseurUserConsColumns.next()) {
            String constraintName = rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
            String columnName = rsCurseurUserConsColumns.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME);
            int positionColumn = rsCurseurUserConsColumns.getInt(Preferences.FETCHER_ORACLE_POSITION);
            for (MPDRUnique dbUniqueConst : dbTable.getMPDRUniques()) {
                if (dbUniqueConst instanceof MPDROracleUnique) {
                    //si la position est null, il s'agit d'une contrainte qui doit être mise que sur une seule colonne (par exemple NOT NULL)
                    if (positionColumn > 0 && constraintName.equals(dbUniqueConst.getName())) {
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
    }

    //ATTENTION, J'écrase les contraintes car j'en crée une seulement alors que la db en possède 2
    public void fetchCheck(MPDRTable dbTable) throws SQLException {
        MPDROracleCheckSpecific dbCheckSpecific = null;
        //Map<String,String> conditions = new HashMap<>();
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
            //conditions.put(constraintName,condition);
            dbCheckSpecific = MVCCDElementFactory.instance().createMPDROracleCheckSpecific(dbTable.getMDRContConstraints(), null);
            dbCheckSpecific.setName(constraintName);
            dbParameter = (MPDROracleParameter) MVCCDElementFactory.instance().createMPDROracleParameter(dbCheckSpecific, (MLDRParameter) null);
            dbParameter.setValue(condition);
        }
        /*
        //On récupère toutes les contraintes et les colonnes auquelles elles sont rattachées
        StringBuilder requeteSQL1 = new StringBuilder();
        requeteSQL1.append("SELECT * FROM USER_CONS_COLUMNS WHERE TABLE_NAME = '" + dbTable.getName() + "'");
        PreparedStatement pStmt1 = connection.prepareStatement(requeteSQL1.toString());
        ResultSet rsCurseur1 = pStmt1.executeQuery();
        while (rsCurseur1.next()){
            String constraintName = rsCurseur1.getString("CONSTRAINT_NAME");
            for (MPDRCheck check : dbTable.getMPDRChecks()) {
                if(constraintName.equals(check.getName())){
                            check.getMPDRParameter().setValue(conditions.get(constraintName));
                        }
                    }
                }
        conditions.clear();
         */
    }

    private void fetchFk(MPDRTable dbTable) throws SQLException {
        //Créer des extrémités de relation + des contraintes et paramètres
        MPDROracleFK dbFK = null;
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        pStmt.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_FK);
        pStmt.setString(2, dbTable.getName());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            if (!dbTable.getMPDRFKs().contains(dbFK)) {
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
                            //Ajout de la méthode getId() dans la classe "ProjectElement"
                            dbParameter.setTargetId(dbColumn.getId());
                            //A VOIR POUR AFFECTER LA COLONNE PK setmdrColumnPK
                        }
                    }
                }
            }
        }
    }

    //A VOIR POUR SORTIR CETTE METHODE DE LA TABLE POUR LA METTRE AU NIVEAU SUPERIEUR A CAUSE DE LA LISTE DES SEQ A SUPPRIMER
    public void fetchSequences(MPDROracleTable dbTable) throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_SEQUENCES;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            String sequenceName = rsCurseur.getString(Preferences.FETCHER_ORACLE_SEQUENCE_NAME);
            if (!sequencesNotInTable.contains(sequenceName))
                sequencesNotInTable.add(sequenceName);
            if (findTableAccueilSequence(dbTable, sequenceName) != null) {
                //TODO A vérifier comment sélectionner la bonne colonne //on peut setter la colonne pk avec mdrSetColumnPk
                MPDROracleSequence mpdrOracleSequence = MVCCDElementFactory.instance().createMPDROracleSequence(dbTable.getMPDRColumnsSortDefault().get(0), null);
                mpdrOracleSequence.setName(sequenceName);
                mpdrOracleSequence.setMinValue(rsCurseur.getInt(Preferences.FETCHER_ORACLE_MIN_VALUE));
                mpdrOracleSequence.setMinValue(rsCurseur.getInt(Preferences.FETCHER_ORACLE_INCREMENT_BY));
                //ATTENTION, les séquence ne sont pas supprimés lorsque les recharges dans une autre table.
                sequencesNotInTable.remove(sequenceName);
            }
        }
    }

    public void fetchPackages(List<MPDRTable> dbTables) throws SQLException {
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
                if (findTableAccueilTriggerOrPackage(dbTable, paquet) != null) {
                    if (dbTable.getMPDRContTAPIs().getMPDRBoxPackages() == null) {
                        MVCCDElementFactory.instance().createMPDROracleBoxPackages(dbTable.getMPDRContTAPIs(), null);
                    }
                    MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(dbTable.getMPDRContTAPIs().getMPDRBoxPackages(), null);
                    mpdrOraclePackage.setName(paquet);
                    packagesNotInTable.remove(paquet);
                }
            }
        }
    }

    public void fetchTriggers(List<MPDRTable> dbTables) throws SQLException {
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
                if (findTableAccueilTriggerOrPackage(dbTable, trigger) != null) {
                    if (dbTable.getMPDRContTAPIs().getMPDRBoxTriggers() == null) {
                        MVCCDElementFactory.instance().createMPDROracleBoxTriggers(dbTable.getMPDRContTAPIs(), null);
                    }
                    MPDROracleTrigger dbTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(dbTable.getMPDRContTAPIs().getMPDRBoxTriggers(), null);
                    dbTrigger.setName(trigger);
                    triggersNotInTable.remove(trigger);
                }
            }
        }
    }

    public List<String> findColumns(String constraintName, int position) {
        List<String> dbColumns = new ArrayList<>();
        if (position > 0) {
            String columnName = constraintName.split("_")[position + 1];
            dbColumns.add(columnName);
        }
        return dbColumns;
    }

    //Une séquence est nommée {tableShortName}{_SEQPK} = QUAL_SEQPK
    //ATTENTION: le format peut être modifié par l'utilisateur dans ses préférences de projet
    //-> Cette méthode gère uniquement le cas où le nom de la table est au début
    public MPDRTable findTableAccueilSequence(MPDROracleTable dbTable, String sequenceName) {
        //String seqTableName = sequenceName.split("_")[0];
        String seqTableName = sequenceName.split(pref.getMDR_TABLE_SEP_FORMAT())[0];
        if (dbTable.getName().startsWith(seqTableName)) {
            return dbTable;
        }
        return null;
    }

    //Si on veut pour une table spécifique uniquement
    //Un trigger est nommée {tableShortName}{tableSep}{typeTriggerMarker} = QUAL_BIR
    //Un package est nommé {tableShortName}{tableSep}{typePackageMarker} = QUAL_TAPIs
    //ATTENTION: le format peut être modifié par l'utilisateur dans ses préférences de projet
    //-> Cette méthode gère uniquement le cas où le nom de la table est au début
    public MPDRTable findTableAccueilTriggerOrPackage(MPDRTable dbTable, String triggerOrPackageName) {
        //String triggerOrPackageTableName = triggerOrPackageName.split("_")[0];
        String triggerOrPackageTableName = triggerOrPackageName.split(pref.getMDR_TABLE_SEP_FORMAT())[0];
        if (dbTable.getName().startsWith(triggerOrPackageTableName)) {
            return dbTable;
        }
        return null;
    }

    //Si on veut trouver un correspondance dans l'ensembles des tables récupérées
    public MPDRTable findTableAccueilTriggerOrPackage(List<MPDROracleTable> dbTablesList, List<String> triggersOrPackages) {
        for (MPDROracleTable dbTable : dbTablesList) {
            for (String trigger : triggersOrPackages) {
                String triggerOrPackageTableName = trigger.split("_")[0];
                if (dbTable.getName().startsWith(triggerOrPackageTableName)) {
                    return dbTable;
                }
            }
        }
        return null;
    }

    public MPDROracleModel getDbModel() {
        return dbModel;
    }

    public List<String> getDbSequencesNotInTable() {
        return sequencesNotInTable;
        //TODO VINCENT
        // Ajouter au script pour suppression si nécessaire.
    }

    //Attention, car il se peut qu'ils soient nécessaires donc ne pas les supprimer
    public List<String> getDbPackagesNotInTable() {
        return packagesNotInTable;
    }

    //Attention, car il se peut qu'ils soient nécessaires donc ne pas les supprimer
    public List<String> getDbTriggersNotInTable() {
        return triggersNotInTable;
    }

    public void fetchIndex() {
        // A développer ultérieurement
    }
/*
    public void fetchUnique(MPDRTable mpdrTable){
        MPDROracleUnique mpdrOracleUnique = null;
        try (ResultSet result = databaseMetaData.getIndexInfo(databaseName, schemaDB, mpdrTable.getName(), true, true)) {
            while (result.next()) {
                if(mpdrOracleUnique == null){
                    mpdrOracleUnique = MVCCDElementFactory.instance().createMPDROracleUnique(mpdrTable.getMDRContConstraints(), null);
                    mpdrOracleUnique.setName(result.getString("COLUMN_NAME"));
                }
                for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
                    if (mpdrColumn.getName().equals(result.getString("COLUMN_NAME"))) {
                        MVCCDElementFactory.instance().createMPDROracleParameter(mpdrOracleUnique, (MLDRParameter) null);
                        //MVCCDElementFactory.instance().createMPDROracleParameter((IMPDROracleElement) mpdrOracleUnique, mpdrColumn);
                    }
                }
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
    }

    public void fetchCheck(MPDRTable mpdrTable, List<MPDRColumn> mpdrColumns){
        try (ResultSet result = databaseMetaData.getIndexInfo(databaseName, schemaDB, mpdrTable.getName(), true, true)) {
            while (result.next()) {
                // A voir comment gérer plusieurs colonnes
                for (MPDRColumn mpdrColumn : mpdrColumns) {
                    if (mpdrColumn.getName().equals(result.getString("COLUMN_NAME"))) {
                        if (result.getString("FILTER_CONDITION") != null) {
                            MVCCDElementFactory.instance().createMPDROracleCheckSpecific(mpdrTable.getMDRContConstraints(), null);
                            //
                            //MVCCDElementFactory.instance().createMPDROracleParameter();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void fetchPackages(MPDRTable mpdrTable) throws SQLException {
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append("SELECT DISTINCT(OBJECT_NAME) FROM USER_PROCEDURES WHERE OBJECT_TYPE='PACKAGE'");
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()){
            if (findTableAccueilTriggerOrPackage(mpdrTable, rsCurseur.getString("OBJECT_NAME"))!=null) {
                if(mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages()== null){
                    MVCCDElementFactory.instance().createMPDROracleBoxPackages(mpdrTable.getMPDRContTAPIs(), null);
                }
                MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages(),null);
                mpdrOraclePackage.setName(rsCurseur.getString("OBJECT_NAME"));
            }
        }
    }

    public void fetchTriggers(MPDRTable mpdrTable) throws SQLException {
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append("SELECT trigger_name FROM user_triggers");
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            if (findTableAccueilTriggerOrPackage(mpdrTable, rsCurseur.getString("TRIGGER_NAME"))!=null) {
                if (mpdrTable.getMPDRContTAPIs().getMPDRBoxTriggers() == null) {
                    MVCCDElementFactory.instance().createMPDROracleBoxTriggers(mpdrTable.getMPDRContTAPIs(), null);
                }
                MPDROracleTrigger mpdrOracleTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(mpdrTable.getMPDRContTAPIs().getMPDRBoxTriggers(), null);
                mpdrOracleTrigger.setName(rsCurseur.getString("TRIGGER_NAME"));
            }
        }
    }


*/
/*
    // Se met dans la boucle de la colonne et vérifie en fonction du nom de la colonne.
    public void fetchPk2(MPDRTable mpdrTable, MPDRColumn mpdrColumn) throws SQLException {
        try (ResultSet result = this.databaseMetaData.getImportedKeys(this.databaseName, this.schemaDB, mpdrTable.getName())) {
            while (result.next()) {
                if (result.getString("PKCOLUMN_NAME").equals(mpdrColumn.getName())){
                    MPDROraclePK mpdrOraclePK = MVCCDElementFactory.instance().createMPDROraclePK(mpdrTable.getMDRContConstraints(), null);
                    mpdrOraclePK.setName(result.getString("PK_NAME"));
                    //A implémenter
                    //MVCCDElementFactory.instance().createMPDROracleParameter();
                }
            }
        }
    }
    */
}
