package connections;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum ConIDDBName {
    NAME_STD(Preferences.CON_IDDB_NAME_STD),
    SID(Preferences.CON_IDDB_NAME_SID),
    SERVICE_NAME(Preferences.CON_IDDB_NAME_SERVICE_NAME);

    private final String name;

    ConIDDBName(String name) {
        this.name = name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public String getName(){
        return name;
    }

    public static ConIDDBName findByName(String name){
        for (ConIDDBName element: ConIDDBName.values()){
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    public static ConIDDBName findByText(String text){
        for (ConIDDBName element: ConIDDBName.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
