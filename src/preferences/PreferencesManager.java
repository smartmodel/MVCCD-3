package preferences;

import console.ViewLogsManager;
import exceptions.CodeApplException;
import main.MVCCDManager;
import messages.MessagesBuilder;
import profile.ProfileFileChooser;
import project.Project;
import project.ProjectFileChooser;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.Trace;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;

import java.io.File;
import java.io.FileNotFoundException;


public class PreferencesManager {

    private static PreferencesManager instance;

    private Preferences defaultPref;
    private Preferences applicationPref; //maintient les préférences d'application, séparément du projet. Nécessaire lorsque le projet n'est pas encore existant.
    private Preferences profilePref; //ensemble de préférences sauvegardés, pouvant être chargées en tant que préférences de projet.
    private Preferences projectPref;

    public static synchronized PreferencesManager instance() {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    public PreferencesManager() {
        defaultPref = new Preferences(null, null);
    }

    /**
     * Retourne les préférences qui doivent être prises en compte par l'instance du programme lancé par l'utilisateur.
     * Les préférences sont retournées dans l'ordre de priorité suivant: (1) préférences du projet si elles existent,
     * sinon (2) les préférences de l'application si elles existent et, autrement, (3) les préférences par défaut.
     * @return L'ensemble des préférences à utiliser.
     */
    public Preferences preferences() {
        if (projectPref != null) {
            return projectPref;
        } else if (applicationPref != null) {
            return applicationPref;
        } else if (defaultPref != null) {
            return defaultPref;
        } else {
            return null;
        }
    }

    /**
     * Évalue l'existance de préférences de profile.
     * @return Retourne les préférences de profile si elles existent et le cas échéant, retourne les préférences par
     * défaut
     */
    public Preferences profileOrDefault() {
        if (profilePref != null) {
            return profilePref;
        } else if (defaultPref != null) {
            return defaultPref;
        } else {
            return null;
        }
    }

    public Preferences getDefaultPref() {
        return defaultPref;
    }

    public Preferences getApplicationPref() {
        return applicationPref;
    }

    public Preferences getProfilePref() {
        return profilePref;
    }

    public void setProfilePref(Preferences profilePref) {

        this.profilePref = profilePref;
    }

    public Preferences getProjectPref() {

        return projectPref;
    }

    public void setProjectPref(Preferences projectPref) {
        this.projectPref = projectPref;
    }

    public void copyApplicationPref(int projectState) {
        projectPref.setDEBUG(applicationPref.isDEBUG());
        projectPref.setDEBUG_PRINT_MVCCDELEMENT(applicationPref.isDEBUG_PRINT_MVCCDELEMENT());
        projectPref.setDEBUG_BACKGROUND_PANEL(applicationPref.isDEBUG_BACKGROUND_PANEL());
        projectPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(applicationPref.isDEBUG_SHOW_TABLE_COL_HIDDEN());
        projectPref.setDEBUG_INSPECT_OBJECT_IN_TREE(applicationPref.getDEBUG_INSPECT_OBJECT_IN_TREE());
        projectPref.setDEBUG_EDITOR_DATAS_CHANGED(applicationPref.getDEBUG_EDITOR_DATAS_CHANGED());
        projectPref.setDEBUG_TD_PRINT(applicationPref.getDEBUG_TD_PRINT());
        projectPref.setDEBUG_TD_UNICITY_PRINT(applicationPref.getDEBUG_TD_UNICITY_PRINT());
        projectPref.setWARNING_LEVEL(applicationPref.getWARNING_LEVEL());
        //#MAJ 2021-03-16 Provisoire en attendant la sauvegarde XML finalisée
        projectPref.setPERSISTENCE_SERIALISATION_INSTEADOF_XML(
                applicationPref.isPERSISTENCE_SERIALISATION_INSTEADOF_XML());
        File fileProject = MVCCDManager.instance().getFileProjectCurrent();
        if (MVCCDManager.instance().getFileProjectCurrent() != null){
            String extensionAppl ;
            if (PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML()){
                extensionAppl =  Preferences.FILE_PROJECT_EXTENSION ;
            } else {
                extensionAppl = "xml";
            }
            String extensionProject = UtilFiles.getExtension(fileProject.getName()) ;
            if (! extensionAppl.equals(extensionProject)){
                String newNameFileProjet = UtilFiles.changeExtension(fileProject.getName(),extensionAppl);
                String directory = UtilFiles.getStrDirectory(fileProject);
                File newFileProject = UtilFiles.createFile(directory, newNameFileProjet);
                MVCCDManager.instance().setFileProjectCurrent(newFileProject);
                //Activation de la sauvegarde par défaut
                MVCCDManager.instance().setDatasProjectChanged(true);
                // Positionne le nouveau fichier comme dernier ouvert (Premier de la liste)
                String message = "Le projet a été ouvert à partir d'un fichier au format : " +
                        extensionProject + System.lineSeparator() +
                        "La prochaine sauvegarde se fera au format de votre préférence d'application : " + extensionAppl;
                if (extensionAppl.equals("xml")){
                    message = message + System.lineSeparator() +
                            "Tant que la sauvegarde xml n'est pas finalisée des données du projet peuvent être perdues!";
                }
                ViewLogsManager.printMessage(message, ResultatLevel.INFO);
                DialogMessage.showOk(MVCCDManager.instance().getMvccdWindow(), message, "Changement de format");
            }
            // Indicateur pour pouvoir faire une sauvegarde avec le format changé
            MVCCDManager.instance().setExtensionProjectFileNotEqual(! extensionAppl.equals(extensionProject));
        }
        // Fin provisoire !

        // Pour le moment pas de changement possible pour un projet existant
        // A analyser et reprendre plus tard
        if (projectState == Project.NEW) {
            projectPref.setREPOSITORY_MCD_MODELS_MANY(applicationPref.getREPOSITORY_MCD_MODELS_MANY());
        }

        projectPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
    }

    public void copyProfilePref() {
        copyPref(profilePref, projectPref);
    }

    /*
    public void copyDefaultPref() {
        copyPref(defaultPref, projectPref);
     }
     */

    private void copyPref(Preferences from, Preferences to) {

        // Général
        //to.setPROJET_VERSION(from.getPROJET_VERSION());
        to.setGENERAL_RELATION_NOTATION(from.getGENERAL_RELATION_NOTATION()) ;

        // MCD
        to.setMCD_JOURNALIZATION(from.getMCD_JOURNALIZATION());
        to.setMCD_JOURNALIZATION_EXCEPTION(from.getMCD_JOURNALIZATION_EXCEPTION());
        to.setMCD_AUDIT(from.getMCD_AUDIT());
        to.setMCD_AUDIT_EXCEPTION(from.getMCD_AUDIT_EXCEPTION());
        to.setMCDDATATYPE_NUMBER_SIZE_MODE(from.getMCDDATATYPE_NUMBER_SIZE_MODE());
        to.setMCD_AID_DATATYPE_LIENPROG(from.getMCD_AID_DATATYPE_LIENPROG());
        to.setMCD_AID_COLUMN_NAME_LENGTH(from.getMCD_AID_COLUMN_NAME_LENGTH());
        to.setMCD_AID_IND_COLUMN_NAME(from.getMCD_AID_IND_COLUMN_NAME());
        to.setMCD_AID_DEP_COLUMN_NAME(from.getMCD_AID_DEP_COLUMN_NAME());
        to.setMCD_AID_WITH_DEP(from.isMCD_AID_WITH_DEP());
        to.setMCD_AID_SIZEDEFAULT(from.MCDDOMAIN_AID_SIZEDEFAULT);
        to.setMCD_TREE_NAMING_ASSOCIATION(from.getMCD_TREE_NAMING_ASSOCIATION());
        to.setMCD_MODE_NAMING_LONG_NAME(from.getMCD_MODE_NAMING_LONG_NAME());
        to.setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(from.getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME());

        // Editeur
        to.setPREFERENCES_WINDOW_SIZE_CUSTOM(from.getPREFERENCES_WINDOW_SIZE_CUSTOM());
        to.setPREFERENCES_WINDOW_LOCATION_ONSCREEN(from.getPREFERENCES_WINDOW_LOCATION_ONSCREEN());
        to.setENTITY_WINDOW_SIZE_CUSTOM(from.getENTITY_WINDOW_SIZE_CUSTOM());
        to.setENTITY_WINDOW_LOCATION_ONSCREEN(from.getENTITY_WINDOW_LOCATION_ONSCREEN());
        to.setMDRTABLE_WINDOW_SIZE_CUSTOM(from.getMDRTABLE_WINDOW_SIZE_CUSTOM());
        to.setMDRTABLE_WINDOW_LOCATION_ONSCREEN(from.getMDRTABLE_WINDOW_LOCATION_ONSCREEN());

        // MCD --> MLD-R
        to.setMCDTOMLDR_MODE(from.getMCDTOMLDR_MODE());

        // MLD-R --> MPD-R
        to.setMLDRTOMPDR_DB(from.getMLDRTOMPDR_DB());

        //MDR
        to.setMDR_TABLE_NAME_FORMAT(from.getMDR_TABLE_NAME_FORMAT());
        to.setMDR_TABLE_NN_NAME_FORMAT(from.getMDR_TABLE_NN_NAME_FORMAT());
        to.setMDR_TABLE_NN_NAME_INDICE_FORMAT(from.getMDR_TABLE_NN_NAME_INDICE_FORMAT());
        to.setMDR_COLUMN_ATTR_NAME_FORMAT(from.getMDR_COLUMN_ATTR_NAME_FORMAT());
        to.setMDR_COLUMN_ATTR_SHORT_NAME_FORMAT(from.getMDR_COLUMN_ATTR_SHORT_NAME_FORMAT());
        to.setMDR_COLUMN_DERIVED_MARKER(from.getMDR_COLUMN_DERIVED_MARKER());
        to.setMDR_PK_NAME_FORMAT(from.getMDR_PK_NAME_FORMAT());
        to.setMDR_COLUMN_PK_NAME_FORMAT(from.getMDR_COLUMN_PK_NAME_FORMAT());
        to.setMDR_COLUMN_FK_NAME_FORMAT(from.getMDR_COLUMN_FK_NAME_FORMAT());
        to.setMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT(from.getMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT());
        to.setMDR_FK_NAME_FORMAT(from.getMDR_FK_NAME_FORMAT());
        to.setMDR_PATH_SEP_FORMAT(from.getMDR_PATH_SEP_FORMAT());
        to.setMDR_PEA_SEP_FORMAT(from.getMDR_PEA_SEP_FORMAT());
        to.setMDR_TABLE_SEP_FORMAT(from.getMDR_TABLE_SEP_FORMAT());
        to.setMDR_ROLE_SEP_FORMAT(from.getMDR_ROLE_SEP_FORMAT());
        to.setMDR_FKIND_SEP_FORMAT(from.getMDR_FKIND_SEP_FORMAT());

        // MDR Préférences
        to.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR(from.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR());
        to.setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(from.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF());

        // MLDR Preferences
        to.setMLDR_PREF_NAMING_LENGTH(from.getMLDR_PREF_NAMING_LENGTH());

    }

    /**
     * Méthode utile pour la persistance avec sérialisation uniquement.
     * Elle pourra être supprimée lorsque la propriété Preferences.PERSISTENCE_SERIALISATION_INSTEADOF_XML sera supprimée.
     * @author PAS
    */
    public void loadOrCreateFileApplicationPreferences() {
        boolean tempInutile = PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML(); //Ligne créée uniquement dans le but de ne pas oublier de supprimer cette méthode lorsque la préférence sera supprimée.
        try {
            PreferencesLoader loader = new PreferencesLoader();
            applicationPref = loader.load(new File(Preferences.FILE_APPLICATION_PREF_NAME));
        } catch (FileNotFoundException e) {
            applicationPref = new Preferences(null, null);
            PreferencesSaver saver = new PreferencesSaver();
            saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);
        }
    }

