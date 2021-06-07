package window.editor.diagrammer.utils;


/**
 * Cette classe répertorie les constantes utilisées pour le développement du diagrammer.
 */
public class DiagrammerConstants {
    public static final String DRAW_PANEL_CONTAINER_NAME = "DrawPanelContainer";
    public static final String DRAW_PANEL_NAME = "DrawPanel";

    public static final int MINIMUM_ALLOWED_ZOOM = 4;
    public static final int MAXIMUM_ALLOWED_ZOOM = 25;

    public static final int DEFAULT_GRID_SIZE = 10;

    /**
     * Si DEFAULT_GRID_SIZE est modifiée, il faut aligner DEFAULT_ENTITY_POSITION_X avec un multiple de DEFAULT_GRID_SIZE
     */
    public static final int DEFAULT_ENTITY_POSITION_X = 60;

    /**
     * Si DEFAULT_GRID_SIZE est modifiée, il faut aligner DEFAULT_ENTITY_POSITION_Y avec un multiple de DEFAULT_GRID_SIZE
     */
    public static final int DEFAULT_ENTITY_POSITION_Y = 60;
    public static final int DEFAULT_ENTITY_WIDTH = 200;
    public static final int DEFAULT_ENTITY_HEIGHT = 230;
}
