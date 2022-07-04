package mpdr.oracle;

import main.MVCCDElementFactory;
import mdr.MDRContConstraints;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRCheck;
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

    //TODO VINCENT
    /*
    public MPDROracleColumn(String name, String dataType, Integer size, boolean isMandatory, String initValue){
        super();
        this.setName(name);
        this.setDatatypeConstraintLienProg(dataType);
        this.setSize(size);
        this.setMandatory(isMandatory);
        this.setInitValue(initValue);
    }
*/

    public MPDRSequence createMPDRSequence(MLDRColumn mldrColumn) {
        MPDROracleSequence newSequence = MVCCDElementFactory.instance().createMPDROracleSequence(
                this, mldrColumn);
        return newSequence;
    }


    @Override
    public MPDRCheck createMPDRCheckDatatype(MLDRColumn mldrColumn) {
        MDRContConstraints mdrContConstraints = this.getMPDRTableAccueil().getMDRContConstraints();
        MPDROracleCheckSpecific newCheck = MVCCDElementFactory.instance().createMPDROracleCheckSpecific( mdrContConstraints, mldrColumn);
        return newCheck;
    }

}
