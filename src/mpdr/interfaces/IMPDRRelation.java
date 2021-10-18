package mpdr.interfaces;

import m.interfaces.IMRelEnd;
import md.interfaces.IMDElementWithSource;
import mdr.MDRRelEnd;
import mdr.MDRRelFKEnd;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.interfaces.IMLDRElementWithSource;

public interface IMPDRRelation extends IMPDRElement, IMPDRElementWithSource, IMDRElementWithIteration {

    public IMRelEnd getA();
    public IMRelEnd getB();
    /*
    public MDRRelFKEnd getEndParent();
    public MDRRelFKEnd getEndChild();

     */
}
