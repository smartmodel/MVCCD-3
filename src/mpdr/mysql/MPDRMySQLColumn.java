package mpdr.mysql;

import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRSequence;
import project.ProjectElement;

public class MPDRMySQLColumn extends MPDRColumn {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRSequence createSequence(MLDRColumn mldrColumn) {
        return null;
    }


}
