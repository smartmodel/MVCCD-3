package comparatorsql.fetcher.oracle;

import comparatorsql.RecuperationConnectionDB;
import comparatorsql.fetcher.DbFetcher;
import connections.ConConnection;
import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mpdr.*;
import mpdr.oracle.*;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.oracle.MPDROraclePackage;
import mpdr.tapis.oracle.MPDROracleTrigger;
import preferences.Preferences;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbFetcherOracle extends DbFetcher {

    private final Connection connection;
    private final ConConnection conConnection;
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private String databaseName;
    private MPDRModel mpdrDbModel;
    private List<String> sequencesNotInTable = new ArrayList<>();
    private List<String> packagesNotInTable = new ArrayList<>();
    private List<String> triggersNotInTable = new ArrayList<>();

    public DbFetcherOracle() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.databaseName = this.conConnection.getDbName();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrDbModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
        this.mpdrDbModel.setName(Preferences.FETCHER_ORACLE_MPDR_NAME);
    }
    public DbFetcherOracle(ConConnection conConnection, Connection connection) throws SQLException {
        this.connection = connection;
        this.conConnection = conConnection;
        this.databaseMetaData = this.connection.getMetaData();
        this.databaseName = this.conConnection.getDbName();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrDbModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
        this.mpdrDbModel.setName(Preferences.FETCHER_ORACLE_MPDR_NAME);
    }

    public void fetch() throws SQLException {
        fetchTables();
        fetchTriggers(mpdrDbModel.getMPDRTables());
        fetchPackages(mpdrDbModel.getMPDRTables());
    }


    public void fetchTables() throws SQLException {
        String[] types = {Preferences.FETCHER_ORACLE_TYPE_TABLE};

        try (ResultSet result = databaseMetaData.getTables(databaseName, schemaDB, "%", types)) {
            while (result.next()) {
                MPDRTable mpdrTable = MVCCDElementFactory.instance()
                        .createMPDROracleTable(this.mpdrDbModel.getMPDRContTables(), null);
                mpdrTable.setName(result.getString(Preferences.FETCHER_ORACLE_TABLE_NAME));
                fetchColumns(mpdrTable);
                fetchPk(mpdrTable);
                fetchUnique(mpdrTable);
                fetchCheck(mpdrTable);
                //fetchFk();
                fetchSequences(mpdrTable);

            }
        }
    }

    public void fetchColumns(MPDRTable mpdrTable){
        try (ResultSet result = this.databaseMetaData.getColumns(this.databaseName, this.schemaDB, mpdrTable.getName(), null)) {
            while (result.next()) {
                MPDRColumn mpdrColumn = MVCCDElementFactory.instance().createMPDROracleColumn(mpdrTable.getMDRContColumns(), null);
                mpdrColumn.setName(result.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME));
                mpdrColumn.setDatatypeLienProg(result.getString(Preferences.FETCHER_ORACLE_TYPE_NAME));
                if (result.getBoolean(Preferences.FETCHER_ORACLE_NULLABLE)){
                    mpdrColumn.setMandatory(false); //Si nullable -> non obligatoire
                } else{
                    mpdrColumn.setMandatory(true); //Si non-nullable -> obligatoire
                }
                mpdrColumn.setSize(result.getInt(Preferences.FETCHER_ORACLE_COLUMN_SIZE));
                mpdrColumn.setScale(result.getInt(Preferences.FETCHER_ORACLE_DECIMAL_DIGITS));
                mpdrColumn.setInitValue(result.getString(Preferences.FETCHER_ORACLE_COLUMN_DEF));
                mpdrColumn.setOrder(result.getInt(Preferences.FETCHER_ORACLE_ORDINAL_POSITION));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void fetchPk(MPDRTable mpdrTable) throws SQLException {
        MPDROraclePK mpdrOraclePK = null;
        try (ResultSet result = this.databaseMetaData.getPrimaryKeys(this.databaseName, this.schemaDB, mpdrTable.getName())) {
            while (result.next()) {
                if(mpdrOraclePK==null) {
                    mpdrOraclePK = MVCCDElementFactory.instance().createMPDROraclePK(mpdrTable.getMDRContConstraints(), null);
                    mpdrOraclePK.setName(result.getString(Preferences.FETCHER_ORACLE_PK_NAME));
                }
                for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
                    if(mpdrColumn.getName().equals(result.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME))){
                        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(mpdrOraclePK, (MLDRParameter) null);
                        //Ajout de la méthode getId() dans la classe "ProjectElement"
                        mpdrParameter.setTargetId(mpdrColumn.getId());
                    }
                }
            }
        }
    }

    public void fetchUnique(MPDRTable mpdrTable) throws SQLException {
        MPDROracleUnique mpdrOracleUnique = null;
        //On ne récupère que les contraintes Unique
        //Remplacer par ? le getTable et setter ensuite
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        pStmt.setString(1,Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_UNIQUE);
        pStmt.setString(2, mpdrTable.getName());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            if (mpdrOracleUnique == null) {
                mpdrOracleUnique = MVCCDElementFactory.instance().createMPDROracleUnique(mpdrTable.getMDRContConstraints(), null);
                mpdrOracleUnique.setName(rsCurseur.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME));
            }
        }
        //On récupère toutes les contraintes et les colonnes auquelles elles sont rattachées
        String requeteSQLUserConsColumns = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONS_COLUMNS;
        PreparedStatement pStmtUserConsColumns = connection.prepareStatement(requeteSQLUserConsColumns);
        pStmtUserConsColumns.setString(1, mpdrTable.getName());
        ResultSet rsCurseur1 = pStmtUserConsColumns.executeQuery();
        while (rsCurseur1.next()){
            String constraintName = rsCurseur1.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
            String columnName = rsCurseur1.getString(Preferences.FETCHER_ORACLE_COLUMN_NAME);
            int positionColumn = rsCurseur1.getInt(Preferences.FETCHER_ORACLE_POSITION);
            for (MPDRUnique mpdrUnique : mpdrTable.getMPDRUniques()) {
                if (mpdrUnique instanceof MPDROracleUnique){
                    //si la position est null, il s'agit d'une contrainte qui ne peut être mise que sur une seule colonne (= Not Nul)
                    if(positionColumn>0 && constraintName.equals(mpdrUnique.getName())){
                        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter((IMPDROracleElement) mpdrUnique, (MLDRParameter) null);
                        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()) {
                            if(mpdrColumn.getName().equals(columnName)){
                                mpdrParameter.setTargetId(mpdrColumn.getId());
                            }
                        }
                    }
                }
            }
        }
    }

    //ATTENTION, J'écrase les contraintes car j'en crée une seulement alors que la db en possède 2
    public void fetchCheck(MPDRTable mpdrTable) throws SQLException {
        MPDROracleCheckSpecific mpdrOracleCheckSpecific = null;
        Map<String,String> conditions = new HashMap<>();
        MPDROracleParameter mpdrOracleParameter;
        StringBuilder requeteSQL = new StringBuilder();
        //Utilisation de la colonne search_condition_vc (introduit à la version 12c) qui est de type varchar car search_condition est de type long
        //Lors de la création d'une contrainte check, la DB en crée une identique mais avec le nom qui commence par SYS donc on ne la veut pas
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_CONSTRAINTS);
        requeteSQL.append(Preferences.FETCHER_ORACLE_AND);
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SEARCH_CONDITION_VC);
        requeteSQL.append(Preferences.FETCHER_ORACLE_AND);
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_CONSTRAINT_NAME_NOT_LIKE);
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        pStmt.setString(1, Preferences.FETCHER_ORACLE_CONSTRAINT_TYPE_CHECK);
        pStmt.setString(2, mpdrTable.getName());
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()) {
            String constraintName = rsCurseur.getString(Preferences.FETCHER_ORACLE_CONSTRAINT_NAME);
            String condition = rsCurseur.getString(Preferences.FETCHER_ORACLE_SEARCH_CONDITION_VC);
            conditions.put(constraintName,condition);
                mpdrOracleCheckSpecific = MVCCDElementFactory.instance().createMPDROracleCheckSpecific(mpdrTable.getMDRContConstraints(), null);
                mpdrOracleCheckSpecific.setName(constraintName);
                mpdrOracleParameter = (MPDROracleParameter) MVCCDElementFactory.instance().createMPDROracleParameter(mpdrOracleCheckSpecific, (MLDRParameter) null);
                mpdrOracleParameter.setValue(condition);
        }
        /*
        //On récupère toutes les contraintes et les colonnes auquelles elles sont rattachées
        StringBuilder requeteSQL1 = new StringBuilder();
        requeteSQL1.append("SELECT * FROM USER_CONS_COLUMNS WHERE TABLE_NAME = '" + mpdrTable.getName() + "'");
        PreparedStatement pStmt1 = connection.prepareStatement(requeteSQL1.toString());
        ResultSet rsCurseur1 = pStmt1.executeQuery();
        while (rsCurseur1.next()){
            String constraintName = rsCurseur1.getString("CONSTRAINT_NAME");
            for (MPDRCheck check : mpdrTable.getMPDRChecks()) {
                if(constraintName.equals(check.getName())){
                            check.getMPDRParameter().setValue(conditions.get(constraintName));
                        }
                    }
                }
        conditions.clear();
         */
    }

    private void fetchFk(MPDRTable mpdrTable) {
        //Créer des extrémités de relation + des contraintes et paramètres

    }

    //A VOIR POUR SORTIR CETTE METHODE DE LA TABLE POUR LA METTRE AU NIVEAU SUPERIEUR A CAUSE DE LA LISTE DES SEQ A SUPPRIMER
    public void fetchSequences(MPDRTable mpdrTable) throws SQLException {
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_SEQUENCES;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()){
            String sequenceName = rsCurseur.getString(Preferences.FETCHER_ORACLE_SEQUENCE_NAME);
            if(!sequencesNotInTable.contains(sequenceName))
            sequencesNotInTable.add(sequenceName);
            if(findTableAccueilSequence(mpdrTable,sequenceName)!=null){
                //TODO A vérifier comment sélectionner la bonne colonne
                MPDROracleSequence mpdrOracleSequence = MVCCDElementFactory.instance().createMPDROracleSequence(mpdrTable.getMPDRColumnsSortDefault().get(0), null);
                mpdrOracleSequence.setName(sequenceName);
                mpdrOracleSequence.setMinValue(rsCurseur.getInt(Preferences.FETCHER_ORACLE_MIN_VALUE));
                mpdrOracleSequence.setMinValue(rsCurseur.getInt(Preferences.FETCHER_ORACLE_INCREMENT_BY));
                //ATTENTION, les séquence ne sont pas supprimés lorsque les recharges dans une autre table.
                sequencesNotInTable.remove(sequenceName);
            }
        }
    }

    public void fetchPackages(List<MPDRTable> mpdrTables) throws SQLException {
        List<String> packages = new ArrayList<>();
        StringBuilder requeteSQL = new StringBuilder();
        requeteSQL.append(Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_PROCEDURES);
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL.toString());
        pStmt.setString(1, Preferences.FETCHER_ORACLE_PACKAGE);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()){
            packages.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_OBJECT_NAME));
            packagesNotInTable.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_OBJECT_NAME));
        }
        for (MPDRTable mpdrTable : mpdrTables) {
            //package est un mot réservé donc utilisation du nom de variable "paquet"
            for (String paquet : packages) {
                if (findTableAccueilTriggerOrPackage(mpdrTable, paquet)!=null) {
                    if (mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages() == null) {
                        MVCCDElementFactory.instance().createMPDROracleBoxPackages(mpdrTable.getMPDRContTAPIs(), null);
                    }
                    MPDROraclePackage mpdrOraclePackage = MVCCDElementFactory.instance().createMPDROraclePackage(mpdrTable.getMPDRContTAPIs().getMPDRBoxPackages(), null);
                    mpdrOraclePackage.setName(paquet);
                    packagesNotInTable.remove(paquet);
                }
            }
        }
    }

    public void fetchTriggers(List<MPDRTable> mpdrTables) throws SQLException {
        List<String> triggers = new ArrayList<>();
        String requeteSQL = Preferences.FETCHER_ORACLE_REQUETE_SQL_USER_TRIGGERS;
        PreparedStatement pStmt = connection.prepareStatement(requeteSQL);
        ResultSet rsCurseur = pStmt.executeQuery();
        while (rsCurseur.next()){
            triggers.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_TRIGGER_NAME));
            triggersNotInTable.add(rsCurseur.getString(Preferences.FETCHER_ORACLE_TRIGGER_NAME));
        }
        for (MPDRTable mpdrTable : mpdrTables) {
            for (String trigger : triggers) {
                if (findTableAccueilTriggerOrPackage(mpdrTable, trigger)!=null) {
                    if (mpdrTable.getMPDRContTAPIs().getMPDRBoxTriggers() == null) {
                        MVCCDElementFactory.instance().createMPDROracleBoxTriggers(mpdrTable.getMPDRContTAPIs(), null);
                    }
                    MPDROracleTrigger mpdrOracleTrigger = MVCCDElementFactory.instance().createMPDROracleTrigger(mpdrTable.getMPDRContTAPIs().getMPDRBoxTriggers(), null);
                    mpdrOracleTrigger.setName(trigger);
                    triggersNotInTable.remove(trigger);
                }
            }
        }
    }

    public List<String> findColumns(String constraintName, int position){
        List<String> mpdrColumns = new ArrayList<>();
        if (position>0) {
            String columnName = constraintName.split("_")[position + 1];
            mpdrColumns.add(columnName);
        }
        return mpdrColumns;
    }

    public MPDRTable findTableAccueilSequence(MPDRTable mpdrTable, String sequenceName) {
        String seqTableName = sequenceName.split("_")[0];
        if(mpdrTable.getName().startsWith(seqTableName)) {
            return mpdrTable;
        }
        return null;
    }

    //Si on veut pour une table spécifique uniquement
    public MPDRTable findTableAccueilTriggerOrPackage(MPDRTable mpdrTable, String triggerOrPackageName) {
        String triggerOrPackageTableName = triggerOrPackageName.split("_")[0];
        if(mpdrTable.getName().startsWith(triggerOrPackageTableName)) {
                return mpdrTable;
            }
        return null;
    }

    //Si on veut trouver un correspondance dans l'ensembles des tables récupérées
    public MPDRTable findTableAccueilTriggerOrPackage(List<MPDRTable> mpdrTablesList, List<String> triggersOrPackages) {
        for (MPDRTable mpdrTable : mpdrTablesList) {
            for (String trigger : triggersOrPackages) {
                String triggerOrPackageTableName = trigger.split("_")[0];
                if (mpdrTable.getName().startsWith(triggerOrPackageTableName)) {
                    return mpdrTable;
                }
            }
        }
        return null;
    }

    public MPDRModel getMpdrDbModel() {
        return mpdrDbModel;
    }

    public List<String> getSequencesNotInTable() {
        return sequencesNotInTable;
        //TODO VINCENT
        // Ajouter au script pour suppression
    }

    public List<String> getPackagesNotInTable() {
        return packagesNotInTable;
    }

    public List<String> getTriggersNotInTable() {
        return triggersNotInTable;
    }

    public void fetchIndex(){
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
