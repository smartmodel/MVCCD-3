package mdr.orderbuildnaming;

import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordRoleSep extends MDROrderWord{

    public MDROrderWordRoleSep(String name) {
        super(name, Preferences.MDR_SEPARATOR.length());
    }



    public void setValue(){
        Preferences preferences = PreferencesManager.instance().preferences();

        super.setValue(preferences.getMDR_ROLE_SEP_FORMAT());
    }

}
