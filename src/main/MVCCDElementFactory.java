package main;

import mcd.*;
import mcd.MCDContAttributes;
import messages.MessagesBuilder;
import preferences.Preferences;
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

        return new Project(name);
    }

    public MCDModels createMCDModels(Project project, String name){
        
        return new MCDModels(project, name);
    }

    public MCDPackages createMCDPackages(ProjectElement parent, String name){
        return new MCDPackages(parent, name);
    }

    public Preferences createPreferences(ProjectElement parent, String name) {
        return new Preferences(parent, name);
    }

    public MCDDiagrams createMCDDiagrams(ProjectElement parent, String name){
        return new MCDDiagrams(parent, name);
    }

    public MCDEntities createMCDEntities(ProjectElement parent, String name){
        MCDEntities mcdEntities = new MCDEntities(parent, name);
         return mcdEntities;
    }

    public MCDEntity createMCDEntity(ProjectElement parent){
        MCDEntity mcdEntity = new MCDEntity(parent);
        return mcdEntity;
    }

    public MCDContAttributes createMCDAttributes(MCDEntity parent, String name){
        return new MCDContAttributes(parent, name);
    }

    public MCDAttribute createMCDAttribute(MCDContAttributes parent){
        return new MCDAttribute(parent);
    }




    public ProjectElement createMVCCDElementFromXML(String baliseName, ProjectElement ancestor){
        if (baliseName.equals(Preferences.XML_BALISE_PROJECT)) {
            return new Project(null);
        } else if (baliseName.equals(Preferences.XML_BALISE_MODELS)) {
            return new MCDModels(ancestor);
        } else if (baliseName.equals(Preferences.XML_BALISE_DIAGRAMS)) {
            return new MCDDiagrams(ancestor);
        } else if (baliseName.equals(Preferences.XML_BALISE_ENTITIES)) {
            return new MCDEntities(ancestor);
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