package mcd;

import main.MVCCDElement;

import java.util.ArrayList;

public class MCDContAttributes extends MCDElement{

    private static final long serialVersionUID = 1000;

    public MCDContAttributes(MCDEntity parent, String name) {
        super(parent,name);
    }

    public ArrayList<MCDAttribute> getMCDAttributes(){
        ArrayList<MCDAttribute> resultat = new ArrayList<MCDAttribute>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDAttribute) mvccdElement);
        }
        return resultat;
    }

    public boolean removeAttribute(MCDAttribute mcdAttribute){

        return getMCDAttributes().remove(mcdAttribute);
    }


    public static String getClassShortNameUI() {
        return "Conteneur d'attributs";
    }
}
