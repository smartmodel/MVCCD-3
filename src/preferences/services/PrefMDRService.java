package preferences.services;

import exceptions.CodeApplException;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

public class PrefMDRService {

    //TODO-1 Remplacer les getMLDR_PREF_NAMING_LENGTH() par des accès à la classe MDRNamingLength
    public static void adjustMLDRPrefNaming(MDRNamingLength namingLength) {
        Preferences preferences = PreferencesManager.instance().preferences();
        Integer mldrLength ;
        if (preferences.getMLDR_PREF_NAMING_LENGTH().equals (Preferences.MDR_NAMING_LENGTH_30)){
            mldrLength = MDRNamingLength.LENGTH30.getLength();
        }
        else if (preferences.getMLDR_PREF_NAMING_LENGTH().equals (Preferences.MDR_NAMING_LENGTH_60)){
            mldrLength = MDRNamingLength.LENGTH60.getLength();
        }
        else if (preferences.getMLDR_PREF_NAMING_LENGTH().equals (Preferences.MDR_NAMING_LENGTH_120)){
            mldrLength = MDRNamingLength.LENGTH120.getLength();
        } else {
            throw new CodeApplException("PrefMDRService.adjustMLDRPrefNaming() - Paramètre naming inconnu ");
        }
        if (namingLength == MDRNamingLength .LENGTH30) {
            if (!preferences.getMDR_PREF_NAMING_LENGTH_30_REQUIRED()) {
                if (mldrLength == (int) MDRNamingLength.LENGTH30.getLength()) {
                    adjustMLDRPrefNamingInternal();
                }
            }
        }
        if (namingLength == MDRNamingLength .LENGTH60) {
            if (!preferences.getMDR_PREF_NAMING_LENGTH_60_REQUIRED()) {
                if (mldrLength == (int) MDRNamingLength.LENGTH60.getLength()) {
                    adjustMLDRPrefNamingInternal();
                }
            }
        }
        if (namingLength == MDRNamingLength .LENGTH120) {
            if (!preferences.getMDR_PREF_NAMING_LENGTH_120_REQUIRED()) {
                if (mldrLength == (int) MDRNamingLength.LENGTH120.getLength()) {
                    adjustMLDRPrefNamingInternal();
                }
            }
        }
    }

    private static void adjustMLDRPrefNamingInternal() {
        Preferences preferences = PreferencesManager.instance().preferences();
        if (preferences.getMDR_PREF_NAMING_LENGTH_30_REQUIRED()) {
            preferences.setMLDR_PREF_NAMING_LENGTH(preferences.MDR_NAMING_LENGTH_30);
        }
         else if (preferences.getMDR_PREF_NAMING_LENGTH_60_REQUIRED()) {
            preferences.setMLDR_PREF_NAMING_LENGTH(preferences.MDR_NAMING_LENGTH_60);
        }
        else if (preferences.getMDR_PREF_NAMING_LENGTH_120_REQUIRED()) {
            preferences.setMLDR_PREF_NAMING_LENGTH(preferences.MDR_NAMING_LENGTH_120);
        }
        else {
             throw new CodeApplException("PrefMDRService.adjustMLDRPrefNamingInternal() - Paramètre naming inconnu ");
        }
    }

}
