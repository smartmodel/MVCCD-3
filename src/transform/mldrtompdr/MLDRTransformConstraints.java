package transform.mldrtompdr;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import main.MVCCDManager;
import mdr.MDRConstraint;
import mdr.MDRElement;
import mldr.*;
import mldr.interfaces.IMLDRElement;
import mpdr.*;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRElement;
import mpdr.oracle.MPDROracleConstraintCustomSpecialized;

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
            IMPDRConstraint mpdrConstraint = transformConstraint(mldrConstraint);
        }

    }



    private IMPDRConstraint transformConstraint(MDRConstraint mldrConstraint) {
        IMPDRConstraint mpdrConstraint = (IMPDRConstraint) mpdrTable.getMPDRConstraintInheritedByMLDRConstraintSource(mldrConstraint);
        if ( mpdrConstraint == null){
            if (mldrConstraint instanceof MLDRPK){
                MLDRPK mldrPK = (MLDRPK) mldrConstraint;
                mpdrConstraint = mpdrTable.createPK(mldrPK);
            }
            if (mldrConstraint instanceof MLDRFK){
                MLDRFK mldrFK = (MLDRFK) mldrConstraint;
                mpdrConstraint = mpdrTable.createFK(mldrFK);
            }
            if (mldrConstraint instanceof MLDRUnique){
                MLDRUnique mldrUnique = (MLDRUnique) mldrConstraint;
                mpdrConstraint = mpdrTable.createUnique(mldrUnique);
            }
            if (mldrConstraint instanceof MLDRConstraintCustomSpecialized){
                MLDRConstraintCustomSpecialized mldrSpecialized = (MLDRConstraintCustomSpecialized) mldrConstraint;
                mpdrConstraint = mpdrTable.createSpecialized(mldrSpecialized);
            }
            MVCCDManager.instance().addNewMVCCDElementInRepository((MVCCDElement) mpdrConstraint);

        }

        // Le temps de développement
        if (mpdrConstraint != null) {

            modifyConstraint(mldrConstraint, mpdrConstraint );

            MLDRTransformParameters mldrTransformParameters = new MLDRTransformParameters(
                    mldrTransform, mldrConstraint, mpdrModel, mpdrConstraint);
            mldrTransformParameters.transformParameters();

            mpdrConstraint.setIteration(mldrTransform.getIteration());
        } else {
            String message = "La contrainte " + mldrConstraint.getName() +" de type " +
                    mldrConstraint.getClass().getName() + " n'est pas encore implantée pour la base de données " +
                    mpdrModel.getDb().getText();
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }


        return mpdrConstraint;
    }

    private void modifyConstraint(MDRConstraint mldrConstraint, IMPDRConstraint mpdrConstraint ) {
        MLDRTransformService.modifyNames((IMLDRElement) mldrConstraint, (IMPDRElement) mpdrConstraint);
        MLDRTransformService.modifyName(mpdrModel, (MDRElement) mpdrConstraint);
        // FK
        if (mpdrConstraint instanceof MPDRFK) {
            MLDRFK mldrFK = (MLDRFK) mldrConstraint;
            MPDRFK mpdrFK = (MPDRFK) mpdrConstraint;
            modifyConstraintFK(mldrFK, mpdrFK);
        }

        // Unique
        if (mpdrConstraint instanceof MPDRUnique) {
            MLDRUnique mldrUnique = (MLDRUnique) mldrConstraint;
            MPDRUnique mpdrUnique = (MPDRUnique) mpdrConstraint;
            // Nature
            if (mpdrUnique.getMdrUniqueNature() != mldrUnique.getMdrUniqueNature()) {
                mpdrUnique.setMdrUniqueNature(mldrUnique.getMdrUniqueNature());
            }
        }

        // Custom - Spécialized
        if (mpdrConstraint instanceof MPDROracleConstraintCustomSpecialized) {
            MLDRConstraintCustomSpecialized mldrSpecialized = (MLDRConstraintCustomSpecialized) mldrConstraint;
            MPDRConstraintCustomSpecialized mpdrSpecialized = (MPDRConstraintCustomSpecialized) mpdrConstraint;
            modifyConstraintSpecialized(mldrSpecialized, mpdrSpecialized);
        }
    }

    private void modifyConstraintFK(MLDRFK mldrFK, MPDRFK mpdrFK) {
        // Nature
        if (mpdrFK.getNature() != mldrFK.getNature()) {
            mpdrFK.setNature(mldrFK.getNature());
        }
        //Indice
        if (mpdrFK.getIndice() != mldrFK.getIndice()) {
            mpdrFK.setIndice(mldrFK.getIndice());
        }
        //{deleteCascade}
        if (mpdrFK.isDeleteCascade() != mldrFK.isDeleteCascade()) {
            mpdrFK.setDeleteCascade(mldrFK.isDeleteCascade());
        }

        // Oriented
        if (mpdrFK.isOriented() != mldrFK.isOriented()) {
            mpdrFK.setOriented(mldrFK.isOriented());
        }
        // NotNotOriented
        if (mpdrFK.isNotOriented() != mldrFK.isNotOriented()) {
            mpdrFK.setNotOriented(mldrFK.isNotOriented());
        }

        // Index de performance
        MLDRTransformToIndex mldrTransformToIndex = new MLDRTransformToIndex(
                mldrTransform, mldrFK, mpdrModel, mpdrTable);
        //#MAJ 2022-03-28  La clé étrangère de lien avec l'entité généralisée correspond à la PK et ne doit pas être indexée
        //if ( ! (mpdrTable.isSpecialized() && (mldrFK.isIdComp()) )) {
        //#MAJ 2022-05-12 La clé étrangère de lien avec l'entité généralisée correspond à la PK et ne doit pas être indexée
        if ( ! mldrFK.isSpecialized()  ) {
            MPDRIndex mpdrIndex = mldrTransformToIndex.createOrModifyIndex();
        }

    }


    private void modifyConstraintSpecialized(MLDRConstraintCustomSpecialized mldrSpecialized,
                                             MPDRConstraintCustomSpecialized mpdrSpecialized) {
        //#MAJ 2022-03-25 Erreur génération des vues. La contrainte PK de la table parent doit exister ...
        // Les contraintes PK et FK doivent être en place pour générer la vue des TAPIs
        // cela confirme qu'il faudrait céer les TAPIS après la transformation du modèle physique
        /*
        MLDRTransformToView mldrTransformToView = new MLDRTransformToView(
                mldrTransform, mldrSpecialized, mpdrModel, mpdrTable);
        MPDRView mpdrView = mldrTransformToView.createOrModifyView(MPDRViewType.SPEC);

         */
    }


}
