package mcd.transform;

import exceptions.CodeApplException;
import exceptions.OrderBuildNameException;
import main.MVCCDManager;
import mcd.*;
import mdr.MDRColumn;
import mdr.MDRFKNature;
import mdr.MDRTable;
import mdr.utilities.MDROrderBuildString;
import messages.MessagesBuilder;
import mldr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;
import utilities.Transform;

import java.util.ArrayList;

public class MCDTransformToPK {

    //private MLDRModel mldrModel ;
    //private MCDEntity mcdEntity ;
    //private MLDRTable mldrTable ;



    public void fromEntityIndependant(MCDEntity mcdEntity, MLDRTable mldrTable) {

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable);

        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();
        mdrColumnPKs.add(createOrModifyColumnPK(mcdEntity, mldrTable));

        new MLDRAdjustParametersPK().adjustParameters(mldrTable, mldrPK, mdrColumnPKs);
    }


    public MLDRPK fromEntityConcretNoInd(MLDRModel mldrModel, MCDEntity mcdEntity, MLDRTable mldrTable, ArrayList<MCDRelEnd> mcdRelEndsParents) {

        // Ensemble des colonnes PK
        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();

        // Création des PFK
        for (MCDRelEnd mcdRelEndParent : mcdRelEndsParents){
            // Création des PFK
            MLDRFK mldrFK = new MCDTransformToFK().fromRelEndParent(mldrModel, mcdRelEndParent, mldrTable, MDRFKNature.IDCOMP);
            mdrColumnPKs.addAll(mldrFK.getMDRColumns());
        }

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable);

        if ( (mcdEntity.getNature() == MCDEntityNature.DEP)  ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP) ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP ))  {
            MDRColumn mdrColumnPKProper = createOrModifyColumnPK(mcdEntity, mldrTable);
            mdrColumnPKs.add(mdrColumnPKProper);
        }

        new MLDRAdjustParametersPK().adjustParameters(mldrTable, mldrPK, mdrColumnPKs);

        return mldrPK;
    }

    private MLDRPK createOrModifyPKEntityBase(MCDEntity mcdEntity, MLDRTable mldrTable){
        MLDRPK mldrPK = mldrTable.getMLDRPK();
        if (mldrPK == null) {
            mldrPK = mldrTable.createPK(mcdEntity);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrPK);
        }
        modifyPK(mldrPK, mcdEntity);
        return mldrPK;
    }




    public MLDRColumn createOrModifyColumnPK(MCDEntity mcdEntity, MLDRTable mldrTable) {

        // Crée ou modifie Colonne PK (num ou numDep)
        MCDAttribute mcdAttributeAID = mcdEntity.getMCDAttributeAID();

        MLDRColumn mldrColumnPKWithAID = mldrTable.getMLDRColumnByMCDElementSource(mcdAttributeAID);
        MLDRColumn mldrColumnPKWithoutAID = mldrTable.getMLDRColumnByMCDElementSource(mcdEntity);
        MLDRColumn mldrColumnPK;
        if ((mcdAttributeAID == null)){
            if (mldrColumnPKWithoutAID == null){
                mldrColumnPK = mldrTable.createColumnPK(mcdEntity);
                MVCCDManager.instance().addNewMVCCDElementInRepository(mldrColumnPK);
            } else {
                mldrColumnPK = mldrColumnPKWithoutAID;
            }
        } else {
            mldrColumnPK = mldrColumnPKWithAID;
        }
        new MCDTransformToColumn().modifyColumnPK(mcdEntity, mldrColumnPK);

        return mldrColumnPK;
    }



    public void modifyPK(MLDRPK mldrPK, MCDEntity mcdEntity) {
        // Nom
        Transform.name(mldrPK, buildNamePK(mldrPK.getMDRTableAccueil(), mcdEntity));
    }


    public void modifyPK(MLDRPK mldrPK, MCDElement mcdElement, ArrayList<MCDAssociation> mcdAssociationsId) {
    }

    protected String buildNamePK(MDRTable mdrTable, MCDEntity mcdEntity){
        Preferences preferences = PreferencesManager.instance().preferences();
        String path = mcdEntity.getShortPath(preferences.getMDR_PATH_SEP_FORMAT());

        MDROrderBuildString orderBuild = new  MDROrderBuildString();
        orderBuild.setFormat(preferences.getMDR_PK_NAME_FORMAT());
        if (StringUtils.isNotEmpty(path)){
            orderBuild.setPath(path);
            orderBuild.setPathSep(preferences.getMDR_PATH_SEP_FORMAT());
        } else {
            orderBuild.setPath("");
            orderBuild.setPathSep("");
        }
        orderBuild.setTableShortName(mcdEntity.getShortName());

        String nameConstrPk;

        try {
            nameConstrPk = orderBuild.buildString();
        } catch(OrderBuildNameException e){
            String message = MessagesBuilder.getMessagesProperty("mdrfk.build.name.error",
                    new String[] {mdrTable.getName()});
            throw new CodeApplException(message, e);
        }

        return nameConstrPk;
    }

}
