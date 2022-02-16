package main.window.menu;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDManager;
import main.MVCCDWindow;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import repository.editingTreat.ProjectEditingTreat;
import utilities.window.DialogMessage;
import window.editor.diagrammer.elements.shapes.classes.SquaredShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

public class WinMenuContent implements ActionListener {

    private MVCCDWindow mvccdWindow;
    private JMenuBar menuBar;
    private JMenu fichier;
    private JMenu project;
    private JMenu edit;
    private JMenu profile;
    private JMenu help;

    private JMenuItem imprimer;
    private JMenuItem exporter;

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

        fichier = new JMenu("Fichier");
        menuBar.add(fichier);
        project = new JMenu("Projet");
        menuBar.add(project);
        edit = new JMenu("Edition");
        menuBar.add(edit);
        /*
        profile = new JMenu("Profil");
        menuBar.add(profile);
        */

        help = new JMenu("Aide");
        menuBar.add(help);

        imprimer = new JMenuItem("Imprimer");
        imprimer.addActionListener(this);
        fichier.add(imprimer);
        exporter = new JMenuItem("Exporter");
        exporter.addActionListener(this);
        fichier.add(exporter);

        projectNew = new JMenuItem("Nouveau");
        projectNew.addActionListener(this);
        project.add(projectNew);
        projectEdit = new JMenuItem("Propriétés");
        projectEdit.addActionListener(this);
        projectEdit.setEnabled(false);
        project.add(projectEdit);
        projectOpen = new JMenuItem("Ouvrir...");
        projectOpen.addActionListener(this);
        project.add(projectOpen);
        projectOpenRecents = new JMenu("Ouvrir récents");
        projectOpenRecents.addActionListener(this);
        project.add(projectOpenRecents);

        projectOpenRecentsItems = new JMenuItem[Preferences.FILES_RECENTS_AUTHORIZED];
        for (int i = 1; i <= Preferences.FILES_RECENTS_AUTHORIZED; i++) {
            projectOpenRecentsItems[i - 1] = new JMenuItem();
            projectOpenRecentsItems[i - 1].addActionListener(this);
            projectOpenRecentsItems[i - 1].setVisible(false);
            projectOpenRecents.add(projectOpenRecentsItems[i - 1]);
        }


        projectClose = new JMenuItem("Fermer");
        projectClose.addActionListener(this);
        projectClose.setEnabled(true);
        project.add(projectClose);

        project.addSeparator();
        projectSave = new JMenuItem("Sauver");
        projectSave.addActionListener(this);
        projectSave.setEnabled(false);
        project.add(projectSave);
        projectSaveAs = new JMenuItem("Sauver comme copie");
        projectSaveAs.addActionListener(this);
        projectSaveAs.setEnabled(false);
        project.add(projectSaveAs);

