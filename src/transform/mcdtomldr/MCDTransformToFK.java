package transform.mcdtomldr;

import exceptions.TransformMCDException;
import exceptions.orderbuildnaming.OrderBuildNameException;
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

    public void createOrModifyFromAllAssNoIdOrIdNatural(IMCDModel imcdModel, MLDRModel mldrModel) {
        ArrayList<MCDAssociation> mcdAssociations = IMCDModelService.getMCDAssociationNoIdOrIdNatural(imcdModel);
        for (MCDAssociation mcdAssociation : mcdAssociations){
            MCDAssEnd mcdAssEndParent = mcdAssociation.getMCDAssEndParent();
            MCDEntity mcdEntityChild = mcdAssociation.getMCDAssEndChild().getMcdEntity();
            MLDRTable mldrTableChild = mldrModel.getMLDRTableByEntitySource(mcdEntityChild);
            MDRFKNature fkNature = MCDTransformService.mapMCDAssociationNature(mcdAssociation);
            createOrModifyFromRelEndParent(mldrModel, mcdAssEndParent , mldrTableChild, fkNature);
        }
    }

    /**
     *
     * @param mldrModel
     * @param mcdRelEndParent
     * @param mldrTable  ! Table d'accueil de la FK
     * @param fkNature
     * @return
     */
    public MLDRFK createOrModifyFromRelEndParent(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MDRFKNature fkNature) {

        // Contrainte FK
        MLDRFK mldrFK =  mldrTable.getMLDRFKByMCDElementSource((MCDRelEnd) mcdRelEndParent);
        if (mldrFK == null) {
            mldrFK = mldrTable.createFK(mcdRelEndParent);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrFK);
        }

        // modification et Ajout des colonnes
        modifyFK(mldrModel, mcdRelEndParent, mldrTable, mldrFK, fkNature);
        mldrFK.setIteration(mcdTransform.getIteration());


        // Relation FK
        MCDRelation mcdRelation = (MCDRelation) mcdRelEndParent.getImRelation();
        MLDRTable mldrTableParent = mldrModel.getMLDRTableByEntitySource((MCDEntity)mcdRelEndParent.getmElement());
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


        modifyRelationFK(mldrModel, mcdRelEndParent, mldrTable, mldrFK, fkNature);
        mldrRelationFK.setIteration(mcdTransform.getIteration());

        return mldrFK;
    }

    public ArrayList<MLDRFK> createOrModifyFromAssNN(MCDAssociation mcdAssNN, MLDRTable mldrTable) {
        ArrayList<MLDRFK> resultat = new ArrayList<MLDRFK>();
        MLDRFK mldrFKA = createOrModifyFromRelEndParent((MLDRModel) mldrTable.getMDRModelParent(), mcdAssNN.getFrom(), mldrTable, MDRFKNature.IDCOMP) ;
        resultat.add(mldrFKA);
        MLDRFK mldrFKB = createOrModifyFromRelEndParent((MLDRModel) mldrTable.getMDRModelParent(), mcdAssNN.getTo(), mldrTable, MDRFKNature.IDCOMP) ;
        resultat.add(mldrFKB);
        return resultat;
    }


    private void modifyFK(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MLDRFK mldrFK, MDRFKNature fkNature) {
        MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getmElement();
        MLDRTable mldrTableParent = mldrModel.getMLDRTableByEntitySource(mcdEntityParent);
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

        MDRElementNames nameFK = buildNameFK(mldrTable, tableShortNameChild, mldrFK, mcdRelEndParent, mldrTableParent);
        MCDTransformService.names(mldrFK, nameFK, mldrModel);

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
            MCDEntity mcdEntity = (MCDEntity) mcdRelEndParent.getMCDRelEndOpposite().getmElement();
            MCDRelation mcdRelation = (MCDRelation) mcdRelEndParent.getImRelation();

            // Transformation de la colonne PK en colonne FK
            MCDTransformToColumn mcdTransformToColumn = new MCDTransformToColumn(mcdTransform);
            MLDRColumn mldrColumnFK = mcdTransformToColumn.createOrModifyFromRelEndParent(mldrTable, mcdRelEndParent, mldrTableParent, mldrColumnPK, fkNature, mldrFK.getIndice());
            mdrColumnsFK.add(mldrColumnFK);
        }

        // Transformation des paramètres PK en paramètres FK
        MDRAdjustParameters.adjustParameters(mcdTransform, mldrTable, mldrFK, mdrColumnsFK);
    }

    private void modifyRelationFK(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MLDRFK mldrFK, MDRFKNature fkNature) {
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
                throw new TransformMCDException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
   }

}
