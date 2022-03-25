package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRConstraintCustomSpecialized;
import mpdr.MPDRModel;
import mpdr.tapis.*;

import java.util.ArrayList;

public class MLDRTransformToBoxTriggersView {

    private MLDRTransform mldrTransform ;
    private MLDRConstraintCustomSpecialized mldrConstraintCustomSpecialized;
    private MPDRModel mpdrModel ;
    private MPDRView mpdrView;

    public MLDRTransformToBoxTriggersView(MLDRTransform mldrTransform,
                                          MLDRConstraintCustomSpecialized mldrConstraintCustomSpecialized,
                                          MPDRModel mpdrModel,
                                          MPDRView mpdrView) {
        this.mldrTransform = mldrTransform;
        this.mldrConstraintCustomSpecialized = mldrConstraintCustomSpecialized;
        this.mpdrModel = mpdrModel;
        this.mpdrView = mpdrView;
    }


    public MPDRBoxTriggers createOrModifyBoxTriggers(){

        MPDRBoxTriggers mpdrBoxTriggers = mpdrView.getMPDRBoxTriggers();

        if (mpdrBoxTriggers == null){
            mpdrBoxTriggers = mpdrView.createBoxTriggers(mldrConstraintCustomSpecialized);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrBoxTriggers);
        }
        modifyBoxTriggers(mpdrView, mpdrBoxTriggers);
        mpdrBoxTriggers.setIteration(mldrTransform.getIteration());
        return mpdrBoxTriggers;
    }


    private void modifyBoxTriggers (MPDRView mpdrView,
                                   MPDRBoxTriggers mpdrBoxTriggers) {
        MLDRTransformService.modifyNames(mpdrView, mpdrBoxTriggers);
        MLDRTransformService.modifyName(mpdrModel, mpdrBoxTriggers);


        MLDRTransformToTrigger mldrTransformToTrigger = new MLDRTransformToTrigger(mldrTransform, mldrConstraintCustomSpecialized, mpdrModel, mpdrView);
        ArrayList<MPDRTrigger> mpdrTriggers = new ArrayList<MPDRTrigger>();

        for (MPDRTriggerType mpdrTriggerType : MPDRTriggerType.applicableForSelection(mpdrModel.getDb(),
                MPDRTriggerUsage.TAPIS, MPDRTriggerScope.VIEW)){
            mpdrTriggers.add(mldrTransformToTrigger.createOrModifyTrigger(mpdrTriggerType));
        }


        //TODO-0 A faire pour PostgreSQL
        /*
        // Conteneur de fonctions et fonction d'alimentation (Si un trigger nécessite une fonction propre (PostgreSQL))
        if (mpdrTriggers.size() > 0) {
            MLDRTransformToBoxProceduresOrFunctions mldrTransformToBoxProceduresOrFunctions = new MLDRTransformToBoxProceduresOrFunctions(
                    mldrTransform, mldrTable, mpdrModel, mpdrView);
            mldrTransformToBoxProceduresOrFunctions.createOrModifyBoxProceduresOrFunctionsRealizedTrigger(mpdrTriggers);
        }

         */

    }

}
