package transform.mldrtompdr;

import main.MVCCDManager;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mpdr.MPDRColumn;
import mpdr.MPDRConstraintCustomJnal;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRColumnJnal;
import mpdr.tapis.MPDREnumColumnsJnalTech;
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
        for (MPDREnumColumnsJnalTech mpdrEnumColumnsJnalTech : MPDREnumColumnsJnalTech.getValuesByDb(mpdrModel.getDb())){
            MPDRColumnJnal mpdrColumnJnal = transformColumnJnalTechnical(mpdrEnumColumnsJnalTech);
        }
        
        // Colonnes de la table à journaliser
        for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()){
            // Ne pas prendre en compte les colonnes qui seront supprimées en fin de transformation
            if (mpdrColumn.getIteration() == mpdrModel.getIteration()) {
                MPDRColumnJnal mpdrColumnJnal = transformColumnJnalDatas(mpdrColumn);
            }
        }
    }


    private MPDRColumnJnal transformColumnJnalTechnical(MPDREnumColumnsJnalTech mpdrEnumColumnsJnalTech) {

        Stereotype stereotype = StereotypesManager.instance().stereotypes().getStereotypeByLienProg(
                MPDRColumnJnal.class.getName(), mpdrEnumColumnsJnalTech.getStereotypeLienprog());
        MPDRColumnJnal mpdrColumnJnal = mpdrTableJnal.getMPDRColumnJnalTechByStereotypeJnal(stereotype);
        if (mpdrColumnJnal == null){
            mpdrColumnJnal= mpdrTableJnal.createColumnJnalTech(mpdrConstraintCustomJnal, stereotype);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumnJnal);
        }
        modifyColumnTechnical(mpdrEnumColumnsJnalTech, mpdrColumnJnal );
        mpdrColumnJnal.setIteration(mldrTransform.getIteration());
        return mpdrColumnJnal;
    }

    private void modifyColumnTechnical(MPDREnumColumnsJnalTech mpdrEnumColumnsJnalTech, MPDRColumnJnal mpdrColumnJnal ) {
        String name = mpdrEnumColumnsJnalTech.getName();
        // Casse selon les préférences de la DB
        MDRCaseFormat mdrCaseFormat = mpdrModel.getNamingFormatForDB();
        name = MDRModelService.caseFormat(name, mdrCaseFormat);

        TransformService.modifyNames(name, mpdrColumnJnal);
        mpdrColumnJnal.setName(name);


        // Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getDatatypeLienProg(), mpdrEnumColumnsJnalTech.getDatatypeLienprog())){
            mpdrColumnJnal.setDatatypeLienProg(mpdrEnumColumnsJnalTech.getDatatypeLienprog());
        }

         // Size Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getSize(), mpdrEnumColumnsJnalTech.getSize())){
            mpdrColumnJnal.setSize(mpdrEnumColumnsJnalTech.getSize());
        }

        // Contrainte de type
        if (! UtilDivers.equalsWithNull(mpdrColumnJnal.getDatatypeConstraintLienProg(), mpdrEnumColumnsJnalTech.getDatatypeConstraintLienProg())){
            mpdrColumnJnal.setDatatypeConstraintLienProg(mpdrEnumColumnsJnalTech.getDatatypeConstraintLienProg());
        }
    }


    private MPDRColumnJnal transformColumnJnalDatas(MPDRColumn mpdrColumnSource) {
        MPDRColumnJnal mpdrColumnJnal = mpdrTableJnal.getMPDRColumnJnalByMPDRColumnSource(mpdrColumnSource);
        if (mpdrColumnJnal == null) {
            mpdrColumnJnal = mpdrTableJnal.createColumnJnalDatas(mpdrColumnSource);
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
