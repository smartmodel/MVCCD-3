package project;

import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.Trace;
import utilities.files.UtilFiles;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ProjectFileChooser extends JFileChooser {

    public static final String OPEN = "open";
    public static final String SAVE = "save";
    private String mode;

    public ProjectFileChooser(String mode) {
        super();
        this.mode = mode;

    }

    public File fileChoose(boolean nameProposed) {

        String title = "";
        String btnApprovedText = "";
        if (mode.equals(SAVE)) {
            title = MessagesBuilder.getMessagesProperty("window.save.project.title");
            btnApprovedText = MessagesBuilder.getMessagesProperty("window.save.btn.text");
        }  else if (mode.equals(OPEN)) {
            title = MessagesBuilder.getMessagesProperty("window.open.project.title");
            btnApprovedText = MessagesBuilder.getMessagesProperty("window.open.btn.text");
        }

        File directory;
        File file;

        ProjectsRecents projetsRecents = MVCCDManager.instance().getProjectsRecents();
        File lastUsedFile = null;
        if (projetsRecents.getRecents().size() > 0) {
            lastUsedFile = projetsRecents.getRecents().get(0);
            directory = UtilFiles.getDirectory(lastUsedFile);
            setCurrentDirectory(directory);
        }


        if (mode.equals(SAVE)) {
            //Pendant la phase de mise au point
            //#MAJ 2021-03-12 Sauvegarde Serializable/XML L'extension doit être xml ou mvccd
            String extension ;
            if (PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML()){
                extension =  Preferences.FILE_PROJECT_EXTENSION ;
            } else {
                extension = "xml";
            }
            file = new File(MVCCDManager.instance().getProject().getName() +
                        Preferences.FILE_DOT + extension);
            setSelectedFile(file);
        }


        setDialogTitle(title);
        setApproveButtonText(btnApprovedText);
        setAcceptAllFileFilterUsed(false);
        setFileSelectionMode(JFileChooser.FILES_ONLY);

        //#MAJ 2021-03-12 Sauvegarde Serializable/XML L'extension doit être xml ou mvccd
        FileNameExtensionFilter extFilter ;
        if (PreferencesManager.instance().preferences().isPERSISTENCE_SERIALISATION_INSTEADOF_XML()) {
            extFilter = new FileNameExtensionFilter(
                    MessagesBuilder.getMessagesProperty("file.project.extension.descriptif",
                            new String[] {Preferences.FILE_PROJECT_EXTENSION }),
                    Preferences.FILE_PROJECT_EXTENSION );
        } else {
            extFilter = new FileNameExtensionFilter(
                    "Fichier MVC-CD (*.xml)", "xml");
        }

        /*
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
                MessagesBuilder.getMessagesProperty("file.project.extension.descriptif",
                        new String[] {Preferences.FILE_PROJECT_EXTENSION }),
                Preferences.FILE_PROJECT_EXTENSION );

         */
        addChoosableFileFilter(extFilter);
        int returnVal = showOpenDialog(MVCCDManager.instance().getMvccdWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile();
        }
        return null;
    }



}
