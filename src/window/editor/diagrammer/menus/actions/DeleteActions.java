package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.SquaredShape;

public class DeleteActions extends CommonDeleteActions {

  private final SquaredShape shape;

  public DeleteActions(String name, Icon icon, SquaredShape shape) {
    super(name, icon);
    this.shape = shape;
  }


  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getActionCommand().equals("Supprimer graphiquement")) {
      super.deleteGraphically(shape);
    }
  }
}
