package mcd.interfaces;

import mcd.MCDElement;
import mcd.MCDPackage;
import project.ProjectService;

// Implémentée par les classes qui peuvent contenir des paquetages
public interface IMCDContPackages {


    public static IMCDContPackages getIMCDContPackagesByNamePath(int pathMode, String namePath){
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)){
            if (mcdElement instanceof IMCDContPackages){
                return (IMCDContPackages) mcdElement;
            }
        }
        return null;
    }

}
