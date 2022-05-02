package window.editor.diagrammer.elements.shapes.relations;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import m.interfaces.IMRelation;
import main.MVCCDManager;
import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.Position;

public abstract class RelationShape extends JComponent implements IShape, Serializable {

  private static final long serialVersionUID = 1000;
  protected int id;
  protected List<RelationAnchorPointShape> pointsAncrage = new LinkedList<>();
  protected boolean isFocused = false;
  protected ClassShape source;
  protected IShape destination;
  protected IMRelation relation;
  protected MDElement relatedRepositoryElement;
  protected boolean isReflexive;
  protected List<LabelShape> labels = new ArrayList<>();

  public RelationShape(int id) {
    this.id = id;
  }

  public RelationShape() {
    if (MVCCDManager.instance().getProject() != null) {
      this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
    }
  }

  public RelationShape(ClassShape source, IShape destination, boolean isReflexive) {
    this();
    this.source = source;
    this.destination = destination;
    this.isReflexive = isReflexive;

    this.createPointsAncrage(isReflexive);

    this.setFocusable(true);
  }

  public RelationShape(int id, ClassShape source, IShape destination, boolean isReflexive) {
    this.source = source;
    this.destination = destination;
    this.isReflexive = isReflexive;
    this.id = id;

    this.createPointsAncrage(isReflexive);

    this.setFocusable(true);
  }

  public RelationShape(int id, MDElement relatedRepositoryElement, ClassShape source, IShape destination, boolean isReflexive) {
    this.id = id;
    this.source = source;
    this.destination = destination;
    this.isReflexive = isReflexive;
    this.relatedRepositoryElement = relatedRepositoryElement;

    this.createPointsAncrage(isReflexive);

    this.setFocusable(true);

  }

  public void repaintLabels() {
    this.labels.forEach(LabelShape::repaint);
  }

  public void drawPointsAncrage(Graphics2D graphics2D) {
    for (RelationAnchorPointShape pointAncrage : this.pointsAncrage) {
      graphics2D.fillOval(pointAncrage.x - pointAncrage.DIAMETER / 2, pointAncrage.y - pointAncrage.DIAMETER / 2, pointAncrage.DIAMETER, pointAncrage.DIAMETER);
    }
  }

  public void addPointAncrage(RelationAnchorPointShape pointAncrage, int index) {
    this.pointsAncrage.add(index, pointAncrage);
  }

