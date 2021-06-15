package window.editor.diagrammer.services;

import window.editor.diagrammer.elements.RelationShape;
import window.editor.diagrammer.panels.DrawPanel;

public class DiagrammerService {
  public static DrawPanel drawPanel;

  public DiagrammerService(DrawPanel drawPanel) {
    this.drawPanel = drawPanel;
  }

  public static DrawPanel getDrawPanel() {
    return drawPanel;
  }

}
