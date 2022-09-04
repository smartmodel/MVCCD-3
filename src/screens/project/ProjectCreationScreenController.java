package screens.project;

import main.MVCCDFactory;
import preferences.Preferences;
import profile.ProfileManager;
import screens.project.listeners.ProjectInputNameListener;
import screens.utils.RegexMatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectCreationScreenController {
    ProjectCreationScreen view;


    public ProjectCreationScreenController(ProjectCreationScreen screen) {
        this.view = screen;
    }


    public void init() {
        this.initView();
        this.addProjectNameInputListener();
        this.addCreateButtonListener();
        this.populateProfilesList();
    }

    private void initView() {
        this.view.setVisible(true);
    }


    public void displayNameFieldValidationState() {
        if (this.isNameFieldValid()) {
            this.view.getNameTextField().putClientProperty("JComponent.outline", "Color.BLACK");
        } else {
            this.view.getNameTextField().putClientProperty("JComponent.outline", "error");
        }
    }

    private boolean isNameFieldValid() {
        String projectName = this.view.getNameTextField().getText();
        return RegexMatcher.matchesRegex(Preferences.UI_SCREEN_CREATE_PROJECT_NAME_INPUT_REGEX, projectName);
    }

    private void addProjectNameInputListener() {
        ProjectInputNameListener listener = new ProjectInputNameListener(this);
        this.view.getNameTextField().getDocument().addDocumentListener(listener);
    }

    private void addCreateButtonListener() {
        this.view.getCreateProjectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = ProjectCreationScreenController.this.view.getNameTextField().getText();
                if (ProjectCreationScreenController.this.isNameFieldValid()) {
                    MVCCDFactory.instance().createProject(projectName);
                } else {
                    System.out.println("Le nom du projet n'est pas valide !");
                }
            }
        });
    }

    private void populateProfilesList() {
        ArrayList<String> filesProfile = ProfileManager.instance().filesProfile();
        this.view.getProfilCombobox().addItem(Preferences.UI_COMBO_BOX_PLACEHOLDER);
        for (String fileProfile : filesProfile) {
            this.view.getProfilCombobox().addItem(fileProfile);
        }
    }

}
