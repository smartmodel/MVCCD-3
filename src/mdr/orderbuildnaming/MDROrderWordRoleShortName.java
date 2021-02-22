package mdr.orderbuildnaming;

import preferences.Preferences;

public class  MDROrderWordRoleShortName extends MDROrderWord{

    public MDROrderWordRoleShortName(String name) {
        super(name, Preferences.ASSEND_ROLE_SHORT_NAME_LENGTH);
    }


}
