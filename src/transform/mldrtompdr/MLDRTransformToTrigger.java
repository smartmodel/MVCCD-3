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
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRTableOrView;
import mpdr.tapis.MPDRTrigger;
import mpdr.tapis.MPDRTriggerType;
import mpdr.tapis.MPDRView;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToTrigger {

    private MLDRTransform mldrTransform ;
    private IMLDRElement imldrElement;
    private MPDRModel mpdrModel ;
    private IMPDRTableOrView impdrTableOrView;

    public MLDRTransformToTrigger(MLDRTransform mldrTransform,
                                  IMLDRElement imldrElement,
                                  MPDRModel mpdrModel,
                                  IMPDRTableOrView impdrTableOrView) {
        this.mldrTransform = mldrTransform;
        this.imldrElement = imldrElement;
        this.mpdrModel = mpdrModel;
        this.impdrTableOrView = impdrTableOrView;
    }


    public MPDRTrigger createOrModifyTrigger(MPDRTriggerType type) {

        MPDRTrigger mpdrTrigger = impdrTableOrView.getMPDRTriggerByType(type);

        if (mpdrTrigger == null){
            mpdrTrigger = impdrTableOrView.createTrigger(type, imldrElement);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrTrigger);
        }

        modifyTrigger(imldrElement, mpdrTrigger, type);
        mpdrTrigger.setIteration(mldrTransform.getIteration());
        return mpdrTrigger;
    }


    private void modifyTrigger(IMLDRElement imldrElement,
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
                buildNameTrigger(imldrElement, type),
                mpdrModel);
    }


    private MDRElementNames buildNameTrigger (IMLDRElement imldrElement,
                                              MPDRTriggerType type) {
        if (impdrTableOrView instanceof MPDRTable) {
            return buildNameTriggerTable ( (MLDRTable) imldrElement, type);
        }
        if (impdrTableOrView instanceof MPDRView) {
            return buildNameTriggerView ( (MPDRView) impdrTableOrView, type);
        }
        throw new CodeApplException("L'instance de impdTableOrView " + impdrTableOrView.getClass().getName() + " n'est pas connue'.");
    }

    private MDRElementNames buildNameTriggerTable (MLDRTable mldrTable,
                                                   MPDRTriggerType type) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();

        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrModel.getTriggerTableNameFormat());
            orderBuild.setFormatUserMarkerLengthMax(0);
            orderBuild.setTargetNaming(MDROrderBuildTargets.TRIGGERTABLE);

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

    private MDRElementNames buildNameTriggerView (MPDRView mpdrView,
                                                  MPDRTriggerType type) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();

        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrModel.getTriggerViewNameFormat());
            orderBuild.setFormatUserMarkerLengthMax(0);
            orderBuild.setTargetNaming(MDROrderBuildTargets.TRIGGERVIEW);

            orderBuild.getViewName().setValue(mpdrView.getName());
            orderBuild.getTableSep().setValue();
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
                            new String[]{mpdrView.getNamePath(), type.getMarker(), });
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }


        return names;

    }

}
