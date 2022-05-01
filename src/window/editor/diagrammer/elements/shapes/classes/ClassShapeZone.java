package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ClassShapeZone extends Rectangle {

  private static final long serialVersionUID = -8400562139584806739L;
  private List<String> elements;

  public ClassShapeZone() {
    this.elements = new ArrayList<>();
  }

  public void addElement(String element) {
    this.elements.add(element);
  }

  public List<String> getElements() {
    return this.elements;
  }

  public void setElements(List<String> elements) {
    this.elements = elements;
  }
}