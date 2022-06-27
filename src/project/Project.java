package project;

import main.MVCCDElement;
import main.MVCCDFactory;
import main.MVCCDManager;
import mcd.MCDContDiagrams;
import mcd.MCDContModels;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.Profile;
import profile.ProfileLoaderXml;

/**
 * Classe maitresse du projet utilisateur. Divers attributs permettent de mémoriser les caractéristiques du projet utilisateur. Le projet est un élément du référentiel (hérite de MVCCDElement). Les enfants (childs) du projet sont notamment les préférences ainsi que le conteneur de modèles (MCDContModels).
 */
public class Project extends ProjectElement {

  // Utilisées pour indiquer le statut d'un projet
  public static final int NEW = 1;
  public static final int EXISTING = 2;
  private static final long serialVersionUID = 1000;
  private String version;

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
   * L'id du projet est à indiquer uniquement lors du chargement du projet persisté. Autrement, il faut utiliser le constructeur sans le paramètre "id".
   * @param id Id à affecter au projet (qui est lui-même un élément de projet ProjectElement)
   */
  public Project(int id) {
    super(null, id);
  }

  /**
   * Le projet n'a pas de parent ! Par contre, il est rattaché à la racine de l'arbre de visualisation du référentiel pour pouvoir le visualiser. Le rattachement se fait par la commande repository.addProject(project) de la méthode projectToRepository() de la classe MVCCDManager.
   * @param name Le nom donné au projet.
   */
  public Project(String name) {
    super(null, name);
  }

  public String getVersion() {
    if (this.version == null) {
      // Uniquement pour la phase de développement !
      return Preferences.APPLICATION_VERSION;
    }
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getProfileFileName() {
    return this.profileFileName;
  }

  public void setProfileFileName(String profileFileName) {
    this.profileFileName = profileFileName;
  }

  public Profile getProfile() {
    return this.profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Profile adjustProfile() {
    if (this.getProfileFileName() != null) {
      this.profile = MVCCDFactory.instance().createProfile(this.getProfileFileName());
      //Preferences profilePref = ProfileManager.instance().loadFileProfile(Preferences.DIRECTORY_PROFILE_NAME + Preferences.SYSTEM_FILE_SEPARATOR + this.getProfileFileName());
      Preferences profilePref = new ProfileLoaderXml().loadFileProfileXML(this.getProfileFileName());
      if (profilePref != null) {
        profilePref.setOrChangeParent(this.profile);
        profilePref.setName(Preferences.REPOSITORY_PREFERENCES_PROFILE_NAME);
        PreferencesManager.instance().setProfilePref(profilePref);
        PreferencesManager.instance().copyProfilePref();
      }
    } else {
      this.profile = null;
      PreferencesManager.instance().setProfilePref(null);
      // A priori c'est une erreur!
      //PreferencesManager.instance().copyDefaultPref();
    }
    //TODO-1 A voir plus précisément l'impact des changements de préférences qui impactie le MCD : Audit, Journal ...
    //new MCDAdjustPref(this).changeProfile();
    MVCCDManager.instance().profileToRepository();
    return this.profile;
  }

  /**
   * Recherche les préférences du projet en parcourant tous les enfants du projet jusqu'à tomber sur l'objet correspondant aux préférences. Remarque de STB: une alternative est PreferencesManager.getProjectPref()
   * @return Ensemble des préférences du projet
   */
  public Preferences getPreferences() {
    for (MVCCDElement mvccdElement : this.getChilds()) {
      if (mvccdElement instanceof Preferences) {
        return (Preferences) mvccdElement; //Retourne le 1er enfant qui correspond aux préférences
      }
    }
    return null;
  }

  public int getNextIdElementSequence() {
    this.idElementSequence++;
    return this.idElementSequence;
  }

  /**
   * Attention, à n'utiliser que pour connaître le dernier numéro d'id qui a été donné par la séquence d'id unique d'éléments d'un projet.
   * @return Retourne le dernier id que la séquence a donné.
   */
  public int getIdElementSequence() {
    return this.idElementSequence;
  }

  /**
   * Force la séquence d'id à repartir d'un certain numéro.
   * @param id La séquence prendra le numéro passé en paramètre comme dernier numéro déjà attribué à un élément de projet.
   */
  public void setIdElementSequence(int id) {
    this.idElementSequence = id;
  }

  public boolean isModelsMany() {
    return this.modelsMany;
  }

  public void setModelsMany(boolean modelsMany) {
    this.modelsMany = modelsMany;
  }

  public boolean isPackagesAutorizeds() {
    return this.packagesAutorizeds;
  }

  public void setPackagesAutorizeds(boolean packagesAutorizeds) {
    this.packagesAutorizeds = packagesAutorizeds;
  }

  // Sauvegarde de l'état du projet

  public ProjectElement getLastWinRepositoryProjectElement() {
    return this.lastWinRepositoryProjectElement;
  }

  public void setLastWinRepositoryProjectElement(ProjectElement lastWinRepositoryProjectElement) {
    this.lastWinRepositoryProjectElement = lastWinRepositoryProjectElement;
  }

  public boolean isLastWinRepositoryExpand() {
    return this.lastWinRepositoryExpand;
  }

  public void setLastWinRepositoryExpand(boolean lastWinRepositoryExpand) {
    this.lastWinRepositoryExpand = lastWinRepositoryExpand;
  }

  /**
   * Retourne le container de modèles, qui contient le (ou les) MCD qui sont dans le projet de l'utilisateur.
   * @return Si aucun conteneur de modèles n'est trouvé, null est retourné.
   */
  public MCDContModels getMCDContModels() {
    for (MVCCDElement mvccdElementChild : this.getChilds()) {
      if (mvccdElementChild instanceof MCDContModels) {
        return (MCDContModels) mvccdElementChild;
      }
    }
    return null;
  }

  public MCDContDiagrams getMCDContDiagrams() {
    for (MVCCDElement mvccdElementChild : this.getChilds()) {
      if (mvccdElementChild instanceof MCDContDiagrams) {
        return (MCDContDiagrams) mvccdElementChild;
      }
    }
    return null;
  }
}