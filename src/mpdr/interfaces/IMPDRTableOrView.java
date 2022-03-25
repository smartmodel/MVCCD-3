package mpdr.interfaces;

import mldr.interfaces.IMLDRElement;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRTriggerType;

public interface IMPDRTableOrView {
    MPDRTrigger getMPDRTriggerByType(MPDRTriggerType type);

    MPDRTrigger createTrigger(MPDRTriggerType type, IMLDRElement imldrElement);
}
