package mpdr;

import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRConstraintSpecific;
import project.ProjectElement;

public abstract class MPDRCheckSpecific extends MPDRCheck implements IMPDRConstraintSpecific {
    private  static final long serialVersionUID = 1000;

    private MPDRConstraintSpecificRole role = null ; // Pour les contraintes de check créées au nivreau physique
    // et ne provenant de la transformation d'une contrainte de niveau logique

    public MPDRCheckSpecific(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    public MPDRConstraintSpecificRole getRole() {
        return role;
    }

    public void setRole(MPDRConstraintSpecificRole role) {
        this.role = role;
    }

}
