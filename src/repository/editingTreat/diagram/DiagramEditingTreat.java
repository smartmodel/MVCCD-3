package repository.editingTreat.diagram;

import diagram.CloseTitlePanel;
import main.MVCCDElement;
import main.MVCCDWindow;
import main.window.diagram.WinDiagram;
import repository.editingTreat.EditingTreat;

import javax.swing.*;
import java.awt.*;

/**
 * Tous les diagrammeurs concrets doivent être des descendants de cette classe.
 */
public abstract class DiagramEditingTreat extends EditingTreat {


    /**
     * Ferme le diagrammeur.
     * @param owner
     */
    public void treatClose(Window owner) {


        MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
        WinDiagram winDiagram = mvccdWindow.getDiagram();
        JPanel panelTitle = winDiagram.getPanelTitle();
        CloseTitlePanel closeTitlePanel = new CloseTitlePanel(panelTitle);
        closeTitlePanel.getContent();

        //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
        //winDiagram.resizeContent();
        mvccdWindow.setSize(mvccdWindow.getWidth()-1, mvccdWindow.getHeight()-1);
        mvccdWindow.setSize(mvccdWindow.getWidth()+1, mvccdWindow.getHeight()+1);

    }

    /**
     * Supprime un diagramme.
     * @param owner
     * @param element
     * @return
     */
    public boolean treatDelete (Window owner, MVCCDElement element) {
        boolean deleted = super.treatDelete (owner, element);
        if (deleted ){
            //TODO-1 A véifier si le diagramme est effectivement affiché
            treatClose(owner);
        }
        return deleted;
    }

}

