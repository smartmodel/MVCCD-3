package window.editor.mddatatype;

import datatypes.MDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import utilities.UtilDivers;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.STextField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class MDDatatypeInputContent extends PanelInputContent{

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


    public MDDatatypeInputContent(MDDatatypeInput entityInput)     {
        super(entityInput);
     }




    public void createContentCustom() {

        mcdDatatypeName.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        mcdDatatypeName.setToolTipText("Nom de type");

        abstrait.setToolTipText("Type de donnée abstrait ou concret");

        lienProg.setPreferredSize((new Dimension(150,Preferences.EDITOR_FIELD_HEIGHT)));
        lienProg.setToolTipText("Lien de programmation");

        createSize();
        createScale();

        super.getSComponents().add(mcdDatatypeName);
        super.getSComponents().add(abstrait);
        super.getSComponents().add(lienProg);
        super.getSComponents().add(sizeMandatory);
        super.getSComponents().add(sizeMandatoryInheritFrom);
        super.getSComponents().add(sizeDefault);
        super.getSComponents().add(sizeDefaultInheritFrom);
        super.getSComponents().add(sizeMin);
        super.getSComponents().add(sizeMinInheritFrom);
        super.getSComponents().add(sizeMax);
        super.getSComponents().add(sizeMaxInheritFrom);
        super.getSComponents().add(scaleMandatory);
        super.getSComponents().add(scaleMandatoryInheritFrom);
        super.getSComponents().add(scaleDefault);
        super.getSComponents().add(scaleDefaultInheritFrom);
        super.getSComponents().add(scaleMin);
        super.getSComponents().add(scaleMinInheritFrom);
        super.getSComponents().add(scaleMax);
        super.getSComponents().add(scaleMaxInheritFrom);

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


    protected boolean changeField(DocumentEvent e) {
        return true;
     }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        MDDatatype mdDatatype = (MDDatatype) mvccdElement;
        mcdDatatypeName.setText(mdDatatype.getName()) ;
        abstrait.setSelected(mdDatatype.isAbstrait());
        lienProg.setText(mdDatatype.getLienProg());

        sizeMandatory.setText(MDDatatypeService.getSizeMandatoryWithInheritInString(mdDatatype, showNull));
        sizeMandatoryInheritFrom.setText(MDDatatypeService.getSizeMandatoryFrom(mdDatatype, showNull));
        sizeDefault.setText(MDDatatypeService.getSizeDefaultWithInheritInString(mdDatatype, showNull));
        sizeDefaultInheritFrom.setText(MDDatatypeService.getSizeDefaultFrom(mdDatatype, showNull));
        sizeMin.setText(MDDatatypeService.getSizeMinWithInheritInString(mdDatatype, showNull));
        sizeMinInheritFrom.setText(MDDatatypeService.getSizeMinFrom(mdDatatype, showNull));
        sizeMax.setText(MDDatatypeService.getSizeMaxWithInheritInString(mdDatatype, showNull));
        sizeMaxInheritFrom.setText(MDDatatypeService.getSizeMaxFrom(mdDatatype, showNull));

        scaleMandatory.setText(MDDatatypeService.getScaleMandatoryWithInheritInString(mdDatatype, showNull));
        scaleMandatoryInheritFrom.setText(MDDatatypeService.getScaleMandatoryFrom(mdDatatype, showNull));
        scaleDefault.setText(MDDatatypeService.getScaleDefaultWithInheritInString(mdDatatype, showNull));
        scaleDefaultInheritFrom.setText(MDDatatypeService.getScaleDefaultFrom(mdDatatype, showNull));
        scaleMin.setText(MDDatatypeService.getScaleMinWithInheritInString(mdDatatype, showNull));
        scaleMinInheritFrom.setText(MDDatatypeService.getScaleMinFrom(mdDatatype, showNull));
        scaleMax.setText(MDDatatypeService.getScaleMaxWithInheritInString(mdDatatype, showNull));
        scaleMaxInheritFrom.setText(MDDatatypeService.getScaleMaxFrom(mdDatatype, showNull));

    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


    @Override
    protected void enabledContent() {

    }
}
