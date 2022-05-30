package window.editor.diagrammer.elements.shapes.classes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClassShapeZone extends Rectangle {

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