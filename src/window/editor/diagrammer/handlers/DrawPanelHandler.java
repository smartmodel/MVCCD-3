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

    private void removeUnnecessaryWhitespaceAroundDiagram() {

        Rectangle contentBounds = getContentBounds(this.drawPanel.getElements(), 0);

        int newX = 0;
        int newY = 0;

        int newWidth = (int) this.drawPanel.getVisibleRect().getMaxX();

        // Si le max X de contentBounds est plus grand que le maxX de la view, on adapte la largeur
        if (contentBounds.getMaxX() > this.drawPanel.getVisibleRect().getMaxX()) {
            newWidth = (int) (contentBounds.getX() + contentBounds.getWidth());
        }

         int newHeight = (int) this.drawPanel.getVisibleRect().getHeight();

        // Si le maxY de contentBounds dépasse le maxY de la view, on adapte la hauteur
        if (contentBounds.getMaxY() > this.drawPanel.getVisibleRect().getMaxY()) {
            newHeight = (int) (contentBounds.getY() + contentBounds.getHeight());
        }

        this.moveOrigin(newX, newY);

        for (IShape element : this.drawPanel.getElements()) {
            element.setLocation(GridUtils.alignToGrid(element.getBounds().x - newX, this.drawPanel.getGridSize()), GridUtils.alignToGrid(element.getBounds().y - newY, this.drawPanel.getGridSize()));
        }

        // On change la position de la vue
        this.changeViewPosition(-newX, -newY);
        this.drawPanel.setPreferredSize(new Dimension(newWidth - newX, newHeight - newY));

        this.checkIfScrollbarsAreNecessary();
    }

    private boolean isHorizontalScrollbarVisible() {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        return parent.getHorizontalScrollBarPolicy() == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
    }

    private boolean isVerticalScrollbarVisible() {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        return parent.getVerticalScrollBarPolicy() == ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
    }

    private void checkIfScrollbarsAreNecessary() {

        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);

        Rectangle diaWithoutWhite = getContentBounds(this.drawPanel.getElements(), 0);
        Dimension viewSize = getViewableDiagrampanelSize();

        boolean vertWasVisible = this.isVerticalScrollbarVisible();
        boolean horWasVisible = this.isHorizontalScrollbarVisible();

        int verSbWidth = 0;
        int horSbHeight = 0;
        if (vertWasVisible) {
            verSbWidth = parent.getVerticalScrollBar().getWidth();
        }
        if (horWasVisible) {
            horSbHeight = parent.getHorizontalScrollBar().getHeight();
        }

        // La scrollbar est inclut dans la zone visible -> on la masque
        if (parent.getHorizontalScrollBar().getValue() < this.drawPanel.getGridSize() && diaWithoutWhite.getX() + diaWithoutWhite.getWidth() <= viewSize.getWidth() + verSbWidth) {
            this.setHorizontalScrollbarVisibility(false);
        }
        else if (parent.getHorizontalScrollBar().getValue() < this.drawPanel.getGridSize() && getViewableDiagrampanelSize().width + parent.getHorizontalScrollBar().getValue() == diaWithoutWhite.getX() + diaWithoutWhite.getWidth()) {
           setHorizontalScrollbarVisibility(false);
        }
        else {
           setHorizontalScrollbarVisibility(true);
        }

        if (parent.getVerticalScrollBar().getValue() < this.drawPanel.getGridSize() && diaWithoutWhite.getY() + diaWithoutWhite.getHeight() <= viewSize.getHeight() + horSbHeight) {
            setVerticalScrollbarVisibility(false);
        }
        else if (parent.getVerticalScrollBar().getValue() < this.drawPanel.getGridSize() && getViewableDiagrampanelSize().height + parent.getVerticalScrollBar().getValue() == diaWithoutWhite.getY() + diaWithoutWhite.getHeight()) {
            setVerticalScrollbarVisibility(false);
        }
        else {
            setVerticalScrollbarVisibility(true);
        }


        int adjustedX = 0;
        int adjustedY = 0;

        if (parent.getHorizontalScrollBar().getValue() != 0 && vertWasVisible && !isVerticalScrollbarVisible()) {
            adjustedX = GridUtils.alignToGrid(horSbHeight, this.drawPanel.getGridSize());
        }
        if (parent.getVerticalScrollBar().getValue() != 0 && horWasVisible && !isHorizontalScrollbarVisible()) {
            adjustedY = GridUtils.alignToGrid(verSbWidth, this.drawPanel.getGridSize());
        }

        if (adjustedX != 0 || adjustedY != 0) {
            this.drawPanel.setPreferredSize(new Dimension((int) (this.drawPanel.getPreferredSize().getWidth() + adjustedX), (int) this.drawPanel.getPreferredSize().getHeight() + adjustedY));
            changeViewPosition(adjustedX, adjustedY);
        }
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

    public void changeViewPosition(int deltaX, int deltaY) {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        Point viewportPosition = parent.getViewport().getViewPosition();
        parent.getViewport().setViewSize(this.drawPanel.getPreferredSize());
        parent.getViewport().setViewPosition(new Point(viewportPosition.x + deltaX, viewportPosition.y + deltaY));
    }

    public void updatePanelAndScrollbars() {
        this.insertWhiteSpaceInUpperLeftCorner();
        this.removeUnnecessaryWhitespaceAroundDiagram();
        this.drawPanel.repaint();
    }

    private void insertWhiteSpaceInUpperLeftCorner() {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);

        Rectangle diaWithoutWhite = getContentBounds(this.drawPanel.getElements(), 0);

        int adjustWidth = 0;
        if (diaWithoutWhite.getX() < 0) {
            adjustWidth = (int) diaWithoutWhite.getX();
        }

        int adjustHeight = 0;
        if (diaWithoutWhite.getY() < 0) {
            adjustHeight = (int) diaWithoutWhite.getY();
        }

        moveOrigin(adjustWidth, adjustHeight);

        for (IShape element : this.drawPanel.getElements()){
            Component component = element.getComponent();
            component.setLocation(GridUtils.alignToGrid(component.getX() - adjustWidth, this.drawPanel.getGridSize()), GridUtils.alignToGrid(component.getY() - adjustHeight, this.drawPanel.getGridSize()));
        }

        if (adjustWidth < 0) {
            this.setHorizontalScrollbarVisibility(true);
        }
        if (adjustHeight < 0) {
            this.setVerticalScrollbarVisibility(true);
        }


        int width = (int) (parent.getHorizontalScrollBar().getValue() + getViewableDiagrampanelSize().getWidth() - adjustWidth);
        int height = (int) (parent.getVerticalScrollBar().getValue() + getViewableDiagrampanelSize().getHeight() - adjustHeight);
        this.drawPanel.setPreferredSize(new Dimension(width, height));

        changeViewPosition(-adjustWidth, -adjustHeight);
    }

    private void setHorizontalScrollbarVisibility(boolean visible) {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        if (visible) {
            parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        }
        else {
            parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        }
    }

    private void setVerticalScrollbarVisibility(boolean visible) {
        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this.drawPanel);
        if (visible) {
            parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        }
        else {
            parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        }
    }

    public void scroll(int differenceX, int differenceY){
        for (IShape element : this.drawPanel.getElements()) {
            element.setLocationDifference(differenceX, differenceY);
        }
    }

    public void endScroll(){
        // Mise à jour du panel et des scrollbars
        this.updatePanelAndScrollbars();
    }
}
