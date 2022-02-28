package mpdr.tapis;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRStoredCodeScope {
    TABLE (Preferences.MPDR_TRIGGER_SCOPE_TABLE),
    VIEW (Preferences.MPDR_TRIGGER_SCOPE_VIEW);

    private final String name;

    MPDRStoredCodeScope(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRStoredCodeScope findByText(String text){
        for (MPDRStoredCodeScope element: MPDRStoredCodeScope.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
