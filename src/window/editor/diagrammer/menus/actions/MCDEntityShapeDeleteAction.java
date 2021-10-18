package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MCDEntityShapeDeleteAction extends AbstractAction {

    private MCDEntityShape shape;

    public MCDEntityShapeDeleteAction(String name, Icon icon, MCDEntityShape shape) {
        super(name, icon);
        this.shape = shape;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        delete();
    }

    private void delete() {
        DiagrammerService.getDrawPanel().deleteElement(shape);
    }
}
