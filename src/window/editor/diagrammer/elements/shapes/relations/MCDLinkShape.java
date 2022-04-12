package window.editor.diagrammer.elements.shapes.relations;

import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;

import java.awt.*;

public class MCDLinkShape extends RelationShape{

    private RelationShape linkedRelationShape; // Représente l'association à laquelle le link est accroché

    public MCDLinkShape(ClassShape source, RelationShape linkedRelationShape) {
        // Nous partons du principe que l'attribut source de RelationShape représente l'entité liée et que l'attribut destination est toujours null car remplacé par linkedRelationShape
        super(source, null, false);
        this.linkedRelationShape = linkedRelationShape;
    }

    @Override
    public void doDraw(Graphics2D graphics2D) {
        Point linkedRelationShapeCenter = linkedRelationShape.getCenter();
        graphics2D.drawLine(linkedRelationShapeCenter.x, linkedRelationShapeCenter.y, this.source.getX(), this.source.getY());
        System.out.println("dreaing");
    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {

    }

    @Override
    public String getXmlTagName() {
        return Preferences.DIAGRAMMER_MCD_LINK_XML_TAG;
    }
}
