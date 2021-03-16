package main;

import diagram.mcd.MCDDiagram;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mdr.MDRConstraint;
import mdr.MDRContColumns;
import mdr.MDRContConstraints;
import mdr.MDRContTables;
import mdr.interfaces.IMDRParameter;
import messages.MessagesBuilder;
import mldr.*;
import mldr.services.MLDRContConstraintsService;
import mpdr.*;
import mpdr.mysql.MPDRMySQLColumn;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.mysql.MPDRMySQLTable;
import mpdr.oracle.*;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.postgresql.MPDRPostgreSQLColumn;
import mpdr.postgresql.MPDRPostgreSQLModel;
import mpdr.postgresql.MPDRPostgreSQLTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import project.ProjectElement;
import utilities.Trace;

public class MVCCDElementFactory {

    private static MVCCDElementFactory instance;


    public static synchronized MVCCDElementFactory instance() {
        if (instance == null) {
            instance = new MVCCDElementFactory();
        }

        return instance;
    }


    public Project createProject(String name){

        Project project = new Project(name);

        Preferences preferences = MVCCDElementFactory.instance().createPreferences(project, Preferences.REPOSITORY_PREFERENCES_NAME);
        MCDContModels mcdContModels = MVCCDElementFactory.instance().createMCDModels(project, Preferences.REPOSITORY_MCD_MODELS_NAME);

        // Les préférences de projets ne sont pas encore mises à jour...
        if (!PreferencesManager.instance().getApplicationPref().getREPOSITORY_MCD_MODELS_MANY()) {
                createContentMCDModel(mcdContModels);
        }
        return project;
    }

    public MCDContModels createMCDModels(Project project, int id){
        return new MCDContModels(project, id);
    }

    public MCDContModels createMCDModels(Project project, String name){
        return new MCDContModels(project, name);
    }

    public MCDModel createMCDModel(MCDContModels mcdContModels, int id){
        MCDModel mcdModel = new MCDModel(mcdContModels, id);
        createContentMCDModel(mcdModel);
        return mcdModel;
    }

    public MCDModel createMCDModel(MCDContModels mcdContModels){
        MCDModel mcdModel = new MCDModel(mcdContModels);
        createContentMCDModel(mcdModel);
        return mcdModel;
    }

    public MCDPackage createMCDPackage(ProjectElement parent){
        MCDPackage mcdPackage = new MCDPackage(parent);
        createContentPackage(mcdPackage);
        return mcdPackage;
    }

    public Preferences createPreferences(ProjectElement parent, String name) {
        return new Preferences(parent, name);
    }

    public MCDContDiagrams createMCDDiagrams(ProjectElement parent, int id){
        return new MCDContDiagrams(parent, id);
    }

    public MCDContDiagrams createMCDDiagrams(ProjectElement parent, String name){
        return new MCDContDiagrams(parent, name);
    }

    public MCDDiagram createMCDDiagram(ProjectElement parent, int id){
        return new MCDDiagram(parent, id);
    }

    public MCDDiagram createMCDDiagram(ProjectElement parent, String name){
        return new MCDDiagram(parent, name);
    }

    public MCDDiagram createMCDDiagram(ProjectElement parent){
        return new MCDDiagram(parent);
    }

    public MCDContEntities createMCDEntities(ProjectElement parent, String name){
        MCDContEntities mcdContEntities = new MCDContEntities(parent, name);
        return mcdContEntities;
    }

    public MCDContRelations createMCDContRelations(ProjectElement parent, String name){
        MCDContRelations mcdContRelations = new MCDContRelations(parent, name);
        return mcdContRelations;
    }

    public MCDContConstraints createMCDConstraints(ProjectElement parent, String name){
        MCDContConstraints mcdContConstraints = new MCDContConstraints(parent, name);
        return mcdContConstraints;
    }

    public MCDEntity createMCDEntity(ProjectElement parent, int id){
        MCDEntity mcdEntity = new MCDEntity(parent, id);
        this.createMCDContOfEntity(mcdEntity);
        return mcdEntity;
    }

