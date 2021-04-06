package mcd;

import exceptions.TransformMCDException;
import m.interfaces.IMCompletness;
import mcd.compliant.MCDCompliant;
import mcd.interfaces.*;
import mcd.services.IMCDModelService;
import mldr.MLDRModel;
import resultat.Resultat;
import transform.mcdtomldr.MCDTransform;

import java.util.ArrayList;

public class MCDModel extends MCDElement implements IMCDModel, IMCDTraceability,
        IMCDContContainer, /*IMCDNamePathParent,*/ IMCDContPackages, IMCDContainer, IMCompletness,
        IMCDCompliant{

    private static final long serialVersionUID = 1000;
    private MLDRModel lastTransformedMLDRModel;

    private boolean packagesAutorizeds = false;
    private boolean mcdJournalization = false;
    private boolean mcdJournalizationException = false;
    private boolean mcdAudit = false;
    private boolean mcdAuditException = false;


    public MCDModel(MCDContModels parent, int id) {
        super(parent, id);
    }

    public MCDModel(MCDContModels parent, String name) {
        super(parent, name);
    }

    public MCDModel(MCDContModels parent) {
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

    public ArrayList<MCDEntity> getMCDEntities(){
        return IMCDModelService.getMCDEntities(this);
    }

    public Resultat treatCompliant(){
        MCDCompliant mcdCompliant = new MCDCompliant();
        return  mcdCompliant.check(getMCDEntities(), false);
    }

    public Resultat treatTransform()  throws TransformMCDException {
        MCDTransform mcdTransform = new MCDTransform();
        return mcdTransform.transform(this);
    }


    @Override
    public MLDRModel getLastTransformedMLDRModel() {
        return lastTransformedMLDRModel;
    }

    @Override
    public void setLastTransformedMLDRModel(MLDRModel lastTransformedMLDRModel) {
        this.lastTransformedMLDRModel = lastTransformedMLDRModel;
    }
}
