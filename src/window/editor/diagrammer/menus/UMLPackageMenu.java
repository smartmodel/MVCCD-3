package window.editor.diagrammer.menus;

import java.io.Serializable;
import window.editor.diagrammer.elements.shapes.UMLPackage;

public class UMLPackageMenu extends CommonMenu implements Serializable {

  public UMLPackageMenu(UMLPackage shape) {
    super(shape);
  }
}
