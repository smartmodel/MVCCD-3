package mpdr;

import java.io.Serial;
import mdr.MDRContDiagrams;
import project.ProjectElement;

public class MPDRContDiagrams extends MDRContDiagrams {

  @Serial
  private static final long serialVersionUID = -1528721623584797182L;

  public MPDRContDiagrams(ProjectElement parent, int id) {
    super(parent, id);
  }

  public MPDRContDiagrams(ProjectElement parent, String name) {
    super(parent, name);
  }

  public MPDRContDiagrams(ProjectElement parent) {
    super(parent);
  }
}