package mdr.orderbuildnaming;

import mpdr.MPDRTriggerType;
import preferences.Preferences;

public class MDROrderWordTypeTriggerMarker extends MDROrderWord{

    public MDROrderWordTypeTriggerMarker(String name) {
        super(name, Preferences.MARKER_TRIGGER_LENGTH );
    }

    public void setValue(MPDRTriggerType mpdrTriggerType){
        super.setValue(mpdrTriggerType.getMarker());
    }

}
