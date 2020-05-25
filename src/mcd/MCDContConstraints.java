package mcd;

import main.MVCCDElement;
import project.ProjectElement;
import project.ProjectService;

import java.util.ArrayList;

public class MCDContConstraints extends MCDElement {

    private static final long serialVersionUID = 1000;

    public MCDContConstraints(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDContConstraints(ProjectElement parent) {
        super(parent);
    }

    @Override
    public String getNameTree() {
        return null;
    }

    public ArrayList<MCDConstraint> getMCDConstraints(){
        ArrayList<MCDConstraint> resultat = new ArrayList<MCDConstraint>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDConstraint) mvccdElement);
        }
        return resultat;
    }

    public static MCDContConstraints getMCDContConstraintsByNamePath(int pathMode, String namePath) {
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)) {
            if (mcdElement instanceof MCDContConstraints) {
                return (MCDContConstraints) mcdElement;
            }
        }
        return null;
    }

    public ArrayList<MCDNID> getMCDNIDs(){
        ArrayList<MCDNID> resultat = new ArrayList<MCDNID>();
        for (MCDConstraint mcdConstraint: getMCDConstraints()){
            if ( mcdConstraint instanceof MCDNID) {
                resultat.add((MCDNID) mcdConstraint);
            }
        }
        return resultat;
    }

    public ArrayList<MCDUnique> getMCDUniques(){
        ArrayList<MCDUnique> resultat = new ArrayList<MCDUnique>();
        for (MCDConstraint mcdConstraint: getMCDConstraints()){
            if ( mcdConstraint instanceof MCDUnique) {
                resultat.add((MCDUnique) mcdConstraint);
            }
        }
        return resultat;
    }
}
