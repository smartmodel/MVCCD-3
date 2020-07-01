package diagram.mcd;

import diagram.MDDiagram;
import mcd.MCDEntity;
import project.ProjectElement;

import java.util.ArrayList;

public class MCDDiagram extends MDDiagram {
    private static final long serialVersionUID = 1000;
    ArrayList<MCDEntity> entitesDiagramme;

    public MCDDiagram(ProjectElement parent) {
        super(parent);
    }

    public MCDDiagram(ProjectElement parent, String name) {
        super(parent, name);
    }

    public ArrayList<MCDEntity> getEntitesDiagramme() {
        return entitesDiagramme;
    }

    public void setEntitesDiagramme(ArrayList<MCDEntity> entitesDiagramme) {
        this.entitesDiagramme = entitesDiagramme;
    }
}
