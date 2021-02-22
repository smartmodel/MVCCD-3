package mdr;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

public enum MDRNamingLength {
    LENGTH30 (Preferences.MDR_NAMING_LENGTH_30, 30, true),
    LENGTH60 (Preferences.MDR_NAMING_LENGTH_60, 60, true),
    LENGTH120 (Preferences.MDR_NAMING_LENGTH_120, 120, true);

    private final String name;
    private final int length;
    private  boolean required = true;

    MDRNamingLength(String name, int length, boolean required) {
        this.name = name;
        this.length = length;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDRNamingLength findByText(String text){
        for (MDRNamingLength element: MDRNamingLength.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
