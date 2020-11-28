package mcd.transform;

import datatypes.MLDRDatatype;
import exceptions.CodeApplException;
import exceptions.OrderBuildNameException;
import main.MVCCDManager;
import mcd.*;
import mdr.MDRColumn;
import mdr.MDRFKNature;
import mdr.MDRTable;
import mdr.utilities.MDROrderBuildString;
import messages.MessagesBuilder;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Transform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MCDTransformToColumn {

    //private MCDEntity mcdEntity ;
    //private MLDRTable mldrTable ;


    public void fromAttributes(MCDEntity mcdEntity, MLDRTable mldrTable) {
        for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()) {
            fromAttribute(mcdAttribute, mldrTable);
        }
    }

    public void fromAttribute(MCDAttribute mcdAttribute, MLDRTable mldrTable) {

        MLDRColumn mldrColumn = mldrTable.getMLDRColumnByMCDElementSource(mcdAttribute);

        if (mldrColumn == null) {
            mldrColumn = mldrTable.createColumn(mcdAttribute);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrColumn);
        }
        modifyColumn(mldrColumn, mcdAttribute);
    }


    public MLDRColumn fromRelEndParent(MLDRTable mldrTable, MCDRelEnd mcdRelEndParent, MLDRTable mldrTableParent, MLDRColumn mldrColumnPK, MDRFKNature fkNature) {
        MLDRColumn mldrColumnFK = mldrTable.getMLDRColumnFKByMCDRelationAndMLDRColumnPK(
                mcdRelEndParent.getMcdRelation(), mldrColumnPK);
        if (mldrColumnFK == null){
            mldrColumnFK = mldrTable.createColumnFK(mcdRelEndParent.getMcdRelation(), mldrColumnPK);
            MVCCDManager.instance().addNewMVCCDElementInRepository(mldrColumnFK);
        }
        modifyColumnFK(mldrColumnFK, mcdRelEndParent, mldrColumnPK, fkNature);
        return mldrColumnFK;
    }

    public void modifyColumn(MLDRColumn mldrColumn, MCDAttribute mcdAttribute){

        // Nom
        Transform.name(mldrColumn, buildNameColumnAttr(mcdAttribute));

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
        // Les préférences (nom et lienProg) des contraintes datatype sont les mêmes que celles de datratypes !
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
                mldrColumn.setDatatypeLienProg(mldrDatatypeLienProgNew);
            }
        } else {
            mldrColumn.setScale(mcdAttribute.getScale());
        }

        // Mandatory
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

    }


    public void modifyColumnPK(MCDEntity mcdEntity, MLDRColumn mldrColumnPK) {

        // Nom
        Transform.name(mldrColumnPK, buildNameColumnPK(mcdEntity));

        modifyColumnPKorFK(mldrColumnPK);
    }

    public void modifyColumnFK(MLDRColumn mldrColumnFK, MCDRelEnd mcdRelEndParent, MLDRColumn mldrColumnPK, MDRFKNature fkNature) {

        // Nom
        Transform.name(mldrColumnFK, buildNameColumnFK(mldrColumnFK, mcdRelEndParent, mldrColumnPK));

        modifyColumnPKorFK(mldrColumnFK);
    }


    private static  String buildNameColumnAttr(MCDAttribute mcdAttribute){
        //Construire le nom standard de la colonne
        Preferences preferences = PreferencesManager.instance().preferences();
        MCDEntity mcdEntityAccueil = mcdAttribute.getEntityAccueil();

        MDROrderBuildString orderCol = new  MDROrderBuildString();
        orderCol.setFormat(preferences.getMDR_COLUMN_ATTR_NAME_FORMAT());
        orderCol.setAttrName(mcdAttribute.getName());
        orderCol.setTableShortName(mcdEntityAccueil.getShortName());
        if (mcdAttribute.isDerived()){
            orderCol.setColDerived(preferences.getMDR_COLUMN_DERIVED());
        } else {
            orderCol.setColDerived("");
        }
        if (mcdEntityAccueil.getNature() == MCDEntityNature.PSEUDOENTASS){
            orderCol.setPeaShortName(mcdEntityAccueil.getShortName());
            orderCol.setPeaSep(preferences.getMDR_PEA_SEP_FORMAT());
        } else {
            orderCol.setPeaShortName("");
            orderCol.setPeaSep("");
        }

        String nameFinal ;

        try {
            nameFinal = orderCol.buildString();
        } catch(OrderBuildNameException e){
            String message = MessagesBuilder.getMessagesProperty("mdrcolumn.build.name.attribute.error",
                    new String[] {mcdEntityAccueil.getName(), mcdAttribute.getName()});
            throw new CodeApplException(message,e);
        }
        return nameFinal;
    }

    private static  String buildNameColumnPK(MCDEntity mcdEntity){
        String nameBrut = "";
        MCDEntityNature mcdEntityNature = mcdEntity.getNature();
        if (mcdEntityNature == MCDEntityNature.IND) {
            nameBrut = PreferencesManager.instance().preferences().getMCD_AID_IND_COLUMN_NAME();
        }
        if (    (mcdEntityNature == MCDEntityNature.DEP) ||
                (mcdEntityNature == MCDEntityNature.ENTASSDEP) ||
                (mcdEntityNature == MCDEntityNature.NAIREDEP) ) {
            if (PreferencesManager.instance().preferences().getMCDTOMLDR_MODE().equals(
                    Preferences.MCDTOMLDR_MODE_DT)) {
                nameBrut = PreferencesManager.instance().preferences().getMCD_AID_DEP_COLUMN_NAME();
            }
            if (PreferencesManager.instance().preferences().getMCDTOMLDR_MODE().equals(
                    Preferences.MCDTOMLDR_MODE_TI)) {
                nameBrut = PreferencesManager.instance().preferences().getMCD_AID_IND_COLUMN_NAME();
            }
        }

        Preferences preferences = PreferencesManager.instance().preferences();
        MDROrderBuildString orderCol = new  MDROrderBuildString();
        orderCol.setFormat(preferences.getMDR_COLUMN_ATTR_NAME_FORMAT());
        orderCol.setAttrName(nameBrut);
        orderCol.setTableShortName(mcdEntity.getShortName());
        orderCol.setColDerived("");
        orderCol.setPeaShortName("");
        orderCol.setPeaSep("");

        String nameFinal ;

        try {
            nameFinal = orderCol.buildString();
        } catch(OrderBuildNameException e){
            String message = MessagesBuilder.getMessagesProperty("mdrcolumn.build.name.pk.error",
                    new String[] {mcdEntity.getName()});
            throw new CodeApplException(message,e);
        }
        return nameFinal;

    }

    private static  String buildNameColumnFK(MLDRColumn mldrColumnFK, MCDRelEnd mcdRelEndParent, MLDRColumn mldrColumnPK){
        Preferences preferences = PreferencesManager.instance().preferences();

        MDROrderBuildString orderColFk = new  MDROrderBuildString();
        orderColFk.setFormat(preferences.getMDR_COLUMN_FK_NAME_FORMAT());
        orderColFk.setColName(mldrColumnPK.getName());
        if (mldrColumnPK.isFk() && preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR()){
            orderColFk.setParentTableShortName("");
            orderColFk.setParentRoleName("");
            orderColFk.setParentRoleSep("");
            orderColFk.setParentSep("");
        } else {
            orderColFk.setParentTableShortName(
                    mldrColumnPK.getMDRTableParent().getShortName());
            orderColFk.setParentSep(preferences.getMDR_PARENT_NAME_SEP_FORMAT());
            String roleParent = mcdRelEndParent.getNameNoFreeOrNameRelation();
            orderColFk.setParentRoleName(roleParent);
            if (StringUtils.isEmpty(roleParent) ) {
                // Le role parent est inexistant pour les généralisations-spécialisations?
                orderColFk.setParentRoleSep("");
            } else {
                orderColFk.setParentRoleSep(preferences.getMDR_PARENT_ROLE_NAME_SEP_FORMAT());
            }
        }

        String nameColFk;

        try {
            nameColFk = orderColFk.buildString();
        } catch(OrderBuildNameException e){
            String message = MessagesBuilder.getMessagesProperty("mdrcolfk.build.name.error",
                    new String[] {mldrColumnPK.getMDRTableParent().getName(),
                            mldrColumnPK.getName(),
                            mldrColumnFK.getMDRTableParent().getName(),
                            mcdRelEndParent.getNameNoFreeOrNameRelation()});
            throw new CodeApplException(message, e);
        }

        if (mldrColumnPK.isFk() && preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR()){
            Integer indice = null;
            String racine = nameColFk;


            Pattern pattern = Pattern.compile(Preferences.MDR_COLUMN_FK_NAME_INDICE_REGEXPR);
            //Matcher matcher = pattern.matcher(nameColFk);
            Matcher matcher = pattern.matcher(nameColFk);
            if(matcher.find()){
                String differenciation = matcher.group();
                racine = extractRoot(nameColFk, differenciation);
            }

            if (preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_FK)){
                //TODO-0 calculer l'indice de FK !
                //indice = indiceFK;
            }

            if (preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_1)){
                int nbRacinesEqv = nbColumnFKByRoot(racine, mldrColumnFK.getMDRTableParent());
                if (nbRacinesEqv >= 0) indice = nbRacinesEqv + 1;
            }

            if (preferences.getMDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF().equals(Preferences.MDR_PREF_COLUMN_FK_ONE_ANCESTOR_DIFF_INDICE_START_2)){
                int nbRacinesEqv = nbColumnFKByRoot(racine,  mldrColumnFK.getMDRTableParent());
                if (nbRacinesEqv >= 1) indice = nbRacinesEqv + 1;
            }

            if (indice != null){
                nameColFk = racine + preferences.getMDR_COLUMN_FK_NAME_INDICE_SEP() + indice.toString();
            }

        }

        return nameColFk;


    }


    private static String extractRoot(String nameColumn, String differenciation){
        return nameColumn.substring(0, nameColumn.length()- differenciation.length());

    }


    private static int nbColumnFKByRoot(String rootNew, MDRTable mdrTableParent){
        int resultat = 0;
        Pattern pattern = Pattern.compile(Preferences.MDR_COLUMN_FK_NAME_INDICE_REGEXPR);
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



}
