package connexions;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum ConDB {
    ORACLE (Preferences.CON_DB_ORACLE),
    MYSQL (Preferences.CON_DB_MYSQL),
    POSTGRESQL (Preferences.CON_DB_POSTGRESQL);

    private final String name;

    ConDB(String name) {
        this.name = name;
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

}
