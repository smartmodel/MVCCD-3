package window.editor.mcd.operation.parameter;

import main.MVCCDElement;
import mcd.MCDParameter;

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
