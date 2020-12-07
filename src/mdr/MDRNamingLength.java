package mdr;

import exceptions.CodeApplException;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

public enum MDRNamingLength {
    LENGTH30 (Preferences.MDR_NAMING_LENGTH_30, 30),
    LENGTH60 (Preferences.MDR_NAMING_LENGTH_60, 60),
    LENGTH120 (Preferences.MDR_NAMING_LENGTH_120, 120);

    private final String name;
    private final Integer length;
    private  boolean required = true;

    MDRNamingLength(String name, Integer length) {
        this.name = name;
        this.length = length;
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
        Preferences preferences = PreferencesManager.instance().preferences();

        if (this == LENGTH30){
            return preferences.getMDR_PREF_NAMING_LENGTH_30_REQUIRED();
        }
        if (this == LENGTH60){
            return preferences.getMDR_PREF_NAMING_LENGTH_60_REQUIRED();
        }
        if (this == LENGTH120){
            return preferences.getMDR_PREF_NAMING_LENGTH_120_REQUIRED();
        }
        throw new CodeApplException("MDRNamingLength.isRequired - la taille passée en paramètre est inconnue." );
    }




}
