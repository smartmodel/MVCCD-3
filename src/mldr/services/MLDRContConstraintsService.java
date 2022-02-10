package mldr.services;

import mdr.MDRConstraint;
import mdr.MDRContConstraints;
import mdr.interfaces.IMDRConstraintIndice;
import utilities.Indexing;

import java.util.ArrayList;

public class MLDRContConstraintsService {

    public static Integer nextIndice (MDRContConstraints mdrContConstraints, MDRConstraint mdrConstraint){
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
        for (MDRConstraint aMDRConstraint : mdrContConstraints.getMDRConstraints()){
            // L'élément lui-même doit être ignoré
            if (aMDRConstraint != mdrConstraint) {
                if (aMDRConstraint.getClass() == mdrConstraint.getClass()) {
                    if (mdrConstraint instanceof IMDRConstraintIndice) {
                        //#MAJ 2021-03-19 Erreur indexation des contraintes FK (et autres)
                        //IMDRConstraintIndice imdrConstraintIndice = (IMDRConstraintIndice) mdrConstraint;
                        IMDRConstraintIndice imdrConstraintIndice = (IMDRConstraintIndice) aMDRConstraint;
                        Integer indice = imdrConstraintIndice.getIndice();
                        brothers.add(indice);
                    }
                }
            }
        }


        return Indexing.indexInSiblings(brothers);

    }
}
