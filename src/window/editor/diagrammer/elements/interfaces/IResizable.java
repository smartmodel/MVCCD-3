/***
 * Cette interface peut être utilisée en l'état actuel. Elle permet d'identifier les formes redimensionnables.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.elements.interfaces;

import java.awt.*;

public interface IResizable {
  void resize(Rectangle newBounds);
}
