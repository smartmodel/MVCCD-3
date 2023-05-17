/***
 * Cette classe peut être utilisée en l'état actuel. Elle permet de récupérer la zone de dessin d'ArcDataModeler.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.services;

import window.editor.diagrammer.drawpanel.DrawPanel;

public final class DiagrammerService {

    private static DrawPanel drawPanel;

    public static DrawPanel getDrawPanel() {
        if (drawPanel == null) {
            drawPanel = new DrawPanel();
        }
        return drawPanel;
    }

}
