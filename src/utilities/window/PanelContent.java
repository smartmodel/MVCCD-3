package utilities.window;

import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import java.awt.*;

/**
 * Extension de JPanel.
 */
public abstract class PanelContent extends JPanel {
    private JScrollPane scroll; // Barre de défilement
    private JPanel panel;
    PanelBorderLayout panelBL;  // JPanel constitutif d'un jeu de panneaux gérés par un BorderLayout
    private boolean scrollInComponent= false; // Indicateur de prise en charge des barres de défilement

    public PanelContent(PanelBorderLayout panelBL) {
        this.panelBL = panelBL;
    }


    /**
     * Permet d'ajouter un composant (classe Component de awt) dans un JPanel. Si nécessaire, des barres de défilement sont ajoutées.
     */
    public void addContent(JPanel panel) {
        scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
        panelBL.add(this);
    }


    public void addContent(JPanel panel, boolean scrollable ){
       if (scrollable){
           addContent(panel);
       } else {
           this.panel = panel;
           this.add(panel);
           panelBL.add(this);
       }
       colorDebug();
    }


    /**
     * Utilisé pour la fenêtre de gauche de l'écran d'accueil.
     * @param tree
     */
    public void addContent(JTree tree) {
        addContentBase(tree);
    }

    public void addContent(JTextArea text) {
        addContentBase(text);
    }



    private void addContentBase(Component component){
        scrollInComponent = true;
        scroll = new JScrollPane(this);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(component);
        panelBL.add(scroll);
        colorDebug();
    }


    /**
     * Ajuste la taille du panneau en cas de redimensionnement de la fenêtre.
     * @return
     */
    public Dimension resizeContent() {

        int widthBL = panelBL.getSize().width;
        int heightBL = panelBL.getSize().height;

        int width = widthBL - 2 * Preferences.JPANEL_HGAP;
        int height = heightBL - 2 * Preferences.JPANEL_VGAP;

        Dimension dimension = new Dimension(width, height);

        this.setSize(dimension);

        if (scroll != null) {
            scroll.setSize(dimension);
            scroll.setPreferredSize(dimension);
         }

        if (panel != null){
            panel.setSize(dimension);
            panel.setPreferredSize(dimension);
            //panel.setLocation(Preferences.JPANEL_HGAP, Preferences.JPANEL_VGAP);
            panel.setLocation(0,0);
        }
        if (!scrollInComponent) {
            this.setPreferredSize(dimension);
            if (scroll != null){
                scroll.setLocation(0,0);
            }
        }

        return dimension;
    }

    public PanelBorderLayout getPanelBL() {
        return panelBL;
    }

    private void colorDebug(){
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            if (PreferencesManager.instance().preferences().isDEBUG_BACKGROUND_PANEL()) {
                this.setBackground(Color.CYAN);
                if (scroll != null) {
                    scroll.setBackground(Color.orange);
                }
            }
        }
    }


}
