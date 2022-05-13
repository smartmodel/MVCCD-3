package preferences;

import connections.ConDBMode;
import console.WarningLevel;
import m.MUMLExtensionNaming;
import main.MVCCDElement;
import mdr.MDRCaseFormat;
import mdr.MDRNamingLength;
import mpdr.MPDRDBPK;
import window.editor.diagrammer.services.DiagrammerService;

import java.awt.*;

/**
 * Regroupe l'ensemble des préférences développeur (les attributs statics de la classe), des préférences d'application (les attributs qui suivent ensuite) et des préférences de projet (les attributs plus bas dans la classe). Cette classe est instanciée plusieurs fois depuis MVCCDManager. Lorsqu'une préférence doit être utilisée, il est plus simple d'utiliser la méthode statique
 * Preferences.preferences() qui se charge de retourner les bonnes préférences à utiliser en fonction du contexte de l'utilisateur (celles du projet, du profile, de l'appliation, etc.). Lorsque des préférences doivent être chargé spécifiquement pour le projet (préférences de projet) ou pour l'application (préférences d'application), il est conseillé d'utiliser
 * PreferencesManager.instance().getProjectPref(). L'utilisation de MVCCDManager.instance().getProject().getPreferences() n'est pas à privilégier. Elle est utile surtout dans le cas spécifique où le projet existe mais n'est pas encore créé dans le référentiel.
 *
 * @author PAS
 */
public class Preferences extends MVCCDElement {

  //Toutes les préférences statics sont des préférences développeurs, qui ne doivent pas être sauvegardées dans les fichiers.
  private static final long serialVersionUID = 1000;
  public static String APPLICATION_NAME = "MVCCD";
  public static String LANGUAGE = "fr";
  public static String APPLICATION_VERSION = "3.0.19"; //Format: x.y.z (x, y et z devait être des chiffres ou des nombres)
  public static Integer JPANEL_HGAP = 5;
  public static Integer JPANEL_VGAP = 5;
  public static Integer PANEL_REPOSITORY_WIDTH = 200;
  public static String REPOSITORY_PROFILE_NAME = "Profile";
  public static String REPOSITORY_PREFERENCES_PROFILE_NAME = "Préférences de profil";
  public static String REPOSITORY_PREFERENCES_NAME = "Préférences de projet";  //Project
  public static String REPOSITORY_PREFERENCES_APPLICATION_NAME = "PréférencesApplication";  //Project
  public static String REPOSITORY_MCD_MODELS_NAME = "MCD";
  public static String REPOSITORY_MCD_PACKAGE_NAME = "Paquetage";
  public static String REPOSITORY_MCD_DIAGRAMS_NAME = "Diagrammes";
  public static String REPOSITORY_MCD_ENTITIES_NAME = "Entités";
  public static String REPOSITORY_MCD_RELATIONS_NAME = "Relations";
  public static String REPOSITORY_MCD_RELATIONS_ENDS_NAME = "Extrémités de relations";
  public static String REPOSITORY_MCD_ATTRIBUTES_NAME = "Attributs";
  public static String REPOSITORY_MCD_CONSTRAINTS_NAME = "Contraintes";
  public static String REPOSITORY_MDR_TABLES_NAME = "Tables";
  public static String REPOSITORY_MDR_RELATIONS_NAME = "Relations";
  public static String REPOSITORY_MDR_TAPIS_NAME = "TAPIs";
  public static String REPOSITORY_MDR_SEQUENCES_NAME = "Séquences";
  public static String REPOSITORY_MDR_COLUMNS_NAME = "Colonnes";
  public static String REPOSITORY_MDR_CONSTRAINTS_NAME = "Contraintes";
  public static String REPOSITORY_MDR_RELENDS_NAME = "Extrémités de relations";
  public static String REPOSITORY_MLDR_MODEL_DT_NAME = "MLDR_DT";
  public static String REPOSITORY_MLDR_MODEL_TI_NAME = "MLDR_TI";
  public static String REPOSITORY_MPDR_MODEL_ORACLE_NAME = "MPDR_Oracle";
  public static String REPOSITORY_MPDR_MODEL_MYSQL_NAME = "MPDR_MySQL";
  public static String REPOSITORY_MPDR_MODEL_POSTGRESQL_NAME = "MPDR_PostgreSQL";



  public static String CON_DB_MODE_CONNECTION = "con.db.mode.connection";
  public static String CON_DB_MODE_CONNECTOR = "con.db.mode.connector";

  public static String CON_DIRECTORY_DRIVERS = "drivers";
  public static String CON_DIRECTORY_ORACLE = "oracle";
  public static String CON_DIRECTORY_MYSQL = "mysql";
  public static String CON_DIRECTORY_POSTGRESQL = "postgresql";

  public static final int CON_NAME_LENGTH = 40 ;
  public static final int CON_HOSTNAME_LENGTH = 100 ;
  public static final int CON_DBNAME_LENGTH = 40 ;
  public static final int CON_PORT_MIN = 0 ;
  public static final int CON_PORT_MAX = 6553 ;
  public static final int CON_PORT_DEFAULT_ORACLE = 1521 ;
  public static final int CON_PORT_DEFAULT_MYSQL = 3306 ;
  public static final int CON_PORT_DEFAULT_POSTGRESQL = 5432 ;
  public static final String CON_HOSTNAME_WORD = "hostName";
  public static final String CON_PORT_WORD = "port";
  public static final String CON_DBNAME_WORD = "dbName";
  public static final String CON_DBNAME_ID_MARKER = "dbNameId";  // : pour SID et / ServiceName par exemple :Oracle

  public static final String CON_URL_TEMPLATE_ORACLE = "jdbc:oracle:thin:@{"+ CON_HOSTNAME_WORD + "}:{" + CON_PORT_WORD +"}{"+CON_DBNAME_ID_MARKER+"}{"+ CON_DBNAME_WORD + "}" ;
  public static final String CON_URL_TEMPLATE_MYSQL = "jdbc:mysql://{"+ CON_HOSTNAME_WORD + "}:{" + CON_PORT_WORD +"}/{"+ CON_DBNAME_WORD + "}" ;
  public static final String CON_URL_TEMPLATE_POSTGRESQL = "jdbc:postgresql://{"+ CON_HOSTNAME_WORD + "}:{" + CON_PORT_WORD +"}/{"+ CON_DBNAME_WORD + "}" ;

  public static final String CON_FOR_NAME_ORACLE = "oracle.jdbc.driver.OracleDriver";
  public static final String CON_FOR_NAME_MYSQL = "com.mysql.jdbc.Driver";
  public static final String CON_FOR_NAME_POSTGRESQL = "org.postgresql.Driver";

  public static final String CON_IDDB_NAME_STD = "con.iddb.name.std";
  public static final String CON_IDDB_NAME_SID = "con.iddb.name.sid";
  public static final String CON_IDDB_NAME_SERVICE_NAME = "con.iddb.name.service.name";

  public static final String CON_IDDB_NAME_SID_ORACLE_MARKER = ":";
  public static final String CON_IDDB_NAME_SERVICE_NAME_ORACLE_MARKER = "/";

  public static final String DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME = "DrawPanelContainer";
  public static final String DIAGRAMMER_DRAW_PANEL_NAME = "DrawPanel";
  public static final String DIAGRAMMER_PALETTE_PANEL_NAME = "PalettePanel";
  public static final int DIAGRAMMER_MINIMUM_ALLOWED_ZOOM = 4;
  public static final int DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM = 25;
  public static final int DIAGRAMMER_DEFAULT_GRID_SIZE = 10;
  public static final int DIAGRAMMER_DEFAULT_ENTITY_POSITION_X = 60;
  public static final int DIAGRAMMER_DEFAULT_ENTITY_POSITION_Y = 60;
  public static final int DIAGRAMMER_DEFAULT_CLASS_WIDTH = 200;
  public static final int DIAGRAMMER_DEFAULT_CLASS_HEIGHT = 100;
  // Couleur de fond d'entité MCD
  public static final Color DIAGRAMMER_ENTITY_DEFAULT_BACKGROUND_COLOR = new Color(255, 255, 255);
  public static final Font DIAGRAMMER_CLASS_NAME_FONT = new Font("Arial", Font.BOLD, 13);
  public static final Font DIAGRAMMER_CLASS_FONT = new Font("Arial", Font.PLAIN, 13);
  public static final Font DIAGRAMMER_ABSTRACT_CLASS_NAME_FONT = new Font("Arial", Font.ITALIC + Font.BOLD, 13);
  public static final int DIAGRAMMER_CLASS_PADDING = 10;
  public static final String DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT = "Entité";
  public static final String DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT = "Association";
  public static final String DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT = "Généralisation";
  public static final String DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT = "Composition";
  public static final String DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT = "Association réflexive";
  public static final int DIAGRAMMER_RELATION_CLICK_AREA = 5;
  public static final String DIAGRAMMER_ENTITY_STEREOTYPE_TEXT = "<<Entity>>";
  public static final String DIAGRAMMER_ENTITY_ORDERED_TEXT = "{ordered}";
  public static final String DIAGRAMMER_MCD_ENTITY_XML_TAG = "mcdEntityShape";
  public static final String DIAGRAMMER_MCD_ASSOCIATION_XML_TAG = "mcdAssociationShape";
  public static final String DIAGRAMMER_MCD_GENERALIZATION_XML_TAG = "mcdGeneralizationShape";
  public static final String DIAGRAMMER_MCD_COMPOSITION_XML_TAG = "mcdCompositionShape";
  public static final String DIAGRAMMER_RELATION_ANCHOR_POINT_XML_TAG = "anchorPoint";
  public static final String DIAGRAMMER_LABEL_XML_TAG = "labelShape";
  public static final String DIAGRAMMER_LABELS_XML_TAG_NAME = "labelShapes";
  public static final String DIAGRAMMER_ANCHOR_POINTS_XML_TAG_NAME = "anchorPoints";

  public static Color BACKGROUND_PANEL = Color.LIGHT_GRAY;
  public static String REGEXPR_FIRSTCAR = "[a-zA-Z]{1,1}";
  public static String REGEXPR_LASTCAR = "([a-zA-Z]{1,1})$"; // pas de chiffre (indiçage) et _ (séparateur) en fin de nom
  public static String REGEXPR_NOFREE = "[a-zA-Z0-9_]*" + REGEXPR_LASTCAR;
  public static String REGEXPR_FREE = "([ ]{0,1}[a-zA-Z0-9_àâäçéèêëòöùûü]+)*";
  public static String NAME_REGEXPR = REGEXPR_FIRSTCAR + REGEXPR_NOFREE;
  public static String NAME_FREE_REGEXPR = REGEXPR_FIRSTCAR + REGEXPR_FREE;
  public static String CON_HOSTNAME_REGEXPR = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$"; //Provient de  : https://stackoverflow.com/questions/106179/regular-expression-to-match-dns-hostname-or-ip-address
  public static String CON_DBNAME_REGEXPR = "^[0-9a-zA-Z$_]+$"; //Provient de  : https://stackoverflow.com/questions/6731833/database-name-regex-restriction
  public static String REGEXPR_1_99 = "[1-9]{1}[0-9]{0,1}";
  public static String NAME_NOTMODEL_REGEXPR = "[a-zA-Z0-9_]*";


  public static String MULTI_MERISE_N = "n";
  public static String MULTI_OCL_SP = "\\.\\.";
  public static String MULTI_CUSTOM_REGEXPR = "((0|" + REGEXPR_1_99 + ")" + MULTI_OCL_SP + "(" + REGEXPR_1_99 + "|\\*|n))|1|\\*";
  public static Integer PROJECT_NAME_LENGTH = 50;
  public static Integer MODEL_NAME_LENGTH = 20;
  public static Integer MODEL_SHORT_NAME_LENGTH = 6;
  public static Integer MODEL_LONG_NAME_LENGTH = 50;
  public static Integer PACKAGE_NAME_LENGTH = 20;
  public static Integer PACKAGE_SHORT_NAME_LENGTH = 2;
  public static Integer PACKAGE_LEVEL_MAX = 2;
  public static Integer PACKAGE_LONG_NAME_LENGTH = 50;
  public static Integer ENTITY_NAME_LENGTH = 20;
  public static Integer ENTITY_SHORT_NAME_LENGTH = 6;
  public static Integer ENTITY_LONG_NAME_LENGTH = 50;
  public static Integer ENTITY_TABLE_NAME_LENGTH = ENTITY_NAME_LENGTH;
  public static Integer ATTRIBUTE_NAME_LENGTH = 26;
  public static Integer ATTRIBUTE_SHORT_NAME_LENGTH = 6;
  public static Integer ATTRIBUTE_LONG_NAME_LENGTH = 50;
  public static Integer ATTRIBUTE_PEA_NAME_LENGTH = 15;
  public static Integer ASSOCIATION_NAME_LENGTH = 20;
  public static Integer ASSEND_ROLE_SHORT_NAME_LENGTH = 6;
  public static Integer ASSEND_ROLE_NAME_LENGTH = 20;
  public static Integer ASSOCIATION_SHORT_NAME_LENGTH = ASSEND_ROLE_SHORT_NAME_LENGTH;
  public static Integer ASSOCIATION_LONG_NAME_LENGTH = 50;
  public static Integer UNIQUE_NAME_LENGTH = 20;
  public static Integer UNIQUE_NATURE_NAME_LENGTH = 6;
  public static Integer UNIQUE_SHORT_NAME_LENGTH = 6;
  public static Integer UNIQUE_LONG_NAME_LENGTH = 50;
  public static String MCD_AID_IND_COLUMN_NAME_DEFAULT = "num";
  public static String MCD_AID_DEP_COLUMN_NAME_DEFAULT = "numDep";
  public static Integer MCD_AID_COLUMN_NAME_LENGTH = 5;
  public static Integer MPDR_VIEW_NAME_LENGTH = 15;  //Calculé

  public static String INTEGER_REGEXPR = "([0])|([-]{0,1}[1-9]{1,1}[0-9]*)";
  //public static String XML_ENTETE_FICHIER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  public static String XML_ENTETE_FICHIER = "<?xml version=\"1.0\" encoding=\"windows-1252\"?>";

  public static String LIEN_PROG_SEP = "-#-";
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
  public static String FILE_HELP_PROJECT_NAME = "helpProject.txt";
  public static String FILE_HELP_PREFERENCES_NAME = "helpPreferences.txt";
  public static String FILE_HELP_MODEL_NAME = "helpModel.txt";
  public static String FILE_HELP_ENTITY_NAME = "helpEntity.txt";
  public static String FILE_HELP_ASSOCIATION_NAME = "helpAssociation.txt";
  public static String FILE_HELP_GENERALIZATION_NAME = "helpGeneralization.txt";
  public static String FILE_HELP_LINK_NAME = "helpLink.txt";
  public static String FILE_HELP_PARAMETER_NAME = "helpParameter.txt";

  public static String FILE_HELP_ANCHOR_NAME = "helpAnchor.txt";
  public static String FILE_HELP_MCDDATATYPE_NAME = "helpMCDDatatpe.txt";

  public static String FILE_CREATE_SQL_SHORT_NAME = "create";
  public static String FILE_POPULATE_SQL_SHORT_NAME = "populate";
  public static String FILE_SQL_DDL_EXTENSION = "sql";
  public static String FILE_SQL_DML_EXTENSION = "sql";


  public static String DIRECTORY_PROFILE_NAME = "profiles";
  public static String DIRECTORY_RESSOURCES_NAME = "ressources";
  public static String DIRECTORY_TEMPLATES_NAME = "template";
  public static String DIRECTORY_TEMPLATES_SQLDDL_NAME = "sql-ddl";
  public static String DIRECTORY_TEMPLATES_ORACLE_NAME = "oracle";
  public static String DIRECTORY_TEMPLATES_MYSQL_NAME = "mysql";
  public static String DIRECTORY_TEMPLATES_POSTGRESQL_NAME = "postgresql";
  public static String DIRECTORY_TEMPLATES_CREATE = "create";
  public static String DIRECTORY_TEMPLATES_ALTER = "alter";
  public static String DIRECTORY_TEMPLATES_DROP = "drop";
  public static String DIRECTORY_TEMPLATES_TRIGGERS = "triggers";
  public static String DIRECTORY_TEMPLATES_STOREDCODE = "storedCode";
  public static String DIRECTORY_TEMPLATES_DYNAMIC_CODE = "dynamicCode";
  public static String DIRECTORY_TEMPLATES_OPTIONS = "options";
  //public static String DIRECTORY_TEMPLATES_BLOCKS = "blocks";

  public static String TEMPLATE_DROP_TABLE = "dropTable.txt";
  public static String TEMPLATE_DROP_SEQUENCE = "dropSequence.txt";
  public static String TEMPLATE_DROP_TRIGGER = "dropTrigger.txt";
  public static String TEMPLATE_DROP_FUNCTION = "dropFunction.txt";
  public static String TEMPLATE_DROP_PACKAGE = "dropPackage.txt";
  public static String TEMPLATE_DROP_INDEX = "dropIndex.txt";
  public static String TEMPLATE_DROP_VIEW = "dropView.txt";

