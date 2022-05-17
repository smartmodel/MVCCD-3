package window.editor.diagrammer.elements.shapes.relations.mcd;

import mcd.MCDGeneralization;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

import java.awt.*;
import java.io.Serializable;

public class MCDGeneralizationShape extends RelationShape implements Serializable {

    private static final long serialVersionUID = -4638463083584589287L;

    public MCDGeneralizationShape(MCDGeneralization relatedRepositoryGeneralization, MCDEntityShape source, MCDEntityShape destination) {
        this(source, destination);
        this.relatedRepositoryElement = relatedRepositoryGeneralization;
    }

    public MCDGeneralizationShape(MCDEntityShape source, MCDEntityShape destination) {
        super(source, destination, false);
    }

    @Override
    public void defineLineAspect(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(1));
    }

    @Override
    public void doDraw(Graphics2D graphics2D) {
        this.drawArrow(graphics2D);
    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {

    }

    @Override
    public String getXmlTagName() {
        return Preferences.DIAGRAMMER_MCD_GENERALIZATION_XML_TAG;
    }

    public void drawArrow(Graphics2D graphics2D) {
        // TODO -> Faire en sorte que la flêche ait une bordune noire et un fond blanc

        final RelationAnchorPointShape previousPoint = this.anchorPoints.get(this.anchorPoints.get(this.getAnchorPoints().size() - 1).getIndex() - 1);
        final RelationAnchorPointShape lastPoint = this.anchorPoints.get(this.getAnchorPoints().size() - 1);
        final int differenceX = lastPoint.x - previousPoint.x;
        final int differenceY = lastPoint.y - previousPoint.y;
        final double squareRoot = Math.sqrt(differenceX * differenceX + differenceY * differenceY);

        final int ARROW_WIDTH = 8;
        final int ARROW_HEIGHT = 13;

        double xm = squareRoot - ARROW_HEIGHT;
        double xn = xm;
        double ym = ARROW_WIDTH;
        double yn = -ARROW_WIDTH, x;

        final double sin = differenceY / squareRoot;
        final double cos = differenceX / squareRoot;
        final int NUMBER_OF_POINTS = 3;

        x = xm * cos - ym * sin + previousPoint.x;
        ym = xm * sin + ym * cos + previousPoint.y;
        xm = x;
        x = xn * cos - yn * sin + previousPoint.x;
        yn = xn * sin + yn * cos + previousPoint.y;
        xn = x;

        int[] xPoints = {lastPoint.x, (int) xm, (int) xn};
        int[] yPoints = {lastPoint.y, (int) ym, (int) yn};

        Graphics2D copied = (Graphics2D) graphics2D.create();
        copied.setColor(Color.WHITE);
        copied.fillPolygon(xPoints, yPoints, NUMBER_OF_POINTS);
        copied.setColor(Color.BLACK);
        copied.drawPolygon(xPoints, yPoints, NUMBER_OF_POINTS);
    }

    public MCDGeneralization getGeneralization() {
        return (MCDGeneralization) this.relatedRepositoryElement;
    }

    public void setGeneralization(MCDGeneralization generalization) {
        this.relatedRepositoryElement = generalization;
    }
}