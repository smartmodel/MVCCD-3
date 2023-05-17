/***
 * Cette classe peut être utilisée en l'état actuel. Elle contient une méthode permettant d'aligner un point d'ancrage
 * à la grille du diagrammeur.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */
package window.editor.diagrammer.utils;

public final class GridUtils {

    /**
     * Cete méthode retourne une nouvelle valeur alignée à la grille.
     * @param valueToAlign : ancienne valeur devant être alignée
     * @param gridSize : taille de la grille actuelle
     * @return nouvelle valeur alignée à la grille
     */
    public static int alignToGrid(double valueToAlign, int gridSize) {

        double toAlign = valueToAlign;
        double mod = toAlign % gridSize;
        if (mod != 0) {
            toAlign -= mod;
            boolean roundToNextGridSizeMultiple = (mod >= (gridSize / 2));
            if (roundToNextGridSizeMultiple) {
                // La coordonnée doit être arrondie au multiple supérieur de gridSize
                return (int) toAlign + gridSize;
            }
        }
        return (int) toAlign;

    }

}
