package mpdr.interfaces;

import mldr.MLDRTable;
import mpdr.tapis.MPDRBoxPackages;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;

public interface IMPDRTableRequirePackages {

    MPDRBoxPackages getMPDRBoxPackages();
    MPDRBoxPackages createBoxPackages(MLDRTable mldrTable);

    MPDRPackage getMPDRPackageByType(MPDRPackageType type);

    MPDRPackage createPackage(MPDRPackageType type, MLDRTable mldrTable);
}
