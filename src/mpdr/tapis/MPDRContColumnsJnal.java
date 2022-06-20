package mpdr.tapis;

import main.MVCCDElement;
import mdr.MDRColumn;
import mpdr.MPDRContColumns;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public class MPDRContColumnsJnal extends MPDRContColumns {

    // Un éventuel tri ne peut pas se faire en héritant de la méthode du parent MDRContColumns

    private  static final long serialVersionUID = 1000;

    public MPDRContColumnsJnal(ProjectElement parent, String name) {
        super(parent, name);
    }


    public ArrayList<MPDRColumnJnal> getMPDRColumnsJnal(){
        ArrayList<MPDRColumnJnal> resultat = new ArrayList<MPDRColumnJnal>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MPDRColumnJnal) mvccdElement);
        }
        return resultat;
    }

    public ArrayList<MPDRColumnJnal> getMPDRColumnsJnalSortDefault(){
        ArrayList<MPDRColumnJnal> mpdrColumnsJnalSorted = new ArrayList<MPDRColumnJnal>();
        for (MPDRColumnJnal mpdrColumnJnal : getMPDRColumnsJnal()){
            mpdrColumnsJnalSorted.add(mpdrColumnJnal);
        }
        Collections.sort(mpdrColumnsJnalSorted, MPDRColumnJnal::compareToDefault);
        return mpdrColumnsJnalSorted;
    }


    // Surcharge pour l'affichage des colonnes de journalisation...
    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        ArrayList<? extends MPDRColumnJnal> mpdrColumnsJnal = getMPDRColumnsJnalSortDefault();
        return mpdrColumnsJnal ;
    }


    // Surcharge pour le traitement des colonnes de journalisation...
    public ArrayList<? extends MDRColumn> getMDRColumnsSortDefault(){
        ArrayList< MPDRColumnJnal> mpdrColumnsJnal = getMPDRColumnsJnalSortDefault();
        return mpdrColumnsJnal ;
    }



}
