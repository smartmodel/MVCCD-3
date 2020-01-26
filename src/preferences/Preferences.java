package preferences;


import java.awt.*;

public class Preferences {
    public static String LANGUAGE = "fr";
    public static String VERSION = "3.0.16";
    public static Integer JPANEL_HGAP = 5;
    public static Integer JPANEL_VGAP = 5;
    public static Integer PANEL_REPOSITORY_WIDTH = 200;
    public static String REPOSITORY_MCD_MODELS_NAME = "MCD";
    public static String REPOSITORY_MCD_PACKAGES_NAME = "Paquetages";
    public static String REPOSITORY_MCD_DIAGRAMS_NAME = "Diagrammes";
    public static String REPOSITORY_MCD_ENTITIES_NAME = "Entités";
    public static Boolean REPOSITORY_MCD_MODELS_MANY = false;
    public static Boolean REPOSITORY_MCD_PACKAGES_EXISTS = false;
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
    public static String FILE_DOT = ".";

    public static Integer FILES_RECENTS_MAX = 20;
    public static Integer FILES_RECENTS_AUTHORIZED = 20;
    public static String FILE_FILES_RECENTS_NAME = "recentFiles.dat";
    public static String FILE_HELP_PROJECT_NAME ="helpProject.txt";
    public static String FILE_HELP_PREFERENCES_NAME ="helpPreferences.txt";
    public static String FILE_HELP_ENTITY_NAME ="helpEntity.txt";

    public static String SYSTEM_LINE_SEPARATOR =System.getProperty("line.separator");
    public static String CHARSET_FILES_TEXT = "UTF-8";


    public static Integer PANEL_BUTTONS_MESSAGES_HEIGHT = 50;
    public static Integer PREF_WINDOW_WIDTH = 600;
    public static Integer PREF_WINDOW_HEIGHT = 300;
    public static Integer PROJECT_WINDOW_WIDTH = 600;
    public static Integer PROJECT_WINDOW_HEIGHT = 300;
    public static Integer ENTITY_WINDOW_WIDTH = 600;
    public static Integer ENTITY_WINDOW_HEIGHT = 300;
    public static Integer HELP_WINDOW_WIDTH = 600;
    public static Integer HELP_WINDOW_HEIGHT = 300;

    public static Boolean DEBUG_BACKGROUND_PANEL = false;

    public static Color EDITOR_SCOMPONENT_BACKGROUND_NORMAL = Color.WHITE;
    public static Color EDITOR_SCOMPONENT_BACKGROUND_ERROR = Color.pink;
    public static Color EDITOR_SCOMPONENT_LINEBORDER_NORMAL = Color.gray;
    public static Color EDITOR_SCOMPONENT_LINEBORDER_ERROR = Color.RED;


}
