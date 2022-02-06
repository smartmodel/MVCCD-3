package mpdr.mysql;

import main.MVCCDElementFactory;
import mdr.MDRContConstraints;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRCheck;
import mpdr.MPDRColumn;
import mpdr.MPDRSequence;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLColumn extends MPDRColumn implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    // Pas de séquence pour MySQL
    public MPDRSequence createMPDRSequence(MLDRColumn mldrColumn) {
       return null;
    }


    @Override
    public MPDRCheck createMPDRCheckDatatype(MLDRColumn mldrColumn) {
        MDRContConstraints mdrContConstraints = this.getMPDRTableAccueil().getMDRContConstraints();
        MPDRMySQLCheck newCheck = MVCCDElementFactory.instance().createMPDRMySQLCheck( mdrContConstraints, mldrColumn);
        return newCheck;
    }

}
