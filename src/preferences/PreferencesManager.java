package preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import profile.ProfileFileChooser;
import project.Project;
import project.ProjectFileChooser;
import project.ProjectSaverXML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


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
        // MCD
        to.setMCD_AID_IND_COLUMN_NAME(from.getMCD_AID_IND_COLUMN_NAME());
        to.setMCD_AID_WITH_DEP(from.isMCD_AID_WITH_DEP());
        to.setMCD_AID_DEP_COLUMN_NAME(from.getMCD_AID_DEP_COLUMN_NAME());
        to.setMCD_AID_DATATYPE_LIENPROG(from.getMCD_AID_DATATYPE_LIENPROG());
        to.setMCDDATATYPE_NUMBER_SIZE_MODE(from.getMCDDATATYPE_NUMBER_SIZE_MODE());
        to.setMCD_JOURNALIZATION(from.getMCD_JOURNALIZATION());
        to.setMCD_JOURNALIZATION_EXCEPTION(from.getMCD_JOURNALIZATION_EXCEPTION());
        to.setMCD_AUDIT(from.getMCD_AUDIT());
        to.setMCD_AUDIT_EXCEPTION(from.getMCD_AUDIT_EXCEPTION());
        to.setMCD_TREE_NAMING_ASSOCIATION(from.getMCD_TREE_NAMING_ASSOCIATION());
        to.setMCD_MODE_NAMING_LONG_NAME(from.getMCD_MODE_NAMING_LONG_NAME());
        to.setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(from.getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME());
    }

    /*public void loadOrCreateFileApplicationPreferences() {
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
        if (fileChoose != null) {
            new PreferencesSaver().save(fileChoose, projectPref);
        }
    }*/

    public void loadOrCreateFileXMLApplicationPref() {
        try {
            applicationPref = new PreferencesLoaderXml().loadFileApplicationPref();
        } catch (FileNotFoundException e) {
            applicationPref = new Preferences(null, null);
            new PreferencesSaverXml().createFileApplicationPref();
        }
    }
}
