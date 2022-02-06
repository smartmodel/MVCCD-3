package mpdr.mysql;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRParameter;
import mpdr.MPDRUnique;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLUnique extends MPDRUnique implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLUnique(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    // A finaliser pour la sauvegarde XML
    /*
    public MPDROracleUnique(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

     */

    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRMySQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
