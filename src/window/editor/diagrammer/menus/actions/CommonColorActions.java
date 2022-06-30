package window.editor.diagrammer.menus.actions;

import java.awt.Color;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JColorChooser;
import main.MVCCDManager;
import window.editor.diagrammer.elements.shapes.SquaredShape;

public abstract class CommonColorActions extends AbstractAction {

  private final MVCCDManager instanceMVCCDManager = MVCCDManager.instance();


  protected CommonColorActions(String name, Icon icon) {
    super(name, icon);
  }

  protected void modifyColorOfAllSameObjects(SquaredShape shape) {
    Color currentColor = shape.getBackground();
    Color newColor = JColorChooser.showDialog(null, "Sélectionner une couleur",
        currentColor);

    if (newColor != null && newColor != currentColor) {
      instanceMVCCDManager.getCurrentDiagram()
          .getSquaredShapes()
          .stream()
          .filter(e -> e.getClass().equals(shape.getClass()))
          .forEach(
              ele -> ele.setBackground(newColor)
          );
    }
  }

  protected void modifyColorOfThisObject(SquaredShape shape) {
    Color currentColor = shape.getBackground();
    Color newColor = JColorChooser.showDialog(null, "Sélectionner une couleur",
        currentColor);
    if (newColor != null && newColor != currentColor) {
      shape.setBackground(newColor);
    }
  }
}
