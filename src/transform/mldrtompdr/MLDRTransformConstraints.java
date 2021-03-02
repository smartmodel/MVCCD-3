package transform.mldrtompdr;

import datatypes.MPDRDatatype;
import main.MVCCDManager;
import mdr.MDRConstraint;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

public class MLDRTransformConstraints {


    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable ;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformConstraints(MLDRTransform mldrTransform, MLDRTable mldrTable, MPDRModel mpdrModel, MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform ;
        this.mldrTable= mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }



    void transformConstraints() {
        for (MDRConstraint mldrConstraint : mldrTable.getMDRConstraints()){
            MDRConstraint mpdrConstraint = transformConstraint(mldrConstraint);
        }
    }



    private MDRConstraint transformConstraint(MDRConstraint mldrConstraint) {

        MDRConstraint mpdrConstraint = mpdrTable.getMPDRConstraintByMLDRConstraintSource(mldrConstraint);
        if (mpdrConstraint == null){
            mpdrConstraint = mpdrTable.createConstraint(mldrConstraint);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrConstraint);
        }
        //modifyColumn(mldrColumn, mpdrColumn );
        mpdrConstraint.setIteration(mldrTransform.getIteration());
        return mpdrConstraint;
    }

    private void modifyColumn(MLDRColumn mldrColumn, MPDRColumn mpdrColumn ) {
        MLDRTransformService.modifyNames(mldrColumn, mpdrColumn);
        MLDRTransformService.modifyName(mpdrModel, mpdrColumn);

        // Datatype
        MPDRDatatype mpdrDatatype = MLDRTransformToMPDRDatatype.fromMLDRDatatype(mldrColumn);
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
        pushSize(mpdrColumn, mldrColumn.getSize().intValue());


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

    private void pushSize(MPDRColumn mpdrColumn, int value) {
        if (mpdrColumn.getSize() != null) {
            if (mpdrColumn.getSize().intValue() != value) {
                mpdrColumn.setSize(value);
            }
        } else {
            mpdrColumn.setSize(value);
        }
    }


}
