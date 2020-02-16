package window.editor.project;

import main.MVCCDElement;
import main.MVCCDFactory;
import main.MVCCDManager;
import preferences.Preferences;
import project.Project;
import utilities.window.editor.PanelButtonsContent;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ProjectButtonsContent extends PanelButtonsContent implements ActionListener {


    public ProjectButtonsContent(ProjectButtons projectButtons) {

        super(projectButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement() {
        JTextField projectName = getEditorContent().getProjectName();
        Project project = MVCCDFactory.instance().createProject(projectName.getText());
        return project;
    }

    @Override
    protected void completeNewMVCCDElement(MVCCDElement mvccdElement) {
        MVCCDManager.instance().completeNewProject((Project) mvccdElement);
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
