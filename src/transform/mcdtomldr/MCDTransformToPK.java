package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.*;
import mdr.MDRColumn;
import mdr.MDRElementNames;
import mdr.MDRFKNature;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

import java.util.ArrayList;

public class MCDTransformToPK {

    private MCDTransform mcdTransform ;

    //private MLDRModel mldrModel ;
    //private MCDEntity mcdEntity ;
    //private MLDRTable mldrTable ;


    public MCDTransformToPK(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public void createOrModifyFromEntityInd(MCDEntity mcdEntity, MLDRTable mldrTable)  {

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable);

        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();
        mdrColumnPKs.add(createOrModifyColumnPKProper(mcdEntity, mldrTable));

        MCDTransformService.adjustParameters(mcdTransform, mldrTable, mldrPK, mdrColumnPKs);
    }


    public MLDRPK createOrModifyFromEntityConcretNoInd(MLDRModel mldrModel,
                                                       MCDEntity mcdEntity,
                                                       MLDRTable mldrTable,
                                                       ArrayList<MCDRelEnd> mcdRelEndsSources)  {

        // Ensemble des colonnes PK
        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();

        // Création des PFK
        for (MCDRelEnd mcdRelEndSource : mcdRelEndsSources){
            // Création des PFK
            MCDTransformToFK mcdTransformToFK = new MCDTransformToFK(mcdTransform);
            MLDRFK mldrFK = mcdTransformToFK.createOrModifyFromRelEndSource(mldrModel, mcdRelEndSource, mldrTable, MDRFKNature.IDCOMP);
            mdrColumnPKs.addAll(mldrFK.getMDRColumns());
        }

        MLDRPK mldrPK = createOrModifyPKEntityBase(mcdEntity, mldrTable) ;

        if ( (mcdEntity.getNature() == MCDEntityNature.DEP)  ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP) ||
                (mcdEntity.getNature() == MCDEntityNature.ENTASSDEP ))  {
            MDRColumn mdrColumnPKProper = createOrModifyColumnPKProper(mcdEntity, mldrTable);
            mdrColumnPKs.add(mdrColumnPKProper);
        }

        MCDTransformService.adjustParameters(mcdTransform, mldrTable, mldrPK, mdrColumnPKs);

        return mldrPK;
    }

    public MLDRPK createOrModifyFromAssNN(MLDRModel mldrModel,
                                          MCDAssociation mcdAssNN,
                                          MLDRTable mldrTable,
                                          ArrayList<MLDRFK> mldrPFKs)  {

        // Ensemble des colonnes PFK
        ArrayList<MDRColumn> mdrColumnPKs = new ArrayList<MDRColumn>();

        // Reprise des colonnes de PFK !
        for (MLDRFK mldrFK : mldrPFKs){
            mdrColumnPKs.addAll(mldrFK.getMDRColumns());
        }

        MLDRPK mldrPK = createOrModifyPKAssNN(mcdAssNN,mldrTable);

        MCDTransformService.adjustParameters(mcdTransform, mldrTable, mldrPK, mdrColumnPKs);

        return mldrPK;
    }

    private MLDRPK createOrModifyPKEntityBase(MCDEntity mcdEntity, MLDRTable mldrTable)  {
        MLDRPK mldrPK = mldrTable.getMLDRPK();
        if (mldrPK == null) {
            mldrPK = mldrTable.createPK(mcdEntity);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrPK);
        }
        modifyPK(mldrPK, mcdEntity);
        mldrPK.setIteration(mcdTransform.getIteration());

        return mldrPK;
    }

    private MLDRPK createOrModifyPKAssNN(MCDAssociation mcdAssNN, MLDRTable mldrTable){
        MLDRPK mldrPK = mldrTable.getMLDRPK();
        if (mldrPK == null) {
            mldrPK = mldrTable.createPK(mcdAssNN);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrPK);
        }

        modifyPK(mldrPK, mcdAssNN);
        mldrPK.setIteration(mcdTransform.getIteration());

        return mldrPK;
    }


    /**
     * Création d'une éventuelle colonne de clé primaire (autre que PFK)
     * num pour une entité indépendante
     * numDp pour une entité dépendante  (y-compris AssDep, NAireDep...)
     * @param mcdEntity
     * @param mldrTable
     * @return
     */
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



    public void modifyPK(MLDRPK mldrPK, MCDElement mcdElement) {
        // Nom
        MLDRModel mldrModel = (MLDRModel) mldrPK.getMDRTableAccueil().getMDRModelParent();
        if ( mcdElement instanceof MCDEntity) {
            transform.mcdtomldr.services.MCDTransformService.names(mldrPK,
                    buildNamePK((MLDRTable) mldrPK.getMDRTableAccueil(), (MCDEntity) mcdElement), mldrModel);}
        if ( mcdElement instanceof MCDAssociation) {
            transform.mcdtomldr.services.MCDTransformService.names(mldrPK,
                    buildNamePK((MLDRTable) mldrPK.getMDRTableAccueil(), (MCDAssociation) mcdElement), mldrModel);}
    }

    public void modifyPK(MLDRPK mldrPK, MCDElement mcdElement, ArrayList<MCDAssociation> mcdAssociationsId) {
    }

    protected MDRElementNames buildNamePK(MLDRTable mldrTable, MCDEntity mcdEntity) {
        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_PK_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_PK_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.PK);
            //orderBuild.setNamingFormat(preferences.getMLDR_PREF_NAMING_FORMAT());

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
                            new String[]{mldrTable.getNamePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
    }

    protected MDRElementNames buildNamePK(MLDRTable mldrTable, MCDAssociation mcdAssociationNN) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element : MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_PK_NN_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_PK_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.PKNN);
            //orderBuild.setNamingFormat(preferences.getMLDR_PREF_NAMING_FORMAT());

            MCDAssEnd assEndA = mcdAssociationNN.getFrom();
            MCDAssEnd assEndB = mcdAssociationNN.getTo();

            MCDEntity mcdEntityA = assEndA.getMcdEntity();
            orderBuild.getTableShortNameA().setValue(mcdEntityA);
            orderBuild.getTableSep().setValue();

            String roleA = assEndA.getShortName(); // Le format du nom est libre
            String roleB = assEndB.getShortName(); // Le format du nom est libre
            if (StringUtils.isNotEmpty(roleA) && StringUtils.isNotEmpty(roleB)) {
                orderBuild.getRoleShortNameA().setValue(roleA);
                orderBuild.getRoleSep().setValue();
                orderBuild.getRoleShortNameB().setValue(roleB);
                orderBuild.getAssShortName().setValue("");
            } else {
                orderBuild.getRoleShortNameA().setValue("");
                orderBuild.getRoleSep().setValue("");
                orderBuild.getRoleShortNameB().setValue("");
                orderBuild.getAssShortName().setValue(mcdAssociationNN.getShortName());
            }

            orderBuild.getTableSep().setValue();
            MCDEntity mcdEntityB = assEndB.getMcdEntity();
            orderBuild.getTableShortNameB().setValue(mcdEntityB);

            // Pour le nommage indicé en cas de limite de taille
            String nameNN = orderBuild.getTableShortNameA().getValue() + orderBuild.getTableSep().getValue() +
                    orderBuild.getTableShortNameB().getValue();

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
                    message = MessagesBuilder.getMessagesProperty("mdrpk.build.name.error",
                            new String[]{mcdAssociationNN.getNameTreePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
    }


    }
