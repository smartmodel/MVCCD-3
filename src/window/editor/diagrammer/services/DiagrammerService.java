package window.editor.diagrammer.services;

import window.editor.diagrammer.drawpanel.DrawPanel;

public class DiagrammerService {
  public static DrawPanel drawPanel;

  public DiagrammerService(DrawPanel drawPanel) {
    this.drawPanel = drawPanel;
  }

  public static DrawPanel getDrawPanel() {
    return drawPanel;
  }

}
