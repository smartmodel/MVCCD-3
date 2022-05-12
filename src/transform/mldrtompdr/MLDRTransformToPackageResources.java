package transform.mldrtompdr;

import main.MVCCDManager;
import messages.MessagesBuilder;
import mldr.MLDRModel;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;
import transform.TransformService;

public class MLDRTransformToPackageResources {


    private MLDRTransform mldrTransform ;
    private MLDRModel mldrModel;
    private IMPDRModelRequirePackage mpdrModel ;


    public MLDRTransformToPackageResources(MLDRTransform mldrTransform,
                                           MLDRModel mldrModel,
                                           IMPDRModelRequirePackage mpdrModel) {
        this.mldrTransform = mldrTransform;
        this.mldrModel = mldrModel;
        this.mpdrModel = mpdrModel;
    }


    public MPDRPackage createOrModifyPackage(MPDRPackageType type) {

        MPDRPackage mpdrPackage = mpdrModel.getMPDRPackageByType(type);

        if (mpdrPackage == null){
            mpdrPackage = mpdrModel.createPackage(type, mldrModel);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrPackage);
        }

        modifyPackage(mpdrPackage, type);
        mpdrPackage.setIteration(mldrTransform.getIteration());
        return mpdrPackage;
    }


    private void modifyPackage(MPDRPackage mpdrPackage,
                               MPDRPackageType mpdrPackageType) {

        if (mpdrPackage.getType() != null) {
            if (mpdrPackage.getType() != mpdrPackageType) {
                mpdrPackage.setType(mpdrPackageType);
            }
        } else {
            mpdrPackage.setType(mpdrPackageType);
        }

        // Nom
        String name = MessagesBuilder.getMessagesProperty("mpdr.box.resources.package.name") + mpdrPackageType.getMarker();
        TransformService.modifyNames(name, mpdrPackage);
        mpdrPackage.setName(name);
    }

}
