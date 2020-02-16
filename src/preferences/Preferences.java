package preferences;


import main.MVCCDElement;

import java.awt.*;

public class Preferences extends MVCCDElement {

    private static final long serialVersionUID = 1000;

    public static String APPLICATION_NAME = "MVCCD";
    public static String LANGUAGE = "fr";
    public static String VERSION = "3.0.16";
    public static Integer JPANEL_HGAP = 5;
    public static Integer JPANEL_VGAP = 5;
    public static Integer PANEL_REPOSITORY_WIDTH = 200;
    public static String REPOSITORY_PROFILE_NAME = "Profile";
    public static String REPOSITORY_PREFERENCES_PROFILE_NAME = "Préférences de profil";
    public static String REPOSITORY_PREFERENCES_NAME = "Préférences de projet";  //Project
    public static String REPOSITORY_PREFERENCES_APPLICATION_NAME = "PréférencesApplication";  //Project
    public static String REPOSITORY_MCD_MODELS_NAME = "MCD";
    public static String REPOSITORY_MCD_PACKAGES_NAME = "Paquetages";
    public static String REPOSITORY_MCD_DIAGRAMS_NAME = "Diagrammes";
    public static String REPOSITORY_MCD_ENTITIES_NAME = "Entités";
    public static Boolean REPOSITORY_MCD_MODELS_MANY = false;
    public static Boolean REPOSITORY_MCD_PACKAGES_AUTHORIZEDS = false;
    public static Color BACKGROUND_PANEL = Color.LIGHT_GRAY;
    public static String NAME_REGEXPR = "[a-zA-Z]{1,1}[a-zA-Z0-9_]*";
    public static Integer PROJECT_NAME_LENGTH = 5;
    public static Integer ENTITY_NAME_LENGTH = 5;
    //public static String XML_ENTETE_FICHIER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static String XML_ENTETE_FICHIER = "<?xml version=\"1.0\" encoding=\"windows-1252\"?>";

    public static String XML_BALISE_PROJECT = "project";
    public static String XML_BALISE_DIAGRAMS = "diagrams";
    public static String XML_BALISE_ENTITIES = "entities";
    public static String XML_BALISE_ENTITY = "entity";
    public static String XML_BALISE_MODELS = "models";
    public static String XML_BALISE_PACKAGES = "packages";


    public static String XML_ATTRIBUTE_NAME = "name";
    public static String XML_ATTRIBUTE_SHORTNAME = "shortName";

    public static String FILE_PROJECT_EXTENSION = "mvccd";
    public static String FILE_PREFERENCES_EXTENSION = "pref";
    public static String FILE_PROFILE_EXTENSION = "profile";
    public static String FILE_DOT = ".";

    public static Integer FILES_RECENTS_MAX = 20;
    public static Integer FILES_RECENTS_AUTHORIZED = 20;
    public static String FILE_FILES_RECENTS_NAME = "recentFiles.dat";
    public static String FILE_APPLICATION_PREF_NAME = "application.pref";
    public static String FILE_HELP_PROJECT_NAME ="helpProject.txt";
    public static String FILE_HELP_PREFERENCES_NAME ="helpPreferences.txt";
    public static String FILE_HELP_ENTITY_NAME = "helpEntity.txt";

    public static String DIRECTORY_PROFILE_NAME = "profiles";

    public static String SYSTEM_LINE_SEPARATOR =System.getProperty("line.separator");
    public static String SYSTEM_FILE_SEPARATOR =System.getProperty("file.separator");
    public static String CHARSET_FILES_TEXT = "UTF-8";


    public static Integer PANEL_BUTTONS_MESSAGES_HEIGHT = 50;
    public static Integer PROJECT_WINDOW_WIDTH = 600;
    public static Integer PROJECT_WINDOW_HEIGHT = 300;
    public static Integer ENTITY_WINDOW_WIDTH = 800;
    public static Integer ENTITY_WINDOW_HEIGHT = 600;
    public static Integer HELP_WINDOW_WIDTH = 600;
    public static Integer HELP_WINDOW_HEIGHT = 300;
    public static Integer PREFERENCES_WINDOW_WIDTH = 600;
    public static Integer PREFERENCES_WINDOW_HEIGHT = 300;


