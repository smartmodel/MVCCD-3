package mdr.orderbuildnaming;

import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordUniqueNatureSep extends MDROrderWord{

    public MDROrderWordUniqueNatureSep(String name) {

        super(name, Preferences.MDR_SEPARATOR.length());
    }



    public void setValue(){
        Preferences preferences = PreferencesManager.instance().preferences();

        super.setValue(preferences.getMDR_UNIQUE_NATURE_SEP_FORMAT());
    }

}
