package utilities.window.editor.texteditor;

import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.files.FileRead;
import utilities.window.DialogMessage;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.scomponents.STextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;


public class TextEditor extends JDialog implements WindowListener {


    private JPanel panel= new JPanel();;
    private TextEditorText textEditorText;
    private TextEditorButtons textEditorButtons;

    private PanelBorderLayoutResizer panelBLResizer ;

    private File file ;


    /**
     * Dans le constructeur, le menu, les panneaux et les listeners sont mis en place.
     * Le gestionnaire de redimensionnement est créé.
     * @param owner
     * @param file
     */
    public TextEditor(Dialog owner, File file) {
        this.file = file;

        setTitle(file.getPath());
        setSize(Preferences.TEXTEDITOR_WINDOW_WIDTH, Preferences.TEXTEDITOR_SQL_WINDOW_HEIGHT);
      getContentPane().add(panel);

        setModal(true);

        panelBLResizer = new PanelBorderLayoutResizer();

        String borderLayoutPositionText = BorderLayout.CENTER;
        String borderLayoutPositionButtons= BorderLayout.SOUTH;

        textEditorText = new TextEditorText(this, borderLayoutPositionText, panelBLResizer);
        textEditorButtons = new TextEditorButtons(this, borderLayoutPositionButtons, panelBLResizer);
        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);

        panel.add(textEditorText, borderLayoutPositionText);
        panel.add(textEditorButtons, borderLayoutPositionButtons);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

        addWindowListener(this);

    }

    public PanelBorderLayoutResizer getPanelBLResizer() {
        return panelBLResizer;
    }

    public TextEditorText getTextEditorText() {
        return textEditorText;
    }

    public TextEditorButtons getTextEditorButtons() {
        return textEditorButtons;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
        //Chargement du code
        initDatas();

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    public File getFile() {
        return file;
    }


    public void initDatas() {
       String text = "";
        if (file.exists()) {
            text = FileRead.readToString(file);
        }
        textEditorText.getTextEditorTextContent().getTextArea().setText(text);
        getTextEditorText().getTextEditorTextContent().setDataInitialized(true);
        enabledButtons();
     }

    void confirmClose() {
        if (getTextEditorText().getTextEditorTextContent().getTextArea().checkIfUpdated()){
            String message = MessagesBuilder.getMessagesProperty ("editor.close.change.not.saved");
            boolean confirm = DialogMessage.showConfirmYesNo_No(this, message) == JOptionPane.YES_OPTION;
            if (confirm){
                dispose();
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//cancel
            }
        } else {
            dispose();
        }
    }

    public void enabledButtons(){
        STextArea textArea = getTextEditorText().getTextEditorTextContent().getTextArea();
        boolean update = textArea.checkIfUpdated();
        TextEditorButtonsContent  buttonsContent = getTextEditorButtons().getTextEditorButtonsContent();
        buttonsContent.getBtnUndo().setEnabled(update);
        buttonsContent.getBtnOk().setEnabled(update);
        buttonsContent.getBtnApply().setEnabled(update);
    }

}
