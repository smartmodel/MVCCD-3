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

    public void setValue (MCDRelEnd mcdRelEnd){
        String value = null;

        Preferences preferences = PreferencesManager.instance().preferences();
        if (StringUtils.isNotEmpty(mcdRelEnd.getShortName())){
            value = mcdRelEnd.getShortName();
        } else if (StringUtils.isNotEmpty(mcdRelEnd.getImRelation().getShortName())){
            value = mcdRelEnd.getImRelation().getShortName();
        } else if(mcdRelEnd instanceof MCDGSEnd){
            value = preferences.getMDR_ROLE_GENERALIZE_MARKER();
        }

        super.setValue(value);
    }

}
