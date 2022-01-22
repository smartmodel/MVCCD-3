package mpdr.oracle;

import main.MVCCDElementFactory;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRSequence;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleColumn extends MPDRColumn implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleColumn(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


    public MPDRSequence createSequence(MLDRColumn mldrColumn) {
        MPDROracleSequence newSequence = MVCCDElementFactory.instance().createMPDROracleSequence(
                this, mldrColumn);
        return newSequence;
    }


}
