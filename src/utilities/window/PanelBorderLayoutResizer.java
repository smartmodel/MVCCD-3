package utilities.window;

import preferences.Preferences;
import utilities.window.services.ComponentService;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
    Le redimensionnement est possible si:
    1. Start: La souris quitte un panneau
    2. Dimensionnement possible si la souris entre dans un panneau adjacent avec un déplacemt restreint
    3. Annulation : La souris quitte la zone de détection de panneau adjacent de plus de x pixels
*/


public class PanelBorderLayoutResizer {

    private  ArrayList<PanelBorderLayout> panels = new ArrayList<PanelBorderLayout>() ;
    private PanelBorderLayoutForResize subPanelExit = new PanelBorderLayoutForResize() ;
    private PanelBorderLayoutForResize subPanelEnter = new PanelBorderLayoutForResize() ;
    private Integer cursorTypeDefault = Cursor.DEFAULT_CURSOR;
    private Boolean cursorResizingVertical = false;
    private Boolean cursorResizingHorizontal = false;
    private Boolean draggedDetected = false;
    private Boolean draggedInProgress = false;

    public PanelBorderLayoutResizer() {
     }

    /*
        La souris quitte un panneau
        Start du processus de dimensionnement en mémorisant le panneau quitté
     */
    public void mouseExitSubPanel(MouseEvent mouseEvent){
        if(!draggedInProgress) {
            if (mouseEvent.getComponent() == subPanelEnter.getPanelForResize()) {
                // La souris quitte le panneau qui devait être rétréci
                reinitialisation();
            } else {
                // la souris quitte un panneau autre que celui à rétrécir
                // Ce panneau devient le nouveau panneau à rétrécir
                subPanelExit.setxOnScreen(mouseEvent.getXOnScreen());
                subPanelEnter.setxOnScreen(null);
                subPanelExit.setyOnScreen(mouseEvent.getYOnScreen());
                subPanelEnter.setyOnScreen(null);
                subPanelExit.setPanelForResize((PanelBorderLayout) mouseEvent.getComponent());
            }
        }
    }


    /*
        Evaluation de dimensionnement possible
        Suite du processus de dimensionnement en mémorisant le panneau entré
     */

    public void mouseEnterSubPanel(MouseEvent mouseEvent){
         if ((subPanelExit.getPanelForResize() != null) && (!draggedDetected) &&
                 subPanelExit.getPanelForResize().isResizable()){
            // 1ère condition de démarrage du processus de dimensionnement
            // Entré dans un panneau autorisé à rétrécir
            // La souris n'est pas pressée

            if ((((PanelBorderLayout) mouseEvent.getComponent()) != subPanelExit.getPanelForResize()) &&
                    (((PanelBorderLayout) mouseEvent.getComponent()).isResizable())){
                // 2ème condition de démarrage du processus de dimensionnement
                // Le panneau d'entré est différent du panneau de sortie et autorise le redimensionnement
                // Le cas se produit lorsque la souris quitte la fenêtre (JFrame) et y revient
                subPanelEnter.setPanelForResize( (PanelBorderLayout) mouseEvent.getComponent());
                subPanelEnter.setxOnScreen(mouseEvent.getXOnScreen());
                subPanelEnter.setyOnScreen(mouseEvent.getYOnScreen());
                int yDelta = Math.abs(subPanelEnter.getyOnScreen() - subPanelExit.getyOnScreen() );
                int xDelta = Math.abs(subPanelEnter.getxOnScreen() - subPanelExit.getxOnScreen() );
               if ((yDelta <= Preferences.JPANEL_VGAP)) {
                    // 3ème condition de démarrage du processus de dimensionnement
                    // Déplacement vertical restreint entre les 2 panneaux
                    if (resizeVerticalCoherent()) {
                        // 4ème condition de démarrage du processus de dimensionnement
                        // Un redimensionnement vertical est cohérent entre les 2 panneaux
                         Cursor cursor = new Cursor(Cursor.N_RESIZE_CURSOR);
                        subPanelEnter.getPanelForResize().setCursor(cursor);
                       cursorResizingVertical = true;
                    }
                }
                if ((xDelta <= Preferences.JPANEL_HGAP)) {
                    // 3ème condition de démarrage du processus de dimensionnement
                    // Déplacement horizontal restreint entre les 2 panneaux
                    if (resizeHorizontalCoherent()) {
                        // 4ème condition de démarrage du processus de dimensionnement
                        // Un redimensionnement vertical est cohérent entre les 2 panneaux
                        Cursor cursor = new Cursor(Cursor.E_RESIZE_CURSOR);
                        subPanelEnter.getPanelForResize().setCursor(cursor);
                        cursorResizingHorizontal = true;
                    }
                }
            }
        }
    }

