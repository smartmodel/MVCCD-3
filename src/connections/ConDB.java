package connections;

import messages.MessagesBuilder;
import preferences.Preferences;
import repository.editingTreat.connections.connection.ConConnectionEditingTreat;
import repository.editingTreat.connections.connection.ConConnectionOracleEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorOracleEditingTreat;

public enum ConDB {
    ORACLE (Preferences.CON_DB_ORACLE,
            "Oracle",
            Preferences.CON_URL_TEMPLATE_ORACLE,
            Preferences.CON_IDDB_NAME_SID_ORACLE_MARKER,
            Preferences.CON_IDDB_NAME_SERVICE_NAME_ORACLE_MARKER,
            String.valueOf(Preferences.CON_PORT_DEFAULT_ORACLE),
            Preferences.CON_DIRECTORY_ORACLE,
            Preferences.CON_FOR_NAME_ORACLE,
            new ConConnectionOracleEditingTreat(),
            new ConConnectorOracleEditingTreat()),

    MYSQL (Preferences.CON_DB_MYSQL,
            "MySQL",
            Preferences.CON_URL_TEMPLATE_MYSQL,
            null,
            null,
            String.valueOf(Preferences.CON_PORT_DEFAULT_MYSQL),
            Preferences.CON_DIRECTORY_MYSQL,
            Preferences.CON_FOR_NAME_MYSQL,
            null,
            null),

    POSTGRESQL (Preferences.CON_DB_POSTGRESQL,
            "PostgreSQL",
            Preferences.CON_URL_TEMPLATE_POSTGRESQL,
            null,
            null,
            String.valueOf(Preferences.CON_PORT_DEFAULT_POSTGRESQL),
            Preferences.CON_DIRECTORY_POSTGRESQL,
            Preferences.CON_FOR_NAME_POSTGRESQL,
            null,
            null);

    private final String name;
    private final String lienProg;
    private String urlTemplate;
    private String urlTemplateSIDMarker ;
    private String urlTemplateServiceNameMarker ;
    private String portDefault;
    private String directoryDriverDefault;
    private String forName;
    private ConConnectionEditingTreat conConnectionEditingTreat;
    private ConConnectorEditingTreat conConnectorEditingTreat;

    ConDB(String name,
          String lienProg,
          String urlTemplate,
          String urlTemplateSIDMarker,
          String urlTemplateServiceNameMarker,
          String portDefault,
          String directoryDriverDefault,
          String forName,
          ConConnectionEditingTreat conConnectionEditingTreat,
          ConConnectorEditingTreat conConnectorEditingTreat) {
        this.name = name;
        this.lienProg = lienProg;
        this.urlTemplate = urlTemplate;
        this.urlTemplateSIDMarker = urlTemplateSIDMarker;
        this.urlTemplateServiceNameMarker = urlTemplateServiceNameMarker;
        this.portDefault = portDefault;
        this.directoryDriverDefault = directoryDriverDefault;
        this.forName = forName;
        this.conConnectionEditingTreat = conConnectionEditingTreat;
        this.conConnectorEditingTreat = conConnectorEditingTreat;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static ConDB findByText(String text){
        for (ConDB element: ConDB.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public String getLienProg() {
        return lienProg;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public String getUrlTemplateSIDMarker() {
        return urlTemplateSIDMarker;
    }

    public String getUrlTemplateServiceNameMarker() {
        return urlTemplateServiceNameMarker;
    }

    public String getPortDefault() {
        return portDefault;
    }

    public String getDirectoryDriverDefault() {
        return directoryDriverDefault;
    }

    public String getDefaultDriverFileNamePathAbsolute(){
        return ConManager.getDefaultDriverFileNamePathAbsolute(this);
    }

    public String getForName() {
        return forName;
    }

    public ConConnectionEditingTreat getConConnectionEditingTreat() {
        return conConnectionEditingTreat;
    }

    public ConConnectorEditingTreat getConConnectorEditingTreat() {
        return conConnectorEditingTreat;
    }
}
