package mdr.orderbuildnaming;

import mcd.MCDEntity;
import mcd.MCDEntityNature;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordAttrShortName extends MDROrderWord{

    public MDROrderWordAttrShortName(String name) {
        super(name, Preferences.ATTRIBUTE_SHORT_NAME_LENGTH);
    }



}