  public void deletePointAncrage(RelationAnchorPointShape pointAncrage) {
    this.pointsAncrage.remove(pointAncrage);
    this.reindexAllAnchorPoint();
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    for (RelationAnchorPointShape pointAncrage : this.pointsAncrage) {
      pointAncrage.setLocationDifference(differenceX, differenceY);
    }
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public Point getCenter() {
    final RelationAnchorPointShape left = GeometryUtils.getLeftPoint(this.getFirstPoint(), this.getLastPoint());
    final RelationAnchorPointShape right = GeometryUtils.getRightPoint(this.getFirstPoint(), this.getLastPoint());
    final Line2D segment = new Line2D.Double();
    segment.setLine(this.getFirstPoint().x, this.getFirstPoint().y, this.getLastPoint().x, this.getLastPoint().y);

    if (this.pointsAncrage.size() == 2) {
      if (GeometryUtils.isVertical(segment) || GeometryUtils.isHorizontal(segment)) {
        int x = (int) segment.getBounds().getCenterX();
        int y = (int) segment.getBounds().getCenterY();
        return new Point(x, y);
      } else {
        int x = (right.x - left.x) / 2;
        int y = (right.y - left.y) / 2;
        return new Point(left.x + x, left.y + y);
      }
    } else if (this.pointsAncrage.size() % 2 != 0) {
      int indexCenter = Math.round(this.pointsAncrage.size() / 2);

      return new Point(this.pointsAncrage.get(indexCenter).x, this.pointsAncrage.get(indexCenter).y);
    } else {
      int indexCenter = this.pointsAncrage.size() / 2 - 1;
      final RelationAnchorPointShape centerPoint = this.pointsAncrage.get(indexCenter);
      final RelationAnchorPointShape pointNextToCenter = this.pointsAncrage.get(indexCenter + 1);
      return new Point((centerPoint.x + pointNextToCenter.x) / 2, (centerPoint.y + pointNextToCenter.y) / 2);
    }
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {
    for (RelationAnchorPointShape pointAncrageShape : this.pointsAncrage) {
      pointAncrageShape.zoom(fromFactor, toFactor);
    }
  }

  @Override
  public void drag(int differenceX, int differenceY) {
  }

  @Override
  public boolean isFocused() {
    return this.isFocused;
  }

  @Override
  public void setFocused(boolean focused) {
    this.isFocused = focused;
    DiagrammerService.getDrawPanel().repaint();
  }

  @Override
  public void setSize(int width, int height) {
    IShape.super.setSize(width, height);
  }

  @Override
  public void setSize(Dimension dimension) {
  }

  @Override
  public Rectangle getBounds() {
    return this.getPointsAncrageMinRectangle();
  }

  @Override
  public void repaint() {
    this.repaintLabels();
  }

  @Override
  public String toString() {
    return "RelationShape{" + "relatedRepositoryElement=" + this.relatedRepositoryElement + '}';
  }

  public List<RelationAnchorPointShape> getAnchorPoints() {
    return this.pointsAncrage;
  }

  public void drawSegments(Graphics2D graphics2D) {
    if (this.isFocused) {
      graphics2D.setStroke(new BasicStroke(3));
    }

    this.defineLineAspect(graphics2D);

    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Pour chaque point d'ancrage
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (i != this.pointsAncrage.size() - 1) {
        graphics2D.drawLine((int) this.pointsAncrage.get(i).getX(), (int) this.pointsAncrage.get(i).getY(), (int) this.pointsAncrage.get(i + 1).getX(), (int) this.pointsAncrage.get(i + 1).getY());
      }
    }

  }

  public abstract void defineLineAspect(Graphics2D graphics2D);

  public int getPointAncrageIndex(RelationAnchorPointShape pointAncrage) {
    int index = Integer.MAX_VALUE;
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (pointAncrage.equals(this.pointsAncrage.get(i))) {
        index = this.pointsAncrage.get(i).getIndex();
      }
    }
    return index;
  }

  public RelationAnchorPointShape convertPoint2DToPointAncrage(Point2D point2D) {
    return new RelationAnchorPointShape((int) point2D.getX(), (int) point2D.getY());
  }

  public void updateFirstAndLastPointsAncrage(ClassShape shape, boolean isResize) {

    RelationAnchorPointShape point = shape == this.source ? this.getFirstPoint() : this.getLastPoint();

    if (isResize) {
      int cursor = shape.getCursor().getType();
      if (cursor == Cursor.E_RESIZE_CURSOR) {
        if (point.y != shape.getBounds().getMaxY() && point.y != shape.getBounds().getMinY() && point.x != shape.getBounds().getMinX()) {
          point.drag((int) shape.getBounds().getMaxX(), point.y);
        } else {
          if (!GeometryUtils.pointIsAroundShape(point, shape)) {
            point.drag((int) shape.getBounds().getMaxX(), point.y);
          }
        }
      } else if (cursor == Cursor.W_RESIZE_CURSOR) {
        if (point.y != shape.getBounds().getMaxY() && point.y != shape.getBounds().getMinY() && point.x != shape.getBounds().getMaxX()) {
          point.drag((int) shape.getBounds().getMinX(), point.y);
        } else {
          if (!GeometryUtils.pointIsAroundShape(point, shape)) {
            point.drag((int) shape.getBounds().getMinX(), point.y);
          }
        }
      } else if (cursor == Cursor.S_RESIZE_CURSOR) {
        if (point.x != shape.getBounds().getMaxX() && point.x != shape.getBounds().getMinX() && point.y != shape.getBounds().getMinY()) {
          point.drag(point.x, (int) shape.getBounds().getMaxY());
        } else {
          if (!GeometryUtils.pointIsAroundShape(point, shape)) {
            point.drag(point.x, (int) shape.getBounds().getMaxY());
          }
        }
      } else if (cursor == Cursor.N_RESIZE_CURSOR) {
        if (point.x != shape.getBounds().getMaxX() && point.x != shape.getBounds().getMinX() && point.y != shape.getBounds().getMaxY()) {
          point.drag(point.x, (int) shape.getBounds().getMinY());
        } else {
          if (!GeometryUtils.pointIsAroundShape(point, shape)) {
            point.drag(point.x, (int) shape.getBounds().getMinY());
          }
        }
      }
    }
    DiagrammerService.getDrawPanel().repaint();
  }

