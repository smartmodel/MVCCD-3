package project;

import main.MVCCDElement;
import main.MVCCDFactory;
import main.MVCCDManager;
import mcd.services.MCDAdjustPref;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import profile.ProfileLoaderXml;

/**
 * Classe maitresse du projet utilisateur.
 * Divers attributs permettent de mémoriser les caractéristiques du projet utilisateur.
 * Le projet est un élément du référentiel (hérite de MVCCDElement).
 */
public class Project extends ProjectElement {

    private static final long serialVersionUID = 1000;

    // Utilisées pour indiquer le statut d'un projet
    public static final int NEW = 1;
    public static final int EXISTING = 2;

    // Fichier de profil
    private String profileFileName;
    // Instance de profil
    private Profile profile;
    //Plusieurs modèles autorisés
    private boolean modelsMany;
    //Plusieurs paquetages autorisés
    private boolean packagesAutorizeds;

    // Dernier numéro de séquence donné
    private int idElementSequence = 0;

    // Sauvegarde de l'état du projet
    // Dernier noeud du référentiel visité
    private ProjectElement lastWinRepositoryProjectElement = null;
    // Etat du dernier noeud visité
    private boolean lastWinRepositoryExpand = false;


    /**
     * Le projet n'a pas de parent ! Par contre, il est rattaché à la racine de l'arbre de visualisation du référentiel
     * pour pouvoir le visualiser. Le rattachement se fait par la commande repository.addProject(project) de la méthode
     * projectToRepository() de la classe MVCCDManager.
     */
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

    public Profile adjustProfile() {
        if (this.getProfileFileName() != null) {
            profile = MVCCDFactory.instance().createProfile(this.getProfileFileName());
            //Preferences profilePref = ProfileManager.instance().loadFileProfile(Preferences.DIRECTORY_PROFILE_NAME + Preferences.SYSTEM_FILE_SEPARATOR + this.getProfileFileName());
            Preferences profilePref = new ProfileLoaderXml().loadFileProfileXML(this.getProfileFileName());
            if (profilePref != null) {
                profilePref.setOrChangeParent(profile);
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
        new MCDAdjustPref(this).changeProfile();
        MVCCDManager.instance().profileToRepository();
        return profile;
    }

    public Preferences getPreferences() {
        for (MVCCDElement mvccdElement : getChilds()) {
            if (mvccdElement instanceof Preferences) {
                return (Preferences) mvccdElement;
            }
        }
        return null;
    }


    public int getNextIdElementSequence() {
        idElementSequence++;
        return idElementSequence;
    }

    public boolean isModelsMany() {
        return modelsMany;
    }

    public void setModelsMany(boolean modelsMany) {
        this.modelsMany = modelsMany;
    }

    public boolean isPackagesAutorizeds() {
        return packagesAutorizeds;
    }

    public void setPackagesAutorizeds(boolean packagesAutorizeds) {
        this.packagesAutorizeds = packagesAutorizeds;
    }


    // Sauvegarde de l'état du projet


    public ProjectElement getLastWinRepositoryProjectElement() {
        return lastWinRepositoryProjectElement;
    }

    public void setLastWinRepositoryProjectElement(ProjectElement lastWinRepositoryProjectElement) {
        this.lastWinRepositoryProjectElement = lastWinRepositoryProjectElement;
    }

    public boolean isLastWinRepositoryExpand() {
        return lastWinRepositoryExpand;
    }

    public void setLastWinRepositoryExpand(boolean lastWinRepositoryExpand) {
        this.lastWinRepositoryExpand = lastWinRepositoryExpand;
    }
}
