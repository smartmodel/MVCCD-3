package window.editor.diagrammer.utils;

import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

public class RelationUtils {

  public static RelationAnchorPointShape getNextPoint(RelationShape relationShape, RelationAnchorPointShape point) {
    return relationShape.getAnchorPoints().get(point.getIndex() + 1);
  }

  public static RelationAnchorPointShape getPreviousPoint(RelationShape relationShape, RelationAnchorPointShape point) {
    return relationShape.getAnchorPoints().get(point.getIndex() - 1);
  }
}