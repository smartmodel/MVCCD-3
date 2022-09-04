package screens.project.listeners;

import screens.project.ProjectCreationScreenController;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ProjectInputNameListener implements DocumentListener {

    ProjectCreationScreenController controller;

    public ProjectInputNameListener(ProjectCreationScreenController controller) {
        this.controller = controller;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.controller.displayNameFieldValidationState();

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.controller.displayNameFieldValidationState();

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.controller.displayNameFieldValidationState();
    }
}
