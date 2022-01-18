package mpdr.tapis;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRTriggerUsage {
    TAPIS (Preferences.MPDR_TRIGGER_USAGE_TAPIS),
    WITHOUTTAPIS (Preferences.MPDR_TRIGGER_USAGE_WITHOUT_TAPIS);

    private final String name;

    MPDRTriggerUsage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRTriggerUsage findByText(String text){
        for (MPDRTriggerUsage element: MPDRTriggerUsage.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
