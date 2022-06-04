package transform.mldrtompdr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRConstraintCustomJnal;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRTableJnal;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToTableJnal {

    private MLDRTransform mldrTransform ;
    private MLDRConstraintCustomJnal mldrConstraintCustomJnal;
    private MPDRModel mpdrModel ;
    private MPDRTable mpdrTable;

    public MLDRTransformToTableJnal(MLDRTransform mldrTransform,
                                    MLDRConstraintCustomJnal mldrConstraintCustomJnal,
                                    MPDRModel mpdrModel,
                                    MPDRTable mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrConstraintCustomJnal = mldrConstraintCustomJnal;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }

    public MPDRTableJnal createOrModifyTableJnal(){

        MPDRTableJnal mpdrTableJnal = mpdrTable.getMPDRTableJnal();

        if (mpdrTableJnal == null){
            mpdrTableJnal = mpdrTable.createTableJnal(mldrConstraintCustomJnal);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrTableJnal);
        }
        modifyTableJnal(mldrConstraintCustomJnal, mpdrTableJnal);
        mpdrTableJnal.setIteration(mldrTransform.getIteration());
        return mpdrTableJnal;
    }

    private void modifyTableJnal(MLDRConstraintCustomJnal mldrConstraintCustomJnal,
                                 MPDRTableJnal mpdrTableJnal) {

        MCDTransformService.names(mpdrTableJnal,
                buildNameTableJnal(mldrConstraintCustomJnal), mpdrModel);
    }


    private MDRElementNames buildNameTableJnal (MLDRConstraintCustomJnal mldrConstraintCustomJnal) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();

        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrModel.getTableJnalNameFormat());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_TABLEJNAL_NAME_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.TABLEJNAL);

            MLDRTable mldrTable = (MLDRTable) mldrConstraintCustomJnal.getMDRTableAccueil();
            orderBuild.getTableShortName().setValue(mldrTable.getShortName());

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mpdrtablejnal.build.name.error",
                            new String[]{mldrTable.getNamePath() });
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }


        return names;

    }
}
