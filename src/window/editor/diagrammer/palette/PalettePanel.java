package window.editor.diagrammer.palette;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import preferences.Preferences;

public class PalettePanel extends JPanel {

  public static PaletteButton activeButton;

  public PalettePanel() {
    activeButton = null;
    this.setBackground(Color.WHITE);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT, new ImageIcon("ressources/icons-diagrammer/palette/icon_entity.png")));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT, new ImageIcon("ressources/icons-diagrammer/palette/icon_association.png")));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT, new ImageIcon("ressources/icons-diagrammer/palette/icon_reflexive.png")));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT, new ImageIcon("ressources/icons-diagrammer/palette/icon_generalization.png")));
    this.add(new PaletteButton(Preferences.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT, new ImageIcon("ressources/icons-diagrammer/palette/icon_composition.png")));
  }

  public static void setActiveButton(PaletteButton activeButton) {
    PalettePanel.activeButton = activeButton;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
