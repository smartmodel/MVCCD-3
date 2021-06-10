package window.editor.diagrammer.elements;

import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;
import window.editor.diagrammer.utils.ResizableBorder;

import java.awt.*;

public class MCDEntityShape extends ClassShape {

    public MCDEntityShape(DrawPanel drawPanel) {
        super();
        this.setBackground(Color.MAGENTA);
        MCDEntityShapeListener listener = new MCDEntityShapeListener(this, drawPanel);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        this.setBorder(new ResizableBorder());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
    }

    public void resize(int newX, int newY, int newWidth, int newHeight) {
        this.setBounds(newX,newY,newWidth,newHeight);
    }

}
