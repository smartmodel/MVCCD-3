/***
 * Cette classe peut être utilisée en l'état actuel.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.utils;

import main.MVCCDManager;

public class IDManager {

    public static int generateId() {
        if (MVCCDManager.instance().getProject() != null) {
            return MVCCDManager.instance().getProject().getNextIdElementSequence();
        } else {
            return Integer.MAX_VALUE;
        }
    }
}