package mdr.orderbuildnaming;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MDROrderWordIndice extends MDROrderWord{


    public MDROrderWordIndice(String name, Integer length) {
        super(name, length);
    }



    public void setValue(String nameInitial, ArrayList<MVCCDElement> brothers){
        Preferences preferences = PreferencesManager.instance().preferences();

        Integer indice = null;
        String racine = nameInitial;


        Pattern pattern = Pattern.compile(Preferences.MDR_INDICE_REGEXPR);
        //Matcher matcher = pattern.matcher(nameColFk);
        Matcher matcher = pattern.matcher(racine);
        if(matcher.find()){
            String differenciation = matcher.group();
            racine = extractRoot(nameInitial, differenciation);
        }

        indice = compute(racine, brothers);

        if (indice != null){
            super.setValue(indice.toString());
        } else {
            super.setValue("");
        }



    }

    protected abstract Integer compute(String racine, ArrayList<MVCCDElement> brothers);


    protected static String extractRoot(String nameColumn, String differenciation){
        return nameColumn.substring(0, nameColumn.length()- differenciation.length());

    }



    protected static int nbColumnFKByRoot(String racineToCheck, ArrayList<MVCCDElement> brothers){
        int resultat = 0;
        Pattern pattern = Pattern.compile(Preferences.MDR_INDICE_REGEXPR);
        for (MVCCDElement mvccdElement : brothers){
            Matcher matcher = pattern.matcher(mvccdElement.getName());
            if (matcher.find()){
                String differentiation = matcher.group();
                String racineBrother = extractRoot(mvccdElement.getName(), differentiation);
                if (racineBrother.equals(racineToCheck)) resultat++;
            } else {
                if (mvccdElement.getName().equals(racineToCheck)) resultat++;
            }
        }
        return resultat;
    }



}
