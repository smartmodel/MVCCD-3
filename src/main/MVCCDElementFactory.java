package main;

import main.window.diagram.WinDiagram;
import main.window.diagram.WinDiagramContent;
import main.window.repository.WinRepository;
import main.window.repository.WinRepositoryContent;
import mcd.*;
import messages.LoadMessages;
import messages.MessagesBuilder;
import preferences.Preferences;
import profile.Profile;
import project.Project;
import utilities.window.services.ComponentService;

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

    public MCDPackages createMCDPackages(MVCCDElement mvccdElement, String name){
        return new MCDPackages(mvccdElement, name);
    }

    public MCDDiagrams createMCDDiagrams(MVCCDElement mvccdElement, String name){
        return new MCDDiagrams(mvccdElement, name);
    }

    public MCDEntities createMCDEntities(MVCCDElement mvccdElement, String name){
        return new MCDEntities(mvccdElement, name);
    }

    public MCDEntity createMCDEntity(MVCCDElement mvccdElement){
        return new MCDEntity(mvccdElement);
    }

    public Preferences createPreferences(MVCCDElement mvccdElement, String name) {
        return new Preferences(mvccdElement, name);
    }




    public MVCCDElement createMVCCDElementFromXML(String baliseName, MVCCDElement ancestor){
        if (baliseName.equals(Preferences.XML_BALISE_PROJECT)) {
            return new Project(ancestor);
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