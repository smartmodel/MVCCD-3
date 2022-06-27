package window.editor.diagrammer.menus;

import diagram.Diagram;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import main.MVCCDElement;
import md.MDElement;
import window.editor.diagrammer.elements.shapes.MDTableShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.menus.actions.MDTableShapeDeleteActions;

public class MDTableShapeMenu extends CommonMenu implements Serializable {

  private final MDTableShape shape;
  private final JMenuItem deleteObject;
  private final JMenuItem deleteObjectAndClones;


  public MDTableShapeMenu(MDTableShape shape) {
    super(shape);
    this.shape = shape;
    this.deleteObject = new JMenuItem(
        new MDTableShapeDeleteActions("Supprimer objet", null, shape));
    this.deleteObjectAndClones = new JMenuItem(
        new MDTableShapeDeleteActions("Supprimer objet et clones", null, shape));

    this.searchInOtherDiagrams();
  }

  private void searchInOtherDiagrams() {
    MDElement mdElement = shape.getRelatedRepositoryElement();
    ArrayList<MVCCDElement> diagramElements = mdElement.getParent().getParent().getChilds().get(2)
        .getChilds();

    // Si le nombre d'enfants dans le noeud diagramme est <= 1, on affiche le menu de suppression avec la suppression graphique et objet
    if (diagramElements.size() <= 1) {
      this.add(this.deleteObject);
    }
    // Sinon, on va vérifier si la shape sur laquelle l'user clique, existe dans d'autres diagrammes du même niveau d'abstraction
    else {
      int numberOfSameShapeInOtherDiagrams = 0;

      for (MVCCDElement diagram : diagramElements) {
        List<ClassShape> shapes = ((Diagram) diagram).getClassShapes();

        if (shapes.contains(shape)) {
          numberOfSameShapeInOtherDiagrams++;
        }
      }

      if (numberOfSameShapeInOtherDiagrams == 1) {
        this.add(this.deleteObject);
      } else {
        this.add(this.deleteObjectAndClones);
      }
    }
  }
}
