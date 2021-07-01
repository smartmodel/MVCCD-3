package window.editor.diagrammer.listeners;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.utils.RelationCreator;

public class ClassShapeListener extends MouseAdapter {

  private final ClassShape shape;

  public ClassShapeListener(ClassShape shape) {
    this.shape = shape;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    if (PalettePanel.activeButton != null) {
      this.handleRelationCreation(this.shape);
    }
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
  public void mouseDragged(MouseEvent e) {
    final ClassShape shape = (ClassShape) e.getSource();
    final int cursor = shape.getCursor().getType();
    if (cursor != Cursor.MOVE_CURSOR) {
      shape.updateRelations();
    }
  }

  public void handleRelationCreation(ClassShape shape) {
    if (RelationCreator.source == null) {
      RelationCreator.setSource(shape);
    } else if (RelationCreator.destination == null) {
      RelationCreator.setDestination(shape);
    }
    // Création
    if (RelationCreator.source != null && RelationCreator.destination != null) {
      RelationCreator.createRelation();
      PalettePanel.setActiveButton(null);
    }
  }

}
