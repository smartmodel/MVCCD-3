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

  private static PalettePanel instance;

  public static synchronized PalettePanel instance() {
    if (instance == null) {
      instance = new PalettePanel();
    }
    return instance;
  }

  private static final long serialVersionUID = 1000;
  public static PaletteButton activeButton;
  private List<PaletteButton> buttons = new ArrayList<>();


  private PalettePanel() {
    activeButton = null;

    this.setBackground(Color.WHITE);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Création des boutons
    PaletteButton buttonEntity = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT, PaletteButtonType.SHAPE_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_entity.png"));
    PaletteButton buttonLink = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ENTITE_ASSOCIATIVE, PaletteButtonType.LINK_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_association_class.png"));
    PaletteButton buttonReflexiveRelation = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_association.png"));
    PaletteButton buttonRelation = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_association.png"));
    PaletteButton buttonGeneralization = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_generalization.png"));
    PaletteButton buttonComposition = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_composition.png"));
    PaletteButton buttonNaturalIdentifier = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_NATURAL_IDENTIFIER_BUTTON_TEXT, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_natural_identifier.png"));
    PaletteButton buttonNote = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_NOTE_BUTTON_TEXT, PaletteButtonType.SHAPE_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_note.png"));
    PaletteButton buttonAnchor = new PaletteButton(Preferences.DIAGRAMMER_PALETTE_ANCHOR_BUTTON, PaletteButtonType.RELATION_CREATION, new ImageIcon("ressources/icons-diagrammer/palette/icon_anchor.png"));

    this.addButton(buttonEntity);
    this.addButton(buttonLink);
    this.addButton(buttonRelation);
    this.addButton(buttonComposition);
    this.addButton(buttonNaturalIdentifier);
    this.addButton(buttonReflexiveRelation);
    this.addButton(buttonGeneralization);
    this.addButton(buttonNote);
    this.addButton(buttonAnchor);

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