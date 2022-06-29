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
import java.util.*;

public class DbFetcherOracle extends DbFetcher {

    private final Connection connection;
    private final ConConnection conConnection;
    private Map<String, String> triggersSequencesMap = new HashMap<>(); //K=sequenceName, V=triggerName
    private Map<String, String> packagesMap = new HashMap<>(); //K=packageName, V= tableName
    private Map<String, String> triggersMap = new HashMap<>(); //K=triggerName, V=tableName
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
        fetchTriggers(dbModel.getMPDRTables()); //Trigger avant séquence car la séquence à besoin des trigger pour etre attribuée à une table
        fetchDependencies();
        fetchSequences(dbModel.getMPDRTables());
        fetchPackages(dbModel.getMPDRTables());
        //fetchIndex(); //Non implémenté dans ce projet - Vincent
    }


    private void fetchTables() throws SQLException {
        String[] types = {Preferences.FETCHER_ORACLE_TABLE};
        try (ResultSet rsTables = databaseMetaData.getTables(databaseName, schemaDB, "%", types)) {
            while (rsTables.next()) {
                MPDROracleTable dbTable = MVCCDElementFactory.instance()
                        .createMPDROracleTable(this.dbModel.getMPDRContTables(), null);
                dbTable.setName(rsTables.getString(Preferences.FETCHER_ORACLE_TABLE_NAME));
                //On crée un box pour les packages et triggers pour éviter d'avoir un nullpointeur lors des comparaisons quand on boucle sur les box
                MVCCDElementFactory.instance().createMPDROracleBoxPackages(dbTable.getMPDRContTAPIs(), null);
                MVCCDElementFactory.instance().createMPDROracleBoxTriggers(dbTable.getMPDRContTAPIs(), null);
                fetchColumns(dbTable);
                fetchPk(dbTable);
                fetchUnique(dbTable);
                fetchCheck(dbTable);
                fetchFk(dbTable);
                fetchIndex(dbTable);
            }
        }
    }

    private void fetchColumns(MPDROracleTable dbTable) throws SQLException {
        try (ResultSet rsColumns = this.databaseMetaData.getColumns(this.databaseName, this.schemaDB, dbTable.getName(), null)) {
            while (rsColumns.next()) {
                MPDROracleColumn dbColumn = MVCCDElementFactory.instance().createMPDROracleColumn(dbTable.getMDRContColumns(), null);
                dbColumn.setName(rsColumns.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME));
                dbColumn.setDatatypeLienProg(rsColumns.getString(Preferences.FETCHER_ORACLE_TYPE_NAME));
                dbColumn.setMandatory(!rsColumns.getBoolean(Preferences.FETCHER_ORACLE_NULLABLE)); //Si nullable -> non obligatoire
                dbColumn.setSize(rsColumns.getInt(Preferences.FETCHER_ORACLE_COLUMN_SIZE));
                dbColumn.setScale(rsColumns.getInt(Preferences.FETCHER_ORACLE_DECIMAL_DIGITS));
                dbColumn.setInitValue(rsColumns.getString(Preferences.FETCHER_ORACLE_COLUMN_DEF));
                dbColumn.setOrder(rsColumns.getInt(Preferences.FETCHER_ORACLE_ORDINAL_POSITION));
            }
        }
    }


    private void fetchPk(MPDROracleTable dbTable) throws SQLException {
        MPDROraclePK dbPK = null;
        try (ResultSet rsPk = this.databaseMetaData.getPrimaryKeys(this.databaseName, this.schemaDB, dbTable.getName())) {
            while (rsPk.next()) {
                if (dbPK == null) {
                    dbPK = MVCCDElementFactory.instance().createMPDROraclePK(dbTable.getMDRContConstraints(), null);
                    dbPK.setName(rsPk.getString(Preferences.FETCHER_ORACLE_PK_NAME));
                }
                for (MPDRColumn dbColumn : dbTable.getMPDRColumns()) {
                    if (dbColumn.getName().equals(rsPk.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME))) {
                        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(dbPK, (MLDRParameter) null);
                        //Ajout de la méthode getId() dans la classe "ProjectElement"
                        mpdrParameter.setTargetId(dbColumn.getId());
                        mpdrParameter.setName(dbColumn.getName());
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
        PreparedStatement pStmtUnique = connection.prepareStatement(requeteSQL);
        pStmtUnique.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_UNIQUE);
        pStmtUnique.setString(2, dbTable.getName());
        ResultSet rsCurseurUnique = pStmtUnique.executeQuery();
        while (rsCurseurUnique.next()) {
            //Si le nom de la contrainte n'existe pas, on en crée une
            if (!dbTable.getMPDRUniques().contains(rsCurseurUnique.getString((Preferences.FETCHER_ORACLE_CONSTRAINT_NAME)))) {
                dbUnique = MVCCDElementFactory.instance().createMPDROracleUnique(dbTable.getMDRContConstraints(), null);
                dbUnique.setName(rsCurseurUnique.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME));
            }
        }
        pStmtUnique.close();
        rsCurseurUnique.close();
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
        PreparedStatement pStmtChecks = connection.prepareStatement(requeteSQL.toString());
        pStmtChecks.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_CHECK);
        pStmtChecks.setString(2, dbTable.getName());
        ResultSet rsCursorChecks = pStmtChecks.executeQuery();
        while (rsCursorChecks.next()) {
            String constraintName = rsCursorChecks.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
            String condition = rsCursorChecks.getString(Preferences.FETCHER_ORACLE_SEARCH_CONDITION_VC);
            dbCheckSpecific = MVCCDElementFactory.instance().createMPDROracleCheckSpecific(dbTable.getMDRContConstraints(), null);
            dbCheckSpecific.setName(constraintName);
            dbParameter = (MPDROracleParameter) MVCCDElementFactory.instance().createMPDROracleParameter(dbCheckSpecific, (MLDRParameter) null);
            dbParameter.setValue(condition);
        }
        pStmtChecks.close();
        rsCursorChecks.close();
    }

    private void fetchFk(MPDRTable dbTable) throws SQLException {
        MPDROracleFK dbFK = null;
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS;
        PreparedStatement pStmtFks = connection.prepareStatement(requeteSQL);
        pStmtFks.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_FK);
        pStmtFks.setString(2, dbTable.getName());
        ResultSet rsCursorFks = pStmtFks.executeQuery();
        while (rsCursorFks.next()) {
            if (!dbTable.getMPDRFKs().contains(rsCursorFks.getString((Preferences.FETCHER_ORACLE_CONSTRAINT_NAME)))) {
                dbFK = MVCCDElementFactory.instance().createMPDROracleFK(dbTable.getMDRContConstraints(), null);
                dbFK.setName(rsCursorFks.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME));
                if (rsCursorFks.getString(Preferences.FETCHER_ORACLE_DELETE_RULE).equals(Preferences.FETCHER_ORACLE_CASCADE)) {
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
                            //A VOIR POUR AFFECTER LA COLONNE PK setmdrColumnPK si nécessaire
                        }
                    }
                }
            }
            pStmtUserConsColumns.close();
            rsCurseurUserConsColumns.close();
        }
        pStmtFks.close();
        rsCursorFks.close();
    }

    private void fetchIndex(MPDROracleTable dbTable) throws SQLException {
        MPDRIndex dbIndex = null;
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_IND_COLUMNS;
        PreparedStatement pStmtIndex = connection.prepareStatement(requeteSQL);
        ResultSet rsCursorIndex = pStmtIndex.executeQuery();
        while (rsCursorIndex.next()){
            String indexName = rsCursorIndex.getString(Preferences.FETCHER_ORACLE_INDEX_NAME);
            String tableName = rsCursorIndex.getString(Preferences.FETCHER_ORACLE_TABLE_NAME);
            String columnName = rsCursorIndex.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME);
            if(dbTable.getName().equals(tableName)) {
                //Si le nom de l'index n'existe pas, on en crée un
                if (!dbTable.getMPDRIndexes().contains(indexName)) {
                    dbIndex = MVCCDElementFactory.instance().createMPDROracleIndex(dbTable.getMDRContConstraints(), null);
                    dbIndex.setName(indexName);
                }
                for (MPDRColumn dbColumn : dbTable.getMPDRColumns()) {
                    if (dbColumn.getName().equals(columnName)) {
                        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter((IMPDROracleElement) dbIndex, (MLDRParameter) null);
                        mpdrParameter.setTargetId(dbColumn.getId());
                        mpdrParameter.setName(dbColumn.getName());
                    }
                }
            }
        }
    }

    private void fetchSequences(List<MPDRTable> dbTables) throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_SEQUENCES;
        PreparedStatement pStmtSequences = connection.prepareStatement(requeteSQL);
        ResultSet rsCursorSequences = pStmtSequences.executeQuery();
        while (rsCursorSequences.next()) {
            String sequenceName = rsCursorSequences.getString(Preferences.FETCHER_ORACLE_SEQUENCE_NAME);
            sequencesNotInTable.add(sequenceName);
            for (MPDRTable dbTable : dbTables) {
                if (findTableAccueilleSequence(dbTable, sequenceName) != null) {
                    MPDROracleSequence mpdrOracleSequence;
                    for (MPDRColumn mpdrColumn : dbTable.getMPDRColumns()) {
                        if (mpdrColumn.getIsPk()) {
                            mpdrOracleSequence = MVCCDElementFactory.instance().createMPDROracleSequence(mpdrColumn, null);
                            mpdrOracleSequence.setName(sequenceName);
                            mpdrOracleSequence.setMinValue(rsCursorSequences.getInt(Preferences.FETCHER_ORACLE_MIN_VALUE));
                            mpdrOracleSequence.setMinValue(rsCursorSequences.getInt(Preferences.FETCHER_ORACLE_INCREMENT_BY));
                            sequencesNotInTable.remove(sequenceName);
                            break;
                        }
                    }
                }
            }
        }
        pStmtSequences.close();
        rsCursorSequences.close();
    }

    private void fetchTriggers(List<MPDRTable> dbTables) throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_TRIGGERS;
        PreparedStatement pStmtTriggers = connection.prepareStatement(requeteSQL);
        ResultSet rsCursorTriggers = pStmtTriggers.executeQuery();
        while (rsCursorTriggers.next()) {
            triggersMap.put(rsCursorTriggers.getString(Preferences.FETCHER_ORACLE_TRIGGER_NAME), rsCursorTriggers.getString(Preferences.FETCHER_ORACLE_TABLE_NAME));
            triggersNotInTable.add(rsCursorTriggers.getString(Preferences.FETCHER_ORACLE_TRIGGER_NAME));

        }
        for (MPDRTable dbTable : dbTables) {
            if (triggersMap.containsValue(dbTable.getName())) {
                MPDROracleTrigger dbTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(dbTable.getMPDRContTAPIs().getMPDRBoxTriggers(), null);
                String triggerName=null;
                for (Map.Entry<String, String> entry : triggersMap.entrySet()) {
                    if(entry.getValue().equals(dbTable.getName())){
                        triggerName = entry.getKey();
                    }
                }
                dbTrigger.setName(triggerName);
                triggersNotInTable.remove(triggerName);
            }
        }
        pStmtTriggers.close();
        rsCursorTriggers.close();
    }

    private void fetchPackages(List<MPDRTable> dbTables) throws SQLException {
        List<String> packages = new ArrayList<>();
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_PROCEDURES);
        PreparedStatement pStmtPackages = connection.prepareStatement(requeteSQL.toString());
        pStmtPackages.setString(1, Preferences.FETCHER_ORACLE_PACKAGE);
        ResultSet rsCursorPackages = pStmtPackages.executeQuery();
        while (rsCursorPackages.next()) {
            packages.add(rsCursorPackages.getString(Preferences.FETCHER_ORACLE_OBJECT_NAME));
            packagesNotInTable.add(rsCursorPackages.getString(Preferences.FETCHER_ORACLE_OBJECT_NAME));
        }
        pStmtPackages.close();
        rsCursorPackages.close();

        for (MPDRTable dbTable : dbTables) {
            //package est un mot réservé donc utilisation du nom de variable "paquet"
            for (String paquet : packages) {
                if (findTableAccueillePackage(dbTable, paquet) != null) {
                    //if (findTableAccueillePackage(dbTable, paquet) != null) {
                    MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(dbTable.getMPDRContTAPIs().getMPDRBoxPackages(), null);
                    mpdrOraclePackage.setName(paquet);
                    packagesNotInTable.remove(paquet);
                }
            }
        }
    }

    private MPDRTable findTableAccueilleSequence(MPDRTable dbTable, String sequenceName) {
        Iterator<String> itr = triggersSequencesMap.keySet().iterator(); //K=sequenceName, V=triggerName
        while (itr.hasNext()) {
            String seqName = itr.next();
            String triggerName = triggersSequencesMap.get(seqName);
            if(seqName.equals(sequenceName)){
                String tabName= triggersMap.get(triggerName); //K=triggerName, V=tableName
                if(dbTable.getName().equals(tabName)){
                    return dbTable;
                }
            }
        }
        return null;
    }


    private MPDRTable findTableAccueillePackage(MPDRTable dbTable, String packageName) {
        if(packagesMap.get(packageName)!=null) {
            String tableName = packagesMap.get(packageName);
            if(tableName.equals(dbTable.getName())){
                return dbTable;
            }
        }
        return null;
    }

    //Attention, ces méthode ne récupère que les séquences liées à un trigger et que les packages liés à une table
    private void fetchDependencies() throws SQLException {
        fetchDependenciesSequences();
        fetchDependenciesPackages();
    }

    private void fetchDependenciesSequences() throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_USER_DEPENDENCIES;
        PreparedStatement pStmtSequences = connection.prepareStatement(requeteSQL);
        pStmtSequences.setString(1, Preferences.FETCHER_ORACLE_SEQUENCE);
        pStmtSequences.setString(2, Preferences.FETCHER_ORACLE_TRIGGER);
        ResultSet rsCursorSequences = pStmtSequences.executeQuery();
        while (rsCursorSequences.next()) {
            //referenced_type = nom de la séquence, NAME = nom du trigger
            triggersSequencesMap.put(rsCursorSequences.getString(Preferences.FETCHER_ORACLE_REFERENCED_NAME),
                    rsCursorSequences.getString(Preferences.FETCHER_ORACLE_NAME));
        }
        pStmtSequences.close();
        rsCursorSequences.close();
    }

    private void fetchDependenciesPackages() throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_USER_DEPENDENCIES;
        PreparedStatement pStmtPackages = connection.prepareStatement(requeteSQL);
        pStmtPackages.setString(1, Preferences.FETCHER_ORACLE_TABLE);
        pStmtPackages.setString(2, Preferences.FETCHER_ORACLE_PACKAGE);
        ResultSet rsCursorPackages = pStmtPackages.executeQuery();
        while (rsCursorPackages.next()) {
            //NAME = nom du package, REFERENCED_NAME = nom de la table
            packagesMap.put(rsCursorPackages.getString(Preferences.FETCHER_ORACLE_NAME), rsCursorPackages.getString(Preferences.FETCHER_ORACLE_REFERENCED_NAME));
        }
        pStmtPackages.close();
        rsCursorPackages.close();
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

}