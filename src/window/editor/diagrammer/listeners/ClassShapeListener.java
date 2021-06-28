package window.editor.diagrammer.listeners;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.utils.RelationCreator;

public class ClassShapeListener extends MouseAdapter {

  private Point startPoint = null;

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    if (PalettePanel.activeButton != null) {
      ClassShape shape = (ClassShape) e.getSource();
      this.handleRelationCreation(shape);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.startPoint = e.getPoint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    this.startPoint = null;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    ClassShape shape = (ClassShape) e.getSource();
    int cursor = shape.getCursor().getType();
    if (cursor != Cursor.MOVE_CURSOR) {
      shape.updateRelations();
    }
  }

  public void handleRelationCreation(ClassShape shape) {
    if (RelationCreator.source == null && RelationCreator.destination == null) {
      RelationCreator.setSource(shape);
    } else if (RelationCreator.source != null && RelationCreator.destination == null) {
      RelationCreator.setDestination(shape);
    }
    // Création
    if (RelationCreator.source != null && RelationCreator.destination != null) {
      RelationCreator.createRelation();
    }
  }

}
