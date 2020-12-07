package mcd.transform;

import delete.Delete;
import exceptions.TransformMCDException;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mdr.MDRConstraint;
import mldr.*;
import mldr.services.MLDRModelService;
import mldr.services.MLDRTableService;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MCDTransform {

    private IMCDModel  imcdModel ;
    private MLDRModel mldrModel ;

    public ArrayList<String> transform(IMCDModel imcdModel)  throws TransformMCDException {
        this.imcdModel = imcdModel ;
        ArrayList<String> resultat = new ArrayList<String>();

        // Création du modèle logique si inexistant
        mldrModel = foundOrCreateMLDRModel(
                PreferencesManager.instance().preferences().getMCDTOMLDR_MODE());

        // Change source MCD pour mldrColumnPK (Attribut AID <--> Entité)
        changeSourceMLDRColumnPK();

        // Delete orphans
        deleteTablesAndChildsOrphans();

        // Transformation des entités
        new MCDTransformToTable().fromEntities(imcdModel, mldrModel);
        return resultat;
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
                        mldrColumnPK.setMdElementSource(mcdAttributeAID);
                        mldrColumnPK.setMcdElementSource(mcdAttributeAID);
                    }

                    // Passe de présence d'un attribut AID à l'absence d'un attribut AID
                    if ((mcdElementSourceColumnPK instanceof MCDAttribute) &&
                            (mcdAttributeAID == null)){
                        mldrColumnPK.setMdElementSource(mcdEntitySource);
                        mldrColumnPK.setMcdElementSource(mcdEntitySource);
                    }
                }
            }
        }
    }

    private void deleteTablesAndChildsOrphans() {
        for (MLDRTable mldrTable : mldrModel.getMLDRTables()){
            boolean foundSource = MLDRModelService.foundMCDElementSource(mldrTable, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement(mldrTable);
            } else {
                deleteColumnsOrphans(mldrTable);
                deleteConstraintsOrphans(mldrTable);
            }
        }
    }

    private void deleteColumnsOrphans(MLDRTable mldrTable) {
        for (MLDRColumn mldrColumn : mldrTable.getMLDRColumns()) {
            boolean foundSource = MLDRModelService.foundMCDElementSource(mldrTable, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement(mldrColumn);
            }
        }
    }

    private void deleteConstraintsOrphans(MLDRTable mldrTable) {
        for (MDRConstraint mdrConstraint : mldrTable.getMDRConstraints()) {
            boolean foundSource = MLDRModelService.foundMCDElementSource(mldrTable, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement(mdrConstraint);
            }
        }
    }

}
