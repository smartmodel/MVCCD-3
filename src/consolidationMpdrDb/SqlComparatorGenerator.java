package consolidationMpdrDb;

import mdr.MDRTable;
import mpdr.MPDRModel;

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
