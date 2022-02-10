package transform.mldrtompdr;

import main.MVCCDManager;
import mdr.MDRElement;
import mldr.MLDRFK;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRConstraintSpecificRole;
import mpdr.MPDRIndex;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRElement;

public class MLDRTransformToIndex {

    private MLDRTransform mldrTransform ;
    MLDRFK mldrFK ;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToIndex(MLDRTransform mldrTransform,
                                MLDRFK mldrFK,
                                MPDRModel mpdrModel,
                                MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrFK = mldrFK;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRIndex createOrModifyIndex(){
        MPDRConstraintSpecificRole mpdrConstraintSpecificRole = MPDRConstraintSpecificRole.INDEX;
        IMPDRConstraint impdrConstraint =  mpdrTable.getMPDRConstraintSpecificByMLDRSourceAndRole(
                mldrFK, mpdrConstraintSpecificRole);

        MPDRIndex mpdrIndex = null;
        if (impdrConstraint instanceof MPDRIndex) {
            mpdrIndex = (MPDRIndex) impdrConstraint;
        }

        if (impdrConstraint == null) {
            mpdrIndex = mpdrTable.createIndex(mldrFK);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrIndex);
        }
        mpdrIndex.setIteration(mldrTransform.getIteration());

        // Transformation des paramètres
        MLDRTransformParameters mldrTransformParameters = new MLDRTransformParameters(
                    mldrTransform, mldrFK, mpdrModel, mpdrIndex);
        mldrTransformParameters.transformParameters();

        modifyIndex(mpdrIndex, mpdrConstraintSpecificRole);
        return mpdrIndex;
    }

    private void modifyIndex(MPDRIndex mpdrIndex,
                             MPDRConstraintSpecificRole mpdrConstraintSpecificRole) {

        MLDRTransformService.modifyNames((IMLDRElement) mldrFK, (IMPDRElement) mpdrIndex);
        MLDRTransformService.modifyName(mpdrModel, (MDRElement) mpdrIndex);

        if (mpdrIndex.getRole() != null){
            if ( mpdrIndex.getRole() != mpdrConstraintSpecificRole) {
                mpdrIndex.setRole(mpdrConstraintSpecificRole);
            }
        } else {
            mpdrIndex.setRole(mpdrConstraintSpecificRole);
        }
    }
}
