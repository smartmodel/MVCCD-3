package mcd;

import constraints.Constraint;
import exceptions.CodeApplException;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import mcd.interfaces.IMCDElementWithTargets;
import mcd.services.MCDRelationService;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MCDRelation extends MCDElement implements IMRelation, IMCDElementWithTargets {

    private  static final long serialVersionUID = 1000;

    private IMRelEnd a ; //dans le cas du lien (link) d'entité associative, correspond à l'entité reliée par le lien
    private IMRelEnd b ; //dans le cas du lien (link) d'entité associative, correspond à l'association reliée par le lien

    public MCDRelation(MCDElement parent) {
        super(parent);
    }

    public MCDRelation(MCDElement parent, int id) {
        super(parent, id);
    }

    public MCDRelation(MCDElement parent, String name) {
        super(parent, name);
    }

    @Override
    public IMRelEnd getA() {
        return a;
    }

    @Override
    public void setA(IMRelEnd a) {
        this.a = a;
    }

    @Override
    public IMRelEnd getB() {
        return b;
    }

    @Override
    public void setB(IMRelEnd b) {
        this.b = b;
    }

    public MCDRelEnd getMCDRelEndOpposite(MCDRelEnd mcdRelEnd) {
        if (this.getA() == mcdRelEnd){
            return (MCDRelEnd) this.getB();
        }
        if (this.getB() == mcdRelEnd){
            return (MCDRelEnd)this.getA();
        }

        throw new CodeApplException("L'extrémité de relation passée en paramètre n'existe pas pour cette relation ");

    }

    public abstract String getClassShortNameUI();


    public ArrayList<MCDRelation> getMCDRelationsChilds(){
        return MCDRelationService.getMCDRelationsChilds(this);
    }

    public void removeInParent(){

        ArrayList<MCDRelation> mcdRelationChilds = getMCDRelationsChilds();
        for (int i = mcdRelationChilds.size()-1 ; i >= 0 ; i--){
            MCDRelation  mcdRelationChild =  mcdRelationChilds.get(i);
            mcdRelationChild.removeInParent();
            mcdRelationChild = null;
         }

        MCDRelEnd a = (MCDRelEnd) getA();
        if (a != null) {
            a.removeInParent();
            a = null;
        }
        MCDRelEnd b = (MCDRelEnd) getB();
        if (b != null) {
            b.removeInParent();
            b = null;
        }
        super.removeInParent();
    }

    public abstract ArrayList<Stereotype> getToStereotypes();

    public abstract ArrayList<Constraint> getToConstraints(); // Contraintes UML


}
