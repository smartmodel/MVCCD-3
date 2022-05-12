package transform.mldrtompdr;

import main.MVCCDManager;
import messages.MessagesBuilder;
import mldr.MLDRModel;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.tapis.*;
import transform.TransformService;

import java.util.ArrayList;

public class MLDRTransformToBoxPackagesResources {

    private MLDRTransform mldrTransform ;
    private MLDRModel mldrModel;
    private IMPDRModelRequirePackage mpdrModel ;

    public MLDRTransformToBoxPackagesResources(MLDRTransform mldrTransform,
                                               MLDRModel mldrModel,
                                               IMPDRModelRequirePackage mpdrModel) {
        this.mldrTransform = mldrTransform;
        this.mldrModel = mldrModel;
        this.mpdrModel = mpdrModel;
    }

    public MPDRBoxPackages createOrModifyBoxPackages(){

        MPDRBoxPackages mpdrBoxPackages = mpdrModel.getMPDRBoxPackages();

        if (mpdrBoxPackages == null){
            mpdrBoxPackages = mpdrModel.createBoxPackages(mpdrModel, mldrModel);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrBoxPackages);
        }
        modifyBoxPackages(mpdrBoxPackages);
        mpdrBoxPackages.setIteration(mldrTransform.getIteration());
        return mpdrBoxPackages;
    }

    private void modifyBoxPackages(MPDRBoxPackages mpdrBoxPackages) {
        // Nom
        String name = MessagesBuilder.getMessagesProperty("mpdr.box.resources.name");
        TransformService.modifyNames(name, mpdrBoxPackages);
        mpdrBoxPackages.setName(name);

        createPackages();
   }

    public void createPackages() {
        MLDRTransformToPackageResources mldrTransformToPackageResources = new MLDRTransformToPackageResources(mldrTransform, mldrModel, mpdrModel);
        ArrayList<MPDRPackage> mpdrPackages = new ArrayList<MPDRPackage>();

        for (MPDRPackageType mpdrPackageType : MPDRPackageType.applicableForSelection(((MPDRModel)mpdrModel).getDb(),
                MPDRStoredCodeUsage.RESOURCES, MPDRStoredCodeScope.MPDR)){
            mpdrPackages.add(mldrTransformToPackageResources.createOrModifyPackage(mpdrPackageType));
        }
    }
}
