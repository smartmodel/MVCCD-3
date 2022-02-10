package mdr.orderbuildnaming;

import mcd.MCDEntity;
import preferences.Preferences;

public class MDROrderWordTableShortName extends MDROrderWord{

    public MDROrderWordTableShortName(String name) {
        super(name, Preferences.PACKAGE_SHORT_NAME_LENGTH * Preferences.PACKAGE_LEVEL_MAX +
                Preferences.MDR_SEPARATOR.length() +
                Preferences.ENTITY_SHORT_NAME_LENGTH );
    }

    public void setValue(MCDEntity mcdEntity){
        super.setValue(MDROrderService.getPath(mcdEntity) + mcdEntity.getShortName());
    }

}
