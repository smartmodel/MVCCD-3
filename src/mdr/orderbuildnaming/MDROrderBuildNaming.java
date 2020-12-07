package mdr.orderbuildnaming;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.*;
import main.MVCCDElement;
import main.MVCCDElementConvert;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDROrderBuildNaming {

    private String format;
    private Integer formatUserMarkerLengthMax ;
    private MDRNamingLength namingLength;
    //private Integer lengthMax ;
    private MDROrderBuildTargets targetNaming ; // Cible de nommage table, column ...


    private MDROrderWordTableName tableName;
    private MDROrderWordTableShortName tableShortName;
    private MDROrderWordTableShortName tableShortNameA;
    private MDROrderWordTableShortName tableShortNameB;
    private MDROrderWordTableShortNameParent tableShortNameParent;
    private MDROrderWordTableShortNameChild tableShortNameChild;

    private MDROrderWordAttrName attrName;
    private MDROrderWordColName colName;
    private MDROrderWordColDerived colDerived;

    private MDROrderWordAssociationShortName assShortName;

    private MDROrderWordRoleShortName roleShortNameA;
    private MDROrderWordRoleShortName roleShortNameB;
    private MDROrderWordRoleShortNameParent roleShortNameParent;

    private MDROrderWordPEA pea;

    private MDROrderWordIndiceColFK indColFK;
    private MDROrderWordIndiceConstFK indConstFK;
    private MDROrderWordIndiceTableNN indTableNN;
    private MDROrderWordTableSep tableSep;
    private MDROrderWordPEASep peaSep;
    private MDROrderWordRoleSep roleSep;



    public MDROrderBuildNaming(MDRNamingLength namingLength) {
        this.namingLength = namingLength;
        init();
    }

    public void init() {
        tableName = new MDROrderWordTableName(Preferences.MDR_TABLE_NAME_WORD);
        tableShortName  = new MDROrderWordTableShortName(Preferences.MDR_TABLE_SHORT_NAME_WORD);
        tableShortNameA = new MDROrderWordTableShortName(Preferences.MDR_TABLE_SHORT_NAME_A_WORD);
        tableShortNameB = new MDROrderWordTableShortName(Preferences.MDR_TABLE_SHORT_NAME_B_WORD);
        tableShortNameParent = new MDROrderWordTableShortNameParent(Preferences.MDR_TABLE_SHORT_NAME_PARENT_WORD);
        tableShortNameChild = new MDROrderWordTableShortNameChild(Preferences.MDR_TABLE_SHORT_NAME_CHILD_WORD);

        attrName = new MDROrderWordAttrName(Preferences.MDR_ATTR_NAME_WORD);
        colName = new MDROrderWordColName(Preferences.MDR_COL_NAME_WORD);
        colDerived = new MDROrderWordColDerived(Preferences.MDR_COLUMN_DERIVED_WORD);

        assShortName = new MDROrderWordAssociationShortName(Preferences.MDR_ASS_SHORT_NAME_WORD);

        roleShortNameA = new MDROrderWordRoleShortName(Preferences.MDR_ROLE_SHORT_NAME_A_WORD);
        roleShortNameB = new MDROrderWordRoleShortName(Preferences.MDR_ROLE_SHORT_NAME_B_WORD);
        roleShortNameParent = new MDROrderWordRoleShortNameParent(Preferences.MDR_ROLE_SHORT_NAME_PARENT_WORD);

        pea = new MDROrderWordPEA(Preferences.MDR_PEA_SHORT_NAME_WORD);

        indColFK= new MDROrderWordIndiceColFK(Preferences.MDR_INDICE_COL_FK_WORD);
        indConstFK= new MDROrderWordIndiceConstFK(Preferences.MDR_INDICE_CONST_FK_WORD);
        indTableNN = new MDROrderWordIndiceTableNN(Preferences.MDR_INDICE_TABLENN_WORD);
        tableSep = new MDROrderWordTableSep(Preferences.MDR_TABLE_SEP_WORD);
        roleSep = new MDROrderWordRoleSep(Preferences.MDR_ROLE_SEP_WORD);
        peaSep = new MDROrderWordPEASep(Preferences.MDR_PEA_SEP_WORD);

    }


    public String buildNaming() throws OrderBuildNameException{

        Preferences preferences = PreferencesManager.instance().preferences();

        try {
            Pattern pattern = Pattern.compile(Preferences.MDR_WORDS_PATTERN);
            //Pattern pattern = Pattern.compile("\\{[a-zA-Z_]+\\}");

            //TODO-0
            // vérifier que les constantes inclues dans le format ne fassent pas dépasser la taille maximale prévue
            // IL faut passer la taille prévue maximale en paramètre
            // Ensuite il faut calculer la taille effective maximale avec les constants et les diffèrents champs
            // La taille maximale effective ne doit pas dépasser la taille prévue
            Matcher matcher = pattern.matcher(this.getFormat());
            String newName = "";
            int sizeMarkers = 0 ;
            int sizeMarkersLimit = 0 ;
            if (formatUserMarkerLengthMax != null){
                sizeMarkersLimit = getFormatUserMarkerLengthMax();
            }
            while (matcher.find()) {
                String mg = matcher.group().substring(1, matcher.group().length() - 1);
                String value = null;

                if (mg.equals(Preferences.MDR_TABLE_NAME_WORD)) {
                    value = pushValue(tableName);

                } else if (mg.equals(Preferences.MDR_TABLE_SHORT_NAME_WORD)) {
                    value = pushValue(tableShortName);

                } else if (mg.equals(Preferences.MDR_TABLE_SHORT_NAME_A_WORD)) {
                    value = pushValue(tableShortNameA);

                } else if (mg.equals(Preferences.MDR_TABLE_SHORT_NAME_B_WORD)) {
                    value = pushValue(tableShortNameB);

                } else if (mg.equals(Preferences.MDR_TABLE_SHORT_NAME_PARENT_WORD)) {
                    value = pushValue(tableShortNameParent);

                } else if (mg.equals(Preferences.MDR_TABLE_SHORT_NAME_CHILD_WORD)) {
                    value = pushValue(tableShortNameChild);

                } else if (mg.equals(Preferences.MDR_ATTR_NAME_WORD)) {
                    value = pushValue(attrName);

                } else if (mg.equals(Preferences.MDR_COL_NAME_WORD)) {
                    value = pushValue(colName);

                } else if (mg.equals(Preferences.MDR_COLUMN_DERIVED_WORD)) {
                    value = pushValue(colDerived);

                } else if (mg.equals(Preferences.MDR_ASS_SHORT_NAME_WORD)) {
                    value = pushValue(assShortName);

                } else if (mg.equals(Preferences.MDR_ROLE_SHORT_NAME_A_WORD)) {
                    value = pushValue(roleShortNameA);

                } else if (mg.equals(Preferences.MDR_ROLE_SHORT_NAME_B_WORD)) {
                    value = pushValue(roleShortNameB);

                } else if (mg.equals(Preferences.MDR_ROLE_SHORT_NAME_PARENT_WORD)) {
                    value = pushValue(roleShortNameParent);

                } else if (mg.equals(Preferences.MDR_PEA_SHORT_NAME_WORD)) {
                    value = pushValue(pea);

                } else if (mg.equals(Preferences.MDR_TABLE_SEP_WORD)) {
                    value = pushValue(tableSep);

                } else if (mg.equals(Preferences.MDR_ROLE_SEP_WORD)) {
                    value = pushValue(roleSep);

                } else if (mg.equals(Preferences.MDR_PEA_SEP_WORD)) {
                    value = pushValue(peaSep);

                } else if (mg.equals(Preferences.MDR_INDICE_COL_FK_WORD)) {
                    value = pushValue(indColFK);

                } else if (mg.equals(Preferences.MDR_INDICE_CONST_FK_WORD)) {
                    value = pushValue(indConstFK);

                } else if (mg.equals(Preferences.MDR_INDICE_TABLENN_WORD)) {
                    value = pushValue(indTableNN);

                } else {
                    // Marqueurs libres
                    // Marqueurs libres non autorisés
                    if (sizeMarkersLimit <= 0){
                        throw new OrderBuildNameMarkerNotAuthorizedException();
                    }
                    // Taille maximale des marqueurs libres dépassés
                    sizeMarkers += mg.length();
                    if (sizeMarkers > sizeMarkersLimit){
                        throw new OrderBuildNameMarkerSizeLimitException();
                    }
                    value = mg;

                }

                newName += value;
            }


            // Contrôle de la taille du nom
            int lengthMax = namingLength.getLength();
            if (newName.length() > lengthMax) {
                limitSize(newName);
            }
            return newName;

        } catch (OrderBuildNameTableNNSizeLimitException e){
            // Calcul du nom avec la variante indicée
            format = preferences.getMDR_TABLE_NN_NAME_INDICE_FORMAT();
            String nameNN = tableShortNameA.getValue() + tableSep.getValue() + tableShortNameB.getValue() ;

            //TODO-0
            // A terminer le code lorsque la génération des tables n:n sera réalisée!
            // Brohters devra être passé en paramètre par la f(9 de build initiale
            // Il faudra vérifier l'unicité dans les 2 sens !
            ArrayList<MVCCDElement> brothers = new ArrayList<MVCCDElement>();
            indTableNN.setValue(nameNN, brothers);

            return buildNaming();

        } catch (OrderBuildNameWordNotAuthorizedException e) {
            String message = MessagesBuilder.getMessagesProperty("mdr.build.name.word.notauthorized.error",
                    new String[]{e.getMessage(), format, targetNaming.getMessageOfTarget()});
            throw new OrderBuildNameException(message, e);
        } catch (OrderBuildNameMarkerNotAuthorizedException e){
            String message = MessagesBuilder.getMessagesProperty("mdr.build.name.markers.notauthorized.error",
                    new String[]{format, targetNaming.getMessageOfTarget()});
            throw new OrderBuildNameException(message, e);
        } catch (OrderBuildNameMarkerSizeLimitException e){
            String message = MessagesBuilder.getMessagesProperty("mdr.build.name.markers.sizeLimit.error",
                    new String[]{format, targetNaming.getMessageOfTarget()});
            throw new OrderBuildNameException(message, e);
        } catch (Exception e) {
           throw new OrderBuildNameException(e);
        }
    }



    private String pushValue (MDROrderWord orderWord) throws OrderBuildNameWordNotAuthorizedException {
        if (orderWord.getValue() != null) {
           return  orderWord.getValue();
        } else {
            throw new OrderBuildNameWordNotAuthorizedException(orderWord.getName());
        }
    }

    private void limitSize(String newName) {
        if (newName.length() > namingLength.getLength()){
            boolean c1 = targetNaming == MDROrderBuildTargets.TABLE;
            boolean c2 = targetNaming == MDROrderBuildTargets.TABLENN;
            boolean c3 = targetNaming == MDROrderBuildTargets.TABLENNINDICE;
            if (c1 ){
                limitCodeError(newName);
           }
            if (c2 || c3){
                limitSizeTableNN(newName);
            }

        }
    }

    private void limitSizeTableNN(String newName){
        if (namingLength == MDRNamingLength.LENGTH30){
            if (targetNaming == MDROrderBuildTargets.TABLENN){
                throw new OrderBuildNameTableNNSizeLimitException();
            }
            if (targetNaming == MDROrderBuildTargets.TABLENNINDICE){
                newName.replace(Preferences.MDR_SEPARATOR, "");
            }
        } else {
           limitCodeError(newName);
        }
    }

    private void limitCodeError(String newName) {
        int gap = namingLength.getLength() - newName.length();
        throw new CodeApplException("MDROrderBuildNaming - " + targetNaming.getText() +
                " - Le nom calculé " + newName + " dépasse de " + gap +
                "caractères la taille maximale autorisée de " + namingLength.getLength());
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getFormatUserMarkerLengthMax() {
        return formatUserMarkerLengthMax;
    }

    public void setFormatUserMarkerLengthMax(Integer formatUserMarkerLengthMax) {
        this.formatUserMarkerLengthMax = formatUserMarkerLengthMax;
    }

    public MDRNamingLength getNamingLength() {
        return namingLength;
    }

    public MDROrderBuildTargets getTargetNaming() {
        return targetNaming;
    }

    public void setTargetNaming(MDROrderBuildTargets targetNaming) {
        this.targetNaming = targetNaming;
    }

    public MDROrderWordTableName getTableName() {
        return tableName;
    }

    public void setTableName(MDROrderWordTableName tableName) {
        this.tableName = tableName;
    }

    public MDROrderWordTableShortName getTableShortName() {
        return tableShortName;
    }

    public void setTableShortName(MDROrderWordTableShortName tableShortName) {
        this.tableShortName = tableShortName;
    }

    public MDROrderWordAttrName getAttrName() {
        return attrName;
    }

    public void setAttrName(MDROrderWordAttrName attrName) {
        this.attrName = attrName;
    }

    public MDROrderWordColDerived getColDerived() {
        return colDerived;
    }

    public void setColDerived(MDROrderWordColDerived colDerived) {
        this.colDerived = colDerived;
    }

    public MDROrderWordPEA getPea() {
        return pea;
    }

    public void setPea(MDROrderWordPEA pea) {
        this.pea = pea;
    }

    public MDROrderWordPEASep getPeaSep() {
        return peaSep;
    }

    public void setPeaSep(MDROrderWordPEASep peaSep) {
        this.peaSep = peaSep;
    }

    public MDROrderWordTableShortNameParent getTableShortNameParent() {
        return tableShortNameParent;
    }

    public void setTableShortNameParent(MDROrderWordTableShortNameParent tableShortNameParent) {
        this.tableShortNameParent = tableShortNameParent;
    }

    public MDROrderWordTableShortNameChild getTableShortNameChild() {
        return tableShortNameChild;
    }

    public void setTableShortNameChild(MDROrderWordTableShortNameChild tableShortNameChild) {
        this.tableShortNameChild = tableShortNameChild;
    }

    public MDROrderWordTableSep getTableSep() {
        return tableSep;
    }

    public void setTableSep(MDROrderWordTableSep tableSep) {
        this.tableSep = tableSep;
    }

    public MDROrderWordRoleShortNameParent getRoleShortNameParent() {
        return roleShortNameParent;
    }

    public void setRoleShortNameParent(MDROrderWordRoleShortNameParent roleShortNameParent) {
        this.roleShortNameParent = roleShortNameParent;
    }

    public MDROrderWordRoleSep getRoleSep() {
        return roleSep;
    }

    public void setRoleSep(MDROrderWordRoleSep roleSep) {
        this.roleSep = roleSep;
    }

    public MDROrderWordColName getColName() {
        return colName;
    }

    public void setColName(MDROrderWordColName colName) {
        this.colName = colName;
    }

    public MDROrderWordIndiceColFK getIndColFK() {
        return indColFK;
    }

    public void setIndColFK(MDROrderWordIndiceColFK indColFK) {
        this.indColFK = indColFK;
    }

    public MDROrderWordIndiceConstFK getIndConstFK() {
        return indConstFK;
    }

    public MDROrderWordIndiceTableNN getIndTableNN() {
        return indTableNN;
    }

    public void setIndConstFK(MDROrderWordIndiceConstFK indConstFK) {
        this.indConstFK = indConstFK;
    }
}