package utilities.window;

public class PanelBorderLayoutForResize {
    //Panneau à redimensionner
    private PanelBorderLayout panelForResize = null ;
    // Position X à l'écran du panneau
    private Integer xOnScreen = null ;
    // Position Y à l'écran du panneau
    private Integer yOnScreen = null ;

    public PanelBorderLayoutForResize() {
    }

    public PanelBorderLayoutForResize(PanelBorderLayout panelForResize) {
        this.panelForResize = panelForResize;
    }

    public PanelBorderLayoutForResize(PanelBorderLayout panelForResize, Integer xOnScreen, Integer yOnScreen) {
        this.xOnScreen = xOnScreen;
        this.yOnScreen = yOnScreen;
        this.panelForResize = panelForResize;
    }

    public PanelBorderLayout getPanelForResize() {
        return panelForResize;
    }

    public void setPanelForResize(PanelBorderLayout panelForResize) {
        this.panelForResize = panelForResize;
    }

    public Integer getxOnScreen() {
        return xOnScreen;
    }

    public void setxOnScreen(Integer xOnScreen) {
        this.xOnScreen = xOnScreen;
    }

    public Integer getyOnScreen() {
        return yOnScreen;
    }

    public void setyOnScreen(Integer yOnScreen) {
        this.yOnScreen = yOnScreen;
    }

    public void reinitialize(){
        this.xOnScreen = null;
        this.yOnScreen = null;
        this.panelForResize = null;
    }


}
