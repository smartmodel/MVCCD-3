package mpdr;

import mdr.MDRContTables;
import mdr.MDRModel;
import mldr.MLDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.services.MPDRModelService;
import project.ProjectElement;

public abstract class MPDRModel extends MDRModel  implements IMPDRElement {
    private  static final long serialVersionUID = 1000;

    public MPDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }


    public MPDRTable getMPDRTableByMLDRTableSource(MLDRTable mldrTable){
        return MPDRModelService.getMPDRTableByMLDRTableSource(this, mldrTable);
    }

    public  abstract MPDRTable createTable(MLDRTable mldrTable);



    public MPDRContTables getMPDRContTables(){
        return MPDRModelService.getMPDRContTables(this);
    }



}
