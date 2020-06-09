package main;

import diagram.mcd.MCDDiagram;
import mcd.*;
import mcd.MCDContAttributes;
import messages.MessagesBuilder;
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
                createContentModel(mcdContModels);
        }
        return project;
    }


    public MCDContModels createMCDModels(Project project, String name){
        return new MCDContModels(project, name);
    }

    public MCDModel createMCDModel(MCDContModels mcdContModels){
        MCDModel mcdModel = new MCDModel(mcdContModels);
        createContentModel(mcdModel);
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
        MCDContEndRels mcdContEndRels = MVCCDElementFactory.instance().createMCDContEndRels(mcdEntity,
                Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME);
        MCDContConstraints mcdContConstraints = MVCCDElementFactory.instance().createMCDConstraints(mcdEntity,
                Preferences.REPOSITORY_MCD_CONSTRAINTS_NAME);

        return mcdEntity;
    }

    public MCDContAttributes createMCDAttributes(MCDEntity parent, String name){
        return new MCDContAttributes(parent, name);
    }

    public MCDContEndRels createMCDContEndRels(MCDElement parent, String name){
        return new MCDContEndRels(parent, name);
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

        MCDContEndRels mcdContEndRelsEntityFrom = mcdEntityFrom.getMCDContEndRels();
        MCDAssEnd mcdAssEndFrom = new MCDAssEnd(mcdContEndRelsEntityFrom) ;

        MCDContEndRels mcdContEndRelsEntityTo = mcdEntityTo.getMCDContEndRels();
        MCDAssEnd mcdAssEndTo = new MCDAssEnd(mcdContEndRelsEntityTo) ;

        mcdAssociation.setFrom(mcdAssEndFrom);
        mcdAssociation.setTo(mcdAssEndTo);

        mcdAssEndFrom.setMcdEntity(mcdEntityFrom);
        mcdAssEndFrom.setMcdAssociation(mcdAssociation);

        mcdAssEndTo.setMcdEntity(mcdEntityTo);
        mcdAssEndTo.setMcdAssociation(mcdAssociation);

        MCDContEndRels mcdContEndRels = MVCCDElementFactory.instance().createMCDContEndRels(mcdAssociation,
                Preferences.REPOSITORY_MCD_RELATIONS_ENDS_NAME);

        return mcdAssociation;
    }


    public MCDGeneralization createMCDGeneralization( MCDContRelations mcdContRelations,
                                                      MCDEntity mcdEntityGen,
                                                      MCDEntity mcdEntitySpec) {

        MCDGeneralization mcdGeneralization = new MCDGeneralization(mcdContRelations) ;


        MCDContEndRels mcdContEndRelsEntityGen = mcdEntityGen.getMCDContEndRels();
        MCDGSEnd mcdGSEndGen = new MCDGSEnd(mcdContEndRelsEntityGen) ;

        MCDContEndRels mcdContEndRelsEntitySpec = mcdEntitySpec.getMCDContEndRels();
        MCDGSEnd mcdGSEndSpec = new MCDGSEnd(mcdContEndRelsEntitySpec) ;


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

        MCDContEndRels mcdContEndRelsEntity = mcdEntity.getMCDContEndRels();
        MCDLinkEnd mcdLinkEndEntity = new MCDLinkEnd(mcdContEndRelsEntity) ;

        MCDContEndRels mcdContEndRelsAssociation= mcdAssociation.getMCDContEndRels();
        MCDLinkEnd mcdLinkEndAssociation = new MCDLinkEnd(mcdContEndRelsAssociation) ;

        mcdLink.setEndEntity(mcdLinkEndEntity);
        mcdLink.setEndAssociation(mcdLinkEndAssociation);

        mcdLinkEndEntity.setMcdElement(mcdEntity);
        mcdLinkEndEntity.setMcdLink(mcdLink);

        mcdLinkEndAssociation.setMcdElement(mcdAssociation);
        mcdLinkEndAssociation.setMcdLink(mcdLink);

        return mcdLink;
    }


    private void createContentModel(MCDElement parent) {
        createContentPackage(parent);
    }

    private void createContentPackage(MCDElement parent) {
        MCDContDiagrams mcdContDiagrams = MVCCDElementFactory.instance().createMCDDiagrams(parent,Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
        MCDContEntities mcdContEntities = MVCCDElementFactory.instance().createMCDEntities(parent,Preferences.REPOSITORY_MCD_ENTITIES_NAME);
        MCDContRelations mcdContRelations = MVCCDElementFactory.instance().createMCDContRelations(parent,Preferences.REPOSITORY_MCD_RELATIONS_NAME);
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