package window.editor.diagrammer.listeners;

import diagram.Diagram;
import diagram.mcd.MCDDiagram;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import main.MVCCDManager;
import main.window.repository.WinRepositoryPopupMenu;
import project.ProjectElement;
import window.editor.diagrammer.services.DiagrammerService;

public class ReferentielListener extends MouseAdapter implements TreeSelectionListener,
    TreeExpansionListener {

  private DefaultMutableTreeNode node;

  public void valueChanged(TreeSelectionEvent evt) {
    if (evt.getNewLeadSelectionPath() != null) {
      // récupérer le noeud sélectionné
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)
          evt.getNewLeadSelectionPath().getLastPathComponent();
    }
  }

  @Override
  public void treeExpanded(TreeExpansionEvent evt) {
    memorizeLastNodeUsed(evt, true);
  }

  @Override
  public void treeCollapsed(TreeExpansionEvent evt) {
    memorizeLastNodeUsed(evt, false);
  }

  private void memorizeLastNodeUsed(TreeExpansionEvent evt, boolean expandCollapse) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();

    ProjectElement lastProjectElement = null;
    if (node.getUserObject() instanceof ProjectElement) {
      lastProjectElement = (ProjectElement) node.getUserObject();
    }
    if (MVCCDManager.instance().getProject() != null) {
      MVCCDManager.instance().getProject().setLastWinRepositoryProjectElement(lastProjectElement);
      MVCCDManager.instance().getProject().setLastWinRepositoryExpand(expandCollapse);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {

    JTree tree = (JTree) e.getSource();
    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
    tree.setSelectionPath(path);

    if (path == null) {
      return;
    }

    DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

    if (clickedNode != null) {
      // Code pour l'ouverture du diagramme MCD, MLDR et MPDR
      if (clickedNode.getUserObject() instanceof Diagram) {
        if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e) && !e.isConsumed()) {
          e.consume();
          DiagrammerService.getDrawPanel().unloadAllShapes();
          Diagram diagramClicked = (Diagram) clickedNode.getUserObject();

          // On supprime la palette d'éléments spécifique au MCD si un autre type de modèle est ouvert
          if (!(clickedNode.getUserObject() instanceof MCDDiagram)) {
            MVCCDManager.instance().getWinDiagram().getContent().removePalette();

          } else {
            MVCCDManager.instance().getWinDiagram().getContent().addPalette();
          }

          MVCCDManager.instance().setCurrentDiagram(diagramClicked);
        }
      }
    }
  }

  public void mousePressed(MouseEvent e) {
    if (e.isPopupTrigger()) {
      myPopupEvent(e);
    }
    JTree c = (JTree) e.getSource();

    // Pour récupérer le noeud sur lequel l'utilisateur a cliqué (utile pour les tables du MPDR)
    this.node = (DefaultMutableTreeNode) c.getLastSelectedPathComponent();
  }

  private void myPopupEvent(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    JTree tree = (JTree) e.getSource();
    TreePath path = tree.getPathForLocation(x, y);
    if (path == null) {
      return;
    }

    tree.setSelectionPath(path);

    DefaultMutableTreeNode clickedNode =
        (DefaultMutableTreeNode) path.getLastPathComponent();

    if (clickedNode != null) {

      WinRepositoryPopupMenu popup = new WinRepositoryPopupMenu(clickedNode);
      popup.show(tree, x, y);
    }
  }


  public void mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger()) {
      myPopupEvent(e);
    }
  }


  public DefaultMutableTreeNode getNode() {
    return node;
  }
}
