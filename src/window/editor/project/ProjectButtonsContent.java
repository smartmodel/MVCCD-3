package window.editor.project;

import main.MVCCDManager;
import main.window.diagram.WinDiagram;
import main.window.repository.WinRepository;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.Project;
import utilities.window.ButtonsContent;
import utilities.window.DialogMessage;
import utilities.window.services.ComponentService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectButtonsContent extends ButtonsContent implements ActionListener {

    private ProjectButtons projectButtons;
    /*private JPanel panel = new JPanel();
    private Box bHor;
    private JButton btnOk ;
    private JButton btnCancel ;
    private JButton btnApply ; */

    public ProjectButtonsContent(ProjectButtons newProjectButtons) {
        super(newProjectButtons);
        this.projectButtons = newProjectButtons;
        getBtnOk().addActionListener(this);
        //getBtnOk().setEnabled(false);
        getBtnCancel().addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == super.getBtnOk()) {
            if (getEditorContent().checkDatas()) {
                createProject();
                getWindow().dispose();
            } else {
                DialogMessage.showErrorEditor(projectButtons.getProjectWindow());
            }
        }
        if (source == super.getBtnCancel()) {
            getWindow().dispose();
        }
    }

    private ProjectEditorContent getEditorContent(){
        return  (ProjectEditorContent) projectButtons.getProjectWindow().getEditor().getContent();
    }
    private void createProject() {
        System.out.println("Dans create (depuis Btn)");
        JTextField projectName = getEditorContent().getProjectName();
        MVCCDManager.instance().createProject(projectName.getText());
    }

    private ProjectWindow getWindow(){
        return projectButtons.getProjectWindow();
    }

    @Override
    public Integer getWidthWindow() {
        return Preferences.PROJECT_WINDOW_WIDTH;
    }
}
