package mdr.orderbuildnaming;

import main.MVCCDElement;
import main.MVCCDElementService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.IndexingName;
import utilities.Trace;

import java.util.ArrayList;

public class MDROrderWordIndiceTableNN extends MDROrderWord{

    public MDROrderWordIndiceTableNN(String name) {
        super(name, Preferences.MDR_INDICE_TABLENN_LENGTH);
    }

    public void setValue(String name, ArrayList<MVCCDElement> brothers){
        Preferences preferences = PreferencesManager.instance().preferences();
        String strIndex ;

        // Dans un premier temps utilisé le paramétrage des colonnes de FK est utilisé pour l'indexation des tables n:n
        // où la reprise des noms de rôle ou d'association fait dépasser la limite de taille
        boolean c1 = preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2);

        IndexingName indexingName = new IndexingName(Preferences.MDR_INDICE_REGEXPR);
        int index = indexingName.indexInSiblings(name, MVCCDElementService.convertArrayMVCCDElementsToNames(brothers));
        boolean noShowIndex = c1  && (index < 2) ;
        if (noShowIndex) {
            strIndex = "";
        } else{
            strIndex = "" + index;
        }
        super.setValue(strIndex);
    }

}
