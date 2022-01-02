package transform.mdr.services;

import main.MVCCDManager;
import mdr.*;
import mdr.services.MDRModelService;
import transform.MDTransform;

import java.util.ArrayList;

public class MDRTransformService {


    public static void names(MDRElement mdrElement, MDRElementNames newNames, MDRModel mdrModel){
        // Enregistrement de l'objet contenant tous les noms
        mdrElement.setNames(newNames);

        // Affectation du nom
        // A partir des noms calculés et du nom sélectionné
        String newName = newNames.getNameByNameLength(mdrModel.getNamingLengthFuture());
        newName = MDRModelService.formatNaming(newName, mdrModel.getNamingFormatFuture());

        if (mdrElement.getName() != null) {
            if (!mdrElement.getName().equals(newName)) {
                mdrElement.setName(newName);
            }
        } else {
            mdrElement.setName(newName);
        }
    }


    public static void adjustParameters(MDTransform mdTransform,
                                        MDRTableOrView mdrTableOrView,
                                        MDROperation mdrOperation,
                                        ArrayList<MDRColumn> mdrColumns) {

        for (MDRColumn mdrColumn : mdrColumns){

            MDRParameter mdrParameter = mdrOperation.getParameter(mdrColumn);
            // Ajout des paramètres de colonnes manquants
            if (mdrParameter == null) {
                // Le tri est fait par par la méthode de base compareToDefault()
                mdrParameter = mdrOperation.createParameter(mdrColumn);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mdrParameter);
            }
            // Pas de modification car les propriétés viennent de la colonne
            // Mémorisation de l'itération courante
            mdrParameter.setIteration(mdTransform.getIteration());
            // La supression se fait sur la base de la valeur d'itération comme tout MDRElement!
        }

    }
}
