package window.editor.diagrammer.utils;

public class GridUtils {
    /**
     * Cete méthode retourne une nouvelle valeur alignée à la grille.
     * @param valueToAlign : ancienne valeur devant être alignée
     * @param gridSize : taille de la grille actuelle
     * @return nouvelle valeur alignée à la grille
     */
    public static int alignToGrid(double valueToAlign, int gridSize){
/*        System.out.println();
        System.out.println("Valeur reçue : " + valueToAlign);
        System.out.println("GridSize reçue : " + gridSize);*/

        double toAlign = valueToAlign;
        double mod = toAlign % gridSize;
        if(mod != 0){
            toAlign -= mod;
            boolean roundToNextGridSizeMultiple = (mod >= (gridSize / 2));
            if(roundToNextGridSizeMultiple) {
                // Coordinate needs to be rounded to the upper multiple of gridSize
                return (int) toAlign + gridSize;
            }
        }
        return (int) toAlign;

    }

}
