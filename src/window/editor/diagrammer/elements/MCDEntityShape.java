package window.editor.diagrammer.elements;

import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;

import java.awt.*;

public class MCDEntityShape extends ClassShape {

    public MCDEntityShape(DrawPanel drawPanel) {
        super();
        this.setBackground(Color.MAGENTA);
        MCDEntityShapeListener listener = new MCDEntityShapeListener(this, drawPanel);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

}
