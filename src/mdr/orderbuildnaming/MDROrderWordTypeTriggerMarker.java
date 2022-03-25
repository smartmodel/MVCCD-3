package mdr.orderbuildnaming;

import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mpdr.MPDRModel;
import mpdr.tapis.MPDRTriggerType;
import preferences.Preferences;

public class MDROrderWordTypeTriggerMarker extends MDROrderWord{

    public MDROrderWordTypeTriggerMarker(String name) {
        super(name, Preferences.MARKER_TRIGGER_LENGTH );
    }

    public void setValue(MPDRModel mpdrModel, MPDRTriggerType mpdrTriggerType){
        MDRCaseFormat mdrCaseFormat = mpdrModel.getNamingFormatForDB();
        super.setValue(MDRModelService.caseFormat(mpdrTriggerType.getMarker(), mdrCaseFormat));
    }

}
