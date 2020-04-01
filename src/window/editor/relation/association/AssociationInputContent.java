package window.editor.relation.association;

import main.MVCCDElement;
import mcd.MCDAssociation;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import mcd.services.MCDEntityService;
import mcd.services.MCDUtilService;
import newEditor.PanelInputContent;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class AssociationInputContent extends PanelInputContent {

    private JPanel panel = new JPanel();
    private STextField fieldAssociationName = new STextField(this);
    private SComboBox fieldFromEntity = new SComboBox(this);
    private SComboBox fieldToEntity = new SComboBox(this);
    private IMCDModel iMCDModelContainer;

    public AssociationInputContent(AssociationInput associationInput)     {
        super(associationInput);
        associationInput.setPanelContent(this);
        if ((MCDElement) getEditor().getMvccdElementParent() != null) {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementParent());
        } else {
            iMCDModelContainer = IMCDModelService.getIModelContainer((MCDElement) getEditor().getMvccdElementCrt());
        }
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();

    }




    private void createContent() {

        fieldAssociationName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        fieldAssociationName.setToolTipText("Nom de l'association");
        //fieldAssociationName.setCheckPreSave(true);
        fieldAssociationName.getDocument().addDocumentListener(this);
        fieldAssociationName.addFocusListener(this);

        System.out.println(((MCDElement)iMCDModelContainer).getName());
        ArrayList<MCDEntity> mcdEntities = MCDEntityService.getMCDEntitiesByClassName(
               iMCDModelContainer, MCDEntity.class.getName());

        //ArrayList<MCDEntity> mcdEntities = ProjectManager.instance().getEntities();
        MCDEntityService.sortNameAsc(mcdEntities);

        fieldToEntity.addItem(SComboBox.LINEWHITE);
        fieldFromEntity.addItem(SComboBox.LINEWHITE);
        //fieldFromEntity.addItem(SComboBox.LIGNEVIDE);
        for (MCDEntity mcdEntity : mcdEntities){
            fieldFromEntity.addItem(mcdEntity.getNamePath());
            fieldToEntity.addItem(mcdEntity.getNamePath());
        }

        fieldFromEntity.addItemListener(this);
        fieldFromEntity.addFocusListener(this);
        fieldToEntity.addItemListener(this);
        fieldToEntity.addFocusListener(this);

        super.getsComponents().add(fieldAssociationName);
        super.getsComponents().add(fieldFromEntity);
        super.getsComponents().add(fieldToEntity);

        //enabledOrVisibleFalse();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panel);

        panel.add(new JLabel("Tracée depuis"), gbc);
        gbc.gridx++;
        panel.add(new JLabel("Nom"), gbc);
        gbc.gridx++;
        panel.add(new JLabel("Tracée vers"), gbc);
        gbc.gridx++;

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(fieldFromEntity, gbc);
        gbc.gridx++;
        panel.add(fieldAssociationName, gbc);
        gbc.gridx++;
        panel.add(fieldToEntity, gbc);
        gbc.gridx++;


        this.add(panel);

    }







    protected void changeField(DocumentEvent e) {
        // Les champs obligatoires sont testés sur la procédure checkDatasPreSave()

    }



    @Override
    protected void changeFieldSelected(ItemEvent e) {
            Object source = e.getSource();

    }



    @Override
    protected void changeFieldDeSelected(ItemEvent e) {
        Object source = e.getSource();

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    @Override
    public boolean checkDatasPreSave(boolean unitaire) {
        boolean ok =   checkAssociationFrom(unitaire) ;
        ok = checkAssociationTo(unitaire && ok) && ok;

        return ok;
    }

    protected boolean checkDatas(){
        boolean ok = checkDatasPreSave(false);
        // Autre attributs

        return ok;
    }



    private boolean checkAssociationFrom(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldFromEntity,
                true,
                "entity.from.and.name" );

        return super.checkInput(fieldFromEntity, unitaire, messages);

     }

    private boolean checkAssociationTo(boolean unitaire) {
        ArrayList<String> messages = MCDUtilService.checkEmptyComboBox(
                fieldToEntity,
                true,
                "entity.to.and.name" );

        return super.checkInput(fieldToEntity, unitaire, messages);

    }




    @Override
    protected void initDatas() {
        /*
        MCDContEndRels mcdContEndRels = (MCDContEndRels) mvccdElement;
        MCDEntity mcdEntityFrom = (MCDEntity) mcdContEndRels.getParent();

        SComboBoxService.selectByText(fieldFromEntity, mcdEntityFrom.getName());
        */
        fieldFromEntity.setSelectedEmpty();
        fieldAssociationName.setText("");
        fieldToEntity.setSelectedEmpty();


    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDAssociation mcdAssociation = (MCDAssociation) mvccdElement;

        if (fieldAssociationName.checkIfUpdated()) {
            mcdAssociation.setName(fieldAssociationName.getText());
        }

    }



    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        //entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
    }

    public MCDEntity getMCDEntityTo(){
        return MCDEntityService.getMCDEntityByNamePath(
                iMCDModelContainer, (String) fieldToEntity.getSelectedItem());
    }

    public MCDEntity getMCDEntityFrom(){
        return MCDEntityService.getMCDEntityByNamePath(
                iMCDModelContainer, (String) fieldFromEntity.getSelectedItem());
   }
}
