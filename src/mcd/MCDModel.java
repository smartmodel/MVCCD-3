package mcd;

import mcd.interfaces.IMCDModel;
import mcd.interfaces.IMCDNamePathParent;
import mcd.interfaces.IMCDTraceability;
import project.ProjectElement;
import utilities.window.scomponents.SCheckBox;

import javax.swing.*;

public class MCDModel extends MCDElement implements IMCDModel, IMCDTraceability, IMCDNamePathParent {

    private static final long serialVersionUID = 1000;

    private boolean packagesAutorizeds = false;
    private boolean mcdJournalization = false;
    private boolean mcdJournalizationException = false;
    private boolean mcdAudit = false;
    private boolean mcdAuditException = false;


    public MCDModel(MCDModels parent, String name) {

        super(parent, name);
    }

    public MCDModel(MCDModels parent) {

        super (parent);
    }

    public boolean isPackagesAutorizeds() {
        return packagesAutorizeds;
    }

    public void setPackagesAutorizeds(boolean packagesAutorizeds) {
        this.packagesAutorizeds = packagesAutorizeds;
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

    @Override
    public String getNameTree() {
        return null;
    }
}
