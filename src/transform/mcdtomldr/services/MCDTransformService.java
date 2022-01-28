package transform.mcdtomldr.services;

import exceptions.CodeApplException;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mdr.MDRElement;
import mdr.MDRElementNames;
import mdr.MDRFKNature;
import mdr.MDRModel;
import mdr.services.MDRModelService;

public class MCDTransformService {


    public static void names(MDRElement mdrElement, MDRElementNames newNames, MDRModel mdrModel){
        // Enregistrement de l'objet contenant tous les noms
        mdrElement.setNames(newNames);

        // Affectation du nom
        // A partir des noms calculés et du nom sélectionné
        String  newName = MDRModelService.buildName(mdrModel,mdrElement);

        //String newName = newNames.getNameByNameLength(mdrModel.getNamingLengthFuture());
        //newName = MDRModelService.formatNaming(newName, mdrModel.getNamingFormatFuture());

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
