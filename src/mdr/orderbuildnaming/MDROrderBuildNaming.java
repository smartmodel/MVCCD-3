package mdr.orderbuildnaming;

import exceptions.CodeApplException;
import exceptions.orderbuildnaming.*;
import mdr.MDRNamingLength;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDROrderBuildNaming {

    private String format;
    private Integer formatUserMarkerLengthMax ;
    private MDRNamingLength namingLength;
    //private MDRNamingFormat namingFormat = null; Pas de formattage à ce niveau

    //private Integer lengthMax ;
    private MDROrderBuildTargets targetNaming ; // Cible de nommage table, column ...


    private MDROrderWordTableName tableName;
    private MDROrderWordTableShortName tableShortName;
    private MDROrderWordTableShortNameA tableShortNameA;
    private MDROrderWordTableShortNameB tableShortNameB;
    private MDROrderWordTableShortNameParent tableShortNameParent;
    private MDROrderWordTableShortNameChild tableShortNameChild;

    private MDROrderWordAttrName attrName;
    private MDROrderWordAttrShortName attrShortName;
    private MDROrderWordColName colName;
    private MDROrderWordColNameOneAncestor colNameOneAncestor;
    private MDROrderWordColDerived colDerived;

    private MDROrderWordAssociationShortName assShortName;

    private MDROrderWordRoleShortNameA roleShortNameA;
    private MDROrderWordRoleShortNameB roleShortNameB;
    private MDROrderWordRoleShortNameParent roleShortNameParent;

    private MDROrderWordPEA pea;

    private MDROrderWordUniqueNatureName uniqueNature;
    private MDROrderWordUniqueName uniqueName;
    private MDROrderWordUniqueShortName uniqueShortName;

    private MDROrderWordIndiceColFK indColFK;  // Colonne de FK
    private MDROrderWordIndiceConstFK indConstFK;  //Contrainte de FK
    private MDROrderWordIndiceTableNN indTableNN;

    private MDROrderWordTableSep tableSep;
    private MDROrderWordPEASep peaSep;
    private MDROrderWordRoleSep roleSep;
    private MDROrderWordFKIndSep fkIndSep;
    private MDROrderWordUniqueNatureSep uniqueNatSep;

    private MDROrderWordTypeTriggerMarker typeTriggerMarker ;
    private MDROrderWordMPDRColumnName mpdrColumnName ;




    public MDROrderBuildNaming(MDRNamingLength namingLength) {
        this.namingLength = namingLength;
        init();
    }

    public void init() {
        tableName = new MDROrderWordTableName(Preferences.MDR_TABLE_NAME_WORD);
        tableShortName  = new MDROrderWordTableShortName(Preferences.MDR_TABLE_SHORT_NAME_WORD);
        tableShortNameA = new MDROrderWordTableShortNameA(Preferences.MDR_TABLE_SHORT_NAME_A_WORD);
        tableShortNameB = new MDROrderWordTableShortNameB(Preferences.MDR_TABLE_SHORT_NAME_B_WORD);
        tableShortNameParent = new MDROrderWordTableShortNameParent(Preferences.MDR_TABLE_SHORT_NAME_PARENT_WORD);
        tableShortNameChild = new MDROrderWordTableShortNameChild(Preferences.MDR_TABLE_SHORT_NAME_CHILD_WORD);

        attrName = new MDROrderWordAttrName(Preferences.MDR_ATTR_NAME_WORD);
        attrShortName = new MDROrderWordAttrShortName(Preferences.MDR_ATTR_NAME_WORD);
        colName = new MDROrderWordColName(Preferences.MDR_COL_NAME_WORD);
        colNameOneAncestor = new MDROrderWordColNameOneAncestor(Preferences.MDR_COL_NAME_ONE_ANCESTOR_WORD);
        colDerived = new MDROrderWordColDerived(Preferences.MDR_COLUMN_DERIVED_WORD);

        assShortName = new MDROrderWordAssociationShortName(Preferences.MDR_ASS_SHORT_NAME_WORD);

        roleShortNameA = new MDROrderWordRoleShortNameA(Preferences.MDR_ROLE_SHORT_NAME_A_WORD);
        roleShortNameB = new MDROrderWordRoleShortNameB(Preferences.MDR_ROLE_SHORT_NAME_B_WORD);
        roleShortNameParent = new MDROrderWordRoleShortNameParent(Preferences.MDR_ROLE_SHORT_NAME_PARENT_WORD);

        pea = new MDROrderWordPEA(Preferences.MDR_PEA_SHORT_NAME_WORD);

        uniqueNature = new MDROrderWordUniqueNatureName(Preferences.MDR_UNIQUE_NATURE_NAME_WORD);
        uniqueName = new MDROrderWordUniqueName(Preferences.MDR_UNIQUE_NAME_WORD);
        uniqueShortName = new MDROrderWordUniqueShortName(Preferences.MDR_UNIQUE_SHORT_NAME_WORD);

        indColFK= new MDROrderWordIndiceColFK(Preferences.MDR_INDICE_COL_FK_WORD);
        indConstFK= new MDROrderWordIndiceConstFK(Preferences.MDR_INDICE_CONST_FK_WORD);
        indTableNN = new MDROrderWordIndiceTableNN(Preferences.MDR_INDICE_TABLENN_WORD);

        tableSep = new MDROrderWordTableSep(Preferences.MDR_TABLE_SEP_WORD);
        roleSep = new MDROrderWordRoleSep(Preferences.MDR_ROLE_SEP_WORD);
        fkIndSep = new MDROrderWordFKIndSep(Preferences.MDR_FKIND_SEP_WORD);
        peaSep = new MDROrderWordPEASep(Preferences.MDR_PEA_SEP_WORD);
        uniqueNatSep = new MDROrderWordUniqueNatureSep(Preferences.MDR_UNIQUE_NATURE_SEP_WORD);

        typeTriggerMarker = new MDROrderWordTypeTriggerMarker(Preferences.MPDR_TYPE_TRIGGER_MARKER_WORD);
        mpdrColumnName = new MDROrderWordMPDRColumnName(Preferences.MPDR_COLUMN_NAME_WORD);
    }


    public String buildNaming() throws OrderBuildNameException {

        Preferences preferences = PreferencesManager.instance().preferences();
        String userMarkersDetected = "";

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
            int sizeMarkers = 0;
            int sizeMarkersLimit = 0;
            if (formatUserMarkerLengthMax != null) {
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

                } else if (mg.equals(Preferences.MDR_ATTR_SHORT_NAME_WORD)) {
                    value = pushValue(attrShortName);

                } else if (mg.equals(Preferences.MDR_COL_NAME_WORD)) {
                    value = pushValue(colName);

                } else if (mg.equals(Preferences.MDR_COL_NAME_ONE_ANCESTOR_WORD)) {
                    value = pushValue(colNameOneAncestor);

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

                } else if (mg.equals(Preferences.MDR_FKIND_SEP_WORD)) {
                    value = pushValue(fkIndSep);

                } else if (mg.equals(Preferences.MDR_PEA_SEP_WORD)) {
                    value = pushValue(peaSep);

                } else if (mg.equals(Preferences.MDR_INDICE_COL_FK_WORD)) {
                    value = pushValue(indColFK);

                } else if (mg.equals(Preferences.MDR_INDICE_CONST_FK_WORD)) {
                   value = pushValue(indConstFK);

                } else if (mg.equals(Preferences.MDR_INDICE_TABLENN_WORD)) {
                    value = pushValue(indTableNN);

                } else if (mg.equals(Preferences.MDR_UNIQUE_NATURE_NAME_WORD)) {
                    value = pushValue(uniqueNature);

                } else if (mg.equals(Preferences.MDR_UNIQUE_NAME_WORD)) {
                    value = pushValue(uniqueName);

                } else if (mg.equals(Preferences.MDR_UNIQUE_SHORT_NAME_WORD)) {
                    value = pushValue(uniqueShortName);

                } else if (mg.equals(Preferences.MDR_UNIQUE_NATURE_SEP_WORD)) {
                    value = pushValue(uniqueNatSep);

                } else if (mg.equals(Preferences.MPDR_TYPE_TRIGGER_MARKER_WORD)) {
                    value = pushValue(typeTriggerMarker);

                } else if (mg.equals(Preferences.MPDR_COLUMN_NAME_WORD)) {
                    value = pushValue(mpdrColumnName);

                } else {
                    // Marqueurs libres
                    value = mg;
                    userMarkersDetected += Preferences.MDR_WORDS_BEGIN + value + Preferences.MDR_WORDS_END;

                    // Marqueurs libres non autorisés
                    if (sizeMarkersLimit <= 0) {
                        throw new OrderBuildNameMarkerNotAuthorizedException(userMarkersDetected);
                    }
                    // Taille maximale des marqueurs libres dépassés
                    sizeMarkers += mg.length();
                    if (sizeMarkers > sizeMarkersLimit) {
                        throw new OrderBuildNameMarkerSizeLimitException();
                    }
                }
                newName = newName + value;
            }

            // Contrôle de la taille du nom
            newName = limitSize(newName);

            // Provoquer une erreur pour faire un test d'alternative
            /*
            if (targetNaming == MDROrderBuildTargets.TABLENN ){
                throw new OrderBuildNameTableNNSizeLimitException();
            }
            */

            // Formattage (lowercase, uppercase...)
            // Pas de formattage - Les données doivent rester brutes pour permettre les retours en arrière
            /*
            if (namingFormat != null) {
                newName = MDRModelService.formatNaming(newName, namingFormat);
            } else {
                throw new CodeApplException("Le formatage (uppercase, lowercas...) n'est pas défini pour :  "+ format);
            }

             */
            return newName;

        }
        catch (OrderBuildNameTableNNSizeLimitException e) {
            // Calcul du nom avec la variante indicée
            format = preferences.getMDR_TABLE_NN_NAME_INDICE_FORMAT();
            targetNaming = MDROrderBuildTargets.TABLENNINDICE;
            return buildNaming();
       }

        catch (OrderBuildNameColumnAttrSizeLimitException e){
            // Calcul du nom avec le shortName
            format = preferences.getMDR_COLUMN_ATTR_SHORT_NAME_FORMAT();
            targetNaming = MDROrderBuildTargets.COLUMNATTRSHORTNAME;
            return buildNaming();
        }

        catch (OrderBuildNameColumnFKSizeLimitException e){
            // Calcul du nom avec le shortName
            format = preferences.getMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT();
            targetNaming = MDROrderBuildTargets.COLUMNFKONEANCESTOR;
            return buildNaming();
        }
        catch (OrderBuildNameFKSizeLimitException e){
            // Calcul du nom avec la variante sans rôle
            format = preferences.getMDR_FK_NAME_WITHOUT_ROLE_FORMAT();
            targetNaming = MDROrderBuildTargets.FKWITHOUTROLE;
            return buildNaming();
        }
        catch (OrderBuildNameUniqueSizeLimitException e){
            // Calcul du nom avec le shortName
            format = preferences.getMDR_UNIQUE_NAME_MAX30_FORMAT();
            targetNaming = MDROrderBuildTargets.UNIQUEMAX30;
            return buildNaming();
        }

        catch (OrderBuildNameWordNullException e) {
            String message = MessagesBuilder.getMessagesProperty("mdr.build.name.word.null.error",
                    new String[]{e.getMessage(), format, targetNaming.getMessageOfTarget()});
            throw new OrderBuildNameException(message, e);
        }
        catch (OrderBuildNameMarkerNotAuthorizedException e){
            String message = MessagesBuilder.getMessagesProperty("mdr.build.name.markers.notauthorized.error",
                    new String[]{userMarkersDetected, format, targetNaming.getMessageOfTarget()});
            throw new OrderBuildNameException(message, e);
        }
        catch (OrderBuildNameMarkerSizeLimitException e){
            String message = MessagesBuilder.getMessagesProperty("mdr.build.name.markers.sizeLimit.error",
                    new String[]{userMarkersDetected, format, targetNaming.getMessageOfTarget()});
            throw new OrderBuildNameException(message, e);
        }
        catch (OrderBuildNameException e) {
            // Renvoyer le message généré lors de la création de l'exception
            // Si le message n'est pas renvoyé l'affichage de la source de l'erreur sera effectué
            throw new OrderBuildNameException(e.getMessage(),e);
        }
        catch (Exception e) {
            throw new OrderBuildNameException(e);
        }
    }




    private String pushValue (MDROrderWord orderWord) throws OrderBuildNameWordNullException {
        if (orderWord.getValue() != null) {
            return  orderWord.getValue();
        } else {
            throw new OrderBuildNameWordNullException(orderWord.getName());
        }
    }

    private String limitSize(String newName) {
        if (newName.length() > namingLength.getLength()) {
            //boolean table = targetNaming == MDROrderBuildTargets.TABLE;
            boolean tableNN = targetNaming == MDROrderBuildTargets.TABLENN;
            boolean tableNNInd = targetNaming == MDROrderBuildTargets.TABLENNINDICE;
            boolean colAttr = targetNaming == MDROrderBuildTargets.COLUMNATTR;
            boolean colAttrShortName = targetNaming == MDROrderBuildTargets.COLUMNATTRSHORTNAME;
            boolean colFKFromEntityInd = targetNaming == MDROrderBuildTargets.COLUMNFKFROMENTITYIND;
            boolean colFKFromEntityNoInd = targetNaming == MDROrderBuildTargets.COLUMNFKFROMENTITYNOIND;
            boolean colFKOneAncestor = targetNaming == MDROrderBuildTargets.COLUMNFKONEANCESTOR;
            boolean  uniqueWithName = targetNaming == MDROrderBuildTargets.UNIQUE;

            boolean pk = targetNaming == MDROrderBuildTargets.PK;
            boolean pkNN = targetNaming == MDROrderBuildTargets.PKNN;
            boolean pkNNInd = targetNaming == MDROrderBuildTargets.PKNNINDICE;
            boolean fk = targetNaming == MDROrderBuildTargets.FK;
            boolean fkInd = targetNaming == MDROrderBuildTargets.FKWITHOUTROLE;

            int lengthMax = namingLength.getLength();

            if (newName.length() > lengthMax - Preferences.MDR_INDICE_CONST_FK_LENGTH) {
                if (colFKFromEntityNoInd || colFKFromEntityInd){
                    // Suffisament de place pour un futur indice
                    return limitSizeColFK(newName);
                }
            }

            if (newName.length() > lengthMax) {
                if (tableNN || tableNNInd) {
                    return limitSizeTableNN(newName);
                } else if (pkNN || pkNNInd) {
                    return limitSizeTableNN(newName);
                } else if (colAttr || colAttrShortName) {
                    return limitSizeColumnAttr(newName);
                } else if (fk || fkInd) {
                    return limitSizeFK(newName);
                } else if (uniqueWithName) {
                    return limitUniqueWithName(newName);
                } else {
                    limitCodeError(newName);
                }
            } else {
                return newName ;
            }

        }
        return newName;
    }



    private void limitCodeError(String newName) {
        int gap =  newName.length() - namingLength.getLength() ;
        throw new CodeApplException(targetNaming.getText() +
                " - Le nom calculé " + newName + " dépasse de " + gap +
                " caractères la taille maximale autorisée de " + namingLength.getLength());
    }

    private String limitComputedCodeError(String newName){
        if (newName.length() > namingLength.getLength()) {
            throw new CodeApplException(targetNaming.getText() +
                    " - Le nom calculé " + newName + " n'a pu être limité qu'à " + newName.length() +
                    " caractères au lieu de " + namingLength.getLength());
        }
        return newName;
    }

    private String limitSizeColumnAttr(String newName) {
        if (namingLength == MDRNamingLength.LENGTH30) {
            if (targetNaming == MDROrderBuildTargets.COLUMNATTR) {
                if (StringUtils.isNotEmpty(attrShortName.getValue())) {
                    // Relancement du calcul avec shortname de l'attribut
                    throw new OrderBuildNameColumnAttrSizeLimitException();
                } else {
                    String message = MessagesBuilder.getMessagesProperty("mdr.build.column.attr.sizeLimit.error",
                            new String[] { "" + (newName.length() - namingLength.getLength()), namingLength.getLength().toString()});
                    message += System.lineSeparator();
                    message +=  MessagesBuilder.getMessagesProperty("mdr.build.column.attr.sizeLimit.error.advice",
                            MessagesBuilder.getMessagesProperty("menu.preferences.mcd"));
                    throw new OrderBuildNameException(message);
                }
            }
            if (targetNaming == MDROrderBuildTargets.COLUMNATTRSHORTNAME) {
                limitCodeError(newName);;
            }
        } else {
            limitCodeError(newName);
        }
        throw new CodeApplException("Erreur limitSizeColumnAttr");
    }

    private String limitSizeTableNN(String newName){
        String error = "Erreur limitSizeTableNN";
        if (namingLength == MDRNamingLength.LENGTH30){
            if (targetNaming == MDROrderBuildTargets.TABLENN){
                throw new OrderBuildNameTableNNSizeLimitException();
            }
            else if (targetNaming == MDROrderBuildTargets.TABLENNINDICE){
                newName = newName.replace(Preferences.MDR_SEPARATOR, "");
                if (newName.length() <= MDRNamingLength.LENGTH30.getLength()) {
                    return newName;
                } else {
                    limitComputedCodeError(newName);
                }
            } else {
                throw new CodeApplException(error);
            }
        } else {
            limitCodeError(newName);
        }
        throw new CodeApplException(error);
    }

    private String limitSizePKNN(String newName){
        String error = "Erreur limitSizePKNN";
        if (namingLength == MDRNamingLength.LENGTH30){
            if (targetNaming == MDROrderBuildTargets.PKNN){
                throw new OrderBuildNameTableNNSizeLimitException();
            }
            else if (targetNaming == MDROrderBuildTargets.PKNNINDICE){
                newName = newName.replace(Preferences.MDR_SEPARATOR, "");
                if (newName.length() <= MDRNamingLength.LENGTH30.getLength()) {
                    return newName;
                } else {
                    limitComputedCodeError(newName);
                }
            } else {
                throw new CodeApplException(error);
            }
        } else {
            limitCodeError(newName);
        }
        throw new CodeApplException(error);
    }

    private String limitSizeFK(String newName){
        String error = "Erreur limitSizeFK";
        if (namingLength == MDRNamingLength.LENGTH30){
            if (targetNaming == MDROrderBuildTargets.FK){
                throw new OrderBuildNameFKSizeLimitException();
            }
            else if (targetNaming == MDROrderBuildTargets.FKWITHOUTROLE){
                newName = newName.replace(Preferences.MDR_SEPARATOR, "");
                if (newName.length() <= MDRNamingLength.LENGTH30.getLength()) {
                    return newName;
                } else {
                    limitComputedCodeError(newName);
                }
            }
            else {
                throw new CodeApplException(error);
            }
        } else {
            limitCodeError(newName);
        }
        throw new CodeApplException(error);
    }


    private String limitSizeColFK(String newName) {
        throw new OrderBuildNameColumnFKSizeLimitException();
    }

    private String limitUniqueWithName(String newName) {
        throw new OrderBuildNameUniqueSizeLimitException();
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

    /*
    public MDRNamingFormat getNamingFormat() {
        return namingFormat;
    }

    public void setNamingFormat(MDRNamingFormat namingFormat) {
        this.namingFormat = namingFormat;
    }

     */

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

    public MDROrderWordTableShortNameA getTableShortNameA() {
        return tableShortNameA;
    }

    public void setTableShortNameA(MDROrderWordTableShortNameA tableShortNameA) {
        this.tableShortNameA = tableShortNameA;
    }

    public MDROrderWordTableShortNameB getTableShortNameB() {
        return tableShortNameB;
    }

    public void setTableShortNameB(MDROrderWordTableShortNameB tableShortNameB) {
        this.tableShortNameB = tableShortNameB;
    }

    public MDROrderWordAttrName getAttrName() {
        return attrName;
    }

    public void setAttrName(MDROrderWordAttrName attrName) {
        this.attrName = attrName;
    }

    public MDROrderWordAttrShortName getAttrShortName() {
        return attrShortName;
    }

    public void setAttrShortName(MDROrderWordAttrShortName attrShortName) {
        this.attrShortName = attrShortName;
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

    public MDROrderWordUniqueNatureName getUniqueNature() {
        return uniqueNature;
    }

    public void setUniqueNature(MDROrderWordUniqueNatureName uniqueNature) {
        this.uniqueNature = uniqueNature;
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

    public MDROrderWordAssociationShortName getAssShortName() {
        return assShortName;
    }

    public void setAssShortName(MDROrderWordAssociationShortName assShortName) {
        this.assShortName = assShortName;
    }

    public MDROrderWordRoleShortNameParent getRoleShortNameParent() {
        return roleShortNameParent;
    }

    public void setRoleShortNameParent(MDROrderWordRoleShortNameParent roleShortNameParent) {
        this.roleShortNameParent = roleShortNameParent;
    }

    public MDROrderWordRoleShortName getRoleShortNameA() {
        return roleShortNameA;
    }

    public void setRoleShortNameA(MDROrderWordRoleShortNameA roleShortNameA) {
        this.roleShortNameA = roleShortNameA;
    }

    public MDROrderWordRoleShortName getRoleShortNameB() {
        return roleShortNameB;
    }

    public void setRoleShortNameB(MDROrderWordRoleShortNameB roleShortNameB) {
        this.roleShortNameB = roleShortNameB;
    }

    public MDROrderWordRoleSep getRoleSep() {
        return roleSep;
    }

    public void setRoleSep(MDROrderWordRoleSep roleSep) {
        this.roleSep = roleSep;
    }

    public MDROrderWordFKIndSep getFkIndSep() {
        return fkIndSep;
    }

    public void setFkIndSep(MDROrderWordFKIndSep fkIndSep) {
        this.fkIndSep = fkIndSep;
    }

    public MDROrderWordColName getColName() {
        return colName;
    }

    public void setColName(MDROrderWordColName colName) {
        this.colName = colName;
    }

    public MDROrderWordColNameOneAncestor getColNameOneAncestor() {
        return colNameOneAncestor;
    }

    public void setColNameOneAncestor(MDROrderWordColNameOneAncestor colNameOneAncestor) {
        this.colNameOneAncestor = colNameOneAncestor;
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

    public void setIndConstFK(MDROrderWordIndiceConstFK indConstFK) {
        this.indConstFK = indConstFK;
    }

    public void setIndTableNN(MDROrderWordIndiceTableNN indTableNN) {
        this.indTableNN = indTableNN;
    }

    public MDROrderWordIndiceTableNN getIndTableNN() {
        return indTableNN;
    }

    public MDROrderWordUniqueNatureSep getUniqueNatSep() {
        return uniqueNatSep;
    }

    public void setUniqueNatSep(MDROrderWordUniqueNatureSep uniqueNatSep) {
        this.uniqueNatSep = uniqueNatSep;
    }

    public MDROrderWordUniqueName getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(MDROrderWordUniqueName uniqueName) {
        this.uniqueName = uniqueName;
    }

    public MDROrderWordUniqueShortName getUniqueShortName() {
        return uniqueShortName;
    }

    public void setUniqueShortName(MDROrderWordUniqueShortName uniqueShortName) {
        this.uniqueShortName = uniqueShortName;
    }

    public MDROrderWordTypeTriggerMarker getTypeTriggerMarker() {
        return typeTriggerMarker;
    }

    public void setTypeTriggerMarker(MDROrderWordTypeTriggerMarker typeTriggerMarker) {
        this.typeTriggerMarker = typeTriggerMarker;
    }

    public MDROrderWordMPDRColumnName getMpdrColumnName() {
        return mpdrColumnName;
    }

    public void setMpdrColumnName(MDROrderWordMPDRColumnName mpdrColumnName) {
        this.mpdrColumnName = mpdrColumnName;
    }
}
