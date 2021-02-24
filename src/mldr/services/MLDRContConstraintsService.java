package mldr.services;

import mdr.MDRConstraint;
import mdr.interfaces.IMDRConstraintIndice;
import mldr.MLDRContConstraints;
import utilities.Indexing;

import java.util.ArrayList;

public class MLDRContConstraintsService {

    public static Integer nextIndice (MLDRContConstraints mldrContConstraints, MDRConstraint mdrConstraint){
        /*
        Integer lastIndice = 0 ;
        for (MDRConstraint aMDRConstraint : mldrContConstraints.getMDRConstraints()){
            // L'élément lui-même doit être ignoré
            if (aMDRConstraint != mdrConstraint) {
                if (aMDRConstraint.getClass() == mdrConstraint.getClass()) {
                    if (mdrConstraint instanceof IMDRConstraintIndice) {
                        IMDRConstraintIndice imdrConstraintIndice = (IMDRConstraintIndice) mdrConstraint;
                        Integer indice = imdrConstraintIndice.getIndice();
                        if (indice != null) {
                            if (indice > lastIndice) {
                                lastIndice = indice;
                            }
                        }
                    }
                }
            }
        }
        return ++lastIndice;

         */

        ArrayList<Integer> brothers = new ArrayList<Integer>();
        for (MDRConstraint aMDRConstraint : mldrContConstraints.getMDRConstraints()){
            // L'élément lui-même doit être ignoré
            if (aMDRConstraint != mdrConstraint) {
                if (aMDRConstraint.getClass() == mdrConstraint.getClass()) {
                    if (mdrConstraint instanceof IMDRConstraintIndice) {
                        IMDRConstraintIndice imdrConstraintIndice = (IMDRConstraintIndice) mdrConstraint;
                        Integer indice = imdrConstraintIndice.getIndice();
                        brothers.add(indice);
                    }
                }
            }
        }


        return Indexing.indexInBrothers(brothers);

    }
}
