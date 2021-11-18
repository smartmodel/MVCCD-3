package connections;

import messages.MessagesBuilder;

public enum ConIDDBNameOracle {
    /*ID(Preferences.CON_IDDB_NAME_ORACLE_SID),
    SERVICE_NAME(Preferences.CON_IDDB_NAME_ORACLE_SERVICE_NAME)

     */
    ;

    private final String name;

    ConIDDBNameOracle(String name) {
        this.name = name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

}
