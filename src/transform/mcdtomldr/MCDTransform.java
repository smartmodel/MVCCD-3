package transform.mcdtomldr;

import exceptions.TransformMCDException;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
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
        this.imcdModel = imcdModel ;

        ArrayList<String> resultat = new ArrayList<String>();

        // Création du modèle logique si inexistant
        mldrModel = foundOrCreateMLDRModel(
                PreferencesManager.instance().preferences().getMCDTOMLDR_MODE());

        mldrModel.incrementeIteration();

        // Change source MCD pour mldrColumnPK (Attribut AID <--> Entité)
        changeSourceMLDRColumnPK();
        //TODO-0 chgt entre n:n et entité associative
        
        // Transformation des entités
        MCDTransformToTable mcdTransformToTable = new MCDTransformToTable(this, imcdModel, mldrModel);
        mcdTransformToTable.createOrModifyFromAllEntities();

        //Suppression des MLDRElement absents de l'itération
        deleteMDRElementNotInIteration();

        // Enregistrer la dernière transformation
        //TODO-0 Mettre après le traitement d'erreur lorsqu'il sera fait!
        imcdModel.setLastTransformedMLDRModel(mldrModel);
        return resultat;
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
        for (MLDRTable mldrTable : mldrModel.getMLDRTables()) {
            MLDRColumn mldrColumnPK = mldrTable.getMLDRColumnPKProper();
            if (mldrColumnPK != null) {
                MCDElement mcdElementSourceColumnPK = mldrColumnPK.getMcdElementSource();
                MCDElement mcdElementSourceTable = mldrTable.getMcdElementSource();
                if (mcdElementSourceTable instanceof MCDEntity) {
                    MCDEntity mcdEntitySource = (MCDEntity) mcdElementSourceTable;
                    MCDAttribute mcdAttributeAID = mcdEntitySource.getMCDAttributeAID();

                    // Passe d'absence d'un attribut AID à présence d'un attribut AID
                    if ((mcdElementSourceColumnPK instanceof MCDEntity) &&
                            (mcdAttributeAID != null)){
                        mldrColumnPK.setMcdElementSource(mcdAttributeAID);
                    }

                    // Passe de présence d'un attribut AID à l'absence d'un attribut AID
                    if ((mcdElementSourceColumnPK instanceof MCDAttribute) &&
                            (mcdAttributeAID == null)){
                        mldrColumnPK.setMcdElementSource(mcdEntitySource);
                    }
                }
            }
        }
    }

    @Override
    protected ArrayList<IMDRElementWithIteration> getIMDRElementWithIteration() {
        return mldrModel.getIMDRElementsWithIteration();
    }


}
