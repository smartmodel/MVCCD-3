package window.editor.diagrammer.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.listeners.RelationFocusListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;

public class RelationShape extends JComponent implements IShape {
  private LinkedList<RelationPointAncrageShape> pointsAncrage = new LinkedList<>();
  private MCDEntityShape source;
  private MCDEntityShape destination;
  boolean isSelected = false;

  public RelationShape(MCDEntityShape source, MCDEntityShape destination) {
    this.source = source;
    this.destination = destination;
    this.pointsAncrage.add(new RelationPointAncrageShape(source.getX() + source.getWidth(), source.getY() + source.getHeight() / 2, 0));
    this.pointsAncrage.add(new RelationPointAncrageShape(destination.getX(), destination.getY() + destination.getHeight() / 2, 1));
    this.setFocusable(true);
    this.addListeners();
  }

  private void addListeners(){
    RelationFocusListener focusListener = new RelationFocusListener();
    this.addFocusListener(focusListener);
  }

  public void drawPointsAncrage(Graphics2D graphics2D){
    for (RelationPointAncrageShape pointAncrage : this.pointsAncrage) {
      graphics2D.fillOval(pointAncrage.x - pointAncrage.DIAMETER / 2, pointAncrage.y - pointAncrage.DIAMETER / 2, pointAncrage.DIAMETER, pointAncrage.DIAMETER);
    }
   // this.updateFirstAndLastPointsAncrage();
  }

  public void addPointAncrage(RelationPointAncrageShape pointAncrage, int index){
    this.pointsAncrage.add(index, pointAncrage);
  }

  @Override
  public void setSize(Dimension dimension) {

  }

  @Override
  public Rectangle getBounds() {
    return this.getPointsAncrageMinRectangle();
  }

  @Override
  public void zoom(int fromFactor, int toFactor){
    for (RelationPointAncrageShape pointAncrageShape : this.pointsAncrage){
      pointAncrageShape.zoom(fromFactor, toFactor);
    }
  }

  @Override
  public void drag(int differenceX, int differenceY) {

  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    for (RelationPointAncrageShape pointAncrage : this.pointsAncrage){
      pointAncrage.setLocationDifference(differenceX, differenceY);
    }
  }

  @Override
  public void setSize(int width, int height) {
    IShape.super.setSize(width, height);
  }

  public LinkedList<RelationPointAncrageShape> getPointsAncrage() {
    return pointsAncrage;
  }

