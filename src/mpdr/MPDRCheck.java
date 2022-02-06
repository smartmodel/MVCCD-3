package mpdr;

import md.MDElement;
import mdr.MDRCheck;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.interfaces.IMPDRConstraint;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRElementWithSource;
import project.ProjectElement;

public abstract class MPDRCheck extends MDRCheck implements IMPDRElement, IMPDRElementWithSource,
        IMPDRConstraint {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    private MPDRCheckRole role = null ; // Pour les contraintes de check créées au nivreau physique
    // et ne provenant de la transformation d'une contrainte de niveau logique


    public MPDRCheck(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRCheck(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRCheck(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, id);
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public IMLDRElement getMldrElementSource() {
        return mldrElementSource;
    }

    @Override
    public void setMldrElementSource(IMLDRElement imldrElementSource) {
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }

    public MPDRCheckRole getRole() {
        return role;
    }

    public void setRole(MPDRCheckRole role) {
        this.role = role;
    }

    public MPDRParameter getMPDRParameter(){
        return (MPDRParameter) getMDRParameter() ;
    }

    // Méthode invoquée lors de la création d'une contrainte de Check physique qui n'est pas
    // transformée depuis une contrainte logique.
    // Par exemple, les contraintes de type de données numérique.
    public abstract MPDRParameter createParameter(IMLDRSourceMPDRCheck imldrSourceMPDRCheck);
}
