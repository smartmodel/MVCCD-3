package window.editor.mdr.column;

import constraints.ConstraintService;
import constraints.ConstraintsManager;
import datatypes.MDDatatypeService;
import main.MVCCDElement;
import mdr.MDRColumn;
import mldr.MLDRColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import preferences.Preferences;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MDRColumnInputContent extends PanelInputContentIdMDR {

    protected JPanel panelDatatype = new JPanel ();

    private JLabel labelDatatypeName;
    private STextField fieldDatatypeName;
    private STextField fieldDatatypeConstraint;
    private JLabel labelDatatypeSize;
    private STextField fieldDatatypeSize;
    private JLabel labelDatatypeScale;
    private STextField fieldDatatypeScale;

    private JLabel labelMandatory ;
    private SCheckBox fieldMandatory ;
    private JLabel labelFrozen ;
    private SCheckBox  fieldFrozen ;
    private JLabel labelUppercase ;
    private SCheckBox fieldUppercase ;
    private JLabel labelInitValue ;
    private STextField fieldInitValue ;
    private JLabel labelDerivedValue ;
    private STextField fieldDerivedValue ;

    private JLabel labelReferencePK; // pour les colonnes FK
    private STextField fieldReferencePK ;


    public MDRColumnInputContent(MDRColumnInput MDRColumnInput)     {
        super(MDRColumnInput);
    }

    public MDRColumnInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        labelDatatypeName = new JLabel("Type de données : ");
        fieldDatatypeName = new STextField(this, labelDatatypeName);
        fieldDatatypeName.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));

        fieldDatatypeConstraint = new STextField(this);
        fieldDatatypeConstraint.setPreferredSize((new Dimension(150, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDatatypeConstraint.setToolTipText("Contrainte...");

        labelDatatypeSize= new JLabel("Taille : ");
        fieldDatatypeSize = new STextField(this, labelDatatypeSize);
        fieldDatatypeSize.setPreferredSize((new Dimension(100, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDatatypeSize.setToolTipText("Taille...");

        labelDatatypeScale = new JLabel("Décimales : ");
        fieldDatatypeScale = new STextField(this, labelDatatypeScale);
        fieldDatatypeScale.setPreferredSize((new Dimension(30, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDatatypeScale.setToolTipText("Décimales...");

        labelMandatory = new JLabel("Obligatoire : ");
        fieldMandatory = new SCheckBox(this, labelMandatory);
        fieldMandatory.setToolTipText("Obligatoire...");

        labelFrozen = new JLabel("Non modif. : ");
        fieldFrozen = new SCheckBox(this, labelFrozen);
        fieldFrozen.setToolTipText("Non modif. ...");

        labelUppercase = new JLabel("Maj. : ");
        fieldUppercase = new SCheckBox(this, labelUppercase);
        fieldUppercase.setToolTipText("Maj. ...");

        labelInitValue = new JLabel("Valeur initiale : ");
        fieldInitValue = new STextField(this, labelInitValue);
        fieldInitValue.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldInitValue.setToolTipText("Valeur initiale...");

        labelDerivedValue = new JLabel("Valeur dérivée : ");
        fieldDerivedValue= new STextField(this, labelDerivedValue);
        fieldDerivedValue.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldDerivedValue.setToolTipText("Valeur dérivée...");

        labelReferencePK = new JLabel("Référence PK : ");
        fieldReferencePK= new STextField(this, labelReferencePK);
        fieldReferencePK.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldReferencePK.setToolTipText("Référence de colonne PK pour une colonne FK");


        super.getSComponents().add(fieldDatatypeName);
        super.getSComponents().add(fieldDatatypeConstraint);
        super.getSComponents().add(fieldDatatypeScale);
        super.getSComponents().add(fieldDatatypeSize);
        super.getSComponents().add(fieldMandatory);
        super.getSComponents().add(fieldFrozen);
        super.getSComponents().add(fieldUppercase);
        super.getSComponents().add(fieldInitValue);
        super.getSComponents().add(fieldDerivedValue);
        super.getSComponents().add(fieldReferencePK);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        /*
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelDatatypeName,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldDatatypeName, gbc);
*/

        gbc.gridx = 0;
        gbc.gridy++;
        createPanelDatatype();
        panelInputContentCustom.add(panelDatatype, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelMandatory, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldMandatory, gbc);

        gbc.gridx++;
        panelInputContentCustom.add(labelFrozen, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldFrozen, gbc);

        gbc.gridx++;
        panelInputContentCustom.add(labelUppercase, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldUppercase, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelInitValue, gbc);
        gbc.gridx++;
        gbc.gridwidth = 5;
        panelInputContentCustom.add(fieldInitValue, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelDerivedValue, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldDerivedValue, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelReferencePK, gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldReferencePK, gbc);

        this.add(panelInputContentCustom);
    }


    private void createPanelDatatype() {
        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder panelDataypeBorder = BorderFactory.createTitledBorder(border, "Type de données");
        panelDatatype.setBorder(panelDataypeBorder);

        panelDatatype.setLayout(new GridBagLayout());
        GridBagConstraints gbcDT = new GridBagConstraints();
        gbcDT.anchor = GridBagConstraints.NORTHWEST;
        gbcDT.insets = new Insets(10, 10, 0, 0);

        gbcDT.gridx = 0;
        gbcDT.gridy = 0;
        gbcDT.gridwidth = 1;
        gbcDT.gridheight = 1;

        panelDatatype.add(fieldDatatypeName, gbcDT);

        gbcDT.gridx++;
        panelDatatype.add(fieldDatatypeConstraint, gbcDT);

        gbcDT.gridx = 0;
        gbcDT.gridy++;
        panelDatatype.add(labelDatatypeSize, gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(fieldDatatypeSize, gbcDT);


        gbcDT.gridx++;
        panelDatatype.add(labelDatatypeScale, gbcDT);
        gbcDT.gridx++;
        panelDatatype.add(fieldDatatypeScale, gbcDT);

    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRColumn mdrColumn= (MDRColumn) mvccdElementCrt;
        super.loadDatas(mdrColumn);

        String datatypeName = "";
        if (mdrColumn.getDatatypeLienProg() != null) {
            if (mdrColumn instanceof MLDRColumn){
                datatypeName = MDDatatypeService.convertMLDRLienProgToName(mdrColumn.getDatatypeLienProg());
            }
            if (mdrColumn instanceof MPDRColumn){
                MPDRColumn mpdrColumn = (MPDRColumn) mdrColumn;
                MPDRTable mpdrTable = mpdrColumn.getMPDRTableAccueil();
                datatypeName = MDDatatypeService.convertMPDRLienProgToName(mpdrTable.getMPDRModelParent().getDb(), mdrColumn.getDatatypeLienProg());
            }
        }
        fieldDatatypeName.setText(datatypeName);


        String datatypeConstraintName = "";
        if (mdrColumn.getDatatypeLienProg() != null) {
            datatypeConstraintName = ConstraintsManager.instance().constraints().convertLienProgToName(
                    MDRColumn.class.getName(), mdrColumn.getDatatypeConstraintLienProg());
            datatypeConstraintName = ConstraintService.getUMLName(datatypeConstraintName);
        }
        fieldDatatypeConstraint.setText(datatypeConstraintName);


        String datatypeSize = "";
        if (mdrColumn.getSize() != null){
            datatypeSize = mdrColumn.getSize().toString();
        }
        fieldDatatypeSize.setText((datatypeSize));

        String datatypeScale = "";
        if (mdrColumn.getScale() != null){
            datatypeScale = mdrColumn.getScale().toString();
        }
        fieldDatatypeScale.setText((datatypeScale));

        fieldMandatory.setSelected(mdrColumn.isMandatory());

        fieldFrozen.setSelected(mdrColumn.isFrozen());

        fieldUppercase.setSelected(mdrColumn.isUppercase());

        String initValue = "";
        if (mdrColumn.getInitValue() != null){
            initValue = mdrColumn.getInitValue();
        }
        fieldInitValue.setText((initValue));

        String derivedValue = "";
        if (mdrColumn.getDerivedValue() != null){
            derivedValue = mdrColumn.getDerivedValue();
        }
        fieldDerivedValue.setText((derivedValue));

        String referencePK = "";
        if (mdrColumn.getMDRColumnPK() != null){
            referencePK = mdrColumn.getMDRColumnPK().getName();
        }
        fieldReferencePK.setText((referencePK));

    }



}
