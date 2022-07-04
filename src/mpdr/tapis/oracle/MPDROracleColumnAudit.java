package mpdr.tapis.oracle;

import mldr.MLDRConstraintCustomAudit;
import mpdr.tapis.MPDRColumnAudit;
import project.ProjectElement;
import stereotypes.Stereotype;

public class MPDROracleColumnAudit extends MPDRColumnAudit {

    public MPDROracleColumnAudit(ProjectElement parent,
                                 MLDRConstraintCustomAudit mldrConstraintCustomAudit,
                                 Stereotype stereotype) {
        super(parent, mldrConstraintCustomAudit, stereotype);
    }

}
