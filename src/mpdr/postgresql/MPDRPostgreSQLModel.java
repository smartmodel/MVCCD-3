package mpdr.postgresql;

import main.MVCCDElementFactory;
import mldr.MLDRTable;
import mpdr.MPDRModel;
import project.ProjectElement;

public class MPDRPostgreSQLModel extends MPDRModel {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLModel(ProjectElement parent, String name) {

        super(parent, name);
    }



    @Override
    public MPDRPostgreSQLTable createTable(MLDRTable mldrTable){
        MPDRPostgreSQLTable newTable = MVCCDElementFactory.instance().createMPDRPostgreSQLTable(
                getMDRContTables(), mldrTable);

        return newTable;
    }


}
