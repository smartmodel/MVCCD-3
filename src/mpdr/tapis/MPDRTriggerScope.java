package mpdr.tapis;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRTriggerScope {
    TABLE (Preferences.MPDR_TRIGGER_USAGE_TAPIS),
    VIEW (Preferences.MPDR_TRIGGER_USAGE_WITHOUT_TAPIS);

    private final String name;

    MPDRTriggerScope(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRTriggerScope findByText(String text){
        for (MPDRTriggerScope element: MPDRTriggerScope.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
