package mcd.services;

import mcd.MCDElement;
import mcd.MCDPackage;
import mcd.interfaces.IMCDContPackages;
import mcd.interfaces.IMCDModel;
import project.ProjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDPackageService {


    public static ArrayList<String> check(MCDPackage mcdPackage) {
        ArrayList<String> messages = new ArrayList<String>();
        return messages;
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
        for (MCDPackage mcdPackage : ProjectService.getMCDPackages()){
            if (mcdPackage.getNamePath(pathMode).equals(namePath)){
                return mcdPackage;
            }
        }
        return null;
    }

}
