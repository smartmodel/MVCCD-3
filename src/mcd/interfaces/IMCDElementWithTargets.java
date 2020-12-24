package mcd.interfaces;

import md.interfaces.IMDElementWithTargets;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public interface IMCDElementWithTargets extends IMDElementWithTargets {


    public ArrayList<IMLDRElement> getImldrElementTargets() ;

    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) ;

    public String getName();


}
