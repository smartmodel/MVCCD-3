package diagram.mpdr;

import diagram.mldr.MLDRDiagram;
import project.ProjectElement;

public class MPDRDiagram extends MLDRDiagram {

  private static final long serialVersionUID = 1000;

  public MPDRDiagram(ProjectElement parent) {
    super(parent);
  }

  public MPDRDiagram(ProjectElement parent, String name) {
    super(parent, name);
  }
}