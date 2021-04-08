package mldr.services;

import exceptions.CodeApplException;
import mdr.MDRPK;
import mldr.MLDRPK;

public class MLDRPKService {

    
    
    public static MLDRPK to(MDRPK mdrPK){
        if (mdrPK != null) {
            return validMLDRPK((mdrPK));
        } else {
            return null;
        }
   }
    
    private static MLDRPK validMLDRPK(MDRPK mdrPK){
        if (mdrPK instanceof MLDRPK) {
            return (MLDRPK) mdrPK;
        } else {
            throw new CodeApplException("MLDRPKService.to() : La PK " + mdrPK.getName() + " n'est pas de la classe " + MLDRPK.class.getName());
        }
    }

}
