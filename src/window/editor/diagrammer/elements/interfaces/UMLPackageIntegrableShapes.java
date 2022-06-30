package window.editor.diagrammer.elements.interfaces;

import window.editor.diagrammer.elements.shapes.UMLPackage;

public interface UMLPackageIntegrableShapes {

  void initUI();

  void setParentUMLPackage(UMLPackage parentUMLPackage);

  UMLPackage getParentUMLPackage();
}
