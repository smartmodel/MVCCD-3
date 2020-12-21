package transform.mcdtomldr;

import exceptions.orderbuildnaming.OrderBuildNameException;
import exceptions.TransformMCDException;
import main.MVCCDManager;
import mcd.MCDEntity;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import mdr.*;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

import java.util.ArrayList;

public class MCDTransformToFK {

    private MCDTransform mcdTransform ;

    public MCDTransformToFK(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public MLDRFK createOrModifyFromRelEndParent(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MDRFKNature fkNature) {

        MLDRFK mldrFK =  mldrTable.getMLDRFKByMCDElementSource(mcdRelEndParent.getMcdRelation());

        MCDRelation mcdRelation = mcdRelEndParent.getMcdRelation();
        if (mldrFK == null) {
            mldrFK = mldrTable.createFK(mcdRelation);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrFK);
        }
        modifyFK(mldrModel, mcdRelEndParent, mldrTable, mldrFK, fkNature);
        mcdTransform.addInTrace(mcdRelEndParent.getMCDRelEndOpposite(), mldrFK);

        return mldrFK;
    }


    private void modifyFK(MLDRModel mldrModel, MCDRelEnd mcdRelEndParent, MLDRTable mldrTable, MLDRFK mldrFK, MDRFKNature fkNature) {
        MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getMcdElement();
        MLDRTable mldrTableParent = mldrModel.getMLDRTableByEntitySource(mcdEntityParent);
        MLDRPK mldrPKParent = mldrTableParent.getMLDRPK();

        // Nom
        MDRElementNames nameFK = buildNameFK(mldrTable, mldrFK, mcdRelEndParent, mldrTableParent);
        MCDTransformService.names(mldrFK, nameFK, mldrModel);
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
            MCDTransformToColumn mcdTransformToColumn = new MCDTransformToColumn(mcdTransform);
            MLDRColumn mldrColumnFK = mcdTransformToColumn.createOrModifyFromRelEndParent(mldrTable, mcdRelEndParent, mldrTableParent, mldrColumnPK, fkNature, mldrFK.getIndice());
            mdrColumnsFK.add(mldrColumnFK);
        }

        // Transformation des paramètres PK en paramètres FK
        new MCDRAdjustParametersFK().adjustParameters(mldrTable, mldrFK, mdrColumnsFK);
    }

    private void adjustParameters(MLDRFK mldrFK, MLDRColumn mldrColumnPK) {

    }

    protected MDRElementNames buildNameFK(MDRTable mdrTable,
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

            orderBuild.getTableShortNameChild().setValue((MCDEntity) mcdRelEndParent.getMCDRelEndOpposite().getMcdElement());
            orderBuild.getTableShortNameParent().setValue((MCDEntity) mcdRelEndParent.getMcdElement());
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
                            new String[]{mdrTable.getName(), mdrTableParent.getName(), mcdRelEndParent.getNameNoFreeOrNameRelation()});
                }
                throw new TransformMCDException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
   }

}
