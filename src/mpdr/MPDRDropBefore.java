package mpdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRDropBefore {
    NOTHING (Preferences.MPDR_DROP_BEFORE_NOTHING),
    OBJECTSCREATED (Preferences.MPDR_DROP_BEFORE_OBJECTS_CREATED),
    EMPTY (Preferences.MPDR_DROP_BEFORE_EMPTY);

    private final String name;

    MPDRDropBefore(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRDropBefore findByText(String text){
        for (MPDRDropBefore element: MPDRDropBefore.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
