package connections;

import main.MVCCDManager;
import messages.MessagesBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ConDriverFileChooser extends JFileChooser {

    private ConDB conDB;

    public ConDriverFileChooser(ConDB conDB) {
        super();
        this.conDB = conDB;

    }

    public File conResourceDriverFileChoose() {

        String title = MessagesBuilder.getMessagesProperty("con.connection.driver.file.title", conDB.getText());
        String btnApprovedText =MessagesBuilder.getMessagesProperty("window.reference.btn.text");

        setDialogTitle(title);
        setApproveButtonText(btnApprovedText);
        setAcceptAllFileFilterUsed(false);
        setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        FileNameExtensionFilter  extFilter = new FileNameExtensionFilter(
                MessagesBuilder.getMessagesProperty("file.driver.extension.descriptif",
                        new String[] {conDB.getText()}), "jar");

        addChoosableFileFilter(extFilter);
        int returnVal = showOpenDialog(MVCCDManager.instance().getMvccdWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile();
        }
        return null;
    }



}
