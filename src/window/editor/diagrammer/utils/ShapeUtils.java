package window.editor.diagrammer.utils;

import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.LinkShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public final class ShapeUtils {

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

    public static boolean pointIsAroundShape(Point point, SquaredShape shape) {
        // On convertit le point pour le rendre relatif à la ClassShape
        Point converted = SwingUtilities.convertPoint(DiagrammerService.getDrawPanel(), point, shape);
        return (converted.x >= 0 && converted.x <= shape.getWidth() && (converted.y == 0 || converted.y == shape.getHeight())) || (converted.y >= 0 && converted.y <= shape.getHeight() && (converted.x == 0 || converted.x == shape.getWidth()));
    }

    public static boolean anchorPointsAreOnSameSegment(Line2D segment, RelationAnchorPointShape p1, RelationAnchorPointShape p2) {
        return segment.contains(p1) && segment.contains(p2);
    }

    public static RelationAnchorPointShape getNearestPointAncrage(SquaredShape shape, RelationShape relation) {
        return shape == relation.getSource() ? relation.getFirstPoint() : relation.getLastPoint();
    }

    public static double getDistanceBetweenLineAndPoint(RelationAnchorPointShape start, RelationAnchorPointShape end, RelationAnchorPointShape pointToCheck) {
        return getDistanceBetweenLineAndPoint(start.x, start.y, end.x, end.y, pointToCheck.x, pointToCheck.y);
    }

    public static RelationAnchorPointShape getLeftPoint(RelationAnchorPointShape firstPoint, RelationAnchorPointShape secondPoint) {
        if (firstPoint.x < secondPoint.x) {
            return firstPoint;
        } else if (secondPoint.x < firstPoint.x) {
            return secondPoint;
        } else {
            return firstPoint;
        }
    }

    public static RelationAnchorPointShape getRightPoint(RelationAnchorPointShape firstPoint, RelationAnchorPointShape secondPoint) {
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

    /***
     * Retourne les points d'ancrage appartenenat à des Link qui doivent être déplacés lorsqu'un est segment d'une relation est modifié.
     * @return Liste de points d'ancrage à déplacer
     */
    public static List<RelationAnchorPointShape> getLinkedShapesAnchorPointsToMove(Point p1, Point p2) {
        Line2D segment = new Line2D.Double();
        segment.setLine(p1.x, p1.y, p2.x, p2.y);
        List<LinkShape> linkShapes = DiagrammerService.getDrawPanel().getLinkShapes();
        List<RelationAnchorPointShape> anchorPointsToMove = new ArrayList<>();


        for (LinkShape shape : linkShapes) {
            RelationAnchorPointShape relatedAnchorPoint = shape.getLastPoint();
            System.out.println(ShapeUtils.getDistanceBetweenLineAndPoint(segment, relatedAnchorPoint));

            if (ShapeUtils.pointIsOnSegment(relatedAnchorPoint, segment)) {
                anchorPointsToMove.add(relatedAnchorPoint);
            }
        }

        return anchorPointsToMove;

    }

    public static List<RelationAnchorPointShape> getAnchorPointsToMove(RelationShape relationShape, RelationAnchorPointShape anchorPointDragged) {
        List<RelationAnchorPointShape> anchorPointsToMove = new ArrayList<>();

        if (anchorPointDragged == relationShape.getFirstPoint()) {
            // Premier point
            RelationAnchorPointShape nextPoint = relationShape.getAnchorPoints().get(anchorPointDragged.getIndex() + 1);
            anchorPointsToMove.add(nextPoint);
        } else if (anchorPointDragged == relationShape.getLastPoint()) {
            RelationAnchorPointShape previousPoint = relationShape.getAnchorPoints().get(anchorPointDragged.getIndex() - 1);
            // Dernier point
            anchorPointsToMove.add(previousPoint);
        } else {
            // Point d'indice n dans une relation ayant au moins 3 points d'ancrage
            RelationAnchorPointShape nextPoint = relationShape.getAnchorPoints().get(anchorPointDragged.getIndex() + 1);
            RelationAnchorPointShape previousPoint = relationShape.getAnchorPoints().get(anchorPointDragged.getIndex() - 1);
            anchorPointsToMove.add(nextPoint);
            anchorPointsToMove.add(previousPoint);
        }

        return anchorPointsToMove;
    }


    public static boolean pointIsOnCornerOfSquaredShape(RelationAnchorPointShape point, SquaredShape shape) {

        boolean isInTopLeftCorner = point.x == shape.getX() && point.y == shape.getY();
        boolean isInTopRightCorner = point.x == shape.getX() + shape.getWidth() && point.y == shape.getY() + shape.getHeight();
        boolean isInBottomLeftCorner = point.x == shape.getX() && point.y == shape.getHeight();
        boolean isInBottomRightCorner = point.x == shape.getX() + shape.getWidth() && point.y == shape.getHeight();

        return isInTopLeftCorner || isInTopRightCorner || isInBottomLeftCorner || isInBottomRightCorner;
    }


    public static Position getSourceShapePosition(SquaredShape source, SquaredShape comparedTo) {

        if (isRight(source, comparedTo) && isTop(source, comparedTo)) {
            return Position.TOP_CORNER_RIGHT;
        } else if (isLeft(source, comparedTo) && isTop(source, comparedTo)) {
            return Position.TOP_CORNER_LEFT;
        } else if (isRight(source, comparedTo) && isBottom(source, comparedTo)) {
            return Position.BOTTOM_CORNER_RIGHT;
        } else if (isLeft(source, comparedTo) && isBottom(source, comparedTo)) {
            return Position.BOTTOM_CORNER_LEFT;
        } else if (isXCenteredTopLeft(source, comparedTo) || isXCenteredTopRight(source, comparedTo)) {
            if (isTop(source, comparedTo)) {
                return isXCenteredTopLeft(source, comparedTo) ? Position.TOP_CENTER_LEFT : Position.TOP_CENTER_RIGHT;
            } else if (isBottom(source, comparedTo)) {
                return isXCenteredTopLeft(source, comparedTo) ? Position.BOTTOM_CENTER_LEFT : Position.BOTTOM_CENTER_RIGHT;
            }
        } else if (isYCenteredTop(source, comparedTo) || isYCenteredBottom(source, comparedTo)) {
            if (isLeft(source, comparedTo)) {
                return isYCenteredTop(source, comparedTo) ? Position.LEFT_CENTER_TOP : Position.LEFT_CENTER_BOTTOM;
            } else if (isRight(source, comparedTo)) {
                return isYCenteredTop(source, comparedTo) ? Position.RIGHT_CENTER_TOP : Position.RIGHT_CENTER_BOTTOM;
            }
        }
        return Position.UNHANDLED;
    }

    public static boolean isTop(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMaxY() <= comparedTo.getBounds().getMinY();
    }

    public static boolean isBottom(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMinY() >= comparedTo.getBounds().getMaxY();
    }

    public static boolean isRight(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMinX() >= comparedTo.getBounds().getMaxX();
    }

    public static boolean isLeft(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMaxX() <= comparedTo.getBounds().getMinX();
    }

    public static boolean isXCenteredTopLeft(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMaxX() >= comparedTo.getBounds().getMinX() && shape.getBounds().getMaxX() <= comparedTo.getBounds().getMaxX();
    }

    public static boolean isXCenteredTopRight(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMinX() <= comparedTo.getBounds().getMaxX() && shape.getBounds().getMinX() >= comparedTo.getBounds().getMinX();
    }

    public static boolean isYCenteredTop(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMaxY() >= comparedTo.getBounds().getMinY() && shape.getBounds().getMaxY() <= comparedTo.getBounds().getMaxY();
    }

    public static boolean isYCenteredBottom(SquaredShape shape, SquaredShape comparedTo) {
        return shape.getBounds().getMinY() <= comparedTo.getBounds().getMaxY() && shape.getBounds().getMinY() >= comparedTo.getBounds().getMinY();
    }

    public static boolean isHigher(SquaredShape shape, SquaredShape comparedTo) {
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

    public static RelationShape getRelationFromAnchorPoint(RelationAnchorPointShape anchorPoint) {
        List<RelationShape> allRelations = DiagrammerService.getDrawPanel().getRelationShapes();
        for (RelationShape relation : allRelations) {
            for (RelationAnchorPointShape point : relation.getAnchorPoints()) {
                if (point == anchorPoint) {
                    return relation;
                }
            }
        }
        return null;
    }

    public static boolean pointIsOnRelation(Point point, RelationShape relationShape) {
        return relationShape.contains(point);
    }

    public static boolean pointIsOnSegment(Point point, Line2D segment) {
        return ShapeUtils.getDistanceBetweenLineAndPoint(segment, point) == 0.0;
    }

    public static boolean pointHasCommonYWithShapes(Point point, ClassShape leftShape, ClassShape rightShape) {
        return (point.y >= leftShape.getBounds().getMinY() && point.y <= leftShape.getBounds().getMaxY()) && (point.y >= rightShape.getBounds().getMinY() && point.y <= rightShape.getBounds().getMaxY());
    }

    public static boolean pointHasCommonXWithShapes(Point point, ClassShape leftShape, ClassShape rightShape) {
        return (point.x >= leftShape.getBounds().getMinX() && point.x <= leftShape.getBounds().getMaxX()) && (point.x >= rightShape.getBounds().getMinX() && point.x <= rightShape.getBounds().getMaxX());
    }
}