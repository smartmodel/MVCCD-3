package mdr.services;

import exceptions.CodeApplException;
import mdr.MDRRelFKEnd;
import mdr.MDRRelationFK;

public class MDRRelationFKService {

    public static MDRRelFKEnd getEndByRole(MDRRelationFK mdrRelationFK, Integer role) {
        MDRRelFKEnd mdrRelFKEnd = (MDRRelFKEnd) mdrRelationFK.getA();
        if (mdrRelFKEnd.getRole().intValue() == role.intValue()){
            return mdrRelFKEnd ;
        } else {
            mdrRelFKEnd = (MDRRelFKEnd) mdrRelationFK.getB();
            if (mdrRelFKEnd.getRole().intValue() == role.intValue()) {
                return mdrRelFKEnd;
            } else {
                throw new CodeApplException("Le rôle " + role.intValue() +
                        "ne peut pas être trouvé pour la relationFK " + mdrRelationFK.getNameTreePath());
            }
        }

    }
}
