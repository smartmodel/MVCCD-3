package window.editor.diagrammer.utils;

import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDCompositionShape;
import window.editor.diagrammer.elements.shapes.relations.MCDGeneralizationShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

public class RelationCreator {

  public static ClassShape source = null;
  public static ClassShape destination = null;

  public static void setSource(ClassShape source) {
    RelationCreator.source = source;
    System.out.println("Source set");
    DiagrammerService.drawPanel.setShowRelationProjectionLine(true);
  }

  public static void setDestination(ClassShape destination) {
    RelationCreator.destination = destination;
    System.out.println("Destination set");
    DiagrammerService.drawPanel.setShowRelationProjectionLine(false);
  }

  public static void createRelation() {
    // Création de l'association
    if (RelationCreator.source != null && RelationCreator.destination != null) {
      RelationShape relation = null;
      if (PalettePanel.activeButton.getText().equals(Preferences.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT)) {
        // Généralisation
        relation = new MCDGeneralizationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
      } else if (PalettePanel.activeButton.getText().equals(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT)) {
        // Association
        relation = new MCDAssociationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination, false);
      } else if (PalettePanel.activeButton.getText().equals(Preferences.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT)) {
        // Composition
        relation = new MCDCompositionShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
      } else if (PalettePanel.activeButton.getText().equals(Preferences.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT)) {
        // Réflexive
        relation = new MCDAssociationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination, true);
      }
      // Ajoute l'élément au DrawPanel
      DiagrammerService.drawPanel.addElement(relation);
      resetSourceAndDestination();
    }
  }

  public static void resetSourceAndDestination() {
    source = null;
    destination = null;
  }

}
