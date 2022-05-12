package mpdr.interfaces;

import mldr.MLDRModel;
import mpdr.tapis.MPDRBoxPackages;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;

import java.util.ArrayList;

public interface IMPDRModelRequirePackage {

    String getPackageNameFormat();
    void setPackageNameFormat(String packageNameFormat) ;

    MPDRBoxPackages getMPDRBoxPackages();
    MPDRBoxPackages createBoxPackages(IMPDRModelRequirePackage mpdrModel, MLDRModel mldrModel);

    ArrayList<MPDRPackage> getMPDRPackages();
    MPDRPackage getMPDRPackageByType(MPDRPackageType type);
    MPDRPackage createPackage(MPDRPackageType type, MLDRModel mldrModel);

}
