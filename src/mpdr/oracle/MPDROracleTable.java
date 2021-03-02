package mpdr.oracle;

import exceptions.CodeApplException;
import main.MVCCDElementFactory;
import mdr.MDRConstraint;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDROracleTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDROracleTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        MPDROracleColumn newColumn = MVCCDElementFactory.instance().createMPDROracleColumn(
                getMDRContColumns(),  mldrColumn);

        return newColumn;
    }

    @Override

    public MDRConstraint createConstraint(MDRConstraint mldrConstraint) {
        /*
        if (mldrConstraint instanceof MLDRPK) {
            MPDROraclePK newPK = MVCCDElementFactory.instance().createMPDROracleColumn(
                    getMDRContColumns(),  mldrColumn);
            return newPK;
        }

         */

        throw new CodeApplException("La contrainte "  + mldrConstraint.getName() + " n'est pas reconnue");
    }




    @Override
    public void setMldrElementSource(IMLDRElement imldrElementSource) {

    }
}
