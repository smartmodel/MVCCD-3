package window.editor.preferences.project;

import preferences.PreferencesManager;
import repository.editingTreat.preferences.*;
import utilities.window.editor.PanelNavTreeContent;
import window.editor.preferences.project.mcd.PrefMCDEditor;
import window.editor.preferences.project.general.PrefGeneralEditor;
import window.editor.preferences.project.mcdtomldr.PrefMCDToMLDREditor;
import window.editor.preferences.project.mdr.PrefMDREditor;
import window.editor.preferences.project.mdrformat.PrefMDRFormatEditor;
import window.editor.preferences.project.mldrtompdr.PrefMLDRToMPDREditor;

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
        DefaultMutableTreeNode mdr = new DefaultMutableTreeNode(PrefProjectMenu.MDR);
        root.add( mdr);
        DefaultMutableTreeNode mdrFormat = new DefaultMutableTreeNode(PrefProjectMenu.MDRFormat);
        root.add( mdrFormat);
        DefaultMutableTreeNode mcdToMldr = new DefaultMutableTreeNode(PrefProjectMenu.MCDToMLDR);
        root.add( mcdToMldr );
        DefaultMutableTreeNode mldrToMpdr = new DefaultMutableTreeNode(PrefProjectMenu.MLDRToMPDR);
        root.add( mldrToMpdr );
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
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MDR) {
            if (!(getEditor() instanceof PrefMDREditor)) {
                getEditor().myDispose();
                new PrefMDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MDRFormat) {
            if (!(getEditor() instanceof PrefMDRFormatEditor)) {
                getEditor().myDispose();
                new PrefMDRFormatEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MCDToMLDR) {
            if (!(getEditor() instanceof PrefMCDToMLDREditor)) {
                getEditor().myDispose();
                new PrefMCDToMLDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MLDRToMPDR) {
            if (!(getEditor() instanceof PrefMLDRToMPDREditor)) {
                getEditor().myDispose();
                new PrefMLDRToMPDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }

    }

}