  public static String TEMPLATE_CREATE_TABLE = "createTable.txt";
  public static String TEMPLATE_CREATE_TABLE_COLUMNS = "createTableColumns.txt";
  public static String TEMPLATE_CREATE_TABLE_COLUMN_IDENTITY = "createTableColumnIdentity.txt";
  public static String TEMPLATE_CREATE_TABLE_PK = "createTablePK.txt";
  public static String TEMPLATE_CREATE_TABLE_CHECKS = "createTableChecks.txt";
  public static String TEMPLATE_CREATE_TABLE_UNIQUES = "createTableUniques.txt";
  public static String TEMPLATE_CREATE_SEQUENCE = "createSequence.txt";
  public static String TEMPLATE_CREATE_INDEX = "createIndex.txt";
  public static String TEMPLATE_CREATE_INDEX_COLUMNS = "createIndexColumns.txt";
  public static String TEMPLATE_OPTION_COLUMN_IDENTITY = "columnIdentity.txt";
  public static String TEMPLATE_OPTION_COLUMN_NOTNULL =  "columnNotNull.txt";
  public static String TEMPLATE_OPTION_COLUMN_DEFAULTVALUE =  "columnDefaultValue.txt";

  //public static String TEMPLATE_BLOCK_NODML =  "nodml.txt";


  public static String TEMPLATES_ALTER_TABLE_FK = "alterTableFK.txt";

  public static String TEMPLATES_DROP_EMPTY_SCHEMA = "emptySchema.txt";
  public static String TEMPLATES_DROP_EXECUTE_EMPTY_SCHEMA = "executeEmptySchema.txt";
  public static String SQL_SEPARATOR_ARGUMENTS = ",";
  public static String SQL_MARKER_SEPARATOR_ARGUMENTS = "__separatorArguments";
  public static String SQL_MARKER_NAMESPACE = ".";

  public static String SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator");
  public static String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");
  public static String CHARSET_FILES_TEXT = "UTF-8";

  public static String LOGGING_FILE_NAME_FORMAT = "yyyy-MM-dd";
  public static String LOGGING_FILE_NAME_EXTENSION = "log";
  public static String LOGGING_FILE_NAME_FORMAT_ERROR_NULL = "Le format de nommage du fichier de log est inexistant";
  public static String DIRECTORY_LOGGING_NAME = "logs";

    // Path de nommage au niveau des objets  (Modèle.paquetatge.entité....
    public static String PATH_NAMING_SEPARATOR = ".";
    // Séparation entre conteneur de modèle et nommage d'association qui contient déjà des séparateurs "."
    public static String PATH_NAMING_RELATION_SEPARATOR = "!";

    public static Integer PANEL_BUTTONS_MESSAGES_HEIGHT = 50;
    public static Integer MVCCD_WINDOW_WIDTH = 1200;
    public static Integer MVCCD_WINDOW_HEIGHT = 900; // Limité à 900 pour l'utilisation portable
    public static Integer NAMING_WINDOW_WIDTH = 1200;
    public static Integer NAMING_WINDOW_HEIGHT = 900; // Limité à 900 pour l'utilisation portable

    public static Integer PROJECT_WINDOW_WIDTH = 800;
    public static Integer PROJECT_WINDOW_HEIGHT = 400;
    public static Integer MODEL_WINDOW_WIDTH = 800;
    public static Integer MODEL_WINDOW_HEIGHT = 600;
    public static Integer ENTITY_WINDOW_WIDTH = 800;
    public static Integer ENTITY_WINDOW_HEIGHT = 600;
    public static Integer ATTRIBUTE_WINDOW_WIDTH = 800;
    public static Integer ATTRIBUTE_WINDOW_HEIGHT = 600;
    public static Integer ASSOCIATION_WINDOW_WIDTH = 900; // Limité à 900 pour l'utilisation portable
    public static Integer ASSOCIATION_WINDOW_HEIGHT = 600;
    public static Integer UNICITY_WINDOW_WIDTH = 900; // Limité à 900 pour l'utilisation portable
    public static Integer UNIQUE_WINDOW_HEIGHT = 600;
    public static Integer PARAMETER_WINDOW_WIDTH = 400;
    public static Integer PARAMETER_WINDOW_HEIGHT = 300;
    public static Integer ANCHOR_WINDOW_WIDTH = 800;
    public static Integer ANCHOR_WINDOW_HEIGHT = 600;
    public static Integer GENSPEC_WINDOW_WIDTH = 800;
    public static Integer GENSPEC_WINDOW_HEIGHT = 600;
    public static Integer LINK_WINDOW_WIDTH = 800;
    public static Integer LINK_WINDOW_HEIGHT = 600;
    public static Integer HELP_WINDOW_WIDTH = 600;
    public static Integer HELP_WINDOW_HEIGHT = 300;
    public static Integer PREFERENCES_WINDOW_WIDTH = 900; // Limité à 900 pour l'utilisation portable
    public static Integer PREFERENCES_WINDOW_HEIGHT = 600;
    public static Integer MCDDATATYPE_WINDOW_WIDTH = 800;
    public static Integer MCDDATATYPE_WINDOW_HEIGHT = 600;
    public static Integer CON_CONNECTION_WINDOW_WIDTH = 1000;
    public static Integer CON_CONNECTION_WINDOW_HEIGHT = 800;
    public static Integer CON_CONNECTOR_WINDOW_WIDTH = 1000;
    public static Integer CON_CONNECTOR_WINDOW_HEIGHT = 600;
   public static Integer GENERATOR_SQL_WINDOW_WIDTH = 1000;
  public static Integer GENERATOR_SQL_WINDOW_HEIGHT = 1000;
  public static Integer TEXTEDITOR_WINDOW_WIDTH = 600;
  public static Integer TEXTEDITOR_SQL_WINDOW_HEIGHT = 600;

  public static Integer MDRTABLE_WINDOW_WIDTH = 900; // Limité à 900 pour l'utilisation portable
    public static Integer MDRTABLE_WINDOW_HEIGHT = 600;

  public static Integer EDITOR_FIELD_HEIGHT = 20;
  public static Integer EDITOR_TABLE_ROW_HEIGHT = 20;

  public static Color EDITOR_SCOMPONENT_BACKGROUND_NORMAL = Color.WHITE;
  public static Color EDITOR_SCOMPONENT_BACKGROUND_ERROR = Color.pink;
  public static Color EDITOR_SCOMPONENT_LINEBORDER_NORMAL = Color.gray;
  public static Color EDITOR_SCOMPONENT_LINEBORDER_ERROR = Color.RED;

  public static Integer EDITOR_CONSTRAINTS_STEREO_WIDTH = 80;
  public static Integer EDITOR_CONSTRAINTS_NAME_WIDTH = 150;
  public static Integer EDITOR_CONSTRAINTS_UMLCONSTRAINTS_WIDTH = 150;

  public static Color SCOMPONENT_INDIRECT_INPUT_FOREGROUND = Color.RED;

  public static String STEREOTYPE_SEPARATOR = "-";

    public static String STEREOTYPE_ENTITY_NAME = "Entity";
    public static String STEREOTYPE_ENTITY_LIENPROG = "Entity";
  public static String STEREOTYPE_TABLE_NAME = "Table";
  public static String STEREOTYPE_TABLE_LIENPROG = "Table";
  public static String STEREOTYPE_VIEW_NAME = "View";
  public static String STEREOTYPE_VIEW_LIENPROG = "View";
  public static String STEREOTYPE_SEQUENCE_NAME = "Sequence";
  public static String STEREOTYPE_SEQUENCE_LIENPROG = "Sequence";
  public static String STEREOTYPE_TRIGGERS_NAME = "Triggers";
  public static String STEREOTYPE_TRIGGERS_LIENPROG = "Triggers";
  public static String STEREOTYPE_PROCEDURES_NAME = "Procedures";
  public static String STEREOTYPE_PROCEDURES_LIENPROG = "Procedures";
  public static String STEREOTYPE_PACKAGES_NAME = "Paquetages";
  public static String STEREOTYPE_PACKAGES_LIENPROG = "Paquetages";
  public static String STEREOTYPE_ORACLE_NAME = "Oracle";
    public static String STEREOTYPE_ORACLE_LIENPROG = "Oracle";
    public static String STEREOTYPE_MYSQL_NAME = "MySQL";
    public static String STEREOTYPE_MYSQL_LIENPROG = "MySQL";
    public static String STEREOTYPE_POSTGRESQL_NAME = "PostgreSQL";
    public static String STEREOTYPE_POSTGRESQL_LIENPROG = "PostgreSQL";

    public static String STEREOTYPE_AID_NAME = "AID";
    public static String STEREOTYPE_AID_LIENPROG = "AID";
    public static String STEREOTYPE_M_NAME = "M";
    public static String STEREOTYPE_M_LIENPROG = "M";
    public static String STEREOTYPE_L_NAME = "L";
    public static String STEREOTYPE_L_LIENPROG = "L";
    public static String STEREOTYPE_LP_NAME = "LP";
    public static String STEREOTYPE_LP_LIENPROG = "LP";

  public static String STEREOTYPE_CID_NAME = "CID";
  public static String STEREOTYPE_CID_LIENPROG = "CID";

  public static int STEREOTYPE_NID_MAX = 5;
  public static String STEREOTYPE_NID_NAME = "NID";
  public static String STEREOTYPE_NID_LIENPROG = "NID";

  public static String STEREOTYPE_CP_NAME = "CP";
  public static String STEREOTYPE_CP_LIENPROG = "CP";

  public static int STEREOTYPE_U_MAX = 10;
  public static String STEREOTYPE_U_NAME = "U";
  public static String STEREOTYPE_U_LIENPROG = "U";

    public static String STEREOTYPE_PK_NAME = "PK";
    public static String STEREOTYPE_PK_LIENPROG = "PK";

    public static int STEREOTYPE_FK_MAX = 20;
    public static String STEREOTYPE_FK_NAME = "FK";
    public static String STEREOTYPE_FK_LIENPROG = "FK";

    public static int STEREOTYPE_PFK_MAX = 20;  // Même indiçage que FK !
    public static String STEREOTYPE_PFK_NAME = "PFK";
    public static String STEREOTYPE_PFK_LIENPROG = "PFK";


    public static String STEREOTYPE_CHECK_NAME = "CHK";
    public static String STEREOTYPE_CHECK_LIENPROG = "CHK";
    public static String STEREOTYPE_INDEX_NAME = "IDX";
    public static String STEREOTYPE_INDEX_LIENPROG = "IDX";

  public static String STEREOTYPE_SPECIALIZED_NAME = "SPEC";
  public static String STEREOTYPE_SPECIALIZED_LIENPROG = "SPEC";


  public static String CONSTRAINT_ORDERED_NAME = "ordered";
    public static String CONSTRAINT_ORDERED_LIENPROG = "ordered";
    public static String CONSTRAINT_FROZEN_NAME = "frozen";
    public static String CONSTRAINT_FROZEN_LIENPROG = "frozen";
    public static String CONSTRAINT_DELETECASCADE_NAME = "deletecascade";
    public static String CONSTRAINT_DELETECASCADE_LIENPROG = "deletecascade";
    public static String CONSTRAINT_ORIENTED_NAME = "oriented";
    public static String CONSTRAINT_ORIENTED_LIENPROG = "oriented";
    public static String CONSTRAINT_NONORIENTED_NAME = "nonoriented";
    public static String CONSTRAINT_NONORIENTED_LIENPROG = "nonoriented";

  public static String CONSTRAINT_ABSOLUTE_NAME = "absolute";
  public static String CONSTRAINT_ABSOLUTE_LIENPROG = "absolute";


    public static String MCDDATATYPE_ROOT_NAME = "MCD";
    public static String MCDDATATYPE_ROOT_LIENPROG = "MCD";

  public static String MCDDATATYPE_BOOLEAN_NAME = "boolean";
  public static String MCDDATATYPE_BOOLEAN_LIENPROG = "boolean";

  //ATTENTION, en cas de changement de valeurs de tailles possibles, il est nécessaire d'adapter les contrôles en conséquences dans SchemaProject.xsd, qui vérifie les exports/imports de projet sauvegardé.
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
  public static Integer MCDDATATYPE_DECIMAL_SCALEMAX = 10;
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

  public static String MCDDOMAIN_AID_NAME = "aid";
  public static String MCDDOMAIN_AID_LIENPROG = "aid";
  public static Integer MCDDOMAIN_AID_SIZEMIN = 3;
  public static Integer MCDDOMAIN_AID_SIZEMAX = 12;
  public static Integer MCDDOMAIN_AID_SIZEDEFAULT = 9;

  public static String MCDDATATYPE_MONEY_NAME = "money";
  public static String MCDDATATYPE_MONEY_LIENPROG = "money";
  public static Integer MCDDATATYPE_MONEY_SIZEMAX = 30;
  public static Integer MCDDATATYPE_MONEY_SIZEDEFAULT = 12;
  public static Integer MCDDATATYPE_MONEY_SCALEMIN = 0;
  public static Integer MCDDATATYPE_MONEY_SCALEMAX = 10;
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

  public static String MCDDATATYPE_NUMBER_SIZE_PRECISION = "mcddatatype.number.size.precision";
  public static String MCDDATATYPE_NUMBER_SIZE_INTEGER_PORTION_ONLY = "mcddatatype.number.size.integer.portion.only";


    public static String MCD_NAMING_NAME="mcd.naming.name";
    public static String MCD_NAMING_SHORT_NAME="mcd.naming.short.name";

  public static String MCD_NAMING_ASSOCIATION_SEPARATOR = " ... ";
  public static String MCD_NAMING_ASSOCIATION_ARROW_RIGHT = " -> ";
  public static String MCD_NAMING_ASSOCIATION_ARROW_LEFT = " <- ";
  public static String MCD_NAMING_GENERALIZATION = " <|- ";
  public static String MCD_NAMING_LINK = " []--| ";

  public static String MCD_NAMING_GENERALIZATION_SPECIALIZE = " <|- ";
  public static String MCD_NAMING_GENERALIZATION_GENERALIZE = " -|> ";
  public static String MCD_NAMING_LINK_ELEMENT = " |--[] ";
  public static String MCD_NAMING_LINK_ASSOCIATION = " []--| ";

    public static String DIRECTORY_IMAGE_ICONE_RELATION = "images" + SYSTEM_FILE_SEPARATOR + "icones" + SYSTEM_FILE_SEPARATOR + "relations";
    public static String ICONE_RELATION_GENERALIZATION_LEFT_LG = "GeneralizationLeft.png";
    public static String ICONE_RELATION_GENERALIZATION_RIGHT_LG = "GeneralizationRight.png";
    public static String ICONE_RELATION_ASS_NONID_LG = "AssNonId.png";
    public static String ICONE_RELATION_ASS_ID_COMP_LEFT_LG = "AssIdCompLeft.png";
    public static String ICONE_RELATION_ASS_ID_COMP_RIGHT_LG = "AssIdCompRight.png";
    public static String ICONE_RELATION_ASS_ID_COMP_STEREO_LG = "AssIdCompStereo.png";
    public static String ICONE_RELATION_ASS_ID_NAT_LEFT_LG = "AssIdNatLeft.png";
    public static String ICONE_RELATION_ASS_ID_NAT_RIGHT_LG = "AssIdNatRight.png";
    public static String ICONE_RELATION_ASS_ID_NAT_STEREO_LG = "AssIdNatStereo.png";
    public static String ICONE_RELATION_LINK_LEFT_LG = "LinkLeft.png";
    public static String ICONE_RELATION_LINK_RIGHT_LG = "LinkRight.png";

  public static String GENERAL_RELATION_NOTATION_UML = "uml";
  public static String GENERAL_RELATION_NOTATION_STEREOTYPES = "stereotypes";

  public static String OPTION_ALWAYS = "option.always";
  public static String OPTION_NEVER = "option.never";
  public static String OPTION_YES = "option.yes";
  public static String OPTION_NO = "option.no";
  public static String OPTION_FREE = "option.free";
  public static String OPTION_FREE_UNIQUE = "option.free.unique";

  public static String MCD_MODE_NAMING_SHORT_NAME = OPTION_YES;

  public static String MCD_ENTITY_NATURE_IND = "mcd.entity.nature.ind";
  public static String MCD_ENTITY_NATURE_DEP = "mcd.entity.nature.dep";
  public static String MCD_ENTITY_NATURE_ENTASS = "mcd.entity.nature.entass";
  public static String MCD_ENTITY_NATURE_ENTASSDEP = "mcd.entity.nature.entassdep";
  public static String MCD_ENTITY_NATURE_NAIRE = "mcd.entity.nature.naire";
  public static String MCD_ENTITY_NATURE_NAIREDEP = "mcd.entity.nature.nairedep";
  public static String MCD_ENTITY_NATURE_SPEC = "mcd.entity.nature.spec";
  public static String MCD_ENTITY_NATURE_PSEUDOENTASS = "mcd.entity.nature.pseudoentass";

