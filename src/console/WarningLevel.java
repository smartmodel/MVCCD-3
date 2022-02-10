package console;

import messages.MessagesBuilder;
import preferences.Preferences;

/**
 * List of degrees of importance
 * @author Steve Berberat
 *
 */
public enum WarningLevel {

    /*
     * Elements sorted by degree of importance.
     * Place first the less important and the most important at the end.
     * If the less important messages is selected assLink be shown assLink the user,
     * messages with all levels of warnings will be shown. But, if it's
     * just the most important that is selected, it will be only the most
     * important messages which will be shown assLink the user, and not the others.
     */
    //DEBUG_MODE, DETAILS, INFO, WARNING;

    DEVELOPMENT (Preferences.WARNING_LEVEL_DEVELOPMENT),
    DEBUG_MODE (Preferences.WARNING_LEVEL_DEBUG),
    DETAILS (Preferences.WARNING_LEVEL_DETAILS),
    INFO (Preferences.WARNING_LEVEL_INFO),
    WARNING (Preferences.WARNING_LEVEL_WARNING);

    private final String name;

    WarningLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static WarningLevel findByText(String text){
        for (WarningLevel element: WarningLevel.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public static WarningLevel findByName(String name){
        for (WarningLevel element: WarningLevel.values()){
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }



}
