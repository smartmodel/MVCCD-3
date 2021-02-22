package mdr.orderbuildnaming;

import mcd.MCDEntity;
import preferences.Preferences;

public class MDROrderWordTableName extends MDROrderWord{

    public MDROrderWordTableName(String name) {
        super(name, Preferences.PACKAGE_SHORT_NAME_LENGTH * Preferences.PACKAGE_LEVEL_MAX +
                Preferences.MDR_SEPARATOR.length() +
                Preferences.ENTITY_SHORT_NAME_LENGTH );
    }

    public void setValue(MCDEntity mcdEntity){
        /*
        String value = "";

        Preferences preferences = PreferencesManager.instance().preferences();
        String path = mcdEntity.getShortPath(preferences.getMDR_PATH_SEP_FORMAT());

        if (StringUtils.isNotEmpty(path)){
            value = value + path + preferences.getMDR_PATH_SEP_FORMAT();
        }
        value = value + mcdEntity.getMldrTableName();

        super.setValue(value);

         */

        super.setValue(MDROrderService.getPath(mcdEntity) + mcdEntity.getMldrTableName());
    }

}
