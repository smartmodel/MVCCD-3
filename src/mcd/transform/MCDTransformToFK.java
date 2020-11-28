package mcd.transform;

import exceptions.CodeApplException;
import exceptions.OrderBuildNameException;
import main.MVCCDManager;
import mcd.MCDEntity;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import mdr.MDRColumn;
import mdr.MDRFK;
import mdr.MDRFKNature;
import mdr.MDRTable;
import mdr.utilities.MDROrderBuildString;
import messages.MessagesBuilder;
import mldr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Transform;

import java.util.ArrayList;

public class MCDTransformToFK {

    public MLDRFK  fromRelEndParent(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MDRFKNature fkNature) {

        MLDRFK mldrFK =  mldrTable.getMLDRFKByMCDElementSource(mcdRelEndParent.getMcdRelation());

        if (mldrFK == null) {
            mldrFK = mldrTable.createFK(mcdRelEndParent.getMcdRelation());
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrFK);
        }
        modifyFK(mldrModel, mcdRelEndParent, mldrTable, mldrFK, fkNature);
        return mldrFK;
    }


    private void modifyFK(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MLDRFK mldrFK, MDRFKNature fkNature) {
        MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getMcdElement();
        MLDRTable mldrTableParent = mldrModel.getMLDRTableByEntitySource(mcdEntityParent);
        MLDRPK mldrPKParent = mldrTableParent.getMLDRPK();

        // Nom
        String nameFK = buildNameFK(mldrTable, mldrFK, mcdRelEndParent, mldrTableParent);
        Transform.name(mldrFK, nameFK);

        // Nature
        mldrFK.setNature(fkNature);


        ArrayList<MDRColumn> mdrColumnsFK = new ArrayList<MDRColumn>();
        //Création des colonnes FK et des paramètres
        // Parcours des colonne de la PK
        for (MLDRParameter mldrParameter : mldrPKParent.getMLDRParameters()){
            MLDRColumn mldrColumnPK = (MLDRColumn) mldrParameter.getTarget() ;
            MCDEntity mcdEntity = (MCDEntity) mcdRelEndParent.getMCDRelEndOpposite().getMcdElement();
            MCDRelation mcdRelation = mcdRelEndParent.getMcdRelation();

            // Transformation de la colonne PK en colonne FK
            MLDRColumn mldrColumnFK = new MCDTransformToColumn().fromRelEndParent(mldrTable, mcdRelEndParent, mldrTableParent, mldrColumnPK, fkNature);
            mdrColumnsFK.add(mldrColumnFK);
        }

        // Transformation des paramètres PK en paramètres FK
        new MLDRAdjustParametersFK().adjustParameters(mldrTable, mldrFK, mdrColumnsFK);
    }

    private void adjustParameters(MLDRFK mldrFK, MLDRColumn mldrColumnPK) {

    }

    protected String buildNameFK(MDRTable mdrTable,
                                 MDRFK mdrFK,
                                 MCDRelEnd mcdRelEnd,
                                 MDRTable mdrTableParent){
        Preferences preferences = PreferencesManager.instance().preferences();

        MDROrderBuildString orderConstrFk = new  MDROrderBuildString();
        orderConstrFk.setFormat(preferences.getMDR_FK_NAME_FORMAT());
        orderConstrFk.setChildTableShortName(mdrFK.getShortName());
        orderConstrFk.setParentTableShortName(mdrTableParent.getShortName());
        String roleParent = mcdRelEnd.getNameNoFreeOrNameRelation();
        orderConstrFk.setParentRoleName(roleParent);
        orderConstrFk.setIndice(mdrFK.getIndice().toString());
        if (StringUtils.isEmpty(roleParent) ){
            orderConstrFk.setParentRoleSep("");
        } else{
            orderConstrFk.setParentRoleSep(preferences.getMDR_PARENT_ROLE_NAME_SEP_FORMAT());
        }

        String nameConstrFk;

        try {
            nameConstrFk = orderConstrFk.buildString();
        } catch(OrderBuildNameException e){
            String message = MessagesBuilder.getMessagesProperty("mdrfk.build.name.error",
                    new String[] {mdrTable.getName(), mdrTableParent.getName(), mcdRelEnd.getNameNoFreeOrNameRelation()});
            throw new CodeApplException(message,e);
        }

        return nameConstrFk;
   }

}
