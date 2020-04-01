package mcd.interfaces;

public interface IMCDTraceability {

    public boolean isMcdJournalization();

    public void setMcdJournalization(boolean mcdJournalization);

    public boolean isMcdJournalizationException();

    public void setMcdJournalizationException(boolean mcdJournalizationException);

    public boolean isMcdAudit();

    public void setMcdAudit(boolean mcdAudit);

    public boolean isMcdAuditException();

    public void setMcdAuditException(boolean mcdAuditException);

}

