package mpdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRDB {
    ORACLE (Preferences.DB_ORACLE),
    MYSQL (Preferences.DB_MYSQL),
    POSTGRESQL (Preferences.DB_POSTGRESQL);

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
