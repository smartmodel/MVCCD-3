package main;

import diagram.mcd.MCDDiagram;
import mcd.*;
import mcd.MCDContAttributes;
import mcd.interfaces.IMCDModel;
import mdr.*;
import mdr.interfaces.IMDRParameter;
import messages.MessagesBuilder;
import mldr.*;
import mldr.services.MLDRContConstraintsService;
import mpdr.MPDRModel;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.mysql.MPDRMySQLTable;
import mpdr.oracle.MPDROracleModel;
import mpdr.oracle.MPDROracleTable;
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


    public MCDContModels createMCDModels(Project project, String name){
        return new MCDContModels(project, name);
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

    public MCDContDiagrams createMCDDiagrams(ProjectElement parent, String name){
        return new MCDContDiagrams(parent, name);
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

    public MCDEntity createMCDEntity(ProjectElement parent){
        MCDEntity mcdEntity = new MCDEntity(parent);
        MCDContAttributes mcdContAttributes = MVCCDElementFactory.instance().createMCDAttributes(mcdEntity,
                Preferences.REPOSITORY_MCD_ATTRIBUTES_NAME);
        MCDContRelEnds mcdContRelEnds = MVCCDElementFactory.instance().createMCDContEndRels(mcdEntity,
                Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME);
        MCDContConstraints mcdContConstraints = MVCCDElementFactory.instance().createMCDConstraints(mcdEntity,
                Preferences.REPOSITORY_MCD_CONSTRAINTS_NAME);

        return mcdEntity;
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

        MCDContRelEnds mcdContRelEndsEntityFrom = mcdEntityFrom.getMCDContEndRels();
        MCDAssEnd mcdAssEndFrom = new MCDAssEnd(mcdContRelEndsEntityFrom) ;

        MCDContRelEnds mcdContRelEndsEntityTo = mcdEntityTo.getMCDContEndRels();
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


        MCDContRelEnds mcdContRelEndsEntityGen = mcdEntityGen.getMCDContEndRels();
        MCDGSEnd mcdGSEndGen = new MCDGSEnd(mcdContRelEndsEntityGen) ;

        MCDContRelEnds mcdContRelEndsEntitySpec = mcdEntitySpec.getMCDContEndRels();
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

        MCDContRelEnds mcdContRelEndsEntity = mcdEntity.getMCDContEndRels();
        MCDLinkEnd mcdLinkEndEntity = new MCDLinkEnd(mcdContRelEndsEntity) ;

        MCDContRelEnds mcdContRelEndsAssociation = mcdAssociation.getMCDContEndRels();
        MCDLinkEnd mcdLinkEndAssociation = new MCDLinkEnd(mcdContRelEndsAssociation) ;

        mcdLink.setEndEntity(mcdLinkEndEntity);
        mcdLink.setEndAssociation(mcdLinkEndAssociation);

        mcdLinkEndEntity.setMcdElement(mcdEntity);
        mcdLinkEndEntity.setMcdLink(mcdLink);

        mcdLinkEndAssociation.setMcdElement(mcdAssociation);
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



    public MLDRModelDT createMLDRModelDT(IMCDModel imcdModel){
        MLDRModelDT mldrModelDT = new MLDRModelDT((ProjectElement) imcdModel, Preferences.REPOSITORY_MLDR_MODEL_DT_NAME);
        createContentMLDRModel(mldrModelDT);
        return mldrModelDT;
    }

    public MLDRModelTI createMLDRModelTI(IMCDModel imcdModel){
        MLDRModelTI mldrModelTI = new MLDRModelTI((ProjectElement) imcdModel, Preferences.REPOSITORY_MLDR_MODEL_TI_NAME);
        createContentMLDRModel(mldrModelTI);
        return mldrModelTI;
    }

    private void createContentMLDRModel(MLDRModel mldrModel) {
        MDRContTables mdrContTable = new MDRContTables(mldrModel, Preferences.REPOSITORY_MDR_TABLES_NAME);
    }


    public MLDRTable createMLDRTable(MDRContTables mdrContTables, MCDEntity entitySource) {
        MLDRTable mldrTable = new MLDRTable(mdrContTables, entitySource);
        MLDRContColumns mldrContColumns = new MLDRContColumns(mldrTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        MLDRContConstraints mldrContConstraints = new MLDRContConstraints(mldrTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);

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

    public MLDRParameter createMLDRParameter(MDRConstraint mdrConstraint, IMDRParameter target) {
        MLDRParameter mldrParameter = new MLDRParameter(mdrConstraint, target);
        return mldrParameter;
    }



    public MLDRFK createMLDRFK(MLDRContConstraints mldrContConstraints, MCDElement mcdElementSource) {
        MLDRFK mldrFK= new MLDRFK(mldrContConstraints, mcdElementSource);
        Integer indice = MLDRContConstraintsService.nextIndice(mldrContConstraints, mldrFK);
        mldrFK.setIndice(indice);
        return mldrFK;
    }


    public MPDROracleModel createMPDRModelOracle(MLDRModel mldrModel) {
        MPDROracleModel mpdrOracleModel = new MPDROracleModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_ORACLE_NAME);
        createContentMPDRModel(mpdrOracleModel);
        return mpdrOracleModel;
    }

    public MPDRMySQLModel createMPDRModelMySQL(MLDRModel mldrModel) {
        MPDRMySQLModel mpdrMySQLModel = new MPDRMySQLModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_MYSQL_NAME);
        createContentMPDRModel(mpdrMySQLModel);
        return mpdrMySQLModel;
    }

    public MPDRPostgreSQLModel createMPDRModelPostgreSQL(MLDRModel mldrModel) {
        MPDRPostgreSQLModel mpdrPostgreSQLModel = new MPDRPostgreSQLModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_POSTGRESQL_NAME);
        createContentMPDRModel(mpdrPostgreSQLModel);
        return mpdrPostgreSQLModel;
    }
    

    private void createContentMPDRModel(MPDRModel mpdrModel) {
        MDRContTables mdrContTable = new MDRContTables(mpdrModel, Preferences.REPOSITORY_MDR_TABLES_NAME);
    }

    public MPDROracleTable createMPDROracleTable(MDRContTables mdrContTables, String buildNameTable, MLDRTable mldrTable) {
        MPDROracleTable mpdrOracleTable = new MPDROracleTable(mdrContTables, buildNameTable, mldrTable);
        return mpdrOracleTable;
    }

    public MPDRMySQLTable createMPDRMySQLTable(MDRContTables mdrContTables, String buildNameTable, MLDRTable mldrTable) {
        MPDRMySQLTable mpdrMySQLTable = new MPDRMySQLTable(mdrContTables, buildNameTable, mldrTable);
        return mpdrMySQLTable;
    }

    public MPDRPostgreSQLTable createMPDRPostgreSQLTable(MDRContTables mdrContTables, String buildNameTable, MLDRTable mldrTable) {
        MPDRPostgreSQLTable mpdrPostgreSQLTable = new MPDRPostgreSQLTable(mdrContTables, buildNameTable, mldrTable);
        return mpdrPostgreSQLTable;
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