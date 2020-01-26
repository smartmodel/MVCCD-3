package main.window.repository;

import preferences.Preferences;
import utilities.window.PanelContent;

import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinRepositoryContent extends PanelContent implements ActionListener {

    private WinRepositoryTree tree;

    public WinRepositoryContent(WinRepository winRepository) {
        super(winRepository);

        tree = new WinRepositoryTree(null);
        if ( Preferences.DEBUG_BACKGROUND_PANEL){
            tree.setBackground(Color.RED);
        }
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        super.addContent(tree);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public WinRepositoryTree getTree() {
        return tree;
    }
}
