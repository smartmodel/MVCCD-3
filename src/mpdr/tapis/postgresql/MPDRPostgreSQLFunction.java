package mpdr.tapis.postgresql;

import generatorsql.generator.postgresql.MPDRPostgreSQLGenerateSQL;
import generatorsql.generator.postgresql.MPDRPostgreSQLGenerateSQLFunction;
import mldr.interfaces.IMLDRElement;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import mpdr.tapis.MPDRFunction;
import project.ProjectElement;

public class MPDRPostgreSQLFunction extends MPDRFunction implements IMPDRPostgreSQLElement {
    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLFunction(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRPostgreSQLFunction(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLFunction(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public String generateSQLDDL() {
        MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL = new MPDRPostgreSQLGenerateSQL(getMPDRModelParent());
        MPDRPostgreSQLGenerateSQLFunction mpdrPostgreSQLGenerateSQLFunction = new MPDRPostgreSQLGenerateSQLFunction(mpdrPostgreSQLGenerateSQL);
        return mpdrPostgreSQLGenerateSQLFunction.generateSQLCreateFunction(this);
    }


}
