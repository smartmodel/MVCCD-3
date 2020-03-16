package datatypes;

import main.MVCCDElement;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MDDatatypeService {

    public static String getSizeMandatoryInString(MDDatatype mdDatatype , int showNull) {
        Boolean sizeMandatory = mdDatatype.getSizeMandatory();
        return UtilDivers.fromBooleanToString(sizeMandatory, showNull);
    }

    public static String getSizeMandatoryWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Boolean sizeMandatory = mdDatatype.isSizeMandatoryWithInherit();
        return UtilDivers.fromBooleanToString(sizeMandatory, showNull);
    }

    public static String getSizeMandatoryFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getSizeMandatoryFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }

    public static String getSizeMinInString(MDDatatype mdDatatype , int showNull) {
        Integer sizeMin = mdDatatype.getSizeMin();
        return UtilDivers.fromIntegerToString(sizeMin, showNull);
    }

    public static String getSizeMinWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Integer sizeMin = mdDatatype.getSizeMinWithInherit();
        return UtilDivers.fromIntegerToString(sizeMin, showNull);
    }

    public static String getSizeDefaultInString(MDDatatype mdDatatype , int showNull) {
        Integer sizeDefault = mdDatatype.getSizeDefault();
        return UtilDivers.fromIntegerToString(sizeDefault, showNull);
    }

    public static String getSizeDefaultWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Integer sizeDefault = mdDatatype.getSizeDefaultWithInherit();
        return UtilDivers.fromIntegerToString(sizeDefault, showNull);
    }

    public static String getSizeDefaultFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getSizeDefaultFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }

    public static String getSizeMinFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getSizeMinFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }


    public static String getSizeMaxInString(MDDatatype mdDatatype , int showNull) {
        Integer sizeMax = mdDatatype.getSizeMax();
        return UtilDivers.fromIntegerToString(sizeMax, showNull);
    }

    public static String getSizeMaxWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Integer sizeMax = mdDatatype.getSizeMaxWithInherit();
        return UtilDivers.fromIntegerToString(sizeMax, showNull);
    }


    public static String getSizeMaxFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getSizeMaxFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }

    public static String getScaleMandatoryInString(MDDatatype mdDatatype , int showNull) {
        Boolean scaleMandatory = mdDatatype.getScaleMandatory();
        return UtilDivers.fromBooleanToString(scaleMandatory, showNull);
    }

    public static String getScaleMandatoryWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Boolean scaleMandatory = mdDatatype.isScaleMandatoryWithInherit();
        return UtilDivers.fromBooleanToString(scaleMandatory, showNull);
    }

    public static String getScaleMandatoryFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getScaleMandatoryFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }

    public static String getScaleDefaultInString(MDDatatype mdDatatype , int showNull) {
        Integer scaleDefault = mdDatatype.getScaleDefault();
        return UtilDivers.fromIntegerToString(scaleDefault, showNull);
    }

    public static String getScaleDefaultWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Integer scaleDefault = mdDatatype.getScaleDefaultWithInherit();
        return UtilDivers.fromIntegerToString(scaleDefault, showNull);
    }

    public static String getScaleDefaultFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getScaleDefaultFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }

    public static String getScaleMinInString(MDDatatype mdDatatype , int showNull) {
        Integer scaleMin = mdDatatype.getScaleMin();
        return UtilDivers.fromIntegerToString(scaleMin, showNull);
    }

    public static String getScaleMinWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Integer scaleMin = mdDatatype.getScaleMinWithInherit();
        return UtilDivers.fromIntegerToString(scaleMin, showNull);
    }

    public static String getScaleMinFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getScaleMinFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }


    public static String getScaleMaxInString(MDDatatype mdDatatype , int showNull) {
        Integer scaleMax = mdDatatype.getScaleMax();
        return UtilDivers.fromIntegerToString(scaleMax, showNull);
    }

    public static String getScaleMaxWithInheritInString(MDDatatype mdDatatype , int showNull) {
        Integer scaleMax = mdDatatype.getScaleMaxWithInherit();
        return UtilDivers.fromIntegerToString(scaleMax, showNull);
    }

    public static String getScaleMaxFrom(MDDatatype mdDatatype , int showNull){
        MDDatatype mdDatatypeFrom = mdDatatype.getScaleMaxFrom();
        return getNameFromIFNotNull(mdDatatype, mdDatatypeFrom);
    }

    public static String getNameFromIFNotNull(MDDatatype mdDatatype , MDDatatype mdDatatypeFrom){
       if ((mdDatatypeFrom != null) && (mdDatatype != mdDatatypeFrom)){
            return mdDatatypeFrom.getName();
        } else {
            return UtilDivers.stringNull(UtilDivers.EMPTY);
        }
    }

    public static ArrayList<MDDatatype> getChilds(MDDatatype mdDatatype){
        ArrayList<MDDatatype> resultat = new ArrayList<MDDatatype>();
        for (MVCCDElement mvccdElement : mdDatatype.getChilds()){
            if (mvccdElement instanceof MDDatatype){
                resultat.add ((MDDatatype) mvccdElement);
            }
        }
        return resultat;
    }



    public static MCDDatatype getMCDDatatypeByName(String name){
        for (MCDDatatype mcdDatatype : MDDatatypesManager.instance().getMCDDatatypes(MDDatatypesManager.BOTH)){
            if (mcdDatatype.getName().equals(name)){
                return mcdDatatype;
            }
        }
        return null;
    }

    public static MCDDatatype getMCDDatatypeByLienProg(String lienProg){
        for (MCDDatatype mcdDatatype : MDDatatypesManager.instance().getMCDDatatypes(MDDatatypesManager.BOTH)){
            if (mcdDatatype.getLienProg().equals(lienProg)){
                return mcdDatatype;
            }
        }
        return null;
    }

    public static String convertNameToLienProg(String name){
        MCDDatatype mcdDatatype = getMCDDatatypeByName(name);
        return mcdDatatype.getLienProg();
    }

    public static String convertLienProgToName(String lienProg){
        MCDDatatype mcdDatatype = getMCDDatatypeByName(lienProg);
        return mcdDatatype.getName();
    }
}
