package mpdr.tapis;

import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRCheck;
import mpdr.MPDRColumn;
import mpdr.MPDRSequence;
import mpdr.interfaces.IMPDRElementWithSource;
import mpdr.tapis.interfaces.IMPDRColumnForTAPIs;
import mpdr.tapis.services.MPDRColumnAuditService;
import project.ProjectElement;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MPDRColumnAudit extends MPDRColumn implements IMPDRElementWithSource, IMPDRColumnForTAPIs {

    private  static final long serialVersionUID = 1000;

    //protected MPDRDB mpdrDb ;
    //ArrayList<Stereotype> stereotypes = new ArrayList<Stereotype>();
    Stereotype sterereotypeAudit  ;


    public MPDRColumnAudit(ProjectElement parent,
                           IMLDRElement mldrElementSource,
                           Stereotype stereotypeAudit) {
        super(parent, mldrElementSource);
        this.sterereotypeAudit = stereotypeAudit;
    }

    public MPDRColumnAudit(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


    public Stereotype getSterereotypeAudit() {
        return sterereotypeAudit;
    }

    public ArrayList<Stereotype> getStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();
        resultat.add(sterereotypeAudit);
        return resultat;
    }

    @Override
    public MPDRSequence createMPDRSequence(MLDRColumn mldrColumn) {
        return null;
    }

    @Override
    public MPDRCheck createMPDRCheckDatatype(MLDRColumn mldrColumn) {
        return null;
    }


    public int compareToDefault(MPDRColumnAudit other) {
        return MPDRColumnAuditService.compareToDefault(this, other);
    }

}
