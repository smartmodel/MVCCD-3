package mcd;

import main.MVCCDElement;

import java.util.ArrayList;

public class MCDContEndRels extends MCDElement{

    private static final long serialVersionUID = 1000;

    public MCDContEndRels(MCDEntity parent, String name) {
        super(parent,name);
    }

    public ArrayList<MCDRelation> getMCDRelations(){
        ArrayList<MCDRelation> resultat = new ArrayList<MCDRelation>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDRelation) mvccdElement);
        }
        return resultat;
    }

    public boolean removeRelation(MCDRelation mcdRelation){

        return getMCDRelations().remove(mcdRelation);
    }

    @Override
    public String getNameTree() {
        return null;
    }
}
