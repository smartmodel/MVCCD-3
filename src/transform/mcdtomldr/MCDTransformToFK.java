package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import m.MRelEndMultiPart;
import main.MVCCDManager;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
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

public class MCDTransformToFK {

    private MCDTransform mcdTransform ;

    public MCDTransformToFK(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public void createOrModifyFromAllAssNotIdCompAndNotNN(IMCDModel imcdModel, MLDRModel mldrModel) {
        ArrayList<MCDAssociation> mcdAssociations = IMCDModelService.getMCDAssociationNotIdCompAndNotNN(imcdModel);
        for (MCDAssociation mcdAssociation : mcdAssociations){
            MCDAssEnd mcdAssEndParent = mcdAssociation.getMCDAssEndParent();
            MCDEntity mcdEntityChild = mcdAssociation.getMCDAssEndChild().getMcdEntity();
            MLDRTable mldrTableChild = mldrModel.getMLDRTableByEntitySource(mcdEntityChild);
            MDRFKNature fkNature = MCDTransformService.mapMCDAssociationNature(mcdAssociation);
            createOrModifyFromRelEndSource(mldrModel, mcdAssEndParent , mldrTableChild, fkNature);
        }
    }

    /**
     *
     * @param mldrModel
     * @param mcdRelEndSource
     * @param mldrTable  ! Table d'accueil de la FK
     * @param fkNature
     * @return
     */
    public MLDRFK createOrModifyFromRelEndSource(MLDRModel mldrModel, MCDRelEnd mcdRelEndSource, MLDRTable mldrTable, MDRFKNature fkNature) {

        // Contrainte FK
        MLDRFK mldrFK =  mldrTable.getMLDRFKByMCDElementSource((MCDRelEnd) mcdRelEndSource);
        if (mldrFK == null) {
            mldrFK = mldrTable.createFK(mcdRelEndSource);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrFK);
        }

        // modification et Ajout des colonnes
        modifyFK(mldrModel, mcdRelEndSource, mldrTable, mldrFK, fkNature);
        mldrFK.setIteration(mcdTransform.getIteration());


        // Relation FK
        MCDRelation mcdRelation = (MCDRelation) mcdRelEndSource.getImRelation();
        MLDRTable mldrTableParent = mldrModel.getMLDRTableByEntitySource((MCDEntity)mcdRelEndSource.getmElement());
        MLDRRelationFK mldrRelationFK = mldrModel.getMLDRRelationFKByMCDRelationSourceAndSameTables(mcdRelation,mldrTable, mldrTableParent);
        if (mldrRelationFK == null){
            mldrRelationFK = mldrModel.createRelationFK(mcdRelation,  mldrTableParent, mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrRelationFK);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrRelationFK.getEndParent());
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrRelationFK.getEndChild());
        }


        // Lien entre contrainte FK et sa représentation sous forme de relation
        // Double lien assuré par setMDRRelationFK
        if (mldrFK.getMDRRelationFK() != mldrRelationFK){
            mldrFK.setMDRRelationFK(mldrRelationFK);
        }


        modifyRelationFK(mldrModel, mcdRelEndSource, mldrTable, mldrFK, fkNature, mldrRelationFK);
        mldrRelationFK.setIteration(mcdTransform.getIteration());

        return mldrFK;
    }

    public ArrayList<MLDRFK> createOrModifyFromAssNN(MCDAssociation mcdAssNN, MLDRTable mldrTable) {
        ArrayList<MLDRFK> resultat = new ArrayList<MLDRFK>();
        MLDRFK mldrFKA = createOrModifyFromRelEndSource((MLDRModel) mldrTable.getMDRModelParent(), mcdAssNN.getFrom(), mldrTable, MDRFKNature.IDCOMP) ;
        resultat.add(mldrFKA);
        MLDRFK mldrFKB = createOrModifyFromRelEndSource((MLDRModel) mldrTable.getMDRModelParent(), mcdAssNN.getTo(), mldrTable, MDRFKNature.IDCOMP) ;
        resultat.add(mldrFKB);
        return resultat;
    }


    private void modifyFK(MLDRModel mldrModel, MCDRelEnd mcdRelEndSource, MLDRTable mldrTable, MLDRFK mldrFK, MDRFKNature fkNature) {
        MCDEntity mcdEntitySource = (MCDEntity) mcdRelEndSource.getmElement();
        MLDRTable mldrTableParent = mldrModel.getMLDRTableByEntitySource(mcdEntitySource);
        MLDRPK mldrPKParent = mldrTableParent.getMLDRPK();

        // Nom
        String tableShortNameChild = "";
        MCDElement mcdElementSource = mldrTable.getMcdElementSource();
        if (mcdElementSource instanceof MCDEntity){
            tableShortNameChild = mcdElementSource.getShortName();
        }
        if (mcdElementSource instanceof MCDAssociation){
            tableShortNameChild = mcdElementSource.getShortName();
        }

        MDRElementNames namesFK = buildNameFK(mldrTable, tableShortNameChild, mldrFK, mcdRelEndSource, mldrTableParent);
        MCDTransformService.names(mldrFK, namesFK, mldrModel);

        //TODO-PAS Faire les test de changements de valeurs
        // Nature
        mldrFK.setNature(fkNature);
        // Lien avec la PK
        mldrFK.setMdrPK(mldrPKParent);

        ArrayList<MDRColumn> mdrColumnsFK = new ArrayList<MDRColumn>();
        //Création des colonnes FK et des paramètres
        // Parcours des colonne de la PK
        for (MLDRParameter mldrParameter : mldrPKParent.getMLDRParameters()){
            MLDRColumn mldrColumnPK = (MLDRColumn) mldrParameter.getTarget() ;
            MCDEntity mcdEntity = (MCDEntity) mcdRelEndSource.getMCDRelEndOpposite().getmElement();
            MCDRelation mcdRelation = (MCDRelation) mcdRelEndSource.getImRelation();

            // Transformation de la colonne PK en colonne FK
            MCDTransformToColumn mcdTransformToColumn = new MCDTransformToColumn(mcdTransform);
            MLDRColumn mldrColumnFK = mcdTransformToColumn.createOrModifyFromRelEndParent(mldrTable, mcdRelEndSource, mldrTableParent, mldrColumnPK, fkNature, mldrFK.getIndice());
            mdrColumnsFK.add(mldrColumnFK);
        }

        // Transformation des paramètres PK en paramètres FK
        MDRAdjustParameters.adjustParameters(mcdTransform, mldrTable, mldrFK, mdrColumnsFK);
    }

    private void modifyRelationFK(MLDRModel mldrModel,
                                  MCDRelEnd mcdRelEndSource,
                                  MLDRTable mldrTable,
                                  MLDRFK mldrFK,
                                  MDRFKNature fkNature,
                                  MLDRRelationFK mldrRelationFK) {

        // Le nom est repris de la contrainte de FK associée

        // Préparation des extrémités adéquates.
        // Source correspond à l'entité parent pour les associations 1:1, 1:n et pour les gén/spec
        MCDRelEnd mcdRelEndForRelationFKEndParent = mcdRelEndSource;
        MCDRelEnd mcdRelEndForRelationFKEndChild = mcdRelEndSource.getMCDRelEndOpposite();
        // Permutation pour les association n:n
        if (mcdRelEndSource instanceof MCDAssEnd) {
            if (((MCDAssEnd) mcdRelEndSource).getMcdAssociation().isDegreeNN()) {
                mcdRelEndForRelationFKEndParent = mcdRelEndSource.getMCDRelEndOpposite();
                mcdRelEndForRelationFKEndChild = mcdRelEndSource;
            }
        }

        // Multplicité
        // Parent FK
        // Minimum
        MRelEndMultiPart multiMinStdParent;
        if (fkNature == MDRFKNature.IDCOMP) {
            // Obligatoirement 1
            // Inclu les fk issues d'associations n:n
            // Imnclu les fk issues de gén/spéc
            multiMinStdParent = MRelEndMultiPart.MULTI_ONE;
        } else {
            // Uniquement les associations 1;1 et 1 (donc MCDAssEnd)
            multiMinStdParent = ((MCDAssEnd) mcdRelEndForRelationFKEndParent).getMultiMinStd();
        }
        if (mldrRelationFK.getEndParent().getMultiMinStd() != multiMinStdParent) {
            mldrRelationFK.getEndParent().setMultiMinStd(multiMinStdParent);
        }
        // Maximum 
        // Obligatoirement 1
        if (mldrRelationFK.getEndParent().getMultiMaxStd() != MRelEndMultiPart.MULTI_ONE) {
            mldrRelationFK.getEndParent().setMultiMaxStd(MRelEndMultiPart.MULTI_ONE);
        }

        // Child FK
        // Association
        if (mcdRelEndSource instanceof MCDAssEnd) {
            /*
            MCDAssEnd mcdAssEndParent = (MCDAssEnd) mcdRelEndSource;
            MCDAssEnd mcdAssEndChild = mcdAssEndParent.getMCDAssEndOpposite();
            MCDAssEnd mcdAssEndForRelationFKMultiChild = mcdAssEndChild;
            if (mcdAssEndParent.getMcdAssociation().isDegreeNN()) {
                mcdAssEndForRelationFKMultiChild = mcdAssEndParent;
            }

             */

            //MCDAssEnd mcdAssEndForRelationFKMultiParent = (MCDAssEnd) mcdRelEndForRelationFKEndParent;
            MCDAssEnd mcdAssEndForRelationFKMultiChild = (MCDAssEnd) mcdRelEndForRelationFKEndChild;


            // Minimum
            if (mldrRelationFK.getEndChild().getMultiMinStd() != mcdAssEndForRelationFKMultiChild.getMultiMinStd()) {
                mldrRelationFK.getEndChild().setMultiMinStd(mcdAssEndForRelationFKMultiChild.getMultiMinStd());
            }
            if (mldrRelationFK.getEndChild().getMultiMinCustom() != mcdAssEndForRelationFKMultiChild.getMultiMinCustom()) {
                mldrRelationFK.getEndChild().setMultiMinCustom(mcdAssEndForRelationFKMultiChild.getMultiMinCustom());
            }
            // Maximum
            if (mldrRelationFK.getEndChild().getMultiMaxStd() != mcdAssEndForRelationFKMultiChild.getMultiMaxStd()) {
                mldrRelationFK.getEndChild().setMultiMaxStd(mcdAssEndForRelationFKMultiChild.getMultiMaxStd());
            }
            if (mldrRelationFK.getEndChild().getMultiMaxCustom() != mcdAssEndForRelationFKMultiChild.getMultiMaxCustom()) {
                mldrRelationFK.getEndChild().setMultiMaxCustom(mcdAssEndForRelationFKMultiChild.getMultiMaxCustom());
            }
        }

        // Généralisation-spécialisation
        if (mcdRelEndSource instanceof MCDGSEnd) {
            // Minimum
            if (mldrRelationFK.getEndChild().getMultiMinStd() != MRelEndMultiPart.MULTI_ZERO) {
                mldrRelationFK.getEndChild().setMultiMinStd(MRelEndMultiPart.MULTI_ZERO);
            }

            // Maximum
            if (mldrRelationFK.getEndChild().getMultiMaxStd() != MRelEndMultiPart.MULTI_ONE) {
                mldrRelationFK.getEndChild().setMultiMaxStd(MRelEndMultiPart.MULTI_ONE);
            }
        }
    }


    protected MDRElementNames buildNameFK(MDRTable mdrTableChild,
                                          String tableShortNameChild,
                                          MDRFK mdrFK,
                                          MCDRelEnd mcdRelEndParent,
                                          MDRTable mdrTableParent  ) {
        Preferences preferences = PreferencesManager.instance().preferences();
        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_FK_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_FK_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.FK);

            orderBuild.getIndConstFK().setValue(mdrFK.getIndice().toString());
            orderBuild.getFkIndSep().setValue();

            //orderBuild.getTableShortNameChild().setValue((MCDEntity) mcdRelEndParent.getMCDRelEndOpposite().getmElement());
            orderBuild.getTableShortNameChild().setValue(tableShortNameChild);
            orderBuild.getTableShortNameParent().setValue((MCDEntity) mcdRelEndParent.getmElement());
            orderBuild.getTableSep().setValue();
            orderBuild.getRoleShortNameParent().setValue(mcdRelEndParent);


            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrfk.build.name.error",
                            new String[]{mdrTableChild.getName(), mdrTableParent.getName(), mcdRelEndParent.getNameNoFreeOrNameRelation()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
   }

}
