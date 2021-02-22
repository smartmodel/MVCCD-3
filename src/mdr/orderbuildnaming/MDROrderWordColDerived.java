package mdr.orderbuildnaming;

import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordColDerived extends MDROrderWord{

    public MDROrderWordColDerived(String name) {
        super(name, Preferences.MDR_MARKER_DERIVED_LENGTH) ;
    }



    public void setValue(){
        Preferences preferences = PreferencesManager.instance().preferences();

        super.setValue(preferences.getMDR_COLUMN_DERIVED_MARKER());
    }

}
