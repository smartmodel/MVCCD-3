package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mcd.MCDContRelations;
import mcd.MCDEntity;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.mcd.relation.association.AssociationEditor;
import window.editor.mcd.relation.association.AssociationInputContent;

import java.awt.*;


public class MCDAssociationEditingTreat extends MCDRelationEditingTreat {

    /**
     * Cette méthode spécifique travaille en 2 temps:
     *  1. Elle appelle la méthode générique pour créer une nouvelle association.
     *  2. Elle ajoute la relation à la représentation du référentiel aux 2 entités participantes.
     * Les 2 extrémités d'association sont visibles dans l'arbre de représentation du référentiel de l'interface
     * utilisateur. Dans le référentiel, on peut voir ces extrémités d'association, (1) au sein des 2 entités reliés
     * par l'association (sous le répertoire "Extrémités de relations", ainsi que (2) sous le répertoire "Relations"
     * qui se trouve sous "MCD".
     * <img src="doc-files/UI_ViewingThe2EndAssociationInRepository.jpg" alt="Visualisation des 2 extrémités d'association dans l'arbre du référentiel">
     */
    public  MCDAssociation treatNew(Window owner,
                                    MVCCDElement parent) {

        MCDAssociation mcdAssociationNew = (MCDAssociation) super.treatNew( owner, parent);

        if (mcdAssociationNew != null) {
           addRelEndsInRepository(mcdAssociationNew);
        }
        return mcdAssociationNew;
    }

    /**
     * Cette méthode est utilisée pour la création d'une association depuis le diagrammeur.
     * Dans l'interface utilisateur, sur le formulaire de création d'une association appelé depuis le diagrammeur, des
     * listes déroulantes permettent de sélectionner l'entité de départ, l'entité d'arrivée, la nature de l'association
     * ainsi que le conteneur dans lequel l'association se trouve (par exemple "MCD.Relations").
     * <img src="doc-files/UI_NewAssociationFormFromDrawingArea.jpg" alt="Visualisation des 2 extrémités d'association dans l'arbre du référentiel">
     * @param entityFrom l'entité de départ de l'association
     * @param entityTo l'entité d'arrivée de l'association
     * @param nature la nature de l'association (par ex: Non identifiante)
     */
    public  MCDAssociation treatNew(Window owner,
                                    MCDContRelations parent,
                                    MCDEntity entityFrom,
                                    MCDEntity entityTo,
                                    MCDAssociationNature nature,
                                    boolean initFrozen) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW);

        //Contenu de l'éditeur d'association
        AssociationInputContent content = (AssociationInputContent) fen.getInput().getInputContent();

        //Initialisation de l'entité de départ
        if (entityFrom  != null){
            SComboBoxService.selectByText(content.getFieldFromEntity(), entityFrom.getNamePath(content.getModePathName()));
            if (initFrozen)  {
                content.getFieldFromEntity().setReadOnly(true);
            }
        }

        //Initialisation de l'entité d'arrivée
        if (entityTo  != null) {
            SComboBoxService.selectByText(content.getFieldToEntity(), entityTo.getNamePath(content.getModePathName()));
            if (initFrozen)  {
                content.getFieldToEntity().setReadOnly(true);
            }
        }

        //Initialisation de la nature de l'association
        if (nature  != null) {
            SComboBoxService.selectByText(content.getFieldNature(), nature.getText());
            if (initFrozen)  {
                content.getFieldNature().setReadOnly(true);
            }
        }

        fen.setVisible(true);
        MCDAssociation mcdAssociationNew = (MCDAssociation)  fen.getMvccdElementNew();

        if (mcdAssociationNew != null) {
            addRelEndsInRepository(mcdAssociationNew);
        }
        return mcdAssociationNew;
    }


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new AssociationInputContent(element);
    }

    /**
     * Fournit l'éditeur d'association à utiliser à la classe ancêtre EditingTreat.
     * Il s'agit de l'éditeur de création d'une nouvelle association qui est affichée à l'utilisateur lorsqu'il crée
     * nouvelle association. Cet éditeur comprend le formulaire permettant de renseigner les informations sur la
     * nouvelle association.
     * <img src="doc-files/UI_NewAssociationForm.jpg" alt="Formulaire spécifique de création d'une association">
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MVCCDElement parent,
                                           MVCCDElement element,
                                           String mode) {
        return new AssociationEditor(owner, (MCDContRelations) parent, (MCDAssociation) element,
                mode, new MCDAssociationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.association";
    }


}
