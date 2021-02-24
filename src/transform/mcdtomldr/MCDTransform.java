package transform.mcdtomldr;

import console.Console;
import delete.Delete;
import exceptions.TransformMCDException;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.*;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.MDTransform;

import java.util.ArrayList;

public class MCDTransform extends MDTransform {

    private IMCDModel  imcdModel ;
    private MLDRModel mldrModel ;


    public ArrayList<String> transform(IMCDModel imcdModel)  throws TransformMCDException {
        ArrayList<String> resultat = new ArrayList<String>();
        Console.clearMessages();

        this.imcdModel = imcdModel;
        // Création du modèle logique si inexistant
        mldrModel = foundOrCreateMLDRModel(
                PreferencesManager.instance().preferences().getMCDTOMLDR_MODE());

        //Clonage du modèle avant transformation
        MLDRModel mldrModelClone = (MLDRModel) mldrModel.cloneDeep();

        try {
            mldrModel.incrementeIteration();

            // Change source MCD pour mldrColumnPK (Attribut AID <--> Entité)
            changeSourceMLDRColumnPK();
            //TODO-0 chgt entre n:n et entité associative
            //TODO-0 Ajouter le traitement d'erreur comme pour changeSourceMLDRColumnPK();

            // Transformation des entités
            MCDTransformToTable mcdTransformToTable = new MCDTransformToTable(this, imcdModel, mldrModel);
            mcdTransformToTable.createOrModifyFromAllEntities();

            // transformation des associations n:n sans entités associatives
            mcdTransformToTable.createOrModifyFromAllAssociationsNN();

            //Suppression des MLDRElement absents de l'itération
            deleteMDRElementNotInIteration();

            // Enregistrer la dernière transformation
            imcdModel.setLastTransformedMLDRModel(mldrModel);

        } catch(TransformMCDException e){
             resultat.add(e.getMessage());
             undoTransform(mldrModelClone);
             Console.printMessages(resultat);
            return resultat;
        }

        //TODO-0 Voir le traitement d'erreur fatale (A quel niveau ?)
        /*catch (Exception e){
            resultat.add(e.getMessage());
            //TODO-0 Affiner les erreurs nécessitant un appel au support!
            resultat.add("Veuillez signaler cette erreur au support...");
        }

         */
        return resultat;
    }

    private void undoTransform(MLDRModel mldrModelClone) {
        Delete.deleteMVCCDElement(mldrModel);
        mldrModelClone.setParent((MVCCDElement) imcdModel);
        MVCCDManager.instance().addNewMVCCDElementInRepository(mldrModelClone);
    }

    protected int getIteration(){
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

    private void changeSourceMLDRColumnPK() {
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
                // Si la table est corrompue par une précédente erreur (pas de PK ou de colonne de PK)
                //TODO-0 Mettre en place une journalisation pour tracer cette suppression
                Delete.deleteMVCCDElement(mldrTable);
            }
            indTable++;
        }

    }

    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementWithIteration() {
        return mldrModel.getIMDRElementsWithIteration();
    }


}
