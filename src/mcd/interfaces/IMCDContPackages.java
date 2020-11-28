package mcd.interfaces;

import project.ProjectService;

// Implémentée par les classes qui peuvent contenir des paquetages
public interface IMCDContPackages {

    public abstract String getNamePath(int pathMode);

    public static IMCDContPackages getIMCDContPackagesByNamePath(int pathMode, String namePath){
        for (IMCDContPackages imcdContPackages: ProjectService.getIMCDContPackages()){
            if (imcdContPackages.getNamePath(pathMode).equals(namePath)){
                return imcdContPackages;
            }
        }
        return null;
    }

}
