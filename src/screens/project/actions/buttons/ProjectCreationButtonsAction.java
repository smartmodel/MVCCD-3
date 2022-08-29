package screens.project.actions.buttons;

import main.MVCCDFactory;
import screens.project.ProjectCreationScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ProjectCreationButtonsAction extends AbstractAction {

    private ProjectCreationScreen parentFrame;

    public ProjectCreationButtonsAction(ProjectCreationScreen parentFrame) {
        super();
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.createProject();
    }

    private void createProject() {
        String projectName = this.parentFrame.getProjectNameInput().getText();
        MVCCDFactory.instance().createProject(projectName);
    }
}
