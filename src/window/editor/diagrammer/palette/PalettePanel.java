package window.editor.diagrammer.palette;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import window.editor.diagrammer.utils.DiagrammerConstants;

public class PalettePanel extends JPanel {

  public static PaletteButton activeButton;

  public PalettePanel() {
    this.activeButton = null;
    this.setBackground(Color.ORANGE);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(new PaletteButton(DiagrammerConstants.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  public static void setActiveButton(PaletteButton activeButton) {
    PalettePanel.activeButton = activeButton;
  }
}
