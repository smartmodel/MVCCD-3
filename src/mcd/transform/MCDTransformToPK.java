package mcd.transform;

import exceptions.orderbuildnaming.OrderBuildNameException;
import exceptions.TransformMCDException;
import main.MVCCDManager;
import mcd.*;
import mdr.*;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.TransformService;

import java.util.ArrayList;

public class MCDTransformToPK {

    //private MLDRModel mldrModel ;
    //private MCDEntity mcdEntity ;
    //private MLDRTable mldrTable ;



    public void fromEntityIndependant(MCDEntity mcdEntity, MLDRTable mldrTable)  throws TransformMCDException{

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable);

        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();
        mdrColumnPKs.add(createOrModifyColumnPK(mcdEntity, mldrTable));

        new MLDRAdjustParametersPK().adjustParameters(mldrTable, mldrPK, mdrColumnPKs);
    }


    public MLDRPK fromEntityConcretNoInd(MLDRModel mldrModel,
                                         MCDEntity mcdEntity,
                                         MLDRTable mldrTable,
                                         ArrayList<MCDRelEnd> mcdRelEndsParents)  throws TransformMCDException{

        // Ensemble des colonnes PK
        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();

        // Création des PFK
        for (MCDRelEnd mcdRelEndParent : mcdRelEndsParents){
            // Création des PFK
            MLDRFK mldrFK = new MCDTransformToFK().fromRelEndParent(mldrModel, mcdRelEndParent, mldrTable, MDRFKNature.IDCOMP);
            mdrColumnPKs.addAll(mldrFK.getMDRColumns());
        }

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable) ;

        if ( (mcdEntity.getNature() == MCDEntityNature.DEP)  ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP) ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP ))  {
            MDRColumn mdrColumnPKProper = createOrModifyColumnPK(mcdEntity, mldrTable);
            mdrColumnPKs.add(mdrColumnPKProper);
        }

        new MLDRAdjustParametersPK().adjustParameters(mldrTable, mldrPK, mdrColumnPKs);

        return mldrPK;
    }

    private MLDRPK createOrModifyPKEntityBase(MCDEntity mcdEntity, MLDRTable mldrTable)  throws TransformMCDException{
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



    public void modifyPK(MLDRPK mldrPK, MCDEntity mcdEntity)  throws TransformMCDException {
        // Nom
        MLDRModel mldrModel = (MLDRModel) mldrPK.getMDRTableAccueil().getMDRModelParent();
        MCDTransformService.names(mldrPK, buildNamePK(mldrPK.getMDRTableAccueil(), mcdEntity), mldrModel);
    }


    public void modifyPK(MLDRPK mldrPK, MCDElement mcdElement, ArrayList<MCDAssociation> mcdAssociationsId) {
    }

    protected MDRElementNames buildNamePK(MDRTable mdrTable, MCDEntity mcdEntity) throws TransformMCDException{
        Preferences preferences = PreferencesManager.instance().preferences();
        String path = mcdEntity.getShortPath(preferences.getMDR_PATH_SEP_FORMAT());

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_PK_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_PK_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.PK);

            orderBuild.getTableShortName().setValue(mcdEntity);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrpk.build.name.error",
                            new String[]{mdrTable.getName()});
                }
                throw new TransformMCDException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
    }

}
