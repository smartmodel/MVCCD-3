package mdr;

import java.io.Serial;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

public class MDRContDiagrams extends MDRElement implements IMPathOnlyRepositoryTree {

  @Serial
  private static final long serialVersionUID = 6286351407673559198L;

  public MDRContDiagrams(ProjectElement parent, int id) {
    super(parent, id);
  }

  public MDRContDiagrams(ProjectElement parent, String name) {
    super(parent, name);
  }

  public MDRContDiagrams(ProjectElement parent) {
    super(parent);
  }
}