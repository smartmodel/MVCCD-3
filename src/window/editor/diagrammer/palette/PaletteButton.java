package window.editor.diagrammer.palette;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JButton;

public class PaletteButton extends JButton implements Serializable {

  private static final long serialVersionUID = 1000;
  private PaletteButtonType type;

  public PaletteButton(String text, PaletteButtonType type) {
    super();
    this.setAction(new PaletteAction());
    this.setText(text);
    this.type = type;
    this.initUI();
  }

  public PaletteButton(String text, PaletteButtonType type, Icon icon) {
    this(text, type);
    this.setIcon(icon);
  }

  private void initUI() {
    this.setBorderPainted(false);
    this.setOpaque(false);
    this.setBackground(Color.WHITE);
  }

  public PaletteButtonType getType() {
    return type;
  }
}
