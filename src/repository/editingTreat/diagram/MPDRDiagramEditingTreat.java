package repository.editingTreat.diagram;

import diagram.mcd.MDTitlePanel;
import diagram.mpdr.MPDRDiagram;
import java.awt.Window;
import javax.swing.JPanel;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDWindow;
import main.window.diagram.WinDiagram;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

public class MPDRDiagramEditingTreat extends DiagramEditingTreat {

  @Override
  public MVCCDElement treatNew(Window owner, MVCCDElement parent) {

    //Remarque: le code ci-dessous est encore expérimental !

    // Création d'un objet transitoire
    MPDRDiagram newDiagram = MVCCDElementFactory.instance().createMPDRDiagram(null);

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