package transform.mldrtompdr;

import main.MVCCDManager;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.interfaces.IMPDRTableRequirePackage;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;

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

        // Transformation des relations (repr. graphique de FK)


        // APIs de tables ou triggers pour les séquences si pas d'APIS de tables
        //TODO-PAS En cours de développement
        if ((mpdrModel instanceof MPDROracleModel) || (mpdrModel instanceof MPDRPostgreSQLModel)) {

            if (mpdrModel.getMPDR_TAPIs()) {
                createOrModifyTAPIs(mldrTable, mpdrTable);
            } else {
                if (mpdrTable.getMPDRColumnPKProper() != null) {
                    // Conteneur de triggers et trigger d'alimentation
                    MLDRTransformToBoxTriggers mldrTransformToBoxTriggers = new MLDRTransformToBoxTriggers(
                            mldrTransform, mldrTable, mpdrModel, mpdrTable);
                    mldrTransformToBoxTriggers.createOrModifyBoxTriggersForColumnPKWithoutTAPIs(mpdrTable.getMPDRColumnPKProper());
                }
           }
        }
    }

    private void modifyTable(MLDRTable mldrTable, MPDRTable mpdrTable ) {
        MLDRTransformService.modifyNames(mldrTable, mpdrTable);
        MLDRTransformService.modifyName(mpdrModel, mpdrTable);
    }

    private void createOrModifyTAPIs(MLDRTable mldrTable, MPDRTable mpdrTable) {

        //TODO-PAS En cours de développement
        if (mpdrModel instanceof MPDROracleModel) {
            MLDRTransformToBoxTriggers mldrTransformToBoxTriggers = new MLDRTransformToBoxTriggers(
                    mldrTransform, mldrTable, mpdrModel, mpdrTable);
            mldrTransformToBoxTriggers.createOrModifyBoxTriggersForTAPIs();

            if (mpdrModel instanceof IMPDRModelRequirePackage) {
                MLDRTransformToBoxPackages mldrTransformToBoxPackages = new MLDRTransformToBoxPackages(
                        mldrTransform, mldrTable, (IMPDRModelRequirePackage) mpdrModel,
                        (IMPDRTableRequirePackage) mpdrTable);
                mldrTransformToBoxPackages.createOrModifyBoxPackages();
            }
        }

    }


}
