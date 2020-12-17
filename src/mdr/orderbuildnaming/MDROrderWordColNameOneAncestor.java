package mdr.orderbuildnaming;

import preferences.Preferences;
import utilities.IndexingName;

public class MDROrderWordColNameOneAncestor extends MDROrderWord {


    public MDROrderWordColNameOneAncestor(String name) {
        //TODO-1 Voir l'intérêt de lengthMax qui n'est pas utilisé de manière générale
        super(name, null);
    }


    public MDROrderWordColNameOneAncestor(String name, Integer lengthMax) {
        super(name, lengthMax);
    }

    public void setValue(String name) {

        IndexingName indexingName = new IndexingName(Preferences.MDR_INDICE_REGEXPR);
        super.setValue(indexingName.extractRoot(name));
    }

}
