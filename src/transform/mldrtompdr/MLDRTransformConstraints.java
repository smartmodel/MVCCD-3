package transform.mldrtompdr;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDManager;
import mdr.MDRConstraint;
import mldr.MLDRFK;
import mldr.MLDRPK;
import mldr.MLDRTable;
import mldr.MLDRUnique;
import mldr.interfaces.IMLDRConstraint;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRFK;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.MPDRUnique;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRElement;

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
            IMPDRConstraint mpdrConstraint = transformConstraint((IMLDRConstraint) mldrConstraint);
        }
    }



    private IMPDRConstraint transformConstraint(IMLDRConstraint mldrConstraint) {
        IMPDRConstraint mpdrConstraint = mpdrTable.getMPDRConstraintByMLDRConstraintSource(mldrConstraint);
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
            MVCCDManager.instance().addNewMVCCDElementInRepository((MDRConstraint) mpdrConstraint);

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
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        }



        return mpdrConstraint;
    }

    private void modifyConstraint(IMLDRConstraint mldrConstraint, IMPDRConstraint mpdrConstraint ) {
        MLDRTransformService.modifyNames((IMLDRElement) mldrConstraint, (IMPDRElement) mpdrConstraint);
        MLDRTransformService.modifyName(mpdrModel, (MDRConstraint) mpdrConstraint);
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
