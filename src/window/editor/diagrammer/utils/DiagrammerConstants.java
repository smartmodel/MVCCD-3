package window.editor.diagrammer.utils;


import java.awt.Color;
import java.awt.Font;

/**
 * Cette classe répertorie les constantes utilisées pour le développement du diagrammer.
 */
public class DiagrammerConstants {
    public static final String DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME = "DrawPanelContainer";
    public static final String DIAGRAMMER_DRAW_PANEL_NAME = "DrawPanel";
    public static final String DIAGRAMMER_PALETTE_PANEL_NAME = "PalettePanel";

    public static final int DIAGRAMMER_MINIMUM_ALLOWED_ZOOM = 4;
    public static final int DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM = 25;

    public static final int DIAGRAMMER_DEFAULT_GRID_SIZE = 10;

    /**
     * Si DEFAULT_GRID_SIZE est modifiée, il faut aligner DEFAULT_ENTITY_POSITION_X avec un multiple de DEFAULT_GRID_SIZE
     */
    public static final int DIAGRAMMER_DEFAULT_ENTITY_POSITION_X = 60;

    /**
     * Si DEFAULT_GRID_SIZE est modifiée, il faut aligner DEFAULT_ENTITY_POSITION_Y avec un multiple de DEFAULT_GRID_SIZE
     */
    public static final int DIAGRAMMER_DEFAULT_ENTITY_POSITION_Y = 60;
    public static final int DIAGRAMMER_DEFAULT_ENTITY_WIDTH = 200;
    public static final int DIAGRAMMER_DEFAULT_ENTITY_HEIGHT = 100;

    public static String DIAGRAMMER_ENTITY_STEREOTYPE_TEXT = "<<Entity>>";
    public static String DIAGRAMMER_ENTITY_ORDERED_TEXT = "{ordered}";
    public static final Color DIAGRAMMER_ENTITY_DEFAULT_BACKGROUND_COLOR = new Color(125, 200, 243);
    public static final Font DIAGRAMMER_CLASS_NAME_FONT = new Font("Arial", Font.BOLD, 13);
    public static final Font DIAGRAMMER_CLASS_FONT = new Font("Arial", Font.PLAIN, 13);
    public static final Font DIAGRAMMER_ABSTRACT_CLASS_NAME_FONT = new Font("Arial", Font.ITALIC + Font.BOLD, 13);
    public static final int DIAGRAMMER_CLASS_PADDING = 10;

    public static final String DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT = "Entité";
    public static final String DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT = "Association";
    public static final String DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT = "Généralisation";
    public static final String DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT = "Composition";
    public static final String DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT = "Association réflexive";
    public static final int DIAGRAMMER_RELATION_CLICK_AREA = 5;
}
