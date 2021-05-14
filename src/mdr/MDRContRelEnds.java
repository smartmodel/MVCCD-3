package mdr;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;

public class MDRContRelEnds extends MDRElement implements IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;

    public MDRContRelEnds(ProjectElement parent, String name) {
        super(parent,name);
    }

    public ArrayList<MDRRelEnd> getMDRRelEnds(){
        ArrayList<MDRRelEnd> resultat = new ArrayList<MDRRelEnd>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MDRRelEnd) mvccdElement);
        }
        return resultat;
    }

}
