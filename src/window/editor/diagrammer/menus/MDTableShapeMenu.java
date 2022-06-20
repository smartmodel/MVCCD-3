package window.editor.diagrammer.menus;

import diagram.Diagram;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import main.MVCCDElement;
import md.MDElement;
import window.editor.diagrammer.elements.shapes.MDTableShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.menus.actions.MDTableShapeDeleteAction;

public class MDTableShapeMenu extends JPopupMenu implements Serializable {

  MDTableShape shape;


  public MDTableShapeMenu(MDTableShape shape) {
    super();
    this.shape = shape;
    JMenuItem delete = new JMenuItem(
        new MDTableShapeDeleteAction("Supprimer graphiquement", null, shape));
    JMenuItem deleteObject = new JMenuItem(
        new MDTableShapeDeleteAction("Supprimer objet", null, shape));

    searchInOtherDiagrams(shape, delete, deleteObject);
  }

  private void searchInOtherDiagrams(MDTableShape shape, JMenuItem delete, JMenuItem deleteObject) {
    MDElement mdElement = shape.getRelatedRepositoryElement();
    ArrayList<MVCCDElement> diagramElements = mdElement.getParent().getParent().getChilds().get(2)
        .getChilds();

    // Si le nombre d'enfants dans le noeud diagramme est <= 1, on affiche le menu de suppression avec la suppression
    // graphique et objet
    if (diagramElements.size() <= 1) {
      addAllDeletes(delete, deleteObject);
    }
    // Sinon, on va vérifier si la shape sur laquelle l'user clique, existe dans d'autres diagrammes du même niveau d'abstraction
    else {
      boolean containsSameShapeInOtherDiagrams = false;
      int smallestID = Integer.MAX_VALUE;
      for (MVCCDElement diagram : diagramElements) {
        List<ClassShape> shapes = ((Diagram) diagram).getClassShapes();

        if (shapes.contains(shape)) {
          containsSameShapeInOtherDiagrams = true;

          for (ClassShape s : shapes) {
            if (s.equals(shape) && ((MDTableShape) s).getID() < smallestID) {
              smallestID = ((MDTableShape) s).getID();
            }
          }
        }
      }
      if (shape.getID() < smallestID) {
        smallestID = shape.getID();
      }

      if (!containsSameShapeInOtherDiagrams) {
        addAllDeletes(delete, deleteObject);
      } else {
        // Si la shape sur laquelle l'user a cliqué est la shape mère, possible de supprimer graphiquement et objet
        if (shape.getID() == smallestID) {
          addAllDeletes(delete, deleteObject);
        }
        // Si la shape sur laquelle l'user a cliqué n'est pas la shape mère, possible de supprimer graphiquement seulement
        else {
          this.add(delete);
        }
      }
    }
  }

  private void addAllDeletes(JMenuItem delete, JMenuItem deleteObject) {
    this.add(delete);
    this.add(deleteObject);
  }
}
