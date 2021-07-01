package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordUniqueName extends MDROrderWord{

    public MDROrderWordUniqueName(String name) {
        super(name, Preferences.UNIQUE_NAME_LENGTH);
    }

}
