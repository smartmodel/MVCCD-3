package window.editor.diagrammer.utils;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeometryUtils {

  public static double roundDouble(double number, int nbDecimals) {
    BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
    bigDecimal = bigDecimal.setScale(nbDecimals, RoundingMode.HALF_UP);
    return bigDecimal.doubleValue();
  }

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
    // Thanks StackOverflow!
    // https://stackoverflow.com/questions/1459368/snap-point-to-a-line-java
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
}
