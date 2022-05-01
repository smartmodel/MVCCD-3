package window.editor.diagrammer.utils;

import main.MVCCDManager;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDCompositionShape;
import window.editor.diagrammer.elements.shapes.relations.MCDGeneralizationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDLinkShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

/**
 * Cette classe permet la création d'une Relation entre deux ClassShape.
 */
public final class RelationCreator {

  public static SquaredShape source = null;
  public static IShape destination = null;
  public static boolean isCreating = false;

  public static void setSource(SquaredShape source) {
    isCreating = true;
    RelationCreator.source = source;
  }

  public static void setDestination(IShape destination) {
    isCreating = false;
    RelationCreator.destination = destination;
  }

  public static void createRelation() {
    // Création de l'association
    if (RelationCreator.source != null && RelationCreator.destination != null) {
      RelationShape relation = null;
      switch (PalettePanel.activeButton.getText()) {
        case Preferences.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT:
          // Généralisation
          System.out.println("généralization créée");
          relation = new MCDGeneralizationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
          break;
        case Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT:
          // Association
          System.out.println("associatio créée");
          relation = new MCDAssociationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination, false);
          break;
        case Preferences.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT:
          // Composition
          System.out.println("composition créée");
          relation = new MCDCompositionShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
          break;
        case Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT:
          // Réflexive
          System.out.println("réflexive créée");
          relation = new MCDAssociationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination, true);
          break;
        case Preferences.DIAGRAMMER_PALETTE_ENTITE_ASSOCIATIVE:
          // Entité associative
          System.out.println("entité associative créée");
          relation = new MCDLinkShape((MCDEntityShape) RelationCreator.source, (RelationShape) RelationCreator.destination);
      }

      // Ajoute l'élément au diagramme courant et au diagrammeur
      MVCCDManager.instance().getCurrentDiagram().addShape(relation);
      DiagrammerService.getDrawPanel().addShape(relation);
      DiagrammerService.getDrawPanel().repaint();

      resetSourceAndDestination();
    }
  }

  public static void resetSourceAndDestination() {
    source = null;
    destination = null;
    isCreating = false;
    DiagrammerService.getDrawPanel().repaint();
  }

  public static void setIsCreating(boolean isCreating) {
    RelationCreator.isCreating = isCreating;
  }
}