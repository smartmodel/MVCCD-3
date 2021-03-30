package transform.mcdtomldr;

import exceptions.CodeApplException;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mdr.MDRElement;
import mdr.MDRElementNames;
import mdr.MDRFKNature;
import mdr.services.MDRModelService;
import mldr.MLDRModel;

public class MCDTransformService {

    public static void names(MDRElement mdrElement, MDRElementNames newNames, MLDRModel mldrModel){
        // Enregistrement de l'objet contenant tous les noms
        mdrElement.setNames(newNames);

        // Affectation du nom
        // A partir des noms calculés et du nom sélectionné
        String newName = newNames.getNameByNameLength(mldrModel.getNamingLengthFuture());
        newName = MDRModelService.formatNaming(newName, mldrModel.getNamingFormatFuture());

        if (mdrElement.getName() != null) {
            if (!mdrElement.getName().equals(newName)) {
                mdrElement.setName(newName);
            }
        } else {
            mdrElement.setName(newName);
        }
    }

    public static MDRFKNature mapMCDAssociationNature (MCDAssociation mcdAssociation){
        MDRFKNature fkNature = null ;
        if (mcdAssociation.getNature() == MCDAssociationNature.NOID) {
            if (! mcdAssociation.isDegreeNN()) {
                fkNature = MDRFKNature.NOID;
            } else {
                throw new CodeApplException("MCDTransformService.MDRFKNature  " + "L'association " + mcdAssociation.getNameTree() + " est de degré n:n et n'est pas transformée en FK");
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
            throw new CodeApplException("MCDTransformService.MDRFKNature  " + "La nature de l'association " + mcdAssociation.getNameTree() + " est inconnue");
        }
        return fkNature;
    }
}
