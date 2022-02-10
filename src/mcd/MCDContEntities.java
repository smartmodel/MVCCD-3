package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;

public class MCDContEntities extends MCDElement implements IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;

    public MCDContEntities(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDContEntities(ProjectElement parent) {
        super(parent);
    }


    public ArrayList<MCDEntity> getMCDEntities() {
        ArrayList<MCDEntity> resultat = new ArrayList<MCDEntity>();
        for (MVCCDElement mvccdElement : getChilds()) {
            resultat.add((MCDEntity) mvccdElement);
        }
        return resultat;
    }


    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        return getChildsSortName();
    }

}

