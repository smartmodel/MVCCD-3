package mpdr.postgresql;

import main.MVCCDElementFactory;
import mdr.MDRContConstraints;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRCheck;
import mpdr.MPDRColumn;
import mpdr.MPDRSequence;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDRPostgreSQLColumn extends MPDRColumn implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLColumn(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRSequence createMPDRSequence(MLDRColumn mldrColumn) {
        MPDRPostgreSQLSequence newSequence = MVCCDElementFactory.instance().createMPDRPostgreSQLSequence(
                this, mldrColumn);

        return newSequence;
    }


    @Override
    public MPDRCheck createMPDRCheckDatatype(MLDRColumn mldrColumn) {
        MDRContConstraints mdrContConstraints = this.getMPDRTableAccueil().getMDRContConstraints();
        MPDRPostgreSQLCheck newCheck = MVCCDElementFactory.instance().createMPDRPostgreSQLCheck( mdrContConstraints, mldrColumn);
        return newCheck;
    }

}
