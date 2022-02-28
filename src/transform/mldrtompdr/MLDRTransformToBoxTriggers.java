package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.*;

import java.util.ArrayList;

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
        ArrayList<MPDRTrigger> mpdrTriggers = new ArrayList<MPDRTrigger>();

        if (mldrTable.isIndependant()) {
            if (mpdrModel.getMpdrDbPK() == MPDRDBPK.SEQUENCE) {
                if (MPDRTriggerType.BIR_PKIND.applicableForMPDRDB(mpdrModel.getDb())) {
                    mpdrTriggers.add(mldrTransformToTrigger.createOrModifyTrigger(MPDRTriggerType.BIR_PKIND));
                }
            }
        }
        if (mldrTable.isKindDependant()){
            if (MPDRTriggerType.BIR_PKDEP.applicableForMPDRDB(mpdrModel.getDb())){
                mpdrTriggers.add(mldrTransformToTrigger.createOrModifyTrigger(MPDRTriggerType.BIR_PKDEP));
            }
        }

        if (mpdrTriggers.size() > 0) {
            // Conteneur de fonctions et fonction d'alimentation (Si un trigger nécessite une fonction)
            MLDRTransformToBoxProceduresOrFunctions mldrTransformToBoxProceduresOrFunctions = new MLDRTransformToBoxProceduresOrFunctions(
                        mldrTransform, mldrTable, mpdrModel, mpdrTable);
            mldrTransformToBoxProceduresOrFunctions.createOrModifyBoxProceduresOrFunctionsRealizedTrigger(mpdrTriggers);
        }

        return mpdrBoxTriggers;
    }


    public void createOrModifyBoxTriggersForTAPIs() {
        MPDRBoxTriggers mpdrBoxTriggers = createOrModifyBoxTriggers();
        MLDRTransformToTrigger mldrTransformToTrigger = new MLDRTransformToTrigger(mldrTransform, mldrTable, mpdrModel, mpdrTable);
        ArrayList<MPDRTrigger> mpdrTriggers = new ArrayList<MPDRTrigger>();

        for (MPDRTriggerType mpdrTriggerType : MPDRTriggerType.applicableForSelection(mpdrModel.getDb(),
                MPDRTriggerUsage.TAPIS, MPDRTriggerScope.TABLE)){
            mpdrTriggers.add(mldrTransformToTrigger.createOrModifyTrigger(mpdrTriggerType));
        }

        /*
        if (MPDRTriggerType.BIR.applicableToMPDRDB(mpdrModel.getDb())) {
            mpdrTriggers.add(mldrTransformToTrigger.createOrModifyTrigger(MPDRTriggerType.BIR));
        }

         */

        // Conteneur de fonctions et fonction d'alimentation (Si un trigger nécessite une fonction propre (PostgreSQL))
        if (mpdrTriggers.size() > 0) {
            MLDRTransformToBoxProceduresOrFunctions mldrTransformToBoxProceduresOrFunctions = new MLDRTransformToBoxProceduresOrFunctions(
                    mldrTransform, mldrTable, mpdrModel, mpdrTable);
            mldrTransformToBoxProceduresOrFunctions.createOrModifyBoxProceduresOrFunctionsRealizedTrigger(mpdrTriggers);
        }
    }
}
