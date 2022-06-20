package transform.mldrtompdr;

import main.MVCCDManager;
import mdr.MDRCaseFormat;
import mdr.services.MDRModelService;
import mldr.MLDRConstraintCustomAudit;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRColumnAudit;
import mpdr.tapis.MPDREnumColumnsAudit;
import stereotypes.Stereotype;
import stereotypes.StereotypesManager;
import transform.TransformService;
import utilities.UtilDivers;

public class MLDRTransformToColumnsAudit {

    private MLDRTransform mldrTransform ;
    MLDRConstraintCustomAudit mldrConstraintCustomAudit;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToColumnsAudit(MLDRTransform mldrTransform,
                                       MLDRConstraintCustomAudit mldrConstraintCustomAudit,
                                       MPDRModel mpdrModel,
                                       MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrConstraintCustomAudit = mldrConstraintCustomAudit;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }

    void transformColumnsAudit() {
       for (MPDREnumColumnsAudit mprdEnumColumnAudit : MPDREnumColumnsAudit.getValuesByDb(mpdrModel.getDb())){
            MPDRColumn mpdrColumn = transformColumnAudit(mprdEnumColumnAudit);
        }
    }



    private MPDRColumn transformColumnAudit(MPDREnumColumnsAudit mpdrEnumColumnsAudit) {
        Stereotype stereotype = StereotypesManager.instance().stereotypes().getStereotypeByLienProg(
                MPDRColumnAudit.class.getName(), mpdrEnumColumnsAudit.getStereotypeLienprog());
        MPDRColumnAudit mpdrColumnAudit = mpdrTable.getMPDRColumnAuditByStereotypeAudit(stereotype);
        if (mpdrColumnAudit == null){
            mpdrColumnAudit= mpdrTable.createColumnAudit(mldrConstraintCustomAudit, stereotype);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrColumnAudit);
        }
        modifyColumn(mpdrEnumColumnsAudit, mpdrColumnAudit );
        mpdrColumnAudit.setIteration(mldrTransform.getIteration());
        return mpdrColumnAudit;
    }

    private void modifyColumn(MPDREnumColumnsAudit mpdrEnumColumnsAudit, MPDRColumnAudit mpdrColumnAudit ) {
        String name = mpdrEnumColumnsAudit.getName();
        // Casse selon les préférences de la DB
        MDRCaseFormat mdrCaseFormat = mpdrModel.getNamingFormatForDB();
        name = MDRModelService.caseFormat(name, mdrCaseFormat);

        TransformService.modifyNames(name, mpdrColumnAudit);
        mpdrColumnAudit.setName(name);


        // Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnAudit.getDatatypeLienProg(), mpdrEnumColumnsAudit.getDatatypeLienprog())){
            mpdrColumnAudit.setDatatypeLienProg(mpdrEnumColumnsAudit.getDatatypeLienprog());
        }

         // Size Datatype
        if (! UtilDivers.equalsWithNull(mpdrColumnAudit.getSize(), mpdrEnumColumnsAudit.getSize())){
            mpdrColumnAudit.setSize(mpdrEnumColumnsAudit.getSize());
        }

        // Contrainte de type
        if (! UtilDivers.equalsWithNull(mpdrColumnAudit.getDatatypeConstraintLienProg(), mpdrEnumColumnsAudit.getDatatypeConstraintLienProg())){
            mpdrColumnAudit.setDatatypeConstraintLienProg(mpdrEnumColumnsAudit.getDatatypeConstraintLienProg());
        }
    }


}
