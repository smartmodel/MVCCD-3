package mldr.transform;

import main.MVCCDManager;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

public class MLDRTransformTables {

    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public MLDRTransformTables(MLDRModel mldrModel, MPDRModel mpdrModel) {
        this.mldrModel = mldrModel;
        this.mpdrModel = mpdrModel;
    }

    public void transform() {

        transformTables();
    }


    void transformTables() {
        for (MLDRTable mldrTable : mldrModel.getMLDRTables()){
            transformTable (mldrTable);
        }
    }

    private void transformTable(MLDRTable mldrTable) {

        MPDRTable mpdrTable = mpdrModel.getMPDRTableByMLDRTableSource(mldrTable);
        if (mpdrTable == null){
            mpdrTable = mpdrModel.createTable(mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrTable);
        }
        modifyTable(mpdrModel, mpdrTable, mldrTable );
    }

    private void modifyTable(MPDRModel mpdrModel, MPDRTable mpdrTable, MLDRTable mldrTable ) {
        MLDRTransformService.modifyNames(mpdrTable, mldrTable);
        MLDRTransformService.modifyName(mpdrModel, mpdrTable,  mldrTable);
    }


}
