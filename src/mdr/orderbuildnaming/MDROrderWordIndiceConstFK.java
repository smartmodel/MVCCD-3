package mdr.orderbuildnaming;

import preferences.Preferences;

public class MDROrderWordIndiceConstFK extends MDROrderWord{

    public MDROrderWordIndiceConstFK(String name) {
        super(name, Preferences.MDR_INDICE_COL_FK_LENGTH);
    }

}
