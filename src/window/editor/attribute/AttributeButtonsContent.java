package window.editor.attribute;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import newEditor.PanelButtonsContent;
import preferences.Preferences;

public class AttributeButtonsContent extends PanelButtonsContent {


    public AttributeButtonsContent(AttributeButtons attributeButtons) {
        super(attributeButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement() {
        //JTextField entityName = getEditorContent().getEntityName();
        MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElementParent();
        MCDAttribute mcdAttribute = MVCCDElementFactory.instance().createMCDAttribute(mcdContAttributes);
        return mcdAttribute;
    }

/*
    private PrefMCDInputContent getEditorContent(){
        return  (PrefMCDInputContent) getEditor().getInput().getPanelContent();
    }
*/


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
