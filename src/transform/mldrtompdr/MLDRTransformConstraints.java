package transform.mldrtompdr;

import main.MVCCDManager;
import mdr.MDRConstraint;
import mldr.MLDRFK;
import mldr.MLDRPK;
import mldr.MLDRTable;
import mldr.MLDRUnique;
import mldr.interfaces.IMLDRElement;
import mpdr.*;
import mpdr.interfaces.IMPDRElement;
import resultat.ResultatElement;
import resultat.ResultatLevel;

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
        //TODO-1 A reprendre et spécialiser par genre de contrainte
        /*
        for (MDRConstraint mldrConstraint : mldrTable.getMDRConstraints()){
            if(mldrConstraint instanceof MLDRPK) {
                MDRConstraint mpdrConstraint = transformConstraint(mldrConstraint);
                Trace.println("MPDRPK   " + mpdrConstraint.getName());
            }
        }
        for (MDRConstraint mldrConstraint : mldrTable.getMDRConstraints()){
            if(mldrConstraint instanceof MLDRFK) {
                MDRConstraint mpdrConstraint = transformConstraint(mldrConstraint);
            }
        }
        for (MDRConstraint mldrConstraint : mldrTable.getMDRConstraints()){
            boolean c1 = !(mldrConstraint instanceof MLDRPK);
            boolean c2 = !(mldrConstraint instanceof MLDRFK);
            if(c1 && c2) {
                MDRConstraint mpdrConstraint = transformConstraint(mldrConstraint);
            }
        }

         */
        for (MDRConstraint mldrConstraint : mldrTable.getMDRConstraints()){
            MDRConstraint mpdrConstraint = transformConstraint(mldrConstraint);
        }
    }



    private MDRConstraint transformConstraint(MDRConstraint mldrConstraint) {
        MDRConstraint mpdrConstraint = mpdrTable.getMPDRConstraintByMLDRConstraintSource(mldrConstraint);
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
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrConstraint);

        }

        // Le temps de développement
        if (mpdrConstraint != null) {
            modifyConstraint(mldrConstraint, mpdrConstraint );
            // Transformation des paramètres
            MLDRTransformParameters mldrTransformParameters = new MLDRTransformParameters(
                    mldrTransform, mldrConstraint, mpdrModel, mpdrConstraint);
            mldrTransformParameters.transformParameters();

            mpdrConstraint.setIteration(mldrTransform.getIteration());
        } else {
            String message = "La contrainte " + mldrConstraint.getName() +" de type " +
                    mldrConstraint.getClass().getName() + " n'est pas encore implantée pour la base de données " +
                    mpdrModel.getDb().getText();
            mldrTransform.getResultat().add(new ResultatElement(message, ResultatLevel.INFO));
        }



        return mpdrConstraint;
    }

    private void modifyConstraint(MDRConstraint mldrConstraint, MDRConstraint mpdrConstraint ) {
        MLDRTransformService.modifyNames((IMLDRElement) mldrConstraint, (IMPDRElement) mpdrConstraint);
        MLDRTransformService.modifyName(mpdrModel, mpdrConstraint);

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
    }

}
