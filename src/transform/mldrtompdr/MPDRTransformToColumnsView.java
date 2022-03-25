package transform.mldrtompdr;

import main.MVCCDManager;
import mpdr.MPDRColumn;
import mpdr.MPDRConstraintCustomSpecialized;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRColumnView;
import mpdr.tapis.MPDRView;

import java.util.ArrayList;

public class MPDRTransformToColumnsView {

    private MLDRTransform mldrTransform ;
    private MPDRTable mpdrTable;
    private MPDRModel mpdrModel ;
    private MPDRView mpdrView;

    // Transformation sans référer à un élément du modèle logique !
    public MPDRTransformToColumnsView(MLDRTransform mldrTransform,
                                      MPDRTable mpdrTable,
                                      MPDRModel mpdrModel,
                                      MPDRView mpdrView) {
        this.mldrTransform = mldrTransform ;
        this.mpdrTable = mpdrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrView = mpdrView;
    }




    public void transformColumns() {

        // Préparation de la liste des colonnes des tables de la généralisation-spécialisation
        ArrayList<MPDRColumn> mpdrColumns = prepareColumns(mpdrTable);

        // Suprresion des colonnes surchargées par une spécialisation
        mpdrColumns = withOverloadColumns(mpdrColumns);

        // Tranfromation proprement dite
        for (MPDRColumn mpdrColumn : mpdrColumns){
            MPDRColumnView mpdrColumnView = transformColumn(mpdrColumn);
        }
    }


    private ArrayList<MPDRColumn> prepareColumns(MPDRTable mpdrTable) {
        ArrayList<MPDRColumn> mpdrColumns = new ArrayList<MPDRColumn>();
        MPDRConstraintCustomSpecialized mpdrConstraintCustomSpecialized = mpdrTable.getMPDRConstraintCustomSpecialized();
        if (mpdrConstraintCustomSpecialized != null){
            mpdrColumns.addAll(
                    prepareColumns(mpdrConstraintCustomSpecialized.getMPDRTableGen()));
        }
        //mpdrColumns.addAll(mpdrTable.getMPDRColumnsSortDefault()) ;
        mpdrColumns.addAll(filtredColumns(mpdrTable)) ;
        return mpdrColumns;
    }

    private ArrayList<MPDRColumn> withOverloadColumns(ArrayList<MPDRColumn> mpdrColumns) {
        ArrayList<MPDRColumn> mpdrColumnsOverload = new ArrayList<MPDRColumn>();
        // Il faut retirer les colonnes de même nom qui sont généralisées
        // Le parcours commence donc par la spéciatisation qui est en fin du tableau de colonnes
        if (mpdrColumns.size() > 0 ){
            mpdrColumnsOverload.add( mpdrColumns.get(mpdrColumns.size() - 1));
            for (int i = mpdrColumns.size() - 2 ; i >= 0 ; i--){
                if (notExistNameInArray(mpdrColumns.get(i), mpdrColumnsOverload)){
                    mpdrColumnsOverload.add( 0, mpdrColumns.get(i));
                }
            }
        }
        return mpdrColumnsOverload ;
    }

    private boolean notExistNameInArray(MPDRColumn mpdrColumn, ArrayList<MPDRColumn> mpdrColumns) {
        for (MPDRColumn mpdrColumnInArray : mpdrColumns){
            if (mpdrColumn.getName().equals(mpdrColumnInArray.getName())){
                return false;
            }
        }
        return true;
    }

    private ArrayList<MPDRColumn> filtredColumns(MPDRTable mpdrTable) {
        ArrayList<MPDRColumn> mpdrColumns = new ArrayList<MPDRColumn>();
        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumnsSortDefault()){
            if ( ! mpdrColumn.isPFk()){
                mpdrColumns.add(mpdrColumn);
            }
        }
        return mpdrColumns;
    }


    private MPDRColumnView transformColumn(MPDRColumn mpdrColumn) {

        // Suppression des anciennes colonnes et création de nouvelle car il y a 2 sources logiques qui peuvent changer :
        // - La colonne elle-même
        // - la relation de généralisation - spécialisation
        MPDRColumnView mpdrColumnView = mpdrView.createColumnView();
        MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumnView);

        modifyColumn(mpdrColumn, mpdrColumnView );
        mpdrColumnView.setIteration(mldrTransform.getIteration());
        return mpdrColumnView;
    }

    private void modifyColumn(MPDRColumn mpdrColumn, MPDRColumnView mpdrColumnView ) {
        MLDRTransformService.modifyNames(mpdrColumn, mpdrColumnView);
        MLDRTransformService.modifyName(mpdrModel, mpdrColumnView);

        mpdrColumnView.setMpdrColumnAsQuerryId(mpdrColumn.getIdProjectElement());
    }

}
