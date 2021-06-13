package window.editor.diagrammer;

import window.editor.diagrammer.utils.DiagrammerConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Cette classe représente le composant du diagrammer. Le DrawPanelComponent est un JScrollPane dont la vue est un DrawPanel (la zone de dessin).
 *
 */
public class DrawPanelComponent extends JScrollPane {
    public DrawPanelComponent(DrawPanel drawPanel) {
        super(drawPanel);
        this.setName(DiagrammerConstants.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME);

        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        this.setViewportView(drawPanel);
        this.getViewport().setPreferredSize(new Dimension(1,1)); // On set width = 1 et height = 1 pour éviter que le viewport prenne la dimension du DrawPanel (de la zone de dessin)
    }
}
