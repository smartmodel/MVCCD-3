package mpdr.tapis.postgresql;

import generatorsql.generator.postgresql.MPDRPostgreSQLGenerateSQL;
import generatorsql.generator.postgresql.MPDRPostgreSQLGenerateSQLTrigger;
import mldr.interfaces.IMLDRElement;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import mpdr.tapis.MPDRTrigger;
import project.ProjectElement;

public class MPDRPostgreSQLTrigger extends MPDRTrigger implements IMPDRPostgreSQLElement {
    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLTrigger(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRPostgreSQLTrigger(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLTrigger(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public String generateSQLDDL() {
        MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL = new MPDRPostgreSQLGenerateSQL(getMPDRModelParent());
        MPDRPostgreSQLGenerateSQLTrigger mpdrPostgreSQLGenerateSQLTrigger = new MPDRPostgreSQLGenerateSQLTrigger(mpdrPostgreSQLGenerateSQL);
        return mpdrPostgreSQLGenerateSQLTrigger.generateSQLCreateTrigger(this);
    }


}
