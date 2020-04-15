package mcd.services;

import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDModel;
import mcd.MCDPackage;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import preferences.Preferences;
import project.ProjectService;

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

    public static ArrayList<IMCDContPackages> getIMCDContPackagesInIModel(IMCDModel iMCDModel) {
        ArrayList<IMCDContPackages> resultat =  new ArrayList<IMCDContPackages>();
        for (MCDElement element :  IMCDModelService.getMCDElementsByClassName(
                iMCDModel, true, IMCDContPackages.class.getName())){
            resultat.add((IMCDContPackages) element);
        }
        return resultat;
    }

    public static ArrayList<MCDElement> toMCDElements(ArrayList<IMCDContPackages> imcdContPackages) {
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        for (IMCDContPackages imcdContPackage : imcdContPackages){
            resultat.add((MCDElement) imcdContPackage);
        }
        return resultat;
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


    public static MCDPackage getMCDPackageByNamePath(int pathMode, String namePath){
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)){
            if (mcdElement instanceof MCDPackage){
                return (MCDPackage) mcdElement;
            }
        }
        return null;
    }

}
