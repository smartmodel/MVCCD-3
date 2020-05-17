package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDAttributesEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;
import project.ProjectElement;

import java.awt.event.ActionEvent;

public class EntityButtonsContent extends PanelButtonsContent {


    public EntityButtonsContent(EntityButtons entityButtons) {
        super(entityButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return MVCCDElementFactory.instance().createMCDEntity((ProjectElement)parent);
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }

    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }

    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object source = e.getSource();
        if (source == btnApply) {
            System.out.println("Dans entityButton");
            if (getEditor().getMode().equals(DialogEditor.NEW)) {
                new MCDEntityEditingTreat().treatUpdate(getEditor().getOwner(),
                        getEditor().getMvccdElementNew());
            }
        }

    }

     */
}
