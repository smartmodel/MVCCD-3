package mcd;

import m.IMCompliant;
import main.MVCCDElement;
import mcd.interfaces.IMCDNamePathParent;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public class MCDEntity extends MCDElement implements IMCDNamePathParent, IMCompliant {

    private static final long serialVersionUID = 1000;

    //private String shortName ;
    private boolean entAbstract = false;
    private boolean ordered = false;
    private boolean journal = false;
    private boolean audit = false;


    public MCDEntity(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDEntity(ProjectElement parent) {
        super (parent);
    }



    public boolean isEntAbstract() {
        return entAbstract;
    }

    public void setEntAbstract(boolean entAbstract) {
        this.entAbstract = entAbstract;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public boolean isJournal() {
        return journal;
    }

    public void setJournal(boolean journal) {
        this.journal = journal;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public ArrayList<MCDAttribute> getMcdAttributes() {
        for (MVCCDElement mvccdElement : getChilds()){
           if (mvccdElement instanceof MCDContAttributes) {
               MCDContAttributes mcdContAttributes = (MCDContAttributes) mvccdElement;
               return mcdContAttributes.getMCDAttributes();
           }
        }
        return new ArrayList<MCDAttribute>();
    }

    public MCDContAttributes getMCDContAttributes() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContAttributes) {
                return (MCDContAttributes) mvccdElement;
            }
        }
        return null;
    }

    public MCDContConstraints getMCDContConstraints() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContConstraints) {
                return (MCDContConstraints) mvccdElement;
            }
        }
        return null;
    }

    public MCDContRelEnds getMCDContRelEnds() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContRelEnds) {
                return (MCDContRelEnds) mvccdElement;
            }
        }
        return null;
    }

    public ArrayList<MCDAssEnd> getMCDAssEnds() {
        ArrayList<MCDAssEnd> resultat = new ArrayList<MCDAssEnd>();
        for (MVCCDElement mvccdElement : this.getMCDContEndRels().getChilds()){
            if (mvccdElement instanceof MCDAssEnd){
                resultat.add((MCDAssEnd) mvccdElement);
            }
        }
        return resultat;
    }

    public static String getClassShortNameUI() {
        return "Entité";
    }


}