        project.addSeparator();

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
            if (source == imprimer) {
                // messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.print.exception");
                // Déselectionne toutes les formes
                DiagrammerService.getDrawPanel().deselectAllShapes();
                printComponent(mvccdWindow.getDiagrammer().getContent().getPanelDraw());

            }
            if (source == exporter) {
                //messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.export.exception");
                // Déselectionne toutes les formes
                DiagrammerService.getDrawPanel().deselectAllShapes();
                // Ouvre une boîte de dialogue pour que l'utilisateur choisisse l'emplacement de sauvegarde et le nom du fichier
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    exportComponent(mvccdWindow.getDiagrammer().getContent().getPanelDraw(), fileChooser.getSelectedFile().toString());
                }
            }
            if (source == projectNew) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.new.exception");
                newProject();
            }
            if (source == projectEdit) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.edit.exception");
                ProjectEditingTreat.treatUpdate(mvccdWindow);
            }
            if (source == projectOpen) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.open.exception");
                openProject();
            }
            for (int i = 0; i < Preferences.FILES_RECENTS_AUTHORIZED; i++) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.recent.open.exception");
                if (source == projectOpenRecentsItems[i]) {
                    openProjectRecent(projectOpenRecentsItems[i].getText());
                }
            }

            if (source == projectClose) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.close.exception");
                boolean confirmClose = true;
                if (MVCCDManager.instance().isDatasProjectChanged()) {
                    String message = MessagesBuilder.getMessagesProperty("project.close.change.not.saved");
                    confirmClose = DialogMessage.showConfirmYesNo_No(mvccdWindow, message) == JOptionPane.YES_OPTION;
                }
                if (confirmClose) {
                    MVCCDManager.instance().closeProject();
                }
            }

            if (source == projectSave) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.save.exception");
                MVCCDManager.instance().saveProject();
            }
            if (source == projectSaveAs) {
                messageExceptionTarget = MessagesBuilder.getMessagesProperty("project.save.as.exception");
                MVCCDManager.instance().saveAsProject(false);
            }

        } catch (Exception exception) {
            String messageException = MessagesBuilder.getMessagesProperty("bar.menu.exception",
                    messageExceptionTarget);
            ViewLogsManager.catchException(exception, messageException);
        }
    }


    /**
     * Exporte un composant au format JPG & PNG
     *
     * @param component -> Le composant à imprimer
     * @param fileName  -> Le chemin complet + nom du fichier à enregistrer
     */
    public void exportComponent(Component component, String fileName) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        // Désactive temporairement la grille de dessin du Diagrammer
        PreferencesManager.instance().getApplicationPref().setDIAGRAMMER_SHOW_GRID(false);
        component.printAll(g);
        g.dispose();
        try {
            ImageIO.write(image, "jpg", new File(fileName.trim() + ".jpg"));
            ImageIO.write(image, "png", new File(fileName.trim() + ".png"));
        } catch (IOException exp) {
            exp.printStackTrace();
        } finally {
            PreferencesManager.instance().getApplicationPref().setDIAGRAMMER_SHOW_GRID(true);
        }
    }

    /**
     * Lance une boîte de dialogue pour l'impression d'un composant et s'occupe de formater le rendu de celui-ci.
     * Le composant est mis en mode landscape au format A4, la grille est de dessin est temporairement désactivée
     * le temps de l'impression et est réactivée ensuite.
     *
     * @param component -> Le composant à imprimer
     */
    public void printComponent(Component component) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");


        PageFormat pf = pj.defaultPage();
        pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        paper.setSize(8.5 * 72, 13 * 72);
        paper.setImageableArea(0.5 * 72, 0.0 * 72, 8 * 72, 14 * 72);
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.LANDSCAPE);
        Book book = new Book();//java.awt.print.Book
        book.append(((pg, pf1, pageNum) -> {
            if (pageNum > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2 = (Graphics2D) pg;
            // Désactive temporairement la grille de dessin du Diagrammer
            PreferencesManager.instance().getApplicationPref().setDIAGRAMMER_SHOW_GRID(false);
            g2.translate(pf1.getImageableX() + pf1.getImageableWidth() / 2 - component.getWidth() / 2, pf1.getImageableY() + pf1.getImageableHeight() / 2 - component.getHeight() / 2);
            component.print(g2);
            return Printable.PAGE_EXISTS;
        }), pf);
        pj.setPageable(book);
        if (pj.printDialog() == false)
            return;

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        } finally {
            PreferencesManager.instance().getApplicationPref().setDIAGRAMMER_SHOW_GRID(true);
        }
    }

    private void newProject() {
        if (MVCCDManager.instance().getProject() == null) {
            Project project = ProjectEditingTreat.treatNew(mvccdWindow);
            if (project != null) {
                // Quittance de création d'un nouveau projet
                String message = MessagesBuilder.getMessagesProperty("project.new", project.getName());
                ViewLogsManager.printMessage(message, WarningLevel.INFO);
            }
        } else {
            String message = MessagesBuilder.getMessagesProperty("project.new.not.close");
            DialogMessage.showOk(mvccdWindow, message);
        }
    }

    private void openProject() {
        if (MVCCDManager.instance().getProject() == null) {
            MVCCDManager.instance().openProject();
        } else {
            String message = MessagesBuilder.getMessagesProperty("project.open.not.close");
            DialogMessage.showOk(mvccdWindow, message);
        }
    }

    private void openProjectRecent(String fileName) {
        if (MVCCDManager.instance().getProject() == null) {
            MVCCDManager.instance().openProjectRecent(fileName);
        } else {
            String message = MessagesBuilder.getMessagesProperty("project.open.not.close");
            DialogMessage.showOk(mvccdWindow, message);
        }

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

    public JMenuItem getProjectEdit() {
        return projectEdit;
    }

    public JMenuItem getProjectSaveAs() {
        return projectSaveAs;

    }

    public JMenuItem getProjectSave() {
        return projectSave;
    }

    public JMenuItem getProjectClose() {
        return projectClose;
    }
}
