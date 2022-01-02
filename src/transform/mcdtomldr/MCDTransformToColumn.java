package transform.mcdtomldr;

import datatypes.MLDRDatatype;
import exceptions.CodeApplException;
import exceptions.orderbuildnaming.OrderBuildNameException;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.MCDEntityNature;
import mcd.MCDRelEnd;
import mdr.MDRElementNames;
import mdr.MDRFKNature;
import mdr.MDRNamingLength;
import mdr.orderbuildnaming.MDROrderBuildNaming;
import mdr.orderbuildnaming.MDROrderBuildTargets;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRModel;
import mldr.MLDRTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import transform.mdr.services.MDRTransformService;

import java.util.ArrayList;

public class MCDTransformToColumn {

    private MCDTransform mcdTransform ;
    private MCDEntity mcdEntity ;
    private MLDRTable mldrTable ;

    public MCDTransformToColumn(MCDTransform mcdTransform) {
        this.mcdTransform = mcdTransform;
        this.mcdEntity = mcdEntity;
        this.mldrTable = mldrTable;
    }

    public void createOrModifyFromAttributes(MCDEntity mcdEntity, MLDRTable mldrTable) {
        for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()) {
            createOrModifyFromAttribute(mcdAttribute, mldrTable);
        }

    }

    public void createOrModifyFromAttribute(MCDAttribute mcdAttribute, MLDRTable mldrTable) {

        MLDRColumn mldrColumn = mldrTable.getMLDRColumnByMCDElementSource(mcdAttribute);

        if (mldrColumn == null) {
            mldrColumn = mldrTable.createColumn(mcdAttribute);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrColumn);
        }
        modifyColumn(mldrColumn, mcdAttribute);
        mldrColumn.setIteration(mcdTransform.getIteration());
    }


    public MLDRColumn createOrModifyFromRelEndParent(MLDRTable mldrTable, MCDRelEnd mcdRelEndParent, MLDRTable mldrTableParent, MLDRColumn mldrColumnPK, MDRFKNature fkNature, Integer indiceFK) {
        MLDRColumn mldrColumnFK = mldrTable.getMLDRColumnFKByMCDRelEndChildAndMLDRColumnPK(
                mcdRelEndParent, mldrColumnPK);
        if (mldrColumnFK == null){
            //mldrColumnFK = mldrTable.createColumnFK(mcdRelEndParent.getMcdRelation(), mldrColumnPK);
            mldrColumnFK = mldrTable.createColumnFK(mcdRelEndParent, mldrColumnPK);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrColumnFK);
        }
        modifyColumnFK(mldrColumnFK, mcdRelEndParent, mldrColumnPK, fkNature, indiceFK);
        mldrColumnFK.setIteration(mcdTransform.getIteration());
        return mldrColumnFK;
    }

    public void modifyColumn(MLDRColumn mldrColumn, MCDAttribute mcdAttribute){

        // Nom
        MLDRModel mldrModel = (MLDRModel) mldrColumn.getMDRTableAccueil().getMDRModelParent();
        MDRTransformService.names(mldrColumn, buildNameColumnAttr(mcdAttribute), mldrModel);


        // Datatype
        MLDRDatatype mldrDatatypeNew = MCDTransformToMLDRDatatype.fromMCDDatatype(mcdAttribute.getDatatypeLienProg());
        String mldrDatatypeLienProgNew = mldrDatatypeNew.getLienProg();
        if (mldrColumn.getDatatypeLienProg() != null) {
            if (!(mldrColumn.getDatatypeLienProg().equals(mldrDatatypeLienProgNew))) {
                mldrColumn.setDatatypeLienProg(mldrDatatypeLienProgNew);
            }
        } else {
            mldrColumn.setDatatypeLienProg(mldrDatatypeLienProgNew);
        }

        // Datatype contrainte
        // Les préférences (nom et lienProg) des contraintes datatype sont les mêmes que celles de datatypes !
        if (mldrColumn.getDatatypeConstraintLienProg() != null) {
            if (!(mldrColumn.getDatatypeConstraintLienProg().equals(mcdAttribute.getDatatypeLienProg()))) {
                mldrColumn.setDatatypeConstraintLienProg(mcdAttribute.getDatatypeLienProg());
            }
        } else {
            mldrColumn.setDatatypeConstraintLienProg(mcdAttribute.getDatatypeLienProg());
        }

        // Datatype size
        if (mldrColumn.getSize() != null) {
            if (mldrColumn.getSize().intValue() != mcdAttribute.getSize().intValue()) {
                mldrColumn.setSize(mcdAttribute.getSize());
            }
        } else {
            mldrColumn.setSize(mcdAttribute.getSize());
        }

        // Datatype scale
        if (mldrColumn.getScale() != null) {
            if (mldrColumn.getScale().intValue() != mcdAttribute.getScale().intValue()) {
                mldrColumn.setScale(mcdAttribute.getScale());
            }
        } else {
            mldrColumn.setScale(mcdAttribute.getScale());
        }

        // Mandatory (pour les colonnes non PK et FK)
        if (mldrColumn.isMandatory() != mcdAttribute.isMandatory()){
            mldrColumn.setMandatory(mcdAttribute.isMandatory());
        }

        // Frozen
        if (mldrColumn.isFrozen() != mcdAttribute.isFrozen()){
            mldrColumn.setFrozen(mcdAttribute.isFrozen());
        }

        // Uppercase
        if (mldrColumn.isUppercase() != mcdAttribute.isUppercase()){
            mldrColumn.setUppercase(mcdAttribute.isUppercase());
        }

        // Init Value
        String mldrInitValue= mcdAttribute.getInitValue();
        if (mldrColumn.getInitValue() != null) {
            if (! mldrColumn.getInitValue().equals(mcdAttribute.getInitValue())) {
                mldrColumn.setInitValue(mldrInitValue);
            }
        } else {
            mldrColumn.setInitValue(mldrInitValue);
        }

        // Derived Value
        String mldrDefaultValue= mcdAttribute.getInitValue();
        if (mldrColumn.getDerivedValue() != null) {
            if (! mldrColumn.getDerivedValue().equals(mcdAttribute.getDerivedValue())) {
                mldrColumn.setDerivedValue(mldrDefaultValue);
            }
        } else {
            mldrColumn.setDerivedValue(mldrDefaultValue);
        }
    }

    public void modifyColumnPKorFK(MLDRColumn mldrColumn) {

        // Datatype
        MLDRDatatype mldrDatatypeNew  = MCDTransformToMLDRDatatype.fromMCDDatatype(
                PreferencesManager.instance().preferences().getMCD_AID_DATATYPE_LIENPROG());
        String mldrDatatypeLienProgNew = mldrDatatypeNew.getLienProg();

        if (mldrColumn.getDatatypeLienProg() != null) {
            if (!(mldrColumn.getDatatypeLienProg().equals(mldrDatatypeLienProgNew))) {
                mldrColumn.setDatatypeLienProg(mldrDatatypeLienProgNew);
            }
        } else {
            mldrColumn.setDatatypeLienProg(mldrDatatypeLienProgNew);
        }

        // Datatype contrainte
        // Les préférences (nom et lienProg) des contraintes datatype sont les mêmes que celles de datratypes !
        String mldrDatatypeConstraintLienProgNew = PreferencesManager.instance().preferences().getMCD_AID_DATATYPE_LIENPROG();
        if (mldrColumn.getDatatypeConstraintLienProg() != null) {
            if (!(mldrColumn.getDatatypeConstraintLienProg().equals(mldrDatatypeConstraintLienProgNew))) {
                mldrColumn.setDatatypeConstraintLienProg(mldrDatatypeConstraintLienProgNew);
            }
        } else {
            mldrColumn.setDatatypeConstraintLienProg(mldrDatatypeConstraintLienProgNew);
        }

        // Datatype size
        // TODO-1 A rendre paramétrable
        Integer mldrDatatypeSizeNew = PreferencesManager.instance().preferences().MCDDOMAIN_AID_SIZEDEFAULT;

        if (mldrColumn.getSize() != null) {
            if (mldrColumn.getSize().intValue() != mldrDatatypeSizeNew) {
                mldrColumn.setSize(mldrDatatypeSizeNew);
            }
        } else {
            mldrColumn.setSize(mldrDatatypeSizeNew);
        }

        // Obligation de valeur pour PFK
        // MDRColumn.isMandatory() Déduit dynamiquement par MDRColumn.isPk()

    }


    public void modifyColumnPK(MCDEntity mcdEntity, MLDRColumn mldrColumnPK) {

        // Nom
        MLDRModel mldrModel = (MLDRModel) mldrColumnPK.getMDRTableAccueil().getMDRModelParent();
        MDRTransformService.names(mldrColumnPK, buildNameColumnPK(mcdEntity), mldrModel);

        // Obligation de valeur
        // MDRColumn.isMandatory() Déduit dynamiquement par MDRColumn.isPk()
        modifyColumnPKorFK(mldrColumnPK);
    }

    public void modifyColumnFK(MLDRColumn mldrColumnFK, MCDRelEnd mcdRelEndParent, MLDRColumn mldrColumnPK, MDRFKNature fkNature, Integer indiceFK) {

        // Nom
        MLDRModel mldrModel = (MLDRModel) mldrColumnFK.getMDRTableAccueil().getMDRModelParent();

        MDRTransformService.names(mldrColumnFK, buildNameColumnFK(mldrColumnFK, mcdRelEndParent, mldrColumnPK, indiceFK), mldrModel);

        modifyColumnPKorFK(mldrColumnFK);
    }


    private static  MDRElementNames buildNameColumnAttr(MCDAttribute mcdAttribute){
        Preferences preferences = PreferencesManager.instance().preferences();
        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            MCDEntity mcdEntityAccueil = mcdAttribute.getEntityAccueil();

            orderBuild.setFormat(preferences.getMDR_COLUMN_ATTR_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_COLUMN_NAME_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.COLUMNATTR);

            orderBuild.getAttrName().setValue(mcdAttribute.getName());
            orderBuild.getAttrShortName().setValue(mcdAttribute.getShortName());

            orderBuild.getColDerived().setValue(
                    mcdAttribute.isDerived() ? preferences.getMDR_COLUMN_DERIVED_MARKER() : "");

            if (mcdEntityAccueil.getNature() == MCDEntityNature.PSEUDOENTASS) {
                orderBuild.getPea().setValue(mcdEntityAccueil.getShortName());
                orderBuild.getPeaSep().setValue(preferences.getMDR_PEA_SEP_FORMAT());
            } else {
                orderBuild.getPea().setValue("");
                orderBuild.getPeaSep().setValue("");
            }

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                /*
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrcolumn.build.name.attribute.error",
                            new String[]{mcdEntityAccueil.getName(), mcdAttribute.getName()});
                }
                throw new TransformMCDException(message, e);

                 */
                String message = MessagesBuilder.getMessagesProperty("mdrcolumn.build.name.attribute.error",
                        new String[]{mcdEntityAccueil.getNamePath(), mcdAttribute.getName()});
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message += System.lineSeparator() + e.getMessage();
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;
    }

    private static  MDRElementNames buildNameColumnPK(MCDEntity mcdEntity){
        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            orderBuild.setFormat(preferences.getMDR_COLUMN_PK_NAME_FORMAT());
            orderBuild.setFormatUserMarkerLengthMax(Preferences.MDR_MARKER_CUSTOM_COLUMN_NAME_LENGTH);
            orderBuild.setTargetNaming(MDROrderBuildTargets.PK);

            orderBuild.getAttrName().setValue(mcdEntity);

            String name;

            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {
                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrcolumn.build.name.pk.error",
                            new String[]{mcdEntity.getName()});
                }
                throw new CodeApplException(message, e);
            }
            names.setElementName(name, element);
        }
        return names;

    }

    private static  MDRElementNames buildNameColumnFK(MLDRColumn mldrColumnFK, MCDRelEnd mcdRelEndParent, MLDRColumn mldrColumnPK, Integer indiceFK){
        Preferences preferences = PreferencesManager.instance().preferences();

        MDRElementNames names = new MDRElementNames();
        for (MDRNamingLength element: MDRNamingLength.values()) {

            MCDEntity mcdEntityParent = (MCDEntity) mcdRelEndParent.getmElement();
            MDROrderBuildNaming orderBuild = new MDROrderBuildNaming(element);
            if (mldrColumnPK.isFk() && preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR()) {
            //if (mcdEntityParent.isNoInd() && preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR()) {
                orderBuild.setFormat(preferences.getMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT());
                orderBuild.setTargetNaming(MDROrderBuildTargets.COLUMNFKONEANCESTOR);

            } else {
                orderBuild.setFormat(preferences.getMDR_COLUMN_FK_NAME_FORMAT());
                if (mcdEntityParent.getNature() == MCDEntityNature.IND) {
                    orderBuild.setTargetNaming(MDROrderBuildTargets.COLUMNFKFROMENTITYIND);
                } else {
                    orderBuild.setTargetNaming(MDROrderBuildTargets.COLUMNFKFROMENTITYNOIND);
                }
            }
            orderBuild.getTableShortNameParent().setValue((MCDEntity) mcdRelEndParent.getmElement());
            orderBuild.getTableSep().setValue();
            //String roleParent = mcdRelEndParent.getNameNoFreeOrNameRelation();
            //orderBuild.getRoleShortNameParent().setValue(roleParent);
            orderBuild.getRoleShortNameParent().setValue(mcdRelEndParent);
            if (StringUtils.isNotEmpty(orderBuild.getRoleShortNameParent().getValue())) {
                orderBuild.getRoleSep().setValue();
            } else {
                orderBuild.getRoleSep().setValue("");
            }
            // Prendre la valeur 30, 60 ou 120 selon l'état de la boucle
            String namePK = mldrColumnPK.getNames().getNameByNameLength(element);
            orderBuild.getColName().setValue(namePK);
            orderBuild.getColNameOneAncestor().setValue(namePK);

            //#MAJ 2021-03-21 Toutes les colonnes au lieu de brothers
            //ArrayList<MVCCDElement> brothers = MVCCDElementConvert.to(
            //        mldrColumnFK.getMDRTableAccueil().getMDRColumns());
            ArrayList<MVCCDElement> brothers = MVCCDElementConvert.to( mldrColumnFK.getBrothers());
            //        mldrColumnFK.getMDRTableAccueil().getBrothers());

            orderBuild.getIndColFK().setValue(namePK, brothers, indiceFK);

            String name;
            try {
                name = orderBuild.buildNaming();
            } catch (OrderBuildNameException e) {

                String message = "";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                } else {
                    message = MessagesBuilder.getMessagesProperty("mdrcolfk.build.name.error",
                            new String[]{mldrColumnPK.getMDRTableAccueil().getName(),
                                    mldrColumnPK.getName(),
                                    mldrColumnFK.getMDRTableAccueil().getName(),
                                    mcdRelEndParent.getNameNoFreeOrNameRelation()});
                }
                throw new CodeApplException(message, e);

            }
            names.setElementName(name, element);

        }
        return names;
    }

    /*
    private static String extractRoot(String nameColumn, String differenciation){
        return nameColumn.substring(0, nameColumn.length()- differenciation.length());

    }


    private static int nbColumnFKByRoot(String rootNew, MDRTable mdrTableParent){
        int resultat = 0;
        Pattern pattern = Pattern.compile(Preferences.MDR_INDICE_REGEXPR);
        for (MDRColumn col : mdrTableParent.getMDRColumns()){
            Matcher matcher = pattern.matcher(col.getName());
            if (matcher.find()){
                String differentiation = matcher.group();
                String rootCol = extractRoot(col.getName(), differentiation);
                if (rootCol.equals(rootNew)) resultat++;
            } else {
                if (col.getName().equals(rootNew)) resultat++;
            }
        }
        return resultat;
    }

     */



}
