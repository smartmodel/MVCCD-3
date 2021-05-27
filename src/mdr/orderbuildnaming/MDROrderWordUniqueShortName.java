package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordUniqueShortName extends MDROrderWord{

    public MDROrderWordUniqueShortName(String name) {
        super(name, Preferences.UNIQUE_SHORT_NAME_LENGTH);
    }

}
