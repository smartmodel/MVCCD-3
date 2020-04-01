package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDNamePathParent;
import mcd.interfaces.IMCDTraceability;
import preferences.Preferences;
import project.ProjectElement;
import utilities.files.UtilXML;

public class MCDPackage extends MCDElement implements IMCDTraceability, IMCDNamePathParent {

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

    @Override
    public String getNameTree() {
        return null;
    }
}
