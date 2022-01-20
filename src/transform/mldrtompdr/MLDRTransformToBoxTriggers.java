package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRBoxTriggers;
import mpdr.tapis.MPDRTriggerType;

public class MLDRTransformToBoxTriggers {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToBoxTriggers(MLDRTransform mldrTransform,
                                      MLDRTable mldrTable,
                                      MPDRModel mpdrModel,
                                      MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRBoxTriggers createOrModifyBoxTriggers(){

        MPDRBoxTriggers mpdrBoxTriggers = mpdrTable.getMPDRBoxTriggers();

        if (mpdrBoxTriggers == null){
            mpdrBoxTriggers = mpdrTable.createBoxTriggers(mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrBoxTriggers);
        }
        modifyBoxTriggers(mldrTable, mpdrBoxTriggers);
        mpdrBoxTriggers.setIteration(mldrTransform.getIteration());
        return mpdrBoxTriggers;
    }


    private void modifyBoxTriggers(MLDRTable mldrTable,
                                   MPDRBoxTriggers mpdrBoxTriggers) {
        MLDRTransformService.modifyNames(mldrTable, mpdrBoxTriggers);
        MLDRTransformService.modifyName(mpdrModel, mpdrBoxTriggers);

    }

    public MPDRBoxTriggers createOrModifyBoxTriggersForColumnPKWithoutTAPIs(MPDRColumn mpdrColumnPKProper){
        MPDRBoxTriggers mpdrBoxTriggers = createOrModifyBoxTriggers();

        MLDRTransformToTrigger mldrTransformToTrigger = new MLDRTransformToTrigger(mldrTransform, mldrTable, mpdrModel, mpdrTable);
        if (mldrTable.isIndependant()) {
            if (MPDRTriggerType.BIR_PKIND.applicableToMPDRDB(mpdrModel.getDb())){
                mldrTransformToTrigger.createOrModifyTrigger(MPDRTriggerType.BIR_PKIND);
            }
        }
        if (mldrTable.isKindDependant()){
            if (MPDRTriggerType.BIR_PKDEP.applicableToMPDRDB(mpdrModel.getDb())){
                mldrTransformToTrigger.createOrModifyTrigger(MPDRTriggerType.BIR_PKDEP);
            }
        }

        return mpdrBoxTriggers;
    }


}
