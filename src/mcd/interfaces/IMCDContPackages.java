package mcd.interfaces;

import mcd.services.IMCDModelService;

// Implémentée par les classes qui peuvent contenir des paquetages
public interface IMCDContPackages {

    public abstract String getNamePath();

    public static IMCDContPackages getIMCDContPackagesByNamePath(IMCDModel imcdModel, String namePath){
        for (IMCDContPackages imcdContPackages: IMCDModelService.getIMCDContPackages(imcdModel)){
            if (imcdContPackages.getNamePath().equals(namePath)){
                return imcdContPackages;
            }
        }
        return null;
    }

}
