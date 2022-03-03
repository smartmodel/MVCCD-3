package transform.mldrtompdr;

import mldr.MLDRModel;
import mpdr.MPDRModel;

public class MLDRTransformResources {
    private MLDRTransform mldrTransform;
    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel;

    public MLDRTransformResources(MLDRTransform mldrTransform, MLDRModel mldrModel, MPDRModel mpdrModel) {
        this.mldrTransform = mldrTransform;
        this.mldrModel = mldrModel;
        this.mpdrModel = mpdrModel;
    }

    /*
    public MPDRBoxPackages createOrModifyBoxPackages() {

        MPDRBoxPackages mpdrBoxPackages = mpdrModel.getMPDRBoxPackages();

        if (mpdrBoxPackages == null){
            mpdrBoxPackages = mpdrModel.createBoxPackages(mldrModel);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrBoxPackages);
        }
        modifyBoxPackages(mldrModel, mpdrBoxPackages);
        mpdrBoxPackages.setIteration(mldrTransform.getIteration());
        return mpdrBoxPackages;

    }


    private void modifyBoxPackages(MLDRModel mldrModel,
                                   MPDRBoxPackages mpdrBoxPackages) {
        MLDRTransformService.modifyNames(mldrModel, mpdrBoxPackages);
        MLDRTransformService.modifyName((MPDRModel) mpdrModel, mpdrBoxPackages);
        createPackages();
    }


    public void createPackages() {
        MLDRTransformToPackage mldrTransformToPackage = new MLDRTransformToPackage(mldrTransform, mldrTable, mpdrModel, mpdrTable);
        ArrayList<MPDRPackage> mpdrPackages = new ArrayList<MPDRPackage>();

        for (MPDRPackageType mpdrPackageType : MPDRPackageType.applicableForSelection(((MPDRModel)mpdrModel).getDb(),
                MPDRStoredCodeUsage.RESOURCES, MPDRStoredCodeScope.MPDR)){
            mpdrPackages.add(mldrTransformToPackage.createOrModifyPackage(mpdrPackageType));
        }
    }

     */


}
