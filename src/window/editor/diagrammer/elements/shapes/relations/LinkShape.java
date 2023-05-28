package window.editor.diagrammer.elements.shapes.relations;

import window.editor.diagrammer.elements.shapes.classes.ClassShape;

import java.awt.*;

/*** Une LinkShape représente une relation qui peut être crochée à une SquaredShape ou à une RelationShape. */
public class LinkShape extends RelationShape {

    public LinkShape(ClassShape source, RelationShape linkedRelationShape) {
        super(source, linkedRelationShape, false);
    }

    @Override
    public void defineLineAspect(Graphics2D graphics2D) {
        float[] dash = {2f, 0f, 2f};
        BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        graphics2D.setStroke(dashedStroke);
    }

    @Override
    public void doDraw(Graphics2D graphics2D) {

    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {

    }

    @Override
    public String getXmlTagName() {
        return null;
    }
}