  public static String MCD_ASSOCIATION_NATURE_NO_ID = "mcd.association.nature.no.id";
  public static String MCD_ASSOCIATION_NATURE_ID_NATURAL = "mcd.association.nature.no.natural";
  public static String MCD_ASSOCIATION_NATURE_ID_COMP = "mcd.association.nature.no.comp";
  public static String MCD_ASSOCIATION_NATURE_SIM_CP = "mcd.association.nature.sim.cp";

  public static String MCD_UNIQUE_NATURE_NO_ID = "mcd.unique.nature.no.id";
  public static String MCD_UNIQUE_NATURE_NID = "mcd.unique.nature.nid";

  public static String PREFERENCES_PROJECT_MENU_GENERAL = "preferences.project.menu.general";
  public static String PREFERENCES_PROJECT_MENU_MCD = "preferences.project.menu.mcd";
  public static String PREFERENCES_PROJECT_MENU_MDR = "preferences.project.menu.mdr";
  public static String PREFERENCES_PROJECT_MENU_MLDR = "preferences.project.menu.mldr";
  public static String PREFERENCES_PROJECT_MENU_MDR_FORMAT = "preferences.project.menu.mdr.format";
  public static String PREFERENCES_PROJECT_MENU_MCD_TO_MLDR = "preferences.project.menu.mcd.to.mldr";
  public static String PREFERENCES_PROJECT_MENU_MLDR_TO_MPDR = "preferences.project.menu.mldr.to.mpdr";
  public static String PREFERENCES_PROJECT_MENU_MPDR = "preferences.project.menu.mpdr";
  public static String PREFERENCES_PROJECT_MENU_MPDR_ORACLE = "preferences.project.menu.mpdr.oracle";
  public static String PREFERENCES_PROJECT_MENU_MPDR_MYSQL = "preferences.project.menu.mpdr.mysql";
  public static String PREFERENCES_PROJECT_MENU_MPDR_POSTGRESQL = "preferences.project.menu.mpdr.postgresql";

  public static String PARAMETERS_SEPARATOR = ", ";

  public static String MCDTOMLDR_MODE_DT = "mcdtomldr.mode.dt";
  public static String MCDTOMLDR_MODE_TI = "mcdtomldr.mode.ti";

  public static String MDR_UNIQUE_NATURE_UNIQUE = "mdr.unique.nature.unique";
  public static String MDR_UNIQUE_NATURE_NID = "mdr.unique.nature.nid";
  public static String MDR_UNIQUE_NATURE_LP = "mdr.unique.nature.lp";
  public static String MDR_UNIQUE_NATURE_SIMPK = "mdr.unique.nature.simpk";
  public static String MDR_UNIQUE_NATURE_SIMCP = "mdr.unique.nature.simcp";
  public static String MDR_UNIQUE_NATURE_FKMAXONECHILD = "mdr.unique.nature.fkmaxonechild";

  public static String MDR_CONSTRAINT_CUSTOM_SPECIALIZED = "mdr.constraint.custom.specialized";
  public static String MDR_CONSTRAINT_CUSTOM_AUDIT = "mdr.unique.nature.unique";
  public static String MDR_CONSTRAINT_CUSTOM_JNAL = "mdr.unique.nature.unique";



  public static String DB_ORACLE = "db.oracle";
  public static String DB_MYSQL = "db.mysql";
  public static String DB_POSTGRESQL = "db.postgresql";

  public static String DB_PK_SEQUENCE = "db.pk.sequence";
  public static String DB_PK_IDENTITY = "db.pk.identity";

  public static String MPDR_SEQUENCE_PK = "mpdr.sequence.pk";
  public static String MPDR_CHECK_DATATYPE = "mpdr.check.datatype";
  public static String MPDR_FK_INDEX = "mpdr.fk.index";

  public static String MLDRDATATYPE_ROOT_NAME = "MLD-R";
  public static String MLDRDATATYPE_ROOT_LIENPROG = "MLD-R";
  public static String MLDRDATATYPE_BOOLEAN_NAME = "BOOLEAN";
  public static String MLDRDATATYPE_BOOLEAN_LIENPROG = "BOOLEAN";
  public static String MLDRDATATYPE_VARCHAR_NAME = "VARCHAR";
  public static String MLDRDATATYPE_VARCHAR_LIENPROG = "VARCHAR";
  public static String MLDRDATATYPE_NUMERIC_NAME = "NUMERIC";
  public static String MLDRDATATYPE_NUMERIC_LIENPROG = "NUMERIC";
  public static String MLDRDATATYPE_TEMPORAL_NAME = "TEMPORAL";
  public static String MLDRDATATYPE_TEMPORAL_LIENPROG = "TEMPORAL";
  public static String MLDRDATATYPE_INTERVAL_NAME = "INTERVAL";
  public static String MLDRDATATYPE_INTERVAL_LIENPROG = "INTERVAL";
  public static String MLDRDATATYPE_YEAR_NAME = "YEAR";
  public static String MLDRDATATYPE_YEAR_LIENPROG = "YEAR";
  public static String MLDRDATATYPE_DATETIME_NAME = "DATETIME";
  public static String MLDRDATATYPE_DATETIME_LIENPROG = "DATETIME";
  public static String MLDRDATATYPE_TIMESTAMP_NAME = "TIMESTAMP";
  public static String MLDRDATATYPE_TIMESTAMP_LIENPROG = "TIMESTAMP";
  public static String MLDRDATATYPE_DATE_NAME = "DATE";
  public static String MLDRDATATYPE_DATE_LIENPROG = "DATE";
  public static String MLDRDATATYPE_TIME_NAME = "TIME";
  public static String MLDRDATATYPE_TIME_LIENPROG = "TIME";

