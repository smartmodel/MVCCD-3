package main;

import mcd.*;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.Project;

public class MVCCDFactory {

    private static MVCCDFactory instance;


    public static synchronized MVCCDFactory instance() {
        if (instance == null) {
            instance = new MVCCDFactory();
        }
        return instance;
    }


    public Project createProject(String name){
        System.out.println ("Manager nom de projet: " + name);
        Project project = MVCCDElementFactory.instance().createProject(name);
        MCDModels mcdModels = MVCCDElementFactory.instance().createMCDModels(project, Preferences.REPOSITORY_MCD_MODELS_NAME);
        if (!Preferences.REPOSITORY_MCD_MODELS_MANY) {
            if (Preferences.REPOSITORY_MCD_PACKAGES_EXISTS) {
                MCDPackages mcdPackages = MVCCDElementFactory.instance().createMCDPackages(mcdModels, Preferences.REPOSITORY_MCD_PACKAGES_NAME);
            }
            MCDDiagrams mcdDiagrams = MVCCDElementFactory.instance().createMCDDiagrams(mcdModels,Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
            MCDEntities mcdEntities = MVCCDElementFactory.instance().createMCDEntities(mcdModels,Preferences.REPOSITORY_MCD_ENTITIES_NAME);

        }
        return project;
    }

}