package transform.mldrtompdr;

import main.MVCCDElementFactory;
import main.MVCCDManager;
import mldr.MLDRModel;
import mldr.services.MLDRModelService;
import mpdr.MPDRModel;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MLDRTransform {

    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public ArrayList<String> transform(MLDRModel mldrModel) {
        this.mldrModel = mldrModel ;
        ArrayList<String> resultat = new ArrayList<String>();

        // Création du modèle physique si inexistant
        mpdrModel = foundOrCreateMPDRModel(
                PreferencesManager.instance().preferences().getMLDRTOMPDR_DB());

        // Transformation des tables
        MLDRTransformTables mldrTransformTables = new MLDRTransformTables(mldrModel, mpdrModel);
        mldrTransformTables.transformTables();


        return resultat;
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


}
