package transform.mldrtompdr;

import console.Console;
import delete.Delete;
import exceptions.TransformMCDException;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.MLDRModel;
import mldr.services.MLDRModelService;
import mpdr.MPDRModel;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.MDTransform;

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

            //Suppression des MPDRElement absents de l'itération
            deleteMDRElementNotInIteration();


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
        if (mldrtompdrDb.equals(Preferences.MLDRTOMPDR_DB_ORACLE)){
            MPDROracleModel mpdrOracleModel = MLDRModelService.getMPDRModelOracle(mldrModel);
            if (mpdrOracleModel == null){
                mpdrOracleModel = MVCCDElementFactory.instance().createMPDRModelOracle(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrOracleModel);
            }
            return mpdrOracleModel;
        }
        if (mldrtompdrDb.equals(Preferences.MLDRTOMPDR_DB_MYSQL)){
            MPDRMySQLModel mpdrMySQLModel = MLDRModelService.getMPDRModelMySQL(mldrModel);
            if (mpdrMySQLModel == null){
                mpdrMySQLModel = MVCCDElementFactory.instance().createMPDRModelMySQL(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrMySQLModel);
            }
            return mpdrMySQLModel;
        }
        if (mldrtompdrDb.equals(Preferences.MLDRTOMPDR_DB_POSTGRESQL)){
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

    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementWithIteration() {
        return mpdrModel.getIMDRElementsWithIteration();

    }
}
