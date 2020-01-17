package window.editor.entity;

import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import preferences.Preferences;
import utilities.window.*;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.editor.PanelInputContent;
import utilities.window.editor.DialogEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class EntityInputContent extends PanelInputContent implements DocumentListener, FocusListener {

    private EntityInput entityInput;
    private JPanel panel = new JPanel();
    private STextField entityName = new STextField();
    private STextField entityShortName = new STextField();
    //private JComboBox<String> profile = new JComboBox<>();


    public EntityInputContent(EntityInput entityInput)     {
        super(entityInput);
        this.entityInput = entityInput;
        createContent();
        System.out.println("Affectation de content!");
        entityInput.setContent(this);
        super.setContent(panel);
        System.out.println(getEditor().toString());
        System.out.println("" + getEditor().getMode());
        if (getEditor().getMode().equals(DialogEditor.UPDATE)){
            MCDEntity mcdEntity = null;
            if (getEditor().getMvccdElement() instanceof MCDEntity){
                mcdEntity = (MCDEntity)getEditor().getMvccdElement();
            }
            if (mcdEntity != null){
                loadDatas(mcdEntity);
            }
        }
        checkDatas();

    }




    private void createContent() {

        entityName.setPreferredSize((new Dimension(300,20)));
        entityName.setToolTipText("Nom de l'entité");


        entityName.getDocument().addDocumentListener(this);
        entityName.addFocusListener(this);

        //entityName.addActionListener((ActionListener) this);


        entityShortName.setPreferredSize((new Dimension(300,20)));
        entityShortName.setToolTipText("Nom court de l'entité utilisé pour nommer certaines contraintes et autres");

        /*
        entityName.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent jComponent) {
                return false;
            }
        }); */

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        panel.add(entityName, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom court : "), gbc);
        gbc.gridx = 1;
        panel.add(entityShortName, gbc);

        this.add(panel);

    }

    public JTextField getEntityName() {
        return entityName;
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        if (entityName.getDocument() == e.getDocument()) {
            checkEntityName(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (entityName.getDocument() == e.getDocument()) {
            checkEntityName(true);
        }

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (entityName.getDocument() == e.getDocument()) {
            checkEntityName(true);
        }

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        Object source = focusEvent.getSource();
        if (source == entityName) {
            checkEntityName(true);
        }

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

         Object source = focusEvent.getSource();
            getButtonsContent().clearMessages();
            if (source == entityName) {
                //checkEntityName(false, true);
            }


    }

    public boolean checkDatas(){
        if (! Preferences.DEBUG_DEACTIVATE_EDITOR_CHECK_PANEL) {
            return checkEntityName(false);
        } else {
            return true;
        }
    }

    private boolean checkEntityName(boolean unitaire) {
        ArrayList<String> messagesErrors = MCDEntityService.checkName(entityName.getText());

        if (unitaire){
            super.showCheckResultat(entityName, messagesErrors);
        }
        return messagesErrors.size() == 0;
    }


    private void loadDatas(MCDEntity mcdEntity) {
        entityName.setText(mcdEntity.getName()) ;
        entityShortName.setText(mcdEntity.getShortName());
    }

    public void saveDatas(MCDEntity mcdEntity) {
        if (entityName.isUpdated()){
            mcdEntity.setName(entityName.getText());
            markChangeDatas();
        }
        if (entityShortName.isUpdated()){
            mcdEntity.setName(entityName.getText());
            markChangeDatas();
        }
    }

}
