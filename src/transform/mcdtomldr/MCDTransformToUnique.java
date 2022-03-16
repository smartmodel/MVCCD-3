package transform.mcdtomldr;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDManager;
import mcd.MCDEntity;
import mcd.MCDUnicity;
import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDParameter;
import mcd.services.IMCDModelService;
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

public class MCDTransformToUnique {

    private MCDTransform mcdTransform;

    public MCDTransformToUnique(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
    }

    public void createOrModifyFromAllUnicities(IMCDModel imcdModel, MLDRModel mldrModel) {

        for (MCDUnicity mcdUnicity: IMCDModelService.getAllMCDUnicitiesInModel(imcdModel)){
            MLDRTable mldrTable = mldrModel.getMLDRTableByEntitySource(mcdUnicity.getEntityAccueil());
            createOrModifyFromUnicity(mldrModel, mcdUnicity , mldrTable);
        }
    }


    private void createOrModifyFromUnicity(MLDRModel mldrModel,
                                           MCDUnicity mcdUnicity,
                                           MLDRTable mldrTable) {

        // Contrainte Unique
        MLDRUnique mldrUnique =  mldrTable.getMLDRUniqueByMCDElementSource(mcdUnicity);
        if (mldrUnique == null) {
            mldrUnique = mldrTable.createUnique(mcdUnicity);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrUnique);
        }

        // modification et Ajout des colonnes
        modifyUnique(mldrModel, mcdUnicity, mldrTable, mldrUnique);
        mldrUnique.setIteration(mcdTransform.getIteration());

    }

    private void modifyUnique(MLDRModel mldrModel,
                              MCDUnicity mcdUnicity,
                              MLDRTable mldrTable,
                              MLDRUnique mldrUnique) {


        //Nature
        MDRUniqueNature mldrUniqueNatureNew = mcdUnicity.getMDRUniqueNature();
        if (mldrUnique.getMdrUniqueNature() != mldrUniqueNatureNew){
            mldrUnique.setMdrUniqueNature(mldrUniqueNatureNew);
        }

        // Nom
        transform.mcdtomldr.services.MCDTransformService.names(mldrUnique, buildNameUnique(mcdUnicity, mldrUnique.getMdrUniqueNature(), mldrTable), mldrModel);

        //absolute
        if (mldrUnique.isAbsolute() != mcdUnicity.isAbsolute()){
            mldrUnique.setAbsolute(mcdUnicity.isAbsolute());
        }

        // Colonnes de la contrainte à transformer en paramètres
        ArrayList<MDRColumn> mdrColumnParameters = new ArrayList<MDRColumn>();

        if (! mldrUnique.isAbsolute()) {
            // Colonnes PFK et IdNat de la table
            ArrayList<MDRColumn> mdrColumnIds =  mldrTable.getMDRColumnsPFK();
            mdrColumnIds.addAll(mldrTable.getMDRColumnsFKIdNat());
            mdrColumnParameters.addAll(mdrColumnIds);
        }

        //Colonnes provenant des paramètres de la contrainte de niveau conceptuel
        mdrColumnParameters.addAll(mdrColumnsFromMCDUnicity(mcdUnicity, mldrTable));

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

    private MDRElementNames buildNameUnique(MCDUnicity mcdUnicity,
                                            MDRUniqueNature uniqueNature,
                                            MLDRTable mldrTable) {
        Preferences preferences = PreferencesManager.instance().preferences();
        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            MCDEntity mcdEntityAccueil = mcdUnicity.getEntityAccueil();

            orderBuild.setFormat(preferences.getMDR_UNIQUE_NAME_FORMAT());
            orderBuild.setTargetNaming(MDROrderBuildTargets.UNIQUE);
            //orderBuild.setNamingFormat(preferences.getMLDR_PREF_NAMING_FORMAT());

            orderBuild.getUniqueNature().setValue(uniqueNature.getText());
            orderBuild.getUniqueNatSep().setValue();

            orderBuild.getTableShortName().setValue(mcdEntityAccueil);
            orderBuild.getTableSep().setValue();

            orderBuild.getUniqueName().setValue(mcdUnicity.getName());
            orderBuild.getUniqueShortName().setValue(mcdUnicity.getShortName());


            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrunique.build.name.error",
                            new String[]{mldrTable.getName(), uniqueNature.getText(), mcdUnicity.getNamePath()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);

        }

        return names;
    }

}
