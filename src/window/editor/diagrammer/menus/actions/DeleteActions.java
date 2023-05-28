package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.SquaredShape;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteActions extends CommonDeleteActions {

  private final SquaredShape shape;

  public DeleteActions(String name, Icon icon, SquaredShape shape) {
    super(name, icon);
    this.shape = shape;
  }


  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getActionCommand().equals("Supprimer la représentation graphique")) {
      super.deleteGraphically(shape);
    }
  }
}
