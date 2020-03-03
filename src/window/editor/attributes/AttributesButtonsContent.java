package window.editor.attributes;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;
import window.editor.entity.EntityButtons;

public class AttributesButtonsContent extends PanelButtonsContent  {


    public AttributesButtonsContent(AttributesButtons attributesButtons) {
        super(attributesButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement() {
        return null;
    }

    @Override
    protected void completeNewMVCCDElement(MVCCDElement mvccdElement) {
     }

    @Override
    public Integer getWidthWindow() {
        return Preferences.ENTITY_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return null;
    }


}
