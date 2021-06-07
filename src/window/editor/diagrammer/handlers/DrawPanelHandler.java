package window.editor.diagrammer.handlers;

import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.DrawPanelComponent;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.elements.ClassShape;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class DrawPanelHandler {

    private DrawPanel drawPanel;

    public DrawPanelHandler(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    public void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(243,243,243));
        int width = this.drawPanel.getWidth();
        int height = this.drawPanel.getHeight();
        for (int i = 0; i < width; i += this.drawPanel.getGridSize()) {
            graphics2D.drawLine(i, 0, i, height);
        }
        for (int i = 0; i < height; i += this.drawPanel.getGridSize()) {
            graphics2D.drawLine(0, i, width, i);
        }
    }

    public Dimension getViewableDiagramSize() {
        return this.drawPanel.getVisibleRect().getSize();
    }

    public double getZoomFactor() {
        return  this.drawPanel.getGridSize() /  DiagrammerConstants.DEFAULT_GRID_SIZE;
    }

  /**
     * Zoom les éléments du modèle.
     * @param fromFactor facteur de zoom initial
     * @param toFactor facteur de zoom final
     */
    public void zoomElements(int fromFactor, int toFactor){
        if(this.zoomAllowed(toFactor)){
            for (IShape element : this.drawPanel.getElements()){
                element.zoom(fromFactor, toFactor);
            }
        }
    }

     public void moveOrigin(int differenceX, int differenceY) {
        this.drawPanel.getOrigin().translate(GridUtils.alignToGrid(differenceX, this.drawPanel.getGridSize()), GridUtils.alignToGrid(differenceY, this.drawPanel.getGridSize()));
    }

    public void zoomOrigin(int oldGridSize, int newGridSize) {
        this.drawPanel.getOrigin().setLocation(this.drawPanel.getOrigin().x * newGridSize / oldGridSize, this.drawPanel.getOrigin().y * newGridSize / oldGridSize);
    }

    /**
     * Vérifie que le zoom est autorisé. Exemple : Si le zoom maximal autorisé est de 25 et qu'un facteur de 26 est passé en paramètre, le zoom n'est pas autorisé.
     * @param factor
     * @return Si le zoom est autorisé
     */
    private boolean zoomAllowed(int factor){
        return factor >= DiagrammerConstants.MINIMUM_ALLOWED_ZOOM && factor <= DiagrammerConstants.MAXIMUM_ALLOWED_ZOOM;
    }

    public void resizeDrawPanel(){
        Rectangle contentBounds = this.getContentBounds(this.drawPanel.getElements(),0);

        int newWidth = 0;
        if (contentBounds.getX() < 0) {
            newWidth = (int) contentBounds.getX();
        }

        if(contentBounds.getX() > (this.drawPanel.getX() + this.drawPanel.getWidth())){
            newWidth = -((int) contentBounds.getX());
        }

        int newHeight = 0;
        if (contentBounds.getY() < 0) {
            newHeight = (int) contentBounds.getY();
        }

        if(contentBounds.getY() > (this.drawPanel.getY() + this.drawPanel.getHeight())){
            newHeight = -((int) contentBounds.getY());
            System.out.println("Ok");
        }

        // On met à jour l'origine de la zone de dessin
        moveOrigin(newWidth, newHeight);

        // If any adjustment is needed we move the components and increase the view position
        if (newWidth != 0 || newHeight != 0) {
            for (IShape element : this.drawPanel.getElements()) {
                JComponent component = element.getComponent();
                component.setLocation(
                        GridUtils.alignToGrid(component.getX() - newWidth, this.drawPanel.getGridSize()),
                        GridUtils.alignToGrid(component.getY() - newHeight, this.drawPanel.getGridSize())
                );
            }
        }

        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        int width = (int) (parent.getHorizontalScrollBar().getValue() + getViewableDiagrampanelSize().getWidth() - newWidth);
        int height = (int) (parent.getVerticalScrollBar().getValue() + getViewableDiagrampanelSize().getHeight() - newHeight);
        this.drawPanel.setPreferredSize(new Dimension(width, height));

        this.changeViewPosition(-newWidth, -newHeight);


    }

    public Rectangle getContentBounds(Collection<IShape> elements, int border){
        if (elements.size() == 0) {
            return new Rectangle(0, 0, 0, 0);
        }
        int minx = Integer.MAX_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxx = 0;
        int maxy = 0;

        for (IShape element : elements) {
            if (element instanceof ClassShape){
                minx = Math.min(minx, element.getBounds().x - border);
                miny = Math.min(miny, element.getBounds().y - border);
                maxx = Math.max(maxx, element.getBounds().x + element.getBounds().width + border);
                maxy = Math.max(maxy, element.getBounds().y + element.getBounds().height + border);
            }
        }
        return new Rectangle(minx, miny, maxx - minx, maxy - miny);
    }

    public Dimension getViewableDiagrampanelSize() {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        return parent.getVisibleRect().getSize();
    }

    public void changeViewPosition(int incx, int incy) {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        Point viewp = parent.getViewport().getViewPosition();
        parent.getViewport().setViewSize(this.drawPanel.getPreferredSize());
        parent.getViewport().setViewPosition(new Point(viewp.x + incx, viewp.y + incy));
    }
}
