package mpdr.postgresql;

import datatypes.MPDRDatatype;
import main.MVCCDElementFactory;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDRPostgreSQLDatatype;

public class MPDRPostgreSQLModel extends MPDRModel {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLModel(ProjectElement parent, String name) {

        super(parent, name, MPDRDB.POSTGRESQL);
    }



    @Override
    public MPDRPostgreSQLTable createTable(MLDRTable mldrTable){
        MPDRPostgreSQLTable newTable = MVCCDElementFactory.instance().createMPDRPostgreSQLTable(
                getMPDRContTables(), mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDRPostgreSQLDatatype.fromMLDRDatatype(mldrColumn);
    }


}
