package window.editor.diagrammer.palette;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class ToolPopupMenu extends JPopupMenu {

  public ToolPopupMenu() {
    JMenuItem associative = new JMenuItem("Ent");
    this.add(associative);
    this.setVisible(true);
  }
}
