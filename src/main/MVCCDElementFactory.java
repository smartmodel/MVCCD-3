package main;

import connections.ConConnectionOracle;
import connections.ConConnectorOracle;
import connections.ConnectionsOracle;
import console.ViewLogsManager;
import console.WarningLevel;
import diagram.mcd.MCDDiagram;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDSourceMLDRRelationFK;
import mcd.interfaces.IMCDSourceMLDRTable;
import mcd.services.IMCDModelService;
import mdr.*;
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

    public ConConnectionOracle createConConnectionOracle(ConnectionsOracle parent) {
        return new ConConnectionOracle(parent);
    }

    public ConConnectorOracle createConConnectorOracle(ConConnectionOracle parent) {
        return new ConConnectorOracle(parent);
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

    public MCDUnique createMCDUnique(MCDContConstraints parent, int id){
        return new MCDUnique(parent, id);
    }

    public MCDUnique createMCDUnique(MCDContConstraints parent){
        return new MCDUnique(parent);
    }

    public MCDNID createMCDNID(MCDContConstraints parent, int id){
        return new MCDNID(parent, id);
    }

    public MCDNID createMCDNID(MCDContConstraints parent){
        return new MCDNID(parent);
    }

    public MCDParameter createMCDParameter(ProjectElement parent) {
        return new MCDParameter(parent);
    }

    public MCDAssociation createMCDAssociation( MCDContRelations mcdContRelations,
                                                MCDEntity mcdEntityFrom, MCDEntity mcdEntityTo, int idAssociation, int idAssEndFrom, int idAssEndTo) {
        MCDAssociation mcdAssociation = new MCDAssociation(mcdContRelations, idAssociation);
        MCDAssEnd mcdAssEndFrom = new MCDAssEnd(mcdEntityFrom.getMCDContRelEnds(), idAssEndFrom);
        MCDAssEnd mcdAssEndTo = new MCDAssEnd(mcdEntityTo.getMCDContRelEnds(), idAssEndTo);
        this.initMCDAssociation(mcdAssociation, mcdEntityFrom, mcdEntityTo, mcdAssEndFrom, mcdAssEndTo);
        return mcdAssociation;
}

    public MCDAssociation createMCDAssociation( MCDContRelations mcdContRelations,
                                                MCDEntity mcdEntityFrom, MCDEntity mcdEntityTo) {
        MCDAssociation mcdAssociation = new MCDAssociation(mcdContRelations) ;
        MCDAssEnd mcdAssEndFrom = new MCDAssEnd(mcdEntityFrom.getMCDContRelEnds()) ;
        MCDAssEnd mcdAssEndTo = new MCDAssEnd(mcdEntityTo.getMCDContRelEnds()) ;
        this.initMCDAssociation(mcdAssociation, mcdEntityFrom, mcdEntityTo, mcdAssEndFrom, mcdAssEndTo);
        return mcdAssociation;
    }

    private void initMCDAssociation(MCDAssociation mcdAssociation, MCDEntity mcdEntityFrom, MCDEntity mcdEntityTo, MCDAssEnd mcdAssEndFrom, MCDAssEnd mcdAssEndTo){
        mcdAssociation.setFrom(mcdAssEndFrom);
        mcdAssociation.setTo(mcdAssEndTo);

        mcdAssEndFrom.setMcdEntity(mcdEntityFrom);
        mcdAssEndFrom.setMcdAssociation(mcdAssociation);

        mcdAssEndTo.setMcdEntity(mcdEntityTo);
        mcdAssEndTo.setMcdAssociation(mcdAssociation);

        MCDContRelEnds mcdContRelEnds = MVCCDElementFactory.instance().createMCDContEndRels(mcdAssociation,
                Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME);
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

    public MCDLink createMCDLink( MCDContRelations mcdContRelations, MCDEntity mcdEntity, MCDAssociation mcdAssociation) {
        MCDLink mcdLink = new MCDLink(mcdContRelations);
        this.initMCDLink(mcdLink, mcdEntity, mcdAssociation);
        this.changeSourceForTable(mcdLink);
        return mcdLink;
    }


    public MCDLink createMCDLink( MCDContRelations mcdContRelations, MCDEntity mcdEntity, MCDAssociation mcdAssociation, int id) {
        MCDLink mcdLink = new MCDLink(mcdContRelations, id);
        this.initMCDLink(mcdLink, mcdEntity, mcdAssociation);
        return mcdLink;
    }

    private void initMCDLink(MCDLink mcdLink, MCDEntity mcdEntity, MCDAssociation mcdAssociation){
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
    }

    // Si nécessaire, changment de la source de la table : Association n:n --> Entité associative
    private void changeSourceForTable(MCDLink mcdLink) {
        MCDAssociation mcdAssociation = mcdLink.getAssociation();
        if (mcdAssociation.isDegreeNN()){
            IMCDModel mcdModelAccueil = mcdLink.getIMCDModelAccueil();
            for (MLDRModel mldrModel : IMCDModelService.getMLDRModels(mcdModelAccueil)){
                MLDRTable mldrTable = mldrModel.getMLDRTableByAssNNSource(mcdAssociation);
                if (mldrTable != null){
                    mldrTable.setMcdElementSource(mcdLink.getEntity());
                    String message = MessagesBuilder.getMessagesProperty("editor.link.change.source.to.entity.ass",
                            new String[] {mldrTable.getNameTreePath(), mcdLink.getEntity().getNameTreePath()} );
                    ViewLogsManager.printMessage(message, WarningLevel.INFO);
                }
            }
        }

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
        createMLDRContModel(mldrModelDT);
        return mldrModelDT;
    }

    public MLDRModelDT createMLDRModelDT(IMCDModel imcdModel){
        MLDRModelDT mldrModelDT = new MLDRModelDT((ProjectElement) imcdModel, Preferences.REPOSITORY_MLDR_MODEL_DT_NAME);
        namingMLDRModel(mldrModelDT);
        createMLDRContModel(mldrModelDT);
        return mldrModelDT;
    }

    public MLDRModelTI createMLDRModelTI(IMCDModel imcdModel, int id){
        MLDRModelTI mldrModelTI = new MLDRModelTI((ProjectElement) imcdModel, id);
        mldrModelTI.setName(Preferences.REPOSITORY_MLDR_MODEL_TI_NAME);
        namingMLDRModel(mldrModelTI);
        createMLDRContModel(mldrModelTI);
        return mldrModelTI;
    }

    public MLDRModelTI createMLDRModelTI(IMCDModel imcdModel){
        MLDRModelTI mldrModelTI = new MLDRModelTI((ProjectElement) imcdModel, Preferences.REPOSITORY_MLDR_MODEL_TI_NAME);
        namingMLDRModel(mldrModelTI);
        createMLDRContModel(mldrModelTI);
        return mldrModelTI;
    }

    private void namingMLDRModel(MLDRModel mldrModel){
        Preferences preferences = PreferencesManager.instance().preferences();
        mldrModel.setNamingLengthActual( preferences.getMLDR_PREF_NAMING_LENGTH());
        mldrModel.setNamingLengthFuture( preferences.getMLDR_PREF_NAMING_LENGTH());
        mldrModel.setNamingFormatActual( preferences.getMLDR_PREF_NAMING_FORMAT());
        mldrModel.setNamingFormatFuture( preferences.getMLDR_PREF_NAMING_FORMAT());

    }

    private void createMLDRContModel(MLDRModel mldrModel) {
        new MLDRContTables(mldrModel, Preferences.REPOSITORY_MDR_TABLES_NAME);
        new MLDRContRelations(mldrModel, Preferences.REPOSITORY_MDR_RELATIONS_NAME);
    }

    public MLDRContTables createMLDRContTables(MLDRModel mldrModel, int id) {
        return new MLDRContTables(mldrModel, id);
    }

    public MLDRTable createMLDRTable(MDRContTables mdrContTables, IMCDSourceMLDRTable imcdSourceMLDRTable, int id) {
        MLDRTable mldrTable = new MLDRTable(mdrContTables, (MCDElement) imcdSourceMLDRTable, id);
        this.initMLDRTable(mldrTable);
        return mldrTable;
    }


    public MLDRTable createMLDRTable(MDRContTables mdrContTables, IMCDSourceMLDRTable imcdSourceMLDRTable) {
        MLDRTable mldrTable = new MLDRTable(mdrContTables, (MCDElement) imcdSourceMLDRTable);
        this.initMLDRTable(mldrTable);
        return mldrTable;
    }

    private void initMLDRTable(MLDRTable mldrTable){
        new MLDRContColumns(mldrTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        new MLDRContConstraints(mldrTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        new MLDRContRelEnds(mldrTable, Preferences.REPOSITORY_MDR_RELENDS_NAME);
    }

    public MLDRColumn createMLDRColumn(MDRContColumns mdrContColumns, MCDElement mcdElementSource, int id) {
        return new MLDRColumn(mdrContColumns, mcdElementSource, id);
    }

    public MLDRColumn createMLDRColumn(MDRContColumns mdrContColumns, MCDElement mcdElementSource) {
        return new MLDRColumn(mdrContColumns, mcdElementSource);
    }

    public MLDRColumn createMLDRColumnFK(MDRContColumns mdrContColumns, MCDElement mcdElementSource, MLDRColumn mldrColumnPK) {
        return new MLDRColumn(mdrContColumns, mcdElementSource, mldrColumnPK);
    }

    public MLDRPK createMLDRPK(MDRContConstraints mdrContConstraints, MCDElement mcdElementSource) {
        return new MLDRPK(mdrContConstraints, mcdElementSource);
    }

    public MLDRPK createMLDRPK(MDRContConstraints mdrContConstraints, MCDElement mcdElementSource, int id) {
        return new MLDRPK(mdrContConstraints, mcdElementSource, id);
    }

    public MLDRParameter createMLDRParameter(MDRConstraint mdrConstraint, IMDRParameter target, MCDElement mcdElementSource) {
        return new MLDRParameter(mdrConstraint, target, mcdElementSource);
    }

    public MLDRParameter createMLDRParameter(MDRConstraint mdrConstraint, IMDRParameter target, MCDElement mcdElementSource, int id) {
        return new MLDRParameter(mdrConstraint, target, mcdElementSource, id);
    }

    public MLDRFK createMLDRFK(MLDRContConstraints mldrContConstraints, MCDElement mcdElementSource) {
        MLDRFK mldrFK = new MLDRFK(mldrContConstraints, mcdElementSource);
        this.initMLDRFK(mldrFK, mldrContConstraints);
        return mldrFK;
    }

    public MLDRFK createMLDRFK(MDRContConstraints mdrContConstraints, MCDElement mcdElementSource, int id) {
        MLDRFK mldrFK = new MLDRFK(mdrContConstraints, mcdElementSource, id);
        this.initMLDRFK(mldrFK, mdrContConstraints);
        return mldrFK;
    }

    public void initMLDRFK(MLDRFK mldrFK, MDRContConstraints mdrContConstraints){
        Integer indice = MLDRContConstraintsService.nextIndice(mdrContConstraints, mldrFK);
        mldrFK.setIndice(indice);
    }


    public MLDRRelationFK createMLDRRelationFK( MLDRContRelations mldrContRelations,
                                                IMCDSourceMLDRRelationFK imcdSourceMLDRRelationFK,
                                                MLDRTable mldrTableA,
                                                MLDRTable mldrTableB) {

        MLDRRelationFK mldrRelationFK = new MLDRRelationFK(mldrContRelations, imcdSourceMLDRRelationFK) ;


        MLDRContRelEnds mldrContEndRelsA = (MLDRContRelEnds) mldrTableA.getMDRContRelEnds();
        MLDRRelFKEnd mldrRelFKEndA = new MLDRRelFKEnd(mldrContEndRelsA) ;

        MLDRContRelEnds mldrContEndRelsB = (MLDRContRelEnds) mldrTableB.getMDRContRelEnds();
        MLDRRelFKEnd mldrRelFKEndB = new MLDRRelFKEnd(mldrContEndRelsB) ;

        /*
        mldrRelationFK.setEndParent(mldrRelFKEndParent);
        mldrRelationFK.setEndChild(mldrRelFKEndChild);
         */
        mldrRelationFK.setA(mldrRelFKEndA);
        mldrRelationFK.setB(mldrRelFKEndB);


        mldrRelFKEndA.setMDRTable(mldrTableA);
        mldrRelFKEndA.setMDRRelationFK(mldrRelationFK);

        mldrRelFKEndB.setMDRTable(mldrTableB);
        mldrRelFKEndB.setMDRRelationFK(mldrRelationFK);

        return mldrRelationFK;
    }

    public MLDRRelFKEnd createMLDRRelFKEnd(MLDRContRelEnds mldrContRelEnds, MDRTable mdrTableParent, int id) {
        MLDRRelFKEnd mldrRelFKEnd = new MLDRRelFKEnd(mldrContRelEnds, id);
        mldrRelFKEnd.setMDRTable(mdrTableParent);
        return mldrRelFKEnd;
    }

    public MLDRRelationFK createMLDRRelationFK( MLDRContRelations mldrContRelations,
                                                MCDRelation mcdRelation,
                                                MLDRRelFKEnd mldrRelFKA,
                                                MLDRRelFKEnd mldrRelFKEndA,
                                                int id) {

        MLDRRelationFK mldrRelationFK = new MLDRRelationFK(mldrContRelations, mcdRelation, id) ;

        //Affectation de la relation avec chacune des extrémités de la relation et inversement
        /*
        mldrRelationFK.setEndParent(mldrRelFKEndParent);
        mldrRelationFK.setEndChild(mldrRelFKEndChild);

         */
        mldrRelationFK.setA(mldrRelFKA);
        mldrRelationFK.setB(mldrRelFKEndA);

        mldrRelFKA.setMDRRelationFK(mldrRelationFK);
        mldrRelFKEndA.setMDRRelationFK(mldrRelationFK);

        return mldrRelationFK;
    }


    public MLDRUnique createMLDRUnique(MLDRContConstraints mldrContConstraints, MCDElement mcdElementSource) {
        MLDRUnique mldrUnique = new MLDRUnique(mldrContConstraints, mcdElementSource);
        return mldrUnique;
    }


    // Oracle
    public MPDROracleModel createMPDRModelOracle(MLDRModel mldrModel) {
        MPDROracleModel mpdrOracleModel = new MPDROracleModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_ORACLE_NAME);
        Preferences preferences = PreferencesManager.instance().preferences();
        mpdrOracleModel.setNamingLengthActual( preferences.getMPDRORACLE_PREF_NAMING_LENGTH());
        mpdrOracleModel.setNamingLengthFuture( preferences.getMPDRORACLE_PREF_NAMING_LENGTH());
        mpdrOracleModel.setNamingFormatActual( preferences.getMPDRORACLE_PREF_NAMING_FORMAT());
        mpdrOracleModel.setNamingFormatFuture( preferences.getMPDRORACLE_PREF_NAMING_FORMAT());
        // Conteneur générique
        createMPDRContModel(mpdrOracleModel);
        return mpdrOracleModel;
    }

    public MPDROracleTable createMPDROracleTable(MDRContTables mdrContTables, MLDRTable mldrTable, int id) {
        MPDROracleTable mpdrOracleTable = new MPDROracleTable(mdrContTables, mldrTable, id);
        this.initMPDRTable(mpdrOracleTable);
        return mpdrOracleTable;
    }

    public MPDROracleTable createMPDROracleTable(MDRContTables mdrContTables, MLDRTable mldrTable) {
        MPDROracleTable mpdrOracleTable = new MPDROracleTable(mdrContTables, mldrTable);
        this.initMPDRTable(mpdrOracleTable);
        return mpdrOracleTable;
    }

    public MPDROracleColumn createMPDROracleColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn) {
        MPDROracleColumn mpdrOracleColumn = new MPDROracleColumn(mdrContColumns, mldrColumn);
        return mpdrOracleColumn;
    }

    public MPDROracleColumn createMPDROracleColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn, int id) {
        MPDROracleColumn mpdrOracleColumn = new MPDROracleColumn(mdrContColumns, mldrColumn, id);
        return mpdrOracleColumn;
    }

    public MPDROraclePK createMPDROraclePK(MDRContConstraints mdrContConstraints, MLDRPK mldrPK) {
        MPDROraclePK mpdrOraclePK = new MPDROraclePK(mdrContConstraints, mldrPK);
        return mpdrOraclePK;
    }

    public MPDROraclePK createMPDROraclePK(MDRContConstraints mdrContConstraints, MLDRPK mldrPK, int id) {
        MPDROraclePK mpdrOraclePK = new MPDROraclePK(mdrContConstraints, mldrPK, id);
        return mpdrOraclePK;
    }


    public MPDROracleFK createMPDROracleFK(MDRContConstraints mdrContConstraints, MLDRFK mldrFK) {
        MPDROracleFK mpdrOracleFK = new MPDROracleFK(mdrContConstraints, mldrFK);
        return mpdrOracleFK;
    }

    public MPDROracleFK createMPDROracleFK(MDRContConstraints mdrContConstraints, MLDRFK mldrFK, int id) {
        MPDROracleFK mpdrOracleFK = new MPDROracleFK(mdrContConstraints, mldrFK, id);
        return mpdrOracleFK;
    }

    public MPDROracleUnique createMPDROracleUnique(MDRContConstraints mdrContConstraints, MLDRUnique mldrUnique) {
        MPDROracleUnique mpdrOracleUnique = new MPDROracleUnique(mdrContConstraints, mldrUnique);
        return mpdrOracleUnique;
    }

    public MPDRParameter createMPDROracleParameter(IMPDROracleElement impdrOracleElement, MLDRParameter mldrParameter){
        return new MPDROracleParameter( impdrOracleElement, mldrParameter);
    }

    /**
     * Créer un nouveau parameter Oracle, qui est l'objet permettant de référencer une colonne. Par exemple, une
     * contrainte FK a un (ou plusieurs) parameter comme enfant, et c'est ce parameter qui va permettre de définir
     * la (ou les) colonne cible pointé par la FK.
     * @param impdrOracleElement L'élément parent pour qui le parameter est ajouté, par exemple la contrainte FK.
     * @param target Élément cible du paramètre (généralement une colonne).
     * @param mldrParameter Il s'agit de l'élément MLDR qui est la source du parameter MPDR lors de la transformation
     *                      MLDR => MPDR.
     * @param id Id déjà connu (lors du chargement du projet depuis le fichier XML) du parameter.
     * @return Retourne le parameter de niveau MPDR Oracle créé.
     */
    public MPDRParameter createMPDROracleParameter(IMPDROracleElement impdrOracleElement, IMDRParameter target, MLDRParameter mldrParameter, int id){
        return new MPDROracleParameter(impdrOracleElement, target, mldrParameter, id);
    }

    //TAPIs Oracle
    public MPDROracleSequence createMPDROracleSequence(MPDRColumn mpdrColumn, MLDRColumn mldrColumn) {
        MPDROracleSequence mpdrOracleSequence = new MPDROracleSequence(mpdrColumn, mldrColumn);
        return mpdrOracleSequence;
    }


    // MySQL
    public MPDRMySQLModel createMPDRModelMySQL(MLDRModel mldrModel) {
        MPDRMySQLModel mpdrMySQLModel = new MPDRMySQLModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_MYSQL_NAME);
        Preferences preferences = PreferencesManager.instance().preferences();
        mpdrMySQLModel.setNamingLengthActual( preferences.getMPDRMYSQL_PREF_NAMING_LENGTH());
        mpdrMySQLModel.setNamingLengthFuture( preferences.getMPDRMYSQL_PREF_NAMING_LENGTH());
        mpdrMySQLModel.setNamingFormatActual( preferences.getMPDRMYSQL_PREF_NAMING_FORMAT());
        mpdrMySQLModel.setNamingFormatFuture( preferences.getMPDRMYSQL_PREF_NAMING_FORMAT());
        createMPDRContModel(mpdrMySQLModel);
        return mpdrMySQLModel;
    }

    public MPDRMySQLTable createMPDRMySQLTable(MDRContTables mdrContTables, MLDRTable mldrTable) {
        MPDRMySQLTable mpdrMySQLTable = new MPDRMySQLTable(mdrContTables, mldrTable);
        this.initMPDRTable(mpdrMySQLTable);
        return mpdrMySQLTable;
    }

    public MPDRMySQLTable createMPDRMySQLTable(MDRContTables mdrContTables, MLDRTable mldrTable, int id) {
        MPDRMySQLTable mpdrMySQLTable = new MPDRMySQLTable(mdrContTables, mldrTable, id);
        this.initMPDRTable(mpdrMySQLTable);
        return mpdrMySQLTable;
    }


    public MPDRMySQLColumn createMPDRMySQLColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn) {
        MPDRMySQLColumn mpdrMySQLColumn = new MPDRMySQLColumn(mdrContColumns, mldrColumn);
        return mpdrMySQLColumn;
    }

    public MPDRMySQLColumn createMPDRMySQLColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn, int id) {
        MPDRMySQLColumn mpdrMySQLColumn = new MPDRMySQLColumn(mdrContColumns, mldrColumn, id);
        return mpdrMySQLColumn;
    }

    //TODO-PAS: PK pour MySQL
    public MDRPK createMPDRMySQLPK(MDRContConstraints mdrContConstraints, MLDRPK mldrPkSourceOfMpdPk, int id) {
        return null;
    }

    //TODO-PAS: FK pour MySQL
    public MDRFK createMPDRMySQLFK(MDRContConstraints mdrContConstraints, MLDRFK mldrFkSourceOfMpdFk, int id) {
        return null;
    }

    //TODO-PAS: Parameter pour MySQL
    public MPDRParameter createMPDRMySQLParameter(MDRConstraint mdrConstraint, IMDRParameter target, MLDRParameter mldrParameterSource, int parameterId) {
        return null;
    }

    // PostgreSQL
    public MPDRPostgreSQLModel createMPDRModelPostgreSQL(MLDRModel mldrModel) {
        MPDRPostgreSQLModel mpdrPostgreSQLModel = new MPDRPostgreSQLModel(mldrModel, Preferences.REPOSITORY_MPDR_MODEL_POSTGRESQL_NAME);
        Preferences preferences = PreferencesManager.instance().preferences();
        mpdrPostgreSQLModel.setNamingLengthActual( preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH());
        mpdrPostgreSQLModel.setNamingLengthFuture( preferences.getMPDRPOSTGRESQL_PREF_NAMING_LENGTH());
        mpdrPostgreSQLModel.setNamingFormatActual( preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT());
        mpdrPostgreSQLModel.setNamingFormatFuture( preferences.getMPDRPOSTGRESQL_PREF_NAMING_FORMAT());
        createMPDRContModel(mpdrPostgreSQLModel);
        return mpdrPostgreSQLModel;
    }

    public MPDRPostgreSQLTable createMPDRPostgreSQLTable(MDRContTables mdrContTables, MLDRTable mldrTable) {
        MPDRPostgreSQLTable mpdrPostgreSQLTable = new MPDRPostgreSQLTable(mdrContTables, mldrTable);
        this.initMPDRTable(mpdrPostgreSQLTable);
        return mpdrPostgreSQLTable;
    }

    public MPDRPostgreSQLTable createMPDRPostgreSQLTable(MDRContTables mdrContTables, MLDRTable mldrTable, int id) {
        MPDRPostgreSQLTable mpdrPostgreSQLTable = new MPDRPostgreSQLTable(mdrContTables, mldrTable, id);
        this.initMPDRTable(mpdrPostgreSQLTable);
        return mpdrPostgreSQLTable;
    }



    public MPDRPostgreSQLColumn createMPDRPostgreSQLColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn) {
        MPDRPostgreSQLColumn mpdrPostgreSQLColumn = new MPDRPostgreSQLColumn(mdrContColumns, mldrColumn);
        return mpdrPostgreSQLColumn;
    }

    public MPDRPostgreSQLColumn createMPDRPostgreSQLColumn(MDRContColumns mdrContColumns, MLDRColumn mldrColumn, int id) {
        MPDRPostgreSQLColumn mpdrPostgreSQLColumn = new MPDRPostgreSQLColumn(mdrContColumns, mldrColumn, id);
        return mpdrPostgreSQLColumn;
    }

    //TODO-PAS: PK pour PostgreSQL
    public MDRPK createMPDRPostgreSQLPK(MDRContConstraints mdrContConstraints, MLDRPK mldrPkSourceOfMpdPk, int id) {
        return null;
    }

    //TODO-PAS: FK pour PostgreSQL
    public MDRFK createMPDRPostgreSQLFK(MDRContConstraints mdrContConstraints, MLDRFK mldrFkSourceOfMpdFk, int id) {
        return null;
    }

    //TODO-PAS: Parameter pour PostgreSQL
    public MPDRParameter createMPDRPostgreSQLParameter(MDRConstraint mdrConstraint, IMDRParameter target, MLDRParameter mldrParameterSource, int parameterId) {
        return null;
    }

    //Tous les MPDR
    private void createMPDRContModel(MPDRModel mpdrModel) {
        new MPDRContTables(mpdrModel, Preferences.REPOSITORY_MDR_TABLES_NAME);
        new MPDRContRelations(mpdrModel, Preferences.REPOSITORY_MDR_RELATIONS_NAME);
    }


    private void initMPDRTable(MPDRTable mpdrTable){
        new MPDRContColumns(mpdrTable, Preferences.REPOSITORY_MDR_COLUMNS_NAME);
        new MPDRContConstraints(mpdrTable, Preferences.REPOSITORY_MDR_CONSTRAINTS_NAME);
        new MPDRContRelEnds(mpdrTable, Preferences.REPOSITORY_MDR_RELENDS_NAME);

        // Le conteneur est toujours créé car sinon il faut le créer/supprimer lors du changement de préférence
        //TODO-2 A faire à terme
        MPDRContTAPIs  mpdrContTAPIs = new MPDRContTAPIs(mpdrTable, Preferences.REPOSITORY_MDR_TAPIS_NAME);
    }


    public MPDRRelFKEnd createMPDRRelFKEnd(MPDRContRelEnds mpdrContRelEnds, MDRTable mdrTableParent, int id) {
        MPDRRelFKEnd mpdrRelFKEnd = new MPDRRelFKEnd(mpdrContRelEnds, id);
        mpdrRelFKEnd.setMDRTable(mdrTableParent);
        return mpdrRelFKEnd;
    }



    public MPDRRelationFK createMPDRRelationFK( MPDRContRelations mpdrContRelations,
                                                MLDRRelationFK mldrRelationFK,
                                                MPDRTable mpdrTableChild,
                                                MPDRTable mpdrTableParent) {

        MPDRRelationFK mpdrRelationFK = new MPDRRelationFK(mpdrContRelations, mldrRelationFK) ;


        MPDRContRelEnds mpdrContEndRelsChild = (MPDRContRelEnds) mpdrTableChild.getMDRContRelEnds();
        MPDRRelFKEnd mpdrRelFKEndChild = new MPDRRelFKEnd(mpdrContEndRelsChild) ;

        MPDRContRelEnds mpdrContEndRelsParent = (MPDRContRelEnds) mpdrTableParent.getMDRContRelEnds();
        MPDRRelFKEnd mpdrRelFKEndParent = new MPDRRelFKEnd(mpdrContEndRelsParent) ;

        /*
        mpdrRelationFK.setEndParent(mpdrRelFKEndParent);
        mpdrRelationFK.setEndChild(mpdrRelFKEndChild);

         */

        mpdrRelationFK.setB(mpdrRelFKEndChild);
        ((MPDRRelFKEnd)mpdrRelationFK.getB()).setRole(MDRRelFKEnd.CHILD);
        ((MPDRRelFKEnd) mpdrRelationFK.getB()).setMldrElementSource( mldrRelationFK.getEndChild());

        mpdrRelationFK.setA(mpdrRelFKEndParent);
        ((MPDRRelFKEnd)mpdrRelationFK.getA()).setRole(MDRRelFKEnd.PARENT);
        ((MPDRRelFKEnd) mpdrRelationFK.getA()).setMldrElementSource( mldrRelationFK.getEndParent());


        mpdrRelFKEndChild.setMDRTable(mpdrTableChild);
        mpdrRelFKEndChild.setMDRRelationFK(mpdrRelationFK);

        mpdrRelFKEndParent.setMDRTable(mpdrTableParent);
        mpdrRelFKEndParent.setMDRRelationFK(mpdrRelationFK);

        return mpdrRelationFK;
    }

}
