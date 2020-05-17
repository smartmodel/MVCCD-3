package window.editor.operation.parameter;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDParameter;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionListener;

public class ParameterTransientButtonsContent extends ParameterButtonsContent implements ActionListener {


    public ParameterTransientButtonsContent(ParameterTransientButtons parameterButtons) {

        super(parameterButtons);
        btnApply.setVisible(false);
    }


    public void  treatCreate() {
        MVCCDElement newMVCCDElement = new MCDParameter(null);
        saveDatas(newMVCCDElement);
        getEditor().setMvccdElementNew(newMVCCDElement);
    }


}
