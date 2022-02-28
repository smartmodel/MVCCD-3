package window.editor.diagrammer.palette;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JButton;

public class PaletteButton extends JButton implements Serializable {

  private static final long serialVersionUID = 1000;

  public PaletteButton(String text) {
    super();
    this.setAction(new PaletteAction());
    this.setText(text);
    this.initUI();
  }

  public PaletteButton(String text, Icon icon) {
    this(text);
    this.setIcon(icon);
  }

  private void initUI() {
    this.setBorderPainted(false);
    this.setOpaque(false);
    this.setBackground(Color.WHITE);
  }

}
