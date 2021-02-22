package transform;

import delete.Delete;
import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import mdr.interfaces.IMDRElementWithIteration;

import java.util.ArrayList;

public abstract class MDTransform {

    protected abstract int getIteration();

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

