package mdr.services;

import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import mdr.*;
import mdr.interfaces.IMDRElementNamingPreferences;
import mdr.interfaces.IMDRElementWithIteration;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;

import java.util.ArrayList;

public class MDRModelService {


    public static ArrayList<MDRTable> getMDRTablesDeep(MDRElement root){
        ArrayList<MDRTable>  resultat = new ArrayList<MDRTable>() ;
        /*for (MDRElement aMDRElement : getMDRElementsDeep(root)){
            if (aMDRElement instanceof MDRTable){
                resultat.add((MDRTable) aMDRElement);
            }
        }

         */
        return resultat;
    }


    public static ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration(MDRModel mdrModel) {
        ArrayList<IMDRElementWithIteration> resultat = new ArrayList<IMDRElementWithIteration>();
        for (MDRElement mdrElement : mdrModel.getMDRElements()) {
            if (mdrElement instanceof IMDRElementWithIteration) {
                resultat.add((IMDRElementWithIteration) mdrElement);
            }
        }
        return resultat;
    }

    public static ArrayList<IMDElementWithSource> getIMDElementsWithSource(MDRModel mdrModel) {
        ArrayList<IMDElementWithSource> resultat = new ArrayList<IMDElementWithSource>();
        for (MDRElement mdrElement : mdrModel.getMDRElements()) {
            if (mdrElement instanceof IMDElementWithSource) {
                resultat.add((IMDElementWithSource) mdrElement);
            }
        }
        return resultat;
    }

    public static ArrayList<MDRElement> getMDRElementsTransformedBySource(MDRModel mdrModel,
                                                                          MDElement mdElementSource) {
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        for (IMDElementWithSource imdElementWithSource : getIMDElementsWithSource(mdrModel)) {
            if (imdElementWithSource.getMdElementSource() == mdElementSource) {
                resultat.add((MDRElement) imdElementWithSource);
            }
        }
        return resultat;
    }


    public static MDRContTables getMDRContTables(MDRModel mdrModel) {
        for (MVCCDElement mvccdElement : mdrModel.getChilds()) {
            if (mvccdElement instanceof MDRContTables) {
                return (MDRContTables) mvccdElement;
            }
        }
        return null;
    }



    public static ArrayList<MDRTable> getMDRTables(MDRModel mdrModel) {
        MDRContTables mdrContTables = getMDRContTables(mdrModel);
        return mdrContTables.getMDRTables();
    }

    /*

    public static ArrayList<MDRTable> getMDRTables(MDRModel mdrModel) {
        ArrayList<MDRTable> resultat = new ArrayList<MDRTable>();
        for (MDRElement mdrElement : mdrModel.getMDRElements()){
            if (mdrElement instanceof MDRTable){
                resultat.add((MDRTable) mdrElement);
            }
        }
        return resultat;
    }

     */

    public static void adjustNaming(MDRModel mdrModel){
        // Il faudra faire le changement de longueur en premier!
        for (MDRElement mdrElement : mdrModel.getMDRElements()){
            if (mdrElement instanceof IMDRElementNamingPreferences) {
                String name = buildName(mdrModel, mdrElement);
                mdrElement.setName(name);
             }
        }
    }

    public static String  buildName(MDRModel mdrModel, MDRElement mdrElement){
        String name = "";
        MDRNamingLength namingLength = mdrModel.getNamingLengthFuture();
        if (namingLength == MDRNamingLength.LENGTH30) {
            name = mdrElement.getNames().getName30();
        }
        if (namingLength == MDRNamingLength.LENGTH60) {
            name = mdrElement.getNames().getName60();
        }
        if (namingLength == MDRNamingLength.LENGTH120) {
            name = mdrElement.getNames().getName120();
        }

        MDRNamingFormat mdrNamingFormat = mdrModel.getNamingFormatFuture();
        return formatNaming(name, mdrNamingFormat);
    }

    public static String formatNaming(String name, MDRNamingFormat mdrNamingFormat){
        if (mdrNamingFormat == MDRNamingFormat.NOTHING) {
            // Rien
        }
        if (mdrNamingFormat == MDRNamingFormat.UPPERCASE) {
            name = StringUtils.upperCase(name);
        }
        if (mdrNamingFormat == MDRNamingFormat.LOWERCASE) {
            name = StringUtils.lowerCase(name);
        }
        if (mdrNamingFormat == MDRNamingFormat.CAPITALIZE) {
            name = capitalize(name);
        }
        return name;
    }

    private static String capitalize(String name){
        String[] parts = name.split(Preferences.MDR_SEPARATOR);
        String nameCapitalized ="";
        for(int i=0 ; i< parts.length ; i++){
            nameCapitalized = nameCapitalized + StringUtils.capitalize(parts[i]);
        }
        return nameCapitalized;
    }
}


