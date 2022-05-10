package window.editor.diagrammer.elements.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import window.editor.diagrammer.elements.interfaces.IResizable;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.SquaredShapeListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.IDManager;

public abstract class SquaredShape extends JPanel implements IShape, IResizable, Serializable {

  private static final long serialVersionUID = 1000;
  protected int id;
  protected boolean isFocused = false;
  protected boolean isResizing = false;

  public SquaredShape(int id) {
    this();
    this.id = id;
  }

  public SquaredShape() {
    super();
    this.id = IDManager.generateId();
    this.addListeners();
    this.initUI();
  }

  protected abstract void defineBackgroundColor();
  protected abstract void defineMinimumSize();
  protected abstract void defineSize();

  private void initUI() {
    this.defineMinimumSize();
    this.defineBackgroundColor();
    this.defineSize();
  }

  private void addListeners() {
    SquaredShapeListener listener = new SquaredShapeListener(this);
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  protected abstract void doDraw(Graphics graphics);

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.isFocused || this.isResizing ? 0 : 0));
    this.doDraw(graphics);
  }

  @Override
  public void resize(Rectangle newBounds) {
    this.setBounds(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public Point getCenter() {
    return new Point((int) this.getBounds().getCenterX(), (int) this.getBounds().getCenterY());
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    Rectangle bounds = this.getBounds();
    bounds.translate(differenceX, differenceY);

    for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapesBySquaredShape(this)) {
      // Récupère le point positionné autour de la forme
      RelationAnchorPointShape pointAroundShape = GeometryUtils.getAnchorPointOnShape(relation, this);

      List<RelationAnchorPointShape> pointsToMove = new LinkedList<>(relation.getAnchorPoints());

      RelationAnchorPointShape firstPoint;
      RelationAnchorPointShape secondPoint;

      if (pointAroundShape == relation.getFirstPoint() && pointAroundShape != null) {
        firstPoint = pointsToMove.get(0);
        secondPoint = pointsToMove.get(1);
      } else {
        firstPoint = pointsToMove.get(pointsToMove.size() - 1);
        secondPoint = pointsToMove.get(pointsToMove.size() - 2);
      }

      if (GeometryUtils.isVertical(new Point(firstPoint.x, firstPoint.y), new Point(secondPoint.x, secondPoint.y))) {
        if (differenceX == 0) {
          firstPoint.drag(firstPoint.x + differenceX, firstPoint.y + differenceY);
        } else {
          firstPoint.drag(firstPoint.x + differenceX, firstPoint.y + differenceY);
          secondPoint.drag(secondPoint.x + differenceX, secondPoint.y + differenceY);
        }
      }

      if (GeometryUtils.isHorizontal(new Point(firstPoint.x, firstPoint.y), new Point(secondPoint.x, secondPoint.y))) {
        if (differenceY == 0) {
          firstPoint.drag(firstPoint.x + differenceX, firstPoint.y + differenceY);
        } else {
          firstPoint.drag(firstPoint.x + differenceX, firstPoint.y + differenceY);
          secondPoint.drag(secondPoint.x + differenceX, secondPoint.y + differenceY);
        }
      }

    }
    this.setBounds(bounds);
    this.repaint();
  }

  @Override
  public boolean isFocused() {
    return this.isFocused;
  }

  @Override
  public void setFocused(boolean selected) {
    this.isFocused = selected;
    this.repaint();
  }

  @Override
  public String toString() {
    return "SquaredShape{" + "id=" + this.id + '}';
  }

  public void setResizing(boolean resizing) {
    this.isResizing = resizing;
  }
}