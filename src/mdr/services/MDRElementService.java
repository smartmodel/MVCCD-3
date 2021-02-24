package mdr.services;

import mdr.MDRElement;

import java.util.ArrayList;

public class MDRElementService {

    //#MAJ 2021-01-14 Suppression de MDRElement.getMDRElements()
    /*
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

     */

    public static ArrayList<MDRElement> getMDRDescendants(MDRElement root) {
        ArrayList<MDRElement> resultat = new ArrayList<MDRElement>();
        for (MDRElement child : root.getMDRChilds()){
            resultat.add(child);
            if (child.getChilds().size() > 0) {
                resultat.addAll(getMDRDescendants(child));
            }
        }
        return resultat;
    }
}
