package window.editor.diagrammer.menus;

import diagram.Diagram;
import main.MVCCDElement;
import md.MDElement;
import window.editor.diagrammer.elements.shapes.MDTableShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.menus.actions.MDTableShapeDeleteActions;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;

public class MDTableShapeMenu extends CommonMenu implements Serializable {

  private final MDTableShape shape;
  private final JMenuItem deleteObject;
  private final JMenuItem deleteObjectAndClones;


  public MDTableShapeMenu(MDTableShape shape) {
    super(shape);
    this.shape = shape;
    this.deleteObject = new JMenuItem(
            new MDTableShapeDeleteActions("Supprimer l'objet et sa représentation graphique", null, shape));
    this.deleteObjectAndClones = new JMenuItem(
            new MDTableShapeDeleteActions("Supprimer l'objet et ses représentations graphiques", null, shape));

    this.searchInOtherDiagrams();
  }

  private void searchInOtherDiagrams() {
    MDElement mdElement = shape.getRelatedRepositoryElement();
    MVCCDElement tablesNode = mdElement.getParent();
    MVCCDElement abstractionNode = tablesNode.getParent();
    MVCCDElement diagrammesNode = abstractionNode.getChilds().get(2);
    List<MVCCDElement> diagramList = diagrammesNode.getChilds();

    // Si le nombre d'enfants dans le noeud diagramme est <= 1, on affiche le menu de suppression avec la suppression graphique et objet
    if (diagramList.size() <= 1) {
      this.add(this.deleteObject);
    }
    // Sinon, on va vérifier si la shape sur laquelle l'user clique, existe dans d'autres diagrammes du même niveau d'abstraction
    else {
      int numberOfSameShapeInOtherDiagrams = 0;

      for (MVCCDElement diagram : diagramList) {
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
