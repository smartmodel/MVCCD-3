package mcd;

import m.interfaces.IMCompletness;
import mcd.compliant.MCDCompliant;
import mcd.interfaces.*;
import mcd.services.MCDElementService;
import mcd.services.MCDPackageService;
import project.ProjectElement;

import java.util.ArrayList;

public class MCDPackage extends MCDElement implements IMCDTraceability,
        IMCDContPackages, IMCDContContainer, IMCDContainer, IMCompletness, IMCDCompliant {

    private static final long serialVersionUID = 1000;

    private boolean mcdJournalization = false;
    private boolean mcdJournalizationException = false;
    private boolean mcdAudit = false;
    private boolean mcdAuditException = false;

    public MCDPackage(ProjectElement parent, String name) {

        super(parent,name);
    }

    public MCDPackage(ProjectElement parent) {

        super(parent);
    }

    public boolean isMcdJournalization() {
        return mcdJournalization;
    }

    public void setMcdJournalization(boolean mcdJournalization) {
        this.mcdJournalization = mcdJournalization;
    }

    public boolean isMcdJournalizationException() {
        return mcdJournalizationException;
    }

    public void setMcdJournalizationException(boolean mcdJournalizationException) {
        this.mcdJournalizationException = mcdJournalizationException;
    }

    public boolean isMcdAudit() {
        return mcdAudit;
    }

    public void setMcdAudit(boolean mcdAudit) {
        this.mcdAudit = mcdAudit;
    }

    public boolean isMcdAuditException() {
        return mcdAuditException;
    }

    public void setMcdAuditException(boolean mcdAuditException) {
        this.mcdAuditException = mcdAuditException;
    }


    public ArrayList<MCDEntity> getMCDEntities(){
        return MCDPackageService.getMCDEntities(this);
    }

    public boolean treatCompliant(){
        MCDCompliant mcdCompliant = new MCDCompliant();
        return mcdCompliant.check(getMCDEntities(), false);
    }

    public int getLevel(){
        return MCDPackageService.getLevel(this);
    }

    public IMCDModel getIMCDModelAccueil(){
        return MCDElementService.getIMCDModelAccueil(this);
    }

    public MCDModel getMCDModelAccueil(){
        return MCDElementService.getMCDModelAccueil(this);
    }

}
