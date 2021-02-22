package mdr.orderbuildnaming;

import mcd.MCDEntity;
import mcd.MCDEntityNature;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MDROrderWordAttrName extends MDROrderWord{

    public MDROrderWordAttrName(String name) {
        super(name, Preferences.ATTRIBUTE_NAME_LENGTH);
    }

    public void setValue(MCDEntity mcdEntity){
        String value = null;

        Preferences preferences = PreferencesManager.instance().preferences();

            MCDEntityNature mcdEntityNature = mcdEntity.getNature();
            if (mcdEntityNature == MCDEntityNature.IND) {
                value = PreferencesManager.instance().preferences().getMCD_AID_IND_COLUMN_NAME();
            }
            if (    (mcdEntityNature == MCDEntityNature.DEP) ||
                    (mcdEntityNature == MCDEntityNature.ENTASSDEP) ||
                    (mcdEntityNature == MCDEntityNature.NAIREDEP) ) {
                if (PreferencesManager.instance().preferences().getMCDTOMLDR_MODE().equals(
                        Preferences.MCDTOMLDR_MODE_DT)) {
                    value = PreferencesManager.instance().preferences().getMCD_AID_DEP_COLUMN_NAME();
                }
                if (PreferencesManager.instance().preferences().getMCDTOMLDR_MODE().equals(
                        Preferences.MCDTOMLDR_MODE_TI)) {
                    value = PreferencesManager.instance().preferences().getMCD_AID_IND_COLUMN_NAME();
                }
            }

        super.setValue(value);
    }

}
