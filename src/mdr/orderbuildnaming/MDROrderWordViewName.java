package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordViewName extends MDROrderWord{

    public MDROrderWordViewName(String name) {
        super(name, Preferences.MPDR_VIEW_NAME_LENGTH);
    }

}
