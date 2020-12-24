package transform;

import mcd.interfaces.IMCDElementWithTargets;
import md.MDElement;
import md.interfaces.IMDElementWithTargets;
import mldr.interfaces.IMLDRElement;

import java.util.ArrayList;

public class MDTransformElementTrace {

    private IMDElementWithTargets imdElementWithTargets;

    private ArrayList<MDElement> mdElementTargets = new ArrayList<MDElement>();


    public MDTransformElementTrace(IMDElementWithTargets imdElementWithTargets) {
        this.imdElementWithTargets = imdElementWithTargets;
    }

    public IMDElementWithTargets getImdElementWithTargets() {
        return imdElementWithTargets;
    }

    public ArrayList<MDElement> getMdElementTargets() {
        return mdElementTargets;
    }
}
