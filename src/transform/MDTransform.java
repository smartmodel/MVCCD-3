package transform;

import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import mdr.interfaces.IMDRElementWithIteration;

import java.util.ArrayList;

public abstract class MDTransform {


    public abstract int getIteration();

    protected void deleteMDRElementNotInIteration(){
        for (IMDRElementWithIteration imdrElementWithIteration : getIMDRElementsWithIterationInScope()){
            if ( imdrElementWithIteration instanceof IMDElementWithSource) {
                // vérifier que'une source soit présente
                MDElement mdElementSource = ((IMDElementWithSource) imdrElementWithIteration).getMdElementSource();
                // Si la source est supprimée mdElementSource est null !
                // En remplaçant MdElementSource par son Id, la référence sera nulle mais, pas léa valeur d'id!
                //if (mdElementSource != null){
                    //Vérifier que les 2 itérations soient différentes

                if (imdrElementWithIteration.getIteration() == null){
                    // Cas d'erreur en développement
                    ((MVCCDElement) imdrElementWithIteration).delete();
                } else if (imdrElementWithIteration.getIteration() != getIteration()){
                    //Delete.deleteMVCCDElement((MVCCDElement) imdrElementWithIteration);
                    ((MVCCDElement) imdrElementWithIteration).delete();
                }
                //}
            }
        }
    }

    //protected abstract ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration();

    protected abstract ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope();


}

