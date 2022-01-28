package mdr.services;

import main.MVCCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import mdr.*;
import mdr.interfaces.IMDRElementNamingPreferences;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MDRModelService {

    /*
    private static final int LOWER = 0;
    private static final int UPPER = 1;
    final static String UPPERCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static String LOWERCHARS = "abcdefghijklmnopqrstuvwxyz";

     */

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


    /*
    public static ArrayList<IMDRElementWithIteration> getIMDRElementsWithIteration(MDRModel mdrModel) {
        ArrayList<IMDRElementWithIteration> resultat = new ArrayList<IMDRElementWithIteration>();
        for (MDRElement mdrElement : mdrModel.getMDRDescendants()) {
            if (mdrElement instanceof IMDRElementWithIteration) {
                resultat.add((IMDRElementWithIteration) mdrElement);
            }
        }
        return resultat;
    }

     */


    public static ArrayList<IMDElementWithSource> getIMDElementsWithSource(MDRModel mdrModel) {
        ArrayList<IMDElementWithSource> resultat = new ArrayList<IMDElementWithSource>();
        for (MDRElement mdrElement : mdrModel.getMDRDescendants()) {
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

    public static MDRContRelations getMDRContRelations(MDRModel mdrModel) {
        for (MVCCDElement mvccdElement : mdrModel.getChilds()){
            if (mvccdElement instanceof MDRContRelations){
                return (MDRContRelations) mvccdElement ;
            }
        }
        return null;
    }

    public static void adjustNaming(MDRModel mdrModel){
        for (MDRElement mdrElement : getMDRDescendantsInModelStrict(mdrModel)){
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
        //TODO-PAS Mettre un message si toujours name =""
        MDRNamingFormat mdrNamingFormat = mdrModel.getNamingFormatForDB();
        return formatNaming(name, mdrNamingFormat);
    }

    private static String formatNaming(String name, MDRNamingFormat mdrNamingFormat){
        if (mdrNamingFormat == MDRNamingFormat.NOTHING) {
            // name tel que repris de MPDRModelService.builName
        }
        if (mdrNamingFormat == MDRNamingFormat.UPPERCASE) {
             name = StringUtils.upperCase( name);
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
        String regex = UtilDivers.toEscapedForRegex(Preferences.MDR_SEPARATOR);

        String[] parts = name.split(regex);
        String nameCapitalized ="";
        for(int i=0 ; i< parts.length ; i++){
            nameCapitalized = nameCapitalized + StringUtils.capitalize(parts[i]);
        }
        return nameCapitalized;
    }

    /*
    private static String lowerOrUpper(int treatment, String name){

        if (isCapitalized(name)){

            boolean separator = false;
            boolean lastUpperChar = true ; // Dernier caractère écrit est une majuscule
            String nameWithSep = "" + name.charAt(0) ;
            for (int i = 1 ; i < name.length() ; i++){
                char c = name.charAt(i);
                Trace.println(name + "  " + c + "   " + StringUtils.contains(UPPERCHARS, c));
                if (StringUtils.contains(UPPERCHARS, c)) {
                    if (!lastUpperChar){
                        nameWithSep += Preferences.MDR_SEPARATOR ;
                    }
                } else {
                    lastUpperChar = false;
                }
                nameWithSep += c;
            }

            name = nameWithSep;

        }

        return name ;
    }

     */

  /*
    private static boolean isCapitalized (String name) {

        String nameWithSep = "";
        boolean nameIsCapitalized = false;
        // Pas de séparateur
        if (name.length() > 0) {
            if (!StringUtils.contains(name, Preferences.MDR_SEPARATOR)) {
                // 1ère lettre une majuscule
                if (StringUtils.contains(UPPERCHARS, name.charAt(0))) {
                    int i = 0;
                    while ((!nameIsCapitalized) && (i < name.length())) {
                        // Une lettre non majuscule
                        boolean oneCharNotUpper = !StringUtils.contains(UPPERCHARS, name.charAt(i));
                        if (oneCharNotUpper) {
                            int j = 0;
                            while ((!nameIsCapitalized) && (j < name.length())) {
                                boolean oneCharUpper = StringUtils.contains(UPPERCHARS, name.charAt(j));
                                if (oneCharUpper) {
                                    nameIsCapitalized = true;
                                }
                                j++;
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return nameIsCapitalized;
    }

   */

    public static ArrayList<MDRElement> getMDRDescendantsInModelStrict(MDRModel mdrModel) {
         return  getMDRDescendantsInModelStrictInternal(mdrModel);
    }

    // A l'appel root est une instance de MDRElement
    private static ArrayList<MDRElement> getMDRDescendantsInModelStrictInternal(MDRElement root) {
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        for (MDRElement child : root.getMDRChilds()){
            if (!(child instanceof MDRModel)) {
                resultat.add(child);
                if (child.getChilds().size() > 0) {
                    resultat.addAll(getMDRDescendantsInModelStrictInternal(child));
                }
            }
        }
        return resultat;

    }


}


