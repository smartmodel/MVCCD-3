package mdr.interfaces;

import md.MDElement;
import mldr.interfaces.IMLDRElement;

public interface IMDRElementWithSource {

    public MDElement getMdElementSource();
    public void setMdElementSource(MDElement mdElementSource);

}
