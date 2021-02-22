package preferences.services;

import mdr.MDRModel;
import mdr.MDRNamingLength;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectService;

public class PrefMDRService {

     public static void adjustMDRPrefNaming(MDRNamingLength namingLength) {
        Preferences preferences = PreferencesManager.instance().preferences();

        for (MDRNamingLength mdrNamingLength : MDRNamingLength.values()){
            if (preferences.getMLDR_PREF_NAMING_LENGTH() == mdrNamingLength){
                if (!mdrNamingLength.isRequired()) {
                    adjustMDRPrefNamingInternal();
                }
            }
        }
    }

    private static void adjustMDRPrefNamingInternal() {
        Preferences preferences = PreferencesManager.instance().preferences();
        // Préférence pour MDR-Préférences
        for (MDRNamingLength mdrNamingLength : MDRNamingLength.values()) {
            if (mdrNamingLength.isRequired()) {
                // Remplacement de la préférence
                // Logique
                preferences.setMLDR_PREF_NAMING_LENGTH(mdrNamingLength);
                // Physique
                preferences.setMPDRORACLE_PREF_NAMING_LENGTH(mdrNamingLength);
                // Sortie après la 1ère affectation
                break ;
            }
        }
        // Valeur par défaut pour toutes les instances de MDRModel (Logiques et physiques)
        for (MDRModel mdrModel : ProjectService.getMDRModels()){
            for (MDRNamingLength mdrNamingLength : MDRNamingLength.values()) {
                if (mdrNamingLength.isRequired()) {
                    // Remplacement de la valeur par défaut
                    mdrModel.setNamingLengthFuture(mdrNamingLength);
                    break ;
                }
            }
        }
    }

}
