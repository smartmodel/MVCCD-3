package mdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MDRConstraintCustomNature {
    SPEC(Preferences.MDR_CONSTRAINT_CUSTOM_SPECIALIZED),
    AUDIT(Preferences.MDR_CONSTRAINT_CUSTOM_AUDIT),
    JNAL(Preferences.MDR_CONSTRAINT_CUSTOM_JNAL);

    private final String name;

    MDRConstraintCustomNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDRConstraintCustomNature findByText(String text){
        for (MDRConstraintCustomNature element: MDRConstraintCustomNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }


    public static int getIndex(MDRConstraintCustomNature nature){
        int i = -1 ;
        for (MDRConstraintCustomNature element: MDRConstraintCustomNature.values()){
            i++;
            if (element == nature) {
                return i;
            }
        }
        return i;
    }

}
