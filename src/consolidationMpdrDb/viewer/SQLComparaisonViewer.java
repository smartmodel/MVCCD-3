package consolidationMpdrDb.viewer;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelContent;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.IPanelInputContent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SQLComparaisonViewer  extends PanelContent implements IPanelInputContent, ActionListener {

    public SQLComparaisonViewer(PanelBorderLayout panelBL) {
        super(panelBL);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public boolean isDataInitialized() {
        return false;
    }

    @Override
    public DialogEditor getEditor() {
        return null;
    }
}