    private boolean resizeVerticalCoherent() {
        String subPanelExitPosition = subPanelExit.getPanelForResize().getBorderLayoutPosition();
        String subPanelEnterPosition = subPanelEnter.getPanelForResize().getBorderLayoutPosition();
        boolean exitVerticalDirect =  subPanelExitPosition.equals(BorderLayout.NORTH) ||
                                    subPanelExitPosition.equals(BorderLayout.CENTER) ||
                                    subPanelExitPosition.equals(BorderLayout.SOUTH);
        boolean enterVerticalDirect =  subPanelEnterPosition.equals(BorderLayout.NORTH) ||
                                    subPanelEnterPosition.equals(BorderLayout.CENTER) ||
                                    subPanelEnterPosition.equals(BorderLayout.SOUTH);
        boolean exitVerticalIndirect =  subPanelExitPosition.equals(BorderLayout.EAST) ||
                                        subPanelExitPosition.equals(BorderLayout.WEST) ||
                                        subPanelExitPosition.equals(BorderLayout.NORTH) ||
                                        subPanelExitPosition.equals(BorderLayout.SOUTH);
        boolean enterVerticalIndirect =  subPanelEnterPosition.equals(BorderLayout.EAST) ||
                                        subPanelEnterPosition.equals(BorderLayout.WEST) ||
                                        subPanelEnterPosition.equals(BorderLayout.NORTH) ||
                                        subPanelEnterPosition.equals(BorderLayout.SOUTH);

        return (exitVerticalDirect && enterVerticalDirect) ||
                (exitVerticalIndirect && enterVerticalIndirect) ;
    }

    private boolean resizeHorizontalCoherent() {
        String subPanelExitPosition = subPanelExit.getPanelForResize().getBorderLayoutPosition();
        String subPanelEnterPosition = subPanelEnter.getPanelForResize().getBorderLayoutPosition();
        boolean exitHorizontal =  subPanelExitPosition.equals(BorderLayout.EAST) ||
                                    subPanelExitPosition.equals(BorderLayout.CENTER) ||
                                    subPanelExitPosition.equals(BorderLayout.WEST);
        boolean enterHorizontal =  subPanelEnterPosition.equals(BorderLayout.EAST) ||
                                    subPanelEnterPosition.equals(BorderLayout.CENTER) ||
                                    subPanelEnterPosition.equals(BorderLayout.WEST);
        return exitHorizontal && enterHorizontal;
    }

    /*
        Annulation du processus de dimensionnement si la souris s'éloigne trop de la zone de jointure des 2 panneaux
        Si le déplacement n'est pas en cours
    */
    public void mouseMoveSubPanel(MouseEvent mouseEvent){
        if (!draggedInProgress){
            if (cursorResizingVertical){
                // Processus de redimensionnement vertical actif
                int yOnScreenCurrent = mouseEvent.getYOnScreen();
                int yDelta = Math.abs(subPanelEnter.getyOnScreen() - yOnScreenCurrent);
                if (yDelta > Preferences.JPANEL_VGAP) {
                    // Curseur trop éloigné de la zone de jointure des 2 panneaux
                    reinitialisation();
                }
            }
            if (cursorResizingHorizontal) {
                // Processus de redimensionnement horizontal actif
                int xOnScreenCurrent = mouseEvent.getXOnScreen();
                int xDelta = Math.abs(subPanelEnter.getxOnScreen() - xOnScreenCurrent);
                if (xDelta > Preferences.JPANEL_HGAP) {
                    // Curseur trop éloigné de la zone de jointure des 2 panneaux
                    reinitialisation();
                }
            }
        } else {

        }
    }

    private void reinitialisation() {
        Cursor cursor = new Cursor(cursorTypeDefault);
        subPanelEnter.getPanelForResize().setCursor(cursor);
        subPanelExit.getPanelForResize().setCursor(cursor);  // A voir
        subPanelEnter.reinitialize();
        subPanelExit.reinitialize();
        cursorResizingVertical = false;
        cursorResizingHorizontal = false;
    }

    public void mouseDraggedSubPanel(MouseEvent mouseEvent) {
        draggedDetected = true;
        if (cursorResizingVertical || cursorResizingHorizontal){
            draggedInProgress = true;
        }
    }

