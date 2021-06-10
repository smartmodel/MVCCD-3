package window.editor.diagrammer.elements;

import window.editor.diagrammer.interfaces.IResizable;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.utils.GridUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class ClassShape extends SquaredShape {

    public ClassShape() {
        super();
        this.setBounds(DiagrammerConstants.DEFAULT_ENTITY_POSITION_X,DiagrammerConstants.DEFAULT_ENTITY_POSITION_Y, DiagrammerConstants.DEFAULT_ENTITY_WIDTH,DiagrammerConstants.DEFAULT_ENTITY_HEIGHT);
    }


}
