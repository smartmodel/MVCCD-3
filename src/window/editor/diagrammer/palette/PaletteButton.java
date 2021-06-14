package window.editor.diagrammer.palette;

import javax.swing.JButton;

public class PaletteButton extends JButton {

  public PaletteButton(String text) {
    super();
    this.setAction(new PaletteAction());
    this.setText(text);
  }
}
