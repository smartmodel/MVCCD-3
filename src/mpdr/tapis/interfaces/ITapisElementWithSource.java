package mpdr.tapis.interfaces;

import md.interfaces.IMDElementWithSource;
import mpdr.interfaces.IMPDRElement;

public interface ITapisElementWithSource extends IMDElementWithSource {

    public IMPDRElement getMpdrElementSource();

    public void setMpdrElementSource(IMPDRElement impdrElementSource);


}
