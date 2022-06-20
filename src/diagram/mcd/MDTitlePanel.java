package diagram.mcd;

import console.ViewLogsManager;
import diagram.MDDiagram;
import diagram.mpdr.MPDRDiagram;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mcd.MCDContEntities;
import mcd.MCDContRelations;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.MCDGeneralization;
import mcd.MCDLink;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import messages.MessagesBuilder;
import preferences.Preferences;
import repository.editingTreat.diagram.MCDDiagramEditingTreat;
import repository.editingTreat.diagram.MPDRDiagramEditingTreat;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.services.PanelService;

/**
 * La classe réalise l'en-tête des diagrammes de type MCD (en finalité, cette classe sera probablement généralisé en TitlePanel).
 */
public class MDTitlePanel {

  MVCCDElement parent;        // Elément parent du diagramme traité
  String mode;               // Mode d'édition DialogEditor.NEW ou autre
  MDDiagram diagram;         // Diagramme traité
  boolean created = false;    // Indicateur de diagramme créé
  private JPanel panelTitle;  // Paneau d'en-tête de la zone du diagrammeur

  public MDTitlePanel(MVCCDElement parent, JPanel panelTitle, String mode, MDDiagram diagram) {
    this.panelTitle = panelTitle;
    this.parent = parent;
    this.mode = mode;
    this.diagram = diagram;
  }

