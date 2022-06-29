package window.editor.diagrammer.menus;

import java.awt.Point;
import java.io.Serializable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDCompositionShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDGeneralizationShape;
import window.editor.diagrammer.menus.actions.MCDAssociationEditAction;
import window.editor.diagrammer.menus.actions.MCDCompositionEditAction;
import window.editor.diagrammer.menus.actions.MCDGeneralizationEditAction;
import window.editor.diagrammer.menus.actions.RelationAddPointAncrageAction;
import window.editor.diagrammer.menus.actions.RelationDeleteAction;

public class RelationShapeMenu extends JPopupMenu implements Serializable {

  private static final long serialVersionUID = 1000;

  public RelationShapeMenu(RelationShape shape, int x, int y) {
    super();
    if (shape instanceof MCDAssociationShape) {
      this.add(new JMenuItem(new MCDAssociationEditAction(Preferences.DIAGRAMMER_MENU_OPEN_MODELING_ASSISTANT_TEXT, null, (MCDAssociationShape) shape)));
    } else if (shape instanceof MCDGeneralizationShape) {
      this.add(new JMenuItem(new MCDGeneralizationEditAction(Preferences.DIAGRAMMER_MENU_OPEN_MODELING_ASSISTANT_TEXT, null, (MCDGeneralizationShape) shape)));
    } else if (shape instanceof MCDCompositionShape) {
      this.add(new JMenuItem(new MCDCompositionEditAction(Preferences.DIAGRAMMER_MENU_OPEN_MODELING_ASSISTANT_TEXT, null, (MCDCompositionShape) shape)));
    }

    // Ajout d'un point d'ancrage sur la relation
    JMenuItem addPointAncrage = new JMenuItem(new RelationAddPointAncrageAction(Preferences.DIAGRAMMER_MENU_ADD_ANCHOR_POINT_TEXT, null, shape, new Point(x, y)));
    this.add(addPointAncrage);

    // Suppression d'une relation
    JMenuItem delete = new JMenuItem(new RelationDeleteAction(Preferences.DIAGRAMMER_MENU_DELETE_TEXT, null, shape));
    this.add(delete);
  }

}