package mpdr.interfaces;

import m.interfaces.IMRelEnd;
import mdr.interfaces.IMDRElementWithIteration;

public interface IMPDRRelation extends IMPDRElement, IMPDRElementWithSource, IMDRElementWithIteration {

    public IMRelEnd getA();
    public IMRelEnd getB();
    /*
    public MDRRelFKEnd getEndParent();
    public MDRRelFKEnd getEndChild();

     */
}
