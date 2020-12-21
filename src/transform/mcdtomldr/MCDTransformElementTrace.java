package transform.mcdtomldr;

import mcd.interfaces.IMCDElementWithTargets;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public class MCDTransformElementTrace {

    private IMCDElementWithTargets imcdElementWithTargets;

    private ArrayList<IMLDRElement> imldrElementTargets = new ArrayList<IMLDRElement>();

    public MCDTransformElementTrace(IMCDElementWithTargets imcdElementWithTargets) {
        this.imcdElementWithTargets = imcdElementWithTargets;
    }

    public IMCDElementWithTargets getImcdElementWithTargets() {
        return imcdElementWithTargets;
    }


    public ArrayList<IMLDRElement> getImldrElementTargets() {
        return imldrElementTargets;
    }

    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) {
        this.imldrElementTargets = imldrElementTargets;
    }
}
