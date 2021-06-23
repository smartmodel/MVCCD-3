package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.drawpanel.DrawPanel;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.utils.DiagrammerConstants;

public class MCDEntityShapeDeleteAction extends AbstractAction {

  private MCDEntityShape shape;

  public MCDEntityShapeDeleteAction(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.delete();
  }

  private void delete(){
    DrawPanel drawPanel = (DrawPanel) SwingUtilities.getAncestorNamed(DiagrammerConstants.DIAGRAMMER_DRAW_PANEL_NAME, this.shape);
    drawPanel.deleteElement(this.shape);
    drawPanel.repaint();
  }
}
