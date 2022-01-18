package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordColName extends MDROrderWord{

    public MDROrderWordColName(String name) {
        super(name, Preferences.MCD_AID_COLUMN_NAME_LENGTH);
    }

}