    public Color EDITOR_SCOMPONENT_BACKGROUND_NORMAL = Color.WHITE;
    public Color EDITOR_SCOMPONENT_BACKGROUND_ERROR = Color.pink;
    public Color EDITOR_SCOMPONENT_LINEBORDER_NORMAL = Color.gray;
    public Color EDITOR_SCOMPONENT_LINEBORDER_ERROR = Color.RED;

    // Modification Application
    private Boolean DEBUG = false;
    private Boolean DEBUG_BACKGROUND_PANEL = false;
    private Boolean DEBUG_PRINT_MVCCDELEMENT = false;
    //private PreferencesDisplay GENERAL_PREFERENCES_DISPLAY = PreferencesDisplay.REPOSITORY;

    // Modification User/Custom
    private Boolean MCD_JOURNALIZATION = false;
    private Boolean MCD_JOURNALIZATION_EXCEPTION = true;
    private Boolean MCD_AUDIT = false;
    private Boolean MCD_AUDIT_EXCEPTION = true;


    public Preferences(MVCCDElement parent, String name) {
        super(parent, name);

    }

    @Override
    public String baliseXMLBegin() {
        return null;
    }

    @Override
    public String baliseXMLEnd() {
        return null;
    }

    /*
    public PreferencesDisplay getGENERAL_PREFERENCES_DISPLAY() {
        return GENERAL_PREFERENCES_DISPLAY;
    }

    public void setGENERAL_PREFERENCES_DISPLAY(PreferencesDisplay GENERAL_PREFERENCES_DISPLAY) {
        this.GENERAL_PREFERENCES_DISPLAY = GENERAL_PREFERENCES_DISPLAY;
    }
    */


    public Boolean getDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(Boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    public Boolean getDEBUG_BACKGROUND_PANEL() {
        return DEBUG_BACKGROUND_PANEL;
    }

    public void setDEBUG_BACKGROUND_PANEL(Boolean DEBUG_BACKGROUND_PANEL) {
        this.DEBUG_BACKGROUND_PANEL = DEBUG_BACKGROUND_PANEL;
    }

    public Boolean getDEBUG_PRINT_MVCCDELEMENT() {
        return DEBUG_PRINT_MVCCDELEMENT;
    }

    public void setDEBUG_PRINT_MVCCDELEMENT(Boolean DEBUG_PRINT_MVCCDELEMENT) {
        this.DEBUG_PRINT_MVCCDELEMENT = DEBUG_PRINT_MVCCDELEMENT;
    }

    public Boolean getMCD_JOURNALIZATION() {
        return MCD_JOURNALIZATION;
    }
    public void setMCD_JOURNALIZATION(Boolean MCD_JOURNALIZATION) {
        this.MCD_JOURNALIZATION = MCD_JOURNALIZATION;
    }

    public Boolean getMCD_JOURNALIZATION_EXCEPTION() {
        return MCD_JOURNALIZATION_EXCEPTION;
    }

    public void setMCD_JOURNALIZATION_EXCEPTION(Boolean MCD_JOURNALIZATION_EXCEPTION) {
        this.MCD_JOURNALIZATION_EXCEPTION = MCD_JOURNALIZATION_EXCEPTION;
    }

    public Boolean getMCD_AUDIT() {
        return MCD_AUDIT;
    }
    public void setMCD_AUDIT(Boolean MCD_AUDIT) {
        this.MCD_AUDIT = MCD_AUDIT;
    }

    public Boolean getMCD_AUDIT_EXCEPTION() {
        return MCD_AUDIT_EXCEPTION;
    }

    public void setMCD_AUDIT_EXCEPTION(Boolean MCD_AUDIT_EXCEPTION) {
        this.MCD_AUDIT_EXCEPTION = MCD_AUDIT_EXCEPTION;
    }

}
