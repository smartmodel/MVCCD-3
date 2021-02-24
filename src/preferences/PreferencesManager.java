package preferences;

import project.Project;

import java.io.FileNotFoundException;


public class PreferencesManager {

    private static PreferencesManager instance;

    private Preferences defaultPref;
    private Preferences applicationPref;
    private Preferences profilePref;
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

    // TODO-STB: Suppression de Giorgio
    /*
    public void loadOrCreateFileApplicationPreferences() {
        try {
            PreferencesLoader loader = new PreferencesLoader();
            applicationPref = loader.load(new File(Preferences.FILE_APPLICATION_PREF_NAME));
        } catch (FileNotFoundException e) {
            applicationPref = new Preferences(null, null);
            PreferencesSaver saver = new PreferencesSaver();
            saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);
        }
    }

    public void createProfile() {
        ProfileFileChooser fileChooser = new ProfileFileChooser(ProjectFileChooser.SAVE);
        File fileChoose = fileChooser.fileChoose();
        if (fileChoose != null){
            if (UtilFiles.confirmIfExist(MVCCDManager.instance().getMvccdWindow(), fileChoose)) {
                new PreferencesSaver().save(fileChoose, projectPref);
            }
        }
    }*/

    //Ajout de Giorgio
    public void loadOrCreateFileXMLApplicationPref() {
        try {
            applicationPref = new PreferencesLoaderXml().loadFileApplicationPref();
        } catch (FileNotFoundException e) {
            applicationPref = new Preferences(null, null);
            new PreferencesSaverXml().createFileApplicationPref();
        }
    }
}
