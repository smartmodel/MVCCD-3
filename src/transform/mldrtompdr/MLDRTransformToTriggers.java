package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRTriggers;

public class MLDRTransformToTriggers {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToTriggers(MLDRTransform mldrTransform,
                                   MLDRTable mldrTable,
                                   MPDRModel mpdrModel,
                                   MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRTriggers createOrModifyTriggers(){

        MPDRTriggers mpdrTriggers = mpdrTable.getMPDRTriggers();

        if (mpdrTriggers == null){
            mpdrTriggers = mpdrTable.createTriggers(mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrTriggers);
        }
        modifyTriggers(mldrTable, mpdrTriggers);
        mpdrTriggers.setIteration(mldrTransform.getIteration());
        return mpdrTriggers;

    }

    private void modifyTriggers(MLDRTable mldrTable,
                                MPDRTriggers mpdrTriggers) {
        MLDRTransformService.modifyNames(mldrTable, mpdrTriggers);
        MLDRTransformService.modifyName(mpdrModel, mpdrTriggers);

    }


}
