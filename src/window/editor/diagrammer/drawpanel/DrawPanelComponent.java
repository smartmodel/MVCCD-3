package window.editor.diagrammer.drawpanel;

import java.awt.Dimension;
import java.io.Serializable;
import javax.swing.JScrollPane;
import preferences.Preferences;

/**
 * Cette classe représente le composant du diagrammer. Le DrawPanelComponent est un JScrollPane dont la vue est un DrawPanel (la zone de dessin).
 */
public class DrawPanelComponent extends JScrollPane implements Serializable {

  private static final long serialVersionUID = 1000;

  public DrawPanelComponent(DrawPanel drawPanel) {
    super(drawPanel);
    this.setName(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME);
    this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    this.setViewportView(drawPanel);
    this.getViewport().setPreferredSize(new Dimension(1, 1)); // On set width = 1 et height = 1 pour éviter que le viewport prenne la dimension du DrawPanel (de la zone de dessin)
  }
}
