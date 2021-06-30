package generatesql.window;

import main.MVCCDElement;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
import repository.editingTreat.mpdr.MPDRModelEditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import treatment.services.TreatmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GenerateSQLWindow extends JDialog {

    private Window owner;
    private MPDRModel mpdrModel;

    private JPanel contentPane;
    private JPasswordField passwordFieldUserPassword;
    private JTextField textFieldUserName;
    private JTextField textFieldSID;
    private JTextField textFieldPort;
    private JTextField textFieldHostName;
    private JLabel labelHostName;
    private JTextArea textAreaCode;
    private JLabel labelPort;
    private JLabel labelSID;
    private JLabel labelUserName;
    private JLabel labelUserPassword;
    private JScrollPane scrollPaneRight;
    private JPanel panelLeft;
    private JPanel panelTop;
    private JTextArea textAreaConsole;
    private JPanel panelBottom;
    private JButton buttonExecuter;

    public GenerateSQLWindow(MPDRModel mpdrModel) {
        this.owner = owner;
        this.mpdrModel = mpdrModel;

        setModal(true);
        setLocation(100,100);

        setTitle("Génération du code SQL-DDL");

        setContentPane(contentPane);

        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("generatesql.mpdrtosql.start",
                new String[] {
                        MessagesBuilder.getMessagesProperty(""),
                        mpdrModel.getName()
                }
        );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(mpdrModel.treatGenerate());

        TreatmentService.treatmentFinish(owner, mpdrModel, resultat,
                "", "generatesql.mpdrtosql.ok", "generatesql.mpdrtosql.abort");

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
