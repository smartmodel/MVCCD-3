package connections;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum ConDBMode {
    CONNECTION (Preferences.CON_DB_MODE_CONNECTION),
    CONNECTOR (Preferences.CON_DB_MODE_CONNECTOR);

    private final String name;

    ConDBMode(String name) {
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


    public static ConDBMode findByName(String name) {
        for (ConDBMode element : ConDBMode.values()) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

}
