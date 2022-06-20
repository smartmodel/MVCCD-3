package transform.mldrtompdr;

import console.ViewLogsManager;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mdr.MDRConstraint;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.*;
import mldr.services.MLDRModelService;
import mpdr.*;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.mysql.MPDRMySQLModel;
import mpdr.oracle.MPDROracleModel;
import mpdr.postgresql.MPDRPostgreSQLModel;
import mpdr.tapis.MPDRView;
import mpdr.tapis.MPDRViewType;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.MDTransform;

import java.util.ArrayList;

public class MLDRTransform extends MDTransform {

    private MLDRModel mldrModel ;
    private MPDRModel mpdrModel ;

    public boolean transform(MLDRModel mldrModel) {
        this.mldrModel = mldrModel ;

        // Création du modèle physique si inexistant
        mpdrModel = foundOrCreateMPDRModel(
                PreferencesManager.instance().preferences().getMLDRTOMPDR_DB());

        //Clonage du modèle avant transformation
        MPDRModel mpdrModelClone = (MPDRModel) mpdrModel.cloneDeep();

        boolean ok = true;
        try {
            mpdrModel.incrementeIteration();
            // Au cas où il y aurait changement de préférences
            mpdrModel.adjustProperties();

            // Ressources pour les APIs de tables
            if (mpdrModel.getMPDR_TAPIs()) {
                if (mpdrModel instanceof IMPDRModelRequirePackage) {
                    MLDRTransformResources mldrTransformResources = new MLDRTransformResources(this, mldrModel, mpdrModel);
                    //mldrTransformResources.createOrModifyBoxPackages();
                }
            }

                // Transformation des tables
            MLDRTransformTables mldrTransformTables = new MLDRTransformTables(this, mldrModel, mpdrModel);
            mldrTransformTables.transformTables();

            //Etablissement des référencements entre MPDRElement (FKs, Colonnes de FKs...
            referencingBetweenPKsAndFKs();

            // Transformation des relationFKs
            MLDRTransformRelations mldrTransformRelations = new MLDRTransformRelations(this, mldrModel, mpdrModel);
            mldrTransformRelations.transformRelations();

            // Vues des TAPIs
            //#MAJ 2022-03-25 Erreur génération des vues. La contrainte PK de la table parent doit exister ...
            // Donc la génération doit se faire lorsque toutes les contraintes de tables sont créées
            for (MLDRTable mldrTable : mldrModel.getMLDRTables()){
                // A généraliser car que pour les vues de spécialisation...
                if (mldrTable.getMDRConstraintCustomSpecialized() != null){
                    MLDRTransformToView mldrTransformToView = new MLDRTransformToView(
                            this,
                            (MLDRConstraintCustomSpecialized) mldrTable.getMDRConstraintCustomSpecialized(),
                            mpdrModel,
                            mpdrModel.getMPDRTableByMLDRTableSource(mldrTable));
                    MPDRView mpdrView = mldrTransformToView.createOrModifyView(MPDRViewType.SPEC);
                }
            }
            //Suppression des MPDRElement absents de l'itération
            deleteMDRElementNotInIteration();

            //Rafraichir l'arbre
            mpdrModel.refreshTreeMPDR();

            // Traçage de changement de projet
            //TODO-1 Véfier la mise à jour effective
            MVCCDManager.instance().setDatasProjectChanged(true);

            return ok;
        } catch(Exception e){
            //undoTransform(mpdrModelClone);
            ViewLogsManager.catchException(e, "Erreur interne dans la classe de transformation");
            return false;
        }
    }

