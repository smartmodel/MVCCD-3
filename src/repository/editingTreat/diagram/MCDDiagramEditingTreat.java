package repository.editingTreat.diagram;

import diagram.mcd.MCDDiagram;
import diagram.mcd.MDTitlePanel;
import java.awt.Window;
import javax.swing.JPanel;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import main.MVCCDWindow;
import main.window.diagram.WinDiagram;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

/**
 * Cette classe implémante le diagrammeur (DiagramEditingTreat) spécifique à l'élaboration d'un MCD. Elle spécialise donc: - Le panneau d'en-tête (à priori) - le panneau de palette (certainement) - le panneau de zone de dessin (à priori)
 */
public class MCDDiagramEditingTreat extends DiagramEditingTreat {

  /**
   * La méthode crée un nouveau diagramme.
   * @param owner
   * @param parent
   * @return
   */
  @Override
  public MVCCDElement treatNew(Window owner, MVCCDElement parent) {

    //Remarque: le code ci-dessous est encore expérimental !

    // Création d'un objet transitoire
    MCDDiagram newDiagram = MVCCDElementFactory.instance().createMCDDiagram(null);

    //MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
    MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
    WinDiagram winDiagram = mvccdWindow.getDiagrammer();
    JPanel panelTitle = winDiagram.getContent().getPanelTitle();

    MDTitlePanel MDTitlePanel = new MDTitlePanel(parent, panelTitle, DialogEditor.NEW, newDiagram);
    MDTitlePanel.getContent();

    //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
    // Et voir le lien avec le paramètre Window owner
    //winDiagram.resizeContent();
    mvccdWindow.setSize(mvccdWindow.getWidth() - 1, mvccdWindow.getHeight() - 1);
    mvccdWindow.setSize(mvccdWindow.getWidth() + 1, mvccdWindow.getHeight() + 1);

    return newDiagram;
  }

  @Override
  public boolean treatUpdate(Window owner, MVCCDElement element) {
    MVCCDElement parentBefore = element.getParent();

    MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
    WinDiagram winDiagram = mvccdWindow.getDiagrammer();
    JPanel panelTitle = winDiagram.getContent().getPanelTitle();
    MDTitlePanel MDTitlePanel = new MDTitlePanel(parentBefore, panelTitle, DialogEditor.UPDATE, (MCDDiagram) element);
    MDTitlePanel.getContent();

    //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
    //winDiagram.resizeContent();
    mvccdWindow.setSize(mvccdWindow.getWidth() - 1, mvccdWindow.getHeight() - 1);
    mvccdWindow.setSize(mvccdWindow.getWidth() + 1, mvccdWindow.getHeight() + 1);

    MVCCDElement parentAfter = element.getParent();
    if (parentBefore != parentAfter) {
      MVCCDManager.instance().changeParentMVCCDElementInRepository(element, parentBefore);
    }

    //TODO-1 A voir pour récupérer le changement effectif
    return true;

  }

  @Override
  public DialogEditor treatRead(Window owner, MVCCDElement element) {
    MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
    WinDiagram winDiagram = mvccdWindow.getDiagrammer();
    JPanel panelTitle = winDiagram.getContent().getPanelTitle();
    MDTitlePanel MDTitlePanel = new MDTitlePanel(element.getParent(), panelTitle, DialogEditor.READ, (MCDDiagram) element);
    MDTitlePanel.getContent();

    //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
    //winDiagram.resizeContent();
    mvccdWindow.setSize(mvccdWindow.getWidth() - 1, mvccdWindow.getHeight() - 1);
    mvccdWindow.setSize(mvccdWindow.getWidth() + 1, mvccdWindow.getHeight() + 1);

    //TODO-1 A voir le choix du retour dans EditingTreat
    return null;
  }

  @Override
  protected PanelInputContent getPanelInputContent(MVCCDElement element) {
    return null;
  }

  @Override
  protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
    return null;
  }

  @Override
  protected String getPropertyTheElement() {
    return "the.diagram";
  }

}