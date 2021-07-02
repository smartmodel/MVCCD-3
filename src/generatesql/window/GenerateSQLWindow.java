package generatesql.window;

import console.LogsManager;
import generatesql.MPDRGenerateSQL;
import generatesql.MPDRGenerateSQLUtil;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import treatment.services.TreatmentService;
import utilities.window.DialogMessage;

import javax.swing.*;
import java.awt.event.*;

public class GenerateSQLWindow extends JDialog {

    private final String DB_HOST_NAME = "db.ig.he-arc.ch";
    private final String DB_PORT = "1521";
    private final String DB_SID = "ens";
    private final String DB_USERNAME = "LOICJONA_SPRINGEN";
    private final String DB_USER_PASSWORD = "LOICJONA_SPRINGEN";

    //private Window owner;
    private MPDRModel mpdrModel;
    private String code;

    private JPanel contentPane;
    private JPasswordField passwordFieldUserPassword;
    private JTextField textFieldUsername;
    private JTextField textFieldSID;
    private JTextField textFieldPort;
    private JTextField textFieldHostName;
    private JLabel labelHostName;
    private JTextArea textAreaCode;
    private JLabel labelPort;
    private JLabel labelSID;
    private JLabel labelUsername;
    private JLabel labelUserPassword;
    private JScrollPane scrollPaneRight;
    private JPanel panelLeft;
    private JPanel panelTop;
    private JTextArea textAreaConsole;
    private JPanel panelBottom;
    private JButton buttonExecute;
    private JButton buttonSaveFile;

    public JTextArea getTextAreaCode() {
        return textAreaCode;
    }

    public JTextArea getTextAreaConsole() {
        return textAreaConsole;
    }

    public GenerateSQLWindow(MPDRModel mpdrModel) {
        //this.owner = owner;
        this.mpdrModel = mpdrModel;

        setModal(true);
        setLocation(100,100);

        setTitle("Génération du code SQL-DDL");

        setContentPane(contentPane);

        textFieldHostName.setText(DB_HOST_NAME);
        textFieldPort.setText(DB_PORT);
        textFieldSID.setText(DB_SID);
        textFieldUsername.setText(DB_USERNAME);
        passwordFieldUserPassword.setText(DB_USER_PASSWORD);

        buttonSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveFile();
            }
        });

        buttonExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExecute();
            }
        });

        //this.code = mpdrModel.treatGenerate(this);

        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("generatesql.mpdrtosql.start",
                new String[] {
                        MessagesBuilder.getMessagesProperty("the.model.physical"),
                        mpdrModel.getName()
                }
        );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(mpdrModel.treatGenerate(this));

        TreatmentService.treatmentFinish(this, mpdrModel, resultat,
                "the.model.physical", "generatesql.mpdrtosql.ok", "generatesql.mpdrtosql.abort");

        scrollPaneRight.getVerticalScrollBar().setValue(0);

        pack();
        setVisible(true);
    }

    private void onExecute() {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("generatesql.sqlexecute.start",
                new String[] {
                        MessagesBuilder.getMessagesProperty("the.sql.code"),
                        MessagesBuilder.getMessagesProperty("the.model.physical"),
                        mpdrModel.getName()
                }
        );

        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(MPDRGenerateSQLUtil.executeQuery(
                textAreaCode.getText(),
                textFieldHostName.getText(),
                textFieldPort.getText(),
                textFieldSID.getText(),
                textFieldUsername.getText(),
                new String(passwordFieldUserPassword.getPassword())));

        treatResultat(this, resultat, "the.sql.code", "generatesql.sqlexecute.ok", "generatesql.sqlexecute.abort");
    }

    private void onSaveFile() {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("generatesql.sqlfile.start",
            new String[] {
                MessagesBuilder.getMessagesProperty("the.sql.code"),
                MessagesBuilder.getMessagesProperty("the.model.physical"),
                mpdrModel.getName()
            }
        );

        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(MPDRGenerateSQLUtil.generateSQLFile(textAreaCode.getText()));
        //MPDRGenerateSQLUtil.generateSQLFile(textAreaCode.getText());

        treatResultat(this, resultat, "the.sql.code", "generatesql.sqlfile.ok", "generatesql.sqlfile.abort");

        //DialogMessage.showOk(this, message);
    }

    private void treatResultat(GenerateSQLWindow owner, Resultat resultat, String propertyTheElement, String propertyOk, String propertyError) {
        String message = "";
        String messageElement = MessagesBuilder.getMessagesProperty(propertyTheElement);

        if (resultat.isNotError()) {
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                new String[] {
                    messageElement, MessagesBuilder.getMessagesProperty("the.model.physical"), mpdrModel.getName()});
        } else {
            message = MessagesBuilder.getMessagesProperty(propertyError,
                    new String[]{messageElement, MessagesBuilder.getMessagesProperty("the.model.physical"), mpdrModel.getName()});
        }
        resultat.add(new ResultatElement (message, ResultatLevel.INFO));

        printResultat(resultat);
        DialogMessage.showOk(owner, message);
    }

    private void printResultat(Resultat resultat) {
        int i = 0;
        for (ResultatElement resultatElement : resultat.getElementsAllLevel()) {
            if (i == 0) {
                this.getTextAreaConsole().setText("");
                LogsManager.newResultatElement(resultatElement);
                this.getTextAreaConsole().append(resultatElement.getText() + System.lineSeparator());
            } else {
                LogsManager.continueResultatElement(resultatElement);
                this.getTextAreaConsole().append(resultatElement.getText() + System.lineSeparator());
            }
            i++;
        }
    }
}
