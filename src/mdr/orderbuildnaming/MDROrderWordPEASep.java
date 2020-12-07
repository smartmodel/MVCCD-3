package mdr.orderbuildnaming;

import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordPEASep extends MDROrderWord{

    public MDROrderWordPEASep(String name) {
        super(name, Preferences.MDR_SEPARATOR.length());
    }



    public void setValue(){
        Preferences preferences = PreferencesManager.instance().preferences();

        super.setValue(preferences.getMDR_PEA_SEP_FORMAT());
    }

}
