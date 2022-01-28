package transform.mldrtompdr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRSequence;
import mpdr.MPDRSequenceRole;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToSequence {

    private MLDRTransform mldrTransform ;
    private MLDRColumn mldrColumn ;
    private MPDRModel mpdrModel ;
    private MPDRColumn mpdrColumn;

    public MLDRTransformToSequence(MLDRTransform mldrTransform,
                                   MLDRColumn mldrColumn,
                                   MPDRModel mpdrModel,
                                   MPDRColumn mpdrColumn) {
        this.mldrTransform = mldrTransform;
        this.mldrColumn = mldrColumn;
        this.mpdrModel = mpdrModel;
        this.mpdrColumn = mpdrColumn;
    }


    public MPDRSequence createOrModifySeq(MPDRSequenceRole mpdrSequenceRole){

        MPDRSequence mpdrSequence = mpdrColumn.getMPDRSequence();

        if (mpdrSequence == null){
            mpdrSequence = mpdrColumn.createSequence(mldrColumn);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrSequence);
        }
        modifySequence(mldrColumn, mpdrSequence, mpdrSequenceRole);
        mpdrSequence.setIteration(mldrTransform.getIteration());
        return mpdrSequence;

    }

    private void modifySequence(MLDRColumn mldrColumn,
                                MPDRSequence mpdrSequence,
                                MPDRSequenceRole mpdrSequenceRole) {

        MCDTransformService.names(mpdrSequence,
                buildNameSequence(mldrColumn, mpdrSequenceRole),
                mpdrModel);

        // Pas de modification des paramètres de la séquence à ce niveau
    }

    private MDRElementNames buildNameSequence(MLDRColumn mldrColumn, MPDRSequenceRole mpdrSequenceRole) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrSequenceRole.getNameFormat(mpdrModel));
            orderBuild.setFormatUserMarkerLengthMax(mpdrSequenceRole.getFormatUserMarkerLengthMax());
            orderBuild.setTargetNaming(MDROrderBuildTargets.SEQUENCEPK);

            //orderBuild.setNamingFormat(mpdrModel.getNamingFormatForDB());
            //orderBuild.setNamingFormat(preferences.getMLDR_PREF_NAMING_FORMAT());

            MLDRTable mldrTable = mldrColumn.getMLDRTableAccueil();

            orderBuild.getTableShortName().setValue(mldrTable.getShortName());

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mpdrsequence.build.name.error",
                            new String[]{mldrTable.getNamePath(), mldrColumn.getNamePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;


    }
}
