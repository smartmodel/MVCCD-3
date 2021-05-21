package window.editor.preferences.project;

import console.ViewLogsManager;
import main.MVCCDManager;
import messages.MessagesBuilder;
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
import window.editor.preferences.project.mpdr.postgresql.PrefMPDRPostgreSQLEditor;

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
        String messageExceptionTarget = "";
        try {
            if (rightClickedNode.getUserObject() == PrefProjectMenu.GENERAL) {
                messageExceptionTarget = "project.preferences.generalities";
                if (!(getEditor() instanceof PrefGeneralEditor)){
                    getEditor().myDispose();
                    new PrefGeneralEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MCD) {
                messageExceptionTarget = "project.preferences.mcd";
                if (!(getEditor() instanceof PrefMCDEditor)) {
                    getEditor().myDispose();
                    new PrefMCDEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MDR) {
                messageExceptionTarget = "project.preferences.mdr";
                if (!(getEditor() instanceof PrefMDREditor)) {
                    getEditor().myDispose();
                    new PrefMDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MDRFormat) {
                messageExceptionTarget = "project.preferences.mdr.format";
                if (!(getEditor() instanceof PrefMDRFormatEditor)) {
                    getEditor().myDispose();
                    new PrefMDRFormatEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MLDR) {
                messageExceptionTarget = "project.preferences.mldr";
                if (!(getEditor() instanceof PrefMLDREditor)) {
                    getEditor().myDispose();
                    new PrefMLDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MCDToMLDR) {
                messageExceptionTarget = "project.preferences.mcd.to.mldr";
                if (!(getEditor() instanceof PrefMCDToMLDREditor)) {
                    getEditor().myDispose();
                    new PrefMCDToMLDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MPDROracle) {
                messageExceptionTarget = "project.preferences.mpr.oracle";
                if (!(getEditor() instanceof PrefMPDROracleEditor)) {
                    getEditor().myDispose();
                    new PrefMPDROracleEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MPDRMySQL) {
                messageExceptionTarget = "project.preferences.mpr.mysql";
                if (!(getEditor() instanceof PrefMPDRMySQLEditor)) {
                    getEditor().myDispose();
                    new PrefMPDRMySQLEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MPDRMySQL) {
                messageExceptionTarget = "project.preferences.mpr.postgresql";
                if (!(getEditor() instanceof PrefMPDRPostgreSQLEditor)) {
                    getEditor().myDispose();
                    new PrefMPDRPostgreSQLEditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
            if (rightClickedNode.getUserObject() == PrefProjectMenu.MLDRToMPDR) {
                messageExceptionTarget = "project.preferences.mldr.to.mpdr";
                if (!(getEditor() instanceof PrefMLDRToMPDREditor)) {
                    getEditor().myDispose();
                    new PrefMLDRToMPDREditingTreat().treatUpdate(getEditor().getOwner(), PreferencesManager.instance().getProjectPref());
                }
            }
        } catch(Exception exception){
            String messageException = MessagesBuilder.getMessagesProperty("project.preferences.menu.exception",
                    messageExceptionTarget);
            ViewLogsManager.catchException(exception, MVCCDManager.instance().getMvccdWindow(), messageException);
        }
    }

}