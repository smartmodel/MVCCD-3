package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordAttrShortName extends MDROrderWord{

    public MDROrderWordAttrShortName(String name) {
        super(name, Preferences.ATTRIBUTE_SHORT_NAME_LENGTH);
    }



}
