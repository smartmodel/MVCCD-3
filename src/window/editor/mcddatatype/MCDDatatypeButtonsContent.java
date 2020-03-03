package window.editor.mcddatatype;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDContAttributes;
import mcd.MCDEntity;
import preferences.Preferences;
import project.ProjectElement;
import utilities.window.editor.PanelButtonsContent;
import window.editor.entity.EntityButtons;

public class MCDDatatypeButtonsContent extends PanelButtonsContent  {


    public MCDDatatypeButtonsContent(MCDDatatypeButtons mcdDatatypeButtons) {
        super(mcdDatatypeButtons);
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
        return Preferences.MCDDATATYPE_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_MCDDATATYPE_NAME;
    }


}
