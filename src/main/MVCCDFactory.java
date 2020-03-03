package main;

import datatypes.MDDatatype;
import datatypes.MDDatatypesManager;
import mcd.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import profile.ProfileManager;
import project.Project;

public class MVCCDFactory {

    private static MVCCDFactory instance;

    MVCCDElementProfileEntry profileEntry ;  // L'entrée du profil doit être mémorisée

    public static synchronized MVCCDFactory instance() {
        if (instance == null) {
            instance = new MVCCDFactory();
        }
        return instance;
    }


    public Project createProject(String name){
        Project project = MVCCDElementFactory.instance().createProject(name);
        return project;
    }


    public void completeProject(Project project) {
        Preferences preferences = MVCCDElementFactory.instance().createPreferences(project, Preferences.REPOSITORY_PREFERENCES_NAME);
        MCDModels mcdModels = MVCCDElementFactory.instance().createMCDModels(project, Preferences.REPOSITORY_MCD_MODELS_NAME);
        if (!Preferences.REPOSITORY_MCD_MODELS_MANY) {

            if (Preferences.REPOSITORY_MCD_PACKAGES_AUTHORIZEDS) {
                MCDPackages mcdPackages = MVCCDElementFactory.instance().createMCDPackages(mcdModels, Preferences.REPOSITORY_MCD_PACKAGES_NAME);
            }
            MCDDiagrams mcdDiagrams = MVCCDElementFactory.instance().createMCDDiagrams(mcdModels,Preferences.REPOSITORY_MCD_DIAGRAMS_NAME);
            MCDEntities mcdEntities = MVCCDElementFactory.instance().createMCDEntities(mcdModels,Preferences.REPOSITORY_MCD_ENTITIES_NAME);

        }
    }


    public Profile createProfile(String profileFileName) {

        Profile profile = new Profile(profileEntry , profileFileName);
        return profile;
    }




    public MVCCDElementRepositoryRoot createRepositoryRoot(){
        MVCCDElementRepositoryRoot repositoryRoot = new MVCCDElementRepositoryRoot();
        MVCCDElementRepositoryGlobal repositoryGlobal = new MVCCDElementRepositoryGlobal(repositoryRoot);
        MVCCDElementApplicationPreferences applicationPref = new MVCCDElementApplicationPreferences(repositoryGlobal);
        MVCCDElementApplicationMDDatatypes applicationMDDatatype = new MVCCDElementApplicationMDDatatypes(repositoryGlobal);
        profileEntry = new MVCCDElementProfileEntry (repositoryRoot);
        return repositoryRoot;
    }

}