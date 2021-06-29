package window.editor.diagrammer.palette;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import preferences.Preferences;

public class PalettePanel extends JPanel {

  public static PaletteButton activeButton;
  private final int alignment = SwingConstants.CENTER;

  public PalettePanel() {
    this.activeButton = null;
    this.setBackground(Color.ORANGE);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT));
  }

  public static void setActiveButton(PaletteButton activeButton) {
    PalettePanel.activeButton = activeButton;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
