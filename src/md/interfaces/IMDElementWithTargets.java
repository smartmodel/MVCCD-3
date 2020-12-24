package md.interfaces;

import md.MDElement;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public interface IMDElementWithTargets {

    public String getName();
    public ArrayList<MDElement> getMdElementTargets() ;
    public void setMdElementTargets(ArrayList<MDElement> mdElementTargets) ;

}
