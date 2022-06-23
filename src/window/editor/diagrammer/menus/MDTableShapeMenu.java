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

  private final MDTableShape shape;
  private final JMenuItem delete;
  private final JMenuItem deleteObject;
  private final JMenuItem deleteObjectAndClones;


  public MDTableShapeMenu(MDTableShape shape) {
    super();
    this.shape = shape;
    this.delete = new JMenuItem(
        new MDTableShapeDeleteAction("Supprimer graphiquement", null, shape));
    this.deleteObject = new JMenuItem(
        new MDTableShapeDeleteAction("Supprimer objet", null, shape));
    this.deleteObjectAndClones = new JMenuItem(
        new MDTableShapeDeleteAction("Supprimer objet et clones", null, shape));

    this.searchInOtherDiagrams();
  }

  private void searchInOtherDiagrams() {
    MDElement mdElement = shape.getRelatedRepositoryElement();
    ArrayList<MVCCDElement> diagramElements = mdElement.getParent().getParent().getChilds().get(2)
        .getChilds();

    // Si le nombre d'enfants dans le noeud diagramme est <= 1, on affiche le menu de suppression avec la suppression graphique et objet
    if (diagramElements.size() <= 1) {
      addAllDeletes(this.delete, this.deleteObject);
    }
    // Sinon, on va vérifier si la shape sur laquelle l'user clique, existe dans d'autres diagrammes du même niveau d'abstraction
    else {
      int containsSameShapeInOtherDiagrams = 0;

      for (MVCCDElement diagram : diagramElements) {
        List<ClassShape> shapes = ((Diagram) diagram).getClassShapes();

        if (shapes.contains(shape)) {
          containsSameShapeInOtherDiagrams++;
        }
      }

      if (containsSameShapeInOtherDiagrams == 1) {
        addAllDeletes(this.delete, this.deleteObject);
      } else {
        addAllDeletes(this.delete, this.deleteObjectAndClones);
      }
    }
  }

  private void addAllDeletes(JMenuItem delete, JMenuItem deleteObject) {
    this.add(delete);
    this.add(deleteObject);
  }
}