  /**
   * Dans le traitement de cette méthode, il y a notamment: - la création d'un bouton (btnCancel) qui fait appel à la méthode se chargeant de fermer le diagrammeur; - la création d'un autre bouton (btnApply) qui effectue les changements au sein de l'arbre du projet et qui synchronise la vue du référentiel.
   */
  public void getContent() {
    // Réinitialisation
    //TODO-1 A affiner
    this.panelTitle.removeAll();

    GridBagConstraints gbc = PanelService.createGridBagConstraints(this.panelTitle);

    JLabel labelName = new JLabel("Nom");
    JTextField fieldName = new JTextField();
    fieldName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));

    if (!this.mode.equals(DialogEditor.NEW)) {
      fieldName.setText(this.diagram.getName());
    }
    if (this.mode.equals(DialogEditor.READ)) {
      fieldName.setEnabled(false);
    }
    JButton btnAdd = new JButton("Simulation de création d'éléments");
    btnAdd.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String propertyBtn = "diagram.btn.exception.simulation";
        try {
          String message = "Choisisez la sorte d'élément à ajouter dans le projet";
          Object[] options = {"Annuler", "Entité", "Association", "Généralisation", "Entité ass."};
          MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
          int posOption = DialogMessage.showOptions(mvccdWindow, message, options, JOptionPane.UNINITIALIZED_VALUE);
          if (posOption > 0) {
            MCDElement newElement = null;
            DialogEditor fen = null;
            if (posOption == 1) {
              MCDContEntities mcdContEntities = (MCDContEntities) MDTitlePanel.this.parent.getBrotherByClassName(MCDContEntities.class.getName());
              MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
              MCDEntity newMCDEntity = (MCDEntity) mcdEntityEditingTreat.treatNew(mvccdWindow, mcdContEntities);
            }

            if (posOption == 2) {
              //IMCDModel iMCDModel = IMCDModelService.getIMCDModelContainer((MCDElement) parent);
              IMCDModel iMCDModel = ((MCDElement) MDTitlePanel.this.parent).getIMCDModelAccueil();
              ArrayList<MCDEntity> mcdEntities = IMCDModelService.getMCDEntities(iMCDModel);

              MCDEntity mcdEntityFrom = null;
              MCDEntity mcdEntityTo = null;
              if (mcdEntities.size() == 1) {
                mcdEntityFrom = mcdEntities.get(0);
                mcdEntityTo = mcdEntities.get(0);
              } else if (mcdEntities.size() > 1) {
                mcdEntityFrom = mcdEntities.get(0);
                mcdEntityTo = mcdEntities.get(1);
              }

              MCDContRelations mcdContRelations = (MCDContRelations) MDTitlePanel.this.parent.getBrotherByClassName(MCDContRelations.class.getName());
              MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
              MCDAssociation newMCDAssociation = mcdAssociationEditingTreat.treatNew(mvccdWindow, mcdContRelations, mcdEntityFrom, mcdEntityTo, MCDAssociationNature.NOID, false);
            }

            if (posOption == 3) {
              //IMCDModel iMCDModel = IMCDModelService.getIMCDModelContainer((MCDElement) parent);
              IMCDModel iMCDModel = ((MCDElement) MDTitlePanel.this.parent).getIMCDModelAccueil();
              ArrayList<MCDEntity> mcdEntities = IMCDModelService.getMCDEntities(iMCDModel);

              MCDEntity mcdEntityGen = null;
              MCDEntity mcdEntitySpec = null;
              if (mcdEntities.size() == 1) {
                mcdEntityGen = mcdEntities.get(0);
                mcdEntitySpec = mcdEntities.get(0);
              } else if (mcdEntities.size() > 1) {
                mcdEntityGen = mcdEntities.get(0);
                mcdEntitySpec = mcdEntities.get(1);
              }

              MCDContRelations mcdContRelations = (MCDContRelations) MDTitlePanel.this.parent.getBrotherByClassName(MCDContRelations.class.getName());
              MCDGeneralizationEditingTreat mcdGeneralizationEditingTreat = new MCDGeneralizationEditingTreat();
              MCDGeneralization newMCDGeneralization = mcdGeneralizationEditingTreat.treatNew(mvccdWindow, mcdContRelations, mcdEntityGen, mcdEntitySpec, false);
            }

            if (posOption == 4) {
              //IMCDModel iMCDModel = IMCDModelService.getIMCDModelContainer((MCDElement) parent);
              IMCDModel iMCDModel = ((MCDElement) MDTitlePanel.this.parent).getIMCDModelAccueil();
              ArrayList<MCDEntity> mcdEntities = IMCDModelService.getMCDEntities(iMCDModel);
              ArrayList<MCDAssociation> mcdAssociations = IMCDModelService.getAllMCDAssociationsInIModel(iMCDModel);

              MCDEntity mcdEntity = null;
              MCDAssociation mcdAssociation = null;
              if (mcdEntities.size() >= 1) {
                mcdEntity = mcdEntities.get(0);
              }
              if (mcdAssociations.size() >= 1) {
                mcdAssociation = mcdAssociations.get(0);
              }

              MCDContRelations mcdContRelations = (MCDContRelations) MDTitlePanel.this.parent.getBrotherByClassName(MCDContRelations.class.getName());
              MCDLinkEditingTreat mcdLinkEditingTreat = new MCDLinkEditingTreat();
              MCDLink newMCDLink = mcdLinkEditingTreat.treatNew(mvccdWindow, mcdContRelations, mcdEntity, mcdAssociation, false);
            }

            if (fen != null) {
              fen.setVisible(true);
              newElement = (MCDElement) fen.getMvccdElementNew();
            }

          }
        } catch (Exception exception) {
          MDTitlePanel.this.exceptionUnhandled(exception, propertyBtn);
        }
      }
    });

    // Le bouton qui fait appel à la fermeture du diagrammeur
    JButton btnCancel = new JButton("Fermer");
    btnCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String propertyBtn = "diagram.btn.exception.close";
        try {
          if (MDTitlePanel.this.diagram instanceof MCDDiagram) {
            new MCDDiagramEditingTreat().treatClose(MVCCDManager.instance().getMvccdWindow());
          }

          if (MDTitlePanel.this.diagram instanceof MPDRDiagram) {
            new MPDRDiagramEditingTreat().treatClose(MVCCDManager.instance().getMvccdWindow());
          }
        } catch (Exception exception) {
          MDTitlePanel.this.exceptionUnhandled(exception, propertyBtn);
        }
      }
    });

    // Le bouton qui effectue les changements au sein de l'arbre du projet et qui synchronise la vue du référentiel
    JButton btnApply = new JButton("Appliquer");
    btnApply.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String propertyBtn = "diagram.btn.exception.apply";
        try {
          if (MDTitlePanel.this.mode.equals(DialogEditor.NEW) && (!MDTitlePanel.this.created)) {
            // le diagram transitoire est persisté
            MDTitlePanel.this.diagram.setParent(MDTitlePanel.this.parent);
            MDTitlePanel.this.diagram.setName(fieldName.getText());
            MVCCDManager.instance().addNewMVCCDElementInRepository(MDTitlePanel.this.diagram);
            MDTitlePanel.this.created = true;
          } else {
            // Mise à jour
            MDTitlePanel.this.diagram.setName(fieldName.getText());
            MVCCDManager.instance().showMVCCDElementInRepository(MDTitlePanel.this.diagram);
          }
          //TODO-1 Véfier la mise à jour effective
          MVCCDManager.instance().setDatasProjectChanged(true);
        } catch (Exception exception) {
          MDTitlePanel.this.exceptionUnhandled(exception, propertyBtn);
        }

      }
    });

    this.panelTitle.add(labelName, gbc);
    gbc.gridx++;
    this.panelTitle.add(fieldName, gbc);
    gbc.gridx++;
    this.panelTitle.add(btnAdd);
    gbc.gridx++;
    this.panelTitle.add(btnCancel);
    gbc.gridx++;
    this.panelTitle.add(btnApply);

  }

  private void exceptionUnhandled(Exception exception, String property) {
    //TODO-1 A voir si window doit être passé en paramètre par la création du panneau
    String action = MessagesBuilder.getMessagesProperty(property);
    String message = MessagesBuilder.getMessagesProperty("diagram.btn.exception", new String[]{action});
    //TODO-1 A voir si window doit être passé en paramètre par la création du panneau
    Window window = MVCCDManager.instance().getMvccdWindow();
    ViewLogsManager.catchException(exception, window, message);
  }

}