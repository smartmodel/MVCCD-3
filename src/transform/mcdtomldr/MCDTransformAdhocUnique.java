package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDUnicity;
import mcd.interfaces.IMCDParameter;
import mdr.MDRColumn;
import mdr.MDRElementNames;
import mdr.MDRNamingLength;
import mdr.MDRUniqueNature;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import mdr.services.IMDRParameterService;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRModel;
import mldr.MLDRTable;
import mldr.MLDRUnique;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mcdtomldr.services.MCDTransformService;

import java.util.ArrayList;

public class MCDTransformAdhocUnique {

    private MCDTransform mcdTransform;

    public MCDTransformAdhocUnique(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }


    public void createOrModify(MLDRModel mldrModel,
                               MCDElement mcdElement,
                               ArrayList<MDRColumn> mdrColumns,
                               MLDRTable mldrTable,
                               MDRUniqueNature mdrUniqueNature) {
        MLDRUnique mldrUnique =  mldrTable.getMLDRUniqueByMCDElementSourceAndNature(mcdElement, mdrUniqueNature);
        if (mldrUnique == null) {
            mldrUnique = mldrTable.createUnique(mcdElement);
            // Nature immuable
            mldrUnique.setMdrUniqueNature(mdrUniqueNature);
            // Jamais absolu
            mldrUnique.setAbsolute(false);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrUnique);
        }

        // modification et Ajout des colonnes
        modifyUnique(mldrModel, mcdElement, mdrColumns, mldrTable, mldrUnique);
        mldrUnique.setIteration(mcdTransform.getIteration());
    }


    private void modifyUnique(MLDRModel mldrModel,
                              MCDElement mcdElement,
                              ArrayList<MDRColumn> mdrColumns,
                              MLDRTable mldrTable,
                              MLDRUnique mldrUnique) {

        //Nature - Immuable

        // Nom
        MCDTransformService.names(mldrUnique, buildNameUnique(mcdElement, mldrUnique.getMdrUniqueNature(), mldrTable), mldrModel);

        //Absolute - jamais

        // Colonnes de la contrainte à transformer en paramètres
        ArrayList<MDRColumn> mdrColumnParameters = new ArrayList<MDRColumn>();

        //Création des paramètres à partir des colonnes fournies
        mdrColumnParameters.addAll(mdrColumns);

        // Ajustement des paramètres
        MCDTransformService.adjustParameters(mcdTransform, mldrUnique,
                IMDRParameterService.to(mdrColumnParameters));

    }


    private ArrayList<MDRColumn> mdrColumnsFromMCDUnicity(MCDUnicity mcdUnicity,
                                                          MLDRTable mldrTable) {
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();

        for (IMCDParameter imcdParameter : mcdUnicity.getMcdTargets()){
            resultat.addAll(mdrColumnsFromIMCDParameter(imcdParameter, mldrTable));
        }
        return resultat;
    }

    private ArrayList<MDRColumn>  mdrColumnsFromIMCDParameter(IMCDParameter imcdParameter, MLDRTable mldrTable) {
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        // les colonnes qui ont comme source MCD le paramètre imcdParameter (Attibut ou extrémité d'association)
        for (MLDRColumn mldrColumn :mldrTable.getMLDRColumns()){
            if (mldrColumn.getMcdElementSource() == imcdParameter){
                resultat.add(mldrColumn);
            }
        }
        return resultat;
    }

    private MDRElementNames buildNameUnique(MCDElement mcdElement,
                                            MDRUniqueNature uniqueNature,
                                            MLDRTable mldrTable) {
        Preferences preferences = PreferencesManager.instance().preferences();
        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);

            orderBuild.setFormat(preferences.getMDR_UNIQUE_NAME_FORMAT());
            orderBuild.setTargetNaming(MDROrderBuildTargets.UNIQUE);
            //orderBuild.setNamingFormat(preferences.getMLDR_PREF_NAMING_FORMAT());

            orderBuild.getUniqueNature().setValue(uniqueNature.getText());
            orderBuild.getUniqueNatSep().setValue();

            orderBuild.getTableShortName().setValue(mldrTable.getShortName());
            orderBuild.getTableSep().setValue("");

            orderBuild.getUniqueName().setValue("");
            orderBuild.getUniqueShortName().setValue("");


            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrunique.build.name.error",
                            new String[]{mldrTable.getName(), uniqueNature.getText(), mcdElement.getNamePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);

        }

        return names;
    }


}
