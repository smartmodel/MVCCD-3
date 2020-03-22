package project;

import main.MVCCDElement;
import main.MVCCDFactory;
import main.MVCCDManager;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import profile.ProfileManager;
import utilities.files.UtilXML;

public class Project extends ProjectElement {

    private static final long serialVersionUID = 1000;

    private String profileFileName ;
    private Profile profile;
    private int idElementSequence = 0;

    public Project(String name) {

        super(null, name);
    }



    public String getProfileFileName() {
        return profileFileName;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public void setProfileFileName(String profileFileName) {
        this.profileFileName = profileFileName;
     }

     public  Profile adjustProfile(){
         if (this.getProfileFileName() != null) {
             profile = MVCCDFactory.instance().createProfile(this.getProfileFileName());
             Preferences profilePref = ProfileManager.instance().loadFileProfile(
                      Preferences.DIRECTORY_PROFILE_NAME + Preferences.SYSTEM_FILE_SEPARATOR +this.getProfileFileName());
             if (profilePref != null){
                 profilePref.setParent(profile);
                 profilePref.setName(Preferences.REPOSITORY_PREFERENCES_PROFILE_NAME);
                 PreferencesManager.instance().setProfilePref(profilePref);
                 PreferencesManager.instance().copyProfilePref();
             }
         } else {
             profile = null;
             PreferencesManager.instance().setProfilePref(null);
             // A priori c'est une erreur!
             //PreferencesManager.instance().copyDefaultPref();
         }
         new ProjectAdjustPref(this).changeProfile();
         return profile;
     }

     public Preferences getPreferences(){
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof Preferences){
                return (Preferences) mvccdElement;
            }
        }
        return null;
     }


    public int getNextIdElementSequence(){
        idElementSequence++ ;
        return idElementSequence;
    }

    public  ProjectElement getElementById (int id) {
        return getElementById(this, id);
    }

    public  ProjectElement getElementById (ProjectElement projectElement, int id) {
        ProjectElement resultat = null;
        for (MVCCDElement mvccdElement : projectElement.getChilds()) {
            if (mvccdElement instanceof ProjectElement) {
                ProjectElement child = (ProjectElement) mvccdElement;
                if (child.getId() == id) {
                    resultat = child;
                } else {
                    if (resultat == null) {
                        resultat = getElementById(child, id);
                    }
                }
            }
        }
        return resultat;
    }

}