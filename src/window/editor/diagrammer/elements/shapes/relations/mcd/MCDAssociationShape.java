/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente l'objet graphique d'une association MCD.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.elements.shapes.relations.mcd;

import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import preferences.Preferences;
import preferences.PreferencesManager;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class MCDAssociationShape extends RelationShape implements Serializable {

    private static final long serialVersionUID = 5405071714676740739L;

    public MCDAssociationShape(MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        super(source, destination, isReflexive);
    }

    public MCDAssociationShape(int id, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        super(id, source, destination, isReflexive);
    }

    public MCDAssociationShape(MCDAssociation relatedRepositoryAssociation, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        this(source, destination, isReflexive);
        this.relatedRepositoryElement = relatedRepositoryAssociation;
    }

    public MCDAssociationShape(int id, MCDAssociation relatedRepositoryAssociation, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        super(id, relatedRepositoryAssociation, source, destination, isReflexive);
    }

    @Override
    public void defineLineAspect(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(1));
    }

    @Override
    public void doDraw(Graphics2D graphics2D) {
        MCDAssociation relatedAssociation = this.getMCDAssociation();
        boolean isUMLNotation = PreferencesManager.instance().getApplicationPref().isDIAGRAMMER_UML_NOTATION();

        if (relatedAssociation != null) {
            if (relatedAssociation.getNature() == MCDAssociationNature.IDCOMP) {
                // Association identifiante
                if (isUMLNotation) {
                    this.drawDiamond(graphics2D, Color.BLACK, Color.BLACK);
                } else {
                    // Ajouter le label abec <<CID>>
                }

            } else if (relatedAssociation.getNature() == MCDAssociationNature.IDNATURAL) {
                // Association identifiante naturelle
                this.drawDiamond(graphics2D, Color.WHITE, Color.BLACK);
            }
        }
    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {

        MCDAssociation association = this.getMCDAssociation();

        // Nom d'association
        if (!association.getName().isEmpty()) {
            LabelShape labelShape;
            if (this.anchorPoints.size() <= 2) {
                RelationAnchorPointShape anchorPoint = this.anchorPoints.get(0);
                Point relationCenter = this.getCenter();
                int distanceInXFromAnchorPoint = Math.abs(relationCenter.x - anchorPoint.x);
                int distanceInYFromAnchorPoint = Math.abs(relationCenter.y - anchorPoint.y);
                labelShape = this.createOrUpdateLabel(anchorPoint, association.getName(), LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

            } else {
                int middleIndex = this.anchorPoints.size() / 2;
                labelShape = this.createOrUpdateLabel(this.anchorPoints.get(middleIndex), association.getName(), LabelType.ASSOCIATION_NAME, 0, 0);
            }
            DiagrammerService.getDrawPanel().add(labelShape, JLayeredPane.DRAG_LAYER);
        } else {
            this.deleteLabel(LabelType.ASSOCIATION_NAME);
        }

        // Rôle source
        if (!association.getFrom().getName().isEmpty()) {
            LabelShape labelShape = this.createOrUpdateLabel(this.getFirstPoint(), association.getFrom().getName(), LabelType.SOURCE_ROLE, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape, JLayeredPane.DRAG_LAYER);
        } else {
            this.deleteLabel(LabelType.SOURCE_ROLE);
        }

        // Rôle destination
        if (!association.getTo().getName().isEmpty()) {
            LabelShape labelShape = this.createOrUpdateLabel(this.getLastPoint(), association.getTo().getName(), LabelType.DESTINATION_ROLE, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape, JLayeredPane.DRAG_LAYER);
        } else {
            this.deleteLabel(LabelType.DESTINATION_ROLE);
        }

        // Cardinalités source
        if (!association.getFrom().getMultiStr().isEmpty()) {
            LabelShape labelShape = this.createOrUpdateLabel(this.getFirstPoint(), association.getFrom().getMultiStr(), LabelType.SOURCE_CARDINALITY, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape, JLayeredPane.DRAG_LAYER);
        } else {
            this.deleteLabel(LabelType.SOURCE_CARDINALITY);
        }

        // Cardinalités destination
        if (!association.getTo().getMultiStr().isEmpty()) {
            LabelShape labelShape = this.createOrUpdateLabel(this.getLastPoint(), association.getTo().getMultiStr(), LabelType.DESTINATION_CARDINALITY, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape, JLayeredPane.DRAG_LAYER);
        } else {
            this.deleteLabel(LabelType.DESTINATION_CARDINALITY);
        }

        DiagrammerService.getDrawPanel().repaint();

    }

    @Override
    public String getXmlTagName() {
        return Preferences.DIAGRAMMER_MCD_ASSOCIATION_XML_TAG;
    }

    public MCDAssociation getMCDAssociation() {
        return (MCDAssociation) this.relatedRepositoryElement;
    }

    public void setMCDAssociation(MCDAssociation association) {
        this.relatedRepositoryElement = association;
    }

    protected void drawDiamond(Graphics2D graphics2D, Color diamondBackgroundColor, Color diamondBorderColor) {
        RelationAnchorPointShape previousPoint = this.anchorPoints.get(this.getLastPoint().getIndex() - 1);
        RelationAnchorPointShape lastPoint = this.getLastPoint();

        int NUMBER_OF_POINTS = 4;
        int DIAMOND_WIDTH = (int) UIUtils.getCompositionDiamondWidth();
        int DIAMOND_HEIGHT = (int) UIUtils.getCompositionDiamondHeight();

        int dx = lastPoint.x - previousPoint.x, dy = lastPoint.y - previousPoint.y;
        double D = Math.sqrt(dx * dx + dy * dy);

        double xm = D - DIAMOND_HEIGHT;
        double xn = xm;
        double ym = DIAMOND_WIDTH;
        double yn = -DIAMOND_WIDTH, x;

        double sin = dy / D;
        double cos = dx / D;

        x = xm * cos - ym * sin + previousPoint.x;
        ym = xm * sin + ym * cos + previousPoint.y;
        xm = x;
        x = xn * cos - yn * sin + previousPoint.x;
        yn = xn * sin + yn * cos + previousPoint.y;
        xn = x;

        double xq = (DIAMOND_HEIGHT * 2 / D) * previousPoint.x + ((D - DIAMOND_HEIGHT * 2) / D) * lastPoint.x;
        double yq = (DIAMOND_HEIGHT * 2 / D) * previousPoint.y + ((D - DIAMOND_HEIGHT * 2) / D) * lastPoint.y;

        int[] xpoints = {lastPoint.x, (int) xm, (int) xq, (int) xn};
        int[] ypoints = {lastPoint.y, (int) ym, (int) yq, (int) yn};

        // Dessine le diamant
        graphics2D.setColor(diamondBackgroundColor);
        graphics2D.fillPolygon(xpoints, ypoints, NUMBER_OF_POINTS);

        // Dessine la bordure autour du diamant
        graphics2D.setColor(diamondBorderColor);
        graphics2D.drawPolygon(xpoints, ypoints, NUMBER_OF_POINTS);
    }

}