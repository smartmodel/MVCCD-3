package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

public class MLDRTransformColumns {

    private MLDRTable mldrTable ;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformColumns(MLDRTable mldrTable, MPDRModel mpdrModel, MPDRTable mpdrTable) {
        this.mldrTable= mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }



    void transformColumns() {
        for (MLDRColumn mldrColumn : mldrTable.getMLDRColumns()){
            transformColumn(mldrColumn);
        }
    }

    private void transformColumn(MLDRColumn mldrColumn) {

        MPDRColumn mpdrColumn = mpdrTable.getMPDRColumnByMLDRColumnSource(mldrColumn);
        if (mpdrColumn == null){
            mpdrColumn= mpdrTable.createColumn(mldrColumn);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumn);
        }
        modifyColumn(mldrColumn, mpdrColumn );
    }

    private void modifyColumn(MLDRColumn mldrColumn, MPDRColumn mpdrColumn ) {
        MLDRTransformService.modifyNames(mldrColumn, mpdrColumn);
        MLDRTransformService.modifyName(mpdrModel, mpdrColumn);
    }


}
