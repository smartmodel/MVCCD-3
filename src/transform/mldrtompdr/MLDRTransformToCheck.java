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


    private MPDRCheck createOrModifyCheck(MPDRCheckRole mpdrCheckRole){
        MPDRCheck mpdrCheck =  mpdrTable.getMPDRCheckByMLDRSourceAndRole(imldrSourceMPDRCheck,
                                    mpdrCheckRole);

        if (mpdrCheck == null){
            mpdrCheck = mpdrTable.createCheck(imldrSourceMPDRCheck);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrCheck);
        }
        mpdrCheck.setIteration(mldrTransform.getIteration());
        return mpdrCheck;

    }

    public MPDRCheck createOrModifyCheck(MPDRColumn mpdrColumn,
                                         MPDRCheckRole mpdrCheckRole,
                                         String checkExpression){
        MPDRCheck mpdrCheck =  createOrModifyCheck(mpdrCheckRole);

        modifyCheck(mpdrCheck, mpdrCheckRole, mpdrColumn, checkExpression);
        return mpdrCheck;

    }

    private void modifyCheck(MPDRCheck mpdrCheck,
                             MPDRCheckRole mpdrCheckRole,
                             MPDRColumn mpdrColumn,
                             String checkExpression) {

        MCDTransformService.names(mpdrCheck,
                buildNameCheck(mpdrCheck, mpdrCheckRole, mpdrColumn),
                mpdrModel);

        if (mpdrCheck.getRole() != null){
            if ( mpdrCheck.getRole() != mpdrCheckRole) {
                mpdrCheck.setRole(mpdrCheckRole);
            }
        } else {
            mpdrCheck.setRole(mpdrCheckRole);
        }

        // Transformation des paramètres
        MLDRTransformToParameter mldrTransformToParameter = new MLDRTransformToParameter(
                mldrTransform, imldrSourceMPDRCheck, mpdrModel, mpdrCheck);
        mldrTransformToParameter.createOrModifyParameter(checkExpression);


    }

    private MDRElementNames buildNameCheck(MPDRCheck mpdrCheck,
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
