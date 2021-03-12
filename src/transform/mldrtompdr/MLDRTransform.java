package transform.mldrtompdr;

import console.Console;
import delete.Delete;
import exceptions.TransformMCDException;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mdr.MDRColumn;
import mdr.MDRConstraint;
import mdr.MDRFK;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.*;
import mldr.services.MLDRModelService;
import mpdr.*;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.MDTransform;
import utilities.Trace;

import java.util.ArrayList;

public class MLDRTransform extends MDTransform {

    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public ArrayList<String> transform(MLDRModel mldrModel) {
        this.mldrModel = mldrModel ;
        ArrayList<String> resultat = new ArrayList<String>();

        // Création du modèle physique si inexistant
        mpdrModel = foundOrCreateMPDRModel(
                PreferencesManager.instance().preferences().getMLDRTOMPDR_DB());

        //Clonage du modèle avant transformation
        MPDRModel mpdrModelClone = (MPDRModel) mpdrModel.cloneDeep();

        try {
            mldrModel.incrementeIteration();

            // Transformation des tables
            MLDRTransformTables mldrTransformTables = new MLDRTransformTables(this, mldrModel, mpdrModel);
            mldrTransformTables.transformTables();

            //Etablissement des référencements entre MPDRElement (FKs, Colonnes de FKs...
            referencingBetweenElements();
            //Suppression des MPDRElement absents de l'itération
            deleteMDRElementNotInIteration();

            //Rafraichir l'arbre
            mpdrModel.refreshTreeMPDR();

        } catch(TransformMCDException e){
            resultat.add(e.getMessage());
            undoTransform(mpdrModelClone);
            Console.printMessages(resultat);
            return resultat;
        }

        return resultat;
    }

    private void undoTransform(MPDRModel mpdrModelClone) {
        Delete.deleteMVCCDElement(mpdrModel);
        mpdrModelClone.setParent((MVCCDElement) mldrModel);
        MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrModelClone);
    }


    private MPDRModel foundOrCreateMPDRModel(String mldrtompdrDb) {
        if (mldrtompdrDb.equals(Preferences.MPDR_DB_ORACLE)){
            MPDROracleModel mpdrOracleModel = MLDRModelService.getMPDRModelOracle(mldrModel);
            if (mpdrOracleModel == null){
                mpdrOracleModel = MVCCDElementFactory.instance().createMPDRModelOracle(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrOracleModel);
            }
            return mpdrOracleModel;
        }
        if (mldrtompdrDb.equals(Preferences.MPDR_DB_MYSQL)){
            MPDRMySQLModel mpdrMySQLModel = MLDRModelService.getMPDRModelMySQL(mldrModel);
            if (mpdrMySQLModel == null){
                mpdrMySQLModel = MVCCDElementFactory.instance().createMPDRModelMySQL(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrMySQLModel);
            }
            return mpdrMySQLModel;
        }
        if (mldrtompdrDb.equals(Preferences.MPDR_DB_POSTGRESQL)){
            MPDRPostgreSQLModel mpdrPostgreSQLModel = MLDRModelService.getMPDRModelPostgreSQL(mldrModel);
            if (mpdrPostgreSQLModel == null){
                mpdrPostgreSQLModel = MVCCDElementFactory.instance().createMPDRModelPostgreSQL(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrPostgreSQLModel);
            }
            return mpdrPostgreSQLModel;
        }

        return null;
    }


    @Override
    protected int getIteration() {
        return mpdrModel.getIteration();
    }

    /*
    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration() {
        return mpdrModel.getIMDRElementsWithIteration();

    }

     */

    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope() {
        return mpdrModel.getIMDRElementsWithIterationInScope();

    }
    private void referencingBetweenElements() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()){
            for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()){
                Trace.println ("1 "+ mpdrColumn.getClass().getName() + "  " + mpdrColumn.getName() + "  - Fk : " + mpdrColumn.isFk() );
                if (mpdrColumn.isFk()) {
                    referencingColumnFK(mpdrColumn);
                }
            }
            for (MDRConstraint mpdrConstraint : mpdrTable.getMDRConstraints()){
                if (mpdrConstraint instanceof MPDRFK) {
                    referencingFK((MPDRFK) mpdrConstraint);
                }
            }
        }
    }


    private void referencingColumnFK(MPDRColumn mpdrColumnFK) {
        //Niveau physique
        MPDRTable mpdrTableColumnFK = (MPDRTable) mpdrColumnFK.getMDRTableAccueil();

        // Niveau logique
        MLDRColumn mldrColumnFK = (MLDRColumn) mpdrColumnFK.getMdElementSource();
        MLDRColumn mldrColumnPK = (MLDRColumn) mldrColumnFK.getMDRColumnPK();
        MLDRTable mldrTablePK = (MLDRTable) mldrColumnPK.getMDRTableAccueil();

        // Complément niveau physique
        MPDRColumn mpdrColumnPK = (MPDRColumn) mpdrModel.getIMPDRElementByMLDRElementSource(mldrColumnPK);

        // Lien avec la PK
        if (mpdrColumnFK.getMDRColumnPK() != mldrColumnFK.getMDRColumnPK()) {
            mpdrColumnFK.setMdrColumnPK(mpdrColumnPK);
        }
    }

    private void referencingFK(MPDRFK mpdrFK) {
        //Niveau physique
        MPDRTable mpdrTableFK = (MPDRTable) mpdrFK.getMDRTableAccueil();

        // Niveau logique
        MLDRFK mldrFK = (MLDRFK) mpdrFK.getMdElementSource();
        MLDRPK mldrPK = (MLDRPK) mldrFK.getMdrPK();
        MLDRTable mldrTablePK = (MLDRTable) mldrPK.getMDRTableAccueil();

        // Complément niveau physique
        MPDRPK mpdrPK = (MPDRPK) mpdrModel.getIMPDRElementByMLDRElementSource(mldrPK);

        // Lien avec la PK
        if (mpdrFK.getMdrPK() != mldrFK.getMdrPK()) {
            mpdrFK.setMdrPK(mpdrPK);
        }
    }
}