package mpdr.mysql;

import main.MVCCDElementFactory;
import md.MDElement;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRMySQLColumn extends MPDRColumn {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }


}
