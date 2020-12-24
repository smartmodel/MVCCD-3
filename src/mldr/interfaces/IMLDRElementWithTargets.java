package mldr.interfaces;

import mcd.interfaces.IMCDElementWithTargets;
import md.interfaces.IMDElementWithTargets;
import mpdr.interfaces.IMPDRElement;

import java.util.ArrayList;

public interface IMLDRElementWithTargets extends IMDElementWithTargets {


    public ArrayList<IMLDRElement> getImldrElementTargets() ;

    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) ;


}
