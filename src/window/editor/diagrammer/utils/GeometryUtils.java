package window.editor.diagrammer.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.elements.ClassShape;
import window.editor.diagrammer.elements.RelationPointAncrageShape;
import window.editor.diagrammer.elements.RelationShape;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.services.DiagrammerService;

public class GeometryUtils {

  public static double getDistanceBetweenTwoPoints(Point p1, Point p2){
    double ac = Math.abs(p1.y - p2.y);
    double cb = Math.abs(p1.x - p2.x);
    return Math.hypot(ac, cb);
  }

  private static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
    double xDist = x1 - x2;
    double yDist = y1 - y2;
    return Math.sqrt(xDist * xDist + yDist * yDist);
  }

  public static double getDistanceBetweenLineAndPoint(Line2D segment,  Point pointToCheck) {
    double px = segment.getX2() - segment.getX1();
    double py = segment.getY2() - segment.getY1();

    double mult = px * px + py * py;
    double u = ((pointToCheck.x - segment.getX1()) * px + (pointToCheck.y - segment.getY1()) * py) / mult;

    if (u > 1) {
      u = 1;
    }
    else if (u < 0) {
      u = 0;
    }

    double x = segment.getX1() + u * px;
    double y = segment.getY1() + u * py;

    return distanceBetweenTwoPoints(x, y, pointToCheck.x, pointToCheck.y);
  }

  public static double getDistanceBetweenLineAndPoint(double x1, double y1, double x2, double y2, double checkX, double checkY) {
    double px = x2 - x1;
    double py = y2 - y1;

    double mult = px * px + py * py;
    double u = ((checkX - x1) * px + (checkY - y1) * py) / mult;

    if (u > 1) {
      u = 1;
    }
    else if (u < 0) {
      u = 0;
    }

    double x = x1 + u * px;
    double y = y1 + u * py;

    return distanceBetweenTwoPoints(x, y, checkX, checkY);
  }

  public static Point getNearestPointOnLine(double ax, double ay, double bx, double by, double px, double py, boolean clampToSegment, Point2D dest) {
    if (dest == null) {
      dest = new Point2D.Double();
    }

    double apx = px - ax;
    double apy = py - ay;
    double abx = bx - ax;
    double aby = by - ay;

    double ab2 = abx * abx + aby * aby;
    double ap_ab = apx * abx + apy * aby;
    double t = ap_ab / ab2;
    if (clampToSegment) {
      if (t < 0) {
        t = 0;
      } else if (t > 1) {
        t = 1;
      }
    }
    dest.setLocation(ax + abx * t, ay + aby * t);

    return new Point((int)  dest.getX(), (int) dest.getY());
  }

  public static IShape getShapeOnTheRight(IShape firstShape, IShape secondShape){
    if (firstShape.getBounds().x < secondShape.getBounds().x){
      return secondShape;
    } else if(secondShape.getBounds().x < firstShape.getBounds().x){
      return firstShape;
    } else{
      return null;
    }
  }

  public static IShape getShapeOnTheLeft(IShape firstShape, IShape secondShape){
    if (firstShape.getBounds().x > secondShape.getBounds().x){
      return secondShape;
    } else if(secondShape.getBounds().x > firstShape.getBounds().x){
      return firstShape;
    } else{
      return null;
    }
  }

  public static boolean pointIsAroundShape(Point point, ClassShape shape){
   Point converted = SwingUtilities.convertPoint(DiagrammerService.drawPanel, point, shape);
    return (converted.x >= 0 && converted.x <= shape.getWidth() && (converted.y == 0 || converted.y == shape.getHeight())) ||
        (converted.y >= 0 && converted.y <= shape.getHeight() && (converted.x == 0 || converted.x == shape.getWidth()));
/*    Point2D converted = new Point2D.Double();
    converted.setLocation(point.x, point.y);

    Line2D left = new Line2D.Double();
    Line2D right = new Line2D.Double();
    Line2D top = new Line2D.Double();
    Line2D bottom = new Line2D.Double();

    left.setLine(shape.getBounds().getMinX(), shape.getBounds().getMinY(), shape.getBounds().getMinX(), shape.getBounds().getMaxY());
    right.setLine(shape.getBounds().getMaxX(), shape.getBounds().getMinY(), shape.getBounds().getMaxX(), shape.getBounds().getMaxY());
    top.setLine(shape.getBounds().getMinX(), shape.getBounds().getMinY(), shape.getBounds().getMaxX(), shape.getBounds().getMinY());
    bottom.setLine(shape.getBounds().getMinX(), shape.getBounds().getMaxY(), shape.getBounds().getMaxX(), shape.getBounds().getMaxY());

    System.out.println();
    System.out.println("Bottom : " + (bottom.ptLineDist(converted) == 0));
    System.out.println("Top : " + (top.ptLineDist(converted) == 0));
    System.out.println("Left : " + (left.ptLineDist(converted) == 0));
    System.out.println("Right : " + (right.ptLineDist(converted) == 0));


    return left.ptLineDist(converted) == 0 || right.ptLineDist(converted) == 0 || top.ptLineDist(converted) == 0 || bottom.ptLineDist(converted) == 0 ;*/
  }

  public static boolean pointIsInsideBounds(Point point, ClassShape shape){
    Point converted = SwingUtilities.convertPoint(DiagrammerService.drawPanel, point, shape);
    Rectangle bounds = SwingUtilities.convertRectangle(DiagrammerService.drawPanel, shape.getBounds(), shape);
    System.out.println("Contains ? " + bounds.contains(converted));
    return bounds.contains(converted);
  }

  public static Point alignToClassShapeBounds(Point point, ClassShape shape){
    int xAligned = Integer.MAX_VALUE;
    int yAligned = Integer.MAX_VALUE;

    if (point.x < shape.getBounds().getMinX() && coordinateIsAroundShape(point.y, shape)){
      System.out.println("1");
      yAligned = point.y;
      xAligned = (int) shape.getBounds().getMinX();
    } else if(point.x < shape.getBounds().getMinX()){
      System.out.println("2");
      xAligned = (int) shape.getBounds().getMinX();
    } else if (point.x > shape.getBounds().getMaxX() && coordinateIsAroundShape(point.y, shape)){
      System.out.println("3");
      yAligned = point.y;
      xAligned = (int) shape.getBounds().getMaxX();
    } else if(point.x > shape.getBounds().getMaxX()){
      System.out.println("4");
      xAligned = (int) shape.getBounds().getMaxX();
    }

    if (point.y <= shape.getBounds().getMinY() && coordinateIsAroundShape(point.x, shape)){
      System.out.println("5");
      xAligned = point.x;
      yAligned = (int) shape.getBounds().getMinY();
    } else if(point.y <= shape.getBounds().getMinY()){
      System.out.println("6");
      yAligned = (int) shape.getBounds().getMinY();
    } else if (point.y >= shape.getBounds().getMaxY() && coordinateIsAroundShape(point.x, shape)){
      System.out.println("7");
      xAligned = point.x;
      yAligned = (int) shape.getBounds().getMaxY();
    } else if(point.y >= shape.getBounds().getMaxY()){
      System.out.println("8");
      yAligned = (int) shape.getBounds().getMaxY();

    }
    Point newPoint = new Point(xAligned, yAligned);
    return newPoint;
  }

  public static boolean coordinateIsAroundShape(int coordinate, ClassShape shape){
    return
        (coordinate >= shape.getBounds().getMinX() && coordinate <= shape.getBounds().getMaxX() && (coordinate == shape.getBounds().getMinY() || coordinate == shape.getBounds().getMaxY())) ||
            (coordinate >= shape.getBounds().getMinY() && coordinate <= shape.getBounds().getMaxY() && (coordinate == shape.getBounds().getMinX() || coordinate == shape.getBounds().getMaxX()));
  }

  public static RelationPointAncrageShape getNearestPointAncrage(ClassShape shape, RelationShape relation){
    RelationPointAncrageShape pointFound = relation.getPointsAncrage().getFirst();

      for (RelationPointAncrageShape point : relation.getPointsAncrage()){
        if (GeometryUtils.pointIsAroundShape(point, shape)){
          pointFound = point;
        }
      }

    return pointFound;
  }

  public static double getDistanceBetweenLineAndPoint(RelationPointAncrageShape start, RelationPointAncrageShape end, RelationPointAncrageShape pointToCheck) {
    return getDistanceBetweenLineAndPoint(start.x, start.y, end.x, end.y, pointToCheck.x, pointToCheck.y);
  }


}
