package main.window.repository;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import main.MVCCDElementApplicationPreferences;
import mcd.MCDAttribute;
import mcd.MCDContDiagrams;
import mcd.MCDEntity;
import mldr.MLDRModel;
import mpdr.MPDRModel;
import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

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

                if(node.getUserObject() instanceof MVCCDElementApplicationPreferences){
                    Icon icon = IconFontSwing.buildIcon(FontAwesome.COG, 15);
                    setIcon(icon);
                }
                if(node.getUserObject() instanceof MCDAttribute){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "entity.png").getImage().getScaledInstance(12, 15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                if(node.getUserObject() instanceof MCDEntity){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "entity.png").getImage().getScaledInstance(12, 15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                if(node.getUserObject() instanceof MCDContDiagrams || node.getUserObject() instanceof MLDRModel){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "MLD-R.png").getImage().getScaledInstance(15,15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }
                if(node.getUserObject() instanceof MPDRModel){
                    Icon imageIcon = new ImageIcon(new ImageIcon(uriImage + "MPD-R.png").getImage().getScaledInstance(15,15, Image.SCALE_DEFAULT));
                    setIcon(imageIcon);
                }

                return c;
            }
        });
    }

}
