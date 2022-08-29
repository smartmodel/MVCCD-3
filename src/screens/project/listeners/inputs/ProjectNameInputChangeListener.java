package screens.project.listeners.inputs;

import screens.project.ProjectCreationScreen;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ProjectNameInputChangeListener implements DocumentListener {

    ProjectCreationScreen screen;

    public ProjectNameInputChangeListener(ProjectCreationScreen screen) {
        this.screen = screen;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.screen.revalidateProjectNameField();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.screen.revalidateProjectNameField();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.screen.revalidateProjectNameField();
    }
}
