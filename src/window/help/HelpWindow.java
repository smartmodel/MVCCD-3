package window.help;

import main.MVCCDManager;
import preferences.Preferences;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.editor.DialogEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpWindow extends JDialog implements ActionListener {


    private PanelBorderLayoutResizer panelBLResizer ;
    private JPanel panelHelp;
    private JPanel panelButtons ;


    private JTextArea textArea;
    private JScrollPane textAreaScroll;
    private JButton btnClose;




    public HelpWindow(DialogEditor owner) {
        super(owner);
        setModal(false);
        setLocation(200,200);
        setSize(Preferences.HELP_WINDOW_WIDTH, Preferences.HELP_WINDOW_HEIGHT);

        createContent();
    }

    private void createContent() {

        //panelBLResizer = new PanelBorderLayoutResizer();
        //BorderLayout bl = new BorderLayout(Preferences.JPANEL_HGAP,Preferences.JPANEL_VGAP);
        this.setLayout( new BorderLayout(0,0));

        panelHelp = new JPanel();
        this.add(panelHelp, BorderLayout.CENTER);
        panelHelp.setLayout(new BorderLayout(Preferences.JPANEL_HGAP,Preferences.JPANEL_VGAP));

        panelButtons = new JPanel();
        this.add(panelButtons, BorderLayout.SOUTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textAreaScroll = new JScrollPane(textArea);
        textAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textAreaScroll.setPreferredSize( new Dimension(100,100));
        panelHelp.add(textAreaScroll);

        //panelHelp.add(text);

        btnClose = new JButton("Fermer");
        btnClose.addActionListener(this);
        panelButtons.add(btnClose);

        colorBackground();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
               resize();
            }
        });

    }

    private void resize(){
        int widthHelp = panelHelp.getWidth() -2 * Preferences.JPANEL_HGAP;
        int heightHelp = panelHelp.getHeight() -2 * Preferences.JPANEL_VGAP;
        //int widthHelp = panelHelp.getWidth() ;
        //int heightHelp = panelHelp.getHeight() ;

        Dimension dimHelp = new Dimension(widthHelp, heightHelp);
        textAreaScroll.setPreferredSize( dimHelp);
        textAreaScroll.setSize( dimHelp);
        textAreaScroll.setLocation(Preferences.JPANEL_HGAP,Preferences.JPANEL_VGAP);

    }


    private  void colorBackground() {
        if (Preferences.DEBUG_BACKGROUND_PANEL){
            panelHelp.setBackground(Color.GREEN);
            panelButtons.setBackground(Color.MAGENTA);
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == btnClose) {
            dispose();
        }
    }

    public void setHelpText(String text){
        textArea.setText(text);
    }

}
