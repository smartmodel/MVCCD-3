package mdr.orderbuildnaming;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MDROrderWordIndiceTableNN extends MDROrderWordIndice{

    public MDROrderWordIndiceTableNN(String name) {
        super(name, Preferences.MDR_INDICE_TABLENN_LENGTH);
    }

    //TODO-0
    // A factoriser avec indice de col et partie de FK

    @Override
    protected Integer compute(String racine, ArrayList<MVCCDElement> brothers) {
        Preferences preferences = PreferencesManager.instance().preferences();

        Integer indice = null;


        if (preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1)){
            int nbRacinesEqv = nbColumnFKByRoot(racine, brothers);
            if (nbRacinesEqv >= 0) indice = nbRacinesEqv + 1;
        }

        if (preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2)){
            int nbRacinesEqv = nbColumnFKByRoot(racine,  brothers);
            if (nbRacinesEqv >= 1) indice = nbRacinesEqv + 1;
        }

        return indice;

    }

}
