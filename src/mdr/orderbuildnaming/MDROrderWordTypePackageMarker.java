package mdr.orderbuildnaming;

import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mpdr.MPDRModel;
import mpdr.tapis.MPDRPackageType;
import preferences.Preferences;

public class MDROrderWordTypePackageMarker extends MDROrderWord{

    public MDROrderWordTypePackageMarker(String name) {
        super(name, Preferences.MARKER_PACKAGE_LENGTH );
    }

    public void setValue(MPDRModel mpdrModel, MPDRPackageType mpdrPackageType){
        MDRCaseFormat mdrCaseFormat = mpdrModel.getNamingFormatForDB();
        super.setValue(MDRModelService.caseNaming(mpdrPackageType.getMarker(), mdrCaseFormat));
    }

}
