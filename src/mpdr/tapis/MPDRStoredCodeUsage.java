package mpdr.tapis;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRStoredCodeUsage {
    TAPIS (Preferences.MPDR_STOREDCODE_USAGE_TAPIS),
    WITHOUTTAPIS (Preferences.MPDR_STOREDCODE_USAGE_WITHOUT_TAPIS);

    private final String name;

    MPDRStoredCodeUsage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRStoredCodeUsage findByText(String text){
        for (MPDRStoredCodeUsage element: MPDRStoredCodeUsage.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
