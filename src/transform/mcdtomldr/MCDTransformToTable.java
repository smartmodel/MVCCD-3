package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDSourceMLDRTable;
import mcd.services.IMCDModelService;
import mcd.services.MCDRelEndService;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRFK;
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

    public void createOrModifyFromAllEntities()  {
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

    private void createOrModifyPKEntitiesInd(ArrayList<MCDEntity> mcdEntitiesIndependants)  {
        for (MCDEntity mcdEntityIndependant : mcdEntitiesIndependants){
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntityIndependant);
            new MCDTransformToPK(mcdTransform).createOrModifyFromEntityInd(mcdEntityIndependant, mldrTable);
        }
    }

    private void createOrModifyPKEntitiesNoInd(ArrayList<MCDEntity> mcdEntitiesNoIndNoEntAss)   {

        ArrayList<MCDEntity> mcdEntitiesToTransform = (ArrayList<MCDEntity>) mcdEntitiesNoIndNoEntAss.clone();
        int controle = mcdEntitiesToTransform.size();


        while (mcdEntitiesToTransform.size() > 0){
            ArrayList<MCDEntity> mcdEntitiesToTransformInner = (ArrayList<MCDEntity>) mcdEntitiesToTransform.clone();
            for (MCDEntity mcdEntity : mcdEntitiesToTransformInner){
                ArrayList<MCDRelEnd> mcdRelEndsSources = getMCDRRelEndsParentsNotNN(mcdEntity);
                mcdRelEndsSources.addAll(getMCDRRelEndsLinkNN(mcdEntity));
                ArrayList<MLDRTable> mldrTablesParents = getMLDRTablesParents(mcdRelEndsSources);
                if (pkParentsExists(mldrTablesParents)){
                    MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntity);
                    MLDRPK mldrPK = new MCDTransformToPK(mcdTransform).createOrModifyFromEntityConcretNoInd(mldrModel, mcdEntity, mldrTable,mcdRelEndsSources);
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
        for (MCDAssociation mcdAssNN : IMCDModelService.getMCDAssociationsNNWithoutEntity(imcdModel)){
            createOrModifyFromAssociationNN(mcdAssNN);
        }
    }

    public void createOrModifyFromAssociationNN(MCDAssociation mcdAssNN) {
        // Les éventuels changement de présence où pas d'une entité associative
        // sont pris en charge par :
        // MCLink en cas de suppression de lien
        // MVCDElementFactory en cas d'ajout d'un lien

        // Table
        MLDRTable mldrTable = mldrModel.getMLDRTableByAssNNSource(mcdAssNN);
        if (mldrTable == null){
            mldrTable = mldrModel.createTable(mcdAssNN);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrTable);
         }
        modifyTable(mldrTable, mcdAssNN);
        mldrTable.setIteration(mcdTransform.getIteration());

        // 2 FKs
        MCDTransformToFK mcdTransformToFK = new MCDTransformToFK(mcdTransform);
        ArrayList<MLDRFK> mldrFKs = mcdTransformToFK.createOrModifyFromAssNN(mcdAssNN, mldrTable);

        //PK
        MLDRPK mldrPK = new MCDTransformToPK(mcdTransform).createOrModifyFromAssNN(mldrModel, mcdAssNN, mldrTable, mldrFKs);
    }

    private boolean pkParentsExistsOld(MCDEntity mcdEntity) {

        boolean exist = true;

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getMCDAssEndsIdCompChild();
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

    private boolean pkParentsExists(ArrayList<MLDRTable> mldrTablesParent) {

        boolean exist = true;

        for (MLDRTable mldrTableParent : mldrTablesParent){
            exist = exist && (mldrTableParent.getMLDRPK() != null);
        }

        return exist;
    }

    private ArrayList<MLDRTable> getMLDRTablesParentsOld(MCDEntity mcdEntity) {

        ArrayList<MLDRTable> resultat = new ArrayList<MLDRTable>();

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getMCDAssEndsIdCompChild();
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

        ArrayList<MCDAssEnd> mcdAssEndsIdCompChild = mcdEntity.getMCDAssEndsIdCompChild();
        ArrayList<MCDGSEnd> mcdAssEndsSpecialize = mcdEntity.getGSEndSpecialize();

        ArrayList<MCDRelEnd> mcdRelEndsChild = MCDRelEndService.convertToMCDRelEnd(mcdAssEndsIdCompChild);
        mcdRelEndsChild.addAll( MCDRelEndService.convertToMCDRelEnd(mcdAssEndsSpecialize));
        for (MCDRelEnd mcdRelEndChild : mcdRelEndsChild){
            resultat.add(mcdRelEndChild.getMCDRelEndOpposite());
        }

        return resultat;
    }

    private ArrayList<MCDRelEnd> getMCDRRelEndsLinkNN(MCDEntity mcdEntity) {

        ArrayList<MCDAssEnd> mcdAssEndsNN = mcdEntity.getMCDAssEndsLinkNN();
        ArrayList<MCDRelEnd> mcdRelEnds = MCDRelEndService.convertToMCDRelEnd(mcdAssEndsNN);

        return mcdRelEnds;
    }


    public void modifyTable(MLDRTable mldrTable, IMCDSourceMLDRTable imcdSourceMLDRTable){

        // Nom
        if ( imcdSourceMLDRTable instanceof MCDEntity) {
            MCDTransformService.names(mldrTable, buildNameTable((MCDEntity) imcdSourceMLDRTable), mldrModel);
        }
        if ( imcdSourceMLDRTable instanceof MCDAssociation) {
            MCDTransformService.names(mldrTable, buildNameTable(mldrTable, (MCDAssociation) imcdSourceMLDRTable), mldrModel);
        }

        // A voir !
        // Une table peut aussi provenir d'une association n:n sans entité associative
        //if (mcdEntity.getNature() != mldrTable.getMcdEntitySourceNature()){

        //}
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
                            new String[]{mcdEntity.getNamePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;

    }


    protected MDRElementNames buildNameTable(MLDRTable mldrTable, MCDAssociation mcdAssociationNN){

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_TABLE_NN_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_TABLE_NAME_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.TABLENN);

            MCDAssEnd assEndA = mcdAssociationNN.getFrom();
            MCDAssEnd assEndB = mcdAssociationNN.getTo();

            MCDEntity mcdEntityA = assEndA.getMcdEntity();
            orderBuild.getTableShortNameA().setValue(mcdEntityA );
            orderBuild.getTableSep().setValue();

            String roleA = assEndA.getShortName(); // Le format du nom est libre
            String roleB = assEndB.getShortName(); // Le format du nom est libre
            if (StringUtils.isNotEmpty(roleA)  &&  StringUtils.isNotEmpty(roleB)){
                orderBuild.getRoleShortNameA().setValue(roleA);
                orderBuild.getRoleSep().setValue();
                orderBuild.getRoleShortNameB().setValue(roleB);
                orderBuild.getAssShortName().setValue("");
            }else {
                orderBuild.getRoleShortNameA().setValue("");
                orderBuild.getRoleSep().setValue("");
                orderBuild.getRoleShortNameB().setValue("");
                orderBuild.getAssShortName().setValue(mcdAssociationNN.getShortName());
            }

            orderBuild.getTableSep().setValue();
            MCDEntity mcdEntityB= assEndB.getMcdEntity();
            orderBuild.getTableShortNameB().setValue(mcdEntityB);

            // Pour le nommage indicé en cas de limite de taille
            String nameNN = orderBuild.getTableShortNameA().getValue() +  orderBuild.getTableSep().getValue() +
                            orderBuild.getTableShortNameB().getValue() ;

            ArrayList<MVCCDElement> brothers = MVCCDElementConvert.to(mldrTable.getBrothers());
            orderBuild.getIndTableNN().setValue(nameNN, brothers);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mldrtable.build.name.error",
                            new String[]{mcdAssociationNN.getNameTreePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;

    }



}
