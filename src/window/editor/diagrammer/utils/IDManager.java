package window.editor.diagrammer.utils;

import main.MVCCDManager;

public class IDManager {

  public static int generateId() {
    if (MVCCDManager.instance().getProject() != null) {
      return MVCCDManager.instance().getProject().getNextIdElementSequence();
    } else {
      return Integer.MAX_VALUE;
    }
  }
}