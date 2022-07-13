package window.editor.diagrammer.elements.shapes.relations.mcd;

import preferences.Preferences;
import preferences.PreferencesManager;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class MCDNaturalIdentifierShape extends MCDDiamondRelationShape implements Serializable {

    private static final long serialVersionUID = 1660545571630623463L;

    public MCDNaturalIdentifierShape() {
    }

    public MCDNaturalIdentifierShape(SquaredShape source, IShape destination, boolean isReflexive) {
        super(source, destination, isReflexive);
    }

    @Override
    public void doDraw(Graphics2D graphics2D) {
        boolean isUMLNotation = PreferencesManager.instance().getApplicationPref().isDIAGRAMMER_UML_NOTATION();
        if (isUMLNotation) {
            this.drawDiamond(graphics2D);
            if (this.hasLabel(LabelType.ASSOCIATION_NAME)) {
                this.deleteLabel(LabelType.ASSOCIATION_NAME);
            }
        } else {
            this.addLabelWhenNotUMLNotation();
        }
    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {

    }

    @Override
    public String getXmlTagName() {
        return Preferences.DIAGRAMMER_MCD_NATURAL_IDENTIFIER_XML_TAG;
    }

    private void addLabelWhenNotUMLNotation() {
        int middleAnchorPointIndex = this.getIndexOfMiddleAnchorPoint();
        RelationAnchorPointShape anchorPoint = this.anchorPoints.get(middleAnchorPointIndex);

        LabelShape label = this.createOrUpdateLabel(anchorPoint, "<<NID>", LabelType.ASSOCIATION_NAME, 0, 0);
        DiagrammerService.getDrawPanel().add(label, JLayeredPane.DRAG_LAYER);
    }

    @Override
    protected Color getDiamondBorderColor() {
        return Color.BLACK;
    }

    @Override
    protected Color getDiamondBackgroundColor() {
        return Color.WHITE;
    }
}