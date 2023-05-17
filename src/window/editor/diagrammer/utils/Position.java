/***
 * Cette énumération peut être utilisée en l'état actuelle. Elle permet de situer une forme par rapport à une autre (à côté de, au dessus de, etc.)
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */
package window.editor.diagrammer.utils;

/**
 * Cette classe désigne les possibles positions d'une Shape par rapport à une autre.
 */
public enum Position {
    TOP_CORNER_RIGHT, TOP_CORNER_LEFT, BOTTOM_CORNER_RIGHT, BOTTOM_CORNER_LEFT, TOP_CENTER_LEFT, TOP_CENTER_RIGHT, BOTTOM_CENTER_LEFT, BOTTOM_CENTER_RIGHT, LEFT_CENTER_TOP, LEFT_CENTER_BOTTOM, RIGHT_CENTER_TOP, RIGHT_CENTER_BOTTOM, UNHANDLED
}
