package window.editor.diagrammer.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.Serializable;

import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;

import javax.swing.*;

public class ClassShapeListener extends MouseAdapter implements Serializable {

  private static final long serialVersionUID = 1000;
  private final ClassShape shape;
  private RelationShape focusedRelation = null;


  public ClassShapeListener(ClassShape shape) {
    this.shape = shape;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    super.mouseExited(e);
    ClassShape source = (ClassShape) e.getSource();
    if (!source.isFocused()){
      source.setFocused(false);
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);

  }

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
  }

  private void checkForHoveredRelation(MouseEvent e){
    // On convertit le point pour simuler sa position absolue dans le DrawPanel
    Point converted = SwingUtilities.convertPoint(shape, e.getPoint(), DiagrammerService.getDrawPanel());

    // Vérifie si une association est survolée
    for (RelationShape relationShape : DiagrammerService.getDrawPanel().getRelationShapes()){
      boolean oneSegmentIsHovered = false;
      for (Line2D segment : relationShape.getSegments()) {
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, converted) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          oneSegmentIsHovered = true;
        }
      }
      if (oneSegmentIsHovered) focusedRelation = relationShape;
      relationShape.setFocused(oneSegmentIsHovered);
      DiagrammerService.getDrawPanel().setCursor(new Cursor(oneSegmentIsHovered ? Cursor.MOVE_CURSOR : Cursor.DEFAULT_CURSOR));
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    ClassShape shape = (ClassShape) e.getSource();
    int cursor = shape.getCursor().getType();

    checkForHoveredRelation(e);

    if (cursor != Cursor.MOVE_CURSOR) {
      shape.updateRelations();
    }
  }


}
