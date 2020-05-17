package window.editor.attribute;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDParameter;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

public class AttributeTransientButtonsContent extends AttributeButtonsContent {


    public AttributeTransientButtonsContent(AttributeTransientButtons attributeTransientButtons) {
        super(attributeTransientButtons);
    }


    @Override
    public void  treatCreate()  {
        MVCCDElement newMVCCDElement = new MCDAttribute(null);
        saveDatas(newMVCCDElement);
        getEditor().setMvccdElementNew(newMVCCDElement);
    }

    protected void createContent() {
        super.createContent();
        btnApply.setEnabled(false);
    }




    }
