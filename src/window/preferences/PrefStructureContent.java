package window.preferences;

import utilities.window.PanelContent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class PrefStructureContent extends PanelContent {
   private JTree tree;

    public PrefStructureContent(PrefStructure prefStructure) {
        super(prefStructure);


        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("The Java Series");
        createNodes(top);
        tree = new JTree(top);

        super.addContent(tree);

    }

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode first = null;
        DefaultMutableTreeNode second = null;

        first = new DefaultMutableTreeNode("MCCD");
        top.add(first);
        first = new DefaultMutableTreeNode("MCCD -> MLD-R");
        top.add(first);
        first = new DefaultMutableTreeNode("MLD-R");
        top.add(first);

        second = new DefaultMutableTreeNode("Général");
        first.add(second);

    }

}
