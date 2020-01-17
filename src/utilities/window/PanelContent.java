package utilities.window;

import preferences.Preferences;

import javax.swing.*;
import java.awt.*;

public abstract class PanelContent extends JPanel {
    private JScrollPane scroll;
    private JPanel panel;
    PanelBorderLayout panelBL;
    private boolean scrollInComponent= false;

    public PanelContent(PanelBorderLayout panelBL) {
        this.panelBL = panelBL;
    }


    public void setContent(JPanel panel) {
        scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
        panelBL.add(this);
    }

    public void setContent(JPanel panel, boolean scrollable ){
       if (scrollable){
           setContent(panel);
       } else {
           this.panel = panel;
           this.add(panel);
           panelBL.add(this);
       }
    }


    public void setContent(JTree tree) {
        setContentInJPanel(tree);
    }

    public void setContent(JTextArea text) {
        setContentInJPanel(text);
    }



    public void setContentInJPanel(Component component){
        scrollInComponent = true;
        scroll = new JScrollPane(this);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(component);
        panelBL.add(scroll);
    }


    public Dimension resizeContent() {
        int widthBL = panelBL.getSize().width;
        int heightBL = panelBL.getSize().height;

        int width = widthBL - 2 * Preferences.JPANEL_HGAP;
        int height = heightBL - 2 * Preferences.JPANEL_HGAP;

        Dimension dimension = new Dimension(width, height);

        this.setSize(dimension);

        if (scroll != null) {
            scroll.setSize(dimension);
            scroll.setPreferredSize(dimension);
        }

        if (panel != null){
            panel.setSize(dimension);
            panel.setPreferredSize(dimension);
        }
        if (!scrollInComponent) {
            this.setPreferredSize(dimension);
        }

        return dimension;
    }

    /*
    public void resizeContent() {
        int width = panelBL.getSize().width;
        int height = panelBL.getSize().height;


            this.setSize(new Dimension(width - 2 * Preferences.JPANEL_HGAP,
                    height - 2 * Preferences.JPANEL_HGAP));

            if (scroll != null) {
                scroll.setSize(new Dimension(width - 2 * Preferences.JPANEL_HGAP,
                        height - 2 * Preferences.JPANEL_HGAP));
            }

            if (!scrollInComponent) {
                if (scroll != null) {
                    scroll.setPreferredSize(new Dimension(width - 2 * Preferences.JPANEL_HGAP,
                            height - 2 * Preferences.JPANEL_HGAP));
                }
                this.setPreferredSize(new Dimension(width - 2 * Preferences.JPANEL_HGAP,
                        height - 2 * Preferences.JPANEL_HGAP));
            }


    }

*/

    public PanelBorderLayout getPanelBL() {
        return panelBL;
    }



}
