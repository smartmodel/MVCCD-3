package transform.mldrtompdr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mdr.MDRElementNames;
import mdr.MDRModel;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRConstraintCustomSpecialized;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRView;
import mpdr.tapis.MPDRViewType;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToView {

    private MLDRTransform mldrTransform ;
    MLDRConstraintCustomSpecialized mldrSpecialized;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToView(MLDRTransform mldrTransform,
                               MLDRConstraintCustomSpecialized mldrSpecialized,
                               MPDRModel mpdrModel,
                               MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrSpecialized = mldrSpecialized;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRView createOrModifyView(MPDRViewType type) {

        MPDRView mpdrView = mpdrTable.getMPDRViewByType(type);

        if (mpdrView == null){
            mpdrView = mpdrTable.createView(mldrSpecialized);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrView);
        }

        modifyView(mpdrView, type);
        mpdrView.setIteration(mldrTransform.getIteration());
        return mpdrView;
    }


    private void modifyView(MPDRView mpdrView,
                            MPDRViewType mpdrViewType) {

        if (mpdrView.getType() != null) {
            if (mpdrView.getType() != mpdrViewType) {
                mpdrView.setType(mpdrViewType);
            }
        } else {
            mpdrView.setType(mpdrViewType);
        }
        MCDTransformService.names(mpdrView,
                buildNameView((MLDRTable) mldrSpecialized.getMDRTableAccueil(), mpdrViewType),
                (MDRModel) mpdrModel);
    }


    private MDRElementNames buildNameView(MLDRTable mldrTable, MPDRViewType type) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();

        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrModel.getViewNameFormat());
            orderBuild.setFormatUserMarkerLengthMax(0);
            orderBuild.setTargetNaming(MDROrderBuildTargets.VIEW);
            orderBuild.getTableSep().setValue();
            orderBuild.getTableShortName().setValue(mldrTable.getShortName());
            orderBuild.getTypeViewMarker().setValue((MPDRModel) mpdrModel, type);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mpdrview.build.name.error",
                            new String[]{mldrTable.getNamePath(), type.getMarker(), });
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }


        return names;

    }

}
