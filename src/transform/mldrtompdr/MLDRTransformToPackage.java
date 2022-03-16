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
import mldr.MLDRTable;
import mpdr.MPDRModel;
import mpdr.interfaces.IMPDRModelRequirePackage;
import mpdr.interfaces.IMPDRTableRequirePackage;
import mpdr.tapis.MPDRPackage;
import mpdr.tapis.MPDRPackageType;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

public class MLDRTransformToPackage {


    private MLDRTransform mldrTransform ;
    private MLDRTable mldrTable;
    private IMPDRModelRequirePackage mpdrModel ;
    private IMPDRTableRequirePackage mpdrTable;

    public MLDRTransformToPackage(MLDRTransform mldrTransform,
                                  MLDRTable mldrTable,
                                  IMPDRModelRequirePackage mpdrModel,
                                  IMPDRTableRequirePackage mpdrTable) {
        this.mldrTransform = mldrTransform;
        this.mldrTable = mldrTable;
        this.mpdrModel = mpdrModel;
        this.mpdrTable = mpdrTable;
    }


    public MPDRPackage createOrModifyPackage(MPDRPackageType type) {

        MPDRPackage mpdrPackage = mpdrTable.getMPDRPackageByType(type);

        if (mpdrPackage == null){
            mpdrPackage = mpdrTable.createPackage(type, mldrTable);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mpdrPackage);
        }

        modifyPackage(mpdrPackage, type);
        mpdrPackage.setIteration(mldrTransform.getIteration());
        return mpdrPackage;
    }


    private void modifyPackage(MPDRPackage mpdrPackage,
                               MPDRPackageType mpdrPackageType) {

        if (mpdrPackage.getType() != null) {
            if (mpdrPackage.getType() != mpdrPackageType) {
                mpdrPackage.setType(mpdrPackageType);
            }
        } else {
            mpdrPackage.setType(mpdrPackageType);
        }
        MCDTransformService.names(mpdrPackage,
                buildNamePackage(mldrTable, mpdrPackageType),
                (MDRModel) mpdrModel);
    }


    private MDRElementNames buildNamePackage(MLDRTable mldrTable, MPDRPackageType type) {

        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();

        for (MDRNamingLength element: MDRNamingLength.values()) {
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(mpdrModel.getPackageNameFormat());
            orderBuild.setFormatUserMarkerLengthMax(0);
            orderBuild.setTargetNaming(MDROrderBuildTargets.PACKAGE);
            orderBuild.getTableSep().setValue();
            orderBuild.getTableShortName().setValue(mldrTable.getShortName());
            orderBuild.getTypePackageMarker().setValue((MPDRModel) mpdrModel, type);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mpdrpackage.build.name.error",
                            new String[]{mldrTable.getNamePath(), type.getMarker(), });
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }


        return names;

    }


}
