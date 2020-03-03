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
    public static String REPOSITORY_MCD_ATTRIBUTES_NAME = "Attributs";
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
    public static String FILE_HELP_MCDDATATYPE_NAME = "helpMCDDatatpe.txt";

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
    public static Integer MCDDATATYPE_WINDOW_WIDTH = 800;
    public static Integer MCDDATATYPE_WINDOW_HEIGHT = 600;

    public static Integer EDITOR_FIELD_HEIGHT = 20;
    public static Integer EDITOR_TABLE_ROW_HEIGHT = 20;

    public static Color EDITOR_SCOMPONENT_BACKGROUND_NORMAL = Color.WHITE;
    public static Color EDITOR_SCOMPONENT_BACKGROUND_ERROR = Color.pink;
    public static Color EDITOR_SCOMPONENT_LINEBORDER_NORMAL = Color.gray;
    public static Color EDITOR_SCOMPONENT_LINEBORDER_ERROR = Color.RED;

    public static Color SCOMPONENT_INDIRECT_INPUT_FOREGROUND = Color.RED;

    public static String STEREOTYPE_MCDATTRIBUTE_AID_NAME = "AID";
    public static String STEREOTYPE_MCDATTRIBUTE_AID_LIENPROG = "AID";
    public static String STEREOTYPE_MCDATTRIBUTE_M_NAME = "M";
    public static String STEREOTYPE_MCDATTRIBUTE_M_LIENPROG = "M";
    public static String STEREOTYPE_MCDATTRIBUTE_L_NAME = "L";
    public static String STEREOTYPE_MCDATTRIBUTE_L_LIENPROG = "L";

    public static String CONSTRAINT_MCDATTRIBUTE_ORDERED_NAME = "ordered";
    public static String CONSTRAINT_MCDATTRIBUTE_ORDERED_LIENPROG = "ordered";

    public static String MCDDATATYPE_ROOT_NAME = "MCD";
    public static String MCDDATATYPE_ROOT_LIENPROG = "MCD";

    public static String MCDDATATYPE_BOOLEAN_NAME = "boolean";
    public static String MCDDATATYPE_BOOLEAN_LIENPROG = "boolean";

    public static String MCDDATATYPE_TEXT_NAME = "text";
    public static String MCDDATATYPE_TEXT_LIENPROG = "text";
    public static Integer MCDDATATYPE_TEXT_SIZEMIN = 1;
    public static String MCDDATATYPE_STRING_NAME = "string";
    public static String MCDDATATYPE_STRING_LIENPROG = "string";
    public static Integer MCDDATATYPE_STRING_SIZEMAX = 1000000000;
    public static Integer MCDDATATYPE_STRING_SIZEDEFAULT = 2000;
    public static String MCDDATATYPE_NORMALIZEDSTRING_NAME = "normalizedString";
    public static String MCDDATATYPE_NORMALIZEDSTRING_LIENPROG = "normalizedString";
    public static Integer MCDDATATYPE_NORMALIZEDSTRING_SIZEMAX = 100000;
    public static Integer MCDDATATYPE_NORMALIZEDSTRING_SIZEDEFAULT = 100;
    public static String MCDDATATYPE_TOKEN_NAME = "token";
    public static String MCDDATATYPE_TOKEN_LIENPROG = "token";
    public static Integer MCDDATATYPE_TOKEN_SIZEMAX = 10000;
    public static Integer MCDDATATYPE_TOKEN_SIZEDEFAULT = 50;
    public static String MCDDATATYPE_WORD_NAME = "word";
    public static String MCDDATATYPE_WORD_LIENPROG = "word";
    public static Integer MCDDATATYPE_WORD_SIZEMAX = 1000;
    public static Integer MCDDATATYPE_WORD_SIZEDEFAULT = 10;
    public static String MCDDATATYPE_EMAIL_NAME = "email";
    public static String MCDDATATYPE_EMAIL_LIENPROG = "email";
    public static Integer MCDDATATYPE_EMAIL_SIZEMIN = 5;
    public static Integer MCDDATATYPE_EMAIL_SIZEMAX = 200;
    public static Integer MCDDATATYPE_EMAIL_SIZEDEFAULT = 100;
    public static String MCDDATATYPE_HTTPURL_NAME = "httpURL";
    public static String MCDDATATYPE_HTTPURL_LIENPROG = "httpURL";
    public static Integer MCDDATATYPE_HTTPURL_SIZEMIN = 5;
    public static Integer MCDDATATYPE_HTTPURL_SIZEMAX = 2000;
    public static Integer MCDDATATYPE_HTTPURL_SIZEDEFAULT = 1000;
    public static String MCDDATATYPE_XML_NAME = "xml";
    public static String MCDDATATYPE_XML_LIENPROG = "xml";


    public static String MCDDATATYPE_NUMBER_NAME = "number";
    public static String MCDDATATYPE_NUMBER_LIENPROG = "number";
    public static Integer MCDDATATYPE_NUMBER_SIZEMIN = 1;

    public static String MCDDATATYPE_DECIMAL_NAME = "decimal";
    public static String MCDDATATYPE_DECIMAL_LIENPROG = "decimal";
    public static Integer MCDDATATYPE_DECIMAL_SIZEMAX = 30;
    public static Integer MCDDATATYPE_DECIMAL_SIZEDEFAULT = 10;
    public static Integer MCDDATATYPE_DECIMAL_SCALEMIN = 0;
    public static Integer MCDDATATYPE_DECIMAL_SCALEMAX = 30;
    public static Integer MCDDATATYPE_DECIMAL_SCALEDEFAULT = 2;
    public static String MCDDATATYPE_NONPOSITIVEDECIMAL_NAME = "nonPositiveDecimal";
    public static String MCDDATATYPE_NONPOSITIVEDECIMAL_LIENPROG = "nonPositiveDecimal";
    public static String MCDDATATYPE_NEGATIVEDECIMAL_NAME = "negativeDecimal";
    public static String MCDDATATYPE_NEGATIVEDECIMAL_LIENPROG = "negativeDecimal";
    public static String MCDDATATYPE_NONNEGATIVEDECIMAL_NAME = "nonNegativeDecimal";
    public static String MCDDATATYPE_NONNEGATIVEDECIMAL_LIENPROG = "nonNegativeDecimal";
    public static String MCDDATATYPE_POSITIVEDECIMAL_NAME = "postiveDecimal";
    public static String MCDDATATYPE_POSITIVEDECIMAL_LIENPROG = "positiveDecimal";

    public static String MCDDATATYPE_INTEGER_NAME = "integer";
    public static String MCDDATATYPE_INTEGER_LIENPROG = "integer";
    public static Integer MCDDATATYPE_INTEGER_SIZEMAX = 30;
    public static Integer MCDDATATYPE_INTEGER_SIZEDEFAULT = 8;
    public static String MCDDATATYPE_NONPOSITIVEINTEGER_NAME = "nonPositiveInteger";
    public static String MCDDATATYPE_NONPOSITIVEINTEGER_LIENPROG = "nonPositiveInteger";
    public static String MCDDATATYPE_NEGATIVEINTEGER_NAME = "negativeInteger";
    public static String MCDDATATYPE_NEGATIVEINTEGER_LIENPROG = "negativeInteger";
    public static String MCDDATATYPE_NONNEGATIVEINTEGER_NAME = "nonNegativeInteger";
    public static String MCDDATATYPE_NONNEGATIVEINTEGER_LIENPROG = "nonNegativeInteger";
    public static String MCDDATATYPE_POSITIVEINTEGER_NAME = "postiveInteger";
    public static String MCDDATATYPE_POSITIVEINTEGER_LIENPROG = "positiveInteger";
    public static String MCDDATATYPE_AID_NAME = "aid";
    public static String MCDDATATYPE_AID_LIENPROG = "aid";

    public static String MCDDATATYPE_MONEY_NAME = "money";
    public static String MCDDATATYPE_MONEY_LIENPROG = "money";
    public static Integer MCDDATATYPE_MONEY_SIZEMAX = 30;
    public static Integer MCDDATATYPE_MONEY_SIZEDEFAULT = 12;
    public static Integer MCDDATATYPE_MONEY_SCALEMIN = 0;
    public static Integer MCDDATATYPE_MONEY_SCALEMAX = 30;
    public static Integer MCDDATATYPE_MONEY_SCALEDEFAULT = 2;
    public static String MCDDATATYPE_NONPOSITIVEMONEY_NAME = "nonPositiveMoney";
    public static String MCDDATATYPE_NONPOSITIVEMONEY_LIENPROG = "nonPositiveMoney";
    public static String MCDDATATYPE_NEGATIVEMONEY_NAME = "negativeMoney";
    public static String MCDDATATYPE_NEGATIVEMONEY_LIENPROG = "negativeMoney";
    public static String MCDDATATYPE_NONNEGATIVEMONEY_NAME = "nonNegativeMoney";
    public static String MCDDATATYPE_NONNEGATIVEMONEY_LIENPROG = "nonNegativeMoney";
    public static String MCDDATATYPE_POSITIVEMONEY_NAME = "postiveMoney";
    public static String MCDDATATYPE_POSITIVEMONEY_LIENPROG = "positiveMoney";

    public static String MCDDATATYPE_TEMPORAL_NAME = "temporal";
    public static String MCDDATATYPE_TEMPORAL_LIENPROG = "temporal";

    public static String MCDDATATYPE_DURATION_NAME = "duration";
    public static String MCDDATATYPE_DURATION_LIENPROG = "duration";
    public static String MCDDATATYPE_DATETIME_NAME = "dateTime";
    public static String MCDDATATYPE_DATETIME_LIENPROG = "dateTime";
    public static String MCDDATATYPE_DATE_NAME = "date";
    public static String MCDDATATYPE_DATE_LIENPROG = "date";
    public static String MCDDATATYPE_TIME_NAME = "time";
    public static String MCDDATATYPE_TIME_LIENPROG = "time";
    public static String MCDDATATYPE_GYEARMONTH_NAME = "gYearMonth";
    public static String MCDDATATYPE_GYEARMONTH_LIENPROG = "gYearMonth";
    public static String MCDDATATYPE_GYEAR_NAME = "gYear";
    public static String MCDDATATYPE_GYEAR_LIENPROG = "gYear";
    public static String MCDDATATYPE_GMONTHDAY_NAME = "gMonthDay";
    public static String MCDDATATYPE_GMONTHDAY_LIENPROG = "gMonthDay";
    public static String MCDDATATYPE_GDAY_NAME = "gDay";
    public static String MCDDATATYPE_GDAY_LIENPROG = "gDay";
    public static String MCDDATATYPE_GMONTH_NAME = "gMonth";
    public static String MCDDATATYPE_GMONTH_LIENPROG = "gMonth";



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
