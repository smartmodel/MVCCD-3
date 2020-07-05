package repository.editingTreat.diagram;

import diagram.ClosePalette;
import diagram.mcd.MCDDiagram;
import diagram.mcd.MCDTitlePanel;
import diagram.mcd.MCDPalette;
import diagram.mcd.MCDZoneDessin;
import diagram.mcd.testDrawPanel.DiagramList;
import diagram.mcd.testDrawPanel.MCDEntityDraw;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import main.MVCCDWindow;
import main.window.diagram.WinDiagram;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MCDDiagramEditingTreat extends DiagramEditingTreat {

    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {

        // Création d'un objet transitoire
        MCDDiagram newDiagram = MVCCDElementFactory.instance().createMCDDiagram(null);

        //MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
        WinDiagram  winDiagram = mvccdWindow.getDiagram();
        JPanel panelTitle = winDiagram.getContent().getPanelTitle();
        MCDTitlePanel mcdTitlePanel = new MCDTitlePanel(parent,
                panelTitle,
                DialogEditor.NEW,
                newDiagram);
        mcdTitlePanel.getContent();
        JPanel panelPalette = winDiagram.getContent().getPanelPalette();
        MCDPalette mcdPalette = new MCDPalette(parent,
                panelPalette,
                DialogEditor.NEW,
                newDiagram);
        mcdPalette.getContent();
        JPanel panelZoneDessin = winDiagram.getContent().getPanelDraw();
        MCDZoneDessin mcdZoneDessin = new MCDZoneDessin(parent,
                panelZoneDessin,
                DialogEditor.NEW,
                newDiagram);
        mcdZoneDessin.getContentNew();




        //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
        //winDiagram.resizeContent();
        mvccdWindow.setSize(mvccdWindow.getWidth()-1, mvccdWindow.getHeight()-1);
        mvccdWindow.setSize(mvccdWindow.getWidth()+1, mvccdWindow.getHeight()+1);

        return newDiagram;
    }

    public boolean treatUpdate(Window owner, MVCCDElement element) {
        MVCCDElement parentBefore = element.getParent();

        MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
        WinDiagram  winDiagram = mvccdWindow.getDiagram();
        JPanel panelTitle = winDiagram.getContent().getPanelTitle();
        MCDTitlePanel mcdTitlePanel = new MCDTitlePanel(parentBefore,
                panelTitle,
                DialogEditor.UPDATE,
                (MCDDiagram) element);
        mcdTitlePanel.getContent();
        JPanel panelPalette = winDiagram.getContent().getPanelPalette();
        MCDPalette mcdPalette = new MCDPalette(parentBefore,
                panelPalette,
                DialogEditor.UPDATE,
                (MCDDiagram) element);
        mcdPalette.getContent();
        JPanel panelZoneDessin = winDiagram.getContent().getPanelDraw();
        MCDZoneDessin mcdZoneDessin = new MCDZoneDessin(parentBefore,
                panelZoneDessin,
                DialogEditor.UPDATE,
                (MCDDiagram) element);
        mcdZoneDessin.getContentUpdate(element.getMCDEntityDraws());

        //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
        //winDiagram.resizeContent();
        mvccdWindow.setSize(mvccdWindow.getWidth()-1, mvccdWindow.getHeight()-1);
        mvccdWindow.setSize(mvccdWindow.getWidth()+1, mvccdWindow.getHeight()+1);


        MVCCDElement parentAfter = element.getParent();
        if (parentBefore != parentAfter) {
            MVCCDManager.instance().changeParentMVCCDElementInRepository(element, parentBefore);
        }

        //TODO-1 A voir pour récupérer le changement effectif
        return true;

    }


    public DialogEditor treatRead(Window owner, MVCCDElement element) {
        MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
        WinDiagram  winDiagram = mvccdWindow.getDiagram();
        JPanel panelTitle = winDiagram.getContent().getPanelTitle();
        MCDTitlePanel mcdTitlePanel = new MCDTitlePanel(element.getParent(),
                panelTitle,
                DialogEditor.READ,
                (MCDDiagram) element);
        mcdTitlePanel.getContent();
        JPanel panelZoneDessin = winDiagram.getContent().getPanelDraw();
        MCDZoneDessin mcdZoneDessin = new MCDZoneDessin(element.getParent(),
                panelZoneDessin,
                DialogEditor.READ,
                (MCDDiagram) element);
        mcdZoneDessin.getContentUpdate(element.getMCDEntityDraws());;


        //Fermer la palette afin que l'utilisateur ne puisse pas modifier un diagramme en lecture seule
        JPanel panelPalette= winDiagram.getContent().getPanelPalette();
        ClosePalette closePalette = new ClosePalette((panelPalette));
        closePalette.getContent();

        //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
        //winDiagram.resizeContent();
        mvccdWindow.setSize(mvccdWindow.getWidth()-1, mvccdWindow.getHeight()-1);
        mvccdWindow.setSize(mvccdWindow.getWidth()+1, mvccdWindow.getHeight()+1);


        //TODO-1 A voir le choix du retour dans EditingTreat
        return null;
    }

    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
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
