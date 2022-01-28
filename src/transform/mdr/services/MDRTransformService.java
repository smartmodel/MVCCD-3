package transform.mdr.services;

import main.MVCCDManager;
import mdr.MDRColumn;
import mdr.MDROperation;
import mdr.MDRParameter;
import mdr.MDRTableOrView;
import transform.MDTransform;

import java.util.ArrayList;

public class MDRTransformService {



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
