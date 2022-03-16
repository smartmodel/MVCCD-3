package mpdr.interfaces;

import mldr.MLDRTable;
import mpdr.tapis.MPDRBoxPackages;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;

import java.util.ArrayList;

public interface IMPDRTableRequirePackage {

    MPDRBoxPackages getMPDRBoxPackages();
    MPDRBoxPackages createBoxPackages(MLDRTable mldrTable);

    ArrayList<MPDRPackage> getMPDRPackages();
    MPDRPackage getMPDRPackageByType(MPDRPackageType type);

    MPDRPackage createPackage(MPDRPackageType type, MLDRTable mldrTable);
}
