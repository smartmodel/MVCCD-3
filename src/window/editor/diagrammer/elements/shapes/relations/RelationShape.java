package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
import project.ProjectService;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.Position;

public abstract class RelationShape extends JComponent implements IShape {

  protected int id;
  protected List<RelationPointAncrageShape> pointsAncrage = new LinkedList<>();
  protected boolean isSelected = false;
  protected MCDEntityShape source;
  protected MCDEntityShape destination;
  protected IMRelation relation;
  protected MDElement relatedRepositoryElement;
  protected boolean isReflexive;
  protected List<LabelShape> labels = new ArrayList<>();

  public RelationShape(int id) {
    this.id = id;
  }

  public RelationShape() {
    if (MVCCDManager.instance().getProject() != null)
      this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
  }

  public RelationShape(MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    this();
    this.source = source;
    this.destination = destination;
    this.isReflexive = isReflexive;

    this.createPointsAncrage(isReflexive);

    this.setFocusable(true);

  }

  public RelationShape(int id, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {

    this.source = source;
    this.destination = destination;
    this.isReflexive = isReflexive;
    this.id = id;

    this.createPointsAncrage(isReflexive);

    this.setFocusable(true);

  }

  public RelationShape(int id, MDElement relatedRepositoryElement, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    this.id = id;
    this.source = source;
    this.destination = destination;
    this.isReflexive = isReflexive;
    this.relatedRepositoryElement = relatedRepositoryElement;

    this.createPointsAncrage(isReflexive);

    this.setFocusable(true);

  }

  public void repaintLabels(){
    labels.forEach(LabelShape::repaint);
  }

  public void drawPointsAncrage(Graphics2D graphics2D) {
    for (RelationPointAncrageShape pointAncrage : this.pointsAncrage) {
      graphics2D.fillOval(pointAncrage.x - pointAncrage.DIAMETER / 2, pointAncrage.y - pointAncrage.DIAMETER / 2, pointAncrage.DIAMETER, pointAncrage.DIAMETER);
    }
  }

  public void addPointAncrage(RelationPointAncrageShape pointAncrage, int index) {
    this.pointsAncrage.add(index, pointAncrage);
  }

  public void deletePointAncrage(RelationPointAncrageShape pointAncrage) {
    this.pointsAncrage.remove(pointAncrage);
    this.reindexAllPointsAncrage();
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    for (RelationPointAncrageShape pointAncrage : this.pointsAncrage) {
      pointAncrage.setLocationDifference(differenceX, differenceY);
    }
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {
    for (RelationPointAncrageShape pointAncrageShape : this.pointsAncrage) {
      pointAncrageShape.zoom(fromFactor, toFactor);
    }
  }

  @Override
  public void repaint(){
    repaintLabels();
  }

  @Override
  public void drag(int differenceX, int differenceY) {
  }

  @Override
  public boolean isSelected() {
    return this.isSelected;
  }

  @Override
  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
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

  public List<RelationPointAncrageShape> getPointsAncrage() {
    return this.pointsAncrage;
  }

  public void drawSegments(Graphics2D graphics2D) {
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // Pour chaque point d'ancrage
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (i != this.pointsAncrage.size() - 1) {
        graphics2D.drawLine((int) this.pointsAncrage.get(i).getX(), (int) this.pointsAncrage.get(i).getY(), (int) this.pointsAncrage.get(i + 1).getX(), (int) this.pointsAncrage.get(i + 1).getY());
      }
    }
  }

  public int getPointAncrageIndex(RelationPointAncrageShape pointAncrage) {
    int index = Integer.MAX_VALUE;
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (pointAncrage.equals(this.pointsAncrage.get(i))) {
        index = this.pointsAncrage.get(i).getIndex();
      }
    }
    return index;
  }

  public RelationPointAncrageShape convertPoint2DToPointAncrage(Point2D point2D) {
    return new RelationPointAncrageShape((int) point2D.getX(), (int) point2D.getY());
  }

