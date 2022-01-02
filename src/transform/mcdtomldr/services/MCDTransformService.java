package transform.mcdtomldr.services;

import exceptions.CodeApplException;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mdr.MDRFKNature;

public class MCDTransformService {

    public static MDRFKNature mapMCDAssociationNature (MCDAssociation mcdAssociation){
        MDRFKNature fkNature = null ;
        if (mcdAssociation.getNature() == MCDAssociationNature.NOID) {
            if (! mcdAssociation.isDegreeNN()) {
                fkNature = MDRFKNature.NOID;
            } else {
                throw new CodeApplException("L'association " + mcdAssociation.getNameTree() + " est de degré n:n et n'est pas transformée en FK");
            }
        }

        if (mcdAssociation.getNature() == MCDAssociationNature.IDNATURAL) {
            fkNature = MDRFKNature.IDNATURAL;
        }

        if (mcdAssociation.getNature() == MCDAssociationNature.IDCOMP) {
            fkNature = MDRFKNature.IDCOMP;
        }

        if (mcdAssociation.getNature() == MCDAssociationNature.CP) {
            fkNature = MDRFKNature.IDNATURAL;
        }

        if (fkNature == null) {
            throw new CodeApplException("La nature de l'association " + mcdAssociation.getNameTree() + " est inconnue");
        }
        return fkNature;
    }
}
