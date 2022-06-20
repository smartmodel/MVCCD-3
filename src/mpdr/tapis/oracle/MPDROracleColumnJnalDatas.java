package mpdr.tapis.oracle;

import mcd.MCDAttribute;
import md.MDElement;
import mpdr.MPDRDB;
import mpdr.interfaces.IMPDRElement;
import mpdr.tapis.MPDRColumnJnalDatas;
import project.ProjectElement;

public class MPDROracleColumnJnalDatas extends MPDRColumnJnalDatas {

    protected MPDRDB mpdrDb ;

    public MPDROracleColumnJnalDatas(ProjectElement parent,
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
