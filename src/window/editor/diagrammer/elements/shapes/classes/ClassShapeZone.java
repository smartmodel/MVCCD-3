/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente une zone d'une classe UML.
 * Par exemple, une zone pour le nom de la classe, les attributs, une zone pour les méthodes
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.elements.shapes.classes;

import java.awt.*;
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