package mpdr;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

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

    public String getDelimiterInstructions(){
        if (this == ORACLE){
            return PreferencesManager.instance().preferences().getMPDRORACLE_DELIMITER_INSTRUCTIONS();
        } else if (this == MYSQL){
            return PreferencesManager.instance().preferences().getMPDRMYSQL_DELIMITER_INSTRUCTIONS();
        } else if (this == POSTGRESQL){
            return PreferencesManager.instance().preferences().getMPDRPOSTGRESQL_DELIMITER_INSTRUCTIONS();
        } else {
            throw new CodeApplException("La méthode n'a pas de retour pour cette valeur de Database");
        }
    }


}
