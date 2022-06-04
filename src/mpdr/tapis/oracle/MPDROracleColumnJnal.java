package mpdr.tapis.oracle;

import mcd.MCDAttribute;
import md.MDElement;
import mpdr.MPDRDB;
import mpdr.interfaces.IMPDRElement;
import mpdr.tapis.MPDRColumnJnal;
import project.ProjectElement;
import stereotypes.Stereotype;

public class MPDROracleColumnJnal extends MPDRColumnJnal {

    protected MPDRDB mpdrDb ;

    public MPDROracleColumnJnal(ProjectElement parent,
                                IMPDRElement mpdrElementSource,
                                Stereotype stereotype) {
        super(parent, mpdrElementSource, stereotype);
    }


    public MPDROracleColumnJnal(ProjectElement parent,
                                IMPDRElement mpdrElementSource) {
        super(parent, mpdrElementSource);
    }

    @Override
    public MDElement getMdElementSource() {
        return null;
    }

    @Override
    public MCDAttribute getMcdAttributeSource() {
        return null;
    }

    @Override
    public boolean isPKForEntityIndependant() {
        return false;
    }
}
