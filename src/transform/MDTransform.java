package transform;

import delete.Delete;
import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import mdr.interfaces.IMDRElementWithIteration;
import utilities.Trace;

import java.util.ArrayList;

public abstract class MDTransform {


    protected abstract int getIteration();

    protected void deleteMDRElementNotInIteration(){
        for (IMDRElementWithIteration imdrElementWithIteration : getIMDRElementsWithIterationInScope()){
            if ( imdrElementWithIteration instanceof IMDElementWithSource) {
                // vérifier que'une source soit présente
                MDElement mdElementSource = ((IMDElementWithSource) imdrElementWithIteration).getMdElementSource();
                if (mdElementSource != null){
                    //Vérifier que les 2 itérations soient différentes
                    if (imdrElementWithIteration.getIteration() != getIteration()){
                        //Delete.deleteMVCCDElement((MVCCDElement) imdrElementWithIteration);
                        ((MVCCDElement) imdrElementWithIteration).delete();
                    }
                }
            }
        }
    }

    //protected abstract ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration();

    protected abstract ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope();


}

