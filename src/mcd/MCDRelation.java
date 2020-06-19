package mcd;

import constraints.Constraint;
import exceptions.CodeApplException;
import m.MRelEnd;
import m.MRelation;
import project.ProjectElement;
import project.ProjectService;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MCDRelation extends MCDElement implements MRelation {

    private  static final long serialVersionUID = 1000;

    private MCDRelEnd a ;
    private MCDRelEnd b ;

    public MCDRelation(MCDElement parent) {
        super(parent);
    }

    public MCDRelation(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDRelEnd getA() {
        return a;
    }

    public void setA(MCDRelEnd a) {
        this.a = a;
    }

    public MCDRelEnd getB() {
        return b;
    }

    public void setB(MCDRelEnd b) {
        this.b = b;
    }

    public MCDRelEnd getMCDRelEndOpposite(MCDRelEnd mcdRelEnd) {
        if (this.getA() == mcdRelEnd){
            return this.getB();
        }
        if (this.getB() == mcdRelEnd){
            return this.getA();
        }

        throw new CodeApplException("L'extrémité de relation passée en paramètre n'existe pas pour cette relation ");

    }

    public abstract String getClassShortNameUI();

    public ArrayList<MCDRelation> getAllMCDRelationsChilds(){
        return ProjectService.getAllMCDRelationsChilds(this);
    }

    public void removeInParent(){
        /*
        ArrayList<MCDRelEnd> mcdRelEndLikeMCDElements = ProjectService.getAllMCDRelEndByMCDElement(this);
        for (MCDRelEnd mcdRelEndLikeMCDElement  : mcdRelEndLikeMCDElements ){
            System.out.println( mcdRelEndLikeMCDElement.getMcdRelation().getNameTree());
            MCDRelation mcdRelationFromMCDElement = mcdRelEndLikeMCDElement.getMcdRelation();
            System.out.println( mcdRelationFromMCDElement.getNameTree());
            mcdRelEndLikeMCDElement.getMcdRelation().removeInParent();
            mcdRelationFromMCDElement = null;
            //System.out.println( mcdRelationFromMCDElement.getNameTree());
        }
        */

        ArrayList<MCDRelation> mcdRelationChilds = getAllMCDRelationsChilds();
        for (int i = mcdRelationChilds.size()-1 ; i >= 0 ; i--){
            MCDRelation  mcdRelationChild =  mcdRelationChilds.get(i);
            mcdRelationChild.removeInParent();
            mcdRelationChild = null;
         }

        MCDRelEnd a = getA();
        if (a != null) {
            a.removeInParent();
            a = null;
        }
        MCDRelEnd b = getB();
        if (b != null) {
            b.removeInParent();
            b = null;
        }
        super.removeInParent();
    }

    public abstract ArrayList<Stereotype> getToStereotypes();

    public abstract ArrayList<Constraint> getToConstraints(); // Contraintes UML


}
