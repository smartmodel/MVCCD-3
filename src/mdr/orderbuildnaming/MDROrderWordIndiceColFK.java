package mdr.orderbuildnaming;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MDROrderWordIndiceColFK extends MDROrderWordIndice{

    public MDROrderWordIndiceColFK(String name) {
        super(name, Preferences.MDR_INDICE_COL_FK_LENGTH);
    }

    @Override
    protected Integer compute(String racine, ArrayList<MVCCDElement> brothers) {
        Preferences preferences = PreferencesManager.instance().preferences();

        Integer indice = null;

        if (preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK)){
            //TODO-0 calculer l'indice de FK !
            //indice = indiceFK;
        }

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
