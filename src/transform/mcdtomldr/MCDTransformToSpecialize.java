package transform.mcdtomldr;

import main.MVCCDManager;
import mcd.MCDGeneralization;
import messages.MessagesBuilder;
import mldr.MLDRConstraintCustomSpecialized;
import mldr.MLDRFK;
import mldr.MLDRTable;
import transform.TransformService;
import transform.mcdtomldr.services.MCDTransformService;

public class MCDTransformToSpecialize {

    private MCDTransform mcdTransform;

    public MCDTransformToSpecialize(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public MLDRConstraintCustomSpecialized createOrModifyFromGenSpec(MLDRTable mldrTable,
                                           MCDGeneralization mcdGeneralization,
                                           MLDRFK mldrFK) {

        // Contrainte specialize
        MLDRConstraintCustomSpecialized mldrSpecialized =
                mldrTable.getMLDRSpecializeByMCDElementSource(mcdGeneralization);
        if (mldrSpecialized == null) {
            mldrSpecialized = mldrTable.createSpecialized(mcdGeneralization);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrSpecialized);
        }


        // Modification
        modifySpecialized(mldrTable, mcdGeneralization, mldrFK, mldrSpecialized);
        mldrSpecialized.setIteration(mcdTransform.getIteration());


        // Ajustement des paramètres
        MCDTransformService.adjustParameters(mcdTransform, mldrSpecialized,
                mldrFK);

        return mldrSpecialized;
    }

    private void modifySpecialized(MLDRTable mldrTable,
                                   MCDGeneralization mcdGeneralization,
                                   MLDRFK mldrFK,
                                   MLDRConstraintCustomSpecialized mldrSpecialized) {



        // Nom
        String name = MessagesBuilder.getMessagesProperty("mdr.constraint.custom.specialized.name");
        TransformService.modifyNames(name, mldrSpecialized);
        mldrSpecialized.setName(name);
    }

}
