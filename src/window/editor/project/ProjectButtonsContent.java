package window.editor.project;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import preferences.Preferences;
import project.Project;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.DialogMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectButtonsContent extends PanelButtonsContent implements ActionListener {


    public ProjectButtonsContent(ProjectButtons projectButtons) {
        super(projectButtons);
    }

    @Override
    protected void updateMCDElement() {

    }

    @Override
    protected MVCCDElement createMVCCDElement() {
        JTextField projectName = getEditorContent().getProjectName();
        Project project = MVCCDManager.instance().createProject(projectName.getText());
        getEditorContent().saveDatas(project);
        return project;
    }


    private ProjectInputContent getEditorContent(){
        return  (ProjectInputContent) getEditor().getInput().getPanelContent();
    }

    @Override
    public Integer getWidthWindow() {
        return Preferences.PROJECT_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_PROJECT_NAME;
    }


}
