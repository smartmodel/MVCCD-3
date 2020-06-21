package window.editor.preferences.project;

import preferences.PreferencesManager;
import repository.editingTreat.preferences.PrefGeneralEditingTreat;
import repository.editingTreat.preferences.PrefMCDEditingTreat;
import utilities.window.editor.PanelNavTreeContent;
import window.editor.preferences.project.mcd.PrefMCDEditor;
import window.editor.preferences.project.general.PrefGeneralEditor;

import javax.swing.tree.DefaultMutableTreeNode;

public class PrefNavTreeContent extends PanelNavTreeContent {



    public PrefNavTreeContent(PrefNavTree prefNavTree) {
        super(prefNavTree);
    }



    @Override
    protected Object getTitleRootNode() {
        return "Préférences du projet";
    }

    @Override
    protected void createContentCustom() {

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) super.getTree().getModel().getRoot();

        DefaultMutableTreeNode general = new DefaultMutableTreeNode(PrefProjectMenu.GENERAL);
        root.add( general);
        DefaultMutableTreeNode mcd = new DefaultMutableTreeNode(PrefProjectMenu.MCD);
        root.add( mcd );
    }

    @Override
    protected void clickedNode(DefaultMutableTreeNode rightClickedNode) {
        if (rightClickedNode.getUserObject() == PrefProjectMenu.GENERAL) {
            if (!(getEditor() instanceof PrefGeneralEditor)){
                getEditor().myDispose();
                new PrefGeneralEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MCD) {
            if (!(getEditor() instanceof PrefMCDEditor)) {
                getEditor().myDispose();
                new PrefMCDEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }

        }

    }

}