  private Rectangle getPointsAncrageMinRectangle() {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = 0;
    int maxY = 0;
    for (RelationAnchorPointShape pointAncrage : this.getAnchorPoints()) {
      minX = Math.min(minX, pointAncrage.getBounds().x);
      minY = Math.min(minY, pointAncrage.getBounds().y);
      maxX = Math.max(maxX, pointAncrage.getBounds().x + pointAncrage.getBounds().width);
      maxY = Math.max(maxY, pointAncrage.getBounds().y + pointAncrage.getBounds().height);
    }
    return new Rectangle(minX, minY, maxX - minX, maxY - minY);
  }

  @Override
  public boolean contains(int x, int y) {
    Line2D nearestSegment = this.getNearestSegment(new Point(x, y));
    return nearestSegment != null;
  }

  public ClassShape getSource() {
    return this.source;
  }

  public IShape getDestination() {
    return this.destination;
  }

  public void reindexAllAnchorPoint() {
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      this.pointsAncrage.get(i).setIndex(i);
    }
  }

  public ClassShape getNearestClassShape(RelationAnchorPointShape pointAncrage) {
    return pointAncrage == this.getFirstPoint() ? this.source : (ClassShape) this.destination;
  }

  public List<Line2D> getSegments() {
    final List<Line2D> segments = new ArrayList<>();
    for (int i = 0; i < this.pointsAncrage.size() - 1; i++) {
      Line2D segment = new Line2D.Double();
      segment.setLine(this.pointsAncrage.get(i).getX(), this.pointsAncrage.get(i).getY(), this.pointsAncrage.get(i + 1).getX(), this.pointsAncrage.get(i + 1).getY());
      segments.add(segment);
    }
    return segments;
  }

  public void draw(Graphics2D graphics2D) {
    this.drawSegments(graphics2D);
    if (this.isFocused) {
      this.drawPointsAncrage(graphics2D);
    }
    this.doDraw(graphics2D);
  }

  public abstract void doDraw(Graphics2D graphics2D);

  public void addPointAncrage(Point point) {
    final Line2D nearestSegment = this.getNearestSegment(point);
    if (nearestSegment != null) {
      int newIndex = this.getPointAncrageIndex(this.convertPoint2DToPointAncrage(nearestSegment.getP2()));
      Point nearestPointOnSegment = GeometryUtils.getNearestPointOnLine(nearestSegment.getX1(), nearestSegment.getY1(), nearestSegment.getX2(), nearestSegment.getY2(), point.x, point.y, true, null);
      RelationAnchorPointShape newPointAncrage = new RelationAnchorPointShape(nearestPointOnSegment, newIndex);
      this.addPointAncrage(newPointAncrage, newIndex);
      this.reindexAllAnchorPoint();
      DiagrammerService.getDrawPanel().repaint();
    }
  }

  public Line2D getNearestSegment(Point point) {
    for (Line2D segment : this.getSegments()) {
      if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, point) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
        return segment;
      }
    }
    return null;
  }

  public boolean isReflexive() {
    return this.isReflexive;
  }

  private void createPointsAncrage(boolean isReflexive) {
    final int MARGIN = 50;

    if (isReflexive) {
      RelationAnchorPointShape p1 = new RelationAnchorPointShape((int) this.source.getBounds().getMaxX() - MARGIN, (int) this.source.getBounds().getMinY(), 0);
      RelationAnchorPointShape p2 = new RelationAnchorPointShape((int) this.source.getBounds().getMaxX() - MARGIN, (int) this.source.getBounds().getMinY() - MARGIN, 1);
      RelationAnchorPointShape p3 = new RelationAnchorPointShape((int) this.source.getBounds().getMaxX() + MARGIN, (int) this.source.getBounds().getMinY() - MARGIN, 2);
      RelationAnchorPointShape p4 = new RelationAnchorPointShape((int) this.source.getBounds().getMaxX() + MARGIN, (int) this.source.getBounds().getMinY() + MARGIN, 3);
      RelationAnchorPointShape p5 = new RelationAnchorPointShape((int) this.source.getBounds().getMaxX(), (int) this.source.getBounds().getMinY() + MARGIN, 4);

      this.addPointAncrage(p1, p1.getIndex());
      this.addPointAncrage(p2, p2.getIndex());
      this.addPointAncrage(p3, p3.getIndex());
      this.addPointAncrage(p4, p4.getIndex());
      this.addPointAncrage(p5, p5.getIndex());

      this.isReflexive = true;

    } else {
      if (this.destination instanceof RelationShape) {
        this.generatePointsAncrageWhenDestinationIsRelationShape();
      } else {
        this.generatePointAncrage();
      }
    }
  }

  public abstract void createLabelsAfterRelationShapeEdit();

  public LabelShape createOrUpdateLabel(RelationAnchorPointShape anchorPoint, String text, LabelType type, int distanceInXFromAnchorPoint, int distanceInYFromAnchorPoint) {

    LabelShape labelShape;

    if (this.hasLabel(type)) {
      // La relation a déjà un label du type fourni en paramètre
      labelShape = this.updateLabel(text, type);
    } else {
      // La relation n'as pas encore de label du type fourni en paramètre
      labelShape = this.createLabel(anchorPoint, text, type, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
    }

    return labelShape;

  }

  public LabelShape createLabel(RelationAnchorPointShape anchorPoint, String text, LabelType type, int distanceInXFromAnchorPoint, int distanceInYFromAnchorPoint) {

    // La relation possède déjà un label du type fourni en paramètre
    LabelShape label = new LabelShape(anchorPoint, type, this, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
    label.setText(text);
    label.setVisible(true);

    // Ajoute le label à la liste des labels
    this.labels.add(label);
    return label;

  }

  public LabelShape getLabelByType(LabelType type) {
    List<LabelShape> labelsFound = this.labels.stream().filter(l -> l.getType() == type).collect(Collectors.toList());

    if (labelsFound.isEmpty()) {
      System.err.println("No label found with the type " + type.name());
    } else if (labelsFound.size() > 1) {
      System.err.println("Multiples labels found with type " + type.name());
    } else {
      return labelsFound.get(0);
    }

    return null;
  }

  public LabelShape updateLabel(String newValue, LabelType type) {
    LabelShape label = this.getLabelByType(type);

    if (label != null) {
      label.setText(newValue);
    }

    return label;
  }

  public boolean hasLabel(LabelType type) {
    return this.labels.stream().anyMatch(l -> l.getType() == type);
  }

  public void deleteLabel(LabelType type) {
    LabelShape label = this.getLabelByType(type);

    if (label != null) {
      this.labels.remove(label);
      DiagrammerService.getDrawPanel().remove(label);
    }

  }

  public void addLabelsInDiagrammeur() {
    // Ajoute les labels au diagrammeur
    this.labels.forEach(label -> DiagrammerService.getDrawPanel().add(label, JLayeredPane.DRAG_LAYER));
  }

  public void displayLabels() {
    this.labels.forEach(label -> label.setVisible(true));
    this.repaintLabels();
  }

  public void hideLabels() {
    this.labels.forEach(label -> label.setVisible(false));
    this.repaintLabels();
  }

  public void deleteLabels() {
    this.labels.forEach(label -> DiagrammerService.getDrawPanel().remove(label));
    this.labels.clear();
  }

  public void generatePointsAncrageWhenDestinationIsRelationShape() {
    this.pointsAncrage.clear();

    this.pointsAncrage.add(new RelationAnchorPointShape(this.source.getX(), this.source.getY(), 0));
    this.pointsAncrage.add(new RelationAnchorPointShape(this.destination.getCenter().x, this.destination.getCenter().y, 1));

  }

  /**
   * Génère les points d'ancrage lorsque l'association est créée entre deux SquaredShape
   * @return Une liste contenant les points d'ancrage de l'association
   */
  public void generatePointAncrage() {

    this.pointsAncrage.clear();

    Position sourcePosition = GeometryUtils.getSourceShapePosition(this.source, (ClassShape) this.destination);
    Rectangle sourceBounds = this.source.getBounds();
    Rectangle destBounds = this.destination.getBounds();

    int x;
    int y;

    if (sourcePosition == Position.BOTTOM_CENTER_RIGHT) {
      x = (int) (sourceBounds.getMinX() + ((destBounds.getMaxX() - sourceBounds.getMinX()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) sourceBounds.getMinY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) destBounds.getMaxY(), 1));
    } else if (sourcePosition == Position.BOTTOM_CENTER_LEFT) {
      x = (int) (sourceBounds.getMaxX() - ((sourceBounds.getMaxX() - destBounds.getMinX()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) sourceBounds.getMinY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) destBounds.getMaxY(), 1));
    } else if (sourcePosition == Position.TOP_CENTER_RIGHT) {
      x = (int) (sourceBounds.getMinX() + ((destBounds.getMaxX() - sourceBounds.getMinX()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) sourceBounds.getMaxY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) destBounds.getMinY(), 1));
    } else if (sourcePosition == Position.TOP_CENTER_LEFT) {
      x = (int) (sourceBounds.getMaxX() - ((sourceBounds.getMaxX() - destBounds.getMinX()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) sourceBounds.getMaxY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape(x, (int) destBounds.getMinY(), 1));
    } else if (sourcePosition == Position.LEFT_CENTER_TOP) {
      y = (int) (sourceBounds.getMaxY() - ((sourceBounds.getMaxY() - destBounds.getMinY()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getMaxX(), y, 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMinX(), y, 1));
    } else if (sourcePosition == Position.LEFT_CENTER_BOTTOM) {
      y = (int) (sourceBounds.getMinY() + ((destBounds.getMaxY() - sourceBounds.getMinY()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getMaxX(), y, 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMinX(), y, 1));
    } else if (sourcePosition == Position.RIGHT_CENTER_TOP) {
      y = (int) (sourceBounds.getMinY() + ((sourceBounds.getMaxY() - destBounds.getMinY()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getMinX(), y, 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMaxX(), y, 1));
    } else if (sourcePosition == Position.RIGHT_CENTER_BOTTOM) {
      y = (int) (sourceBounds.getMinY() + ((destBounds.getMaxY() - sourceBounds.getMinY()) / 2));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getMinX(), y, 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMaxX(), y, 1));
    } else if (sourcePosition == Position.TOP_CORNER_RIGHT) {
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMaxY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMaxX(), (int) destBounds.getCenterY(), 2));
    } else if (sourcePosition == Position.TOP_CORNER_LEFT) {
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMaxY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMinX(), (int) destBounds.getCenterY(), 2));
    } else if (sourcePosition == Position.BOTTOM_CORNER_RIGHT) {
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMinY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMaxX(), (int) destBounds.getCenterY(), 2));
    } else if (sourcePosition == Position.BOTTOM_CORNER_LEFT) {
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMinY(), 0));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
      this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMinX(), (int) destBounds.getCenterY(), 2));
    } else {
      if (sourceBounds.intersects(destBounds)) {
        this.pointsAncrage.add(new RelationAnchorPointShape((int) sourceBounds.getMaxX(), (int) sourceBounds.getCenterY(), 0));
        this.pointsAncrage.add(new RelationAnchorPointShape((int) destBounds.getMaxX(), (int) destBounds.getCenterY(), 0));
      }
    }
  }

  public RelationAnchorPointShape getLastPoint() {
    return this.pointsAncrage.get(this.pointsAncrage.size() - 1);
  }

  public RelationAnchorPointShape getFirstPoint() {
    return this.pointsAncrage.get(0);
  }

  public boolean isFirstOrLastPoint(RelationAnchorPointShape pointAncrage) {
    return this.isFirstPoint(pointAncrage) || this.isLastPoint(pointAncrage);
  }

  public boolean isFirstPoint(RelationAnchorPointShape pointAncrage) {
    return pointAncrage == this.getFirstPoint();
  }

  public boolean isLastPoint(RelationAnchorPointShape pointAncrage) {
    return pointAncrage == this.getLastPoint();
  }

  public MDElement getRelatedRepositoryElement() {
    return this.relatedRepositoryElement;
  }

  public abstract String getXmlTagName();

  public List<LabelShape> getLabels() {
    return this.labels;
  }

}