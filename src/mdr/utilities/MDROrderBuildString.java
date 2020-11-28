package mdr.utilities;

import exceptions.OrderBuildNameException;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDROrderBuildString {

    private String format;
    private Integer lengthMax;

    private String path;
    private String pathSep;
    private String attrName;
    private String tableName;
    private String tableShortName;
    private String indice;
    private String columnsName;
    private String orderedName;
    private String parentTableShortName;
    private String childTableShortName;
    private String roleName;
    private String parentRoleName;
    private String childRoleName;
    private String fkName;
    private String colName;
    private String peaShortName;
    private String peaSep;
    private String parentRoleSep;
    private String parentSep;
    private String childRoleSep;
    private String colDerived;

    public  MDROrderBuildString (){

    }

    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }


    public Integer getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(Integer lengthMax) {
        this.lengthMax = lengthMax;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathSep() {
        return pathSep;
    }

    public void setPathSep(String pathSep) {
        this.pathSep = pathSep;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableShortName() {
        return tableShortName;
    }
    public void setTableShortName(String tableShortName) {
        this.tableShortName = tableShortName;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(String columnsName) {
        this.columnsName = columnsName;
    }

    public String getOrderedName() {
        return orderedName;
    }

    public void setOrderedName(String orderedName) {
        this.orderedName = orderedName;
    }

    public String getParentTableShortName() {
        return parentTableShortName;
    }
    public void setParentTableShortName(String parentTableShortName) {
        this.parentTableShortName = parentTableShortName;
    }
    public String getChildTableShortName() {
        return childTableShortName;
    }
    public void setChildTableShortName(String childTableShortName) {
        this.childTableShortName = childTableShortName;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getParentRoleName() {
        return parentRoleName;
    }
    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }
    public String getChildRoleName() {
        return childRoleName;
    }
    public void setChildRoleName(String childRoleName) {
        this.childRoleName = childRoleName;
    }


    public String getFkName() {
        return fkName;
    }

    public void setFkName(String fkName) {
        this.fkName = fkName;
    }

    public String getColName() {
        return colName;
    }
    public void setColName(String colName) {
        this.colName = colName;
    }



    public String getPeaShortName() {
        return peaShortName;
    }

    public void setPeaShortName(String peaShortName) {
        this.peaShortName = peaShortName;
    }


    public String getPeaSep() {
        return peaSep;
    }

    public void setPeaSep(String peaSep) {
        this.peaSep = peaSep;
    }


    public String getParentRoleSep() {
        return parentRoleSep;
    }

    public void setParentRoleSep(String parentRoleSep) {
        this.parentRoleSep = parentRoleSep;
    }


    public String getParentSep() {
        return parentSep;
    }

    public void setParentSep(String parentSep) {
        this.parentSep = parentSep;
    }


    public String getChildRoleSep() {
        return childRoleSep;
    }

    public void setChildRoleSep(String childRoleSep) {
        this.childRoleSep = childRoleSep;
    }


    public String getColDerived() {
        return colDerived;
    }

    public void setColDerived(String colDerived) {
        this.colDerived = colDerived;
    }

    public String buildString (){

        try {
            Pattern pattern = Pattern.compile(Preferences.MDR_WORDS_PATTERN);
            //Pattern pattern = Pattern.compile("\\{[a-zA-Z_]+\\}");

            //TODO-0
            // vérifier que les constantes inclues dans le format ne fassent pas dépasser la taille maximale prévue
            // IL faut passer la taille prévue maximale en paramètre
            // Ensuite il faut calculer la taille effective maximale avec les constants et les diffèrents champs
            // La taille maximale effective ne doit pas dépasser la taille prévue
            Matcher matcher = pattern.matcher(this.getFormat());
            String colFormat = "";
            while (matcher.find()) {
                String mg = matcher.group().substring(1, matcher.group().length()-1);
                if (mg.equals(Preferences.MDR_PATH_WORD) && (this.getPath() != null)){
                    colFormat += this.getPath();
                } else if (mg.equals(Preferences.MDR_PATH_SEP_WORD) && (this.getPathSep() != null)) {
                    colFormat += this.getPathSep();
                } else if (mg.equals(Preferences.MDR_ATTR_NAME_WORD) && (this.getAttrName() != null)){
                    colFormat += this.getAttrName();
                } else if (mg.equals(Preferences.MDR_TABLE_NAME_WORD) && (this.getTableName() != null)){
                    colFormat += this.getTableName();
                } else if (mg.equals(Preferences.MDR_TABLE_SHORT_NAME_WORD) && (this.getTableShortName() != null)){
                    colFormat += this.getTableShortName();
                } else if (mg.equals(Preferences.MDR_INDICE_NAME_WORD) && (this.getIndice() != null)){
                    colFormat += this.getIndice();
                } else if (mg.equals(Preferences.MDR_COLUMNS_NAME_WORD) && (this.getColumnsName() != null)){
                    colFormat += this.getColumnsName();
        /*
           }else if (mg.equals(orderedNameWord) && (order.getOrderedName() != null)){
        	   colFormat += order.getOrderedName();*/
                } else if (mg.equals(Preferences.MDR_PARENT_TABLE_SHORT_NAME_WORD) && (this.getParentTableShortName() != null)){
                    colFormat +=  this.getParentTableShortName();
                } else if (mg.equals(Preferences.MDR_CHILD_TABLE_SHORT_NAME_WORD) && (this.getChildTableShortName() != null)){
                    colFormat +=  this.getChildTableShortName(); /*
           } else if ( mg.equals(roleNameWord) && (this.getRoleName() != null)){
        	   colFormat += this.getRoleName(); */
                } else if ( mg.equals(Preferences.MDR_PARENT_ROLE_NAME_WORD) && (this.getParentRoleName() != null)){
                    colFormat += this.getParentRoleName();
                } else if ( mg.equals(Preferences.MDR_CHILD_ROLE_NAME_WORD) && (this.getChildRoleName() != null)){
                    colFormat += this.getChildRoleName(); /*
           } else if ( mg.equals(fkNameWord) && (order.getFkName() != null)){
        	   colFormat += order.getFkName();*/
                } else if ( mg.equals(Preferences.MDR_COL_NAME_WORD) && (this.getColName() != null)){
                    colFormat += this.getColName();/*
           } else if ( mg.equals(sequenceWord) && (order.getSequence() != null)){
        	   colFormat += order.getSequence();
           } else if ( mg.equals(triggerCodeWord) && (order.getTriggerCode() != null)){
        	   colFormat += order.getTriggerCode();
           } else if ( mg.equals(packageCodeWord) && (order.getPackageCode() != null)){
        	   colFormat += order.getPackageCode(); */
                } else if ( mg.equals(Preferences.MDR_PEA_SHORT_NAME_WORD) && (this.getPeaShortName() != null)){
                    colFormat += this.getPeaShortName();
                } else if ( mg.equals(Preferences.MDR_PEA_SEP_WORD) && (this.getPeaSep() != null)){
                    colFormat += this.getPeaSep();
                } else if ( mg.equals(Preferences.MDR_PARENT_ROLE_NAME_SEP_WORD) && (this.getParentRoleSep() != null)){
                    colFormat += this.getParentRoleSep();
                } else if ( mg.equals(Preferences.MDR_PARENT_NAME_SEP_WORD) && (this.getParentSep() != null)){
                    colFormat += this.getParentSep();
                } else if ( mg.equals(Preferences.MDR_CHILD_ROLE_NAME_SEP_WORD) && (this.getChildRoleSep() != null)){
                    colFormat += this.getChildRoleSep();
                } else if ( mg.equals(Preferences.MDR_COLUMN_DERIVED_WORD) && (this.getColDerived() != null)){
                    colFormat += this.getColDerived();
                } else {
                    colFormat += mg;
                }
                if (this.getLengthMax() != null){
                    //TODO-0 PAS - Limitation provisoire  de la longueur maximale de nom de contrainte
                    colFormat.substring(0, this.getLengthMax()-1);
                }
            }
            return colFormat;
        } catch (Exception e){
           throw new OrderBuildNameException(e);
        }
    }
}
