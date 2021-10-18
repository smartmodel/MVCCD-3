package window.editor.diagrammer.services;

import window.editor.diagrammer.drawpanel.DrawPanel;

public final class DiagrammerService {

  private static DrawPanel drawPanel;

  public static DrawPanel getDrawPanel() {
    if (drawPanel == null) {
      drawPanel = new DrawPanel();
    }
    return drawPanel;
  }

}
