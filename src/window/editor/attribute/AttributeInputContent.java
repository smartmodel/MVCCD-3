package window.editor.attribute;

import main.MVCCDElement;
import mcd.MCDAttribute;
import mcd.services.MCDAttributeService;
import preferences.Preferences;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.StereotypesManager;
import utilities.UtilDivers;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.STextField;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AttributeInputContent extends PanelInputContent  {

    private JPanel panel = new JPanel();
    private STextField attributeName = new STextField();
    private STextField stereotypesStringUML = new STextField();

    private SCheckBox mandatory = new SCheckBox();
    private SCheckBox aid = new SCheckBox();
    private SCheckBox list = new SCheckBox();

    private SCheckBox  frozen = new SCheckBox();
    private SCheckBox  ordered = new SCheckBox();

    private SCheckBox uppercase = new SCheckBox();

    private STextField initValue = new STextField();
    private STextField derivedValue = new STextField();

    public AttributeInputContent(AttributeInput attributeInput)     {
        super(attributeInput);
        attributeInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();
     }




    private void createContent() {

        attributeName.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        attributeName.setToolTipText("Nom de l'attribut");
        attributeName.setCheckPreSave(true);
        attributeName.getDocument().addDocumentListener(this);
        attributeName.addFocusListener(this);

        stereotypesStringUML.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        stereotypesStringUML.setToolTipText("Stéréotypes de l'attribut");
        stereotypesStringUML.getDocument().addDocumentListener(this);
        //stereotypesStringUML.addMouseListener(this);
        stereotypesStringUML.addFocusListener(this);
        stereotypesStringUML.setIndirectInput(true);

        mandatory.setToolTipText("Obligation de valeur");
        mandatory.addItemListener(this);
        mandatory.addFocusListener(this);

        aid.setToolTipText("Identifiant artificiel");
        aid.addItemListener(this);
        aid.addFocusListener(this);

        list.setToolTipText("Liste de valeurs");
        list.addItemListener(this);
        list.addFocusListener(this);

        frozen.setToolTipText("Non modifiable");
        frozen.addItemListener(this);
        frozen.addFocusListener(this);

        ordered.setToolTipText("Ordonnancement des valeurs de liste");
        ordered.addItemListener(this);
        ordered.addFocusListener(this);

        uppercase.setToolTipText("Mise en majuscule");
        uppercase.addItemListener(this);
        uppercase.addFocusListener(this);

        derivedValue.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        derivedValue.setToolTipText("Expression de calcul de dérivation");
        derivedValue.getDocument().addDocumentListener(this);
        derivedValue.addFocusListener(this);

        initValue.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        initValue.setToolTipText("Expression de calcul de valeur initiale");
        initValue.getDocument().addDocumentListener(this);
        initValue.addFocusListener(this);

        super.getsComponents().add(attributeName);
        super.getsComponents().add(stereotypesStringUML);
        super.getsComponents().add(mandatory);
        super.getsComponents().add(aid);
        super.getsComponents().add(list);
        super.getsComponents().add(frozen);
        super.getsComponents().add(ordered);
        super.getsComponents().add(uppercase);
        super.getsComponents().add(derivedValue);
        super.getsComponents().add(initValue);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel("Nom : "), gbc);
        gbc.gridx++;
        panel.add(attributeName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Id. naturel : "), gbc);
        gbc.gridx++;
        panel.add(aid, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Obligatoire : "), gbc);
        gbc.gridx++;
        panel.add(mandatory, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Liste : "), gbc);
        gbc.gridx++;
        panel.add(list, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Ordonnacement : "), gbc);
        gbc.gridx++;
        panel.add(ordered, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Non mod. : "), gbc);
        gbc.gridx++;
        panel.add(frozen, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Maj. : "), gbc);
        gbc.gridx++;
        panel.add(uppercase, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Valeur initiale : "), gbc);
        gbc.gridx++;
        panel.add(initValue, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Dérivation : "), gbc);
        gbc.gridx++;
        panel.add(derivedValue, gbc);

/*
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Stéréotypes : "), gbc);
        gbc.gridx++;
        panel.add(stereotypesStringUML, gbc);

 */

        this.add(panel);

    }

    public JTextField getAttributeName() {
        return attributeName;
    }



    protected void changeField(DocumentEvent e) {
        if (attributeName.getDocument() == e.getDocument()) {
            checkAttributeName(true);
        }

    }

    @Override
    protected void changeField(ItemEvent e) {
        Object source = e.getSource();
        System.out.println("Evénement change détecté");
        if(aid  == source){
            if (aid.isSelected()){
                mandatory.setSelected(true);
                mandatory.setEnabled(false);
                list.setSelected(false);
                list.setEnabled(false);
                frozen.setSelected(true);
                frozen.setEnabled(false);
                derivedValue.setText("");
                derivedValue.setEnabled(false);
                initValue.setText("");
                initValue.setEnabled(false);
            } else {
                mandatory.setSelected(false);
                mandatory.setEnabled(true);
                list.setSelected(false);
                list.setEnabled(true);
                frozen.setSelected(false);
                frozen.setEnabled(true);
                derivedValue.setEnabled(true);
                initValue.setEnabled(true);
            }
        }
        if(list  == source) {
            if (list.isEnabled() && list.isSelected()) {
                ordered.setEnabled(true);
                derivedValue.setText("");
                derivedValue.setEnabled(false);
                initValue.setText("");
                initValue.setEnabled(false);
            } else {
                ordered.setEnabled(false);
                derivedValue.setEnabled(true);
                initValue.setEnabled(true);
            }
            if (!list.isSelected()) {
                ordered.setSelected(false);
                derivedValue.setEnabled(true);
                initValue.setEnabled(true);
            }
        }

    }



    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();
        if (source == attributeName) {
            checkAttributeName(true);
        }
        if (source == stereotypesStringUML) {
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }



    protected boolean checkDatas(){
            boolean resultat = checkAttributeName(false);
            //resultat =  checkEntityShortName(false)  && resultat ;
            return resultat;
    }

    private boolean checkAttributeName(boolean unitaire) {
        return super.checkInput(attributeName, unitaire, MCDAttributeService.checkName(attributeName.getText()));
    }


    @Override
    protected void initDatas(MVCCDElement mvccdElement) {
        // L'ordre d'appel est important pour que les initialisations se fassent
        // du général (aid) au particulier (list)

        Preferences preferences = PreferencesManager.instance().preferences();
        attributeName.setText("");
        aid.setSelected(false);
        mandatory.setSelected(false);
        list.setSelected(false);
        ordered.setSelected(false);
        frozen.setSelected(false);
        uppercase.setSelected(false);
        derivedValue.setText("");
        initValue.setText("");

        stereotypesStringUML.setText("");

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        // L'ordre d'appel est important pour que les initialisations se fassent
        // du général (aid) au particulier (list)

        MCDAttribute mcdAttribute= (MCDAttribute) mvccdElement;
        attributeName.setText(mcdAttribute.getName()) ;
        mandatory.setSelected(mcdAttribute.isMandatory());
        aid.setSelected(mcdAttribute.isAid());
        list.setSelected(mcdAttribute.isList());
        frozen.setSelected(mcdAttribute.isFrozen());
        ordered.setSelected(mcdAttribute.isOrdered());
        uppercase.setSelected(mcdAttribute.isUppercase());
        derivedValue.setText(mcdAttribute.getDerivedValue());
        initValue.setText(mcdAttribute.getInitValue());

        ArrayList<Stereotype> stereotypes =  mcdAttribute.getToStereotypes();
        ArrayList<String> stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);
        stereotypesStringUML.setText(UtilDivers.ArrayStringToString(stereotypesUMLNames, ""));

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MCDAttribute mcdAttribute= (MCDAttribute) mvccdElement;
        if (attributeName.checkIfUpdated()){
            mcdAttribute.setName(attributeName.getText());
        }

        if (mandatory.checkIfUpdated()){
            mcdAttribute.setMandatory(mandatory.isSelected());
        }

        if (aid.checkIfUpdated()){
            mcdAttribute.setAid(aid.isSelected());
        }

        if (list.checkIfUpdated()){
            mcdAttribute.setList(list.isSelected());
        }

        if (frozen.checkIfUpdated()){
            mcdAttribute.setFrozen(frozen.isSelected());
        }

        if (ordered.checkIfUpdated()){
            mcdAttribute.setOrdered(ordered.isSelected());
        }

        if (uppercase.checkIfUpdated()){
            mcdAttribute.setUppercase(uppercase.isSelected());
        }

        if (derivedValue.checkIfUpdated()){
            mcdAttribute.setDerivedValue(derivedValue.getText());
        }

        if (initValue.checkIfUpdated()){
            mcdAttribute.setInitValue(initValue.getText());
        }


        if (stereotypesStringUML.checkIfUpdated()){
            ArrayList<String> stereotypesString =
            StereotypeService.getArrayListFromNamesStringTagged(stereotypesStringUML.getText(), false);
            ArrayList<Stereotype> stereotypes =
                    StereotypesManager.instance().stereotypes().getStereotypeByClassNameAndNames(
                            MCDAttribute.class.getName(),stereotypesString );
            mcdAttribute.setFromStereotypes(stereotypes);
        }

    }



    @Override
    public boolean checkDatasPreSave() {
        boolean resultat = checkAttributeName(false);
        return resultat;
    }

    private void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
        //entityJournal.setEnabled(preferences.getMCD_JOURNALIZATION_EXCEPTION());
    }

   /*
    public void mouseClicked(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (source == stereotypesStringUML) {
            AttributeStereotypesEditor sterotypesEditor =  new AttributeStereotypesEditor (stereotypesStringUML, attributeName.getText());
        }
    }
    */


}
