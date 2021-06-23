package window.editor.diagrammer.listeners;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;

public class ClassShapeListener extends MouseAdapter {

  private Point origin = null;

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.origin = e.getPoint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    if (PalettePanel.activeButton != null) {
      ClassShape shape = (ClassShape) e.getSource();
      this.handleRelationCreation(shape);
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {

    ClassShape shape = (ClassShape) e.getSource();

    int differenceX = GridUtils.alignToGrid(e.getPoint().x - origin.x, DiagrammerService.getDrawPanel().getGridSize());
    int differenceY = GridUtils.alignToGrid(e.getPoint().y - origin.y, DiagrammerService.getDrawPanel().getGridSize());

    int cursor = shape.getCursor().getType();

     if (cursor != Cursor.MOVE_CURSOR){
        shape.updateRelations(0, 0, true);
      } else{
        shape.updateRelations(differenceX, differenceY, false);
     }
    }

    public void handleRelationCreation(ClassShape shape){
      if (RelationCreator.source == null && RelationCreator.destination == null) {
        RelationCreator.setSource(shape);
        System.out.println("Source set");
      } else if (RelationCreator.source != null && RelationCreator.destination == null) {
        RelationCreator.setDestination(shape);
        System.out.println("Destination set");
      }

      // Création
      if(RelationCreator.source != null && RelationCreator.destination != null){
        RelationCreator.createRelation();

        RelationCreator.setSource(null);
        RelationCreator.setDestination(null);
      }

    }

}