  public void updateFirstAndLastPointsAncrage(ClassShape shape, boolean isResize) {

    RelationPointAncrageShape point;
    if (shape == this.source) {
      point = this.getFirstPoint();
    } else {
      point = this.getLastPoint();
    }
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
    for (RelationPointAncrageShape pointAncrage : this.getPointsAncrage()) {
      minX = Math.min(minX, pointAncrage.getBounds().x);
      minY = Math.min(minY, pointAncrage.getBounds().y);
      maxX = Math.max(maxX, pointAncrage.getBounds().x + pointAncrage.getBounds().width);
      maxY = Math.max(maxY, pointAncrage.getBounds().y + pointAncrage.getBounds().height);
    }
    return new Rectangle(minX, minY, maxX - minX, maxY - minY);
  }

  public MCDEntityShape getSource() {
    return this.source;
  }

  public MCDEntityShape getDestination() {
    return this.destination;
  }

  public void reindexAllPointsAncrage() {
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      this.pointsAncrage.get(i).setIndex(i);
    }
  }

  public ClassShape getNearestClassShape(RelationPointAncrageShape pointAncrage) {
    return pointAncrage == this.getFirstPoint() ? this.source : this.destination;
  }

  public Point getCenter() {
    final RelationPointAncrageShape left = GeometryUtils.getLeftPoint(this.getFirstPoint(), this.getLastPoint());
    final RelationPointAncrageShape right = GeometryUtils.getRightPoint(this.getFirstPoint(), this.getLastPoint());
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
      final RelationPointAncrageShape centerPoint = this.pointsAncrage.get(indexCenter);
      final RelationPointAncrageShape pointNextToCenter = this.pointsAncrage.get(indexCenter + 1);
      return new Point((centerPoint.x + pointNextToCenter.x) / 2, (centerPoint.y + pointNextToCenter.y) / 2);
    }
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
    this.doDraw(graphics2D);
  }

  public abstract void doDraw(Graphics2D graphics2D);

  public void addPointAncrage(Point point) {
    final Line2D nearestSegment = this.getNearestSegment(point);
    if (nearestSegment != null) {
      int newIndex = this.getPointAncrageIndex(this.convertPoint2DToPointAncrage(nearestSegment.getP2()));
      Point nearestPointOnSegment = GeometryUtils.getNearestPointOnLine(nearestSegment.getX1(), nearestSegment.getY1(), nearestSegment.getX2(), nearestSegment.getY2(), point.x, point.y, true, null);
      RelationPointAncrageShape newPointAncrage = new RelationPointAncrageShape(nearestPointOnSegment, newIndex);
      this.addPointAncrage(newPointAncrage, newIndex);
      this.reindexAllPointsAncrage();
      DiagrammerService.getDrawPanel().repaint();
    }
  }

  private Line2D getNearestSegment(Point point) {
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
      RelationPointAncrageShape p1 = new RelationPointAncrageShape((int) this.source.getBounds().getMaxX() - MARGIN, (int) this.source.getBounds().getMinY(), 0);
      RelationPointAncrageShape p2 = new RelationPointAncrageShape((int) this.source.getBounds().getMaxX() - MARGIN, (int) this.source.getBounds().getMinY() - MARGIN, 1);
      RelationPointAncrageShape p3 = new RelationPointAncrageShape((int) this.source.getBounds().getMaxX() + MARGIN, (int) this.source.getBounds().getMinY() - MARGIN, 2);
      RelationPointAncrageShape p4 = new RelationPointAncrageShape((int) this.source.getBounds().getMaxX() + MARGIN, (int) this.source.getBounds().getMinY() + MARGIN, 3);
      RelationPointAncrageShape p5 = new RelationPointAncrageShape((int) this.source.getBounds().getMaxX(), (int) this.source.getBounds().getMinY() + MARGIN, 4);

      this.addPointAncrage(p1, p1.getIndex());
      this.addPointAncrage(p2, p2.getIndex());
      this.addPointAncrage(p3, p3.getIndex());
      this.addPointAncrage(p4, p4.getIndex());
      this.addPointAncrage(p5, p5.getIndex());

      this.isReflexive = true;

    } else {
      this.pointsAncrage.addAll(this.generatePointAncrage());
    }
  }

  public abstract void createLabelsAfterRelationShapeEdit();

  public void createOrUpdateLabel(RelationPointAncrageShape anchorPoint, String text, LabelType type, int distanceInXFromAnchorPoint, int distanceInYFromAnchorPoint) {

      if (hasLabel(type))
        // La relation a déjà un label du type fourni en paramètre
        updateLabel(text, type);
      else
        // La relation n'as pas encore de label du type fourni en paramètre
        createLabel(anchorPoint, text, type, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

  }

  public void createLabel(RelationPointAncrageShape anchorPoint, String text, LabelType type, int distanceInXFromAnchorPoint, int distanceInYFromAnchorPoint){

    // La relation possède déjà un label du type fourni en paramètre
    LabelShape label = new LabelShape(anchorPoint, type, this, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
    label.setText(text);
    label.setVisible(true);

    // Ajoute le label à la liste des labels et dans le diagrammer
    labels.add(label);
    DiagrammerService.getDrawPanel().add(label, JLayeredPane.DRAG_LAYER);

  }

  public LabelShape getLabelByType(LabelType type){
    List<LabelShape> labelsFound = labels.stream().filter(l -> l.getType() == type).collect(Collectors.toList());

    if (labelsFound.isEmpty())
      System.err.println("No label found with the type " + type.name());
    else if (labelsFound.size() > 1)
      System.err.println("Multiples labels found with type " + type.name());
    else
      return labelsFound.get(0);
    return null;
  }

  public void updateLabel(String newValue, LabelType type){
    LabelShape label = getLabelByType(type);

    if (label != null){
      label.setText(newValue);
      System.out.println("Label " + type.name() + " updated with new value \"" + newValue + "\"");
    }
  }

  public boolean hasLabel(LabelType type){
    return labels.stream().filter(l -> l.getType() == type).count() > 0;
  }

  public void deleteLabel(LabelType type){
    LabelShape label = getLabelByType(type);

    if (label != null){
      labels.remove(label);
      DiagrammerService.getDrawPanel().remove(label);
      System.out.println("Label " + type + " removed");
    }

  }

  public void addLabelsInDiagrammeur(){
    // Ajoute les labels au diagrammeur
    labels.forEach(label -> DiagrammerService.getDrawPanel().add(label, JLayeredPane.DRAG_LAYER));
    System.out.println("Labels added in diagrammer");
  }

  public void displayLabels(){
    labels.forEach(label -> label.setVisible(true));
    repaintLabels();
    System.out.println("Labels displayed");
  }

  public void hideLabels(){
    labels.forEach(label -> label.setVisible(false));
    repaintLabels();
    System.out.println("Labels hidden");
  }

  public void deleteLabels() {
    labels.forEach(label -> DiagrammerService.getDrawPanel().remove(label));
    labels.clear();
 }

  /**
   * Génère les points d'ancrage lorsque l'association est créée
   * @return Une liste contenant les points d'ancrage de l'association
   */
  public List<RelationPointAncrageShape> generatePointAncrage() {

    this.pointsAncrage.clear();

    final Position sourcePosition = GeometryUtils.getClassShapePosition(this.source, this.destination);
    final Rectangle sourceBounds = this.source.getBounds();
    final Rectangle destBounds = this.destination.getBounds();
    final List<RelationPointAncrageShape> pointsAncrage = new ArrayList<>();

    int x;
    int y;

    if (sourcePosition == Position.BOTTOM_CENTER_RIGHT) {
        x = (int) (sourceBounds.getMinX() + ((destBounds.getMaxX() - sourceBounds.getMinX()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) sourceBounds.getMinY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) destBounds.getMaxY(), 1));
    } else if (sourcePosition == Position.BOTTOM_CENTER_LEFT) {
        x = (int) (sourceBounds.getMaxX() - ((sourceBounds.getMaxX() - destBounds.getMinX()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) sourceBounds.getMinY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) destBounds.getMaxY(), 1));
    } else if (sourcePosition == Position.TOP_CENTER_RIGHT) {
        x = (int) (sourceBounds.getMinX() + ((destBounds.getMaxX() - sourceBounds.getMinX()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) sourceBounds.getMaxY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) destBounds.getMinY(), 1));
    } else if (sourcePosition == Position.TOP_CENTER_LEFT) {
        x = (int) (sourceBounds.getMaxX() - ((sourceBounds.getMaxX() - destBounds.getMinX()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) sourceBounds.getMaxY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape(x, (int) destBounds.getMinY(), 1));
    } else if (sourcePosition == Position.LEFT_CENTER_TOP) {
        y = (int) (sourceBounds.getMaxY() - ((sourceBounds.getMaxY() - destBounds.getMinY()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getMaxX(), y, 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMinX(), y, 1));
    } else if (sourcePosition == Position.LEFT_CENTER_BOTTOM) {
        y = (int) (sourceBounds.getMinY() + ((destBounds.getMaxY() - sourceBounds.getMinY()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getMaxX(), y, 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMinX(), y, 1));
    } else if (sourcePosition == Position.RIGHT_CENTER_TOP) {
        y = (int) (sourceBounds.getMinY() + ((sourceBounds.getMaxY() - destBounds.getMinY()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getMinX(), y, 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMaxX(), y, 1));
    } else if (sourcePosition == Position.RIGHT_CENTER_BOTTOM) {
        y = (int) (sourceBounds.getMinY() + ((destBounds.getMaxY() - sourceBounds.getMinY()) / 2));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getMinX(), y, 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMaxX(), y, 1));
    } else if (sourcePosition == Position.TOP_CORNER_RIGHT) {
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMaxY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMaxX(), (int) destBounds.getCenterY(), 2));
    } else if (sourcePosition == Position.TOP_CORNER_LEFT) {
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMaxY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMinX(), (int) destBounds.getCenterY(), 2));
    } else if (sourcePosition == Position.BOTTOM_CORNER_RIGHT) {
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMinY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMaxX(), (int) destBounds.getCenterY(), 2));
    } else if (sourcePosition == Position.BOTTOM_CORNER_LEFT) {
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) sourceBounds.getMinY(), 0));
        pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getCenterX(), (int) destBounds.getCenterY(), 1));
        pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMinX(), (int) destBounds.getCenterY(), 2));
    } else {
        if (sourceBounds.intersects(destBounds)) {
          pointsAncrage.add(new RelationPointAncrageShape((int) sourceBounds.getMaxX(), (int) sourceBounds.getCenterY(), 0));
          pointsAncrage.add(new RelationPointAncrageShape((int) destBounds.getMaxX(), (int) destBounds.getCenterY(), 0));
        }
    }
    return pointsAncrage;
  }

  public RelationPointAncrageShape getLastPoint() {
    return this.pointsAncrage.get(this.pointsAncrage.size() - 1);
  }

  public RelationPointAncrageShape getFirstPoint() {
    return this.pointsAncrage.get(0);
  }

  public boolean isFirstOrLastPoint(RelationPointAncrageShape pointAncrage) {
    return this.isFirstPoint(pointAncrage) || this.isLastPoint(pointAncrage);
  }

  public boolean isFirstPoint(RelationPointAncrageShape pointAncrage) {
    return pointAncrage == this.getFirstPoint();
  }

  public boolean isLastPoint(RelationPointAncrageShape pointAncrage) {
    return pointAncrage == this.getLastPoint();
  }

  public MDElement getRelatedRepositoryElement() {
    return relatedRepositoryElement;
  }

  public abstract String getXmlTagName();

  public int getId() {
    return id;
  }

  public List<LabelShape> getLabels() {
    return labels;
  }

  @Override
  public String toString() {
    return "RelationShape{" +
            "relatedRepositoryElement=" + relatedRepositoryElement +
            '}';
  }

}
