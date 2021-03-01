package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import transform.mcdtomldr.MCDTransform;
import utilities.Trace;

public class MLDRTransformTables {

    private MLDRTransform mldrTransform ;
    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public MLDRTransformTables(MLDRTransform mldrTransform, MLDRModel mldrModel, MPDRModel mpdrModel) {
        this.mldrTransform = mldrTransform;
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
        mpdrTable.setIteration(mldrTransform.getIteration());


        // Transformation des colonnes
        MLDRTransformColumns mldrTransformColumns = new MLDRTransformColumns(
                mldrTransform, mldrTable, mpdrModel, mpdrTable);
        mldrTransformColumns.transformColumns();

        //Transformation des opérations

    }

    private void modifyTable(MLDRTable mldrTable, MPDRTable mpdrTable ) {
        MLDRTransformService.modifyNames(mldrTable, mpdrTable);
        MLDRTransformService.modifyName(mpdrModel, mpdrTable);
    }


}
