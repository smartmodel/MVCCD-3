package window.editor.operation.constraint.unique;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContConstraints;
import mcd.MCDContModels;
import mcd.MCDModel;
import mcd.MCDUnique;
import preferences.Preferences;
import project.ProjectElement;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;
import window.editor.model.ModelEditor;

import java.awt.event.ActionEvent;

public class UniqueButtonsContent extends PanelButtonsContent {


    public UniqueButtonsContent(UniqueButtons uniqueButtons) {
        super(uniqueButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        int scope = ((UniqueEditor) getEditor()).getScope();
        if (scope == UniqueEditor.UNIQUE) {
            return MVCCDElementFactory.instance().createMCDUnique((MCDContConstraints)parent);
        }
        if (scope == UniqueEditor.NID) {
            return MVCCDElementFactory.instance().createMCDNID((MCDContConstraints)parent);
        }
        return null;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }

    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object source = e.getSource();
        if (source == btnApply) {
            actionApply();
        }
    }

    public void actionApply(){
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            if (getEditor().getScope() == UniqueEditor.NID) {
                new MCDNIDEditingTreat().treatUpdate(getEditor().getOwner(),
                        getEditor().getMvccdElementNew());
            }
            if (getEditor().getScope() == UniqueEditor.UNIQUE) {
                new MCDUniqueEditingTreat().treatUpdate(getEditor().getOwner(),
                        getEditor().getMvccdElementNew());
            }
        }
    }
}
