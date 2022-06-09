package window.editor.diagrammer.utils;

import java.awt.Dimension;
import java.awt.Font;
import preferences.Preferences;
import window.editor.diagrammer.services.DiagrammerService;

/***
 * Cette classe gère les éléments graphiques, tels que les polices d'écriture, padding, etc. selon le zoom actuel du DrawPanel.
 */
public class UIUtils {

  private static double getFontSize() {
    return Preferences.DIAGRAMMER_ELEMENTS_DEFAULT_FONT_SIZE * DiagrammerService.getDrawPanel().getZoomFactor();
  }

  public static Font getShapeFont() {
    return new Font(Preferences.DIAGRAMMER_DEFAULT_FONT_FAMILY, Font.PLAIN, (int) getFontSize());
  }

  public static Font getClassNameFont() {
    return new Font(Preferences.DIAGRAMMER_DEFAULT_FONT_FAMILY, Font.BOLD, (int) getFontSize());
  }

  public static Font getAbstracClassFont() {
    return new Font(Preferences.DIAGRAMMER_DEFAULT_FONT_FAMILY, Font.ITALIC + Font.BOLD, (int) getFontSize());
  }

  public static double getCardinalityPadding() {
    return Preferences.DIAGRAMMER_LABEL_PADDING * DiagrammerService.getDrawPanel().getZoomFactor();
  }

  public static double getClassPadding() {
    return Preferences.DIAGRAMMER_CLASS_PADDING * DiagrammerService.getDrawPanel().getZoomFactor();
  }

  public static double getAnchorPointSize() {
    return Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE * DiagrammerService.getDrawPanel().getZoomFactor();
  }

  public static double getNoteCornerSize() {
    return Preferences.DIAGRAMMER_DEFAULT_NOTE_CORNER_SIZE * DiagrammerService.getDrawPanel().getZoomFactor();
  }

  public static Dimension getClassShapeDefaultSize() {
    return new Dimension((int) (Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH * DiagrammerService.getDrawPanel().getZoomFactor()), (int) (Preferences.DIAGRAMMER_DEFAULT_NOTE_HEIGHT * DiagrammerService.getDrawPanel().getZoomFactor()));
  }

  public static Dimension getNoteDefaultSize() {
    return new Dimension((int) (Preferences.DIAGRAMMER_DEFAULT_NOTE_WIDTH * DiagrammerService.getDrawPanel().getZoomFactor()), (int) (Preferences.DIAGRAMMER_DEFAULT_NOTE_HEIGHT * DiagrammerService.getDrawPanel().getZoomFactor()));
  }

  public static Font getCardinalityFont() {
    return new Font(Preferences.DIAGRAMMER_DEFAULT_FONT_FAMILY, Font.PLAIN, (int) getFontSize());
  }
}