    /**
     * Méthode utile pour la persistance avec sérialisation uniquement.
     * Elle pourra être supprimée lorsque la propriété Preferences.PERSISTENCE_SERIALISATION_INSTEADOF_XML sera supprimée.
     * @author PAS
     */
    public void createProfile() {
        boolean tempInutile = PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML(); //Ligne créée uniquement dans le but de ne pas oublier de supprimer cette méthode lorsque la préférence sera supprimée.
        ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose();
        if (fileChoose != null){
            if (UtilFiles.confirmIfExist(MVCCDManager.instance().getMvccdWindow(), fileChoose)) {
                new PreferencesSaver().save(fileChoose, projectPref);
            }
        }
    }

    /**
     * Se charge de charger le fichier XML des préférences d'application et de les appliquer à this.applicationPref.
     * Si le fichier XML n'existe pas, une instance vide de applicationPref est créée, et le fichier XML est créé
     * également.
     * Cette méthode est créée dans le cadre de la persistance XML au lieu et place de la persistance avec sérialisation.
     * @author Giorgio Roncallo
     */
    public Resultat loadOrCreateFileXMLApplicationPref() {
        Resultat resultat = new Resultat();
        try {
            applicationPref = new PreferencesOfApplicationLoaderXml().loadFileApplicationPref();
            return resultat;
        } catch (Exception eLoad) {
            // Fichier pas trouvé ou corrompu
            // Le fichier des préférences est réinitialisé
            try {
                String message= "";
                if (eLoad instanceof FileNotFoundException ){
                    message = MessagesBuilder.getMessagesProperty("pref.appl.load.not.file.error");
                } else  if (eLoad instanceof CodeApplException){
                    message = MessagesBuilder.getMessagesProperty("pref.appl.load.file.corrupt.error");
                } else {
                    message = MessagesBuilder.getMessagesProperty("pref.appl.load.error");
                }
                resultat.addExceptionCatched(eLoad, message);

                applicationPref = new Preferences(null, null);
                new PreferencesOfApplicationSaverXml().createFileApplicationPref();
                return resultat ;
            } catch(Exception eCreate) {
                throw eCreate;
            }
        }
    }
}
