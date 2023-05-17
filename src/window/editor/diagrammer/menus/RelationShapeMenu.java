/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère le menu affiché lors du clic droit sur une relation
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.menus;

import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDCompositionShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDGeneralizationShape;
import window.editor.diagrammer.menus.actions.MCDAssociationEditAction;
import window.editor.diagrammer.menus.actions.MCDGeneralizationEditAction;
import window.editor.diagrammer.menus.actions.RelationAddPointAncrageAction;
import window.editor.diagrammer.menus.actions.RelationDeleteAction;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class RelationShapeMenu extends JPopupMenu implements Serializable {

  private static final long serialVersionUID = 1000;

  public RelationShapeMenu(RelationShape shape, int x, int y) {
    super();
    JMenuItem edit = null;
    if (shape instanceof MCDAssociationShape) {
      this.add(new JMenuItem(new MCDAssociationEditAction("Ouvrir l'assistant de modélisation", null, (MCDAssociationShape) shape)));
    } else if (shape instanceof MCDGeneralizationShape) {
      this.add(new JMenuItem(new MCDGeneralizationEditAction("Ouvrir l'assistant de modélisation", null, (MCDGeneralizationShape) shape)));
    } else if (shape instanceof MCDCompositionShape) {
      // TODO -> Ajouter le traitement pour l'association de composition
    }

    // Ajout d'un point d'ancrage sur la relation
    JMenuItem addPointAncrage = new JMenuItem(new RelationAddPointAncrageAction("Ajouter un point d'ancrage", null, shape, new Point(x, y)));
    this.add(addPointAncrage);

    // Suppression d'une relation
    JMenuItem delete = new JMenuItem(new RelationDeleteAction("Supprimer", null, shape));
    this.add(delete);
  }

}