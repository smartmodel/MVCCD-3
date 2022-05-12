package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.interfaces.IMPDRTableRequirePackage;
import mpdr.tapis.*;

import java.util.ArrayList;

public class MLDRTransformToBoxPackagesTable {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private IMPDRModelRequirePackage mpdrModel ;
    private IMPDRTableRequirePackage mpdrTable;

    public MLDRTransformToBoxPackagesTable(MLDRTransform mldrTransform,
                                           MLDRTable mldrTable,
                                           IMPDRModelRequirePackage mpdrModel,
                                           IMPDRTableRequirePackage mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }

    public MPDRBoxPackages createOrModifyBoxPackages(){

        MPDRBoxPackages mpdrBoxPackages = mpdrTable.getMPDRBoxPackages();

        if (mpdrBoxPackages == null){
            mpdrBoxPackages = mpdrTable.createBoxPackages(mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrBoxPackages);
        }
        modifyBoxPackages(mldrTable, mpdrBoxPackages);
        mpdrBoxPackages.setIteration(mldrTransform.getIteration());
        return mpdrBoxPackages;
    }

    private void modifyBoxPackages(MLDRTable mldrTable,
                                   MPDRBoxPackages mpdrBoxPackages) {
        MLDRTransformService.modifyNames(mldrTable, mpdrBoxPackages);
        MLDRTransformService.modifyName((MPDRModel) mpdrModel, mpdrBoxPackages);
        createPackages();
   }



    public void createPackages() {
        MLDRTransformToPackageTable mldrTransformToPackageTable = new MLDRTransformToPackageTable(mldrTransform, mldrTable, mpdrModel, mpdrTable);
        ArrayList<MPDRPackage> mpdrPackages = new ArrayList<MPDRPackage>();

        for (MPDRPackageType mpdrPackageType : MPDRPackageType.applicableForSelection(((MPDRModel)mpdrModel).getDb(),
                MPDRStoredCodeUsage.TAPIS, MPDRStoredCodeScope.TABLE)){
            mpdrPackages.add(mldrTransformToPackageTable.createOrModifyPackage(mpdrPackageType));
        }
    }


}
