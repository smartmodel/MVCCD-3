package mdr.orderbuildnaming;

import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mpdr.MPDRModel;
import mpdr.tapis.MPDRViewType;
import preferences.Preferences;

public class MDROrderWordTypeViewMarker extends MDROrderWord{

    public MDROrderWordTypeViewMarker(String name) {
        super(name, Preferences.MARKER_VIEW_LENGTH );
    }

    public void setValue(MPDRModel mpdrModel, MPDRViewType mpdrViewType){
        MDRCaseFormat mdrCaseFormat = mpdrModel.getNamingFormatForDB();
        super.setValue(MDRModelService.caseNaming(mpdrViewType.getMarker(), mdrCaseFormat));
    }

}
