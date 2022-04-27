package window.editor.diagrammer.elements.shapes.relations;

import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;

import java.awt.*;

public class MCDLinkShape extends RelationShape{

    private RelationShape linkedRelationShape; // Représente l'association à laquelle le link est accroché

    public MCDLinkShape(ClassShape source, RelationShape linkedRelationShape) {
        // Nous partons du principe que l'attribut source de RelationShape représente l'entité liée et que l'attribut destination est toujours null car remplacé par linkedRelationShape
        super(source, linkedRelationShape, false);
    }

    @Override
    public void setLineAspect(Graphics2D graphics2D) {
        float[] dash = { 2f, 0f, 2f };
        BasicStroke dashedStroke = new BasicStroke(1,BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,1.0f,dash,2f);
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
        return Preferences.DIAGRAMMER_MCD_LINK_XML_TAG;
    }
}
