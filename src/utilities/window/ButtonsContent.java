package utilities.window;

import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.services.ComponentService;

import javax.swing.*;
import java.awt.*;

public abstract class ButtonsContent extends PanelContent {

    private PanelBorderLayout concreteButtonContent;
    private JPanel panel = new JPanel();
    private Box bHor;
    private JButton btnOk ;
    private JButton btnCancel ;
    private JButton btnApply ;
    private JTextArea messages ;
    private JScrollPane messagesScroll;

    public ButtonsContent(PanelBorderLayout concreteButtonContent) {
        super(concreteButtonContent);

        this.concreteButtonContent = concreteButtonContent;

        createContent();
        super.setContent(panel, false);
        //super.setContent(panel);

    }

    private void createContent() {
        Box bVer = Box.createVerticalBox();

        messages = new JTextArea();
        messages.setText("a\r\n" + "b\r\n" + "c\r\n"  + "d\r\n");
        //messages.setComponentPopupMenu(new ButtonsContentMessagesPopupMenu());
        messagesScroll = new JScrollPane(messages);
        messagesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        messagesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagesScroll.setPreferredSize( new Dimension(getWidthWindow(), Preferences.PANEL_BUTTONS_HEIGHT));

        bVer.add(messagesScroll);

        Box btns = Box.createHorizontalBox();
        btnOk = new JButton("Ok");
        btns.add(btnOk);
        btnCancel = new JButton("Annuler");
        btns.add(btnCancel);
        btnApply = new JButton("Appliquer");
        btns.add(btnApply);
        bVer.add(btns);
        panel.add(bVer);
        bVer.setBackground(Color.YELLOW);
        panel.setBackground(Color.BLACK);
    }

    public abstract Integer getWidthWindow() ;

    public Dimension resizeContent(){

        Dimension dimensionBL = super.resizeContent();

        ComponentService.changeWidth(messagesScroll, (int)panel.getWidth() -10 );
        ComponentService.changePreferredWidth(messagesScroll, (int)panel.getWidth() -10 );

        return dimensionBL;
    }

    public JButton getBtnOk() {
        return btnOk;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnApply() {
        return btnApply;
    }

    public void addMessage(String message){
        if (StringUtils.isNotEmpty(messages.getText())){
            messages.append("\r\n");
        }
        messages.append(message);
    }

    public void clearMessages(){
        messages.setText("");
     }
}
