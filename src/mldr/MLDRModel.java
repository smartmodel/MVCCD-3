package mldr;

import main.MVCCDElementFactory;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.services.MCDElementService;
import mdr.MDRContTables;
import mdr.MDRElement;
import mdr.MDRModel;
import mldr.interfaces.IMLDRElement;
import mldr.services.MLDRModelService;
import mldr.transform.MLDRTransform;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MLDRModel extends MDRModel implements IMLDRElement {

    private  static final long serialVersionUID = 1000;

    public MLDRModel(ProjectElement parent, String name) {
        super(parent, name);
    }


    public MLDRTable createTable(MCDEntity mcdEntity){
        MLDRTable newTable = MVCCDElementFactory.instance().createMLDRTable(
                getMDRContTables(), mcdEntity);

        return newTable;
    }

    public ArrayList<MLDRTable> getMLDRTables() {
        return MLDRModelService.getMLDRTables(this);
    }

    public MDRContTables getMDRContTables(){
        return MLDRModelService.getMDRContTables(this);
    }

    public MLDRTable getMLDRTableByEntitySource(MCDEntity mcdEntity){
        return MLDRModelService.getMLDRTableByEntitySource(this, mcdEntity);
    }



    public ArrayList<String> treatTransform(){
        MLDRTransform mldrTransform = new MLDRTransform();
        ArrayList<String> resultat = mldrTransform.transform(this);

        return resultat;

    }
}
