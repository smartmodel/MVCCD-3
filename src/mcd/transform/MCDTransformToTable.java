package mcd.transform;

import exceptions.CodeApplException;
import exceptions.OrderBuildNameException;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDRelEndService;
import mdr.utilities.MDROrderBuildString;
import messages.MessagesBuilder;
import mldr.MLDRModel;
import mldr.MLDRPK;
import mldr.MLDRTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectService;
import utilities.Transform;

import java.util.ArrayList;

public class MCDTransformToTable {

    private IMCDModel  imcdModel ;
    private MLDRModel mldrModel ;



    public void fromEntities(IMCDModel  imcdModel, MLDRModel mldrModel){
        this.imcdModel = imcdModel;
        this.mldrModel = mldrModel;
        fromEntitiesConcrets(ProjectService.getMCDEntitiesConcrets((MCDElement) imcdModel));
        transformPKEntitiesIndependants(ProjectService.getMCDEntitiesIndependants((MCDElement) imcdModel));
        transformPKEntitiesConcretsNoInd(ProjectService.getMCDEntitiesConcretsNoInd((MCDElement) imcdModel));
    }



    private void fromEntitiesConcrets(ArrayList<MCDEntity> mcdEntitiesConcrets) {
        for (MCDEntity mcdEntityConcret : mcdEntitiesConcrets){
            fromEntityConcret (mcdEntityConcret);
        }
    }



    private void fromEntityConcret(MCDEntity mcdEntity) {
        // Table
        MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntity);
        if (mldrTable == null){
            mldrTable = mldrModel.createTable(mcdEntity);
            mldrTable.setMcdEntitySourceNature(mcdEntity.getNature());
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrTable);
        }
        modifyTable(mldrTable, mcdEntity);

        // Attributs
        new MCDTransformToColumn().fromAttributes(mcdEntity, mldrTable);
    }


    private void transformPKEntitiesIndependants(ArrayList<MCDEntity> mcdEntitiesIndependants) {
        for (MCDEntity mcdEntityIndependant : mcdEntitiesIndependants){
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdEntityIndependant);
            new MCDTransformToPK().fromEntityIndependant(mcdEntityIndependant, mldrTable);
        }
    }

    private void transformPKEntitiesConcretsNoInd(ArrayList<MCDEntity> mcdEntitiesNoIndNoEntAss) {

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
                    MLDRPK mldrPK = new MCDTransformToPK().fromEntityConcretNoInd(mldrModel, mcdEntity, mldrTable,mcdRelEndsParents);
                    if (mldrPK != null){
                        mcdEntitiesToTransform.remove(mcdEntity);
                    }
                 }
            }
            if (controle == mcdEntitiesToTransform.size()){
                throw new CodeApplException("Erreur interne dans la boucle de trnsformation des entités non ondépendantes");
            }
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
            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getMcdElement();
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
            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getMcdElement();
            resultat.add(mldrModel.getMLDRTableByEntitySource(mcdEntityParent));
        }

        return resultat;
    }

    private ArrayList<MLDRTable> getMLDRTablesParents(ArrayList<MCDRelEnd> mcdRelEndsParents) {

        ArrayList<MLDRTable> resultat = new ArrayList<MLDRTable>();

        for (MCDRelEnd mcdRelEndParent : mcdRelEndsParents){
            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getMcdElement();
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
        Transform.name(mldrTable, buildNameTable(mcdEntity));

        if (mcdEntity.getNature() != mldrTable.getMcdEntitySourceNature()){

        }
    }

    protected String buildNameTable(MCDEntity mcdEntity){

        Preferences preferences = PreferencesManager.instance().preferences();
        String path = mcdEntity.getShortPath(preferences.getMDR_PATH_SEP_FORMAT());

        MDROrderBuildString orderBuild = new  MDROrderBuildString();
        orderBuild.setFormat(preferences.getMDR_TABLE_NAME_FORMAT());
        if (StringUtils.isNotEmpty(path)){
            orderBuild.setPath(path);
            orderBuild.setPathSep(preferences.getMDR_PATH_SEP_FORMAT());
        } else {
            orderBuild.setPath("");
            orderBuild.setPathSep("");
        }
        orderBuild.setTableName(mcdEntity.getMldrTableName());


        String nameFinal ;

        try {
            nameFinal = orderBuild.buildString();
        } catch(OrderBuildNameException e){
            String message = MessagesBuilder.getMessagesProperty("mldrtable.build.name.error",
                    new String[] {mcdEntity.getName()});
            throw new CodeApplException(message,e);
        }
        return nameFinal;

    }



}
