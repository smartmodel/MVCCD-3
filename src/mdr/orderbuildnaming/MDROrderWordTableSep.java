package mdr.orderbuildnaming;

import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordTableSep extends MDROrderWord{

    public MDROrderWordTableSep(String name) {
        super(name, Preferences.MDR_SEPARATOR.length());
    }



    public void setValue(){
        Preferences preferences = PreferencesManager.instance().preferences();
        super.setValue(preferences.getMDR_TABLE_SEP_FORMAT());
    }

}
