package transform;

import main.MVCCDManager;
import mdr.MDRColumn;
import mdr.MDROperation;
import mdr.MDRParameter;
import mdr.MDRTableOrView;

import java.util.ArrayList;

public class MDRAdjustParameters {


    public static void adjustParameters(MDTransform mdTransform,
                                        MDRTableOrView mdrTableOrView,
                                        MDROperation mdrOperation,
                                        ArrayList<MDRColumn> mdrColumns) {

        for (MDRColumn mdrColumn : mdrColumns){
            MDRParameter mdrParameter = mdrOperation.getParameter(mdrColumn);
            // Ajout des paramètres de colonnes manquants
            if (mdrParameter == null) {
                    mdrParameter = mdrOperation.createParameter(mdrColumn);
                    MVCCDManager.instance().addNewMVCCDElementInRepository(mdrParameter);
            }
            // Pas de modification car les propriétés viennent de la colonne
            mdrParameter.setIteration(mdTransform.getIteration());
        }

    }
}