    public void mouseReleaseSubPanel(MouseEvent mouseEvent) {
        if (draggedInProgress ) {
            // déplacement en sens inverse
            if (ComponentService.mouseIn(subPanelExit.getPanelForResize(), mouseEvent)) {
                // Permuation des panneaux
                PanelBorderLayoutForResize subPanelTampon = subPanelExit;
                subPanelExit = subPanelEnter;
                subPanelEnter = subPanelTampon;
            }
            //if ( ComponentService.mouseEventInComponentAndContentDeep(subPanelEnter, mouseEvent) ){
            // En cas de release mouseEvent.getComponent() retourne le composant qui avait reçu l'événement de drag...
            if (ComponentService.mouseIn(subPanelEnter.getPanelForResize(), mouseEvent)){
                if (subPanelExit.getPanelForResize().isResizable() &&
                        subPanelEnter.getPanelForResize().isResizable() ) {
                    panelsResize(mouseEvent);
                }
            }

            reinitialisation();
            draggedInProgress = false;
        }
        draggedDetected = false;
    }

    private void panelsResize(MouseEvent mouseEvent) {
        //sizeMinimizeContentPanels();
        if (cursorResizingVertical){
            panelsResizeVertical(mouseEvent);
        }
        if (cursorResizingHorizontal){
            panelsResizeHorizontal(mouseEvent);
        }
        resizerContentPanels();
    }

    private void panelsResizeHorizontal(MouseEvent mouseEvent) {
        int xOnScreenCurrent = mouseEvent.getXOnScreen();
        int xDeltaMouse =  Math.abs(xOnScreenCurrent - subPanelEnter.getxOnScreen());

        // Une taille minimale du panneau à réduire doit être respectée
        xDeltaMouse = subPanelEnter.getPanelForResize().correctedMinimaleWidth(xDeltaMouse);

        subPanelExit.getPanelForResize().increaseWidth(xDeltaMouse);
        subPanelEnter.getPanelForResize().increaseWidth(- xDeltaMouse);

        if (moveDirectionHorizontal().equals(BorderLayout.EAST)){
            subPanelEnter.getPanelForResize().increaseLocationX(xDeltaMouse);
        }
        if (moveDirectionHorizontal().equals(BorderLayout.WEST)){
            subPanelExit.getPanelForResize().increaseLocationX(- xDeltaMouse);
        }

    }

    private String moveDirectionHorizontal() {
        String subPanelExitPosition = subPanelExit.getPanelForResize().getBorderLayoutPosition();
        String subPanelEnterPosition = subPanelEnter.getPanelForResize().getBorderLayoutPosition();

        if ((subPanelExitPosition.equals(BorderLayout.WEST)) &&
                (subPanelEnterPosition.equals(BorderLayout.CENTER))) {
            return BorderLayout.EAST;
        }
        if ((subPanelExitPosition.equals(BorderLayout.CENTER)) &&
                (subPanelEnterPosition.equals(BorderLayout.EAST))) {
            return BorderLayout.EAST;
        }
        if ((subPanelExitPosition.equals(BorderLayout.EAST)) &&
                (subPanelEnterPosition.equals(BorderLayout.CENTER))) {
            return BorderLayout.WEST;
        }
        if ((subPanelExitPosition.equals(BorderLayout.CENTER)) &&
                (subPanelEnterPosition.equals(BorderLayout.WEST))) {
            return BorderLayout.WEST;
        }
        return null;
    }



