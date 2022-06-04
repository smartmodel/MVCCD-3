package transform.mldrtompdr;

import main.MVCCDManager;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mpdr.MPDRColumn;
import mpdr.MPDRConstraintCustomJnal;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRColumnJnal;
import mpdr.tapis.MPDREnumColumnsJnal;
import mpdr.tapis.MPDRTableJnal;
import stereotypes.Stereotype;
import stereotypes.StereotypesManager;
import transform.TransformService;
import utilities.UtilDivers;

public class MLDRTransformToColumnsJnal {

    private MLDRTransform mldrTransform ;
    MPDRConstraintCustomJnal mpdrConstraintCustomJnal;
    private MPDRModel mpdrModel ;
    private MPDRTableJnal mpdrTableJnal;
    private MPDRTable mpdrTable;

    public MLDRTransformToColumnsJnal(MLDRTransform mldrTransform,
                                      MPDRConstraintCustomJnal mpdrConstraintCustomJnal,
                                      MPDRModel mpdrModel,
                                      MPDRTableJnal mpdrTableJnal,
                                      MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mpdrConstraintCustomJnal = mpdrConstraintCustomJnal;
        this.mpdrModel = mpdrModel;
        this.mpdrTableJnal = mpdrTableJnal;
        this.mpdrTable = mpdrTable;
    }

    void transformColumnsJnal() {
        // Colonnes spécifiques à la table de journalisation
        for (MPDREnumColumnsJnal mpdrEnumColumnsJnal : MPDREnumColumnsJnal.getValuesByDb(mpdrModel.getDb())){
            MPDRColumnJnal mpdrColumnJnal = transformColumnJnalTechnical(mpdrEnumColumnsJnal);
        }
        
        // Colonnes de la table à journaliser
        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()){
            // Ne pas prendre en compte les colonnes qui seront supprimées en fin de transformation
            if (mpdrColumn.getIteration() == mpdrModel.getIteration()) {
                MPDRColumnJnal mpdrColumnJnal = transformColumnJnalDatas(mpdrColumn);
            }
        }
    }


    private MPDRColumnJnal transformColumnJnalTechnical(MPDREnumColumnsJnal mpdrEnumColumnsJnal) {

        Stereotype stereotype = StereotypesManager.instance().stereotypes().getStereotypeByLienProg(
                MPDRColumnJnal.class.getName(), mpdrEnumColumnsJnal.getStereotypeLienprog());
        MPDRColumnJnal mpdrColumnJnal = mpdrTableJnal.getMPDRColumnJnalByStereotypeJnal(stereotype);
        if (mpdrColumnJnal == null){
            mpdrColumnJnal= mpdrTableJnal.createColumnJnal(mpdrConstraintCustomJnal, stereotype);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumnJnal);
        }
        modifyColumnTechnical(mpdrEnumColumnsJnal, mpdrColumnJnal );
        mpdrColumnJnal.setIteration(mldrTransform.getIteration());
        return mpdrColumnJnal;
    }

    private void modifyColumnTechnical(MPDREnumColumnsJnal mpdrEnumColumnsJnal, MPDRColumnJnal mpdrColumnJnal ) {
        String name = mpdrEnumColumnsJnal.getName();
        // Casse selon les préférences de la DB
        MDRCaseFormat mdrCaseFormat = mpdrModel.getNamingFormatForDB();
        name = MDRModelService.caseFormat(name, mdrCaseFormat);

        TransformService.modifyNames(name, mpdrColumnJnal);
        mpdrColumnJnal.setName(name);


        // Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getDatatypeLienProg(), mpdrEnumColumnsJnal.getDatatypeLienprog())){
            mpdrColumnJnal.setDatatypeLienProg(mpdrEnumColumnsJnal.getDatatypeLienprog());
        }

         // Size Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getSize(), mpdrEnumColumnsJnal.getSize())){
            mpdrColumnJnal.setSize(mpdrEnumColumnsJnal.getSize());
        }

        // Contrainte de type
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getDatatypeConstraintLienProg(), mpdrEnumColumnsJnal.getDatatypeConstraintLienProg())){
            mpdrColumnJnal.setDatatypeConstraintLienProg(mpdrEnumColumnsJnal.getDatatypeConstraintLienProg());
        }
    }


    private MPDRColumnJnal transformColumnJnalDatas(MPDRColumn mpdrColumnSource) {
        MPDRColumnJnal mpdrColumnJnal = mpdrTableJnal.getMPDRColumnJnalByMPDRColumnSource(mpdrColumnSource);
        if (mpdrColumnJnal == null) {
            mpdrColumnJnal = mpdrTableJnal.createColumnJnal(mpdrColumnSource);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumnJnal);
        }

        modifyColumnDatas(mpdrColumnSource, mpdrColumnJnal );
        mpdrColumnJnal.setIteration(mldrTransform.getIteration());
        return mpdrColumnJnal;
    }


    private void modifyColumnDatas(MPDRColumn mpdrColumn, MPDRColumnJnal mpdrColumnJnal) {
        MLDRTransformService.modifyNames(mpdrColumn, mpdrColumnJnal);
        MLDRTransformService.modifyName(mpdrModel, mpdrColumnJnal);

        //  Stéréotype (si existe)

        // Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getDatatypeLienProg(), mpdrColumn.getDatatypeLienProg())){
            mpdrColumnJnal.setDatatypeLienProg(mpdrColumn.getDatatypeLienProg());
        }

        // Size Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getSize(), mpdrColumn.getSize())){
            mpdrColumnJnal.setSize(mpdrColumn.getSize());
        }

        // Contrainte de type
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getDatatypeConstraintLienProg(), mpdrColumn.getDatatypeConstraintLienProg())){
            mpdrColumnJnal.setDatatypeConstraintLienProg(mpdrColumn.getDatatypeConstraintLienProg());
        }

    }


}
