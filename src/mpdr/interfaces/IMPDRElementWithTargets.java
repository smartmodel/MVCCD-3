package mpdr.interfaces;

import md.interfaces.IMDElementWithTargets;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public interface IMPDRElementWithTargets extends IMDElementWithTargets {


    public ArrayList<IMPDRElement> getImpdrElementTargets() ;

    public void setImpdrElementTargets(ArrayList<IMPDRElement> impdrElementTargets) ;


}
