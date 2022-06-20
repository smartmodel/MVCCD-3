package transform.mcdtomldr;

import main.MVCCDManager;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import mldr.MLDRConstraintCustomJnal;
import mldr.MLDRTable;
import transform.TransformService;

public class MCDTransformToJnal {

    private MCDTransform mcdTransform;

    public MCDTransformToJnal(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public MLDRConstraintCustomJnal createOrModifyFromEntity(MCDEntity mcdEntity,
                                                             MLDRTable mldrTable) {

        // Contrainte journal
        MLDRConstraintCustomJnal mldrJnal =
                mldrTable.getMLDRJnalByMCDElementSource(mcdEntity);
        if (mldrJnal == null) {
            mldrJnal = mldrTable.createJnal(mcdEntity);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrJnal);
        }


        // Modification
        modifyJnal(mldrTable, mcdEntity, mldrJnal);
        mldrJnal.setIteration(mcdTransform.getIteration());



        return mldrJnal;
    }

    private void modifyJnal(MLDRTable mldrTable,
                                   MCDEntity mcdEntity,
                                   MLDRConstraintCustomJnal mldrJnal) {



        // Nom
        String name = MessagesBuilder.getMessagesProperty("mdr.constraint.custom.jnal.name");
        TransformService.modifyNames(name, mldrJnal);
        mldrJnal.setName(name);
    }

}
