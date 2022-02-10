package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.relations.RelationShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RelationAddPointAncrageAction extends AbstractAction {

  private final RelationShape relation;
  private final Point point;

  public RelationAddPointAncrageAction(String name, Icon icon, RelationShape shape, Point point) {
    super(name, icon);
    this.point = point;
    this.relation = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.relation.addPointAncrage(this.point);
  }

}
