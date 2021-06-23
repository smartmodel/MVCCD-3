package window.editor.diagrammer.utils;

import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDCompositionShape;
import window.editor.diagrammer.elements.shapes.relations.MCDGeneralizationShape;
import window.editor.diagrammer.elements.shapes.relations.MCDReflexiveShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

public class RelationCreator {
  public static ClassShape source = null;
  public static ClassShape destination = null;

  public static void setSource(ClassShape source) {
    RelationCreator.source = source;
  }

  public static void setDestination(ClassShape destination) {
    RelationCreator.destination = destination;
  }

  public static void createRelation(){
      // Création de l'association
      if (RelationCreator.source != null && RelationCreator.destination != null) {
        RelationShape relation = null;
        if (PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_GENERALIZATION_BUTTON_TEXT)){
          // Généralisation
          relation = new MCDGeneralizationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
        } else if (PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT)){
          // Association
          relation= new MCDAssociationShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
        } else if(PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_COMPOSITION_BUTTON_TEXT)){
          // Composition
          relation = new MCDCompositionShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape) RelationCreator.destination);
        } else if(PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_ASSOCIATION_REFLEXIVE_BUTTON_TEXT)){
          // Link
          relation = new MCDReflexiveShape((MCDEntityShape) RelationCreator.source, (MCDEntityShape)  RelationCreator.destination);
        }

        // Ajoute l'élément au DrawPanel
        DiagrammerService.drawPanel.addElement(relation);

        RelationCreator.setSource(null);
        RelationCreator.setDestination(null);
      }
    }

}
