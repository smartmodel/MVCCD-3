package window.editor.diagrammer.elements;

import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.utils.GridUtils;

import javax.swing.*;

public abstract class ClassShape extends JPanel implements IShape {

    public ClassShape() {
        this.setBounds(DiagrammerConstants.DEFAULT_ENTITY_POSITION_X,DiagrammerConstants.DEFAULT_ENTITY_POSITION_Y, DiagrammerConstants.DEFAULT_ENTITY_WIDTH,DiagrammerConstants.DEFAULT_ENTITY_HEIGHT);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void zoom(int fromFactor, int toFactor) {
        int newXPosition = this.getBounds().x * toFactor / fromFactor;
        int newYPosition = this.getBounds().y * toFactor / fromFactor;
        int newWidth = this.getBounds().width * toFactor / fromFactor;
        int newHeight = this.getBounds().height * toFactor / fromFactor;

        // Set la nouvelle position et taille de l'élément
        this.setSize(GridUtils.alignToGrid(newWidth, toFactor), GridUtils.alignToGrid(newHeight, toFactor));
        this.setLocation(GridUtils.alignToGrid(newXPosition, toFactor), GridUtils.alignToGrid(newYPosition, toFactor));
    }

    @Override
    public void setLocationDifference(int differenceX, int differenceY) {
        this.setLocation(this.getBounds().x + differenceX, this.getBounds().y + differenceY);
    }
}
