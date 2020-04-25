package preferences.services;

import mcd.services.MCDUtilService;
import preferences.Preferences;

import java.util.ArrayList;

public class PrefMCDService {

    public static ArrayList<String> checkAIDIndName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.ATTRIBUTE_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "naming.of.name.AID","of.entity.ind");

    }

    public static ArrayList<String> checkAIDDepName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.ATTRIBUTE_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "naming.of.name.AID","of.entity.dep");

    }
}
