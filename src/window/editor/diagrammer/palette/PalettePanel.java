package window.editor.diagrammer.palette;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import preferences.Preferences;

public class PalettePanel extends JToolBar implements Serializable {

  private static final long serialVersionUID = 1000;
  public static PaletteButton activeButton;
  private List<PaletteButton> buttons = new ArrayList<>();

  public PalettePanel() {
    activeButton = null;

    this.setBackground(Color.WHITE);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Création des boutons
    PaletteButton buttonEntity = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT, PaletteButtonType.SHAPE_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_entity.png"));
    PaletteButton buttonLink = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ENTITE_ASSOCIATIVE, PaletteButtonType.LINK_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_association_class.png"));
    PaletteButton buttonRelation = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_association.png"));
    PaletteButton buttonReflexiveRelation = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_association.png"));
    PaletteButton buttonGeneralization = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_generalization.png"));
    PaletteButton buttonComposition = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_composition.png"));
    PaletteButton buttonNote = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_NOTE_BUTTON_TEXT, PaletteButtonType.SHAPE_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_note.png"));

    this.addButton(buttonEntity);
    this.addButton(buttonNote);
    this.addButton(buttonLink);
    this.addButton(buttonRelation);
    this.addButton(buttonReflexiveRelation);
    this.addButton(buttonGeneralization);
    this.addButton(buttonComposition);

  }

  public static void setActiveButton(PaletteButton activeButton) {
    PalettePanel.activeButton = activeButton;
  }

  public static boolean activeButtonIsRelationCreation() {
    return activeButton.getType() == PaletteButtonType.RELATION_CREATION;
  }

  private void addButton(PaletteButton button) {
    this.buttons.add(button);
    this.add(button);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}