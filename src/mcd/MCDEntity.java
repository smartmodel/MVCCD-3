package mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import preferences.Preferences;
import utilities.files.UtilXML;

public class MCDEntity extends MCDElement{

    private String shortName ;
    private boolean entAbstract = false;
    private boolean ordered = false;
    private boolean journal = false;
    private boolean audit = false;

    private static final long serialVersionUID = 1000;

    public MCDEntity(MVCCDElement mvccdElement, String name) {
        super(mvccdElement,name);
    }

    public MCDEntity(MVCCDElement parent) {
        super (parent);
    }
    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_ENTITY + " " +
                UtilXML.attributName(getName());
        return  UtilXML.baliseBegin (richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_ENTITY);
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
}
