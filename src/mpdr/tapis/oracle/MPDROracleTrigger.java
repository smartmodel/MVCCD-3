package mpdr.tapis.oracle;

import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQLTrigger;
import mldr.interfaces.IMLDRElement;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.MPDRTrigger;
import project.ProjectElement;

public class MPDROracleTrigger extends MPDRTrigger implements IMPDROracleElement {
    private  static final long serialVersionUID = 1000;

    public MPDROracleTrigger(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDROracleTrigger(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleTrigger(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public String generateSQLDDL() {
        MPDROracleGenerateSQL mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(getMPDRModelParent());
        MPDROracleGenerateSQLTrigger mpdrOracleGenerateSQLTrigger = new MPDROracleGenerateSQLTrigger(mpdrOracleGenerateSQL);
        return mpdrOracleGenerateSQLTrigger.generateSQLCreateTrigger(this);
    }


}