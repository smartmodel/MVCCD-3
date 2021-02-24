package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordAssociationShortName extends MDROrderWord{

    public MDROrderWordAssociationShortName(String name) {
        super(name, Preferences.ASSOCIATION_SHORT_NAME_LENGTH);
    }

}
