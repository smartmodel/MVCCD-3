package project;

import main.MVCCDManager;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
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

    public File fileChoose() {

        String title = "";
        String btnApprovedText = "";
        if (mode.equals(SAVE)) {
            title = MessagesBuilder.getMessagesProperty("window.save.project.title");
            btnApprovedText = MessagesBuilder.getMessagesProperty("window.save.btn.text");
        }  else if (mode.equals(OPEN)) {
            title = MessagesBuilder.getMessagesProperty("window.open.project.title");
            btnApprovedText = MessagesBuilder.getMessagesProperty("window.open.btn.text");
        }

        System.out.println(title);

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
            if (lastUsedFile != null) {
                file = new File(lastUsedFile.getName());
            } else {
                file = new File(MVCCDManager.instance().getProject().getName() +
                        Preferences.FILE_DOT + Preferences.FILE_PROJECT_EXTENSION);
            }
           setSelectedFile(file);
        }

        /*
        if (mode.equals(SAVE)) {
            if (MVCCDManager.instance().getFileProjectCurrent() != null) {
                directory = UtilFiles.getDirectory(MVCCDManager.instance().getFileProjectCurrent());
                file = new File(MVCCDManager.instance().getFileProjectCurrent().getName());
            } else {
                directory = new File("C:" + System.getProperty("file.separator") + "temp");
                file = new File(MVCCDManager.instance().getProject().getName() +
                        Preferences.FILE_DOT + Preferences.FILE_PROJECT_EXTENSION);
            }
            setCurrentDirectory(directory);
            setSelectedFile(file);
        }

         */


        //System.out.println(directory.getPath());

        setDialogTitle(title);
        setApproveButtonText(btnApprovedText);
        setAcceptAllFileFilterUsed(false);
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
                MessagesBuilder.getMessagesProperty("file.project.extension.descriptif"),
                Preferences.FILE_PROJECT_EXTENSION );
        addChoosableFileFilter(extFilter);
        int returnVal = showOpenDialog(MVCCDManager.instance().getMvccdWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile();
        }
        return null;
    }



}
