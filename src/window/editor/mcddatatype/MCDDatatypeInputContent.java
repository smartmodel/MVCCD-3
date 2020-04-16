package window.editor.mcddatatype;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import utilities.UtilDivers;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class MCDDatatypeInputContent extends PanelInputContent{

    //private JPanel panel = new JPanel();
    private STextField mcdDatatypeName = new STextField(this);
    private SCheckBox abstrait = new SCheckBox(this);
    private STextField lienProg = new STextField(this);

    private JPanel panelSize = new JPanel ();
    private STextField sizeMandatory = new STextField(this);
    private STextField sizeMandatoryInheritFrom = new STextField(this);
    private STextField sizeDefault = new STextField(this);
    private STextField sizeDefaultInheritFrom = new STextField(this);
    private STextField sizeMin = new STextField(this);
    private STextField sizeMinInheritFrom = new STextField(this);
    private STextField sizeMax = new STextField(this);
    private STextField sizeMaxInheritFrom = new STextField(this);


    private JPanel panelScale = new JPanel ();
    private STextField scaleMandatory = new STextField(this);
    private STextField scaleMandatoryInheritFrom = new STextField(this);
    private STextField scaleDefault = new STextField(this);
    private STextField scaleDefaultInheritFrom = new STextField(this);
    private STextField scaleMin = new STextField(this);
    private STextField scaleMinInheritFrom = new STextField(this);
    private STextField scaleMax = new STextField(this);
    private STextField scaleMaxInheritFrom = new STextField(this);


    private int showNull = UtilDivers.NULL;


    public MCDDatatypeInputContent(MCDDatatypeInput entityInput)     {
        super(entityInput);
        /*
        entityInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
        enabledContent();

         */
     }




    protected void createContentCustom() {

        mcdDatatypeName.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        mcdDatatypeName.setToolTipText("Nom de type");

        abstrait.setToolTipText("Type de donnée abstrait ou concret");

        lienProg.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        lienProg.setToolTipText("Lien de programmation");

        createSize();
        createScale();

        super.getsComponents().add(mcdDatatypeName);
        super.getsComponents().add(abstrait);
        super.getsComponents().add(lienProg);
        super.getsComponents().add(sizeMandatory);
        super.getsComponents().add(sizeMandatoryInheritFrom);
        super.getsComponents().add(sizeDefault);
        super.getsComponents().add(sizeDefaultInheritFrom);
        super.getsComponents().add(sizeMin);
        super.getsComponents().add(sizeMinInheritFrom);
        super.getsComponents().add(sizeMax);
        super.getsComponents().add(sizeMaxInheritFrom);
        super.getsComponents().add(scaleMandatory);
        super.getsComponents().add(scaleMandatoryInheritFrom);
        super.getsComponents().add(scaleDefault);
        super.getsComponents().add(scaleDefaultInheritFrom);
        super.getsComponents().add(scaleMin);
        super.getsComponents().add(scaleMinInheritFrom);
        super.getsComponents().add(scaleMax);
        super.getsComponents().add(scaleMaxInheritFrom);

        panelInputContentCustom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panelInputContentCustom.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(mcdDatatypeName, gbc);

        gbc.gridx++;
        panelInputContentCustom.add(new JLabel("Abstrait : "), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(abstrait, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Lien prog. :"),gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(lienProg, gbc);

        Border border = BorderFactory.createLineBorder(Color.black);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;

        createPanelSize(border);
        panelInputContentCustom.add(panelSize, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelScale(border);

        panelInputContentCustom.add(panelScale, gbc);
        this.add(panelInputContentCustom);

    }

    private void createPanelScale(Border border) {

        TitledBorder panelScaleBorder = BorderFactory.createTitledBorder(border, "Scale");
        panelScale.setBorder(panelScaleBorder);

        panelScale.setLayout(new GridBagLayout());
        GridBagConstraints gbcScale = new GridBagConstraints();
        gbcScale.anchor= GridBagConstraints.NORTHWEST;
        gbcScale.insets = new Insets(10,10,0,0);


        gbcScale.gridx = 0;
        gbcScale.gridy = 0;
        gbcScale.gridwidth = 1;
        gbcScale.gridheight = 1;
        panelScale.add(new JLabel("Obligatoire : "), gbcScale);
        gbcScale.gridx = 1;
        panelScale.add(scaleMandatory, gbcScale);

        gbcScale.gridx++;
        panelScale.add(new JLabel("Hérité : "), gbcScale);
        gbcScale.gridx++;
        panelScale.add(scaleMandatoryInheritFrom, gbcScale);

        gbcScale.gridx = 0;
        gbcScale.gridy++;
        panelScale.add(new JLabel("Défaut :"),gbcScale);
        gbcScale.gridx = 1;
        panelScale.add(scaleDefault, gbcScale);

        gbcScale.gridx++;
        panelScale.add(new JLabel("Hérité : "), gbcScale);
        gbcScale.gridx++;
        panelScale.add(scaleDefaultInheritFrom, gbcScale);

        gbcScale.gridx = 0;
        gbcScale.gridy++;
        panelScale.add(new JLabel("Minimum :"),gbcScale);
        gbcScale.gridx = 1;
        panelScale.add(scaleMin, gbcScale);

        gbcScale.gridx++;
        panelScale.add(new JLabel("Hérité : "), gbcScale);
        gbcScale.gridx++;
        panelScale.add(scaleMinInheritFrom, gbcScale);

        gbcScale.gridx = 0;
        gbcScale.gridy++;
        panelScale.add(new JLabel("Maximum :"),gbcScale);
        gbcScale.gridx = 1;
        panelScale.add(scaleMax, gbcScale);

        gbcScale.gridx++;
        panelScale.add(new JLabel("Hérité : "), gbcScale);
        gbcScale.gridx++;
        panelScale.add(scaleMaxInheritFrom, gbcScale);


    }

    private void createPanelSize(Border border) {
        TitledBorder panelSizeBorder = BorderFactory.createTitledBorder(border, "Size");
        panelSize.setBorder(panelSizeBorder);

        panelSize.setLayout(new GridBagLayout());
        GridBagConstraints gbcSize = new GridBagConstraints();
        gbcSize.anchor= GridBagConstraints.NORTHWEST;
        gbcSize.insets = new Insets(10,10,0,0);

        gbcSize.gridx = 0;
        gbcSize.gridy = 0;
        gbcSize.gridwidth = 1;
        gbcSize.gridheight = 1;
        panelSize.add(new JLabel("Obligatoire : "), gbcSize);
        gbcSize.gridx = 1;
        panelSize.add(sizeMandatory, gbcSize);

        gbcSize.gridx++;
        panelSize.add(new JLabel("Hérité : "), gbcSize);
        gbcSize.gridx++;
        panelSize.add(sizeMandatoryInheritFrom, gbcSize);

        gbcSize.gridx = 0;
        gbcSize.gridy++;
        panelSize.add(new JLabel("Défaut :"),gbcSize);
        gbcSize.gridx = 1;
        panelSize.add(sizeDefault, gbcSize);

        gbcSize.gridx++;
        panelSize.add(new JLabel("Hérité : "), gbcSize);
        gbcSize.gridx++;
        panelSize.add(sizeDefaultInheritFrom, gbcSize);

        gbcSize.gridx = 0;
        gbcSize.gridy++;
        panelSize.add(new JLabel("Minimum :"),gbcSize);
        gbcSize.gridx = 1;
        panelSize.add(sizeMin, gbcSize);

        gbcSize.gridx++;
        panelSize.add(new JLabel("Hérité : "), gbcSize);
        gbcSize.gridx++;
        panelSize.add(sizeMinInheritFrom, gbcSize);

        gbcSize.gridx = 0;
        gbcSize.gridy++;
        panelSize.add(new JLabel("Maximum :"),gbcSize);
        gbcSize.gridx = 1;
        panelSize.add(sizeMax, gbcSize);

        gbcSize.gridx++;
        panelSize.add(new JLabel("Hérité : "), gbcSize);
        gbcSize.gridx++;
        panelSize.add(sizeMaxInheritFrom, gbcSize);

    }

    private void createScale() {

        scaleMandatory.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleMandatory.setToolTipText("Scale obligatoire");
        scaleMandatoryInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleMandatoryInheritFrom.setToolTipText("Hérité de");

        scaleDefault.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleDefault.setToolTipText("Valeur par défaut");
        scaleDefaultInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleDefaultInheritFrom.setToolTipText("Hérité de");

        scaleMin.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleMin.setToolTipText("Scale minimale");
        scaleMinInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleMinInheritFrom.setToolTipText("Hérité de");

        scaleMax.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleMax.setToolTipText("Scale maximale");
        scaleMaxInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        scaleMaxInheritFrom.setToolTipText("Hérité de");
    }

    private void createSize() {

        sizeMandatory.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeMandatory.setToolTipText("Size obligatoire");
        sizeMandatoryInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeMandatoryInheritFrom.setToolTipText("Hérité de");

        sizeDefault.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeDefault.setToolTipText("Valeur par défaut");
        sizeDefaultInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeDefaultInheritFrom.setToolTipText("Hérité de");

        sizeMin.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeMin.setToolTipText("Size minimale");
        sizeMinInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeMinInheritFrom.setToolTipText("Hérité de");

        sizeMax.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeMax.setToolTipText("Size maximale");
        sizeMaxInheritFrom.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        sizeMaxInheritFrom.setToolTipText("Hérité de");
    }


    protected SComponent changeField(DocumentEvent e) {
        return null;
     }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }


    @Override
    public boolean checkDatasPreSave(boolean unitaire) {

        return true;
    }


    @Override
    protected void enabledContentCustom() {

    }


    protected boolean checkDatas(){
            return true;
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MCDDatatype mcdDatatype = (MCDDatatype) mvccdElement;
        mcdDatatypeName.setText(mcdDatatype.getName()) ;
        abstrait.setSelected(mcdDatatype.isAbstrait());
        lienProg.setText(mcdDatatype.getLienProg());

        sizeMandatory.setText(MDDatatypeService.getSizeMandatoryWithInheritInString(mcdDatatype, showNull));
        sizeMandatoryInheritFrom.setText(MDDatatypeService.getSizeMandatoryFrom(mcdDatatype, showNull));
        sizeDefault.setText(MDDatatypeService.getSizeDefaultWithInheritInString(mcdDatatype, showNull));
        sizeDefaultInheritFrom.setText(MDDatatypeService.getSizeDefaultFrom(mcdDatatype, showNull));
        sizeMin.setText(MDDatatypeService.getSizeMinWithInheritInString(mcdDatatype, showNull));
        sizeMinInheritFrom.setText(MDDatatypeService.getSizeMinFrom(mcdDatatype, showNull));
        sizeMax.setText(MDDatatypeService.getSizeMaxWithInheritInString(mcdDatatype, showNull));
        sizeMaxInheritFrom.setText(MDDatatypeService.getSizeMaxFrom(mcdDatatype, showNull));

        scaleMandatory.setText(MDDatatypeService.getScaleMandatoryWithInheritInString(mcdDatatype, showNull));
        scaleMandatoryInheritFrom.setText(MDDatatypeService.getScaleMandatoryFrom(mcdDatatype, showNull));
        scaleDefault.setText(MDDatatypeService.getScaleDefaultWithInheritInString(mcdDatatype, showNull));
        scaleDefaultInheritFrom.setText(MDDatatypeService.getScaleDefaultFrom(mcdDatatype, showNull));
        scaleMin.setText(MDDatatypeService.getScaleMinWithInheritInString(mcdDatatype, showNull));
        scaleMinInheritFrom.setText(MDDatatypeService.getScaleMinFrom(mcdDatatype, showNull));
        scaleMax.setText(MDDatatypeService.getScaleMaxWithInheritInString(mcdDatatype, showNull));
        scaleMaxInheritFrom.setText(MDDatatypeService.getScaleMaxFrom(mcdDatatype, showNull));

    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
    }


    private void enabledContent() {
    }
}
