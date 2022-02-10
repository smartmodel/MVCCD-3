package mpdr;

import md.MDElement;
import mdr.MDRRelationFK;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRRelation;
import project.ProjectElement;

public class MPDRRelationFK  extends MDRRelationFK implements  IMPDRRelation{

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRRelationFK(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent);
        this.mldrElementSource = mldrElementSource;
    }

    public MPDRRelationFK(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name);
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public IMLDRElement getMldrElementSource() {
        return mldrElementSource;
    }

    //TODO-0 A supprimer cette spécification de l'interface car l'affectation se fait par le constructeur
    @Override
    public void setMldrElementSource(IMLDRElement mldrElementSource) {
        this.mldrElementSource = mldrElementSource;
    }

    @Override
    public MDElement getMdElementSource() {
        return (MDElement) getMldrElementSource();
    }

}
