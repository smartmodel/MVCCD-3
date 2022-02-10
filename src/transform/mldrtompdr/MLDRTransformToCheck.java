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
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.*;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToCheck {

    private MLDRTransform mldrTransform ;
    IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToCheck(MLDRTransform mldrTransform,
                                IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc,
                                MPDRModel mpdrModel,
                                MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.imldrSourceMPDRCConstraintSpecifc = imldrSourceMPDRCConstraintSpecifc;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    private MPDRCheckSpecific createOrModifyCheck(MPDRConstraintSpecificRole mpdrConstraintSpecificRole){
        MPDRCheckSpecific mpdrCheckSpecific =  mpdrTable.getMPDRConstraintSpecificByMLDRSourceAndRole(imldrSourceMPDRCConstraintSpecifc,
                mpdrConstraintSpecificRole);

        if (mpdrCheckSpecific == null){
            mpdrCheckSpecific = mpdrTable.createCheckSpecific(imldrSourceMPDRCConstraintSpecifc);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrCheckSpecific);
        }
        mpdrCheckSpecific.setIteration(mldrTransform.getIteration());
        return mpdrCheckSpecific;

    }

    public MPDRCheckSpecific createOrModifyCheck(MPDRColumn mpdrColumn,
                                         MPDRConstraintSpecificRole mpdrConstraintSpecificRole,
                                         String checkExpression){
        MPDRCheckSpecific mpdrCheckSpecific =  createOrModifyCheck(mpdrConstraintSpecificRole);

        modifyCheck(mpdrCheckSpecific, mpdrConstraintSpecificRole, mpdrColumn, checkExpression);
        return mpdrCheckSpecific;

    }

    private void modifyCheck(MPDRCheckSpecific mpdrCheckSpecific,
                             MPDRConstraintSpecificRole mpdrConstraintSpecificRole,
                             MPDRColumn mpdrColumn,
                             String checkExpression) {

        MCDTransformService.names(mpdrCheckSpecific,
                buildNameCheck(mpdrCheckSpecific, mpdrConstraintSpecificRole, mpdrColumn),
                mpdrModel);

        if (mpdrCheckSpecific.getRole() != null){
            if ( mpdrCheckSpecific.getRole() != mpdrConstraintSpecificRole) {
                mpdrCheckSpecific.setRole(mpdrConstraintSpecificRole);
            }
        } else {
            mpdrCheckSpecific.setRole(mpdrConstraintSpecificRole);
        }

        // Transformation des paramètres
        MLDRTransformToParameter mldrTransformToParameter = new MLDRTransformToParameter(
                mldrTransform, imldrSourceMPDRCConstraintSpecifc, mpdrModel, mpdrCheckSpecific);
        mldrTransformToParameter.createOrModifyParameter(checkExpression);


    }

    private MDRElementNames buildNameCheck(MPDRCheckSpecific mpdrCheckSpecific,
                                           MPDRConstraintSpecificRole mpdrConstraintSpecificRole,
                                           MPDRColumn mpdrColumn) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrConstraintSpecificRole.getNameFormat(mpdrModel));
            orderBuild.setFormatUserMarkerLengthMax(mpdrConstraintSpecificRole.getFormatUserMarkerLengthMax());
            if (mpdrConstraintSpecificRole ==  MPDRConstraintSpecificRole.DATATYPE) {
                orderBuild.setTargetNaming(MDROrderBuildTargets.CHECKCOLUMNDATATYPE);
            }
            MLDRTable mldrTable = (MLDRTable) mpdrTable.getMldrElementSource();
            orderBuild.getTableShortName().setValue(mldrTable.getShortName());
            orderBuild.getTableSep().setValue();
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
