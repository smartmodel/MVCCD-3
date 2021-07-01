package main.window.repository;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import main.MVCCDElementApplicationPreferences;
import mcd.*;
import mldr.*;
import mpdr.MPDRColumn;
import mpdr.MPDRModel;
import mpdr.MPDRTable;
import project.Project;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * La classe met en place le redimensionnement en faisant appel aux méthodes de son ancêtre PanelBorderLayout.
 * La classe crée ensuite le contenu et le place dans le panneau redimensionnable.
 */
public class WinRepository extends JPanel {

    private WinRepositoryContent content;
    private String uriImage = "ressources/images/icones/repository/";

    public WinRepository(){

        IconFontSwing.register(FontAwesome.getIconFont());

        content = new WinRepositoryContent();

        //region Code ajouté par Antoine Frey
        repositoryIcon(content);
        //endregion

        //region Code ajouté par Antoine Frey
        content.setBackground(Color.WHITE);
        //endregion

        add(content);
    }

    public WinRepositoryContent getContent() {
        return content;
    }

    private void repositoryIcon(WinRepositoryContent content){
        content.getTree().setCellRenderer(new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                // Icone de projet
                if(node.getUserObject() instanceof Project){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "project.png").getImage().getScaledInstance(18, 15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone préférences
                if(node.getUserObject() instanceof MVCCDElementApplicationPreferences){
                    Icon icon = IconFontSwing.buildIcon(FontAwesome.COG, 15);
                    setIcon(icon);
                }
                // Icone entité
                if(node.getUserObject() instanceof MCDEntity || node.getUserObject() instanceof MLDRTable){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "entity.png").getImage().getScaledInstance(12, 15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone attribut
                if(node.getUserObject() instanceof MCDAttribute || node.getUserObject() instanceof MLDRColumn){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "mldr-entity-element.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone contrainte d'association
                if(node.getUserObject() instanceof MCDAssociation || node.getUserObject() instanceof MCDAssEnd || node.getUserObject() instanceof MLDRPK
                        || node.getUserObject() instanceof MLDRFK){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "mldr-entity-constraint.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone MLDR-R
                if(node.getUserObject() instanceof MCDContDiagrams || node.getUserObject() instanceof MLDRModel){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "MLD-R.png").getImage().getScaledInstance(15,15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone MPD-R
                if(node.getUserObject() instanceof MPDRModel){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "MPD-R.png").getImage().getScaledInstance(15,15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone entité MPD-R
                if(node.getUserObject() instanceof MPDRTable){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "entity-mpdr.png").getImage().getScaledInstance(18,15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                // Icone element MPD-R
                if(node.getUserObject() instanceof MPDRColumn){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "mpdr-entity-element.png").getImage().getScaledInstance(12,15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }

                return c;
            }
        });
    }

}
