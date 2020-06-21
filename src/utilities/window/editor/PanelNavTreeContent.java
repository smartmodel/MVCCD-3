package utilities.window.editor;

import messages.MessagesBuilder;
import utilities.window.DialogMessage;
import utilities.window.PanelContent;
import utilities.window.scomponents.STree;
import utilities.window.scomponents.services.STreeService;
import window.editor.preferences.project.PrefProject;
import window.editor.preferences.project.PrefProjectMenu;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class PanelNavTreeContent extends PanelContent  implements IAccessDialogEditor{

    //TODO-1 A voir si panel ne peut pas être la classe elle-même
    protected JPanel panelNavContentCustom = new JPanel();

    protected PanelNavTree panelNavTree;
    private STree tree;



    public PanelNavTreeContent(PanelNavTree panelNavTree) {
        super(panelNavTree);
        this.panelNavTree = panelNavTree;
        panelNavTree.setPanelContent(this);

        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode(getTitleRootNode());
        tree = new STree(top);

        // add MouseListener to tree
        // Repris de WinRepositoryTree (exemple de gestion de popup)
        //TODO-1 A factoriser
        MouseAdapter ma = new MouseAdapter() {
            private void myPopupEvent(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                JTree tree = (JTree)e.getSource();
                TreePath path = tree.getPathForLocation(x, y);
                if (path == null)
                    return;

                tree.setSelectionPath(path);
                DefaultMutableTreeNode rightClickedNode =
                        (DefaultMutableTreeNode)path.getLastPathComponent();
                if(rightClickedNode != null){
                    if (getEditor().getInput().getInputContent().datasChangedNow()){
                        DialogMessage.showError(getEditor(),
                                MessagesBuilder.getMessagesProperty("editor.nav.tree.error.datas.changed"));
                    } else {
                        clickedNode(rightClickedNode);
                        String clickedString = (String) rightClickedNode.getUserObject().toString();
                        super.mousePressed(e);
                    }
                }
            }
            public void mousePressed(MouseEvent e) {
                myPopupEvent(e);
            }
            public void mouseReleased(MouseEvent e) { myPopupEvent(e); }
        };
        tree.addMouseListener(ma);
        panelNavContentCustom.add(tree);
    }

    protected abstract void clickedNode(DefaultMutableTreeNode rightClickedNode);

    protected abstract Object getTitleRootNode();

    protected void start(){
        createContentCustom();
        showNode();
        addContent(panelNavContentCustom);
    }



    protected abstract void createContentCustom();

    public DialogEditor getEditor(){
        return panelNavTree.getEditor();
    }

    public JTree getTree() {
        return tree;
    }


    public void showNode() {
            DefaultMutableTreeNode lastNode = STreeService.findNode(tree,
                    ((DialogEditorNavTree) getEditor()).getNavNodeMenu());
            TreeNode[] lastArrayNodes = lastNode.getPath();
            TreePath lastPath = new TreePath (lastArrayNodes);
            tree.collapsePath(lastPath);

    }


}
