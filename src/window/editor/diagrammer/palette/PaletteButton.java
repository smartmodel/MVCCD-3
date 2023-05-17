/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente un bouton de la palette
 * Par exemple : bouton pour créer une association, pour créer une composition, etc.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.palette;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

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
