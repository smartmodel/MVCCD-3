package transform.mldrtompdr;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import datatypes.MPDRDatatype;
import main.MVCCDManager;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

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
        } else if (mldrColumn.getDatatypeLienProg().equals(Preferences.MLDRDATATYPE_NUMERIC_LIENPROG)) {
                exceptionsColumnNumeric(mpdrColumn, mldrColumn);
        } else {
                pushSize(mpdrColumn, mldrColumn.getSize());
        }

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

        // Séquence de clé primaire associée à une entité indépendante
        if (mldrColumn.getEntityParentSource() != null) {
            boolean c1 = mldrColumn.isPkNotFk();
            boolean c2 = mldrColumn.getEntityParentSource().isInd();
            boolean c3 = mpdrModel.getMpdrDbPK() == MPDRDBPK.SEQUENCE;
            if (c1 && c2 && c3) {
                // Création de la séquence de PK
                MLDRTransformToSequence mldrTransformToSequence = new MLDRTransformToSequence(
                        mldrTransform, mldrColumn, mpdrModel, mpdrColumn);
                MPDRSequence mpdrSequence = mldrTransformToSequence.createOrModifySeq(MPDRSequenceRole.PK);
            }
        }



        // Contrainte de Check de type de donnée
        if (mldrColumn.isBusiness()) {
            createCheckForDatatype(mldrColumn, mpdrColumn);
        }
    }


    //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
    private void pushSize(MPDRColumn mpdrColumn, Integer mldrSize) {
        if (mpdrColumn.getSize() != null) {
            if (mldrSize != null) {
                if (mpdrColumn.getSize().intValue() != mldrSize.intValue()) {
                    mpdrColumn.setSize(mldrSize);
                } else {
                    // valeurs identiques et non nulles ...
                }
            } else {
                mpdrColumn.setSize(mldrSize);
            }
        } else {
            mpdrColumn.setSize(mldrSize);
        }
    }



    private void exceptionsColumnBoolean(MPDRColumn mpdrColumn, MLDRColumn mldrColumn) {
        Preferences preferences = PreferencesManager.instance().preferences();
             // Oracle
            if (preferences.getMLDRTOMPDR_DB().equals(Preferences.DB_ORACLE)){
                //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
                if (mpdrColumn.getDatatypeLienProg().equals(Preferences.MPDRORACLEDATATYPE_VARCHAR2_LIENPROG)){
                    pushSize(mpdrColumn, 1);
                }
            }
            // MySQL
            if (preferences.getMLDRTOMPDR_DB().equals(Preferences.DB_MYSQL)){
                //#MAJ 2021-05-30 MLDR-> MPDR Transformation size
                if (mpdrColumn.getDatatypeLienProg().equals(Preferences.MPDRMySQLDATATYPE_TINYINT_LIENPROG)){
                    pushSize(mpdrColumn, 1);
                }
            }
    }

    private void exceptionsColumnNumeric(MPDRColumn mpdrColumn, MLDRColumn mldrColumn) {
        Preferences preferences = PreferencesManager.instance().preferences();
        // PostgreSQL
        boolean c1 = preferences.getMLDRTOMPDR_DB().equals(Preferences.DB_POSTGRESQL);
        boolean c2 = mldrColumn.getScale() == null ;
        boolean c3 = mldrColumn.isNotBusiness();

        if ( c1 && c2 & c3) {
            //SMALLINT, INTEGER, BIGINT
            pushSize(mpdrColumn, null);
        } else {
            pushSize(mpdrColumn, mldrColumn.getSize());
        }
    }


    private void createCheckForDatatype(MLDRColumn mldrColumn, MPDRColumn mpdrColumn) {

        String checkExpression = "";
        String mcdDatatypeLienProg = mpdrColumn.getDatatypeConstraintLienProg();
        MCDDatatype mcdDatatype = MDDatatypeService.getMCDDatatypeByLienProg(mcdDatatypeLienProg);

        MCDDatatype number = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NUMBER_LIENPROG);

        if (mcdDatatype.isDescendantOf(number)) {
            String comparisonExpression = checkNumberDatatype (mcdDatatype) ;
            if (StringUtils.isNotEmpty(comparisonExpression)) {
                checkExpression = mpdrColumn.getName() + comparisonExpression;
            }
        }


        // Création de la contrainte de CHECK
        if (StringUtils.isNotEmpty(checkExpression)) {
            MLDRTransformToCheck mldrTransformToCheck = new MLDRTransformToCheck(
                    mldrTransform, mldrColumn, mpdrModel, mpdrTable);
            MPDRCheck mpdrCheck = mldrTransformToCheck.createOrModifyCheck(mpdrColumn, MPDRConstraintSpecificRole.DATATYPE, checkExpression);
        }

    }

    private String checkNumberDatatype(MCDDatatype mcdDatatype) {

        // >= 0 avant > 0 !
        String comparisonExpression  = " >= 0";
        MCDDatatype nonNegativeInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NONNEGATIVEINTEGER_LIENPROG);
        MCDDatatype nonNegativeDecimal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NONNEGATIVEDECIMAL_LIENPROG);
        MCDDatatype nonNegativeMoney = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NONNEGATIVEMONEY_LIENPROG);
        if (mcdDatatype.isSelfOrDescendantOf(nonNegativeInteger)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(nonNegativeDecimal)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(nonNegativeMoney)){
            return comparisonExpression;
        }

        comparisonExpression  = " > 0";
        MCDDatatype positiveInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG);
        MCDDatatype positiveDecimal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEDECIMAL_LIENPROG);
        MCDDatatype positiveMoney = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEMONEY_LIENPROG);
        if (mcdDatatype.isSelfOrDescendantOf(positiveInteger)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(positiveDecimal)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(positiveMoney)){
            return comparisonExpression;
        }

        // <= 0 avant < 0 !
        comparisonExpression  = " <= 0";
        MCDDatatype nonPositiveInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NONPOSITIVEINTEGER_LIENPROG);
        MCDDatatype nonPositiveDecimal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NONPOSITIVEDECIMAL_LIENPROG);
        MCDDatatype nonPositiveMoney = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NONPOSITIVEMONEY_LIENPROG);
        if (mcdDatatype.isSelfOrDescendantOf(nonPositiveInteger)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(nonPositiveDecimal)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(nonPositiveMoney)){
            return comparisonExpression;
        }
        comparisonExpression  = " < 0";
        MCDDatatype negativeInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NEGATIVEINTEGER_LIENPROG);
        MCDDatatype negativeDecimal = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NEGATIVEDECIMAL_LIENPROG);
        MCDDatatype negativeMoney = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_NEGATIVEMONEY_LIENPROG);
        if (mcdDatatype.isSelfOrDescendantOf(negativeInteger)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(negativeDecimal)){
            return comparisonExpression;
        } else if (mcdDatatype.isSelfOrDescendantOf(negativeMoney)){
            return comparisonExpression;
        }


        return "";
    }


}
