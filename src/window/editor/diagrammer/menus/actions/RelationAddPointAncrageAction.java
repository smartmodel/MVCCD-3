package window.editor.diagrammer.menus.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

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