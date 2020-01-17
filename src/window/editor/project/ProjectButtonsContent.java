package window.editor.project;

import main.MVCCDManager;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.DialogMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectButtonsContent extends PanelButtonsContent implements ActionListener {

    private ProjectButtons projectButtons;
    /*private JPanel panel = new JPanel();
    private Box bHor;
    private JButton btnOk ;
    private JButton btnCancel ;
    private JButton btnApply ; */

    public ProjectButtonsContent(ProjectButtons projectButtons) {
        super(projectButtons);
        this.projectButtons = projectButtons;
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
                getEditor().dispose();
            } else {
                DialogMessage.showErrorEditor(projectButtons.getEditor());
            }
        }
        if (source == super.getBtnCancel()) {
            getEditor().dispose();
        }
    }

    private ProjectInputContent getEditorContent(){
        return  (ProjectInputContent) projectButtons.getEditor().getInput().getContent();
    }
    private void createProject() {
        System.out.println("Dans create (depuis Btn)");
        JTextField projectName = getEditorContent().getProjectName();
        MVCCDManager.instance().createProject(projectName.getText());
    }


    @Override
    public Integer getWidthWindow() {
        return Preferences.PROJECT_WINDOW_WIDTH;
    }


}
