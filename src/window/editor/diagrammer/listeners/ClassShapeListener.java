/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère les mouvements des ClassShapes ainsi que les autres événements
 * Par exemple : drag, clic droit, etc.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.listeners;

import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.Serializable;

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
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    super.mouseEntered(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    super.mouseExited(e);
    ClassShape source = (ClassShape) e.getSource();
    if (!source.isFocused()) {
      source.setFocused(false);
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    ClassShape shape = (ClassShape) e.getSource();
    int cursor = shape.getCursor().getType();

    this.checkForHoveredRelation(e);

    if (cursor != Cursor.MOVE_CURSOR) {
      shape.updateRelations();
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);

  }

  private void checkForHoveredRelation(MouseEvent e) {
    // On convertit le point pour simuler sa position absolue dans le DrawPanel
    Point converted = SwingUtilities.convertPoint(this.shape, e.getPoint(), DiagrammerService.getDrawPanel());

    // Vérifie si une association est survolée
    for (RelationShape relationShape : DiagrammerService.getDrawPanel().getRelationShapes()) {
      boolean oneSegmentIsHovered = false;
      for (Line2D segment : relationShape.getSegments()) {
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, converted) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          oneSegmentIsHovered = true;
        }
      }
      if (oneSegmentIsHovered) {
        this.focusedRelation = relationShape;
      }
      relationShape.setFocused(oneSegmentIsHovered);
      DiagrammerService.getDrawPanel().setCursor(new Cursor(oneSegmentIsHovered ? Cursor.MOVE_CURSOR : Cursor.DEFAULT_CURSOR));
    }
  }

}