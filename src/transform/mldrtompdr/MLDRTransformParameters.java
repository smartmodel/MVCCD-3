package transform.mldrtompdr;

import console.Console;
import main.MVCCDManager;
import mdr.MDRConstraint;
import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRPK;
import mldr.MLDRParameter;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.services.MLDRConstraintService;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRParameter;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.services.MPDRConstraintService;
import utilities.Trace;

public class MLDRTransformParameters {


    private MLDRTransform mldrTransform ;
    private MDRConstraint mldrConstraint ;
    private MPDRModel mpdrModel ;
    private MDRConstraint mpdrConstraint;

    public MLDRTransformParameters(MLDRTransform mldrTransform, MDRConstraint mldrConstraint, MPDRModel mpdrModel, MDRConstraint mpdrConstraint) {
        this.mldrTransform = mldrTransform ;
        this.mldrConstraint= mldrConstraint;
        this.mpdrModel = mpdrModel;
        this.mpdrConstraint = mpdrConstraint;
    }




    void transformParameters() {
        for (MLDRParameter mldrParameter: MLDRConstraintService.getMLDRParameters(mldrConstraint)){
            MPDRParameter mpdrParameter = transformParameter(mldrParameter);
        }
    }



    private MPDRParameter transformParameter(MLDRParameter mldrParameter) {

        MPDRParameter mpdrParameter = MPDRConstraintService.getMPDRParameterByMLDRParameterSource(mpdrConstraint, mldrParameter);
        if ( mpdrParameter == null){
            mpdrParameter = mpdrConstraint.createParameter(mldrParameter);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrParameter);
        }
        modifyParameter(mldrParameter, mpdrParameter );
        mpdrParameter.setIteration(mldrTransform.getIteration());

        return mpdrParameter;
    }

    private void modifyParameter(MLDRParameter mldrParameter, MPDRParameter mpdrParameter ) {
        // Le nom est repris de Target pour les paramètrees de type colonnes!

        //Target
        MLDRColumn mldrColumnTarget = (MLDRColumn) mldrParameter.getTarget();
        MPDRTable mpdrTable = (MPDRTable) mpdrParameter.getMDRTableAccueil();
        MPDRColumn mpdrColumnTargetNew = mpdrTable.getMPDRColumnByMLDRColumnSource(mldrColumnTarget);
        Integer mpdrColumnTargetIdNew = mpdrColumnTargetNew.getIdProjectElement();
        MPDRColumn mpdrColumnTargetOld = (MPDRColumn) mpdrParameter.getTarget();


        if (mpdrColumnTargetOld != null) {
            if ( mpdrColumnTargetOld != mpdrColumnTargetNew) {
                mpdrParameter.setTargetId(mpdrColumnTargetIdNew);
            }
        } else {
            mpdrParameter.setTargetId(mpdrColumnTargetIdNew);
        }
    }

}
