package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;
import window.editor.diagrammer.elements.interfaces.IResizable;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.listeners.SquaredShapeListener;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.ResizableBorder;

public abstract class SquaredShape extends JPanel implements IShape, IResizable {

  protected boolean isSelected = false;
  protected final ResizableBorder BORDER = new ResizableBorder();

  public SquaredShape() {
    this.addListeners();
    this.setBorder(this.BORDER);
  }

  public Point getCenter() {
    return new Point((int) this.getBounds().getCenterX(), (int) this.getBounds().getCenterY());
  }

  private void addListeners() {
    final SquaredShapeListener listener = new SquaredShapeListener(this);
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  @Override
  public ResizableBorder getBorder() {
    return this.BORDER;
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {
    final int newXPosition = this.getBounds().x * toFactor / fromFactor;
    final int newYPosition = this.getBounds().y * toFactor / fromFactor;
    final int newWidth = this.getBounds().width * toFactor / fromFactor;
    final int newHeight = this.getBounds().height * toFactor / fromFactor;

    // Set la nouvelle position, la nouvelle taille de l'élément et met à jour la nouvelle taille minimale de l'élément
    this.setSize(GridUtils.alignToGrid(newWidth, toFactor), GridUtils.alignToGrid(newHeight, toFactor));
    this.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
    this.setLocation(GridUtils.alignToGrid(newXPosition, toFactor), GridUtils.alignToGrid(newYPosition, toFactor));
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    final Rectangle bounds = this.getBounds();
    bounds.translate(differenceX, differenceY);
    this.setBounds(bounds);
    this.repaint();
  }

  @Override
  public boolean isSelected() {
    return this.isSelected;
  }

  @Override
  public void setSelected(boolean selected) {
    this.isSelected = selected;
    this.BORDER.setVisible(selected);
  }

  @Override
  public void resize(Rectangle newBounds) {
    this.setBounds(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
  }

}
