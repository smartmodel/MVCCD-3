package window.editor.preferences.project;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum PrefProjectMenu {
    GENERAL (Preferences.PREFERENCES_PROJECT_MENU_GENERAL),
    MCD (Preferences.PREFERENCES_PROJECT_MENU_MCD);

    private final String name;

    PrefProjectMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static PrefProjectMenu findByText(String text){
        for (PrefProjectMenu element: PrefProjectMenu.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public String toString(){
        return getText();
    }

}
