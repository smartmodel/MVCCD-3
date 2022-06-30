package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.SquaredShape;

public class ModifyColorActions extends CommonColorActions {

  private final SquaredShape shape;

  public ModifyColorActions(String name, Icon icon, SquaredShape shape) {
    super(name, icon);
    this.shape = shape;
  }


  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getActionCommand()
        .equals("Modifier couleur de fond de tous les objets de ce type")) {
      super.modifyColorOfAllSameObjects(shape);
    } else if (actionEvent.getActionCommand().equals("Modifier couleur de fond de cet objet")) {
      super.modifyColorOfThisObject(shape);
    }
  }
}

