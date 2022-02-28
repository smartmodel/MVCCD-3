package window.editor.diagrammer.palette;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;

public class PaletteAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;

  @Override
  public void actionPerformed(ActionEvent e) {
    PaletteButton clickedButton = (PaletteButton) e.getSource();
    PalettePanel.setActiveButton(clickedButton);
  }
}
