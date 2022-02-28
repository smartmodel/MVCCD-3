package mpdr.tapis.oracle;

import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQLPackage;
import mldr.interfaces.IMLDRElement;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.MPDRPackage;
import project.ProjectElement;

public class MPDROraclePackage extends  MPDRPackage implements IMPDROracleElement {
    private  static final long serialVersionUID = 1000;

    public MPDROraclePackage(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDROraclePackage(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROraclePackage(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


    @Override
    public String generateSQLDDL() {
        MPDROracleGenerateSQL mpdrOracleGenerateSQL = new MPDROracleGenerateSQL(getMPDRModelParent());
        MPDROracleGenerateSQLPackage mpdrOracleGenerateSQLPackage = new MPDROracleGenerateSQLPackage(mpdrOracleGenerateSQL);
        return mpdrOracleGenerateSQLPackage.generateSQLCreatePackage(this);
    }


}
