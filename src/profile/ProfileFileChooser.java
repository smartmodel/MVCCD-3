package profile;

import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.ProjectsRecents;
import utilities.files.UtilFiles;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ProfileFileChooser extends JFileChooser {

    public static final String OPEN = "open";
    public static final String SAVE = "save";
    private String mode;

    public ProfileFileChooser(String mode) {
        super();
        this.mode = mode;

    }

    public File fileChoose() {

        String title = "";
        String btnApprovedText = "";
        if (mode.equals(SAVE)) {
            title = MessagesBuilder.getMessagesProperty("window.save.profile.title");
            btnApprovedText = MessagesBuilder.getMessagesProperty("window.create.btn.text");
        }  else if (mode.equals(OPEN)) {
            title = MessagesBuilder.getMessagesProperty("window.open.profile.title");
            btnApprovedText = MessagesBuilder.getMessagesProperty("window.reference.btn.text");
        }

        File directory;
        File file;


        if (mode.equals(SAVE)) {
            file = new File(MVCCDManager.instance().getProject().getName() +
                        Preferences.FILE_DOT + Preferences.FILE_PROFILE_EXTENSION);

            setSelectedFile(file);
        }

        setDialogTitle(title);
        setApproveButtonText(btnApprovedText);
        setAcceptAllFileFilterUsed(false);
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
                MessagesBuilder.getMessagesProperty("file.preferences.extension.descriptif",
                        new String[] {Preferences.FILE_PROFILE_EXTENSION }),
                Preferences.FILE_PROFILE_EXTENSION );
        addChoosableFileFilter(extFilter);
        int returnVal = showOpenDialog(MVCCDManager.instance().getMvccdWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile();
        }
        return null;
    }



}
