package transform.mcdtomldr;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mdr.interfaces.IMDRElementWithIteration;
import messages.MessagesBuilder;
import mldr.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.MDTransform;

import java.util.ArrayList;

public class MCDTransform extends MDTransform {

    private IMCDModel  imcdModel ;
    private MLDRModel mldrModel ;


    public boolean transform(IMCDModel imcdModel)   {
        this.imcdModel = imcdModel;
        // Création du modèle logique si inexistant
        mldrModel = foundOrCreateMLDRModel(
                PreferencesManager.instance().preferences().getMCDTOMLDR_MODE());

        //Clonage du modèle avant transformation
        //TODO-1 Faut-il s'arrêter au niveau MLD-R ou descendre vers tous les MPD-R ?
        MLDRModel mldrModelClone = (MLDRModel) mldrModel.cloneDeep();

        boolean ok = true;
        try {
            mldrModel.incrementeIteration();

            // Change source MCD pour mldrColumnPK (Attribut AID <--> Entité)
            ok = changeSourceMLDRColumnPK();
           //TODO-0 Ajouter le traitement d'erreur comme pour changeSourceMLDRColumnPK();

            // Transformation des entités
            MCDTransformToTable mcdTransformToTable = new MCDTransformToTable(this, imcdModel, mldrModel);
            mcdTransformToTable.createOrModifyFromAllEntities();

            // Transformation des associations n:n sans entités associatives
            mcdTransformToTable.createOrModifyFromAllAssociationsNN();

            // Transformation des associations non identifiantes de composition
            MCDTransformToFK mcdTransformToFK = new MCDTransformToFK(this);
            mcdTransformToFK.createOrModifyFromAllAssNotIdCompAndNotNN(imcdModel, mldrModel);

            // Transformation des contraintes d'unicité
            MCDTransformToUnique mcdTransformToUnique = new MCDTransformToUnique(this);
            mcdTransformToUnique.createOrModifyFromAllUnicities(imcdModel, mldrModel);

           // Transformation des LP - liens de programmation

            //Suppression des MLDRElement absents de l'itération
            deleteMDRElementNotInIteration();

            // Enregistrer la dernière transformation
            imcdModel.setLastTransformedMLDRModel(mldrModel);

            //Rafraichir l'arbre
            mldrModel.refreshTreeMLDR();

            // Traçage de changement de projet
            //TODO-1 Véfier la mise à jour effective
            MVCCDManager.instance().setDatasProjectChanged(true);

            return ok;
        } catch(Exception e){
            undoTransform(mldrModelClone);
            ViewLogsManager.catchException(e, "Erreur interne dans la classe de transformation");
            return false;
        }
    }


    private void undoTransform(MLDRModel mldrModelClone) {
        //Delete.deleteMVCCDElement(mldrModel);
        mldrModel.delete();
        mldrModelClone.setParent((MVCCDElement) imcdModel);
        MVCCDManager.instance().addNewMVCCDElementInRepository(mldrModelClone);
    }

    public int getIteration(){
        return mldrModel.getIteration();
    }

    private MLDRModel foundOrCreateMLDRModel(String mcdtomldrMode) {
        if (mcdtomldrMode.equals(Preferences.MCDTOMLDR_MODE_DT)){
            MLDRModelDT mldrModelDT = IMCDModelService.getMLDRModelDT(imcdModel);
            if (mldrModelDT == null){
                mldrModelDT = MVCCDElementFactory.instance().createMLDRModelDT(imcdModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mldrModelDT);
            }
            return mldrModelDT ;
        }
        if (mcdtomldrMode.equals(Preferences.MCDTOMLDR_MODE_TI)){
            MLDRModelTI mldrModelTI = IMCDModelService.getMLDRModelTI(imcdModel);
            if (mldrModelTI == null){
                mldrModelTI = MVCCDElementFactory.instance().createMLDRModelTI(imcdModel);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mldrModelTI);
            }
            return mldrModelTI ;
        }
        return null;
    }

    private boolean changeSourceMLDRColumnPK() {
        boolean ok = true;
        int indTable = 0;
        //for (MLDRTable mldrTable : mldrModel.getMLDRTables()) {
        while (indTable < mldrModel.getMLDRTables().size()) {
            MLDRTable mldrTable = mldrModel.getMLDRTables().get(indTable);
            try {
                MLDRColumn mldrColumnPK = mldrTable.getMLDRColumnPKProper();
                if (mldrColumnPK != null) {
                    MCDElement mcdElementSourceColumnPK = mldrColumnPK.getMcdElementSource();
                    MCDElement mcdElementSourceTable = mldrTable.getMcdElementSource();
                    if (mcdElementSourceTable instanceof MCDEntity) {
                        MCDEntity mcdEntitySource = (MCDEntity) mcdElementSourceTable;
                        MCDAttribute mcdAttributeAID = mcdEntitySource.getMCDAttributeAID();

                        // Passe d'absence d'un attribut AID à présence d'un attribut AID
                        if ((mcdElementSourceColumnPK instanceof MCDEntity) &&
                                (mcdAttributeAID != null)) {
                            mldrColumnPK.setMcdElementSource(mcdAttributeAID);
                        }

                        // Passe de présence d'un attribut AID à l'absence d'un attribut AID
                        if ((mcdElementSourceColumnPK instanceof MCDAttribute) &&
                                (mcdAttributeAID == null)) {
                            mldrColumnPK.setMcdElementSource(mcdEntitySource);
                        }
                    }
                }
            }
            catch (Exception e){
                //Delete.deleteMVCCDElement(mldrTable);
                mldrTable.delete();
                // Information de suppression
                String message = MessagesBuilder.getMessagesProperty ("transform.mcdtomldr.mtable.without.pk",
                        new String[] {mldrTable.getName() });
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
                ok = false;
            }
            indTable++;
        }
        return ok ;
    }


    /*
    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration() {
        return mldrModel.getIMDRElementsWithIteration();
    }

     */


    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope() {
        return mldrModel.getIMDRElementsWithIterationInScope();
    }


}
