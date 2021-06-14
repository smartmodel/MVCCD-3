package window.editor.diagrammer.palette;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class PaletteAction extends AbstractAction {

  @Override
  public void actionPerformed(ActionEvent e) {
    PaletteButton clickedButton = (PaletteButton) e.getSource();
    PalettePanel palette = (PalettePanel) clickedButton.getParent();
    palette.setActiveButton(clickedButton);
  }
}