  public static String MPDRORACLEDATATYPE_ROOT_NAME = "MPDR-Oracle";
  public static String MPDRORACLEDATATYPE_ROOT_LIENPROG = "MPDR-Oracle";
  public static String MPDRORACLEDATATYPE_VARCHAR2_NAME = "VARCHAR2";
  public static String MPDRORACLEDATATYPE_VARCHAR2_LIENPROG = "VARCHAR2";
  public static String MPDRORACLEDATATYPE_NUMBER_NAME = "NUMBER";
  public static String MPDRORACLEDATATYPE_NUMBER_LIENPROG = "NUMBER";
  public static String MPDRORACLEDATATYPE_TEMPORAL_NAME = "TEMPORAL";
  public static String MPDRORACLEDATATYPE_TEMPORAL_LIENPROG = "TEMPORAL";
  public static String MPDRORACLEDATATYPE_TIMESTAMP_NAME = "TIMESTAMP";
  public static String MPDRORACLEDATATYPE_TIMESTAMP_LIENPROG = "TIMESTAMP";
  public static String MPDRORACLEDATATYPE_DATE_NAME = "DATE";
  public static String MPDRORACLEDATATYPE_DATE_LIENPROG = "DATE";
  public static String MPDRORACLE_DELIMITER_INSTRUCTIONS_DEFAULT = "\\\\";
  public static MPDRDBPK MPDRORACLE_PK_GENERATE_DEFAULT = MPDRDBPK.SEQUENCE;
  public static String MPDRORACLE_SEQPK_NAME_FORMAT_DEFAULT = "{tableShortName}{_SEQPK}";
  public static String MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typeTriggerMarker}";
  public static String MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT_DEFAULT = "{viewName}{tableSep}{typeTriggerMarker}";
  public static String MPDRORACLE_PACKAGE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typePackageMarker}";
  public static String MPDRORACLE_VIEW_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typeViewMarker}";
  public static String MPDRORACLE_CHECK_COLUMN_DATATYPE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{mpdrColumnName}{_DTY}";
  public static String MPDRORACLE_CHECK_COLUMN_DATATYPE_MAX30_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{mcdAttrShortName}{_DTY}";
  public static String MPDRORACLE_EMPTY_SCHEMA = "empty_schema";
  public static String MPDRORACLE_RECORD_NEW = ":NEW";
  public static String MPDRORACLE_RECORD_OLD = ":OLD";

  public static String MPDRMySQLDATATYPE_ROOT_NAME = "MPDR-MySQL";
  public static String MPDRMySQLDATATYPE_ROOT_LIENPROG = "MPDR-MySQL";
  public static String MPDRMySQLDATATYPE_TEXT_NAME = "TEXT";
  public static String MPDRMySQLDATATYPE_TEXT_LIENPROG = "TEXT";
  public static String MPDRMySQLDATATYPE_VARCHAR_NAME = "VARCHAR";
  public static String MPDRMySQLDATATYPE_VARCHAR_LIENPROG = "VARCHAR";
  public static String MPDRMySQLDATATYPE_NUMERIC_NAME = "NUMERIC";
  public static String MPDRMySQLDATATYPE_NUMERIC_LIENPROG = "NUMERIC";
  public static String MPDRMySQLDATATYPE_TINYINT_NAME = "TINYINT";
  public static String MPDRMySQLDATATYPE_TINYINT_LIENPROG = "TINYINT";
  public static String MPDRMySQLDATATYPE_DECIMAL_NAME = "DECIMAL";
  public static String MPDRMySQLDATATYPE_DECIMAL_LIENPROG = "DECIMAL";
  public static String MPDRMySQLDATATYPE_BIGINT_NAME = "BIGINT";
  public static String MPDRMySQLDATATYPE_BIGINT_LIENPROG = "BIGINT";
  public static String MPDRMySQLDATATYPE_INT_NAME = "INT";
  public static String MPDRMySQLDATATYPE_INT_LIENPROG = "INT";
  public static String MPDRMySQLDATATYPE_TEMPORAL_NAME = "TEMPORAL";
  public static String MPDRMySQLDATATYPE_TEMPORAL_LIENPROG = "TEMPORAL";
  public static String MPDRMySQLDATATYPE_DATE_NAME = "DATE";
  public static String MPDRMySQLDATATYPE_DATE_LIENPROG = "DATE";
  public static String MPDRMySQLDATATYPE_DATETIME_NAME = "DATETIME";
  public static String MPDRMySQLDATATYPE_DATETIME_LIENPROG = "DATETIME";
  public static String MPDRMySQLDATATYPE_TIMESTAMP_NAME = "TIMESTAMP";
  public static String MPDRMySQLDATATYPE_TIMESTAMP_LIENPROG = "TIMESTAMP";
  public static String MPDRMySQLDATATYPE_TIME_NAME = "TIME";
  public static String MPDRMySQLDATATYPE_TIME_LIENPROG = "TIME";
  public static String MPDRMySQLDATATYPE_YEAR_NAME = "YEAR";
  public static String MPDRMySQLDATATYPE_YEAR_LIENPROG = "YEAR";
  public static String MPDRMYSQL_DELIMITER_INSTRUCTIONS_DEFAULT = "\\\\";
  public static MPDRDBPK MPDRMYSQL_PK_GENERATE_DEFAULT = MPDRDBPK.IDENTITY;
  public static String MPDRMYSQL_SEQPK_NAME_FORMAT_DEFAULT = "{tableShortName}{_SEQPK}";
  public static String MPDRMYSQL_CHECK_COLUMN_DATATYPE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{mpdrColumnName}{_DTY}";
  public static String MPDRMYSQL_CHECK_COLUMN_DATATYPE_MAX30_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{mcdAttrShortName}{_DTY}";
  public static String MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typeTriggerMarker}";
  public static String MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT_DEFAULT = "{viewName}{tableSep}{typeTriggerMarker}";
  public static String MPDRMYSQL_VIEW_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typeViewMarker}";
  public static String MPDRMYSQL_RECORD_NEW = "NEW";
  public static String MPDRMYSQL_RECORD_OLD = "OLD";


  public static String MPDRPOSTGRESQLDATATYPE_ROOT_NAME = "MPDR-PostgreSQL";
  public static String MPDRPOSTGRESQLDATATYPE_ROOT_LIENPROG = "MPDR-PostgreSQL";
  public static String MPDRPOSTGRESQLDATATYPE_BOOLEAN_NAME = "BOOLEAN";
  public static String MPDRPOSTGRESQLDATATYPE_BOOLEAN_LIENPROG = "BOOLEAN";
  public static String MPDRPOSTGRESQLDATATYPE_TEXT_NAME = "TEXT";
  public static String MPDRPOSTGRESQLDATATYPE_TEXT_LIENPROG = "TEXT";
  public static String MPDRPOSTGRESQLDATATYPE_VARCHAR_NAME = "VARCHAR";
  public static String MPDRPOSTGRESQLDATATYPE_VARCHAR_LIENPROG = "VARCHAR";
  public static String MPDRPOSTGRESQLDATATYPE_NUMERIC_NAME = "NUMERIC";
  public static String MPDRPOSTGRESQLDATATYPE_NUMERIC_LIENPROG = "NUMERIC";
  public static String MPDRPOSTGRESQLDATATYPE_SMALLINT_NAME = "SMALLINT";
  public static String MPDRPOSTGRESQLDATATYPE_SMALLINT_LIENPROG = "SMALLINT";
  public static String MPDRPOSTGRESQLDATATYPE_INTEGER_NAME = "INTEGER";
  public static String MPDRPOSTGRESQLDATATYPE_INTEGER_LIENPROG = "INTEGER";
  public static String MPDRPOSTGRESQLDATATYPE_BIGINT_NAME = "BIGINT";
  public static String MPDRPOSTGRESQLDATATYPE_BIGINT_LIENPROG = "BIGINT";
  public static String MPDRPOSTGRESQLDATATYPE_TEMPORAL_NAME = "TEMPORAL";
  public static String MPDRPOSTGRESQLDATATYPE_TEMPORAL_LIENPROG = "TEMPORAL";
  public static String MPDRPOSTGRESQLDATATYPE_INTERVAL_NAME = "INTERVAL";
  public static String MPDRPOSTGRESQLDATATYPE_INTERVAL_LIENPROG = "INTERVAL";
  public static String MPDRPOSTGRESQLDATATYPE_DATE_NAME = "DATE";
  public static String MPDRPOSTGRESQLDATATYPE_DATE_LIENPROG = "DATE";
  public static String MPDRPOSTGRESQLDATATYPE_TIMESTAMP_NAME = "TIMESTAMP";
  public static String MPDRPOSTGRESQLDATATYPE_TIMESTAMP_LIENPROG = "TIMESTAMP";
  public static String MPDRPOSTGRESQLDATATYPE_TIME_NAME = "TIME";
  public static String MPDRPOSTGRESQLDATATYPE_TIME_LIENPROG = "TIME";
  public static String MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS_DEFAULT = "\\\\";
  public static MPDRDBPK MPDRPOSTGRESQL_PK_GENERATE_DEFAULT = MPDRDBPK.SEQUENCE;
  public static String MPDRPOSTGRESQL_SEQPK_NAME_FORMAT_DEFAULT = "{tableShortName}{_SEQPK}";
  public static String MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typeTriggerMarker}";
  public static String MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT_DEFAULT = "{viewName}{tableSep}{typeTriggerMarker}";
  public static String MPDRPOSTGRESQL_VIEW_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{typeViewMarker}";
  public static String MPDRPOSTGRESQL_CHECK_COLUMN_DATATYPE_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{mpdrColumnName}{_DTY}";
  public static String MPDRPOSTGRESQL_CHECK_COLUMN_DATATYPE_MAX30_NAME_FORMAT_DEFAULT = "{tableShortName}{tableSep}{mcdAttrShortName}{_DTY}";
  public static String MPDRPOSTGRESQL_EMPTY_SCHEMA = "empty_schema";
  public static String MPDRPOSTGRESQL_RECORD_NEW = "NEW";
  public static String MPDRPOSTGRESQL_RECORD_OLD = "OLD";


  public static String MDR_TABLE_NATURE_IND = "mdr.table.nature.ind";
  public static String MDR_TABLE_NATURE_DEP = "mdr.table.nature.dep";
  public static String MDR_TABLE_NATURE_ASS = "mdr.table.nature.ass";
  public static String MDR_TABLE_NATURE_ASSDEP = "mdr.table.nature.assdep";
  public static String MDR_TABLE_NATURE_NAIRE = "mdr.table.nature.naire";
  public static String MDR_TABLE_NATURE_NAIREDEP = "mdr.table.nature.nairedep";
  public static String MDR_TABLE_NATURE_SPEC = "mdr.table.nature.spec";
  

  public static String MDR_NAMING_LENGTH_30 = "mdr.naming.length.30";
  public static String MDR_NAMING_LENGTH_60 = "mdr.naming.length.60";
  public static String MDR_NAMING_LENGTH_120 = "mdr.naming.length.120";

  public static String MDR_NAMING_FORMAT_NOTHING = "mdr.naming.format.nothing"; //Attention, si cette valeur est changée, elle doit également être changée dans ProjectLoaderXML.
  public static String MDR_NAMING_FORMAT_UPPERCASE = "mdr.naming.format.uppercase"; //Idem
  public static String MDR_NAMING_FORMAT_LOWERCASE = "mdr.naming.format.lowercase"; //Idem
  public static String MDR_NAMING_FORMAT_CAPITALIZE = "mdr.naming.format.capitalize"; //Idem
  public static String MPDR_NAMING_FORMAT_LIKEDB = "mpdr.naming.format.likedb"; //Idem

  public static String MDR_NAMING_TABLE = "mdr.table";
    public static String MDR_NAMING_TABLE_NN = "mdr.table.nn";
    public static String MDR_NAMING_TABLE_NN_INDICE = "mdr.table.nn.indice";
    public static String MDR_NAMING_COLUMN_ATTR = "mdr.column.attr";
    public static String MDR_NAMING_COLUMN_PK = "mdr.column.pk";
    public static String MDR_NAMING_COLUMN_FK = "mdr.column.fk";
    public static String MDR_NAMING_PK = "mdr.pk";
    public static String MDR_NAMING_PK_NN = "mdr.pk.nn";
    public static String MDR_NAMING_PK_NN_INDICE = "mdr.pk.nn.indice";
    public static String MDR_NAMING_FK = "mdr.fk";
    public static String MDR_NAMING_UNIQUE = "mdr.unique";
  public static String MPDR_NAMING_SEQUENCE_PK = "mpdr.sequence.pk";
  public static String MPDR_NAMING_CHECK_COLUMNDATATYPE = "mpdr.check.datatype";
  public static String MPDR_NAMING_CHECK_COLUMNDATATYPE_MAX30 = "mpdr.check.datatype.max30";
  public static String MPDR_NAMING_TRIGGER_TABLE = "mpdr.trigger.table";
  public static String MPDR_NAMING_TRIGGER_VIEW = "mpdr.trigger.view";
  public static String MPDR_NAMING_FUNCTION = "mpdr.function";
  public static String MPDR_NAMING_PACKAGE = "mpdr.package";
  public static String MPDR_NAMING_VIEW = "mpdr.view";

  public static String MDR_PATH_WORD = "path";
  public static String MDR_PATH_SEP_WORD = "pathSep";
  public static String MDR_TABLE_NAME_WORD = "tableName";
  public static String MDR_TABLE_PK_NAME_WORD = "tablePKName";
  public static String MDR_TABLE_FK_NAME_WORD = "tableFKName";
  public static String MDR_TABLE_ORIGIN_NAME_WORD = "tableOriginName";
  public static String MDR_TABLE_SHORT_NAME_WORD = "tableShortName";
  public static String MDR_TABLE_SHORT_NAME_A_WORD = "ATableShortName";
  public static String MDR_TABLE_SHORT_NAME_B_WORD = "BTableShortName";
  public static String MDR_TABLE_SHORT_NAME_PARENT_WORD = "parentTableShortName";
  public static String MDR_TABLE_SHORT_NAME_CHILD_WORD = "childTableShortName";
  public static String MDR_TABLE_NAME_PARENT_WORD = "parentTableName";
  public static String MDR_TABLE_NAME_CHILD_WORD = "childTableName";
  public static String MDR_FK_NAME_WORD = "fkName";
  public static String MDR_FK_COLUMNS_WORD = "fkColumns";
  public static String MDR_VIEW_COLUMNS_WORD = "viewColumns";
  public static String MDR_LIST_COLUMNS_WORD = "listColumns";
  public static String MDR_LIST_NEW_COLUMNS_WORD = "listNewColumns";
  public static String MDR_VIEW_SELECT_COLUMNS_WORD = "viewSelectColumns";
  public static String MDR_FK_COLUMNS_REF_WORD = "fkColumnsRef"; // Il ne faut pas utiliser MDR_PK_COLUMNS_WORD qui ne garanti pas le lien entre colonnes
  public static String MDR_PK_NAME_WORD = "pkName";
  public static String MDR_PK_COLUMNS_WORD = "pkColumns";
  public static String MDR_UNIQUE_NAME_WORD = "uniqueName";
  public static String MDR_UNIQUE_COLUMNS_WORD = "uniqueColumns";
  public static String MDR_COLUMN_NAME_WORD =  "columnName";
  public static String MDR_COLUMN_VALUE_WORD =  "columnValue";
  public static String MDR_COLUMN_PK_NAME_WORD =  "columnPKName";
  public static String MDR_COLUMN_FK_NAME_WORD =  "columnFKName";
  public static String MDR_COLUMN_DATATYPE_WORD =  "columnDataType";
  public static String MDR_DEFAULT_VALUE_WORD =  "defaultValue";
  public static String MPDR_SEQUENCE_NAME_WORD = "sequenceName";
  public static String MPDR_MIN_VALUE_WORD = "minValue";
  public static String MPDR_INCREMENT_WORD = "increment";
  public static String MPDR_TRIGGER_NAME_WORD = "triggerName";
  public static String MPDR_PACKAGE_NAME_WORD = "packageName";
  public static String MPDR_PACKAGE_RESOURCES_NAME_WORD = "packageResourcesName";
  public static String MPDR_PROCEDURE_RESOURCES_CHECK_DATATYPE_WORD = "procedureResourcesCheckDatatypeWord";
  public static String MPDR_FUNCTION_NAME_WORD = "functionName";
  public static String MPDR_PROCEDURE_NAME_WORD = "procedureName";
  public static String MPDR_COLUMN_NAME_WORD = "mpdrColumnName";
  public static String MDR_CONSTRAINT_DATATYPE_NAME_WORD = "constraintDatatypeName";
  public static String MCD_ATTRIBUTE_NAME_WORD = "mcdAttrName";
  public static String MCD_ATTRIBUTE_SHORT_NAME_WORD = "mcdAttrShortName";
  public static String MDR_CHECK_NAME_WORD =  "checkName";
  public static String MDR_CHECK_EXPRESSION_WORD =  "checkExpression";
  public static String MPDR_COLUMN_AUDIT_AJUSER_NAME_WORD = "auditAjUserName";
  public static String MPDR_COLUMN_AUDIT_AJDATE_NAME_WORD = "auditAjDateName";
  public static String MPDR_COLUMN_AUDIT_MOUSER_NAME_WORD = "auditMoUserName";
  public static String MPDR_COLUMN_AUDIT_MODATE_NAME_WORD = "auditMoDateName";
  public static String MDR_COLUMN_LIEN_PROG_NAME_WORD =  "columnTargetLienProgName";

  public static String MPDR_INDEX_NAME_WORD =  "indexName";
  public static String MPDR_VIEW_NAME_WORD =  "viewName";

  public static String MPDR_CHECK_PARAM_EXPRESSION_NAME = "expr";  // Le nom du paramètre qui contient l'expression'

  public static String MPDR_EMPTY_SCHEMA_NAME_WORD = "emptySchema";
  public static String MPDR_DROP_BEFORE_NOTHING = "mpdr.drop.before.nothing";
  public static String MPDR_DROP_BEFORE_OBJECTS_CREATED = "mpdr.drop.before.objects.created";
  public static String MPDR_DROP_BEFORE_EMPTY = "mpdr.drop.before.empty";
  public static String MPDR_TRIGGER_USAGE_TAPIS = "mpdr.trigger.usage.tapis";
  public static String MPDR_TRIGGER_USAGE_WITHOUT_TAPIS = "mpdr.trigger.usage.without.tapis";
  public static String MPDR_TRIGGER_SCOPE_TABLE = "mpdr.trigger.scope.table";
  public static String MPDR_TRIGGER_SCOPE_VIEW = "mpdr.trigger.scope.view";

  public static String MPDR_STOREDCODE_USAGE_TAPIS = "mpdr.storedcode.usage.tapis";
  public static String MPDR_STOREDCODE_USAGE_WITHOUT_TAPIS = "mpdr.storedcode.usage.without.tapis";
  public static String MPDR_STOREDCODE_USAGE_RESOURCES = "mpdr.storedcode.usage.resources";
  public static String MPDR_STOREDCODE_SCOPE_TABLE = "mpdr.storedcode.scope.table";
  public static String MPDR_STOREDCODE_SCOPE_VIEW = "mpdr.storedcode.scope.view";
  public static String MPDR_STOREDCODE_SCOPE_MPDR = "mpdr.storedcode.scope.mpdr";


  public static String MPDR_COLUMN_AUDIT_AJUSER_NAME = "mpdr.column.audit.ajuser.name";
  public static String MPDR_COLUMN_AUDIT_AJDATE_NAME = "mpdr.column.audit.ajdate.name";
  public static String MPDR_COLUMN_AUDIT_MOUSER_NAME = "mpdr.column.audit.mouser.name";
  public static String MPDR_COLUMN_AUDIT_MODATE_NAME = "mpdr.column.audit.modate.name";

  public static String MDR_COL_NAME_WORD = "colName";
  public static String MDR_COL_NAME_ONE_ANCESTOR_WORD = "colNameOneAncestor";
  public static String MDR_COLUMN_DERIVED_WORD = "colDerived";
  public static String MDR_COLUMNS_NAME_WORD = "columnsName";

  public static String MDR_ASS_SHORT_NAME_WORD = "assShortName";

  public static String MDR_ROLE_SHORT_NAME_A_WORD = "roleA";
  public static String MDR_ROLE_SHORT_NAME_B_WORD = "roleB";
  public static String MDR_ROLE_SHORT_NAME_PARENT_WORD = "parentRoleShortName";
  public static String MDR_ROLE_SHORT_NAME_CHILD_WORD = "childRoleShortName";

  public static String MDR_PEA_SHORT_NAME_WORD = "peaShortName";

  public static String MDR_UNIQUE_NATURE_NAME_WORD = "uniqueNature";
  public static String MDR_UNIQUE_SHORT_NAME_WORD = "uniqueShortName";

  public static String MDR_INDICE_TABLENN_WORD = "indTableNN";
  public static String MDR_INDICE_COL_FK_WORD = "indColFK";
  public static String MDR_INDICE_CONST_FK_WORD = "indConstFK";

  public static String MDR_ROLE_SEP_WORD = "roleSep";
  public static String MDR_TABLE_SEP_WORD = "tableSep";
  public static String MDR_PEA_SEP_WORD = "peaSep";
  public static String MDR_FKIND_SEP_WORD = "fkIndSep";
  public static String MDR_UNIQUE_NATURE_SEP_WORD = "uniqueNatSep";

  public static String MPDR_TYPE_TRIGGER_MARKER_WORD = "typeTriggerMarker";
  public static String MPDR_TYPE_PACKAGE_MARKER_WORD = "typePackageMarker";
  public static String MPDR_TYPE_VIEW_MARKER_WORD = "typeViewMarker";

  public static Integer MDR_MARKER_DERIVED_LENGTH = 4;
  public static Integer MDR_MARKER_PEA_LENGTH = 4;
  public static Integer MDR_MARKER_CUSTOM_TABLE_NAME_LENGTH = 3;
  public static Integer MDR_MARKER_CUSTOM_COLUMN_NAME_LENGTH = 3;
  public static Integer MDR_MARKER_CUSTOM_PK_LENGTH = 3;
  public static Integer MDR_MARKER_CUSTOM_FK_LENGTH = 3;
  public static Integer MARKER_CUSTOM_SEQPK_LENGTH = 8;
  public static Integer MARKER_TRIGGER_LENGTH = 8;
  public static Integer MARKER_VIEW_LENGTH = 8;
  public static Integer MARKER_PACKAGE_LENGTH = 10;
  public static Integer MARKER_CHECK_LENGTH = 4;
  public static Integer MDR_SEPARATOR_NAMING_LENGTH = 1;

  public static String MARKER_PACKAGE_BODY = "_BODY";

  public static String MDR_WORDS_BEGIN = "{";
  public static String MDR_WORDS_END = "}";
  public static String MPDR_MARKER_DYNAMIC_CODE = "-";
  public static String MDR_WORDS_PATTERN = "\\{[a-zA-Z_]+\\}";
    public static String MDR_TABLE_NAME_FORMAT_DEFAULT = "{tableName}";
    public static String MDR_TABLE_NN_NAME_FORMAT_DEFAULT = "{ATableShortName}{tableSep}{assShortName}{roleA}{roleSep}{roleB}{tableSep}{BTableShortName}";
    public static String MDR_TABLE_NN_NAME_INDICE_FORMAT_DEFAULT = "{ATableShortName}{tableSep}{BTableShortName}{indTableNN}";
    public static String MDR_COLUMN_PK_NAME_FORMAT_DEFAULT = "{mcdAttrName}";
    public static String MDR_COLUMN_ATTR_NAME_FORMAT_DEFAULT = "{colDerived}{peaShortName}{peaSep}{mcdAttrName}";
    public static String MDR_COLUMN_ATTR_SHORT_NAME_FORMAT_DEFAULT = "{colDerived}{peaShortName}{peaSep}{mcdAttrShortName}";
    public static String MDR_COLUMN_DERIVED_MARKER_DEFAULT = "DER_";
    public static String MDR_COLUMN_FK_NAME_FORMAT_DEFAULT = "{parentTableShortName}{tableSep}{parentRoleShortName}{roleSep}{colName}";
    public static String MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT_DEFAULT = "{colNameOneAncestor}{indColFK}";
    public static String MDR_PK_NAME_FORMAT_DEFAULT = "{PK_}{tableShortName}";
    public static String MDR_PK_NN_NAME_FORMAT_DEFAULT = "{PK_}{ATableShortName}{tableSep}{assShortName}{roleA}{roleSep}{roleB}{tableSep}{BTableShortName}";
    public static String MDR_PK_NN_NAME_INDICE_FORMAT_DEFAULT = "{PK_}{ATableShortName}{tableSep}{BTableShortName}{indTableNN}";

    public static String MDR_INDICE_REGEXPR = "([0-9]+)$";
    public static Integer MDR_INDICE_TABLENN_LENGTH = 1 ;
    public static Integer MDR_INDICE_COL_FK_LENGTH = 1 ;
    public static Integer MDR_INDICE_CONST_FK_LENGTH = 2 ;
    public static String MDR_FK_NAME_FORMAT_DEFAULT = "{FK}{indConstFK}{fkIndSep}{childTableShortName}{tableSep}{parentTableShortName}{tableSep}{parentRoleShortName}";
    public static String MDR_FK_NAME_WITHOUT_ROLE_FORMAT_DEFAULT = "{FK}{indConstFK}{fkIndSep}{childTableShortName}{tableSep}{parentTableShortName}";
    public static String MDR_UNIQUE_NAME_FORMAT_DEFAULT = "{uniqueNature}{uniqueNatSep}{tableShortName}{tableSep}{uniqueName}";
    public static String MDR_UNIQUE_NAME_MAX30_FORMAT_DEFAULT = "{uniqueNature}{uniqueNatSep}{tableShortName}{tableSep}{uniqueShortName}";
    public static String MDR_ROLE_GENERALIZE_MARKER_DEFAULT = "gs";
    public static String MDR_SEPARATOR = "_" ;
    public static String MDR_PATH_SEP_FORMAT_DEFAULT = MDR_SEPARATOR;
    public static String MDR_PEA_SEP_FORMAT_DEFAULT = MDR_SEPARATOR;
    public static String MDR_TABLE_SEP_FORMAT_DEFAULT = MDR_SEPARATOR;
    public static String MDR_ROLE_SEP_FORMAT_DEFAULT = MDR_SEPARATOR;
    public static String MDR_FKIND_SEP_FORMAT_DEFAULT = MDR_SEPARATOR;
    public static String MDR_UNIQUE_NATURE_SEP_FORMAT_DEFAULT = MDR_SEPARATOR;

    public static String MDR_CONSTRAINT_CUSTOM_SPECIALIZED_NAME= "mdr.constraint.custom.specialized.name";

  public static Boolean MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DEFAULT = true;


  //A priori pas judicieux
  //public static String MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK = "mdr.pref.column.fk.one.diff.indice.fk";
  public static String MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1 = "mdr.pref.column.fk.one.diff.indice.start.1";
  public static String MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2 = "mdr.pref.column.fk.one.diff.indice.start.2";

    public static String MDR_PREF_COLUMN_PFK_STEREOTYPE_BOTH = "mdr.pref.column.pfk.stereotype.both";
    public static String MDR_PREF_COLUMN_PFK_STEREOTYPE_SEPARATE = "mdr.pref.column.pfk.stereotype.separate";

    public static Boolean MDR_PREF_COLUMN_NID_DEFAULT = true;

    public static String WARNING_LEVEL_DEVELOPMENT= "warning.level.development";
    public static String WARNING_LEVEL_DEBUG = "warning.level.debug";
    public static String WARNING_LEVEL_DETAILS = "warning.level.details";
    public static String WARNING_LEVEL_INFO = "warning.level.info";
    public static String WARNING_LEVEL_WARNING = "warning.level.warning";

  /*
    public static String RESULTAT_EXCEPTION_STACKTRACE = "resultat.exception.stacktrace";
    public static String RESULTAT_EXCEPTION_UNHANDLED = "resultat.exception.unhandled";
    public static String RESULTAT_EXCEPTION_CATCHED = "resultat.exception.catched";
    public static String RESULTAT_FATAL = "resultat.error.fatal";
    public static String RESULTAT_NO_FATAL = "resultat.error.no.fatal";
    public static String RESULTAT_INFO = "resultat.info";
  */

  public static String ATTRIBUTE_IS_ACTIVE = "is_active";
  public static String ATTRIBUTE_REPOSITORY_ENTITY_ID = "repository_entity_id";
  public static String ATTRIBUTE_HEIGHT = "height";
  public static String ATTRIBUTE_WIDTH = "width";
  public static String ATTRIBUTE_X = "x";
  public static String ATTRIBUTE_Y = "y";
  public static String ATTRIBUTE_ID = "id";
  public static String ATTRIBUTE_TYPE = "type";
  public static String ATTRIBUTE_NAME = "name";
  public static String ATTRIBUTE_PARENT_ID = "parent_id";
  public static String ATTRIBUTE_X_DISTANCE_FROM_ANCHOR_POINT = "x_distance_from_anchor_point";
  public static String ATTRIBUTE_Y_DISTANCE_FROM_ANCHOR_POINT = "y_distance_from_anchor_point";
  public static String ATTRIBUTE_RELATED_ANCHOR_POINT_ID = "related_anchor_point_id";
  public static String ATTRIBUTE_REPOSITORY_ASSOCIATION_ID = "repository_association_id";
  public static String ATTRIBUTE_DESTINATION_ENTITY_SHAPE_ID = "destination_entity_shape_id";
  public static String ATTRIBUTE_SOURCE_ENTITY_SHAPE_ID = "source_entity_shape_id";
  public static String NODE_SHAPES = "shapes";
  public static String NODE_DIAGRAM = "diagramme";
  public static String NODE_DIAGRAMS = "diagrammes";