    public MCDEntity createMCDEntity(ProjectElement parent){
        MCDEntity mcdEntity = new MCDEntity(parent);
        this.createMCDContOfEntity(mcdEntity);
        return mcdEntity;
    }

    private void createMCDContOfEntity(MCDEntity mcdEntity){
        MVCCDElementFactory.instance().createMCDAttributes(mcdEntity, Preferences.REPOSITORY_MCD_ATTRIBUTES_NAME);
        MVCCDElementFactory.instance().createMCDContEndRels(mcdEntity, Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME);
        MVCCDElementFactory.instance().createMCDConstraints(mcdEntity, Preferences.REPOSITORY_MCD_CONSTRAINTS_NAME);
    }

    public MCDContAttributes createMCDAttributes(MCDEntity parent, String name){
        return new MCDContAttributes(parent, name);
    }

    public MCDContRelEnds createMCDContEndRels(MCDElement parent, String name){
        return new MCDContRelEnds(parent, name);
    }

    public MCDAttribute createMCDAttribute(MCDContAttributes parent){
        return new MCDAttribute(parent);
    }

    public MCDAttribute createMCDAttribute(MCDContAttributes parent, int id){
        return new MCDAttribute(parent, id);
    }

    public MCDUnique createMCDUnique(MCDContConstraints parent){

        return new MCDUnique(parent);
    }

    public MCDNID createMCDNID(MCDContConstraints parent){

        return new MCDNID(parent);
    }

    public MCDParameter createMCDParameter(ProjectElement parent) {
        return new MCDParameter(parent);
    }

    public MCDAssociation createMCDAssociation( MCDContRelations mcdContRelations,
                                                MCDEntity mcdEntityFrom, MCDEntity mcdEntityTo) {

        MCDAssociation mcdAssociation = new MCDAssociation(mcdContRelations) ;

        MCDContRelEnds mcdContRelEndsEntityFrom = mcdEntityFrom.getMCDContRelEnds();
        MCDAssEnd mcdAssEndFrom = new MCDAssEnd(mcdContRelEndsEntityFrom) ;

        MCDContRelEnds mcdContRelEndsEntityTo = mcdEntityTo.getMCDContRelEnds();
        MCDAssEnd mcdAssEndTo = new MCDAssEnd(mcdContRelEndsEntityTo) ;

        mcdAssociation.setFrom(mcdAssEndFrom);
        mcdAssociation.setTo(mcdAssEndTo);

        mcdAssEndFrom.setMcdEntity(mcdEntityFrom);
        mcdAssEndFrom.setMcdAssociation(mcdAssociation);

        mcdAssEndTo.setMcdEntity(mcdEntityTo);
        mcdAssEndTo.setMcdAssociation(mcdAssociation);

        MCDContRelEnds mcdContRelEnds = MVCCDElementFactory.instance().createMCDContEndRels(mcdAssociation,
                Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME);

        return mcdAssociation;
    }


    public MCDGeneralization createMCDGeneralization( MCDContRelations mcdContRelations,
                                                      MCDEntity mcdEntityGen,
                                                      MCDEntity mcdEntitySpec) {

        MCDGeneralization mcdGeneralization = new MCDGeneralization(mcdContRelations) ;


        MCDContRelEnds mcdContRelEndsEntityGen = mcdEntityGen.getMCDContRelEnds();
        MCDGSEnd mcdGSEndGen = new MCDGSEnd(mcdContRelEndsEntityGen) ;

        MCDContRelEnds mcdContRelEndsEntitySpec = mcdEntitySpec.getMCDContRelEnds();
        MCDGSEnd mcdGSEndSpec = new MCDGSEnd(mcdContRelEndsEntitySpec) ;


        mcdGeneralization.setGen(mcdGSEndGen);
        mcdGeneralization.setSpec(mcdGSEndSpec);

        mcdGSEndGen.setMcdEntity(mcdEntityGen);
        mcdGSEndGen.setMcdGeneralization(mcdGeneralization);

        mcdGSEndSpec.setMcdEntity(mcdEntitySpec);
        mcdGSEndSpec.setMcdGeneralization(mcdGeneralization);

        return mcdGeneralization;
    }



