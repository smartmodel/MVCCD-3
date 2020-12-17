package mldr.transform;

import main.MVCCDManager;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import utilities.Trace;

public class MLDRTransformTables {

    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public MLDRTransformTables(MLDRModel mldrModel, MPDRModel mpdrModel) {
        this.mldrModel = mldrModel;
        this.mpdrModel = mpdrModel;
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
        modifyTable(mldrTable, mpdrTable );


        // Transformation des colonnes
        MLDRTransformColumns mldrTransformColumns = new MLDRTransformColumns(mldrTable, mpdrModel, mpdrTable);
        mldrTransformColumns.transformColumns();
    }

    private void modifyTable(MLDRTable mldrTable, MPDRTable mpdrTable ) {
        MLDRTransformService.modifyNames(mldrTable, mpdrTable);
        MLDRTransformService.modifyName(mpdrModel, mpdrTable);
    }


}
