package transform;

import main.MVCCDManager;
import md.interfaces.IMDElementWithTargets;
import mdr.*;
import mdr.interfaces.IMDRParameter;
import utilities.Trace;

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
            // Pas de modification car les propriétés viennet de la colonnes
            mdTransform.addInTrace((IMDElementWithTargets) mdrOperation.getMdElementSource(), mdrParameter);

        }

    }
}
