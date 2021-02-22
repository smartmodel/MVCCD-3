package mdr;

import main.MVCCDElement;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRContColumns extends MDRElement{

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
}
