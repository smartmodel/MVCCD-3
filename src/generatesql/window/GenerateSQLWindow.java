package generatesql.window;

import generatesql.MPDRGenerateSQLUtil;
import mpdr.MPDRModel;

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

        buttonExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExecute();
            }
        });

        this.code = mpdrModel.treatGenerate();
        textAreaCode.setText(code);

        /*Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("generatesql.mpdrtosql.start",
                new String[] {
                        MessagesBuilder.getMessagesProperty("the.model.physical"),
                        mpdrModel.getName()
                }
        );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(mpdrModel.treatGenerate());

        TreatmentService.treatmentFinish(this, mpdrModel, resultat,
                "the.model.physical", "generatesql.mpdrtosql.ok", "generatesql.mpdrtosql.abort");
        */

        // call onCancel() when cross is clicked
        /*setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);*/

        pack();
        setVisible(true);
    }

    private void onExecute() {
        MPDRGenerateSQLUtil.executeQuery(textAreaCode.getText(), textFieldHostName.getText(), textFieldPort.getText(), textFieldSID.getText(), textFieldUsername.getText(), new String(passwordFieldUserPassword.getPassword()));
    }

    /*private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }*/

}
