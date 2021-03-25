package mdr.orderbuildnaming;

import main.MVCCDElement;
import main.MVCCDElementService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.IndexingName;

import java.util.ArrayList;

public class MDROrderWordIndiceColFK extends MDROrderWord{

    public MDROrderWordIndiceColFK(String name) {
        super(name, Preferences.MDR_INDICE_COL_FK_LENGTH);
    }

    public void setValue(String name, ArrayList<MVCCDElement> brothers, int indiceFK){
        Preferences preferences = PreferencesManager.instance().preferences();
        String strIndex ;

        // indiceFK est abandoné car la succession de valeur de FK en remontant l'ascendance peut être ambigue!
        // A voir
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
