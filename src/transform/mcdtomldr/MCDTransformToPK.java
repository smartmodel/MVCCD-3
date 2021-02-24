package transform.mcdtomldr;

import exceptions.TransformMCDException;
import exceptions.orderbuildnaming.OrderBuildNameException;
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
import transform.MDRAdjustParameters;

import java.util.ArrayList;

public class MCDTransformToPK {

    private MCDTransform mcdTransform ;

    //private MLDRModel mldrModel ;
    //private MCDEntity mcdEntity ;
    //private MLDRTable mldrTable ;


    public MCDTransformToPK(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public void createOrModifyFromEntityInd(MCDEntity mcdEntity, MLDRTable mldrTable)  throws TransformMCDException{

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable);

        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();
        mdrColumnPKs.add(createOrModifyColumnPKProper(mcdEntity, mldrTable));

        MDRAdjustParameters.adjustParameters(mcdTransform, mldrTable, mldrPK, mdrColumnPKs);
    }


    public MLDRPK createOrModifyFromEntityConcretNoInd(MLDRModel mldrModel,
                                                       MCDEntity mcdEntity,
                                                       MLDRTable mldrTable,
                                                       ArrayList<MCDRelEnd> mcdRelEndsParents)  throws TransformMCDException{

        // Ensemble des colonnes PK
        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();

        // Création des PFK
        for (MCDRelEnd mcdRelEndParent : mcdRelEndsParents){
            // Création des PFK
            MCDTransformToFK mcdTransformToFK = new MCDTransformToFK(mcdTransform);
            MLDRFK mldrFK = mcdTransformToFK.createOrModifyFromRelEndParent(mldrModel, mcdRelEndParent, mldrTable, MDRFKNature.IDCOMP);
            mdrColumnPKs.addAll(mldrFK.getMDRColumns());
        }

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable) ;

        if ( (mcdEntity.getNature() == MCDEntityNature.DEP)  ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP) ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP ))  {
            MDRColumn mdrColumnPKProper = createOrModifyColumnPKProper(mcdEntity, mldrTable);
            mdrColumnPKs.add(mdrColumnPKProper);
        }

        MDRAdjustParameters.adjustParameters(mcdTransform, mldrTable, mldrPK, mdrColumnPKs);

        return mldrPK;
    }

    private MLDRPK createOrModifyPKEntityBase(MCDEntity mcdEntity, MLDRTable mldrTable)  throws TransformMCDException{
        MLDRPK mldrPK = mldrTable.getMLDRPK();
        if (mldrPK == null) {
            mldrPK = mldrTable.createPK(mcdEntity);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrPK);
        }
        modifyPK(mldrPK, mcdEntity);
        mldrPK.setIteration(mcdTransform.getIteration());

        return mldrPK;
    }




    public MLDRColumn createOrModifyColumnPKProper(MCDEntity mcdEntity, MLDRTable mldrTable) {

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
            // Sans attribut AID
            mldrColumnPK.setIteration(mcdTransform.getIteration());

        } else {
            mldrColumnPK = mldrColumnPKWithAID;
            // Déjà tracé lors de la transformation de la colonne
        }
        MCDTransformToColumn mcdTransformToColumn = new MCDTransformToColumn(mcdTransform);
        mcdTransformToColumn.modifyColumnPK(mcdEntity, mldrColumnPK);


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
