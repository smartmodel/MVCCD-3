package mdr.services;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mdr.*;
import mdr.interfaces.IMDRElementNamingPreferences;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;

import java.util.ArrayList;

public class MDRModelService {


    public static ArrayList<MDRElement> getMDRElements(MDRElement parentMDRElement) {
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        resultat.add(parentMDRElement);
        for (MVCCDElement mvccdElement : parentMDRElement.getChilds()) {
            if (mvccdElement instanceof MDRElement) {
                MDRElement childProjectElement = (MDRElement) mvccdElement;
                if (!(childProjectElement instanceof MDRModel)) {
                    resultat.addAll(getMDRElements(childProjectElement));
                }
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


    public static void adjustNaming(MDRModel mdrModel){

        // Il faudra faire le changement de longueur en premier!
        for (MDRElement mdrElement : getMDRElements(mdrModel)){
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


