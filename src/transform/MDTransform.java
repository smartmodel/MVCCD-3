package transform;

import delete.Delete;
import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import md.interfaces.IMDElementWithTargets;
import mdr.interfaces.IMDRElementWithIteration;
import project.ProjectService;
import utilities.Trace;
import utilities.UtilDivers;

import java.util.ArrayList;

public abstract class MDTransform {

    private ArrayList<MDTransformElementTrace> mdTransformElementTraces = new ArrayList<MDTransformElementTrace>();


    public void addInTrace( IMDElementWithTargets imdElementWithTargets, MDElement mdElement) {
        MDTransformElementTrace mdTransformElementTrace = findTrace(imdElementWithTargets);
        mdTransformElementTrace.getMdElementTargets().add(mdElement);
        ((IMDRElementWithIteration) mdElement).setIteration(getIteration());
    }

    protected abstract int getIteration();

    protected MDTransformElementTrace findTrace(IMDElementWithTargets imdElementWithTargets){
        for (MDTransformElementTrace mdTransformElementTrace : mdTransformElementTraces){
            if (mdTransformElementTrace.getImdElementWithTargets() == imdElementWithTargets ){
                return mdTransformElementTrace;
            }
        }
        return createTrace(imdElementWithTargets);
     }

    protected MDTransformElementTrace createTrace(IMDElementWithTargets imdElementWithTargets) {
        MDTransformElementTrace mdTransformElementTrace = new MDTransformElementTrace(imdElementWithTargets);
        mdTransformElementTraces.add(mdTransformElementTrace);
        return mdTransformElementTrace;
    }

    protected void recordTraces() {
        for (MDTransformElementTrace mdTransformElementTrace : mdTransformElementTraces){
            IMDElementWithTargets imdElementWithTargets = mdTransformElementTrace.getImdElementWithTargets();
            imdElementWithTargets.setMdElementTargets(mdTransformElementTrace.getMdElementTargets());
        }
    }

    protected void deleteMDRElementNotInIteration(){
        for (IMDRElementWithIteration imdrElementWithIteration : getIMDRElementWithIteration()){
            if ( imdrElementWithIteration instanceof IMDElementWithSource) {
                // vérifier que'une source soit présente
                MDElement mdElementSource = ((IMDElementWithSource) imdrElementWithIteration).getMdElementSource();
                if (mdElementSource != null){
                    //Vérifier que les 2 itérations soient différentes
                    if (imdrElementWithIteration.getIteration() != getIteration()){
                        Delete.deleteMVCCDElement((MVCCDElement) imdrElementWithIteration);
                    }
                }
            }
        }
    }

    protected abstract ArrayList<IMDRElementWithIteration> getIMDRElementWithIteration();


}

