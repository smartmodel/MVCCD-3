package window.editor.mcd.attribute;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class AttributeButtonsContent extends PanelButtonsContent {


    public AttributeButtonsContent(AttributeButtons attributeButtons) {
        super(attributeButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        MCDAttribute mcdAttribute = MVCCDElementFactory.instance().createMCDAttribute((MCDContAttributes) parent);
        return mcdAttribute;
    }


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