    private void panelsResizeVertical(MouseEvent mouseEvent) {
        int yOnScreenCurrent = mouseEvent.getYOnScreen();
        int yDeltaMouse =  Math.abs(yOnScreenCurrent - subPanelEnter.getyOnScreen());

        // Une taille minimale du panneau à réduire doit être respectée
        yDeltaMouse = subPanelEnter.getPanelForResize().correctedMinimaleHeight(yDeltaMouse);

        if (moveDirectionVertical().equals(BorderLayout.SOUTH)) {
            if (subPanelExit.getPanelForResize().getBorderLayoutPosition().equals(BorderLayout.NORTH)) {
                subPanelExit.getPanelForResize().increaseHeight(yDeltaMouse);

                PanelBorderLayout panelEast = getPanelByBorderLayoutPosition(BorderLayout.EAST);
                if (panelEast != null) {
                    panelEast.increaseHeight(-yDeltaMouse);
                    panelEast.increaseLocationY(yDeltaMouse);
                }
                PanelBorderLayout panelCenter = getPanelByBorderLayoutPosition(BorderLayout.CENTER);
                if (panelCenter != null) {
                    panelCenter.increaseHeight(-yDeltaMouse);
                    panelCenter.increaseLocationY(yDeltaMouse);
                }
                PanelBorderLayout panelWest = getPanelByBorderLayoutPosition(BorderLayout.WEST);
                if (panelWest != null) {
                    panelWest.increaseHeight(-yDeltaMouse);
                    panelWest.increaseLocationY(yDeltaMouse);
                }
            }
            if (subPanelEnter.getPanelForResize().getBorderLayoutPosition().equals(BorderLayout.SOUTH)) {
                subPanelEnter.getPanelForResize().increaseHeight(-yDeltaMouse);
                subPanelEnter.getPanelForResize().increaseLocationY( yDeltaMouse);

                PanelBorderLayout panelEast = getPanelByBorderLayoutPosition(BorderLayout.EAST);
                if (panelEast != null) {
                    panelEast.increaseHeight(yDeltaMouse);
                    //panelEast.increaseLocationY(yDeltaMouse);
                }
                PanelBorderLayout panelCenter = getPanelByBorderLayoutPosition(BorderLayout.CENTER);
                if (panelCenter != null) {
                    panelCenter.increaseHeight(yDeltaMouse);
                    //panelCenter.increaseLocationY(yDeltaMouse);
                }
                PanelBorderLayout panelWest = getPanelByBorderLayoutPosition(BorderLayout.WEST);
                if (panelWest != null) {
                    panelWest.increaseHeight(yDeltaMouse);
                    //panelWest.increaseLocationY(yDeltaMouse);
                }

            }
        }
        if (moveDirectionVertical().equals(BorderLayout.NORTH)) {
                if (subPanelExit.getPanelForResize().getBorderLayoutPosition().equals(BorderLayout.SOUTH)) {
                    subPanelExit.getPanelForResize().increaseHeight(yDeltaMouse);
                    subPanelExit.getPanelForResize().increaseLocationY(- yDeltaMouse);
                    PanelBorderLayout panelEast = getPanelByBorderLayoutPosition(BorderLayout.EAST);
                    if (panelEast != null) {
                        panelEast.increaseHeight(-yDeltaMouse);
                        //panelEast.increaseLocationY(- yDeltaMouse);
                    }
                    PanelBorderLayout panelCenter = getPanelByBorderLayoutPosition(BorderLayout.CENTER);
                    if (panelCenter != null) {
                        panelCenter.increaseHeight(- yDeltaMouse);
                        //panelCenter.increaseLocationY(- yDeltaMouse);
                    }
                    PanelBorderLayout panelWest = getPanelByBorderLayoutPosition(BorderLayout.WEST);
                    if (panelWest != null) {
                        panelWest.increaseHeight(-yDeltaMouse);
                        //panelWest.increaseLocationY(- yDeltaMouse);
                    }
                }
                if (subPanelEnter.getPanelForResize().getBorderLayoutPosition().equals(BorderLayout.NORTH)) {
                    subPanelEnter.getPanelForResize().increaseHeight(- yDeltaMouse);
                    PanelBorderLayout panelEast = getPanelByBorderLayoutPosition(BorderLayout.EAST);
                    if (panelEast != null) {
                        panelEast.increaseHeight(yDeltaMouse);
                        panelEast.increaseLocationY(- yDeltaMouse);
                    }
                    PanelBorderLayout panelCenter = getPanelByBorderLayoutPosition(BorderLayout.CENTER);
                    if (panelCenter != null) {
                        panelCenter.increaseHeight(yDeltaMouse);
                        panelCenter.increaseLocationY(- yDeltaMouse);
                    }
                    PanelBorderLayout panelWest = getPanelByBorderLayoutPosition(BorderLayout.WEST);
                    if (panelWest != null) {
                        panelWest.increaseHeight(yDeltaMouse);
                        panelWest.increaseLocationY(- yDeltaMouse);
                    }

                }

        }

    }

    private String moveDirectionVertical() {
        String subPanelExitPosition = subPanelExit.getPanelForResize().getBorderLayoutPosition();
        String subPanelEnterPosition = subPanelEnter.getPanelForResize().getBorderLayoutPosition();

        if (subPanelExitPosition.equals(BorderLayout.NORTH)) {
            return BorderLayout.SOUTH;
        }
        if (subPanelEnterPosition.equals(BorderLayout.SOUTH)) {
            return BorderLayout.SOUTH;
        }

        if (subPanelExitPosition.equals(BorderLayout.SOUTH)) {
            return BorderLayout.NORTH;
        }
        if (subPanelEnterPosition.equals(BorderLayout.NORTH)) {
            return BorderLayout.NORTH;
        }
        return null;
    }

    private PanelBorderLayout getPanelByBorderLayoutPosition(String pos){
       for (PanelBorderLayout panel : panels){
            if (panel.getBorderLayoutPosition() != null){
                if (panel.getBorderLayoutPosition().equals(pos)) {
                    return panel;
                }
            }
        }
        return null;
    }

    public ArrayList<PanelBorderLayout> getPanels() {
        return panels;
    }

    public void resizerContentPanels(){
        for (PanelBorderLayout panel : panels){
            panel.resizeContent();
        }
    }

}
