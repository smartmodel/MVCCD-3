package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordMPDRColumnName extends MDROrderWord{

    public MDROrderWordMPDRColumnName(String name) {

        super(name, Preferences.ATTRIBUTE_SHORT_NAME_LENGTH + Preferences.MDR_MARKER_CUSTOM_COLUMN_NAME_LENGTH);
    }

}
