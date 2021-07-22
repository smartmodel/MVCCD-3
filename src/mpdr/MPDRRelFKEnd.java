package mpdr;

import md.MDElement;
import mdr.MDRRelFKEnd;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRelEnd;
import project.ProjectElement;

public class MPDRRelFKEnd extends MDRRelFKEnd implements IMPDRelEnd {

    private  static final long serialVersionUID = 1000;
    private IMLDRElement mldrElementSource;

    public MPDRRelFKEnd(ProjectElement parent) {
        super(parent);
    }

    public MPDRRelFKEnd(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MPDRRelFKEnd(ProjectElement parent, String name) {
        super(parent, name);
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
