package mdr;

import main.MVCCDElement;
import mcd.MCDEntity;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public class MDRContTables extends MDRElement {

    private  static final long serialVersionUID = 1000;
    public MDRContTables(ProjectElement parent, String name) {
        super(parent, name);
    }

    public ArrayList<MDRTable> getMDRTables(){
        ArrayList<MDRTable> resultat = new ArrayList<MDRTable>();
        for (MVCCDElement mvccdElement: getChildsSortName()){
            resultat.add((MDRTable) mvccdElement);
        }
        return resultat;
    }


}
