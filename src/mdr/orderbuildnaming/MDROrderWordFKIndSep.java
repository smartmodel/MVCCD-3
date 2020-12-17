package mdr.orderbuildnaming;

import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordFKIndSep extends MDROrderWord{

    public MDROrderWordFKIndSep(String name) {
        super(name, Preferences.MDR_SEPARATOR.length());
    }



    public void setValue(){
        Preferences preferences = PreferencesManager.instance().preferences();

        super.setValue(preferences.getMDR_FKIND_SEP_FORMAT());
    }

}
