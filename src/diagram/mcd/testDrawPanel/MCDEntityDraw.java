package diagram.mcd.testDrawPanel;

import mcd.MCDEntity;
import project.ProjectElement;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MCDEntityDraw {
    private static final long serialVersionUID = 1000;

    Rectangle rectangleEntite;
    Line2D.Double separationLine;
    String typeEntite = "<<MCDEntityDraw>>";
    String titre;
    ArrayList<Attributes> attributesList;
    ArrayList<Line2D.Double> linesList;


    public MCDEntityDraw(Rectangle rectangleEntite, ArrayList<Line2D.Double> linesList, ArrayList<Attributes> attributes) {
        this.rectangleEntite = rectangleEntite;
        this.linesList = linesList;
        this.attributesList = attributes;
    }

    public MCDEntityDraw(Rectangle rectangleEntite, ArrayList<Attributes> attributes) {
        this.rectangleEntite = rectangleEntite;
        this.attributesList= attributes;
    }

    public Rectangle getRectangleEntite() {
        return rectangleEntite;
    }

    public void setRectangleEntite(Rectangle rectangleEntite) {
        this.rectangleEntite = rectangleEntite;
    }

    public Line2D.Double getSeparationLine() {
        return separationLine;
    }

    public void setSeparationLine(Line2D.Double separationLine) {
        this.separationLine = separationLine;
    }

    public String getTypeEntite() {
        return typeEntite;
    }

    public void setTypeEntite(String typeEntite) {
        this.typeEntite = typeEntite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public ArrayList<Attributes> getAttributesList() {
        return attributesList;
    }

    public void setAttributesList(ArrayList<Attributes> attributesList) {
        this.attributesList = attributesList;
    }

    public ArrayList<Line2D.Double> getLinesList() {
        return linesList;
    }

    public void setLinesList(ArrayList<Line2D.Double> linesList) {
        this.linesList = linesList;
    }
}
