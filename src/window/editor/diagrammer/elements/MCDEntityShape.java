package window.editor.diagrammer.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.ResizableBorder;

public class MCDEntityShape extends ClassShape {

  public MCDEntityShape() {
    super();
    this.setBackground(Color.MAGENTA);

    // On ajoute les différents listeners
    this.addListeners();

    // Met à jour la taille minimale du composant
    this.setMinimumSize(new Dimension(DiagrammerConstants.DEFAULT_ENTITY_WIDTH, DiagrammerConstants.DEFAULT_ENTITY_HEIGHT));

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  private void addListeners(){
    MCDEntityShapeListener listener = new MCDEntityShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

}