  public void drawSegments(Graphics2D graphics2D) {
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    // Pour chaque point d'ancrage
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (i != this.pointsAncrage.size()-1){
        graphics2D.drawLine((int) pointsAncrage.get(i).getX(),(int) pointsAncrage.get(i).getY(),(int) pointsAncrage.get(i + 1).getX(),(int) pointsAncrage.get(i + 1).getY());
      }
    }
  }

  public int getPointAncrageIndex(RelationPointAncrageShape pointAncrage){
    int index = Integer.MAX_VALUE;
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (pointAncrage.equals(this.pointsAncrage.get(i))){
        index = this.pointsAncrage.get(i).getIndex();
      }
    }
    return index;
  }

  public RelationPointAncrageShape convertPoint2DToPointAncrage(Point2D point2D){
    return new RelationPointAncrageShape((int) point2D.getX(), (int) point2D.getY());
  }

  public void updateFirstAndLastPointsAncrage(int differenceX, int differenceY, ClassShape shape){

    RelationPointAncrageShape point;
    ClassShape leftShape = (ClassShape) GeometryUtils.getShapeOnTheLeft(this.source, this.destination);
    ClassShape rightShape = (ClassShape) GeometryUtils.getShapeOnTheRight(this.source, this.destination);
    RelationPointAncrageShape firstPoint = this.pointsAncrage.getFirst();
    RelationPointAncrageShape lastPoint = this.pointsAncrage.getLast();

    // Met à jour les points d'ancrage lorsque la ClassShape est déplacée ou resize
    // TODO -> Faire les modifications lors d'un resize
    if (shape == this.source){
      point = this.pointsAncrage.getFirst();
    } else{
      point = this.pointsAncrage.getLast();
    }

/*
    if (!GeometryUtils.pointIsInsideBounds(point, shape)){
      point.setLocationDifference(differenceX, differenceY);


    }*/

/*
    if (GeometryUtils.pointIsInsideBounds(point, shape)){
      //Point p2 = SwingUtilities.convertPoint(source, point, DiagrammerService.drawPanel);
      Point p = GeometryUtils.alignToClassShapeBounds(point, shape);
      point.drag(p.x, p.y);
      System.out.println("Point final : " + point);
    }
*/

/*
    if (this.pointsAncrage.size() == 2){
      if (leftShape.getBounds().getMaxY() < rightShape.getBounds().getMinY()){
        firstPoint.drag((int) leftShape.getBounds().getMaxX(), (int) leftShape.getBounds().getMaxY());
        lastPoint.drag((int) rightShape.getBounds().getMinX(), (int) rightShape.getBounds().getMinY());
      }

      if (leftShape.getBounds().getMinY() > rightShape.getBounds().getMaxY()){
        firstPoint.drag((int) leftShape.getBounds().getMaxX(), (int) leftShape.getBounds().getMinY());
        lastPoint.drag((int) rightShape.getBounds().getMinX(), (int) rightShape.getBounds().getMaxY());
      }

      if (lastPoint.y > rightShape.getBounds().getMaxY()){
        firstPoint.drag((int) leftShape.getBounds().getMaxX(), (int) rightShape.getBounds().getMaxY());
        lastPoint.drag((int) rightShape.getBounds().getMinX(), (int) rightShape.getBounds().getMaxY());
      }

      if (lastPoint.y < rightShape.getBounds().getMinY()){
        firstPoint.drag((int) leftShape.getBounds().getMaxX(), (int) rightShape.getBounds().getMinY());
        lastPoint.drag((int) rightShape.getBounds().getMinX(), (int) rightShape.getBounds().getMinY());
      }

      if (firstPoint.y > leftShape.getBounds().getMaxY()){
        firstPoint.drag((int) leftShape.getBounds().getMaxX(), (int) leftShape.getBounds().getMaxY());
        lastPoint.drag((int) rightShape.getBounds().getMinX(), (int) leftShape.getBounds().getMaxY());
      }

      if (firstPoint.y < leftShape.getBounds().getMinY()){
        firstPoint.drag((int) leftShape.getBounds().getMaxX(), (int) leftShape.getBounds().getMinY());
        lastPoint.drag((int) rightShape.getBounds().getMinX(), (int) leftShape.getBounds().getMinY());
      }
    } else{
      point.setLocationDifference(differenceX, differenceY);
    }*/
    point.setLocationDifference(differenceX, differenceY);




/*    if (shape == this.source){
      firstPoint.drag(firstPoint.x - differenceX, firstPoint.y);
    } else if (shape == this.destination){
      lastPoint.drag(lastPoint.x - differenceX, lastPoint.y);
    }*/

    DiagrammerService.drawPanel.repaint();

  }

  private Rectangle getPointsAncrageMinRectangle(){
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = 0;
    int maxY = 0;

    for (RelationPointAncrageShape pointAncrage : this.getPointsAncrage()){
      minX = Math.min(minX, pointAncrage.getBounds().x);
      minY = Math.min(minY, pointAncrage.getBounds().y);
      maxX = Math.max(maxX, pointAncrage.getBounds().x + pointAncrage.getBounds().width);
      maxY = Math.max(maxY, pointAncrage.getBounds().y + pointAncrage.getBounds().height);
    }

    return new Rectangle(minX, minY, maxX - minX, maxY - minY);
  }

  public MCDEntityShape getSource() {
    return source;
  }

  public MCDEntityShape getDestination() {
    return destination;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
    DiagrammerService.drawPanel.repaint();
  }

  public void reindexAllPointsAncrage(){
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
     this.pointsAncrage.get(i).setIndex(i);
    }
  }
  public ClassShape getNearestClassShape(RelationPointAncrageShape pointAncrageIndex){
      Point sourcePoint = new Point((int) this.source.getBounds().getMaxX(), (int) this.source.getBounds().getMaxY());
      Point destinationPoint = new Point((int) this.destination.getBounds().getMinX(), (int) this.destination.getBounds().getMinY());

      int distanceFromSource = (int) GeometryUtils.getDistanceBetweenTwoPoints(pointAncrageIndex, sourcePoint);
      int distanceFromDestination = (int) GeometryUtils.getDistanceBetweenTwoPoints(pointAncrageIndex, destinationPoint);

      return distanceFromSource < distanceFromDestination ? this.source : this.destination;
  }
}
