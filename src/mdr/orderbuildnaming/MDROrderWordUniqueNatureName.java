package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordUniqueNatureName extends MDROrderWord{

    public MDROrderWordUniqueNatureName(String name) {
        super(name, Preferences.UNIQUE_NATURE_NAME_LENGTH);
    }

}
