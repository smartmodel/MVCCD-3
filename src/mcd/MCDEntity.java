package mcd;

import main.MVCCDElement;
import project.ProjectElement;

import java.util.ArrayList;

public class MCDEntity extends MCDElement{

    private static final long serialVersionUID = 1000;

    private String shortName ;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
        return null;
    }
}
