/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère l'action d'ajouter un point d'ancrage d'une relation
 * via le menu contextuel affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.relations.RelationShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class RelationAddPointAncrageAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
  private final RelationShape relation;
  private final Point point;

  public RelationAddPointAncrageAction(String name, Icon icon, RelationShape shape, Point point) {
    super(name, icon);
    this.point = point;
    this.relation = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.relation.addAnchorPoint(this.point);
  }

}