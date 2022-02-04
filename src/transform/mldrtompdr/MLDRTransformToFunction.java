package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRFunction;
import mpdr.tapis.MPDRFunctionType;
import mpdr.tapis.MPDRTrigger;

public class MLDRTransformToFunction {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToFunction(MLDRTransform mldrTransform,
                                   MLDRTable mldrTable,
                                   MPDRModel mpdrModel,
                                   MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRFunction createOrModifyFunction(MPDRFunctionType type, MPDRTrigger mpdrTrigger) {

        MPDRFunction mpdrFunction = mpdrTable.getMPDRFunctionByType(type);

        if (mpdrFunction == null){
            mpdrFunction = mpdrTable.createFunction(type, mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrFunction);
        }

        modifyFunction(mpdrTrigger, mpdrFunction, type);
        mpdrFunction.setIteration(mldrTransform.getIteration());
        return mpdrFunction;
    }


    private void modifyFunction(MPDRTrigger mpdrTrigger,
                                MPDRFunction mpdrFunction,
                                MPDRFunctionType mpdrFunctionType) {

        if (mpdrFunction.getType() != null) {
            if (mpdrFunction.getType() != mpdrFunctionType) {
                mpdrFunction.setType(mpdrFunctionType);
            }
        } else {
            mpdrFunction.setType(mpdrFunctionType);
        }
        MLDRTransformService.modifyNames(mpdrTrigger, mpdrFunction);
        MLDRTransformService.modifyName(mpdrModel, mpdrFunction);
    }

}
