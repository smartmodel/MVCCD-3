package mdr;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public abstract class MDRContColumns extends MDRElement implements IMPathOnlyRepositoryTree {

    private  static final long serialVersionUID = 1000;

    public MDRContColumns(ProjectElement parent, String name) {
        super(parent, name);
    }

    public ArrayList<MDRColumn> getMDRColumns(){
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MDRColumn) mvccdElement);
        }
       return resultat;
    }

    public ArrayList<? extends MDRColumn> getMDRColumnsSortDefault(){
        ArrayList<MDRColumn> mdrColumnsSorted = new ArrayList<MDRColumn>();
        for (MDRColumn mdrColumn : getMDRColumns()){
            mdrColumnsSorted.add(mdrColumn);
        }
        Collections.sort(mdrColumnsSorted, MDRColumn::compareToDefault);
        return mdrColumnsSorted;
    }



    // Surcharge pour l'affichage trié PFK - PK - FK
    public ArrayList<? extends  MVCCDElement> getChildsSortDefault() {
        ArrayList<? extends MDRColumn> mdrColumns = getMDRColumnsSortDefault();
        return mdrColumns ;
    }
}
