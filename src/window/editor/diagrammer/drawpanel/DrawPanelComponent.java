package window.editor.diagrammer.drawpanel;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import preferences.Preferences;

/**
 * Cette classe représente le composant du diagrammer. Le DrawPanelComponent est un JScrollPane dont la vue est un DrawPanel (la zone de dessin).
 */
public class DrawPanelComponent extends JScrollPane implements Serializable {

  private static final long serialVersionUID = 1000;

  public DrawPanelComponent(DrawPanel drawPanel) {
    super(drawPanel);
    this.setName(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME);

    this.getHorizontalScrollBar().setUnitIncrement(50); // Using mousewheel on bar or click on arrow
    this.getHorizontalScrollBar().setSize(0, 15);
    this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.getVerticalScrollBar().setUnitIncrement(50);
    this.getVerticalScrollBar().setSize(15, 0);
    this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    this.setViewportView(drawPanel);

    this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
  }

  @Override
  protected void paintComponent(Graphics g) {

  }
}