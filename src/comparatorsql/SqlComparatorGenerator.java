package comparatorsql;

import main.MVCCDElementFactory;
import mcd.MCDContModels;
import mcd.interfaces.IMCDModel;
import mdr.MDRModel;
import mdr.MDRTable;
import mldr.MLDRModel;
import mpdr.MPDRModel;
import project.Project;

import java.util.List;

public class SqlComparatorGenerator {

    private MPDRModel mpdrModel;

    public SqlComparatorGenerator(MPDRModel mpdrModel) {

        this.mpdrModel = mpdrModel;
    }

    public void setMpdrModelTable(List<MDRTable> tableList) {
        this.mpdrModel.getMPDRContTables().getMDRTables().addAll(tableList);
    }

    public MPDRModel getMpdrModel() {
        return mpdrModel;
    }
}
