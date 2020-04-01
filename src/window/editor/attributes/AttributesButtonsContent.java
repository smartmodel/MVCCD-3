package window.editor.attributes;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import newEditor.PanelButtonsContent;
import preferences.Preferences;
import window.editor.entity.EntityButtons;

public class AttributesButtonsContent extends PanelButtonsContent {


    public AttributesButtonsContent(AttributesButtons attributesButtons) {
        super(attributesButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement() {
        return null;
    }



    @Override
    protected String getHelpFileName() {
        return null;
    }


}
