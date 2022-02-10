package m.services;

import m.MRelEndMultiPart;
import m.MRelationDegree;

public class MRelationService {

    public static MRelationDegree computeDegree(MRelEndMultiPart maxA , MRelEndMultiPart maxB){

        if (maxA == MRelEndMultiPart.MULTI_ONE)  {
            if (maxB == MRelEndMultiPart.MULTI_ONE){
                return MRelationDegree.DEGREE_ONE_ONE;
            } else{
                return MRelationDegree.DEGREE_ONE_MANY;
            }
        } else {
            if (maxB == MRelEndMultiPart.MULTI_ONE){
                return MRelationDegree.DEGREE_ONE_MANY;
            } else{
                return MRelationDegree.DEGREE_MANY_MANY;
            }
        }
    }

    // Utilisée pour éclencher la suppression de relations dépendantes d'autres relations
    /* A priori pas nécessaire !
    public static boolean isDescendingDependent (IMRelation parent , IMRelation descending){
        if (descending instanceof MRelationUMLDependency) {
            return true;
        } else if (descending instanceof MRelationUMLConstraint) {
            return true;
        } else if (descending instanceof MCDLink) {
            if (parent instanceof MCDAssociation) {
                return true;
            }
        }
        return false;
    }

     */
}
