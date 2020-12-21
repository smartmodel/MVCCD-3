package mcd.interfaces;

import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public interface IMCDElementWithTargets {


    public ArrayList<IMLDRElement> getImldrElementTargets() ;

    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) ;

    public String getName();


}
