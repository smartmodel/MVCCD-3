package mcd.services;

import mcd.MCDEntity;
import mcd.MCDModel;
import mcd.MCDPackage;
import preferences.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDPackageService {


    public static ArrayList<String> check(MCDPackage mcdPackage) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(mcdPackage.getName()));
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.PACKAGE_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "package.and.name");

    }

    public static ArrayList<String> checkShortName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.PACKAGE_SHORT_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "package.and.short.name");

    }


    public static void sortNameAsc(ArrayList<MCDPackage> packages){
        Collections.sort(packages, NAME_ASC);
    }

    static final Comparator<MCDPackage> NAME_ASC =
            new Comparator<MCDPackage>() {
                public int compare(MCDPackage e1, MCDPackage e2) {
                    return e1.getName().compareTo(e2.getName());
                }
    };
}