    public MCDLink createMCDLink( MCDContRelations mcdContRelations,
                                  MCDEntity mcdEntity,
                                  MCDAssociation mcdAssociation) {

        MCDLink mcdLink = new MCDLink(mcdContRelations) ;

        MCDContRelEnds mcdContRelEndsEntity = mcdEntity.getMCDContRelEnds();
        MCDLinkEnd mcdLinkEndEntity = new MCDLinkEnd(mcdContRelEndsEntity) ;

        MCDContRelEnds mcdContRelEndsAssociation = mcdAssociation.getMCDContRelEnds();
        MCDLinkEnd mcdLinkEndAssociation = new MCDLinkEnd(mcdContRelEndsAssociation) ;

        mcdLink.setEndEntity(mcdLinkEndEntity);
        mcdLink.setEndAssociation(mcdLinkEndAssociation);

        mcdLinkEndEntity.setmElement(mcdEntity);
        mcdLinkEndEntity.setMcdLink(mcdLink);

        mcdLinkEndAssociation.setmElement(mcdAssociation);
        mcdLinkEndAssociation.setMcdLink(mcdLink);

        return mcdLink;
    }


    private void createContentMCDModel(MCDElement parent) {
        createContentPackage(parent);
    }

    private void createContentPackage(MCDElement parent) {
        MCDContDiagrams mcdContDiagrams = MVCCDElementFactory.instance().createMCDDiagrams(parent,Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
        MCDContEntities mcdContEntities = MVCCDElementFactory.instance().createMCDEntities(parent,Preferences.REPOSITORY_MCD_ENTITIES_NAME);
        MCDContRelations mcdContRelations = MVCCDElementFactory.instance().createMCDContRelations(parent,Preferences.REPOSITORY_MCD_RELATIONS_NAME);
    }


    public MLDRModelDT createMLDRModelDT(IMCDModel imcdModel, int id){
        MLDRModelDT mldrModelDT = new MLDRModelDT((ProjectElement) imcdModel, id);
        mldrModelDT.setName(Preferences.REPOSITORY_MLDR_MODEL_DT_NAME);
        namingMLDRModel(mldrModelDT);
        createContentMLDRModel(mldrModelDT);
        return mldrModelDT;
    }

    public MLDRModelDT createMLDRModelDT(IMCDModel imcdModel){
        MLDRModelDT mldrModelDT = new MLDRModelDT((ProjectElement) imcdModel, Preferences.REPOSITORY_MLDR_MODEL_DT_NAME);
        namingMLDRModel(mldrModelDT);
        createContentMLDRModel(mldrModelDT);
        return mldrModelDT;
    }

    public MLDRModelTI createMLDRModelTI(IMCDModel imcdModel, int id){
        MLDRModelTI mldrModelTI = new MLDRModelTI((ProjectElement) imcdModel, id);
        mldrModelTI.setName(Preferences.REPOSITORY_MLDR_MODEL_TI_NAME);
        namingMLDRModel(mldrModelTI);
        createContentMLDRModel(mldrModelTI);
        return mldrModelTI;
    }

    public MLDRModelTI createMLDRModelTI(IMCDModel imcdModel){
        MLDRModelTI mldrModelTI = new MLDRModelTI((ProjectElement) imcdModel, Preferences.REPOSITORY_MLDR_MODEL_TI_NAME);
        namingMLDRModel(mldrModelTI);
        createContentMLDRModel(mldrModelTI);
        return mldrModelTI;
    }

    private void namingMLDRModel(MLDRModel mldrModel){
        Preferences preferences = PreferencesManager.instance().preferences();
        mldrModel.setNamingLengthActual( preferences.getMLDR_PREF_NAMING_LENGTH());
        mldrModel.setNamingLengthFuture( preferences.getMLDR_PREF_NAMING_LENGTH());
        mldrModel.setNamingFormatActual( preferences.getMLDR_PREF_NAMING_FORMAT());
        mldrModel.setNamingFormatFuture( preferences.getMLDR_PREF_NAMING_FORMAT());

    }

    private void createContentMLDRModel(MLDRModel mldrModel) {
        MLDRContTables mldrContTable = new MLDRContTables(mldrModel, Preferences.REPOSITORY_MDR_TABLES_NAME);
        MLDRContRelations mldrContRelations = new MLDRContRelations(mldrModel, Preferences.REPOSITORY_MDR_RELATIONS_NAME);
    }

    public MLDRContTables createMLDRContTables(MLDRModel mldrModel, int id) {
        return new MLDRContTables(mldrModel, id);
    }

    public MLDRTable createMLDRTable(MDRContTables mdrContTables, MCDEntity entitySource, int id) {
        MLDRTable mldrTable = new MLDRTable(mdrContTables, entitySource, id);
        MLDRContColumns mldrContColumns = new MLDRContColumns(mldrTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        MLDRContConstraints mldrContConstraints = new MLDRContConstraints(mldrTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        MLDRContRelEnds mldrContRelEnds = new MLDRContRelEnds(mldrTable, Preferences.REPOSITORY_MDR_RELENDS_NAME);

        return mldrTable;
    }


    public MLDRTable createMLDRTable(MDRContTables mdrContTables, MCDEntity entitySource) {
        MLDRTable mldrTable = new MLDRTable(mdrContTables, entitySource);
        MLDRContColumns mldrContColumns = new MLDRContColumns(mldrTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        MLDRContConstraints mldrContConstraints = new MLDRContConstraints(mldrTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        MLDRContRelEnds mldrContRelEnds = new MLDRContRelEnds(mldrTable, Preferences.REPOSITORY_MDR_RELENDS_NAME);

        return mldrTable;
    }


    public MLDRColumn createMLDRColumn(MDRContColumns mdrContColumns, MCDElement mcdElementSource) {
        MLDRColumn mldrColumn= new MLDRColumn(mdrContColumns, mcdElementSource);
        return mldrColumn;
    }

    public MLDRColumn createMLDRColumnFK(MDRContColumns mdrContColumns, MCDElement mcdElementSource, MLDRColumn mldrColumnPK) {
        MLDRColumn mldrColumn= new MLDRColumn(mdrContColumns, mcdElementSource, mldrColumnPK);
        return mldrColumn;
    }


    public MLDRPK createMLDRPK(MDRContConstraints mdrContConstraints, MCDElement mcdElementSource) {
        MLDRPK mldrPK= new MLDRPK(mdrContConstraints, mcdElementSource);
        return mldrPK;
    }

    public MLDRParameter createMLDRParameter(MDRConstraint mdrConstraint, IMDRParameter target, MCDElement mcdElementSource) {
        MLDRParameter mldrParameter = new MLDRParameter(mdrConstraint, target, mcdElementSource);
        return mldrParameter;
    }



    public MLDRFK createMLDRFK(MLDRContConstraints mldrContConstraints, MCDElement mcdElementSource) {
        MLDRFK mldrFK= new MLDRFK(mldrContConstraints, mcdElementSource);
        Integer indice = MLDRContConstraintsService.nextIndice(mldrContConstraints, mldrFK);
        mldrFK.setIndice(indice);
        return mldrFK;
    }



    public MLDRRelationFK createMLDRRelationFK( MLDRContRelations mldrContRelations,
                                                MCDRelation mcdRelation,
                                                MLDRTable mldrTableParent,
                                                MLDRTable mldrTableChild) {

        MLDRRelationFK mldrRelationFK = new MLDRRelationFK(mldrContRelations, mcdRelation) ;


        MLDRContRelEnds mldrContEndRelsParent = (MLDRContRelEnds) mldrTableParent.getMDRContRelEnds();
        MLDRRelFKEnd mldrRelFKEndParent = new MLDRRelFKEnd(mldrContEndRelsParent) ;

        MLDRContRelEnds mldrContEndRelsChild = (MLDRContRelEnds) mldrTableChild.getMDRContRelEnds();
        MLDRRelFKEnd mldrRelFKEndChild = new MLDRRelFKEnd(mldrContEndRelsChild) ;

        mldrRelationFK.setEndParent(mldrRelFKEndParent);
        mldrRelationFK.setEndChild(mldrRelFKEndChild);

        mldrRelFKEndParent.setMDRTable(mldrTableParent);
        mldrRelFKEndParent.setMDRRelationFK(mldrRelationFK);

        mldrRelFKEndChild.setMDRTable(mldrTableChild);
        mldrRelFKEndChild.setMDRRelationFK(mldrRelationFK);

        return mldrRelationFK;
    }

    // Oracle
    public MPDROracleModel createMPDRModelOracle(MLDRModel mldrModel) {
        MPDROracleModel mpdrOracleModel = new MPDROracleModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_ORACLE_NAME);
        Preferences preferences = PreferencesManager.instance().preferences();
        mpdrOracleModel.setNamingLengthActual( preferences.getMPDRORACLE_PREF_NAMING_LENGTH());
        mpdrOracleModel.setNamingLengthFuture( preferences.getMPDRORACLE_PREF_NAMING_LENGTH());
        mpdrOracleModel.setNamingFormatActual( preferences.getMPDRORACLE_PREF_NAMING_FORMAT());
        mpdrOracleModel.setNamingFormatFuture( preferences.getMPDRORACLE_PREF_NAMING_FORMAT());
        createContentMPDRModel(mpdrOracleModel);
        return mpdrOracleModel;
    }

    public MPDROracleTable createMPDROracleTable(MPDRContTables mpdrContTables, MLDRTable mldrTable) {
        MPDROracleTable mpdrOracleTable = new MPDROracleTable(mpdrContTables, mldrTable);
        MPDRContColumns mpdrContColumns = new MPDRContColumns(mpdrOracleTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        MPDRContConstraints mpdrConstraints = new MPDRContConstraints(mpdrOracleTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        return mpdrOracleTable;
    }


    public MPDROracleColumn createMPDROracleColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn) {
        MPDROracleColumn mpdrOracleColumn = new MPDROracleColumn(mdrContColumns, mldrColumn);
        return mpdrOracleColumn;
    }

    public MPDROraclePK createMPDROraclePK(MDRContConstraints mdrContConstraints, MLDRPK mldrPK) {
        MPDROraclePK mpdrOraclePK = new MPDROraclePK(mdrContConstraints, mldrPK);
        return mpdrOraclePK;
    }


    public MPDROracleFK createMPDROracleFK(MDRContConstraints mdrContConstraints, MLDRFK mldrFK) {
        MPDROracleFK mpdrOracleFK = new MPDROracleFK(mdrContConstraints, mldrFK);
        return mpdrOracleFK;
    }

    public MPDRParameter createMPDROracleParameter(IMPDROracleElement impdrOracleElement,
                                                   MLDRParameter mldrParameter) {
        MPDROracleParameter mpdrOracleParameter = new MPDROracleParameter( impdrOracleElement, mldrParameter);
        return mpdrOracleParameter;
    }


    // MySQL
    public MPDRMySQLModel createMPDRModelMySQL(MLDRModel mldrModel) {
        MPDRMySQLModel mpdrMySQLModel = new MPDRMySQLModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_MYSQL_NAME);
        Preferences preferences = PreferencesManager.instance().preferences();
        mpdrMySQLModel.setNamingLengthActual( preferences.getMPDRMYSQL_PREF_NAMING_LENGTH());
        mpdrMySQLModel.setNamingLengthFuture( preferences.getMPDRMYSQL_PREF_NAMING_LENGTH());
        mpdrMySQLModel.setNamingFormatActual( preferences.getMPDRMYSQL_PREF_NAMING_FORMAT());
        mpdrMySQLModel.setNamingFormatFuture( preferences.getMPDRMYSQL_PREF_NAMING_FORMAT());
        createContentMPDRModel(mpdrMySQLModel);
        return mpdrMySQLModel;
    }

    public MPDRMySQLTable createMPDRMySQLTable(MPDRContTables mpdrContTables, MLDRTable mldrTable) {
        MPDRMySQLTable mpdrMySQLTable = new MPDRMySQLTable(mpdrContTables, mldrTable);
        MPDRContColumns mpdrContColumns = new MPDRContColumns(mpdrMySQLTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        MPDRContConstraints mpdrConstraints = new MPDRContConstraints(mpdrMySQLTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        return mpdrMySQLTable;
    }


    public MPDRMySQLColumn createMPDRMySQLColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn) {
        MPDRMySQLColumn mpdrMySQLColumn = new MPDRMySQLColumn(mdrContColumns, mldrColumn);
        return mpdrMySQLColumn;
    }



    // PostgreSQL
    public MPDRPostgreSQLModel createMPDRModelPostgreSQL(MLDRModel mldrModel) {
        MPDRPostgreSQLModel mpdrPostgreSQLModel = new MPDRPostgreSQLModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_POSTGRESQL_NAME);
        Preferences preferences = PreferencesManager.instance().preferences();
        mpdrPostgreSQLModel.setNamingLengthActual( preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH());
        mpdrPostgreSQLModel.setNamingLengthFuture( preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH());
        mpdrPostgreSQLModel.setNamingFormatActual( preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT());
        mpdrPostgreSQLModel.setNamingFormatFuture( preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT());
        createContentMPDRModel(mpdrPostgreSQLModel);
        return mpdrPostgreSQLModel;
    }

    public MPDRPostgreSQLTable createMPDRPostgreSQLTable(MPDRContTables mpdrContTables, MLDRTable mldrTable) {
        MPDRPostgreSQLTable mpdrPostgreSQLTable = new MPDRPostgreSQLTable(mpdrContTables, mldrTable);
        MPDRContColumns mpdrContColumns = new MPDRContColumns(mpdrPostgreSQLTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        MPDRContConstraints mpdrConstraints = new MPDRContConstraints(mpdrPostgreSQLTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        return mpdrPostgreSQLTable;
    }


    public MPDRPostgreSQLColumn createMPDRPostgreSQLColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn) {
        MPDRPostgreSQLColumn mpdrPostgreSQLColumn = new MPDRPostgreSQLColumn(mdrContColumns, mldrColumn);
        return mpdrPostgreSQLColumn;
    }


    //Tous les MPDR
    private void createContentMPDRModel(MPDRModel mpdrModel) {
        MPDRContTables mpdrContTable = new MPDRContTables(mpdrModel, Preferences.REPOSITORY_MDR_TABLES_NAME);
    }





    public ProjectElement createMVCCDElementFromXML(String baliseName, ProjectElement ancestor){
        if (baliseName.equals(Preferences.XML_BALISE_PROJECT)) {
            return new Project(null);
        } else if (baliseName.equals(Preferences.XML_BALISE_MODELS)) {
            return new MCDContModels(ancestor);
        } else if (baliseName.equals(Preferences.XML_BALISE_DIAGRAMS)) {
            return new MCDContDiagrams(ancestor);
        } else if (baliseName.equals(Preferences.XML_BALISE_ENTITIES)) {
            return new MCDContEntities(ancestor);
        } else if (baliseName.equals(Preferences.XML_BALISE_ENTITY)) {
            return new MCDEntity(ancestor);
        } else {
            String message = MessagesBuilder.getMessagesProperty("load.xml.balise.unknow", new String[] {
                    baliseName, ancestor.getName(), ancestor.getClass().getName()});
            MVCCDManager.instance().getConsole().printMessage(message);
            return null;
        }
    }

}
