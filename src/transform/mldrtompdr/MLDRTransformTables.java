package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

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
        //tansformTablesWithoutFKsIdComp();
        for (MLDRTable mldrTable : mldrModel.getMLDRTables()){
            transformTable (mldrTable);
        }
    }
    /*
    void tansformTablesWithoutFKsIdComp() {
        for (MLDRTable mldrTable : mldrModel.getMLDRTables()){
            if (mldrTable.getMDRFKsIdComp().size() == 0) {
                transformTable(mldrTable);
            }
        }
    }

     */

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
        MLDRTransformConstraints mldrTransformConstraints = new MLDRTransformConstraints(
                mldrTransform, mldrTable, mpdrModel, mpdrTable);
        mldrTransformConstraints.transformConstraints();

    }

    private void modifyTable(MLDRTable mldrTable, MPDRTable mpdrTable ) {
        MLDRTransformService.modifyNames(mldrTable, mpdrTable);
        MLDRTransformService.modifyName(mpdrModel, mpdrTable);
    }


}
