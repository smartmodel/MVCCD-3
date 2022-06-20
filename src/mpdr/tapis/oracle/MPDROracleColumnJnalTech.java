package mpdr.tapis.oracle;

import mpdr.MPDRConstraintCustomJnal;
import mpdr.MPDRDB;
import mpdr.tapis.MPDRColumnJnalTech;
import project.ProjectElement;
import stereotypes.Stereotype;

public class MPDROracleColumnJnalTech extends MPDRColumnJnalTech {

    protected MPDRDB mpdrDb ;

    public MPDROracleColumnJnalTech(ProjectElement parent,
                                    MPDRConstraintCustomJnal mpdrConstraintCustomJnal,
                                    Stereotype stereotype) {
        super(parent, mpdrConstraintCustomJnal, stereotype);
    }
}
