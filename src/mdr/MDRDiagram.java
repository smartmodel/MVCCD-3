package mdr;

import diagram.MDDiagram;
import java.io.Serial;
import project.ProjectElement;

public class MDRDiagram extends MDDiagram {

  @Serial
  private static final long serialVersionUID = 560728892193743452L;

  public MDRDiagram(ProjectElement parent) {
    super(parent);
  }

  public MDRDiagram(ProjectElement parent, int id) {
    super(parent, id);
  }

  public MDRDiagram(ProjectElement parent, String name) {
    super(parent, name);
  }
}