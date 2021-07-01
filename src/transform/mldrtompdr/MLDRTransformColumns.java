package transform.mldrtompdr;

import datatypes.MPDRDatatype;
import main.MVCCDManager;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

public class MLDRTransformColumns {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable ;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformColumns(MLDRTransform mldrTransform, MLDRTable mldrTable, MPDRModel mpdrModel, MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform ;
        this.mldrTable= mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }



    void transformColumns() {
        for (MLDRColumn mldrColumn : mldrTable.getMLDRColumns()){
            MPDRColumn mpdrColumn = transformColumn(mldrColumn);
        }
    }



    private MPDRColumn transformColumn(MLDRColumn mldrColumn) {

        MPDRColumn mpdrColumn = mpdrTable.getMPDRColumnByMLDRColumnSource(mldrColumn);
        if (mpdrColumn == null){
            mpdrColumn= mpdrTable.createColumn(mldrColumn);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumn);
        }
        modifyColumn(mldrColumn, mpdrColumn );
        mpdrColumn.setIteration(mldrTransform.getIteration());
        return mpdrColumn;
    }

    private void modifyColumn(MLDRColumn mldrColumn, MPDRColumn mpdrColumn ) {
        MLDRTransformService.modifyNames(mldrColumn, mpdrColumn);
        MLDRTransformService.modifyName(mpdrModel, mpdrColumn);

        // Datatype
        //MPDRDatatype mpdrDatatype = MLDRTransformToMPDRDatatype.fromMLDRDatatype(mldrColumn);
        MPDRDatatype mpdrDatatype = mpdrModel.fromMLDRDatatype(mldrColumn);
        String mpdrDatatypeLienProg = mpdrDatatype.getLienProg();
        if (mpdrColumn.getDatatypeLienProg() != null) {
            if (!(mpdrColumn.getDatatypeLienProg().equals(mpdrDatatypeLienProg))) {
                mpdrColumn.setDatatypeLienProg(mpdrDatatypeLienProg);
            }
        } else {
            mpdrColumn.setDatatypeLienProg(mpdrDatatypeLienProg);
        }

        // Reprise des valeurs du modèle logique
        // Datatype contrainte
        if (mpdrColumn.getDatatypeConstraintLienProg() != null) {
            if (!(mpdrColumn.getDatatypeConstraintLienProg().equals(mldrColumn.getDatatypeLienProg()))) {
                mpdrColumn.setDatatypeConstraintLienProg(mldrColumn.getDatatypeConstraintLienProg());
            }
        } else {
            mpdrColumn.setDatatypeConstraintLienProg(mldrColumn.getDatatypeConstraintLienProg());
        }
        
        // Datatype size
        //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
        //if (mpdrColumn.getSize() != null) {
        // Boolean
        if (mldrColumn.getDatatypeLienProg().equals(Preferences.MLDRDATATYPE_BOOLEAN_LIENPROG)) {
            exceptionsColumnBoolean(mpdrColumn, mldrColumn);
        } else {
            pushSize(mpdrColumn, mldrColumn.getSize());
        }
        //}


        // Datatype scale
        if (mpdrColumn.getScale() != null) {
            if (mpdrColumn.getScale().intValue() != mldrColumn.getScale().intValue()) {
                mpdrColumn.setScale(mldrColumn.getScale());
            }
        } else {
            mpdrColumn.setScale(mldrColumn.getScale());
        }


        // Mandatory
        if (mpdrColumn.isMandatory() != mldrColumn.isMandatory()){
            mpdrColumn.setMandatory(mldrColumn.isMandatory());
        }

        // Frozen
        if (mpdrColumn.isFrozen() != mldrColumn.isFrozen()){
            mpdrColumn.setFrozen(mldrColumn.isFrozen());
        }

        // Uppercase
        if (mpdrColumn.isUppercase() != mldrColumn.isUppercase()){
            mpdrColumn.setUppercase(mldrColumn.isUppercase());
        }

        // Init Value
        String mpdrInitValue= mldrColumn.getInitValue();
        if (mpdrColumn.getInitValue() != null) {
            if (! mpdrColumn.getInitValue().equals(mldrColumn.getInitValue())) {
                mpdrColumn.setInitValue(mpdrInitValue);
            }
        } else {
            mpdrColumn.setInitValue(mpdrInitValue);
        }

        // Derived Value
        String mpdrDefaultValue= mldrColumn.getInitValue();
        if (mpdrColumn.getDerivedValue() != null) {
            if (! mpdrColumn.getDerivedValue().equals(mldrColumn.getDerivedValue())) {
                mpdrColumn.setDerivedValue(mpdrDefaultValue);
            }
        } else {
            mpdrColumn.setDerivedValue(mpdrDefaultValue);
        }
    }


    //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
    private void pushSize(MPDRColumn mpdrColumn, Integer mldrSize) {
        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getSize().intValue() != mldrSize.intValue()) {
                mpdrColumn.setSize(mldrSize);
            }
        } else {
            mpdrColumn.setSize(mldrSize);
        }
    }



    private void exceptionsColumnBoolean(MPDRColumn mpdrColumn, MLDRColumn mldrColumn) {
        Preferences preferences = PreferencesManager.instance().preferences();
             // Oracle
            if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MPDR_DB_ORACLE)){
                //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
                if (mpdrColumn.getDatatypeLienProg().equals(Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG)){
                    pushSize(mpdrColumn, 1);
                }
            }
            // MySQL
            if (preferences.getMLDRTOMPDR_DB().equals(Preferences.MPDR_DB_MYSQL)){
                //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
                if (mpdrColumn.getDatatypeLienProg().equals(Preferences.MPDRMySQLDATATYPE_TINYINT_LIENPROG)){
                    pushSize(mpdrColumn, 1);
                }
            }
    }


}
