package window.editor.diagrammer.menus;

import java.awt.Point;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDCompositionShape;
import window.editor.diagrammer.elements.shapes.relations.MCDGeneralizationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDReflexiveShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.menus.actions.MCDAssociationEditAction;
import window.editor.diagrammer.menus.actions.MCDGeneralizationEditAction;
import window.editor.diagrammer.menus.actions.MCDReflexiveEditAction;
import window.editor.diagrammer.menus.actions.RelationAddPointAncrageAction;

public class RelationMenu extends JPopupMenu {


  public RelationMenu(RelationShape shape, int x, int y) {
    super();
    JMenuItem  edit = null;
    if (shape instanceof MCDAssociationShape){
      edit = new JMenuItem(new MCDAssociationEditAction("Ouvrir l'assistant de modélisation", null, (MCDAssociationShape) shape));
    } else if(shape instanceof MCDReflexiveShape){
      edit = new JMenuItem(new MCDReflexiveEditAction("Ouvrir l'assistant de modélisation", null, (MCDReflexiveShape) shape));
    } else if (shape instanceof MCDGeneralizationShape){
      edit = new JMenuItem(new MCDGeneralizationEditAction("Ouvrir l'assistant de modélisation", null, (MCDGeneralizationShape) shape));
    } else if (shape instanceof MCDCompositionShape){
      // TODO -> Ajouter le traitement pour l'association de composition
    }

      this.add(edit);
      JMenuItem addPointAncrage = new JMenuItem(new RelationAddPointAncrageAction("Ajouter un point d'ancrage", null, shape, new Point(x, y)));
      this.add(addPointAncrage);
  }

}
