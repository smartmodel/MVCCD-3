/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère le menu d'un point d'ancrage affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus;

import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.menus.actions.PointAncrageDeleteAction;

import javax.swing.*;
import java.io.Serializable;

public class AnchorPointMenu extends JPopupMenu implements Serializable {

  private static final long serialVersionUID = 1000;
  private final RelationAnchorPointShape shape;
  private final RelationShape relation;

  public AnchorPointMenu(RelationAnchorPointShape shape, RelationShape relation) {
    this.shape = shape;
    this.relation = relation;
    final JMenuItem delete = new JMenuItem(new PointAncrageDeleteAction("Supprimer le point d'ancrage", null, shape, relation));
    this.add(delete);
  }
}