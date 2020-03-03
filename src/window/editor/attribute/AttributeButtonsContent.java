package window.editor.attribute;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

public class AttributeButtonsContent extends PanelButtonsContent  {


    public AttributeButtonsContent(AttributeButtons attributeButtons) {
        super(attributeButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement() {
        //JTextField entityName = getEditorContent().getEntityName();
        MCDContAttributes mcdContAttributes = (MCDContAttributes) getEditor().getMvccdElement();
        MCDAttribute mcdAttribute = MVCCDElementFactory.instance().createMCDAttribute(mcdContAttributes);
        saveDatas(mcdAttribute);
        return mcdAttribute;
    }

    @Override
    protected void completeNewMVCCDElement(MVCCDElement mvccdElement) {
        MCDAttribute mcdAttribute = (MCDAttribute) mvccdElement;
        MVCCDManager.instance().showNewMVCCDElementInRepository(mcdAttribute, getEditor());
     }

/*
    private PrefMCDInputContent getEditorContent(){
        return  (PrefMCDInputContent) getEditor().getInput().getPanelContent();
    }
*/

    @Override
    public Integer getWidthWindow() {
        return Preferences.ENTITY_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
