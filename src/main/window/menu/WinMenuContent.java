package main.window.menu;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDManager;
import main.MVCCDWindow;
import messages.MessagesBuilder;
import preferences.Preferences;
import repository.editingTreat.ProjectEditingTreat;
import screens.project.ProjectCreationScreen;
import screens.project.ProjectCreationScreenController;
import screens.project.ProjectCreationScreenModel;
import utilities.window.DialogMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinMenuContent implements ActionListener {

    private MVCCDWindow mvccdWindow;
    private JMenuBar menuBar;
    private JMenu project;
    private JMenu edit;
    private JMenu profile;
    private JMenu help;

    private JMenuItem projectNew;
    private JMenuItem projectEdit;
    private JMenuItem projectOpen;
    private JMenu projectOpenRecents;
    private JMenuItem[] projectOpenRecentsItems;


    private JMenuItem projectClose;
    private JMenuItem projectSave;
    private JMenuItem projectSaveAs;
    /*
    private JMenu projectPreferences;
    private JMenuItem projectPreferencesDeveloper;
    private JMenuItem projectPreferencesGeneral;
    private JMenuItem projectPreferencesMCD;
    private JMenuItem projectProfile;

    private JMenuItem profileCreate;
    */


    public WinMenuContent(MVCCDWindow mvccdWindow, JMenuBar menuBar) {
        this.mvccdWindow = mvccdWindow;
        this.menuBar = menuBar;

        this.project = new JMenu("Projet");
        menuBar.add(this.project);
        this.edit = new JMenu("Edition");
        menuBar.add(this.edit);
        /*
        profile = new JMenu("Profil");
        menuBar.add(profile);
        */

        this.help = new JMenu("Aide");
        menuBar.add(this.help);

        this.projectNew = new JMenuItem("Nouveau");
        this.projectNew.addActionListener(this);
        this.project.add(this.projectNew);
        this.projectEdit = new JMenuItem("Propriétés");
        this.projectEdit.addActionListener(this);
        this.projectEdit.setEnabled(false);
        this.project.add(this.projectEdit);
        this.projectOpen = new JMenuItem("Ouvrir...");
        this.projectOpen.addActionListener(this);
        this.project.add(this.projectOpen);
        this.projectOpenRecents = new JMenu("Ouvrir récents");
        this.projectOpenRecents.addActionListener(this);
        this.project.add(this.projectOpenRecents);

        this.projectOpenRecentsItems = new JMenuItem[Preferences.FILES_RECENTS_AUTHORIZED];
        for (int i = 1; i <= Preferences.FILES_RECENTS_AUTHORIZED; i++) {
            this.projectOpenRecentsItems[i - 1] = new JMenuItem();
            this.projectOpenRecentsItems[i - 1].addActionListener(this);
            this.projectOpenRecentsItems[i - 1].setVisible(false);
            this.projectOpenRecents.add(this.projectOpenRecentsItems[i - 1]);
        }


        this.projectClose = new JMenuItem("Fermer");
        this.projectClose.addActionListener(this);
        this.projectClose.setEnabled(true);
        this.project.add(this.projectClose);

        this.project.addSeparator();
        this.projectSave = new JMenuItem("Sauver");
        this.projectSave.addActionListener(this);
        this.projectSave.setEnabled(false);
        this.project.add(this.projectSave);
        this.projectSaveAs = new JMenuItem("Sauver comme copie");
        this.projectSaveAs.addActionListener(this);
        this.projectSaveAs.setEnabled(false);
        this.project.add(this.projectSaveAs);

        this.project.addSeparator();

        /*
        projectPreferences = new JMenu("Préférences");
        projectPreferences.addActionListener(this);
        projectPreferencesDeveloper = new JMenuItem("Développeur");
        projectPreferencesDeveloper.addActionListener(this);
        projectPreferences.add(projectPreferencesDeveloper);
        projectPreferencesGeneral = new JMenuItem("Général");
        projectPreferencesGeneral.addActionListener(this);
        projectPreferences.add(projectPreferencesGeneral);
        projectPreferencesMCD = new JMenuItem("MCD");
        projectPreferencesMCD.addActionListener(this);
        projectPreferences.add(projectPreferencesMCD);
        project.add(projectPreferences);

        projectProfile = new JMenuItem("Profil");
        project.add(projectProfile);

        profileCreate = new JMenuItem("Créer/Modifier");
        profile.add(profileCreate);
*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String messageExceptionTarget = "";
        try {
            Object source = e.getSource();
            if (source == this.projectNew) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.new.exception");
                this.newProject();
            }
            if (source == this.projectEdit) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.edit.exception");
                ProjectEditingTreat.treatUpdate(this.mvccdWindow);
            }
            if (source == this.projectOpen) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.open.exception");
                this.openProject();
            }
            for (int i = 0; i < Preferences.FILES_RECENTS_AUTHORIZED; i++) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.recent.open.exception");
                if (source == this.projectOpenRecentsItems[i]) {
                    this.openProjectRecent(this.projectOpenRecentsItems[i].getText());
                }
            }

            if (source == this.projectClose) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.close.exception");
                boolean confirmClose = true;
                if (MVCCDManager.instance().isDatasProjectChanged()) {
                    String message = MessagesBuilder.getMessagesProperty("project.close.change.not.saved");
                    confirmClose = DialogMessage.showConfirmYesNo_No(this.mvccdWindow, message) == JOptionPane.YES_OPTION;
                }
                if (confirmClose) {
                    MVCCDManager.instance().closeProject();
                }
            }

            if (source == this.projectSave) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.save.exception");
                MVCCDManager.instance().saveProject();
            }
            if (source == this.projectSaveAs) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.save.as.exception");
                MVCCDManager.instance().saveAsProject(false);
            }

        } catch (Exception exception) {
            String messageException = MessagesBuilder.getMessagesProperty("bar.menu.exception", messageExceptionTarget);
            ViewLogsManager.catchException(exception, messageException);
        }
    }

    private void newProject() {
        if (MVCCDManager.instance().getProject() == null) {
            //Project project = ProjectEditingTreat.treatNew(mvccdWindow);
            ProjectCreationScreen projectCreationView = new ProjectCreationScreen();
            ProjectCreationScreenModel projectCreationScreenModel = new ProjectCreationScreenModel();

            ProjectCreationScreenController projectCreationScreenController = new ProjectCreationScreenController(projectCreationView, projectCreationScreenModel);
            projectCreationScreenController.init();

            if (this.project != null) {
                // Quittance de création d'un nouveau projet
                String message = MessagesBuilder.getMessagesProperty("project.new", this.project.getName());
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
            }
        } else {
            String message = MessagesBuilder.getMessagesProperty("project.new.not.close");
            DialogMessage.showOk(this.mvccdWindow, message);
        }
    }

    private void openProject() {
        if (MVCCDManager.instance().getProject() == null) {
            MVCCDManager.instance().openProject();
        } else {
            String message = MessagesBuilder.getMessagesProperty("project.open.not.close");
            DialogMessage.showOk(this.mvccdWindow, message);
        }
    }

    private void openProjectRecent(String fileName) {
        if (MVCCDManager.instance().getProject() == null) {
            MVCCDManager.instance().openProjectRecent(fileName);
        } else {
            String message = MessagesBuilder.getMessagesProperty("project.open.not.close");
            DialogMessage.showOk(this.mvccdWindow, message);
        }

    }

    public void activateProjectOpenRecentsItem(int i, String text) {
        this.projectOpenRecentsItems[i].setText(text);
        this.projectOpenRecentsItems[i].setVisible(true);

        this.projectOpenRecents.setEnabled(true);
    }

    public void desActivateProjectOpenRecentsItems() {
        for (int i = 0; i < Preferences.FILES_RECENTS_AUTHORIZED; i++) {
            this.projectOpenRecentsItems[i].setVisible(false);
        }
        this.projectOpenRecents.setEnabled(false);
    }

    public JMenuItem getProjectEdit() {
        return this.projectEdit;
    }

    public JMenuItem getProjectSaveAs() {
        return this.projectSaveAs;

    }

    public JMenuItem getProjectSave() {
        return this.projectSave;
    }

    public JMenuItem getProjectClose() {
        return this.projectClose;
    }
}
