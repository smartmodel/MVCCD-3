package transform.mldrtompdr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToCheck {

    private MLDRTransform mldrTransform ;
    IMLDRSourceMPDRCheck imldrSourceMPDRCheck ;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToCheck(MLDRTransform mldrTransform,
                                IMLDRSourceMPDRCheck imldrSourceMPDRCheck,
                                MPDRModel mpdrModel,
                                MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.imldrSourceMPDRCheck = imldrSourceMPDRCheck;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    private MPDRCheckSpecific createOrModifyCheck(MPDRCheckRole mpdrCheckRole){
        MPDRCheckSpecific mpdrCheckSpecific =  mpdrTable.getMPDRCheckSpecificByMLDRSourceAndRole(imldrSourceMPDRCheck,
                                    mpdrCheckRole);

        if (mpdrCheckSpecific == null){
            mpdrCheckSpecific = mpdrTable.createCheckSpecific(imldrSourceMPDRCheck);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrCheckSpecific);
        }
        mpdrCheckSpecific.setIteration(mldrTransform.getIteration());
        return mpdrCheckSpecific;

    }

    public MPDRCheckSpecific createOrModifyCheck(MPDRColumn mpdrColumn,
                                         MPDRCheckRole mpdrCheckRole,
                                         String checkExpression){
        MPDRCheckSpecific mpdrCheckSpecific =  createOrModifyCheck(mpdrCheckRole);

        modifyCheck(mpdrCheckSpecific, mpdrCheckRole, mpdrColumn, checkExpression);
        return mpdrCheckSpecific;

    }

    private void modifyCheck(MPDRCheckSpecific mpdrCheckSpecific,
                             MPDRCheckRole mpdrCheckRole,
                             MPDRColumn mpdrColumn,
                             String checkExpression) {

        MCDTransformService.names(mpdrCheckSpecific,
                buildNameCheck(mpdrCheckSpecific, mpdrCheckRole, mpdrColumn),
                mpdrModel);

        if (mpdrCheckSpecific.getRole() != null){
            if ( mpdrCheckSpecific.getRole() != mpdrCheckRole) {
                mpdrCheckSpecific.setRole(mpdrCheckRole);
            }
        } else {
            mpdrCheckSpecific.setRole(mpdrCheckRole);
        }

        // Transformation des paramètres
        MLDRTransformToParameter mldrTransformToParameter = new MLDRTransformToParameter(
                mldrTransform, imldrSourceMPDRCheck, mpdrModel, mpdrCheckSpecific);
        mldrTransformToParameter.createOrModifyParameter(checkExpression);


    }

    private MDRElementNames buildNameCheck(MPDRCheckSpecific mpdrCheckSpecific,
                                           MPDRCheckRole mpdrCheckRole,
                                           MPDRColumn mpdrColumn) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrCheckRole.getNameFormat(mpdrModel));
            orderBuild.setFormatUserMarkerLengthMax(mpdrCheckRole.getFormatUserMarkerLengthMax());
            if (mpdrCheckRole ==  MPDRCheckRole.DATATYPE) {
                orderBuild.setTargetNaming(MDROrderBuildTargets.CHECKCOLUMNDATATYPE);
            }
            orderBuild.getMpdrColumnName().setValue(mpdrColumn.getName());

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mpdrcheck.columndatatype.build.name.error",
                            new String[]{mpdrColumn.getNamePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;


    }
}
