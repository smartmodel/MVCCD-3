package mpdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRDB {
    ORACLE (Preferences.MPDR_DB_ORACLE),
    MYSQL (Preferences.MPDR_DB_MYSQL),
    POSTGRESQL (Preferences.MPDR_DB_POSTGRESQL);

    private final String name;

    MPDRDB(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRDB findByText(String text){
        for (MPDRDB element: MPDRDB.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
