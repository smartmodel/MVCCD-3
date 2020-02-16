package main.window.repository;

import preferences.Preferences;
import preferences.PreferencesManager;
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
        colorBackground();
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        super.addContent(tree);
    }

    private void colorBackground() {
        if (PreferencesManager.instance().preferences().getDEBUG()) {
            if (PreferencesManager.instance().preferences().getDEBUG_BACKGROUND_PANEL()) {
                tree.setBackground(Color.RED);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public WinRepositoryTree getTree() {
        return tree;
    }
}
