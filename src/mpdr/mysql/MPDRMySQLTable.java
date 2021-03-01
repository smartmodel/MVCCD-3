package mpdr.mysql;

import main.MVCCDElementFactory;
import md.MDElement;
import mdr.MDRConstraint;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRMySQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRMySQLTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    @Override
    public MPDRMySQLColumn createColumn(MLDRColumn mldrColumn) {
        MPDRMySQLColumn newColumn = MVCCDElementFactory.instance().createMPDRMySQLColumn(
                getMDRContColumns(),  mldrColumn);

        return newColumn;
    }

    @Override
    public MDRConstraint createConstraint(MDRConstraint mldrConstraint) {
        return null;
    }


    /*
    @Override
    public  MPDRMySQLTable createTable(MLDRTable mldrTable){
        MPDRMySQLTable newTable = MVCCDElementFactory.instance().createMPDRMySQLTable(
                getMDRContTables(),  mldrTable);

        return newTable;
    }

     */

}
