package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.TransformMCDException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mcd.services.MCDRelEndService;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRModel;
import mldr.MLDRPK;
import mldr.MLDRTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class MCDTransformToTable {

    private MCDTransform mcdTransform ;
    private IMCDModel  imcdModel ;
    private MLDRModel mldrModel ;

    public MCDTransformToTable(MCDTransform mcdTransform, IMCDModel imcdModel, MLDRModel mldrModel) {
        this.mcdTransform = mcdTransform;
        this.imcdModel = imcdModel;
        this.mldrModel = mldrModel;
    }


    public void createOrModifyFromAllEntities()  throws TransformMCDException{
        createOrModifyFromEntities(IMCDModelService.getMCDEntitiesConcrets(imcdModel));
        createOrModifyPKEntitiesInd(IMCDModelService.getMCDEntitiesIndependants(imcdModel));
        createOrModifyPKEntitiesNoInd(IMCDModelService.getMCDEntitiesConcretsNoInd(imcdModel));
    }

    private void createOrModifyFromEntities(ArrayList<MCDEntity> mcdEntitiesConcrets) {
        for (MCDEntity mcdEntityConcret : mcdEntitiesConcrets){
            createOrModifyFromEntity(mcdEntityConcret);
        }
    }

    private void createOrModifyFromEntity(MCDEntity mcdEntity) {
        // Table
        MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntity);
        if (mldrTable == null){
            mldrTable = mldrModel.createTable(mcdEntity);
            mldrTable.setMcdEntitySourceNature(mcdEntity.getNature());
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrTable);
        }
        modifyTable(mldrTable, mcdEntity);
        mldrTable.setIteration(mcdTransform.getIteration());

        // Attributs
        MCDTransformToColumn mcdTransformToColumn = new MCDTransformToColumn(mcdTransform);
        mcdTransformToColumn.createOrModifyFromAttributes(mcdEntity, mldrTable);
    }


    private void createOrModifyPKEntitiesInd(ArrayList<MCDEntity> mcdEntitiesIndependants)  throws TransformMCDException{
        for (MCDEntity mcdEntityIndependant : mcdEntitiesIndependants){
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntityIndependant);
            new MCDTransformToPK(mcdTransform).createOrModifyFromEntityInd(mcdEntityIndependant, mldrTable);
        }
    }

    private void createOrModifyPKEntitiesNoInd(ArrayList<MCDEntity> mcdEntitiesNoIndNoEntAss)  throws TransformMCDException {

        ArrayList<MCDEntity> mcdEntitiesToTransform = (ArrayList<MCDEntity>) mcdEntitiesNoIndNoEntAss.clone();
        int controle = mcdEntitiesToTransform.size();


        while (mcdEntitiesToTransform.size() > 0){
            ArrayList<MCDEntity> mcdEntitiesToTransformInner = (ArrayList<MCDEntity>) mcdEntitiesToTransform.clone();
            for (MCDEntity mcdEntity : mcdEntitiesToTransformInner){
                ArrayList<MCDRelEnd> mcdRelEndsParents = getMCDRRelEndsParentsNotNN(mcdEntity);
                mcdRelEndsParents.addAll(getMCDRRelEndsParentsNN(mcdEntity));
                ArrayList<MLDRTable> mldrTablesParents = getMLDRTablesParents(mcdRelEndsParents);
                if (pkParentsExists(mldrTablesParents)){
                    MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntity);
                    MLDRPK mldrPK = new MCDTransformToPK(mcdTransform).createOrModifyFromEntityConcretNoInd(mldrModel, mcdEntity, mldrTable,mcdRelEndsParents);
                    if (mldrPK != null){
                        // Suppression de l'entité dans la liste à transformer
                        mcdEntitiesToTransform.remove(mcdEntity);
                    }
                 }
            }
            if (controle == mcdEntitiesToTransform.size()){
                throw new CodeApplException("Erreur interne dans la boucle de transformation des entités non indépendantes");
            }
        }
    }

    public void createOrModifyFromAllAssociationsNN() {
        for (MCDAssociation mcdAssNN : IMCDModelService.getMCDAssociationsNN(imcdModel)){

        }
    }

    private boolean pkParentsExistsOld(MCDEntity mcdEntity) {

        boolean exist = true;

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getAssEndsIdCompChild();
        for (MCDAssEnd mcdAssEndIdCompChild : mcdAssEndsIdCompChild){
            MCDAssEnd mcdAssEndIdCompParent = mcdAssEndIdCompChild.getMCDAssEndOpposite() ;
            MCDEntity mcdEntityParent = mcdAssEndIdCompParent.getMcdEntity();
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntityParent);
            exist = exist && (mldrTable.getMLDRPK() != null);
        }

        ArrayList<MCDGSEnd> mcdAssEndsSpecialize = mcdEntity.getGSEndSpecialize();
        for (MCDGSEnd mcdGSEndSpecialize : mcdAssEndsSpecialize) {
            MCDGSEnd mcdGSEndGeneralize = mcdGSEndSpecialize.getMCDGSEndOpposite();
            MCDEntity mcdEntityGeneralize = mcdGSEndGeneralize.getMcdEntity();
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntityGeneralize);
            exist = exist && (mldrTable.getMLDRPK() != null);
        }

        return exist;
    }

    private boolean pkParentsExistsOld2(MCDEntity mcdEntity) {

        boolean exist = true;

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getAssEndsIdCompChild();
        ArrayList<MCDGSEnd> mcdAssEndsSpecialize = mcdEntity.getGSEndSpecialize();

        ArrayList<MCDRelEnd> mcdRelEndsChild = MCDRelEndService.convertToMCDRelEnd(mcdAssEndsIdCompChild);
        mcdRelEndsChild.addAll( MCDRelEndService.convertToMCDRelEnd(mcdAssEndsSpecialize));
        for (MCDRelEnd mcdRelEndChild : mcdRelEndsChild){
            MCDRelEnd mcdRelEndParent = mcdRelEndChild.getMCDRelEndOpposite() ;
            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getmElement();
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntityParent);
            exist = exist && (mldrTable.getMLDRPK() != null);
        }

        return exist;
    }

    private boolean pkParentsExistsOld3(MCDEntity mcdEntity) {

        boolean exist = true;

        for (MLDRTable mldrTableParent : getMLDRTablesParentsOld(mcdEntity)){
            exist = exist && (mldrTableParent.getMLDRPK() != null);
        }

        return exist;
    }

    private boolean pkParentsExists(ArrayList<MLDRTable> mldrTablesParent) {

        boolean exist = true;

        for (MLDRTable mldrTableParent : mldrTablesParent){
            exist = exist && (mldrTableParent.getMLDRPK() != null);
        }

        return exist;
    }

    private ArrayList<MLDRTable> getMLDRTablesParentsOld(MCDEntity mcdEntity) {

        ArrayList<MLDRTable> resultat = new ArrayList<MLDRTable>();

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getAssEndsIdCompChild();
        ArrayList<MCDGSEnd> mcdAssEndsSpecialize = mcdEntity.getGSEndSpecialize();

        ArrayList<MCDRelEnd> mcdRelEndsChild = MCDRelEndService.convertToMCDRelEnd(mcdAssEndsIdCompChild);
        mcdRelEndsChild.addAll( MCDRelEndService.convertToMCDRelEnd(mcdAssEndsSpecialize));
        for (MCDRelEnd mcdRelEndChild : mcdRelEndsChild){
            MCDRelEnd mcdRelEndParent = mcdRelEndChild.getMCDRelEndOpposite() ;
            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getmElement();
            resultat.add(mldrModel.getMLDRTableByEntitySource(mcdEntityParent));
        }

        return resultat;
    }

    private ArrayList<MLDRTable> getMLDRTablesParents(ArrayList<MCDRelEnd> mcdRelEndsParents) {

        ArrayList<MLDRTable> resultat = new ArrayList<MLDRTable>();

        for (MCDRelEnd mcdRelEndParent : mcdRelEndsParents){
            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getmElement();
            resultat.add(mldrModel.getMLDRTableByEntitySource(mcdEntityParent));
        }

        return resultat;
    }

    private ArrayList<MCDRelEnd> getMCDRRelEndsParentsNotNN(MCDEntity mcdEntity) {

        ArrayList<MCDRelEnd> resultat = new ArrayList<MCDRelEnd>();

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getAssEndsIdCompChild();
        ArrayList<MCDGSEnd> mcdAssEndsSpecialize = mcdEntity.getGSEndSpecialize();

        ArrayList<MCDRelEnd> mcdRelEndsChild = MCDRelEndService.convertToMCDRelEnd(mcdAssEndsIdCompChild);
        mcdRelEndsChild.addAll( MCDRelEndService.convertToMCDRelEnd(mcdAssEndsSpecialize));
        for (MCDRelEnd mcdRelEndChild : mcdRelEndsChild){
            resultat.add(mcdRelEndChild.getMCDRelEndOpposite());
        }

        return resultat;
    }

    private ArrayList<MCDRelEnd> getMCDRRelEndsParentsNN(MCDEntity mcdEntity) {

        ArrayList<MCDAssEnd> mcdAssEndsNNParent = mcdEntity.getAssEndsAssNNParent();
        ArrayList<MCDRelEnd> mcdRelEndsParent = MCDRelEndService.convertToMCDRelEnd(mcdAssEndsNNParent);

        return mcdRelEndsParent;
    }


    public void modifyTable(MLDRTable mldrTable, MCDEntity mcdEntity){

        // Nom
        MCDTransformService.names(mldrTable, buildNameTable(mcdEntity), mldrModel);

        if (mcdEntity.getNature() != mldrTable.getMcdEntitySourceNature()){

        }
    }

    protected MDRElementNames buildNameTable(MCDEntity mcdEntity){

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_TABLE_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_TABLE_NAME_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.TABLE);

            orderBuild.getTableName().setValue(mcdEntity);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mldrtable.build.name.error",
                            new String[]{mcdEntity.getName()});
                }
                throw new TransformMCDException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;

    }



}
