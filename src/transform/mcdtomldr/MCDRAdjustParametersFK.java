package transform.mcdtomldr;

import main.MVCCDManager;
import mdr.MDRColumn;
import mdr.MDROperation;
import mdr.MDRTableOrView;
import mldr.MLDRFK;
import mldr.MLDRParameter;
import transform.MDRAdjustParameters;

import java.util.ArrayList;

public class MCDRAdjustParametersFK extends MDRAdjustParameters {


    @Override
    protected void addParameters(MDRTableOrView mdrTableOrView,
                                 MDROperation mdrOperation,
                                 ArrayList<MDRColumn> mdrColumns) {
        // Ajout des colonnes manquantes
        for (MDRColumn mdrColumn : mdrColumns){
            if (! mdrOperation.existeTarget(mdrColumn)) {
                MLDRFK mldrFK = (MLDRFK) mdrOperation;
                MLDRParameter mldrParameter = mldrFK.createParameter(mdrColumn);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mldrParameter);
            }
        }

    }
}
