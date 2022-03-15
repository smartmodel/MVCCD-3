package versioning;

import main.MVCCDManager;

public class ProjectChecker implements IVersioning {

  @Override
  public void adjust_3_0_19() {
    System.out.println("Ajustements du projet");
  }
}