    private void undoTransform(MPDRModel mpdrModelClone) {
        //Delete.deleteMVCCDElement(mpdrModel);
        mpdrModel.delete();
        mpdrModelClone.setParent((MVCCDElement) mldrModel);
        MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrModelClone);
    }


    private MPDRModel foundOrCreateMPDRModel(String mldrtompdrDb) {
        Preferences preferences = PreferencesManager.instance().preferences();
        if (mldrtompdrDb.equals(Preferences.DB_ORACLE)){
            MPDROracleModel mpdrOracleModel = MLDRModelService.getMPDRModelOracle(mldrModel);
            if (mpdrOracleModel == null){
                mpdrOracleModel = MVCCDElementFactory.instance().createMPDROracleModel(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrOracleModel);
            }
            //TODO-2 A voir l'organisation des préférences et MPDR
            // Provisoirement,m il faut le faire ici pour que tout changement dans les préférences soient répercutées
            // Ensuite, les préférences ne devraient être utiles que pour la création ? A voir! 
            mpdrOracleModel.setMpdrDbPK(preferences.getMPDRORACLE_PK_GENERATE());
            mpdrOracleModel.setTapis(preferences.getMPDRORACLE_TAPIS());
            mpdrOracleModel.setSequencePKNameFormat(preferences.getMPDRORACLE_SEQPK_NAME_FORMAT());
            mpdrOracleModel.setTriggerTableNameFormat(preferences.getMPDRORACLE_TRIGGER_TABLE_NAME_FORMAT());
            mpdrOracleModel.setTriggerViewNameFormat(preferences.getMPDRORACLE_TRIGGER_VIEW_NAME_FORMAT());
            mpdrOracleModel.setPackageNameFormat(preferences.getMPDRORACLE_PACKAGE_NAME_FORMAT());
            mpdrOracleModel.setViewNameFormat(preferences.getMPDRORACLE_VIEW_NAME_FORMAT());
            mpdrOracleModel.setTableJnalNameFormat(preferences.getMPDRORACLE_TABLEJNAL_NAME_FORMAT());
            mpdrOracleModel.setCheckColumnDatatypeNameFormat(preferences.getMPDRORACLE_CHECK_COLUMNDATATYPE_NAME_FORMAT());
            mpdrOracleModel.setCheckColumnDatatypeMax30NameFormat(preferences.getMPDRORACLE_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
            return mpdrOracleModel;
        }
        if (mldrtompdrDb.equals(Preferences.DB_MYSQL)){
            MPDRMySQLModel mpdrMySQLModel = MLDRModelService.getMPDRModelMySQL(mldrModel);
            if (mpdrMySQLModel == null){
                mpdrMySQLModel = MVCCDElementFactory.instance().createMPDRMySQLModel(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrMySQLModel);
            }
            mpdrMySQLModel.setMpdrDbPK(preferences.getMPDRMYSQL_PK_GENERATE());
            mpdrMySQLModel.setTapis(preferences.getMPDRMYSQL_TAPIS());
            mpdrMySQLModel.setSequencePKNameFormat(preferences.getMPDRMYSQL_SEQPK_NAME_FORMAT());
            mpdrMySQLModel.setTriggerTableNameFormat(preferences.getMPDRMYSQL_TRIGGER_TABLE_NAME_FORMAT());
            mpdrMySQLModel.setTriggerViewNameFormat(preferences.getMPDRMYSQL_TRIGGER_VIEW_NAME_FORMAT());
            mpdrMySQLModel.setViewNameFormat(preferences.getMPDRMYSQL_VIEW_NAME_FORMAT());
            mpdrMySQLModel.setTableJnalNameFormat(preferences.getMPDRMYSQL_TABLEJNAL_NAME_FORMAT());
            mpdrMySQLModel.setCheckColumnDatatypeNameFormat(preferences.getMPDRMYSQL_CHECK_COLUMNDATATYPE_NAME_FORMAT());
            mpdrMySQLModel.setCheckColumnDatatypeMax30NameFormat(preferences.getMPDRMYSQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
            return mpdrMySQLModel;
        }
        if (mldrtompdrDb.equals(Preferences.DB_POSTGRESQL)){
            MPDRPostgreSQLModel mpdrPostgreSQLModel = MLDRModelService.getMPDRModelPostgreSQL(mldrModel);
            if (mpdrPostgreSQLModel == null){
                mpdrPostgreSQLModel = MVCCDElementFactory.instance().createMPDRPostgreSQLModel(mldrModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrPostgreSQLModel);
            }
            mpdrPostgreSQLModel.setMpdrDbPK(preferences.getMPDRPOSTGRESQL_PK_GENERATE());
            mpdrPostgreSQLModel.setTapis(preferences.getMPDRORACLE_TAPIS());
            mpdrPostgreSQLModel.setSequencePKNameFormat(preferences.getMPDRPOSTGRESQL_SEQPK_NAME_FORMAT());
            mpdrPostgreSQLModel.setTriggerTableNameFormat(preferences.getMPDRPOSTGRESQL_TRIGGER_TABLE_NAME_FORMAT());
            mpdrPostgreSQLModel.setTriggerViewNameFormat(preferences.getMPDRPOSTGRESQL_TRIGGER_VIEW_NAME_FORMAT());
            mpdrPostgreSQLModel.setViewNameFormat(preferences.getMPDRPOSTGRESQL_VIEW_NAME_FORMAT());
            mpdrPostgreSQLModel.setTableJnalNameFormat(preferences.getMPDRPOSTGRESQL_TABLEJNAL_NAME_FORMAT());
            mpdrPostgreSQLModel.setCheckColumnDatatypeNameFormat(preferences.getMPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_NAME_FORMAT());
            mpdrPostgreSQLModel.setCheckColumnDatatypeMax30NameFormat(preferences.getMPDRPOSTGRESQL_CHECK_COLUMNDATATYPE_MAX30_NAME_FORMAT());
            return mpdrPostgreSQLModel;
        }

        return null;
    }


    @Override
    public int getIteration() {
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
    private void referencingBetweenPKsAndFKs() {
        for (MPDRTable mpdrTable : mpdrModel.getMPDRTables()){
            for (MPDRColumn mpdrColumn : mpdrTable.getMPDRColumns()){
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
        //MLDRTable mldrTablePK = (MLDRTable) mldrColumnPK.getMDRTableAccueil();

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