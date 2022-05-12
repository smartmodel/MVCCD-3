package window.editor.diagrammer.elements.shapes.relations;

import m.interfaces.IMRelation;
import main.MVCCDManager;
import mcd.MCDAssociation;
import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Inspiré de https://coderanch.com/t/340443/java/Draw-arrow-head-line
 */

public abstract class RelationShapeDashed extends MCDAssociationShape {
    double phi = Math.toRadians(25);
    int barb = 16;

    public RelationShapeDashed(MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        super(source, destination, false);
    }

    public RelationShapeDashed(int id, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        super(id, source, destination, false);
    }

    public RelationShapeDashed(int id, MCDAssociation relatedRepositoryAssociation, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
        super(id, relatedRepositoryAssociation, source, destination, false);
    }

    @Override
    public void drawSegments(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pour chaque point d'ancrage
        for (int i = 0; i < this.pointsAncrage.size(); i++) {
            if (i != this.pointsAncrage.size() - 1) {
                // Tests de lignes ici
                float[] dash1 = {2f, 0f, 2f};
                var bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
                graphics2D.setStroke(bs1);
                graphics2D.drawLine((int) this.pointsAncrage.get(i).getX(), (int) this.pointsAncrage.get(i).getY(), (int) this.pointsAncrage.get(i + 1).getX(), (int) this.pointsAncrage.get(i + 1).getY());
                // Test de tête de flèche
                Point sw = new Point((int) this.pointsAncrage.get(i).getX(), (int) this.pointsAncrage.get(i).getY());
                Point ne = new Point((int) this.pointsAncrage.get(i + 1).getX(), (int) this.pointsAncrage.get(i + 1).getY());

                drawArrowHead(graphics2D, ne, sw);
            }
        }
    }

    private void drawArrowHead(Graphics2D graphics2D, Point tip, Point tail) {
        // On redonne un aspect normal à l'objet Graphics2D afin de dessiner la tête de la flèche sans traits tillés
        var bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, null, 0);
        graphics2D.setStroke(bs);

        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);

        double x, y, rho = theta + phi;
        for (int j = 0; j < 2; j++) {
            x = tip.x - barb * Math.cos(rho);
            y = tip.y - barb * Math.sin(rho);
            graphics2D.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - phi;
        }
    }


}

