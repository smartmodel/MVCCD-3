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
        ArrayList<String> resultat = new ArrayList<String>(); // Pour les erreurs provoquant un arrêt du processus
        //#MAJ 2021-03-26 Console.clearMessages est appelé à chaque invocation de menu conceptuel du référentiel
        //Console.clearMessages();

        this.imcdModel = imcdModel;
        // Création du modèle logique si inexistant
        mldrModel = foundOrCreateMLDRModel(
                PreferencesManager.instance().preferences().getMCDTOMLDR_MODE());

        //Clonage du modèle avant transformation
        //TODO-1 Faut-il s'arrêter au niveau MLD-R ou descendre vers tous les MPD-R ?
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

            // Transformation des associations n:n sans entités associatives
            mcdTransformToTable.createOrModifyFromAllAssociationsNN();

            // Transformation des associations non identifiantes de composition
            MCDTransformToFK mcdTransformToFK = new MCDTransformToFK(this);
            mcdTransformToFK.createOrModifyFromAllAssNoIdOrIdNatural(imcdModel, mldrModel);

            //Suppression des MLDRElement absents de l'itération
            deleteMDRElementNotInIteration();

            // Enregistrer la dernière transformation
            imcdModel.setLastTransformedMLDRModel(mldrModel);

            //Rafraichir l'arbre
            mldrModel.refreshTreeMLDR();


        } catch(TransformMCDException e){
             resultat.add(e.getMessage());
             undoTransform(mldrModelClone);
             Console.printMessages(resultat);
        } finally {
            return resultat;
        }


        //TODO-0 Voir le traitement d'erreur fatale (A quel niveau ?)
        /*catch (Exception e){
            resultat.add(e.getMessage());
            //TODO-0 Affiner les erreurs nécessitant un appel au support!
            resultat.add("Veuillez signaler cette erreur au support...");
        }

         */
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
                Console.printMessage("La table " + mldrTable.getName() +" a été supprimée car sa contrainte de clé primaire est erronée ou manquante");
            }
            indTable++;
        }
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
