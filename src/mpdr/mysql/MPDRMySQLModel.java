package mpdr.mysql;

import datatypes.MPDRDatatype;
import main.MVCCDElementFactory;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import preferences.Preferences;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDRMySQLDatatype;

public class MPDRMySQLModel extends MPDRModel {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLModel(ProjectElement parent, String name) {
        super(parent, name, MPDRDB.MYSQL);
    }

    @Override
    public  MPDRMySQLTable createTable(MLDRTable mldrTable){
        MPDRMySQLTable newTable = MVCCDElementFactory.instance().createMPDRMySQLTable(
                getMPDRContTables(),  mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDRMySQLDatatype.fromMLDRDatatype(mldrColumn);
    }

    @Override
    protected String getTemplateBDDirectory() {
        return Preferences.DIRECTORY_TEMPLATES_MYSQL_NAME;
    }

}
