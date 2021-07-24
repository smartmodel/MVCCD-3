package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import m.MRelEndMultiPart;
import main.MVCCDElement;
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
import utilities.Trace;

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
            MVCCDManager.instance().addNewMVCCDElementInRepository((MVCCDElement) mldrRelationFK.getA());
            MVCCDManager.instance().addNewMVCCDElementInRepository((MVCCDElement) mldrRelationFK.getB());
        }


        // Lien entre contrainte FK et sa représentation sous forme de relation
        // Double lien assuré par setMDRRelationFK
        if (mldrFK.getMDRRelationFK() != mldrRelationFK){
            mldrFK.setMDRRelationFK(mldrRelationFK);
        }


        modifyRelationFK(mldrModel, mcdRelEndSource, mldrTable, mldrFK, fkNature, mldrRelationFK);
        mldrRelationFK.setIteration(mcdTransform.getIteration());
        mldrRelationFK.getEndParent().setIteration(mcdTransform.getIteration());
        mldrRelationFK.getEndChild().setIteration(mcdTransform.getIteration());

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

        MCDAssociation mcdAssociation = null;
        if (mcdRelEndSource.getImRelation() instanceof MCDAssociation) {
            mcdAssociation = (MCDAssociation) mcdRelEndSource.getImRelation();
        }

        MCDGeneralization mcdGeneralization= null;
        if (mcdRelEndSource.getImRelation() instanceof MCDGeneralization) {
            mcdGeneralization = (MCDGeneralization) mcdRelEndSource.getImRelation();
        }

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

        // Nature
        if (mldrFK.getNature() != null) {
            if (mldrFK.getNature() != fkNature){
                mldrFK.setNature(fkNature);
            }
        } else {
            mldrFK.setNature(fkNature);
        }

        // Lien avec la PK
        if (mldrFK.getMdrPK() != null) {
            if (mldrFK.getMdrPK() != mldrPKParent){
                throw new CodeApplException("Le changement de source/destination pour une relation n'est pas supporté");
                // Si la PK de référence peut être modifiée --> iL faut aussi modifier MDRRelationFK
            }
        } else {
            mldrFK.setMdrPK(mldrPKParent);
        }

        // deleteCascade
        if (mcdAssociation != null){
            // deleteCascade sur une association 1:1 ou 1:n
            if (mldrFK.isDeleteCascade() != mcdAssociation.isDeleteCascade() ){
                mldrFK.setDeleteCascade(mcdAssociation.isDeleteCascade());
            }
            // deleteCascade pour le rôle source d'une association n:n
            // n:n non réflexive ou réflexive mais Oriented
            // Dans ce cas mcdRelEndSource correspond au rôle qui porte deleteCascade
            MCDAssEnd mcdAssEndSource = (MCDAssEnd) mcdRelEndSource;
            if (mldrFK.isDeleteCascade() != mcdAssEndSource.isDeleteCascade()){
                mldrFK.setDeleteCascade(mcdAssEndSource.isDeleteCascade());
            }
        }

        // deleteCascade imposée sur une généralisation
        if (mcdGeneralization != null){
            if ( ! mldrFK.isDeleteCascade() ){
                mldrFK.setDeleteCascade(true);
            }
        }

        // oriented et nonOriented
        if (mcdAssociation != null) {
            // mcdAssociation.getOriented() rend :
            // null --> oriented et notOriented = false
            // Vrai --> oriented = true
            // Faux --> notOriented = true
            boolean mcdOriented = false;
            boolean mcdNotOriented = false;
            if (mcdAssociation.getOriented() != null){
                if (mcdAssociation.getOriented()){
                    mcdOriented = true;
                } else {
                    mcdNotOriented = true;
                }
            }
            if (mldrFK.isOriented() != mcdOriented){
                mldrFK.setOriented(mcdOriented);
            }
            if (mldrFK.isNotOriented() != mcdNotOriented){
                mldrFK.setNotOriented(mcdNotOriented);
                // delete cascade pour chaque extrémité
                if (mldrFK.isDeleteCascade() != mcdAssociation.isDeleteCascade()){
                    mldrFK.setDeleteCascade(mcdAssociation.isDeleteCascade());
                }
            }
        }

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
        // Permutation pour les associations n:n
        if (mcdRelEndSource instanceof MCDAssEnd) {
            if (((MCDAssEnd) mcdRelEndSource).getMcdAssociation().isDegreeNN()) {
                mcdRelEndForRelationFKEndParent = mcdRelEndSource.getMCDRelEndOpposite();
                mcdRelEndForRelationFKEndChild = mcdRelEndSource;
            }
        }

        // Création de RelationFK ou modification des extrémités
        MLDRRelFKEnd  relFKEndA = (MLDRRelFKEnd)mldrRelationFK.getA();
        MLDRRelFKEnd  relFKEndB = (MLDRRelFKEnd)mldrRelationFK.getB();
        if ((relFKEndA.getMcdElementSource() == null) && (relFKEndB.getMcdElementSource() == null)){
            relFKEndA.setMcdElementSource(mcdRelEndForRelationFKEndParent);
            relFKEndA.setRole(MDRRelFKEnd.PARENT);
            relFKEndB.setMcdElementSource(mcdRelEndForRelationFKEndChild);
            relFKEndB.setRole(MDRRelFKEnd.CHILD);
        } else if ((relFKEndA.getMcdElementSource() != null) && (relFKEndB.getMcdElementSource() != null)){
            inversionMCDMultiplicities (relFKEndA, relFKEndB, mcdRelEndForRelationFKEndParent, mcdRelEndForRelationFKEndChild);
         } else {
            throw new CodeApplException("la relationFK ne peut être créée car les 2 extrémités ne sont pas cohérentes");
        }

        // Changement des cardinalités au niveau conceptuel
        // Par exemple : 1--*  vers *--1
        // Parent et child sont inversés


        if (mldrRelationFK.getEndParent().getMcdElementSource() !=  mcdRelEndForRelationFKEndParent){
            if (mldrRelationFK.getEndParent().getMcdElementSource() != null){
                MVCCDManager.instance().removeMVCCDElementInRepository(
                        mldrRelationFK.getEndParent(), mldrRelationFK.getEndParent().getParent());
            }
            mldrRelationFK.getEndParent().setMcdElementSource(mcdRelEndForRelationFKEndParent);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrRelationFK.getEndParent());
        }
        if (mldrRelationFK.getEndChild().getMcdElementSource() !=  mcdRelEndForRelationFKEndChild){
            if (mldrRelationFK.getEndChild().getMcdElementSource() != null){
                MVCCDManager.instance().removeMVCCDElementInRepository(
                        mldrRelationFK.getEndChild(), mldrRelationFK.getEndChild().getParent());
            }
            mldrRelationFK.getEndChild().setMcdElementSource(mcdRelEndForRelationFKEndChild);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrRelationFK.getEndChild());
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
        // Remise à nul des valeurs Custom lorsqu'il y a eu permutation Parent <--> Child
        if (mldrRelationFK.getEndParent().getMultiMinCustom() != null) {
            mldrRelationFK.getEndParent().setMultiMinCustom(null);
        }
        if (mldrRelationFK.getEndParent().getMultiMaxCustom() != null) {
            mldrRelationFK.getEndParent().setMultiMaxCustom(null);
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

            // Cardinalité sur l'extrémité opposée pour les associations n:n
            if (((MCDAssEnd) mcdRelEndSource).getMcdAssociation().isDegreeNN()) {
                mcdAssEndForRelationFKMultiChild = mcdAssEndForRelationFKMultiChild.getMCDAssEndOpposite();
            }

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

    private void inversionMCDMultiplicities(MLDRRelFKEnd relFKEndA, MLDRRelFKEnd relFKEndB, MCDRelEnd mcdRelEndForRelationFKEndParent, MCDRelEnd mcdRelEndForRelationFKEndChild) {

        if (relFKEndA.getMcdElementSource() == relFKEndB.getMcdElementSource()) {
            throw new CodeApplException("la relationFK ne peut être modifées car les 2 extrémités ont la même source MCD");
        }
        if (relFKEndA.getRole() == relFKEndB.getRole()) {
            throw new CodeApplException("la relationFK ne peut être modifées car les 2 extrémités ont le même rôle");
        }

        boolean c1a = (relFKEndA.getMcdElementSource() == mcdRelEndForRelationFKEndParent) &&
                (relFKEndA.getRole().intValue() == MDRRelFKEnd.PARENT.intValue());
        boolean c1b = (relFKEndB.getMcdElementSource() == mcdRelEndForRelationFKEndChild) &&
                (relFKEndB.getRole().intValue() == MDRRelFKEnd.CHILD.intValue());
        boolean c2a = (relFKEndA.getMcdElementSource() == mcdRelEndForRelationFKEndChild) &&
                (relFKEndA.getRole().intValue() == MDRRelFKEnd.CHILD.intValue());
        boolean c2b = (relFKEndB.getMcdElementSource() == mcdRelEndForRelationFKEndParent) &&
                (relFKEndB.getRole() == MDRRelFKEnd.PARENT);
        if (!((c1a && c1b) || (c2a && c2b))){
            // le nouveau role de la source MCD est Child
            if (relFKEndA.getMcdElementSource() == mcdRelEndForRelationFKEndChild){
                relFKEndA.setMcdElementSource(mcdRelEndForRelationFKEndChild);
                relFKEndA.setRole(MDRRelFKEnd.CHILD);
                relFKEndB.setMcdElementSource(mcdRelEndForRelationFKEndParent);
                relFKEndB.setRole(MDRRelFKEnd.PARENT);
            }
            // le nouveau role de la source MCD est Parent
            if (relFKEndA.getMcdElementSource() == mcdRelEndForRelationFKEndParent){
                relFKEndA.setMcdElementSource(mcdRelEndForRelationFKEndParent);
                relFKEndA.setRole(MDRRelFKEnd.PARENT);
                relFKEndB.setMcdElementSource(mcdRelEndForRelationFKEndChild);
                relFKEndB.setRole(MDRRelFKEnd.CHILD);
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
