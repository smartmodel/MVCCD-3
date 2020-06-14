package repository.editingTreat.diagram;

import diagram.ClosePalette;
import diagram.CloseTitlePanel;
import diagram.mcd.MCDDiagram;
import diagram.mcd.MCDTitlePanel;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import main.MVCCDWindow;
import main.window.diagram.WinDiagram;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import repository.editingTreat.EditingTreat;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;

import javax.swing.*;
import java.awt.*;

public abstract class DiagramEditingTreat extends EditingTreat {


    public void treatClose(Window owner) {


        MVCCDWindow mvccdWindow = (MVCCDWindow) owner;
        WinDiagram winDiagram = mvccdWindow.getDiagram();
        JPanel panelTitle = winDiagram.getContent().getPanelTitle();
        JPanel panelPalette= winDiagram.getContent().getPanelPalette();
        CloseTitlePanel closeTitlePanel = new CloseTitlePanel(panelTitle);
        closeTitlePanel.getContent();
        ClosePalette closePalette = new ClosePalette((panelPalette));
        closePalette.getContent();

        //TODO-1  A reprendre (resize pour permettre le réaffichage des JPanel chargés dynamiquement
        //winDiagram.resizeContent();
        mvccdWindow.setSize(mvccdWindow.getWidth()-1, mvccdWindow.getHeight()-1);
        mvccdWindow.setSize(mvccdWindow.getWidth()+1, mvccdWindow.getHeight()+1);

    }

    public boolean treatDelete (Window owner, MVCCDElement element) {
        boolean deleted = super.treatDelete (owner, element);
        if (deleted ){
            //TODO-1 A véifier si le diagramme est effectivement affiché
            treatClose(owner);
        }
        return deleted;
    }

}

