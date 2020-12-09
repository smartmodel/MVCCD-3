package mpdr.mysql;

import main.MVCCDElementFactory;
import mdr.MDRElement;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRMySQLModel extends MPDRModel {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLModel(ProjectElement parent, String name) {
        super(parent, name);
    }

    @Override
    public  MPDRMySQLTable createTable(MLDRTable mldrTable){
        MPDRMySQLTable newTable = MVCCDElementFactory.instance().createMPDRMySQLTable(
                getMDRContTables(),  mldrTable);

        return newTable;
    }

}
