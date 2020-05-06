package m.services;

import m.MRelEnd;
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
}