/*
 * Préférences de l'application
 * ATTENTION: pour chaque nouvelle préférence créée, il est nécessaire d'ajouter la préférence dans :
 *  - la classe PreferencesOfApplicationSaverXml: de sorte à ce que la nouvelle préférence soit persisté lors des prochaines sauvegardes des préférences d'application dans le fichier XML.
 *  - la classe PreferencesOfApplicationLeaderXml: de sorte à ce que la nouvelle préférence soit chargée lors des prochains chargements de sauvegardes.
 *  - le schéma SchemaApplicationPref.xsd: de sorte à ce que les futurs sauvegardes soient valides selon le schéma XSD.
 */
  private Boolean DEBUG = false;
  private Boolean DEBUG_BACKGROUND_PANEL = false;
  private Boolean DEBUG_PRINT_MVCCDELEMENT = false;
  private Boolean DEBUG_SHOW_TABLE_COL_HIDDEN = false;
  private Boolean DEBUG_INSPECT_OBJECT_IN_TREE = false;
  private Boolean DEBUG_EDITOR_DATAS_CHANGED = null;
  private Boolean DEBUG_TD_PRINT = null;
  private Boolean DEBUG_TD_UNICITY_PRINT = null;
  private WarningLevel WARNING_LEVEL = null;
  private Boolean REPOSITORY_MCD_MODELS_MANY = false;
  private Boolean REPOSITORY_MCD_PACKAGES_AUTHORIZEDS = false;
  private ConDBMode CON_DB_MODE = null;

  // Pendant la phase de développement
  //ATTENTION: lors de la modification de cette valeur, il faut supprimer le fichier application.pref
  private Boolean PERSISTENCE_SERIALISATION_INSTEADOF_XML = false;

  //private PreferencesDisplay GENERAL_PREFERENCES_DISPLAY = PreferencesDisplay.REPOSITORY;

    // Préférences de projet (modifications User/Custom)
    // Général
    private String GENERAL_RELATION_NOTATION ;
    private MUMLExtensionNaming GENERAL_M_UML_STEREOTYPE_NAMING_INLINE;
    private MUMLExtensionNaming GENERAL_M_UML_STEREOTYPE_NAMING_INBOX;
    private MUMLExtensionNaming GENERAL_M_UML_CONSTRAINT_NAMING_INLINE;
    private MUMLExtensionNaming GENERAL_M_UML_CONSTRAINT_NAMING_INBOX;


    //private String PROJET_VERSION;  //Directement comme propriété de projet

    // MCD
    private Boolean MCD_JOURNALIZATION = false;
    private Boolean MCD_JOURNALIZATION_EXCEPTION = true;
    private Boolean MCD_AUDIT = false;
    private Boolean MCD_AUDIT_EXCEPTION = true;
    private String MCDDATATYPE_NUMBER_SIZE_MODE = null;
    private String MCD_AID_DATATYPE_LIENPROG = null;
    private String MCD_AID_IND_COLUMN_NAME = null;
    private String MCD_AID_DEP_COLUMN_NAME = null;
    private Boolean MCD_AID_WITH_DEP = true;
    private Integer MCD_AID_SIZEDEFAULT = MCDDOMAIN_AID_SIZEDEFAULT;
    //TODO-0 --> Est devenue une préférence de portée générale
    // Par exemple PATH_NAMING et renommer aussi les 2 constantes MCD_NAMING_NAME et MCD_NAMING_SHORT_NAME
    private String MCD_TREE_NAMING_ASSOCIATION = null;
    private String MCD_MODE_NAMING_LONG_NAME = null;
    private String MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME = null;

  // Éditeur
  private Dimension PREFERENCES_WINDOW_SIZE_CUSTOM = null;
  private Point PREFERENCES_WINDOW_LOCATION_ONSCREEN = null;

  private Dimension ENTITY_WINDOW_SIZE_CUSTOM = null;
  private Point ENTITY_WINDOW_LOCATION_ONSCREEN = null;

  private Dimension MDRTABLE_WINDOW_SIZE_CUSTOM = null;
  private Point MDRTABLE_WINDOW_LOCATION_ONSCREEN = null;

  // MCDToMLDR
  private String MCDTOMLDR_MODE = null;

  // MLDRToMPDR
  private String MLDRTOMPDR_DB = null;

    // MDR Format
    private String MDR_TABLE_NAME_FORMAT = null;
    private String MDR_TABLE_NN_NAME_FORMAT = null ;
    private String MDR_TABLE_NN_NAME_INDICE_FORMAT = null;
    private String MDR_COLUMN_ATTR_NAME_FORMAT = null;
    private String MDR_COLUMN_ATTR_SHORT_NAME_FORMAT = null;
    private String MDR_COLUMN_DERIVED_MARKER = null;
    private String MDR_PK_NAME_FORMAT = null;
    private String MDR_PK_NN_NAME_FORMAT = null;
    private String MDR_PK_NN_NAME_INDICE_FORMAT = null;
    private String MDR_COLUMN_PK_NAME_FORMAT = null;
    private String MDR_COLUMN_FK_NAME_FORMAT = null;
    private String MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT = null;
    private String MDR_FK_NAME_FORMAT = null;
    private String MDR_FK_NAME_WITHOUT_ROLE_FORMAT = null;
    private String MDR_UNIQUE_NAME_FORMAT = null;
    private String MDR_UNIQUE_NAME_MAX30_FORMAT = null;
    private String MDR_ROLE_GENERALIZE_MARKER = null;
    private String MDR_PATH_SEP_FORMAT = null;
    private String MDR_PEA_SEP_FORMAT = null;
    private String MDR_TABLE_SEP_FORMAT = null;
    private String MDR_ROLE_SEP_FORMAT = null ;
    private String MDR_FKIND_SEP_FORMAT = null ;
    private String MDR_UNIQUE_NATURE_SEP_FORMAT = null ;

    // MDR Préférences
    private Boolean MDR_PREF_COLUMN_FK_ONE_ANCESTOR = null ;
    private String MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF = null;
    private String MDR_PREF_COLUMN_PFK_STEREOTYPE = null;
    private Boolean MDR_PREF_COLUMN_NID = true;


  // MLDR Préférences
  private MDRNamingLength MLDR_PREF_NAMING_LENGTH = null;
  private MDRCaseFormat MLDR_PREF_NAMING_FORMAT = null;
  private MDRCaseFormat MLDR_PREF_RESERDWORDS_FORMAT = null;

  // MPDR Oracle Préférences
  private MDRNamingLength MPDRORACLE_PREF_NAMING_LENGTH = null;
  private MDRCaseFormat MPDRORACLE_PREF_NAMING_FORMAT = null;
  private MDRCaseFormat MPDRORACLE_PREF_RESERDWORDS_FORMAT = null;
  private MDRCaseFormat MPDRORACLE_PREF_OBJECTSINCODE_FORMAT = null;
  private String MPDRORACLE_DELIMITER_INSTRUCTIONS = null;
  private MPDRDBPK MPDRORACLE_PK_GENERATE = null;
  private Boolean MPDRORACLE_TAPIS = null;
  private String MPDRORACLE_SEQPK_NAME_FORMAT;
  private String MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT;
  private String MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT;
  private String MPDRORACLE_PACKAGE_NAME_FORMAT;
  private String MPDRORACLE_VIEW_NAME_FORMAT;
  private String MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  private String MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;


  // MPDR MySQL Préférences
  private MDRNamingLength MPDRMYSQL_PREF_NAMING_LENGTH = null;
  private MDRCaseFormat MPDRMYSQL_PREF_NAMING_FORMAT = null;
  private MDRCaseFormat MPDRMYSQL_PREF_RESERDWORDS_FORMAT = null;
  private MDRCaseFormat MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT = null;
  private String MPDRMYSQL_DELIMITER_INSTRUCTIONS = null;
  private MPDRDBPK MPDRMYSQL_PK_GENERATE = null;
  private Boolean MPDRMYSQL_TAPIS = null;
  private String MPDRMYSQL_SEQPK_NAME_FORMAT;
  private String MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT;
  private String MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT;
  private String MPDRMYSQL_VIEW_NAME_FORMAT;
  private String MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  private String MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;

  // MPDR PostgreSQL Préférences
  private MDRNamingLength MPDRPOSTGRESQL_PREF_NAMING_LENGTH = null;
  private MDRCaseFormat MPDRPOSTGRESQL_PREF_NAMING_FORMAT = null;
  private MDRCaseFormat MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT = null;
  private MDRCaseFormat MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT = null;
  private String MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS = null;
  private MPDRDBPK MPDRPOSTGRESQL_PK_GENERATE = null;
  private Boolean MPDRPOSTGRESQL_TAPIS = null;
  private String MPDRPOSTGRESQL_SEQPK_NAME_FORMAT;
  private String MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT;
  private String MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT;
  private String MPDRPOSTGRESQL_VIEW_NAME_FORMAT;
  private String MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  private String MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;


  private Boolean DIAGRAMMER_SHOW_GRID = null;

  public Preferences(MVCCDElement parent, String name) {
    super(parent, name);
  }

  public Boolean isPERSISTENCE_SERIALISATION_INSTEADOF_XML() {
    return this.PERSISTENCE_SERIALISATION_INSTEADOF_XML;
  }

  public void setPERSISTENCE_SERIALISATION_INSTEADOF_XML(Boolean PERSISTENCE_SERIALISATION_INSTEADOF_XML) {
    this.PERSISTENCE_SERIALISATION_INSTEADOF_XML = PERSISTENCE_SERIALISATION_INSTEADOF_XML;
  }

  public Boolean isDEBUG() {
    return this.DEBUG;
  }

  public void setDEBUG(Boolean DEBUG) {
    this.DEBUG = DEBUG;
  }

  public Boolean isDEBUG_BACKGROUND_PANEL() {
    return this.DEBUG_BACKGROUND_PANEL;
  }

  public void setDEBUG_BACKGROUND_PANEL(Boolean DEBUG_BACKGROUND_PANEL) {
    this.DEBUG_BACKGROUND_PANEL = DEBUG_BACKGROUND_PANEL;
  }

  public Boolean isDEBUG_PRINT_MVCCDELEMENT() {
    return this.DEBUG_PRINT_MVCCDELEMENT;
  }

  public void setDEBUG_PRINT_MVCCDELEMENT(Boolean DEBUG_PRINT_MVCCDELEMENT) {
    this.DEBUG_PRINT_MVCCDELEMENT = DEBUG_PRINT_MVCCDELEMENT;
  }

  public Boolean isDEBUG_SHOW_TABLE_COL_HIDDEN() {
    return this.DEBUG_SHOW_TABLE_COL_HIDDEN;
  }

  public void setDEBUG_SHOW_TABLE_COL_HIDDEN(Boolean DEBUG_SHOW_TABLE_COL_HIDDEN) {
    this.DEBUG_SHOW_TABLE_COL_HIDDEN = DEBUG_SHOW_TABLE_COL_HIDDEN;
  }

  public Boolean getDEBUG_INSPECT_OBJECT_IN_TREE() {
    return this.DEBUG_INSPECT_OBJECT_IN_TREE;
  }

  public void setDEBUG_INSPECT_OBJECT_IN_TREE(Boolean DEBUG_INSPECT_OBJECT_IN_TREE) {
    this.DEBUG_INSPECT_OBJECT_IN_TREE = DEBUG_INSPECT_OBJECT_IN_TREE;
  }

  public Boolean getDEBUG_EDITOR_DATAS_CHANGED() {
    if (this.DEBUG_EDITOR_DATAS_CHANGED == null) {
      this.DEBUG_EDITOR_DATAS_CHANGED = false;
    }
    return this.DEBUG_EDITOR_DATAS_CHANGED;
  }

  public void setDEBUG_EDITOR_DATAS_CHANGED(Boolean DEBUG_EDITOR_DATAS_CHANGED) {
    this.DEBUG_EDITOR_DATAS_CHANGED = DEBUG_EDITOR_DATAS_CHANGED;
  }

  public Boolean getDEBUG_TD_PRINT() {
    if (this.DEBUG_TD_PRINT == null) {
      this.DEBUG_TD_PRINT = false;
    }
    return this.DEBUG_TD_PRINT;
  }

  public void setDEBUG_TD_PRINT(Boolean DEBUG_TD_PRINT) {
    this.DEBUG_TD_PRINT = DEBUG_TD_PRINT;
  }

  public Boolean getDEBUG_TD_UNICITY_PRINT() {
    if (this.DEBUG_TD_UNICITY_PRINT == null) {
      this.DEBUG_TD_UNICITY_PRINT = false;
    }
    return this.DEBUG_TD_UNICITY_PRINT;
  }

  public void setDEBUG_TD_UNICITY_PRINT(Boolean DEBUG_TD_UNICITY_PRINT) {
    this.DEBUG_TD_UNICITY_PRINT = DEBUG_TD_UNICITY_PRINT;
  }

  public WarningLevel getWARNING_LEVEL() {
    if (this.WARNING_LEVEL == null) {
      //WARNING_LEVEL = WarningLevel.WARNING;
      this.WARNING_LEVEL = WarningLevel.DETAILS;
      //WARNING_LEVEL = WarningLevel.DEBUG_MODE;
    }
    return this.WARNING_LEVEL;
  }

  public void setWARNING_LEVEL(WarningLevel WARNING_LEVEL) {
    this.WARNING_LEVEL = WARNING_LEVEL;
  }

  public Boolean getREPOSITORY_MCD_MODELS_MANY() {
    return this.REPOSITORY_MCD_MODELS_MANY;
  }

  public void setREPOSITORY_MCD_MODELS_MANY(Boolean REPOSITORY_MCD_MODELS_MANY) {
    this.REPOSITORY_MCD_MODELS_MANY = REPOSITORY_MCD_MODELS_MANY;
  }

  public Boolean getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS() {
    return this.REPOSITORY_MCD_PACKAGES_AUTHORIZEDS;
  }

  public void setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(Boolean REPOSITORY_MCD_PACKAGES_AUTHORIZEDS) {
    this.REPOSITORY_MCD_PACKAGES_AUTHORIZEDS = REPOSITORY_MCD_PACKAGES_AUTHORIZEDS;
  }

  public ConDBMode getCON_DB_MODE() {
    if (this.CON_DB_MODE == null) {
      this.CON_DB_MODE = ConDBMode.CONNECTION;
    }
    return CON_DB_MODE;
  }

  public void setCON_DB_MODE(ConDBMode CON_DB_MODE) {
    this.CON_DB_MODE = CON_DB_MODE;
  }

  // Project

