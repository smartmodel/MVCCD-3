package main.window.menu;

import main.MVCCDManager;
import main.MVCCDWindow;
import preferences.Preferences;
import window.editor.project.ProjectWindow;
import window.preferences.PreferencesWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WinMenuContent implements ActionListener {

    private MVCCDWindow mvccdWindow;
    private JMenuBar menuBar;
    private JMenu project;
    private JMenu edit;
    private JMenu profile;
    private JMenu help;

    private JMenuItem projectNew;
    private JMenuItem projectOpen;
    private JMenu projectOpenRecents;
    private JMenuItem[] projectOpenRecentsItems;


    private JMenuItem projectClose;
    private JMenuItem projectSave;
    private JMenuItem projectSaveAs;
    private JMenuItem projectPreferences;
    private JMenuItem projectProfile;

    private JMenuItem profileCreate;

    public WinMenuContent(MVCCDWindow mvccdWindow, JMenuBar menuBar) {
        this.mvccdWindow = mvccdWindow;
        this.menuBar = menuBar;

        project = new JMenu("Projet");
        menuBar.add(project);
        edit = new JMenu("Edition");
        menuBar.add(edit);
        profile = new JMenu("Profil");
        menuBar.add(profile);
        help = new JMenu("Aide");
        menuBar.add(help);

        projectNew = new JMenuItem("Nouveau");
        projectNew.addActionListener(this);
        project.add(projectNew);
        projectOpen = new JMenuItem("Ouvrir...");
        projectOpen.addActionListener(this);
        project.add(projectOpen);
        projectOpenRecents = new JMenu("Ouvrir récents");
        projectOpenRecents.addActionListener(this);
        project.add(projectOpenRecents);

        projectOpenRecentsItems = new JMenuItem[Preferences.FILES_RECENTS_AUTHORIZED];
        for (int i=1 ; i<= Preferences.FILES_RECENTS_AUTHORIZED; i++){
            projectOpenRecentsItems[i-1] = new JMenuItem();
            projectOpenRecentsItems[i-1].addActionListener(this);
            projectOpenRecentsItems[i-1].setVisible(false);
            projectOpenRecents.add(projectOpenRecentsItems[i-1]);
        }


        projectClose = new JMenuItem("Fermer");
        projectClose.addActionListener(this);
        project.add(projectClose);

        project.addSeparator();
        projectSave = new JMenuItem("Sauver");
        projectSave.addActionListener(this);
        project.add(projectSave);
        projectSaveAs = new JMenuItem("Sauver comme copie");
        projectSaveAs.addActionListener(this);
        project.add(projectSaveAs);

        project.addSeparator();
        projectPreferences = new JMenuItem("Préférences");
        projectPreferences.addActionListener(this);
        project.add(projectPreferences);
        projectProfile = new JMenuItem("Profil");
        project.add(projectProfile);

        profileCreate = new JMenuItem("Créer/Modifier");
        profile.add(profileCreate);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == projectNew) {
            ProjectWindow fen = new ProjectWindow(mvccdWindow);
            fen.setVisible(true);
        }
        if (source == projectOpen) {
            MVCCDManager.instance().openProject();
        }
        for (int i = 0; i < Preferences.FILES_RECENTS_AUTHORIZED; i++) {
            if (source == projectOpenRecentsItems[i]) {
                MVCCDManager.instance().openProjectRecent(projectOpenRecentsItems[i].getText());
            }
        }

        if (source == projectClose){
            MVCCDManager.instance().closeProject();
        }
        if (source == projectSave){
            MVCCDManager.instance().saveProject();
        }
        if (source == projectSaveAs){
            MVCCDManager.instance().saveAsProject();
        }

        if (source == projectPreferences){
            PreferencesWindow fen = new PreferencesWindow(mvccdWindow);
            fen.setVisible(true);
        }
    }

    public void setSaveEnable(Boolean enable){
        projectSave.setEnabled(enable);
        projectSaveAs.setEnabled(enable);
    }

    public void activateProjectOpenRecentsItem(int i, String text) {
        projectOpenRecentsItems[i].setText(text);
        projectOpenRecentsItems[i].setVisible(true);

        projectOpenRecents.setEnabled(true);
    }

    public void desActivateProjectOpenRecentsItems() {
        for (int i = 0; i < Preferences.FILES_RECENTS_AUTHORIZED; i++) {
            projectOpenRecentsItems[i].setVisible(false);
        }
        projectOpenRecents.setEnabled(false);
    }
}
