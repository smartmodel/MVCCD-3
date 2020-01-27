package window.editor.entity;

import main.MVCCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import utilities.window.*;
import utilities.window.editor.PanelInputContent;
import utilities.window.editor.DialogEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;

public class EntityInputContent extends PanelInputContent {

    private JPanel panel = new JPanel();
    private STextField entityName = new STextField();
    private STextField entityShortName = new STextField();

    //private JComboBox<String> profile = new JComboBox<>();


    public EntityInputContent(EntityInput entityInput)     {
        super(entityInput);
        entityInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        if (getEditor().getMode().equals(DialogEditor.UPDATE)){
            MCDEntity mcdEntity = null;
            if (getEditor().getMvccdElement() instanceof MCDEntity){
                mcdEntity = (MCDEntity)getEditor().getMvccdElement();
            }
            if (mcdEntity != null){
                loadDatas(mcdEntity);
            }
        }

    }




    private void createContent() {

        entityName.setPreferredSize((new Dimension(300,20)));
        entityName.setToolTipText("Nom de l'entité");
        entityName.setCheckPreSave(true);

        entityName.getDocument().addDocumentListener(this);
        entityName.addFocusListener(this);

        //entityName.addActionListener((ActionListener) this);


        entityShortName.setPreferredSize((new Dimension(300,20)));
        entityShortName.setToolTipText("Nom court de l'entité utilisé pour nommer certaines contraintes et autres");

        entityShortName.getDocument().addDocumentListener(this);
        entityShortName.addFocusListener(this);

        super.getsComponents().add(entityName);
        super.getsComponents().add(entityShortName);

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



    protected void changeField(DocumentEvent e) {
        if (entityName.getDocument() == e.getDocument()) {
            checkEntityName(true);
        }
        if (entityShortName.getDocument() == e.getDocument()) {
            checkEntityShortName(true);
        }
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();
        if (source == entityName) {
            checkEntityName(true);
        }
        if (source == entityShortName) {
            checkEntityShortName(true);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    protected boolean checkDatas(){
            boolean resultat = checkEntityName(false);
            resultat =  checkEntityShortName(false)  && resultat ;
            return resultat;
    }

    private boolean checkEntityName(boolean unitaire) {
        return super.checkInput(entityName, unitaire, MCDEntityService.checkName(entityName.getText()));
    }

    private boolean checkEntityShortName(boolean unitaire) {
        return super.checkInput(entityShortName, unitaire, MCDEntityService.checkShortName(entityShortName.getText()));
    }


    private void loadDatas(MCDEntity mcdEntity) {
        entityName.setText(mcdEntity.getName()) ;
        entityShortName.setText(mcdEntity.getShortName());
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        saveDatas((MCDEntity) mvccdElement);
    }

    public void saveDatas(MCDEntity mcdEntity) {
        System.out.println("save...");
        if (entityName.checkIfUpdated()){
            mcdEntity.setName(entityName.getText());
        }
        if (entityShortName.checkIfUpdated()){
            mcdEntity.setShortName(entityShortName.getText());
         }
    }


    @Override
    public boolean checkDatasPreSave() {
        boolean resultat = checkEntityName(false);
        return resultat;
    }
}
