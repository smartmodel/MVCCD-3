package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRBoxProceduresOrFunctions;
import mpdr.tapis.MPDRFunctionType;
import mpdr.tapis.MPDRTrigger;

import java.util.ArrayList;

public class MLDRTransformToBoxProceduresOrFunctions {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToBoxProceduresOrFunctions(MLDRTransform mldrTransform,
                                                   MLDRTable mldrTable,
                                                   MPDRModel mpdrModel,
                                                   MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRBoxProceduresOrFunctions createOrModifyBoxProceduresOrFunctions(){

        MPDRBoxProceduresOrFunctions mpdrBoxProceduresOrFunctions = mpdrTable.getMPDRBoxProceduresOrFunctions();

        if (mpdrBoxProceduresOrFunctions == null){
            mpdrBoxProceduresOrFunctions = mpdrTable.createBoxProceduresOrFunctions(mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrBoxProceduresOrFunctions);
        }
        modifyBoxProceduresOrFunctions(mldrTable, mpdrBoxProceduresOrFunctions);
        mpdrBoxProceduresOrFunctions.setIteration(mldrTransform.getIteration());
        return mpdrBoxProceduresOrFunctions;
    }


    private void modifyBoxProceduresOrFunctions(MLDRTable mldrTable,
                                                MPDRBoxProceduresOrFunctions mpdrBoxProceduresOrFunctions) {
        MLDRTransformService.modifyNames(mldrTable, mpdrBoxProceduresOrFunctions);
        MLDRTransformService.modifyName(mpdrModel, mpdrBoxProceduresOrFunctions);
   }

    public MPDRBoxProceduresOrFunctions createOrModifyBoxProceduresOrFunctionsForColumnPKWithoutTAPIs(MPDRColumn mpdrColumnPKProper, ArrayList<MPDRTrigger> mpdrTriggers){
        if (MPDRFunctionType.isFunctionForDBAndTriggers(mpdrModel.getDb(), mpdrTriggers)) {
            MPDRBoxProceduresOrFunctions mpdrBoxProceduresOrFunctions = createOrModifyBoxProceduresOrFunctions();
            MLDRTransformToFunction mldrTransformToFunction = new MLDRTransformToFunction(mldrTransform, mldrTable, mpdrModel, mpdrTable);
            for (MPDRTrigger mpdrTrigger : mpdrTriggers) {
                MPDRFunctionType mpdrFunctionType = MPDRFunctionType.getFunctionTypeByDBAndTriggerType(mpdrModel.getDb(), mpdrTrigger.getType());
                if (mpdrFunctionType != null){
                    mldrTransformToFunction.createOrModifyFunction(mpdrFunctionType, mpdrTrigger);
                }
            }
            return mpdrBoxProceduresOrFunctions;
        }
        return null;
    }


}
