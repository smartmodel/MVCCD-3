package mcd;

import constraints.Constraint;
import exceptions.CodeApplException;
import m.interfaces.IMRelEnd;
import m.interfaces.IMRelation;
import mcd.interfaces.IMCDElementWithTargets;
import md.MDElement;
import md.services.MDElementConvert;
import mldr.interfaces.IMLDRElement;
import project.ProjectService;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MCDRelation extends MCDElement implements IMRelation, IMCDElementWithTargets {

    private  static final long serialVersionUID = 1000;

    private ArrayList<IMLDRElement> imldrElementTargets;

    private IMRelEnd a ;
    private IMRelEnd b ;

    public MCDRelation(MCDElement parent) {
        super(parent);
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

    public ArrayList<MCDRelation> getAllMCDRelationsChilds(){
        return ProjectService.getMCDRelationsChilds(this);
    }

    public void removeInParent(){

        ArrayList<MCDRelation> mcdRelationChilds = getAllMCDRelationsChilds();
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

    @Override
    public ArrayList<IMLDRElement> getImldrElementTargets() {
        return imldrElementTargets;
    }

    @Override
    public void setImldrElementTargets(ArrayList<IMLDRElement> imldrElementTargets) {
        this.imldrElementTargets = imldrElementTargets;
    }

    @Override
    public ArrayList<MDElement> getMdElementTargets() {
        return MDElementConvert.to(imldrElementTargets);
    }

    @Override
    public void setMdElementTargets(ArrayList<MDElement> mdElementTargets) {
        this.imldrElementTargets = MDElementConvert.from(mdElementTargets);
    }
}
