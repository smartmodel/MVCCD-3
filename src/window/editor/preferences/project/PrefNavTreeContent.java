package window.editor.preferences.project;

import preferences.PreferencesManager;
import repository.editingTreat.preferences.*;
import utilities.window.editor.PanelNavTreeContent;
import window.editor.preferences.project.general.PrefGeneralEditor;
import window.editor.preferences.project.mcd.PrefMCDEditor;
import window.editor.preferences.project.mcdtomldr.PrefMCDToMLDREditor;
import window.editor.preferences.project.mdr.PrefMDREditor;
import window.editor.preferences.project.mdrformat.PrefMDRFormatEditor;
import window.editor.preferences.project.mldr.PrefMLDREditor;
import window.editor.preferences.project.mldrtompdr.PrefMLDRToMPDREditor;
import window.editor.preferences.project.mpdr.mysql.PrefMPDRMySQLEditor;
import window.editor.preferences.project.mpdr.oracle.PrefMPDROracleEditor;

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
        DefaultMutableTreeNode mldr = new DefaultMutableTreeNode(PrefProjectMenu.MLDR);
        root.add( mldr);
        DefaultMutableTreeNode mcdToMldr = new DefaultMutableTreeNode(PrefProjectMenu.MCDToMLDR);
        root.add( mcdToMldr );
        DefaultMutableTreeNode mpdr = new DefaultMutableTreeNode(PrefProjectMenu.MPDR);
        root.add( mpdr);
        DefaultMutableTreeNode mpdrOracle = new DefaultMutableTreeNode(PrefProjectMenu.MPDROracle);
        mpdr.add( mpdrOracle);
        DefaultMutableTreeNode mpdrMySQL = new DefaultMutableTreeNode(PrefProjectMenu.MPDRMySQL);
        mpdr.add( mpdrMySQL);
        DefaultMutableTreeNode mpdrPostgreSQL = new DefaultMutableTreeNode(PrefProjectMenu.MPDRPostgreSQL);
        mpdr.add( mpdrPostgreSQL);
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
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MLDR) {
            if (!(getEditor() instanceof PrefMLDREditor)) {
                getEditor().myDispose();
                new PrefMLDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MCDToMLDR) {
            if (!(getEditor() instanceof PrefMCDToMLDREditor)) {
                getEditor().myDispose();
                new PrefMCDToMLDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MPDROracle) {
            if (!(getEditor() instanceof PrefMPDROracleEditor)) {
                getEditor().myDispose();
                new PrefMPDROracleEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
            }
        }
        if (rightClickedNode.getUserObject() == PrefProjectMenu.MPDRMySQL) {
            if (!(getEditor() instanceof PrefMPDRMySQLEditor)) {
                getEditor().myDispose();
                new PrefMPDRMySQLEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
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