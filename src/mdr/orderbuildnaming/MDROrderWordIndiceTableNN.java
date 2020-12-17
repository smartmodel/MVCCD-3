package mdr.orderbuildnaming;

import main.MVCCDElement;
import main.MVCCDElementService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.IndexingName;

import java.util.ArrayList;

public class MDROrderWordIndiceTableNN extends MDROrderWord{

    public MDROrderWordIndiceTableNN(String name) {
        super(name, Preferences.MDR_INDICE_TABLENN_LENGTH);
    }

    public void setValue(String name, ArrayList<MVCCDElement> brothers){
        Preferences preferences = PreferencesManager.instance().preferences();

        int indice = 0;

        boolean c1 = preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1);
        boolean c2 = preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2);

        if (c1 || c2) {
            int startIndex = 0;
            if (c1) {
                startIndex = 1;
            }
            if (c2) {
                startIndex = 2;
            }
            IndexingName indexingName = new IndexingName(Preferences.MDR_INDICE_REGEXPR);
            indice = indexingName.indexInBrothers(name, MVCCDElementService.convertArrayMVCCDElementsToNames(brothers));
            boolean c3 = (indice == 1);
            if (c2 && c3) {
                indice = 0;
            }
        }

        if (indice != 0){
            super.setValue("" + indice);
        } else {
            super.setValue("");
        }
    }

}
