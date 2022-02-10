package window.editor.diagrammer.palette;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PaletteAction extends AbstractAction {

  @Override
  public void actionPerformed(ActionEvent e) {
    PaletteButton clickedButton = (PaletteButton) e.getSource();
    PalettePanel.setActiveButton(clickedButton);
  }
}
