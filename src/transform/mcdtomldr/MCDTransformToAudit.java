package transform.mcdtomldr;

import main.MVCCDManager;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import mldr.MLDRConstraintCustomAudit;
import mldr.MLDRTable;
import transform.TransformService;

public class MCDTransformToAudit {

    private MCDTransform mcdTransform;

    public MCDTransformToAudit(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public MLDRConstraintCustomAudit createOrModifyFromEntity(MCDEntity mcdEntity,
                                                              MLDRTable mldrTable) {

        // Contrainte Audit
        MLDRConstraintCustomAudit mldrAudit =
                mldrTable.getMLDRAuditByMCDElementSource(mcdEntity);
        if (mldrAudit == null) {
            mldrAudit = mldrTable.createAudit(mcdEntity);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrAudit);
        }


        // Modification
        modifyAudit(mldrTable, mcdEntity, mldrAudit);
        mldrAudit.setIteration(mcdTransform.getIteration());



        return mldrAudit;
    }

    private void modifyAudit(MLDRTable mldrTable,
                             MCDEntity mcdEntity,
                             MLDRConstraintCustomAudit mldrAudit) {



        // Nom
        String name = MessagesBuilder.getMessagesProperty("mdr.constraint.custom.audit.name");
        TransformService.modifyNames(name, mldrAudit);
        mldrAudit.setName(name);
    }

}
