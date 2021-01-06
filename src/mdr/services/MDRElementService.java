package mdr.services;

import main.MVCCDElement;
import mdr.MDRElement;

import java.util.ArrayList;

public class MDRElementService {


    public static ArrayList<MDRElement> getMDRElements(MDRElement root) {
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        resultat.add(root);
        for (MVCCDElement mvccdElement : root.getChilds()){
            if (mvccdElement instanceof MDRElement){
                MDRElement childMDRElement  = (MDRElement) mvccdElement;
                resultat.addAll(getMDRElements(childMDRElement));
            }
        }
        return resultat;
    }

}
