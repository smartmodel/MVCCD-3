package transform.mldrtompdr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRTriggerScope;
import mpdr.tapis.MPDRTriggerType;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToTrigger {

    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToTrigger(MLDRTransform mldrTransform,
                                  MLDRTable mldrTable,
                                  MPDRModel mpdrModel,
                                  MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRTrigger createOrModifyTrigger(MPDRTriggerType type) {

        MPDRTrigger mpdrTrigger = mpdrTable.getMPDRTriggerByType(type);

        if (mpdrTrigger == null){
            mpdrTrigger = mpdrTable.createTrigger(type, mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrTrigger);
        }

        modifyTrigger(mldrTable, mpdrTrigger, type);
        mpdrTrigger.setIteration(mldrTransform.getIteration());
        return mpdrTrigger;
    }


    private void modifyTrigger(MLDRTable mldrTable,
                               MPDRTrigger mpdrTrigger,
                               MPDRTriggerType type) {

        if (mpdrTrigger.getType() != null) {
            if (mpdrTrigger.getType() != type) {
                mpdrTrigger.setType(type);
            }
        } else {
            mpdrTrigger.setType(type);
        }
        MCDTransformService.names(mpdrTrigger,
                buildNameTrigger(mldrTable, type),
                mpdrModel);
    }

    private MDRElementNames buildNameTrigger(MLDRTable mldrTable, MPDRTriggerType type) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();

        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrModel.getTriggerNameFormat());
            orderBuild.setFormatUserMarkerLengthMax(0);
            if (type.getMpdrTriggerScope() == MPDRTriggerScope.TABLE) {
                orderBuild.setTargetNaming(MDROrderBuildTargets.TRIGGERTABLE);
            } else  if (type.getMpdrTriggerScope() == MPDRTriggerScope.VIEW) {
                orderBuild.setTargetNaming(MDROrderBuildTargets.TRIGGERVIEW);
            } else {
                throw new CodeApplException("MPDRTriggerScope n'est pas défini...");
            }

            orderBuild.getTableSep().setValue();
            orderBuild.getTableShortName().setValue(mldrTable.getShortName());
            orderBuild.getTypeTriggerMarker().setValue(mpdrModel, type);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mpdrtrigger.build.name.error",
                            new String[]{mldrTable.getNamePath(), type.getMarker(), });
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }


        return names;

    }

}
