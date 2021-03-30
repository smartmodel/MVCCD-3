package mdr.orderbuildnaming;

import mcd.MCDGSEnd;
import mcd.MCDRelEnd;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class  MDROrderWordRoleShortName extends MDROrderWord{

    public MDROrderWordRoleShortName(String name) {
        super(name, Preferences.ASSEND_ROLE_SHORT_NAME_LENGTH);
    }

}
