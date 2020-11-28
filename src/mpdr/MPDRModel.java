package mpdr;

import main.MVCCDElementFactory;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.services.MCDElementService;
import mdr.MDRContTables;
import mdr.MDRElement;
import mldr.MLDRTable;
import mpdr.interfaces.IMPDRElement;
import mpdr.services.MPDRModelService;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;

public abstract class MPDRModel extends MDRElement implements IMPDRElement {
    private  static final long serialVersionUID = 1000;

    public MPDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }


    public MPDRTable getMPDRTableByMLDRTableSource(MLDRTable mldrTable){
        return MPDRModelService.getMPDRTableByMLDRTableSource(this, mldrTable);
    }

    public  abstract MPDRTable createTable(MLDRTable mldrTable);

    public void modifyTable(MPDRTable mpdrTable, MLDRTable mldrTable){
        String nameTable = buildNameTable(mldrTable);
        if (!nameTable.equals(mpdrTable.getName())){
            mpdrTable.setName(nameTable);
        }
    }

    public MDRContTables getMDRContTables(){
        return MPDRModelService.getMDRContTables(this);
    }

    protected String buildNameTable(MLDRTable mldrTable){

        return mldrTable.getName();
    }

}
