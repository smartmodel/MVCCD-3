package main.window.repository;

import diagram.mcd.MCDDiagram;
import diagram.mldr.MLDRDiagram;
import diagram.mpdr.MPDRDiagram;
import mcd.MCDAssociation;
import mcd.MCDAttribute;
import mcd.MCDConstraint;
import mcd.MCDEntity;
import mldr.*;
import mpdr.*;
import mpdr.services.MPDRConstraintService;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class RepositoryTreeCellRenderer extends DefaultTreeCellRenderer {

    private final String uriImage = "ressources/icons-referentiel/";
    private final String extension = "png";

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
        Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

        this.setIcon(node);

        return c;
    }

    private void setIcon(DefaultMutableTreeNode node) {

        String iconPath = "";

        if (node.getUserObject() instanceof MCDEntity) {
            iconPath = "mcd/entite";
        } else if (node.getUserObject() instanceof MCDAssociation) {
            iconPath = "mcd/association";
        } else if (node.getUserObject() instanceof MCDDiagram) {
            iconPath = "mcd/diagram";
        } else if (node.getUserObject() instanceof MCDConstraint) {
            iconPath = "mcd/constraint";
        } else if (node.getUserObject() instanceof MCDAttribute) {
            iconPath = "mcd/attribute";
        } else if (node.getUserObject() instanceof MLDRColumn) {
            iconPath = "mldr/column";
        } else if (node.getUserObject() instanceof MLDRConstraintCustomSpecialized || node.getUserObject() instanceof MLDRConstraintCustomAudit || node.getUserObject() instanceof MLDRConstraintCustomJnal) {
            iconPath = "mldr/constraint";
        } else if (node.getUserObject() instanceof MLDRFK) {
            iconPath = "mldr/foreign-key-constraint";
        } else if (node.getUserObject() instanceof MLDRDiagram) {
            iconPath = "mldr/diagram";
        } else if (node.getUserObject() instanceof MLDRTable) {
            iconPath = "mldr/table";
        } else if (node.getUserObject() instanceof MPDRColumn) {
            iconPath = "mpdr/column";
        } else if (node.getUserObject() instanceof MPDRConstraintSpecificRole || node.getUserObject() instanceof MPDRConstraintCustom || node.getUserObject() instanceof MPDRConstraintSpecificRole || node.getUserObject() instanceof MPDRConstraintCustomJnal || node.getUserObject() instanceof MPDRConstraintCustomAudit || node.getUserObject() instanceof MPDRConstraintService) {
            iconPath = "mpdr/constraint";
        } else if (node.getUserObject() instanceof MPDRFK) {
            iconPath = "mpdr/foreign-key-constraint";
        } else if (node.getUserObject() instanceof MPDRDiagram) {
            iconPath = "mpdr/diagram";
        } else if (node.getUserObject() instanceof MPDRPK) {
            iconPath = "mpdr/primary-key-constraint";
        } else if (node.getUserObject() instanceof MPDRTable) {
            iconPath = "mpdr/table";
        }

        // On set l'icon
        Icon imageIcon = new ImageIcon(new ImageIcon(this.uriImage + this.extension + '/' + iconPath + '.' + this.extension).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);

    }
}
