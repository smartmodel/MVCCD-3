package transform.mcdtomldr;

import delete.Delete;
import exceptions.CodeApplException;
import exceptions.TransformMCDException;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import md.MDElement;
import mdr.MDRConstraint;
import mdr.MDRElement;
import mdr.MDRParameter;
import mldr.*;
import mldr.interfaces.IMDLRConstraint;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.interfaces.IMLDRRelation;
import mldr.services.MLDRModelService;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectManager;
import project.ProjectService;
import utilities.Trace;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MCDTransform {

    private IMCDModel  imcdModel ;
    private MLDRModel mldrModel ;
    private ArrayList<MCDTransformElementTrace> mcdTransformElementTraces = new ArrayList<MCDTransformElementTrace>();


    public ArrayList<String> transform(IMCDModel imcdModel)  throws TransformMCDException {
        this.imcdModel = imcdModel ;
        ArrayList<String> resultat = new ArrayList<String>();

        // Création du modèle logique si inexistant
        mldrModel = foundOrCreateMLDRModel(
                PreferencesManager.instance().preferences().getMCDTOMLDR_MODE());

        // Change source MCD pour mldrColumnPK (Attribut AID <--> Entité)
        changeSourceMLDRColumnPK();
        //TODO-0 chgt entre n:n et entité associative

        // Delete orphans (avec source MCD inexistante)
        // Suppression de l'élément MCD entre 2 iérations
        deleteOrphansFromMCDElement();

        // Transformation des entités
        MCDTransformToTable mcdTransformToTable = new MCDTransformToTable(this, imcdModel, mldrModel);
        mcdTransformToTable.createOrModifyFromAllEntities();

        // Delete  orphans (avec trace inexistante dans la nouvelle itération)
        // L'élément MLDR n'est plus traité dans la nouvelle itération
        deleteOrphansFromTrace();


        //Mémorise les cibles de la transformation
        recordTraces();

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

    private void deleteOrphansFromMCDElement() {
        for (MLDRTable mldrTable : mldrModel.getMLDRTables()) {
            boolean foundSource = MLDRModelService.foundMCDElementSource(mldrTable, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement(mldrTable);
            } else {
                deleteColumnsOrphans(mldrTable);
                deleteConstraintsOrphans(mldrTable);
            }
        }

        for (IMLDRRelation imldrRelation : mldrModel.getIMLDRRelations()) {
            boolean foundSource = MLDRModelService.foundMCDElementSource((IMLDRElementWithSource) imldrRelation, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement((MVCCDElement) imldrRelation);
            }
        }
    }

    private void deleteColumnsOrphans(MLDRTable mldrRelation) {
        for (MLDRColumn mldrColumn : mldrRelation.getMLDRColumns()) {
            boolean foundSource = MLDRModelService.foundMCDElementSource(mldrColumn, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement(mldrColumn);
            }
        }
    }

    private void deleteConstraintsOrphans(MLDRTable mldrRelation) {
        for (MDRConstraint mdrConstraint : mldrRelation.getMDRConstraints()) {
            boolean foundSource = MLDRModelService.foundMCDElementSource((IMLDRElementWithSource) mdrConstraint, imcdModel);
            if (!foundSource) {
                Delete.deleteMVCCDElement(mdrConstraint);
            } else {
                deleteParametersOrphans(mdrConstraint);
            }
        }
    }

    private void deleteParametersOrphans(MDRConstraint mdrConstraint) {
        for (MDRParameter mdrParameter : mdrConstraint.getMDRParameters()){
            MDRElement target = (MDRElement) mdrParameter.getTarget();
            if (target != null) {
                boolean foundSource = MLDRModelService.foundMCDElementSource((IMLDRElementWithSource) target, imcdModel);
                if (!foundSource) {
                    Delete.deleteMVCCDElement(mdrParameter);
                }
            }
        }
    }


    private void initializeTraces() {
        for (MCDEntity mcdEntity : ProjectService.getMCDEntities((MCDElement) imcdModel)) {
            createTrace(mcdEntity);
            for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
                createTrace(mcdAttribute);
            }
            for (MCDConstraint mcdConstraint : mcdEntity.getMCDConstraints()){
                createTrace(mcdConstraint);
            }
        }
    }


    private MCDTransformElementTrace createTrace(IMCDElementWithTargets imcdElementWithTargets) {
        MCDTransformElementTrace mcdTransformElementTrace = new MCDTransformElementTrace(imcdElementWithTargets);
        mcdTransformElementTraces.add(mcdTransformElementTrace);
        return mcdTransformElementTrace;
    }


    private void recordTraces() {
        for (MCDTransformElementTrace mcdTransformElementTrace : mcdTransformElementTraces){
            IMCDElementWithTargets imcdElementWithTargets =mcdTransformElementTrace.getImcdElementWithTargets();
            imcdElementWithTargets.setImldrElementTargets(mcdTransformElementTrace.getImldrElementTargets());
            /*
            Trace.println("MCD   :" + ((MVCCDElement)mcdTransformElementTrace.getImcdElementWithTargets()).getName());
            for (IMLDRElement imldrElement : mcdTransformElementTrace.getImldrElementTargets()){
                Trace.println("MLDR   " + ((MVCCDElement)imldrElement).getName());
            }

             */
        }
    }

    public MCDTransformElementTrace findTrace(IMCDElementWithTargets imcdElementWithTargets){
        for (MCDTransformElementTrace mcdTransformElementTrace : mcdTransformElementTraces){
             if (mcdTransformElementTrace.getImcdElementWithTargets() == imcdElementWithTargets ){
                return mcdTransformElementTrace;
            }
        }
        return createTrace(imcdElementWithTargets);

        /*
        throw new CodeApplException("MCDTransform.findTrace - la trace pour " +
                imcdElementWithTargets.getClass().getName() + "de classe " +
                imcdElementWithTargets.getClass().getName() + "n'a pas été trouvée!");

         */
    }

    public void addInTrace(IMCDElementWithTargets imcdElementWithTargets, IMLDRElement imldrElement) {
        MCDTransformElementTrace  mcdTransformElementTrace = findTrace(imcdElementWithTargets);
        mcdTransformElementTrace.getImldrElementTargets().add(imldrElement);
    }

    private void deleteOrphansFromTrace() {
        for (MCDTransformElementTrace mcdTransformElementTrace : mcdTransformElementTraces) {
            ArrayList<IMLDRElement> traceTargets = mcdTransformElementTrace.getImldrElementTargets();
            IMCDElementWithTargets imcdElementWithTargets = mcdTransformElementTrace.getImcdElementWithTargets();
            ArrayList<IMLDRElement> imcdElementTargets = imcdElementWithTargets.getImldrElementTargets();
            // Si une trace existe
            if (UtilDivers.isNotEmpty(imcdElementTargets)) {
                    for (IMLDRElement imldrElement : imcdElementTargets) {
                        if (!traceTargets.contains(imldrElement)) {
                            MDElement mldrElement = (MDElement) imldrElement;
                            // Si la référence de l'élément n'est pas nulle
                            if (mldrElement != null) {
                                // S'assurer que l'élément est toujours existant
                                // Que ce ne soit pas une référence qui n'existe plus
                                // Surtout en phase de développement !
                                if (ProjectService.getElementById(mldrElement.getId() )!= null) {
                                    // Si le parent existe toujours
                                    if (mldrElement.getParent() != null) {
                                        Delete.deleteMVCCDElement(mldrElement);
                                    }
                                }
                            }
                        }
                    }

            }
        }
    }
}
