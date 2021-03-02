package mpdr.interfaces;

import md.interfaces.IMDElementWithSource;
import mldr.interfaces.IMLDRElement;

public interface IMPDRElementWithSource extends IMDElementWithSource {

    public IMLDRElement getMldrElementSource();

    public void setMldrElementSource(IMLDRElement imldrElementSource);


}
