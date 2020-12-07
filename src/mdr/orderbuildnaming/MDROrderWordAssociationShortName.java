package mdr.orderbuildnaming;

import mcd.MCDGSEnd;
import mcd.MCDRelEnd;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordAssociationShortName extends MDROrderWord{

    public MDROrderWordAssociationShortName(String name) {
        super(name, Preferences.ASSOCIATION_SHORT_NAME_LENGTH);
    }

}