/*
    public String getPROJET_VERSION() {
        if ( PROJET_VERSION == null) {
            PROJET_VERSION = APPLICATION_VERSION ;
        }
        return PROJET_VERSION;
    }

    public void setPROJET_VERSION(String PROJET_VERSION) {
        this.PROJET_VERSION = PROJET_VERSION;
    }

 */

  public String getGENERAL_RELATION_NOTATION() {
    if (this.GENERAL_RELATION_NOTATION == null) {
      this.GENERAL_RELATION_NOTATION = GENERAL_RELATION_NOTATION_UML;
    }
    return this.GENERAL_RELATION_NOTATION;
  }

  public void setGENERAL_RELATION_NOTATION(String GENERAL_RELATION_NOTATION) {
    this.GENERAL_RELATION_NOTATION = GENERAL_RELATION_NOTATION;
  }

    public MUMLExtensionNaming getGENERAL_M_UML_STEREOTYPE_NAMING_INLINE() {
        if (GENERAL_M_UML_STEREOTYPE_NAMING_INLINE == null){
            GENERAL_M_UML_STEREOTYPE_NAMING_INLINE = MUMLExtensionNaming.ONELINE_MANYMARKER;
        }
        return GENERAL_M_UML_STEREOTYPE_NAMING_INLINE;
    }

    public void setGENERAL_M_UML_STEREOTYPE_NAMING_INLINE(MUMLExtensionNaming GENERAL_M_UML_STEREOTYPE_NAMING_INLINE) {
        this.GENERAL_M_UML_STEREOTYPE_NAMING_INLINE = GENERAL_M_UML_STEREOTYPE_NAMING_INLINE;
    }

    public MUMLExtensionNaming getGENERAL_M_UML_STEREOTYPE_NAMING_INBOX() {
        if (GENERAL_M_UML_STEREOTYPE_NAMING_INBOX == null){
            GENERAL_M_UML_STEREOTYPE_NAMING_INBOX = MUMLExtensionNaming.MANYLINE;
        }
        return GENERAL_M_UML_STEREOTYPE_NAMING_INBOX;
    }

    public void setGENERAL_M_UML_STEREOTYPE_NAMING_INBOX(MUMLExtensionNaming GENERAL_M_UML_STEREOTYPE_NAMING_INBOX) {
        this.GENERAL_M_UML_STEREOTYPE_NAMING_INBOX = GENERAL_M_UML_STEREOTYPE_NAMING_INBOX;
    }

    public MUMLExtensionNaming getGENERAL_M_UML_CONSTRAINT_NAMING_INLINE() {
        if (GENERAL_M_UML_CONSTRAINT_NAMING_INLINE == null){
            GENERAL_M_UML_CONSTRAINT_NAMING_INLINE = MUMLExtensionNaming.ONELINE_MANYMARKER;
        }
        return GENERAL_M_UML_CONSTRAINT_NAMING_INLINE;
    }

    public void setGENERAL_M_UML_CONSTRAINT_NAMING_INLINE(MUMLExtensionNaming GENERAL_M_UML_CONSTRAINT_NAMING_INLINE) {
        this.GENERAL_M_UML_CONSTRAINT_NAMING_INLINE = GENERAL_M_UML_CONSTRAINT_NAMING_INLINE;
    }

    public MUMLExtensionNaming getGENERAL_M_UML_CONSTRAINT_NAMING_INBOX() {
        if (GENERAL_M_UML_CONSTRAINT_NAMING_INBOX == null){
            GENERAL_M_UML_CONSTRAINT_NAMING_INBOX = MUMLExtensionNaming.MANYLINE;
        }
        return GENERAL_M_UML_CONSTRAINT_NAMING_INBOX;
    }

    public void setGENERAL_M_UML_CONSTRAINT_NAMING_INBOX(MUMLExtensionNaming GENERAL_M_UML_CONSTRAINT_NAMING_INBOX) {
        this.GENERAL_M_UML_CONSTRAINT_NAMING_INBOX = GENERAL_M_UML_CONSTRAINT_NAMING_INBOX;
    }

    public Boolean getMCD_JOURNALIZATION() {
        return MCD_JOURNALIZATION;
    }
    public void setMCD_JOURNALIZATION(Boolean MCD_JOURNALIZATION) {
        this.MCD_JOURNALIZATION = MCD_JOURNALIZATION;
    }

  public Boolean getMCD_JOURNALIZATION_EXCEPTION() {
    return this.MCD_JOURNALIZATION_EXCEPTION;
  }

  public void setMCD_JOURNALIZATION_EXCEPTION(Boolean MCD_JOURNALIZATION_EXCEPTION) {
    this.MCD_JOURNALIZATION_EXCEPTION = MCD_JOURNALIZATION_EXCEPTION;
  }

  public Boolean getMCD_AUDIT() {
    return this.MCD_AUDIT;
  }

  public void setMCD_AUDIT(Boolean MCD_AUDIT) {
    this.MCD_AUDIT = MCD_AUDIT;
  }

  public Boolean getMCD_AUDIT_EXCEPTION() {
    return this.MCD_AUDIT_EXCEPTION;
  }

  public void setMCD_AUDIT_EXCEPTION(Boolean MCD_AUDIT_EXCEPTION) {
    this.MCD_AUDIT_EXCEPTION = MCD_AUDIT_EXCEPTION;
  }

  public String getMCD_AID_DATATYPE_LIENPROG() {
    if (this.MCD_AID_DATATYPE_LIENPROG == null) {
      this.MCD_AID_DATATYPE_LIENPROG = MCDDOMAIN_AID_LIENPROG;
    }
    return this.MCD_AID_DATATYPE_LIENPROG;
  }

  public void setMCD_AID_DATATYPE_LIENPROG(String MCD_AID_DATATYPE_LIENPROG) {
    this.MCD_AID_DATATYPE_LIENPROG = MCD_AID_DATATYPE_LIENPROG;
  }

  public String getMCDDATATYPE_NUMBER_SIZE_MODE() {
    if (this.MCDDATATYPE_NUMBER_SIZE_MODE == null) {
      this.MCDDATATYPE_NUMBER_SIZE_MODE = MCDDATATYPE_NUMBER_SIZE_PRECISION;
    }
    return this.MCDDATATYPE_NUMBER_SIZE_MODE;
  }

  public void setMCDDATATYPE_NUMBER_SIZE_MODE(String MCDDATATYPE_NUMBER_SIZE_MODE) {
    this.MCDDATATYPE_NUMBER_SIZE_MODE = MCDDATATYPE_NUMBER_SIZE_MODE;
  }

  public String getMCD_AID_IND_COLUMN_NAME() {
    if (this.MCD_AID_IND_COLUMN_NAME == null) {
      this.MCD_AID_IND_COLUMN_NAME = MCD_AID_IND_COLUMN_NAME_DEFAULT;
    }
    return this.MCD_AID_IND_COLUMN_NAME;
  }

  public void setMCD_AID_IND_COLUMN_NAME(String MCD_AID_IND_COLUMN_NAME) {
    this.MCD_AID_IND_COLUMN_NAME = MCD_AID_IND_COLUMN_NAME;
  }

  public Integer getMCD_AID_COLUMN_NAME_LENGTH() {
    if (MCD_AID_COLUMN_NAME_LENGTH == null) {
      MCD_AID_COLUMN_NAME_LENGTH = MCD_AID_COLUMN_NAME_LENGTH;
    }
    return MCD_AID_COLUMN_NAME_LENGTH;
  }

  public void setMCD_AID_COLUMN_NAME_LENGTH(Integer MCD_AID_COLUMN_NAME_LENGTH) {
    this.MCD_AID_COLUMN_NAME_LENGTH = MCD_AID_COLUMN_NAME_LENGTH;
  }

  public String getMCD_AID_DEP_COLUMN_NAME() {
    if (this.MCD_AID_DEP_COLUMN_NAME == null) {
      this.MCD_AID_DEP_COLUMN_NAME = MCD_AID_DEP_COLUMN_NAME_DEFAULT;
    }
    return this.MCD_AID_DEP_COLUMN_NAME;
  }

  public void setMCD_AID_DEP_COLUMN_NAME(String MCD_AID_DEP_COLUMN_NAME) {
    this.MCD_AID_DEP_COLUMN_NAME = MCD_AID_DEP_COLUMN_NAME;
  }

  public Boolean isMCD_AID_WITH_DEP() {
    return this.MCD_AID_WITH_DEP;
  }

  public void setMCD_AID_WITH_DEP(Boolean MCD_AID_WITH_DEP) {
    this.MCD_AID_WITH_DEP = MCD_AID_WITH_DEP;
  }

  public Integer getMCD_AID_SIZEDEFAULT() {
    if (this.MCD_AID_SIZEDEFAULT == null) {
      this.MCD_AID_SIZEDEFAULT = MCDDOMAIN_AID_SIZEDEFAULT;
    }
    return this.MCD_AID_SIZEDEFAULT;
  }

  public void setMCD_AID_SIZEDEFAULT(Integer MCD_AID_SIZEDEFAULT) {
    this.MCD_AID_SIZEDEFAULT = MCD_AID_SIZEDEFAULT;
  }

  public String getMCD_TREE_NAMING_ASSOCIATION() {
    if (this.MCD_TREE_NAMING_ASSOCIATION == null) {
      this.MCD_TREE_NAMING_ASSOCIATION = MCD_NAMING_NAME;
    }
    return this.MCD_TREE_NAMING_ASSOCIATION;
  }

  public void setMCD_TREE_NAMING_ASSOCIATION(String MCD_TREE_NAMING_ASSOCIATION) {
    this.MCD_TREE_NAMING_ASSOCIATION = MCD_TREE_NAMING_ASSOCIATION;
  }

  public String getMCD_MODE_NAMING_LONG_NAME() {
    if (this.MCD_MODE_NAMING_LONG_NAME == null) {
      this.MCD_MODE_NAMING_LONG_NAME = OPTION_NO;
    }
    return this.MCD_MODE_NAMING_LONG_NAME;
  }

  public void setMCD_MODE_NAMING_LONG_NAME(String MCD_MODE_NAMING_LONG_NAME) {
    this.MCD_MODE_NAMING_LONG_NAME = MCD_MODE_NAMING_LONG_NAME;
  }

  public String getMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME() {
    if (this.MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME == null) {
      this.MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME = OPTION_NO;
    }
    return this.MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME;
  }

  public void setMCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME(String MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME) {
    this.MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME = MCD_MODE_NAMING_ATTRIBUTE_SHORT_NAME;
  }

  public Dimension getPREFERENCES_WINDOW_SIZE_CUSTOM() {
    if (this.PREFERENCES_WINDOW_SIZE_CUSTOM == null) {
      this.PREFERENCES_WINDOW_SIZE_CUSTOM = new Dimension(PREFERENCES_WINDOW_WIDTH, PREFERENCES_WINDOW_HEIGHT);
    }
    return this.PREFERENCES_WINDOW_SIZE_CUSTOM;
  }

  public void setPREFERENCES_WINDOW_SIZE_CUSTOM(Dimension PREFERENCES_WINDOW_SIZE_CUSTOM) {
    this.PREFERENCES_WINDOW_SIZE_CUSTOM = PREFERENCES_WINDOW_SIZE_CUSTOM;
  }

  public Dimension getENTITY_WINDOW_SIZE_CUSTOM() {
    if (this.ENTITY_WINDOW_SIZE_CUSTOM == null) {
      this.ENTITY_WINDOW_SIZE_CUSTOM = new Dimension(ENTITY_WINDOW_WIDTH, ENTITY_WINDOW_HEIGHT);
    }
    return this.ENTITY_WINDOW_SIZE_CUSTOM;
  }

  public void setENTITY_WINDOW_SIZE_CUSTOM(Dimension ENTITY_WINDOW_SIZE_CUSTOM) {
    this.ENTITY_WINDOW_SIZE_CUSTOM = ENTITY_WINDOW_SIZE_CUSTOM;
  }

  public Point getENTITY_WINDOW_LOCATION_ONSCREEN() {
    return this.ENTITY_WINDOW_LOCATION_ONSCREEN;
  }

  public void setENTITY_WINDOW_LOCATION_ONSCREEN(Point ENTITY_WINDOW_LOCATION_ONSCREEN) {
    this.ENTITY_WINDOW_LOCATION_ONSCREEN = ENTITY_WINDOW_LOCATION_ONSCREEN;
  }

  public Dimension getMDRTABLE_WINDOW_SIZE_CUSTOM() {
    if (this.MDRTABLE_WINDOW_SIZE_CUSTOM == null) {
      this.MDRTABLE_WINDOW_SIZE_CUSTOM = new Dimension(MDRTABLE_WINDOW_WIDTH, MDRTABLE_WINDOW_HEIGHT);
    }
    return this.MDRTABLE_WINDOW_SIZE_CUSTOM;
  }

  public void setMDRTABLE_WINDOW_SIZE_CUSTOM(Dimension MDRTABLE_WINDOW_SIZE_CUSTOM) {
    this.MDRTABLE_WINDOW_SIZE_CUSTOM = MDRTABLE_WINDOW_SIZE_CUSTOM;
  }

  public Point getMDRTABLE_WINDOW_LOCATION_ONSCREEN() {
    return this.MDRTABLE_WINDOW_LOCATION_ONSCREEN;
  }

  public void setMDRTABLE_WINDOW_LOCATION_ONSCREEN(Point MDRTABLE_WINDOW_LOCATION_ONSCREEN) {
    this.MDRTABLE_WINDOW_LOCATION_ONSCREEN = MDRTABLE_WINDOW_LOCATION_ONSCREEN;
  }

  public Point getPREFERENCES_WINDOW_LOCATION_ONSCREEN() {
    return this.PREFERENCES_WINDOW_LOCATION_ONSCREEN;
  }

  public void setPREFERENCES_WINDOW_LOCATION_ONSCREEN(Point PREFERENCES_WINDOW_LOCATION_ONSCREEN) {
    this.PREFERENCES_WINDOW_LOCATION_ONSCREEN = PREFERENCES_WINDOW_LOCATION_ONSCREEN;
  }

  public String getMCDTOMLDR_MODE() {
    if (this.MCDTOMLDR_MODE == null) {
      this.MCDTOMLDR_MODE = MCDTOMLDR_MODE_DT;
    }
    return this.MCDTOMLDR_MODE;
  }

  public void setMCDTOMLDR_MODE(String MCDTOMLDR_MODE) {
    this.MCDTOMLDR_MODE = MCDTOMLDR_MODE;
  }

  public String getMLDRTOMPDR_DB() {
    if (this.MLDRTOMPDR_DB == null) {
      this.MLDRTOMPDR_DB = DB_ORACLE;
    }
    return this.MLDRTOMPDR_DB;
  }

  public void setMLDRTOMPDR_DB(String MLDRTOMPDR_DB) {
    this.MLDRTOMPDR_DB = MLDRTOMPDR_DB;
  }

  public String getMDR_TABLE_NAME_FORMAT() {
    if (this.MDR_TABLE_NAME_FORMAT == null) {
      this.MDR_TABLE_NAME_FORMAT = MDR_TABLE_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_TABLE_NAME_FORMAT;
  }

  public void setMDR_TABLE_NAME_FORMAT(String MDR_TABLE_NAME_FORMAT) {
    this.MDR_TABLE_NAME_FORMAT = MDR_TABLE_NAME_FORMAT;
  }

  public String getMDR_TABLE_NN_NAME_FORMAT() {
    if (this.MDR_TABLE_NN_NAME_FORMAT == null) {
      this.MDR_TABLE_NN_NAME_FORMAT = MDR_TABLE_NN_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_TABLE_NN_NAME_FORMAT;
  }

  public void setMDR_TABLE_NN_NAME_FORMAT(String MDR_TABLE_NN_NAME_FORMAT) {
    this.MDR_TABLE_NN_NAME_FORMAT = MDR_TABLE_NN_NAME_FORMAT;
  }

  public String getMDR_TABLE_NN_NAME_INDICE_FORMAT() {
    if (this.MDR_TABLE_NN_NAME_INDICE_FORMAT == null) {
      this.MDR_TABLE_NN_NAME_INDICE_FORMAT = MDR_TABLE_NN_NAME_INDICE_FORMAT_DEFAULT;
    }
    return this.MDR_TABLE_NN_NAME_INDICE_FORMAT;
  }

  public void setMDR_TABLE_NN_NAME_INDICE_FORMAT(String MDR_TABLE_NN_NAME_INDICE_FORMAT) {
    this.MDR_TABLE_NN_NAME_INDICE_FORMAT = MDR_TABLE_NN_NAME_INDICE_FORMAT;
  }

  public String getMDR_COLUMN_ATTR_NAME_FORMAT() {
    if (this.MDR_COLUMN_ATTR_NAME_FORMAT == null) {
      this.MDR_COLUMN_ATTR_NAME_FORMAT = MDR_COLUMN_ATTR_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_COLUMN_ATTR_NAME_FORMAT;
  }

  public void setMDR_COLUMN_ATTR_NAME_FORMAT(String MDR_COLUMN_ATTR_NAME_FORMAT) {
    this.MDR_COLUMN_ATTR_NAME_FORMAT = MDR_COLUMN_ATTR_NAME_FORMAT;
  }

  public String getMDR_COLUMN_ATTR_SHORT_NAME_FORMAT() {
    if (this.MDR_COLUMN_ATTR_SHORT_NAME_FORMAT == null) {
      this.MDR_COLUMN_ATTR_SHORT_NAME_FORMAT = MDR_COLUMN_ATTR_SHORT_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_COLUMN_ATTR_SHORT_NAME_FORMAT;
  }

  public void setMDR_COLUMN_ATTR_SHORT_NAME_FORMAT(String MDR_COLUMN_ATTR_SHORT_NAME_FORMAT) {
    this.MDR_COLUMN_ATTR_SHORT_NAME_FORMAT = MDR_COLUMN_ATTR_SHORT_NAME_FORMAT;
  }

  public String getMDR_COLUMN_DERIVED_MARKER() {
    if (this.MDR_COLUMN_DERIVED_MARKER == null) {
      this.MDR_COLUMN_DERIVED_MARKER = MDR_COLUMN_DERIVED_MARKER_DEFAULT;
    }
    return this.MDR_COLUMN_DERIVED_MARKER;
  }

  public void setMDR_COLUMN_DERIVED_MARKER(String MDR_COLUMN_DERIVED_MARKER) {
    this.MDR_COLUMN_DERIVED_MARKER = MDR_COLUMN_DERIVED_MARKER;
  }

  public String getMDR_PK_NAME_FORMAT() {
    if (this.MDR_PK_NAME_FORMAT == null) {
      this.MDR_PK_NAME_FORMAT = MDR_PK_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_PK_NAME_FORMAT;
  }

  public void setMDR_PK_NAME_FORMAT(String MDR_PK_NAME_FORMAT) {
    this.MDR_PK_NAME_FORMAT = MDR_PK_NAME_FORMAT;
  }

    public String getMDR_PK_NN_NAME_FORMAT() {
        if (MDR_PK_NN_NAME_FORMAT == null){
            MDR_PK_NN_NAME_FORMAT = MDR_PK_NN_NAME_FORMAT_DEFAULT;
        }
        return MDR_PK_NN_NAME_FORMAT;
    }

    public void setMDR_PK_NN_NAME_FORMAT(String MDR_PK_NN_NAME_FORMAT) {
        this.MDR_PK_NN_NAME_FORMAT = MDR_PK_NN_NAME_FORMAT;
    }

    public String getMDR_PK_NN_NAME_INDICE_FORMAT() {
        if (MDR_PK_NN_NAME_INDICE_FORMAT == null){
            MDR_PK_NN_NAME_INDICE_FORMAT = MDR_PK_NN_NAME_INDICE_FORMAT_DEFAULT;
        }
        return MDR_PK_NN_NAME_INDICE_FORMAT;
    }

    public void setMDR_PK_NN_NAME_INDICE_FORMAT(String MDR_PK_NN_NAME_INDICE_FORMAT) {
        this.MDR_PK_NN_NAME_INDICE_FORMAT = MDR_PK_NN_NAME_INDICE_FORMAT;
    }

  public String getMDR_COLUMN_FK_NAME_FORMAT() {
        if (MDR_COLUMN_FK_NAME_FORMAT == null){
            MDR_COLUMN_FK_NAME_FORMAT = MDR_COLUMN_FK_NAME_FORMAT_DEFAULT;
        }
        return MDR_COLUMN_FK_NAME_FORMAT;
    }

  public void setMDR_COLUMN_FK_NAME_FORMAT(String MDR_COLUMN_FK_NAME_FORMAT) {
    this.MDR_COLUMN_FK_NAME_FORMAT = MDR_COLUMN_FK_NAME_FORMAT;
  }

  public String getMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT() {
    if (this.MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT == null) {
      this.MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT = MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT_DEFAULT;
    }
    return this.MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT;
  }

  public void setMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT(String MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT) {
    this.MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT = MDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT;
  }

  public String getMDR_COLUMN_PK_NAME_FORMAT() {
    if (this.MDR_COLUMN_PK_NAME_FORMAT == null) {
      this.MDR_COLUMN_PK_NAME_FORMAT = MDR_COLUMN_PK_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_COLUMN_PK_NAME_FORMAT;
  }

  public void setMDR_COLUMN_PK_NAME_FORMAT(String MDR_COLUMN_PK_NAME_FORMAT) {
    this.MDR_COLUMN_PK_NAME_FORMAT = MDR_COLUMN_PK_NAME_FORMAT;
  }

  public String getMDR_FK_NAME_FORMAT() {
    if (this.MDR_FK_NAME_FORMAT == null) {
      this.MDR_FK_NAME_FORMAT = MDR_FK_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_FK_NAME_FORMAT;
  }

  public void setMDR_FK_NAME_FORMAT(String MDR_FK_NAME_FORMAT) {
    this.MDR_FK_NAME_FORMAT = MDR_FK_NAME_FORMAT;
  }

  public String getMDR_FK_NAME_WITHOUT_ROLE_FORMAT() {
    if (this.MDR_FK_NAME_WITHOUT_ROLE_FORMAT == null) {
      this.MDR_FK_NAME_WITHOUT_ROLE_FORMAT = MDR_FK_NAME_WITHOUT_ROLE_FORMAT_DEFAULT;
    }
    return this.MDR_FK_NAME_WITHOUT_ROLE_FORMAT;
  }

  public void setMDR_FK_NAME_WITHOUT_ROLE_FORMAT(String MDR_FK_NAME_WITHOUT_ROLE_FORMAT) {
    this.MDR_FK_NAME_WITHOUT_ROLE_FORMAT = MDR_FK_NAME_WITHOUT_ROLE_FORMAT;
  }

  public String getMDR_UNIQUE_NAME_FORMAT() {
    if (this.MDR_UNIQUE_NAME_FORMAT == null) {
      this.MDR_UNIQUE_NAME_FORMAT = MDR_UNIQUE_NAME_FORMAT_DEFAULT;
    }
    return this.MDR_UNIQUE_NAME_FORMAT;
  }

  public void setMDR_UNIQUE_NAME_FORMAT(String MDR_UNIQUE_NAME_FORMAT) {
    this.MDR_UNIQUE_NAME_FORMAT = MDR_UNIQUE_NAME_FORMAT;
  }

  public String getMDR_UNIQUE_NAME_MAX30_FORMAT() {
    if (this.MDR_UNIQUE_NAME_MAX30_FORMAT == null) {
      this.MDR_UNIQUE_NAME_MAX30_FORMAT = MDR_UNIQUE_NAME_MAX30_FORMAT_DEFAULT;
    }
    return this.MDR_UNIQUE_NAME_MAX30_FORMAT;
  }

  public void setMDR_UNIQUE_NAME_MAX30_FORMAT(String MDR_UNIQUE_NAME_MAX30_FORMAT) {
    this.MDR_UNIQUE_NAME_MAX30_FORMAT = MDR_UNIQUE_NAME_MAX30_FORMAT;
  }

  public String getMDR_ROLE_GENERALIZE_MARKER() {
    if (this.MDR_ROLE_GENERALIZE_MARKER == null) {
      this.MDR_ROLE_GENERALIZE_MARKER = MDR_ROLE_GENERALIZE_MARKER_DEFAULT;
    }
    return this.MDR_ROLE_GENERALIZE_MARKER;
  }

  public void setMDR_ROLE_GENERALIZE_MARKER(String MDR_ROLE_GENERALIZE_MARKER) {
    this.MDR_ROLE_GENERALIZE_MARKER = MDR_ROLE_GENERALIZE_MARKER;
  }

  public String getMDR_PATH_SEP_FORMAT() {
    if (this.MDR_PATH_SEP_FORMAT == null) {
      this.MDR_PATH_SEP_FORMAT = MDR_PATH_SEP_FORMAT_DEFAULT;
    }
    return this.MDR_PATH_SEP_FORMAT;
  }

  public void setMDR_PATH_SEP_FORMAT(String MDR_PATH_SEP_FORMAT) {
    this.MDR_PATH_SEP_FORMAT = MDR_PATH_SEP_FORMAT;
  }

  public String getMDR_PEA_SEP_FORMAT() {
    if (this.MDR_PEA_SEP_FORMAT == null) {
      this.MDR_PEA_SEP_FORMAT = MDR_PEA_SEP_FORMAT_DEFAULT;
    }
    return this.MDR_PEA_SEP_FORMAT;
  }

  public void setMDR_PEA_SEP_FORMAT(String MDR_PEA_SEP_FORMAT) {
    this.MDR_PEA_SEP_FORMAT = MDR_PEA_SEP_FORMAT;
  }

  public String getMDR_TABLE_SEP_FORMAT() {
    if (this.MDR_TABLE_SEP_FORMAT == null) {
      this.MDR_TABLE_SEP_FORMAT = MDR_TABLE_SEP_FORMAT_DEFAULT;
    }
    return this.MDR_TABLE_SEP_FORMAT;
  }

  public void setMDR_TABLE_SEP_FORMAT(String MDR_TABLE_SEP_FORMAT) {
    this.MDR_TABLE_SEP_FORMAT = MDR_TABLE_SEP_FORMAT;
  }

  public String getMDR_ROLE_SEP_FORMAT() {
    if (this.MDR_ROLE_SEP_FORMAT == null) {
      this.MDR_ROLE_SEP_FORMAT = MDR_ROLE_SEP_FORMAT_DEFAULT;
    }
    return this.MDR_ROLE_SEP_FORMAT;
  }

  public void setMDR_ROLE_SEP_FORMAT(String MDR_ROLE_SEP_FORMAT) {
    this.MDR_ROLE_SEP_FORMAT = MDR_ROLE_SEP_FORMAT;
  }

  public String getMDR_FKIND_SEP_FORMAT() {
    if (this.MDR_FKIND_SEP_FORMAT == null) {
      this.MDR_FKIND_SEP_FORMAT = MDR_FKIND_SEP_FORMAT_DEFAULT;
    }
    return this.MDR_FKIND_SEP_FORMAT;
  }

  public void setMDR_FKIND_SEP_FORMAT(String MDR_FKIND_SEP_FORMAT) {
    this.MDR_FKIND_SEP_FORMAT = MDR_FKIND_SEP_FORMAT;
  }

  public String getMDR_UNIQUE_NATURE_SEP_FORMAT() {
    if (this.MDR_UNIQUE_NATURE_SEP_FORMAT == null) {
      this.MDR_UNIQUE_NATURE_SEP_FORMAT = MDR_UNIQUE_NATURE_SEP_FORMAT_DEFAULT;
    }
    return this.MDR_UNIQUE_NATURE_SEP_FORMAT;
  }

  public void setMDR_UNIQUE_NATURE_SEP_FORMAT(String MDR_UNIQUE_NATURE_SEP_FORMAT) {
    this.MDR_UNIQUE_NATURE_SEP_FORMAT = MDR_UNIQUE_NATURE_SEP_FORMAT;
  }

  public Boolean getMDR_PREF_COLUMN_FK_ONE_ANCESTOR() {
    if (this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR == null) {
      this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR = MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DEFAULT;
    }
    return this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR;
  }

  public void setMDR_PREF_COLUMN_FK_ONE_ANCESTOR(Boolean MDR_PREF_COLUMN_FK_ONE_ANCESTOR) {
    this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR = MDR_PREF_COLUMN_FK_ONE_ANCESTOR;
  }

  //TODO-0
  // Renommer car utilisé par tous les indices
  // Modifier aussi l'interface utilisateur
  public String getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF() {
    if (this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF == null) {
      //MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF = MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK;
      this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF = MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2;
    }
    return this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF;
  }

  public void setMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF(String MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF) {
    this.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF = MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF;
  }

    public String getMDR_PREF_COLUMN_PFK_STEREOTYPE() {
        if (MDR_PREF_COLUMN_PFK_STEREOTYPE == null){
            MDR_PREF_COLUMN_PFK_STEREOTYPE = MDR_PREF_COLUMN_PFK_STEREOTYPE_BOTH;
        }
        return MDR_PREF_COLUMN_PFK_STEREOTYPE;
    }

    public void setMDR_PREF_COLUMN_PFK_STEREOTYPE(String MDR_PREF_COLUMN_PFK_STEREOTYPE) {
        this.MDR_PREF_COLUMN_PFK_STEREOTYPE = MDR_PREF_COLUMN_PFK_STEREOTYPE;
    }

    public Boolean getMDR_PREF_COLUMN_NID() {
        if (MDR_PREF_COLUMN_NID == null){
            MDR_PREF_COLUMN_NID = MDR_PREF_COLUMN_NID_DEFAULT;
        }
        return MDR_PREF_COLUMN_NID;
    }

    public void setMDR_PREF_COLUMN_NID(Boolean MDR_PREF_COLUMN_NID) {
        this.MDR_PREF_COLUMN_NID = MDR_PREF_COLUMN_NID;
    }

    public MDRNamingLength getMLDR_PREF_NAMING_LENGTH() {
        if (MLDR_PREF_NAMING_LENGTH == null){
            MLDR_PREF_NAMING_LENGTH = MDRNamingLength.LENGTH30;
        }
        return MLDR_PREF_NAMING_LENGTH;
    }

  public void setMLDR_PREF_NAMING_LENGTH(MDRNamingLength MLDR_PREF_NAMING_LENGTH) {
    this.MLDR_PREF_NAMING_LENGTH = MLDR_PREF_NAMING_LENGTH;
  }

  public MDRCaseFormat getMLDR_PREF_NAMING_FORMAT() {
    if (this.MLDR_PREF_NAMING_FORMAT == null) {
      this.MLDR_PREF_NAMING_FORMAT = MDRCaseFormat.NOTHING;
    }
    return this.MLDR_PREF_NAMING_FORMAT;
  }

  public void setMLDR_PREF_NAMING_FORMAT(MDRCaseFormat MLDR_PREF_NAMING_FORMAT) {
    this.MLDR_PREF_NAMING_FORMAT = MLDR_PREF_NAMING_FORMAT;
  }

  public MDRCaseFormat getMLDR_PREF_RESERDWORDS_FORMAT() {
    if (this.MLDR_PREF_RESERDWORDS_FORMAT == null) {
      this.MLDR_PREF_RESERDWORDS_FORMAT = MDRCaseFormat.NOTHING;
    }
    return MLDR_PREF_RESERDWORDS_FORMAT;
  }

  public void setMLDR_PREF_RESERDWORDS_FORMAT(MDRCaseFormat MLDR_PREF_RESERDWORDS_FORMAT) {
    this.MLDR_PREF_RESERDWORDS_FORMAT = MLDR_PREF_RESERDWORDS_FORMAT;
  }

  // Oracle
  public MDRNamingLength getMPDRORACLE_PREF_NAMING_LENGTH() {
    if (this.MPDRORACLE_PREF_NAMING_LENGTH == null) {
      this.MPDRORACLE_PREF_NAMING_LENGTH = MDRNamingLength.LENGTH30;
    }
    return this.MPDRORACLE_PREF_NAMING_LENGTH;
  }

  public void setMPDRORACLE_PREF_NAMING_LENGTH(MDRNamingLength MPDRORACLE_PREF_NAMING_LENGTH) {
    this.MPDRORACLE_PREF_NAMING_LENGTH = MPDRORACLE_PREF_NAMING_LENGTH;
  }

  public MDRCaseFormat getMPDRORACLE_PREF_NAMING_FORMAT() {
    if (this.MPDRORACLE_PREF_NAMING_FORMAT == null) {
      this.MPDRORACLE_PREF_NAMING_FORMAT = MDRCaseFormat.LIKEBD;
    }
    return this.MPDRORACLE_PREF_NAMING_FORMAT;
  }

  public void setMPDRORACLE_PREF_NAMING_FORMAT(MDRCaseFormat MPDRORACLE_PREF_NAMING_FORMAT) {
    this.MPDRORACLE_PREF_NAMING_FORMAT = MPDRORACLE_PREF_NAMING_FORMAT;
  }

  public MDRCaseFormat getMPDRORACLE_PREF_RESERDWORDS_FORMAT() {
    if (this.MPDRORACLE_PREF_RESERDWORDS_FORMAT == null) {
      this.MPDRORACLE_PREF_RESERDWORDS_FORMAT = MDRCaseFormat.LIKEBD;
    }
    return MPDRORACLE_PREF_RESERDWORDS_FORMAT;
  }

  public void setMPDRORACLE_PREF_RESERDWORDS_FORMAT(MDRCaseFormat MPDRORACLE_PREF_RESERDWORDS_FORMAT) {
    this.MPDRORACLE_PREF_RESERDWORDS_FORMAT = MPDRORACLE_PREF_RESERDWORDS_FORMAT;
  }

  public MDRCaseFormat getMPDRORACLE_PREF_OBJECTSINCODE_FORMAT() {
    if (this.MPDRORACLE_PREF_OBJECTSINCODE_FORMAT == null) {
      this.MPDRORACLE_PREF_OBJECTSINCODE_FORMAT = MDRCaseFormat.LIKEBD;
    }
    return MPDRORACLE_PREF_OBJECTSINCODE_FORMAT;
  }

  public void setMPDRORACLE_PREF_OBJECTSINCODE_FORMAT(MDRCaseFormat MPDRORACLE_PREF_OBJECTSINCODE_FORMAT) {
    this.MPDRORACLE_PREF_OBJECTSINCODE_FORMAT = MPDRORACLE_PREF_OBJECTSINCODE_FORMAT;
  }

  
  public String getMPDRORACLE_DELIMITER_INSTRUCTIONS() {
    if (this.MPDRORACLE_DELIMITER_INSTRUCTIONS == null) {
      this.MPDRORACLE_DELIMITER_INSTRUCTIONS = MPDRORACLE_DELIMITER_INSTRUCTIONS_DEFAULT;
    }
    return MPDRORACLE_DELIMITER_INSTRUCTIONS;
  }

  public void setMPDRORACLE_DELIMITER_INSTRUCTIONS(String MPDRORACLE_DELIMITER_INSTRUCTIONS) {
    this.MPDRORACLE_DELIMITER_INSTRUCTIONS = MPDRORACLE_DELIMITER_INSTRUCTIONS;
  }

  public MPDRDBPK getMPDRORACLE_PK_GENERATE() {
    if (this.MPDRORACLE_PK_GENERATE == null) {
      this.MPDRORACLE_PK_GENERATE = MPDRORACLE_PK_GENERATE_DEFAULT;
    }
    return MPDRORACLE_PK_GENERATE;
  }

  public void setMPDRORACLE_PK_GENERATE(MPDRDBPK MPDRORACLE_PK_GENERATE) {
    this.MPDRORACLE_PK_GENERATE = MPDRORACLE_PK_GENERATE;
  }

  public Boolean getMPDRORACLE_TAPIS() {
    if (this.MPDRORACLE_TAPIS == null) {
      this.MPDRORACLE_TAPIS = false;
    }
    return MPDRORACLE_TAPIS;
  }

  public void setMPDRORACLE_TAPIS(Boolean MPDRORACLE_TAPIS) {
    this.MPDRORACLE_TAPIS = MPDRORACLE_TAPIS;
  }

  public String getMPDRORACLE_SEQPK_NAME_FORMAT() {
    if (this.MPDRORACLE_SEQPK_NAME_FORMAT == null) {
      this.MPDRORACLE_SEQPK_NAME_FORMAT = MPDRORACLE_SEQPK_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_SEQPK_NAME_FORMAT;
  }

  public void setMPDRORACLE_SEQPK_NAME_FORMAT(String MPDRORACLE_SEQPK_NAME_FORMAT) {
    this.MPDRORACLE_SEQPK_NAME_FORMAT = MPDRORACLE_SEQPK_NAME_FORMAT;
  }

  public String getMPDRORACLE_TRIGGER_TABLE_NAME_FORMAT() {
    if (this.MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT == null) {
      this.MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT = MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT;
  }

  public void setMPDRORACLE_TRIGGER_TABLE_NAME_FORMAT(String MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT) {
    this.MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT = MPDRORACLE_TRIGGER_TABLE_NAME_FORMAT;
  }

  public String getMPDRORACLE_TRIGGER_VIEW_NAME_FORMAT() {
    if (this.MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT == null) {
      this.MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT = MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT;
  }

  public void setMPDRORACLE_TRIGGER_VIEW_NAME_FORMAT(String MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT) {
    this.MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT = MPDRORACLE_TRIGGER_VIEW_NAME_FORMAT;
  }

  
  
  public String getMPDRORACLE_PACKAGE_NAME_FORMAT() {
    if (this.MPDRORACLE_PACKAGE_NAME_FORMAT == null) {
      this.MPDRORACLE_PACKAGE_NAME_FORMAT = MPDRORACLE_PACKAGE_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_PACKAGE_NAME_FORMAT;
  }

  public void setMPDRORACLE_PACKAGE_NAME_FORMAT(String MPDRORACLE_PACKAGE_NAME_FORMAT) {
    this.MPDRORACLE_PACKAGE_NAME_FORMAT = MPDRORACLE_PACKAGE_NAME_FORMAT;
  }

  public String getMPDRORACLE_VIEW_NAME_FORMAT() {
    if (this.MPDRORACLE_VIEW_NAME_FORMAT == null) {
      this.MPDRORACLE_VIEW_NAME_FORMAT = MPDRORACLE_VIEW_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_VIEW_NAME_FORMAT;
  }

  public void setMPDRORACLE_VIEW_NAME_FORMAT(String MPDRORACLE_VIEW_NAME_FORMAT) {
    this.MPDRORACLE_VIEW_NAME_FORMAT = MPDRORACLE_VIEW_NAME_FORMAT;
  }

  public String getMPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT() {
    if (this.MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT == null) {
      this.MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT = MPDRORACLE_CHECK_COLUMN_DATATYPE_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  }

  public void setMPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT(String MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT) {
    this.MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT = MPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  }

  public String getMPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT() {
    if (this.MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT == null) {
      this.MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT = MPDRORACLE_CHECK_COLUMN_DATATYPE_MAX30_NAME_FORMAT_DEFAULT;
    }
    return MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;
  }

  public void setMPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT(String MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT) {
    this.MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT = MPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;
  }

  // MySQL
  public MDRNamingLength getMPDRMYSQL_PREF_NAMING_LENGTH() {
    if (this.MPDRMYSQL_PREF_NAMING_LENGTH == null) {
      this.MPDRMYSQL_PREF_NAMING_LENGTH = MDRNamingLength.LENGTH30;
    }
    return this.MPDRMYSQL_PREF_NAMING_LENGTH;
  }

  public void setMPDRMYSQL_PREF_NAMING_LENGTH(MDRNamingLength MPDRMYSQL_PREF_NAMING_LENGTH) {
    this.MPDRMYSQL_PREF_NAMING_LENGTH = MPDRMYSQL_PREF_NAMING_LENGTH;
  }

  public MDRCaseFormat getMPDRMYSQL_PREF_NAMING_FORMAT() {
    if (this.MPDRMYSQL_PREF_NAMING_FORMAT == null) {
      this.MPDRMYSQL_PREF_NAMING_FORMAT = MDRCaseFormat.NOTHING;
    }
    return this.MPDRMYSQL_PREF_NAMING_FORMAT;
  }

  public void setMPDRMYSQL_PREF_NAMING_FORMAT(MDRCaseFormat MPDRMYSQL_PREF_NAMING_FORMAT) {
    this.MPDRMYSQL_PREF_NAMING_FORMAT = MPDRMYSQL_PREF_NAMING_FORMAT;
  }


  public MDRCaseFormat getMPDRMYSQL_PREF_RESERDWORDS_FORMAT() {
    if (this.MPDRMYSQL_PREF_RESERDWORDS_FORMAT == null) {
      this.MPDRMYSQL_PREF_RESERDWORDS_FORMAT = MDRCaseFormat.NOTHING;
    }
    return MPDRMYSQL_PREF_RESERDWORDS_FORMAT;
  }

  public void setMPDRMYSQL_PREF_RESERDWORDS_FORMAT(MDRCaseFormat MPDRMYSQL_PREF_RESERDWORDS_FORMAT) {
    this.MPDRMYSQL_PREF_RESERDWORDS_FORMAT = MPDRMYSQL_PREF_RESERDWORDS_FORMAT;
  }
  
  public MDRCaseFormat getMPDRMYSQL_PREF_OBJECTSINCODE_FORMAT() {
    if (this.MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT == null) {
      this.MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT = MDRCaseFormat.NOTHING;
    }
    return MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT;
  }

  public void setMPDRMYSQL_PREF_OBJECTSINCODE_FORMAT(MDRCaseFormat MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT) {
    this.MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT = MPDRMYSQL_PREF_OBJECTSINCODE_FORMAT;
  }

  public String getMPDRMYSQL_DELIMITER_INSTRUCTIONS() {
    if (this.MPDRMYSQL_DELIMITER_INSTRUCTIONS == null) {
      this.MPDRMYSQL_DELIMITER_INSTRUCTIONS = MPDRMYSQL_DELIMITER_INSTRUCTIONS_DEFAULT;
    }
    return MPDRMYSQL_DELIMITER_INSTRUCTIONS;
  }

  public void setMPDRMYSQL_DELIMITER_INSTRUCTIONS(String MPDRMYSQL_DELIMITER_INSTRUCTIONS) {
    this.MPDRMYSQL_DELIMITER_INSTRUCTIONS = MPDRMYSQL_DELIMITER_INSTRUCTIONS;
  }

  public MPDRDBPK getMPDRMYSQL_PK_GENERATE() {
    if (this.MPDRMYSQL_PK_GENERATE == null) {
      this.MPDRMYSQL_PK_GENERATE = MPDRMYSQL_PK_GENERATE_DEFAULT;
    }
    return MPDRMYSQL_PK_GENERATE;
  }

  public void setMPDRMYSQL_PK_GENERATE(MPDRDBPK MPDRMYSQL_PK_GENERATE) {
    this.MPDRMYSQL_PK_GENERATE = MPDRMYSQL_PK_GENERATE;
  }

  public Boolean getMPDRMYSQL_TAPIS() {
    if (this.MPDRMYSQL_TAPIS == null) {
      this.MPDRMYSQL_TAPIS = false;
    }
    return MPDRMYSQL_TAPIS;
  }

  public void setMPDRMYSQL_TAPIS(Boolean MPDRMYSQL_TAPIS) {
    this.MPDRMYSQL_TAPIS = MPDRMYSQL_TAPIS;
  }

  public String getMPDRMYSQL_SEQPK_NAME_FORMAT() {
    if (this.MPDRMYSQL_SEQPK_NAME_FORMAT == null) {
      this.MPDRMYSQL_SEQPK_NAME_FORMAT = MPDRMYSQL_SEQPK_NAME_FORMAT_DEFAULT;
    }
    return MPDRMYSQL_SEQPK_NAME_FORMAT;
  }

  public void setMPDRMYSQL_SEQPK_NAME_FORMAT(String MPDRMYSQL_SEQPK_NAME_FORMAT) {
    this.MPDRMYSQL_SEQPK_NAME_FORMAT = MPDRMYSQL_SEQPK_NAME_FORMAT;
  }

  public String getMPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT() {
    if (this.MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT == null) {
      this.MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT = MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT_DEFAULT;
    }
    return MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT;
  }

  public void setMPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT(String MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT) {
    this.MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT = MPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT;
  }


  public String getMPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT() {
    if (this.MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT == null) {
      this.MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT = MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT_DEFAULT;
    }
    return MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT;
  }

  public void setMPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT(String MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT) {
    this.MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT = MPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT;
  }
  
  public String getMPDRMYSQL_VIEW_NAME_FORMAT() {
    if (this.MPDRMYSQL_VIEW_NAME_FORMAT == null) {
      this.MPDRMYSQL_VIEW_NAME_FORMAT = MPDRMYSQL_VIEW_NAME_FORMAT_DEFAULT;
    }
    return MPDRMYSQL_VIEW_NAME_FORMAT;
  }

  public void setMPDRMYSQL_VIEW_NAME_FORMAT(String MPDRMYSQL_VIEW_NAME_FORMAT) {
    this.MPDRMYSQL_VIEW_NAME_FORMAT = MPDRMYSQL_VIEW_NAME_FORMAT;
  }

  public String getMPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT() {
    if (this.MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT == null) {
      this.MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT = MPDRMYSQL_CHECK_COLUMN_DATATYPE_NAME_FORMAT_DEFAULT;
    }
    return MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  }

  public void setMPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT(String MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT) {
    this.MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT = MPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  }

  public String getMPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT() {
    if (this. MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT == null) {
      this. MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT = MPDRMYSQL_CHECK_COLUMN_DATATYPE_MAX30_NAME_FORMAT_DEFAULT;
    }
    return MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;
  }

  public void setMPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT(String MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT) {
    this.MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT = MPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;
  }

  // PostgreSQL


  public MDRNamingLength getMPDRPOSTGRESQL_PREF_NAMING_LENGTH() {
    if (this.MPDRPOSTGRESQL_PREF_NAMING_LENGTH == null) {
      this.MPDRPOSTGRESQL_PREF_NAMING_LENGTH = MDRNamingLength.LENGTH30;
    }
    return this.MPDRPOSTGRESQL_PREF_NAMING_LENGTH;
  }

  public void setMPDRPOSTGRESQL_PREF_NAMING_LENGTH(MDRNamingLength MPDRPOSTGRESQL_PREF_NAMING_LENGTH) {
    this.MPDRPOSTGRESQL_PREF_NAMING_LENGTH = MPDRPOSTGRESQL_PREF_NAMING_LENGTH;
  }

  public MDRCaseFormat getMPDRPOSTGRESQL_PREF_NAMING_FORMAT() {
    if (this.MPDRPOSTGRESQL_PREF_NAMING_FORMAT == null) {
      this.MPDRPOSTGRESQL_PREF_NAMING_FORMAT = MDRCaseFormat.LIKEBD;
    }
    return this.MPDRPOSTGRESQL_PREF_NAMING_FORMAT;
  }

  public void setMPDRPOSTGRESQL_PREF_NAMING_FORMAT(MDRCaseFormat MPDRPOSTGRESQL_PREF_NAMING_FORMAT) {
    this.MPDRPOSTGRESQL_PREF_NAMING_FORMAT = MPDRPOSTGRESQL_PREF_NAMING_FORMAT;
  }

  public MDRCaseFormat getMPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT() {
    if (this.MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT == null) {
      this.MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT = MDRCaseFormat.LIKEBD;
    }
    return MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT;
  }

  public void setMPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT(MDRCaseFormat MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT) {
    this.MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT = MPDRPOSTGRESQL_PREF_RESERDWORDS_FORMAT;
  }

  public MDRCaseFormat getMPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT() {
    if (this.MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT == null) {
      this.MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT = MDRCaseFormat.LIKEBD;
    }
    return MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT;
  }

  public void setMPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT(MDRCaseFormat MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT) {
    this.MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT = MPDRPOSTGRESQL_PREF_OBJECTSINCODE_FORMAT;
  }

  public String getMPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS() {
    if (this.MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS == null) {
      this.MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS = MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS_DEFAULT;
    }
    return MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS;
  }

  public void setMPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS(String MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS) {
    this.MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS = MPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS;
  }

  public MPDRDBPK getMPDRPOSTGRESQL_PK_GENERATE() {
    if (this.MPDRPOSTGRESQL_PK_GENERATE == null) {
      this.MPDRPOSTGRESQL_PK_GENERATE = MPDRPOSTGRESQL_PK_GENERATE_DEFAULT;
    }
    return MPDRPOSTGRESQL_PK_GENERATE;
  }

  public void setMPDRPOSTGRESQL_PK_GENERATE(MPDRDBPK MPDRPOSTGRESQL_PK_GENERATE) {
    this.MPDRPOSTGRESQL_PK_GENERATE = MPDRPOSTGRESQL_PK_GENERATE;
  }

  public Boolean getMPDRPOSTGRESQL_TAPIS() {
    if (this.MPDRPOSTGRESQL_TAPIS == null) {
      this.MPDRPOSTGRESQL_TAPIS = false;
    }
    return MPDRPOSTGRESQL_TAPIS;
  }

  public void setMPDRPOSTGRESQL_TAPIS(Boolean MPDRPOSTGRESQL_TAPIS) {
    this.MPDRPOSTGRESQL_TAPIS = MPDRPOSTGRESQL_TAPIS;
  }

  public String getMPDRPOSTGRESQL_SEQPK_NAME_FORMAT() {
    if (this.MPDRPOSTGRESQL_SEQPK_NAME_FORMAT == null) {
      this.MPDRPOSTGRESQL_SEQPK_NAME_FORMAT = MPDRPOSTGRESQL_SEQPK_NAME_FORMAT_DEFAULT;
    }
    return MPDRPOSTGRESQL_SEQPK_NAME_FORMAT;
  }

  public void setMPDRPOSTGRESQL_SEQPK_NAME_FORMAT(String MPDRPOSTGRESQL_SEQPK_NAME_FORMAT) {
    this.MPDRPOSTGRESQL_SEQPK_NAME_FORMAT = MPDRPOSTGRESQL_SEQPK_NAME_FORMAT;
  }

  public String getMPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT() {
    if (this.MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT == null) {
      this.MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT = MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT_DEFAULT;
    }
    return MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT;
  }

  public void setMPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT(String MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT) {
    this.MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT = MPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT;
  }

  public String getMPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT() {
    if (this.MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT == null) {
      this.MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT = MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT_DEFAULT;
    }
    return MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT;
  }

  public void setMPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT(String MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT) {
    this.MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT = MPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT;
  }

  public String getMPDRPOSTGRESQL_VIEW_NAME_FORMAT() {
    if (this.MPDRPOSTGRESQL_VIEW_NAME_FORMAT == null) {
      this.MPDRPOSTGRESQL_VIEW_NAME_FORMAT = MPDRPOSTGRESQL_VIEW_NAME_FORMAT_DEFAULT;
    }return MPDRPOSTGRESQL_VIEW_NAME_FORMAT;
  }

  public void setMPDRPOSTGRESQL_VIEW_NAME_FORMAT(String MPDRPOSTGRESQL_VIEW_NAME_FORMAT) {
    this.MPDRPOSTGRESQL_VIEW_NAME_FORMAT = MPDRPOSTGRESQL_VIEW_NAME_FORMAT;
  }

  public String getMPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT() {
    if (this.MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT == null) {
      this.MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT = MPDRPOSTGRESQL_CHECK_COLUMN_DATATYPE_NAME_FORMAT_DEFAULT;
    }
    return MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  }

  public void setMPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT(String MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT) {
    this.MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT = MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT;
  }

  public String getMPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT() {
    if (this.MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT == null) {
      this.MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT = MPDRPOSTGRESQL_CHECK_COLUMN_DATATYPE_MAX30_NAME_FORMAT_DEFAULT;
    }
    return MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;
  }

  public void setMPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT(String MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT) {
    this.MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT = MPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT;
  }

  //
  public Boolean isDIAGRAMMER_SHOW_GRID() {
    if (this.DIAGRAMMER_SHOW_GRID == null){
      this.DIAGRAMMER_SHOW_GRID = true;
    }

    return this.DIAGRAMMER_SHOW_GRID;
  }

  public void setDIAGRAMMER_SHOW_GRID(boolean showGrid) {
    this.DIAGRAMMER_SHOW_GRID = showGrid;
    DiagrammerService.getDrawPanel().repaint();
  }
}
