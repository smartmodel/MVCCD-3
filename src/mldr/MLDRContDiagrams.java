package mldr;

import java.io.Serial;
import mdr.MDRContDiagrams;
import project.ProjectElement;

public class MLDRContDiagrams extends MDRContDiagrams {

  @Serial
  private static final long serialVersionUID = -4107481921726398024L;

  public MLDRContDiagrams(ProjectElement parent, int id) {
    super(parent, id);
  }

  public MLDRContDiagrams(ProjectElement parent, String name) {
    super(parent, name);
  }

  public MLDRContDiagrams(ProjectElement parent) {
    super(parent);
  }
}