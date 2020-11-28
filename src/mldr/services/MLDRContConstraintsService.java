package mldr.services;

import mdr.MDRConstraint;
import mdr.interfaces.IMDRConstraintIndice;
import mldr.MLDRContConstraints;

public class MLDRContConstraintsService {

    public static Integer nextIndice (MLDRContConstraints mldrContConstraints, MDRConstraint mdrConstraint){
        Integer lastIndice = 0 ;
        for (MDRConstraint aMDRConstraint : mldrContConstraints.getMDRConstraints()){
            if (aMDRConstraint.getClass() == mdrConstraint.getClass()){
                if (mdrConstraint instanceof IMDRConstraintIndice) {
                    IMDRConstraintIndice imdrConstraintIndice = (IMDRConstraintIndice) mdrConstraint;
                    // L'élément lui-même doit être ignoré
                    Integer indice = imdrConstraintIndice.getIndice();
                    if(indice != null) {
                        if (indice > lastIndice) {
                            lastIndice = indice;
                        }
                    }
                }
            }
        }
        return ++lastIndice;
    }
}
