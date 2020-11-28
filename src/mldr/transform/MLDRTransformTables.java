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
        if (mpdrTable != null){
            mpdrModel.modifyTable(mpdrTable, mldrTable);
        } else {
            mpdrTable = mpdrModel.createTable(mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrTable);
        }


    }


}
