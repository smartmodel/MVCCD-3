package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import window.editor.diagrammer.elements.interfaces.IResizable;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.listeners.SquaredShapeListener;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.ResizableBorder;

public abstract class SquaredShape extends JPanel implements IShape, IResizable {


  public SquaredShape() {
    this.addListeners();
    this.setBorder(new ResizableBorder());
  }


  @Override
  public void zoom(int fromFactor, int toFactor) {
    int newXPosition = this.getBounds().x * toFactor / fromFactor;
    int newYPosition = this.getBounds().y * toFactor / fromFactor;
    int newWidth = this.getBounds().width * toFactor / fromFactor;
    int newHeight = this.getBounds().height * toFactor / fromFactor;

    // Set la nouvelle position, la nouvelle taille de l'élément et met à jour la nouvelle taille minimale de l'élément
    this.setSize(GridUtils.alignToGrid(newWidth, toFactor), GridUtils.alignToGrid(newHeight, toFactor));
    this.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
    this.setLocation(GridUtils.alignToGrid(newXPosition, toFactor), GridUtils.alignToGrid(newYPosition, toFactor));
  }


  @Override
  public void resize(Rectangle newBounds) {
    this.setBounds(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    Rectangle bounds = this.getBounds();
    bounds.translate(differenceX, differenceY);
    this.setBounds(bounds);
    this.repaint();
  }

  private void addListeners(){
    SquaredShapeListener listener = new SquaredShapeListener(this);
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }
}
