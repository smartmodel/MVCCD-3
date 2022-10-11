package screens.project;

import main.MVCCDElement;
import main.MVCCDFactory;
import main.MVCCDManager;
import preferences.Preferences;
import profile.ProfileManager;
import project.Project;
import screens.IScreenController;
import screens.project.listeners.ProjectInputNameListener;
import screens.utils.RegexMatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ProjectCreationScreenController implements IScreenController {
    ProjectCreationScreen view;
    ProjectCreationScreenModel model;

    public ProjectCreationScreenController(ProjectCreationScreen view, ProjectCreationScreenModel model) {
        this.view = view;
        this.model = model;
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
            this.hideErrors();

        } else {
            this.view.getNameTextField().putClientProperty("JComponent.outline", "error");
            addError("Erreur");
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
                if (isNameFieldValid()) {
                    String projectName = view.getNameTextField().getText();
                    // Crée le projet
                    Project project = MVCCDFactory.instance().createProject(projectName);
                    if (project != null) {
                        MVCCDManager.instance().setProject(project);
                        MVCCDManager.instance().completeNewProject();
                        // Ferme la fenêtre
                        view.dispose();
                    }
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


    public void addPropertyChangeListener(){
        this.view.getNameTextField().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("La propriété " + evt.getPropertyName() + " a été changée !");
            }
        });
    }


    @Override
    public void addError(String error) {
        System.out.println("Nouvelle erreur !");
        // Met à jour le modèle
        this.model.getErrors().add(error);
        // Refresh la vue
        this.displayErrorsAsBulletList();
    }

    public void hideErrors(){
        this.model.getErrors().clear();
        this.view.getErrorsPanel().removeAll();
        this.view.getErrorsPanel().repaint();
    }

    public void displayErrorsAsBulletList(){
        // Crée une liste contenant les erreurs
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        this.model.getErrors().forEach(e -> {
            sb.append("<li>");
            sb.append(e);
            sb.append("</li>");
        });
        sb.append("</html>");
        */
        this.view.getErrorsPanel().removeAll();
        this.model.getErrors().forEach(e -> {
            this.view.getErrorsPanel().add(new JLabel(e));
        });
        this.view.pack();
    }
}
