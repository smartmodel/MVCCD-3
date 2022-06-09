package main.window.repository;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import preferences.PreferencesManager;
import utilities.window.PanelContent;
import window.editor.diagrammer.listeners.DropTargetListener;
import window.editor.diagrammer.listeners.ReferentielListener;
import window.editor.diagrammer.services.DiagrammerService;

/**
 * La classe crée l'arbre de représentation du référentiel.
 * Elle ajoute ensuite l'arbre comme contenu de son ancêtre PanelContent.
 * L'ancêtre PanelContent crée la barre de défilement si nécessaire.
 */
public class WinRepositoryContent extends PanelContent implements ActionListener {

    private WinRepositoryTree tree;

    public WinRepositoryContent(WinRepository winRepository) {
        super(winRepository);

        // Création de l'arbre de représentation du référentiel
        tree = new WinRepositoryTree(null); //Remarque: l'arbre est créé vide. Le contenu est créé par les méthodes MVCCDManager.startRepository() puis MVCCDManager.openLastProject().
        colorBackground();
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setDragEnabled(true);
        ReferentielListener referentielListener = new ReferentielListener();
        tree.addMouseListener(referentielListener);
        new DropTargetListener(DiagrammerService.getDrawPanel(), referentielListener);

        // Place l'arbre dans son ancêtre PanelBorder
        super.addContent(tree);
    }

    private void colorBackground() {
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            if (PreferencesManager.instance().preferences().isDEBUG_BACKGROUND_PANEL()) {
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

    public void reload(DefaultMutableTreeNode node) {
        tree.getTreeModel().reload(node);
    }
}
