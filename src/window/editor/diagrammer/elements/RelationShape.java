package window.editor.diagrammer.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;

public class RelationShape extends JComponent implements IShape {
  List<RelationPointAncrageShape> pointsAncrage = new LinkedList<>();
  MCDEntityShape source;
  MCDEntityShape destination;

  public RelationShape(MCDEntityShape source, MCDEntityShape destination) {
    this.source = source;
    this.destination = destination;
    this.pointsAncrage.add(new RelationPointAncrageShape(source.getX() + source.getWidth(), source.getY() + source.getHeight() / 2));
    this.pointsAncrage.add(new RelationPointAncrageShape(destination.getX(), destination.getY() + destination.getHeight() / 2));
  }

  public void drawPointsAncrage(Graphics2D graphics2D){
    for (RelationPointAncrageShape pointAncrage : this.pointsAncrage) {
      graphics2D.fillOval(pointAncrage.x - pointAncrage.DIAMETER / 2, pointAncrage.y - pointAncrage.DIAMETER / 2, pointAncrage.DIAMETER, pointAncrage.DIAMETER);
    }
  }

  public void addPointAncrage(RelationPointAncrageShape pointAncrage, int index){
    this.pointsAncrage.add(index, pointAncrage);
  }

  @Override
  public void setSize(Dimension dimension) {

  }

  @Override
  public Rectangle getBounds() {
    return null;
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {

  }

  @Override
  public void drag(int differenceX, int differenceY) {

  }


  @Override
  public void setSize(int width, int height) {
    IShape.super.setSize(width, height);
  }

  public List<RelationPointAncrageShape> getPointsAncrage() {
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

  public int getIndexOfNearestPointAncrage(Point point){
    int index = 0;
    for (RelationPointAncrageShape pointAncrage : this.pointsAncrage) {
      if (pointAncrage.getX() < GridUtils.alignToGrid(point.getX(), DiagrammerService.getDrawPanel().getGridSize())){
        index++;
      }
    }
    System.out.println(index);
    return index;
  }

  public int getPointAncrageIndex(RelationPointAncrageShape pointAncrage){
    int index = Integer.MAX_VALUE;
    for (int i = 0; i < this.pointsAncrage.size(); i++) {
      if (pointAncrage.equals(this.pointsAncrage.get(i))){
        index = i;
      }
    }
    System.out.println("Index found : " + index);
    return index;
  }

  public RelationPointAncrageShape convertPoint2DToPointAncrage(Point2D point2D){
    return new RelationPointAncrageShape((int) point2D.getX(), (int) point2D.getY());
  }
}
