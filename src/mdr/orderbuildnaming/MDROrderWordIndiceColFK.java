package mdr.orderbuildnaming;

import main.MVCCDElement;
import main.MVCCDElementService;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.IndexingName;
import utilities.Trace;

import java.util.ArrayList;

public class MDROrderWordIndiceColFK extends MDROrderWord{

    public MDROrderWordIndiceColFK(String name) {
        super(name, Preferences.MDR_INDICE_COL_FK_LENGTH);
    }

    public void setValue(String name, ArrayList<MVCCDElement> brothers, int indiceFK){
        Preferences preferences = PreferencesManager.instance().preferences();

        int indice = 0;

        boolean c1 = preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK);
        boolean c2 = preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1);
        boolean c3 = preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2);

        if (c1){
            indice = indiceFK ;
        }

        if (c2 || c3) {
            IndexingName indexingName = new IndexingName(Preferences.MDR_INDICE_REGEXPR);
            indice = indexingName.indexInBrothers(name, MVCCDElementService.convertArrayMVCCDElementsToNames(brothers));
            if (c3) {
                if (indice < 2) {
                    indice = 0;
                };
            }
        }

        if (indice != 0){
            super.setValue("" + indice);
        } else {
            super.setValue("");
        }
    }

}
