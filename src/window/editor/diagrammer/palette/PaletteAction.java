/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère l'action d'un bouton de la palette.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.palette;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class PaletteAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;

  @Override
  public void actionPerformed(ActionEvent e) {
    PaletteButton clickedButton = (PaletteButton) e.getSource();
    PalettePanel.setActiveButton(clickedButton);
  }
}
