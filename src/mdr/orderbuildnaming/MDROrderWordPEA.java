package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordPEA extends MDROrderWord{

    public MDROrderWordPEA(String name) {
        super(name, Preferences.ENTITY_SHORT_NAME_LENGTH);
    }




}
