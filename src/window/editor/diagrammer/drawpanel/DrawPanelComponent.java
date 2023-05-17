/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente le composant qui contient la zone de dessin d'ArcDataModeler.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.drawpanel;

import preferences.Preferences;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

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
