package window.editor.diagrammer.menus;

import window.editor.diagrammer.elements.shapes.relations.*;
import window.editor.diagrammer.menus.actions.MCDAssociationEditAction;
import window.editor.diagrammer.menus.actions.MCDGeneralizationEditAction;
import window.editor.diagrammer.menus.actions.RelationAddPointAncrageAction;
import window.editor.diagrammer.menus.actions.RelationDeleteAction;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class RelationShapeMenu extends JPopupMenu implements Serializable {

    private static final long serialVersionUID = 1000;

    public RelationShapeMenu(RelationShape shape, int x, int y) {
        super();
        JMenuItem edit = null;
        if (shape instanceof MCDAssociationShape) {
            edit = new JMenuItem(new MCDAssociationEditAction("Ouvrir l'assistant de modélisation", null, (MCDAssociationShape) shape));
        } else if (shape instanceof MCDGeneralizationShape) {
            edit = new JMenuItem(new MCDGeneralizationEditAction("Ouvrir l'assistant de modélisation", null, (MCDGeneralizationShape) shape));
        } else if (shape instanceof MCDCompositionShape) {
            // TODO -> Ajouter le traitement pour l'association de composition
        } else if (shape instanceof MPDAssociationShape) {
            // TODO -> Ajouter le traitement pour l'association MPD
        }
        if (edit != null) {
            this.add(edit);
        }
        // Ajout d'un point d'ancrage sur la relation
        JMenuItem addPointAncrage = new JMenuItem(new RelationAddPointAncrageAction("Ajouter un point d'ancrage", null, shape, new Point(x, y)));
        this.add(addPointAncrage);
        // Suppression d'une relation
        JMenuItem delete = new JMenuItem(new RelationDeleteAction("Supprimer", null, shape));
        this.add(delete);
    }

}
