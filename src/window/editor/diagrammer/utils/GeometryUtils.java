package window.editor.diagrammer.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

public final class GeometryUtils {

  public static double getDistanceBetweenTwoPoints(Point p1, Point p2) {
    double ac = Math.abs(p1.y - p2.y);
    double cb = Math.abs(p1.x - p2.x);
    return Math.hypot(ac, cb);
  }

  private static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
    double xDist = x1 - x2;
    double yDist = y1 - y2;
    return Math.sqrt(xDist * xDist + yDist * yDist);
  }

  public static double getDistanceBetweenLineAndPoint(Line2D segment, Point pointToCheck) {
    double px = segment.getX2() - segment.getX1();
    double py = segment.getY2() - segment.getY1();
    double mult = px * px + py * py;
    double u = ((pointToCheck.x - segment.getX1()) * px + (pointToCheck.y - segment.getY1()) * py) / mult;
    if (u > 1) {
      u = 1;
    } else if (u < 0) {
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
    } else if (u < 0) {
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
    return new Point((int) dest.getX(), (int) dest.getY());
  }

  public static IShape getShapeOnTheRight(IShape firstShape, IShape secondShape) {
    if (firstShape.getBounds().x < secondShape.getBounds().x) {
      return secondShape;
    } else if (secondShape.getBounds().x < firstShape.getBounds().x) {
      return firstShape;
    } else {
      return null;
    }
  }

  public static IShape getShapeOnTheLeft(IShape firstShape, IShape secondShape) {
    if (firstShape.getBounds().x > secondShape.getBounds().x) {
      return secondShape;
    } else if (secondShape.getBounds().x > firstShape.getBounds().x) {
      return firstShape;
    } else {
      return null;
    }
  }

  public static boolean pointIsAroundShape(Point point, ClassShape shape) {
    // On convertit le point pour le rendre relatif à la ClassShape
    Point converted = SwingUtilities.convertPoint(DiagrammerService.getDrawPanel(), point, shape);
    return (converted.x >= 0 && converted.x <= shape.getWidth() && (converted.y == 0 || converted.y == shape.getHeight())) || (converted.y >= 0 && converted.y <= shape.getHeight() && (converted.x == 0 || converted.x == shape.getWidth()));
  }

  public static RelationPointAncrageShape getNearestPointAncrage(ClassShape shape, RelationShape relation) {
    return shape == relation.getSource() ? relation.getFirstPoint() : relation.getLastPoint();
  }

  public static double getDistanceBetweenLineAndPoint(RelationPointAncrageShape start, RelationPointAncrageShape end, RelationPointAncrageShape pointToCheck) {
    return getDistanceBetweenLineAndPoint(start.x, start.y, end.x, end.y, pointToCheck.x, pointToCheck.y);
  }

  public static RelationPointAncrageShape getLeftPoint(RelationPointAncrageShape firstPoint, RelationPointAncrageShape secondPoint) {
    if (firstPoint.x < secondPoint.x) {
      return firstPoint;
    } else if (secondPoint.x < firstPoint.x) {
      return secondPoint;
    } else {
      return firstPoint;
    }
  }

  public static RelationPointAncrageShape getRightPoint(RelationPointAncrageShape firstPoint, RelationPointAncrageShape secondPoint) {
    if (firstPoint.x > secondPoint.x) {
      return firstPoint;
    } else if (secondPoint.x > firstPoint.x) {
      return secondPoint;
    } else {
      return firstPoint;
    }
  }

  public static Point getPointOnLineWithDistance(Point start, Point end, double distance) {
    double xDiff = end.getX() - start.getX();
    double yDiff = end.getY() - start.getY();
    double length = distanceBetweenTwoPoints(start.x, start.y, end.x, end.y);
    double distanceToGo = distance / length;
    return new Point((int) (start.getX() + xDiff * distanceToGo), (int) (start.getY() + yDiff * distanceToGo));
  }

  public static boolean isVertical(Line2D segment) {
    return segment.getX1() == segment.getX2();
  }

  public static boolean isHorizontal(Line2D segment) {
    return segment.getY1() == segment.getY2();
  }

  public static boolean pointIsOnRightSideOfBounds(Point p, Rectangle bounds) {
    return p.x == bounds.getMaxX() && p.y >= bounds.getMinY() && p.y <= bounds.getMaxY();
  }

  public static boolean pointIsOnLeftSideOfBounds(Point p, Rectangle bounds) {
    return p.x == bounds.getMinX() && p.y >= bounds.getMinY() && p.y <= bounds.getMaxY();
  }

  public static boolean pointIsOnTopSideOfBounds(Point p, Rectangle bounds) {
    return p.y == bounds.getMinY() && p.x >= bounds.getMinX() && p.x <= bounds.getMaxX();
  }

  public static boolean pointIsOnBottomSideOfBounds(Point p, Rectangle bounds) {
    return p.y == bounds.getMaxY() && p.x >= bounds.getMinX() && p.x <= bounds.getMaxX();
  }

  public static Position getClassShapePosition(ClassShape shape, ClassShape comparedTo) {

    if (isRight(shape, comparedTo) && isTop(shape, comparedTo)) {
      return Position.TOP_CORNER_RIGHT;
    } else if (isLeft(shape, comparedTo) && isTop(shape, comparedTo)) {
      return Position.TOP_CORNER_LEFT;
    } else if (isRight(shape, comparedTo) && isBottom(shape, comparedTo)) {
      return Position.BOTTOM_CORNER_RIGHT;
    } else if (isLeft(shape, comparedTo) && isBottom(shape, comparedTo)) {
      return Position.BOTTOM_CORNER_LEFT;
    } else if (isXCenteredTopLeft(shape, comparedTo) || isXCenteredTopRight(shape, comparedTo)) {
      if (isTop(shape, comparedTo)) {
        return isXCenteredTopLeft(shape, comparedTo) ? Position.TOP_CENTER_LEFT : Position.TOP_CENTER_RIGHT;
      } else if (isBottom(shape, comparedTo)) {
        return isXCenteredTopLeft(shape, comparedTo) ? Position.BOTTOM_CENTER_LEFT : Position.BOTTOM_CENTER_RIGHT;
      }
    } else if (isYCenteredTop(shape, comparedTo) || isYCenteredBottom(shape, comparedTo)) {
      if (isLeft(shape, comparedTo)) {
        return isYCenteredTop(shape, comparedTo) ? Position.LEFT_TOP : Position.LEFT_BOTTOM;
      } else if (isRight(shape, comparedTo)) {
        return isYCenteredTop(shape, comparedTo) ? Position.RIGHT_TOP : Position.RIGHT_BOTTOM;
      }
    }
    return Position.UNHANDLED;
  }

  public static boolean isTop(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMaxY() <= comparedTo.getBounds().getMinY();
  }

  public static boolean isBottom(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMinY() >= comparedTo.getBounds().getMaxY();
  }

  public static boolean isRight(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMinX() >= comparedTo.getBounds().getMaxX();
  }

  public static boolean isLeft(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMaxX() <= comparedTo.getBounds().getMinX();
  }

  public static boolean isXCenteredTopLeft(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMaxX() >= comparedTo.getBounds().getMinX() && shape.getBounds().getMaxX() <= comparedTo.getBounds().getMaxX();
  }

  public static boolean isXCenteredTopRight(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMinX() <= comparedTo.getBounds().getMaxX() && shape.getBounds().getMinX() >= comparedTo.getBounds().getMinX();
  }

  public static boolean isYCenteredTop(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMaxY() >= comparedTo.getBounds().getMinY() && shape.getBounds().getMaxY() <= comparedTo.getBounds().getMaxY();
  }

  public static boolean isYCenteredBottom(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMinY() <= comparedTo.getBounds().getMaxY() && shape.getBounds().getMinY() >= comparedTo.getBounds().getMinY();
  }

  public static boolean isHigher(ClassShape shape, ClassShape comparedTo) {
    return shape.getBounds().getMinY() < comparedTo.getBounds().getMinY();
  }

  public static boolean pointIsAboveShape(Point point, IShape shape) {
    return point.y < shape.getBounds().getMinY();
  }

  public static boolean pointIsUnderShape(Point point, IShape shape) {
    return point.y > shape.getBounds().getMaxY();
  }

  public static boolean yCoordinateIsOutsideShape(double y, IShape shape) {
    return y < shape.getBounds().getMinY() || y > shape.getBounds().getMaxY();
  }

  public static boolean xCoordinateIsOutsideShape(double x, IShape shape) {
    return x < shape.getBounds().getMinX() || x > shape.getBounds().getMaxX();
  }

  public static boolean pointHasCommonYWithShapes(Point point, ClassShape leftShape, ClassShape rightShape) {
    return (point.y >= leftShape.getBounds().getMinY() && point.y <= leftShape.getBounds().getMaxY()) && (point.y >= rightShape.getBounds().getMinY() && point.y <= rightShape.getBounds().getMaxY());
  }

  public static boolean pointHasCommonXWithShapes(Point point, ClassShape leftShape, ClassShape rightShape) {
    return (point.x >= leftShape.getBounds().getMinX() && point.x <= leftShape.getBounds().getMaxX()) && (point.x >= rightShape.getBounds().getMinX() && point.x <= rightShape.getBounds().getMaxX());
  }
}
