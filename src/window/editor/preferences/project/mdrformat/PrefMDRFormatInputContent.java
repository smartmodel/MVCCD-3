package window.editor.preferences.project.mdrformat;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMDRFormatInputContent extends PanelInputContent {

    
    private JPanel panelConstraintsName = new JPanel ();
    private JLabel labelPKName ;
    private STextField fieldPKName;
    private JLabel labelPKNNName ;
    private STextField fieldPKNNName;
    private JLabel labelPKNNNameIndice ;
    private STextField fieldPKNNNameIndice;


    private JLabel labelFKName = new JLabel();
    private STextField fieldFKName;
    private JLabel labelFKWithoutRoleName = new JLabel();
    private STextField fieldFKWithoutRoleName;

    private JPanel panelColumnsName = new JPanel ();
    private JLabel labelColumnPKName ;
    private STextField fieldColumnPKName;
    private JLabel labelColumnAttrName ;
    private STextField fieldColumnAttrName;
    private JLabel labelColumnAttrShortName ;
    private STextField fieldColumnAttrShortName;
    private JLabel labelColumnFKName ;
    private STextField fieldColumnFKName;
    private JLabel labelColumnFKNameOneAncestor ;
    private STextField fieldColumnFKNameOneAncestor;

    private JPanel panelTablesName = new JPanel ();
    private JLabel labelTableName ;
    private STextField fieldTableName;
    private JLabel labelTableNNName ;
    private STextField fieldTableNNName;
    private JLabel labelTableNNNameIndice ;
    private STextField fieldTableNNNameIndice;

    private JPanel panelSeparators = new JPanel ();
    private JLabel labelPathSep ;
    private SCheckBox fieldPathSep;
    private JLabel labelTableSep ;
    private SCheckBox fieldTableSep;
    private JLabel labelRoleSep ;
    private SCheckBox fieldRoleSep;
    private JLabel labelFKIndSep ;
    private SCheckBox fieldFKIndSep;
    private JLabel labelPEASep ;
    private SCheckBox fieldPEASep;


    private JPanel panelMarkers = new JPanel ();
    private JLabel labelGeneralize ;
    private STextField fieldGeneralize;
    private JPanel panelMarkersColumns = new JPanel ();
    private JLabel labelColumnDerived;
    private STextField fieldColumnDerived;


    private int sizeSep = 20 ;
    private int sizeMarker = 60 ;

    public PrefMDRFormatInputContent(PrefMDRFormatInput prefMDRFormatInput) {
        super(prefMDRFormatInput);
     }

    public void createContentCustom() {


        labelPKName = new JLabel("PK :");
        fieldPKName = new STextField(this, labelPKName);
        fieldPKName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPKName.getDocument().addDocumentListener(this);
        fieldPKName.addFocusListener(this);

        labelFKName = new JLabel("FK :");
        fieldFKName = new STextField(this, labelFKName);
        fieldFKName.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldFKName.getDocument().addDocumentListener(this);
        fieldFKName.addFocusListener(this);

        labelFKWithoutRoleName = new JLabel("FK sans rôle :");
        fieldFKWithoutRoleName = new STextField(this, labelFKWithoutRoleName);
        fieldFKWithoutRoleName.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldFKWithoutRoleName.getDocument().addDocumentListener(this);
        fieldFKWithoutRoleName.addFocusListener(this);

        labelColumnPKName = new JLabel("PK depuis entité:");
        fieldColumnPKName = new STextField(this, labelColumnPKName);
        fieldColumnPKName.setPreferredSize((new Dimension(150, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldColumnPKName.getDocument().addDocumentListener(this);
        fieldColumnPKName.addFocusListener(this);
        
        labelPKNNName = new JLabel("Depuis ass. n:n :");
        fieldPKNNName = new STextField(this, labelPKNNName);
        fieldPKNNName.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPKNNName.getDocument().addDocumentListener(this);
        fieldPKNNName.addFocusListener(this);

        labelPKNNNameIndice = new JLabel("Depuis ass. n:n (indicée) :");
        fieldPKNNNameIndice = new STextField(this, labelPKNNNameIndice);
        fieldPKNNNameIndice.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPKNNNameIndice.getDocument().addDocumentListener(this);
        fieldPKNNNameIndice.addFocusListener(this);

        labelColumnAttrName = new JLabel("Attribut :");
        fieldColumnAttrName = new STextField(this, labelColumnAttrName);
        fieldColumnAttrName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldColumnAttrName.getDocument().addDocumentListener(this);
        fieldColumnAttrName.addFocusListener(this);

        labelColumnAttrShortName = new JLabel("Attribut court:");
        fieldColumnAttrShortName = new STextField(this, labelColumnAttrShortName);
        fieldColumnAttrShortName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldColumnAttrShortName.getDocument().addDocumentListener(this);
        fieldColumnAttrShortName.addFocusListener(this);

        labelColumnFKName = new JLabel("FK :");
        fieldColumnFKName = new STextField(this, labelColumnFKName);
        fieldColumnFKName.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldColumnFKName.getDocument().addDocumentListener(this);
        fieldColumnFKName.addFocusListener(this);

        labelColumnFKNameOneAncestor = new JLabel("FK (1 ancêtre):");
        fieldColumnFKNameOneAncestor = new STextField(this, labelColumnFKNameOneAncestor);
        fieldColumnFKNameOneAncestor.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldColumnFKNameOneAncestor.getDocument().addDocumentListener(this);
        fieldColumnFKNameOneAncestor.addFocusListener(this);

        labelTableName = new JLabel("Depuis entité :");
        fieldTableName = new STextField(this, labelTableName);
        fieldTableName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldTableName.getDocument().addDocumentListener(this);
        fieldTableName.addFocusListener(this);

        labelTableNNName = new JLabel("Depuis ass. n:n :");
        fieldTableNNName = new STextField(this, labelTableNNName);
        fieldTableNNName.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldTableNNName.getDocument().addDocumentListener(this);
        fieldTableNNName.addFocusListener(this);

        labelTableNNNameIndice = new JLabel("Depuis ass. n:n (indicée) :");
        fieldTableNNNameIndice = new STextField(this, labelTableNNNameIndice);
        fieldTableNNNameIndice.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldTableNNNameIndice.getDocument().addDocumentListener(this);
        fieldTableNNNameIndice.addFocusListener(this);

        labelPathSep = new JLabel("Path :");
        fieldPathSep = new SCheckBox(this, labelPathSep);
        fieldPathSep.addItemListener(this);
        fieldPathSep.addFocusListener(this);

        labelTableSep = new JLabel("Table :");
        fieldTableSep = new SCheckBox(this, labelTableSep);
        fieldTableSep.addItemListener(this);
        fieldTableSep.addFocusListener(this);

        labelRoleSep = new JLabel("Rôle :");
        fieldRoleSep = new SCheckBox(this, labelRoleSep);
        fieldRoleSep.addItemListener(this);
        fieldRoleSep.addFocusListener(this);

        labelFKIndSep = new JLabel("FK indicée :");
        fieldFKIndSep = new SCheckBox(this, labelFKIndSep);
        fieldFKIndSep.addItemListener(this);
        fieldFKIndSep.addFocusListener(this);

        labelPEASep = new JLabel("PEA :");
        fieldPEASep = new SCheckBox(this, labelPEASep);
        fieldPEASep.addItemListener(this);
        fieldPEASep.addFocusListener(this);

        labelGeneralize = new JLabel("Généralise :");
        fieldGeneralize = new STextField(this, labelGeneralize);
        fieldGeneralize.setPreferredSize((new Dimension(sizeMarker, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldGeneralize.getDocument().addDocumentListener(this);
        fieldGeneralize.addFocusListener(this);

        labelColumnDerived = new JLabel("Dérivée :");
        fieldColumnDerived = new STextField(this, labelColumnDerived);
        fieldColumnDerived.setPreferredSize((new Dimension(sizeMarker, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldColumnDerived.getDocument().addDocumentListener(this);
        fieldColumnDerived.addFocusListener(this);


        super.getSComponents().add(fieldPKName);
        super.getSComponents().add(fieldPKNNName);
        super.getSComponents().add(fieldPKNNNameIndice);
        super.getSComponents().add(fieldFKName);
        super.getSComponents().add(fieldFKWithoutRoleName);
        super.getSComponents().add(fieldColumnPKName);
        super.getSComponents().add(fieldColumnAttrName);
        super.getSComponents().add(fieldColumnAttrShortName);
        super.getSComponents().add(fieldColumnFKName);
        super.getSComponents().add(fieldColumnFKNameOneAncestor);
        super.getSComponents().add(fieldTableName);
        super.getSComponents().add(fieldTableNNName);
        super.getSComponents().add(fieldTableNNNameIndice);
        super.getSComponents().add(fieldPathSep);
        super.getSComponents().add(fieldRoleSep);
        super.getSComponents().add(fieldFKIndSep);
        super.getSComponents().add(fieldPEASep);
        super.getSComponents().add(fieldGeneralize);
        super.getSComponents().add(fieldColumnDerived);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelConstraintsName();
        createPanelColumnsName();
        createPanelTablesName();
        createPanelSeparators();
        createPanelMarkers();

        panelInputContentCustom.add(panelTablesName, gbc);
        gbc.gridy++;
        panelInputContentCustom.add(panelColumnsName, gbc);
        gbc.gridy++;
        panelInputContentCustom.add(panelConstraintsName, gbc);
        gbc.gridy++;
        panelInputContentCustom.add(panelSeparators, gbc);
        gbc.gridy++;
        panelInputContentCustom.add(panelMarkers, gbc);

    }



    private void createPanelTablesName() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelTablesName,
                "Nom des tables");

        panelTablesName.add(labelTableName, gbcA);
        gbcA.gridx++ ;
        panelTablesName.add(fieldTableName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelTablesName.add(labelTableNNName, gbcA);
        gbcA.gridx++ ;
        panelTablesName.add(fieldTableNNName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelTablesName.add(labelTableNNNameIndice, gbcA);
        gbcA.gridx++ ;
        panelTablesName.add(fieldTableNNNameIndice, gbcA);
    }

    private void createPanelColumnsName() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelColumnsName,
                "Nom des colonnes");


        panelColumnsName.add(labelColumnPKName, gbcA);
        gbcA.gridx++ ;
        panelColumnsName.add(fieldColumnPKName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelColumnsName.add(labelColumnAttrName, gbcA);
        gbcA.gridx++ ;
        panelColumnsName.add(fieldColumnAttrName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelColumnsName.add(labelColumnAttrShortName, gbcA);
        gbcA.gridx++ ;
        panelColumnsName.add(fieldColumnAttrShortName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelColumnsName.add(labelColumnFKName, gbcA);
        gbcA.gridx++ ;
        panelColumnsName.add(fieldColumnFKName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelColumnsName.add(labelColumnFKNameOneAncestor, gbcA);
        gbcA.gridx++ ;
        panelColumnsName.add(fieldColumnFKNameOneAncestor, gbcA);

    }

    private void createPanelConstraintsName() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelConstraintsName,
                "Nom des contraintes");

        panelConstraintsName.add(labelPKName, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldPKName, gbcA);


        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelConstraintsName.add(labelPKNNName, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldPKNNName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelConstraintsName.add(labelPKNNNameIndice, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldPKNNNameIndice, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelConstraintsName.add(labelFKName, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldFKName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelConstraintsName.add(labelFKWithoutRoleName, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldFKWithoutRoleName, gbcA);
    }

    private void createPanelSeparators() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelSeparators,
                "Séparateurs");

        panelSeparators.add(labelPathSep, gbcA);
        gbcA.gridx++ ;
        panelSeparators.add(fieldPathSep, gbcA);

        gbcA.gridx++ ;
        panelSeparators.add(labelTableSep, gbcA);
        gbcA.gridx++ ;
        panelSeparators.add(fieldTableSep, gbcA);

        gbcA.gridx++ ;
        panelSeparators.add(labelRoleSep, gbcA);
        gbcA.gridx++ ;
        panelSeparators.add(fieldRoleSep, gbcA);

        gbcA.gridx++ ;
        panelSeparators.add(labelFKIndSep, gbcA);
        gbcA.gridx++ ;
        panelSeparators.add(fieldFKIndSep, gbcA);

        gbcA.gridx++ ;
        panelSeparators.add(labelPEASep, gbcA);
        gbcA.gridx++ ;
        panelSeparators.add(fieldPEASep, gbcA);
    }


    private void createPanelMarkers() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelMarkers,
                "Marqueurs");

        createPanelMarkersColumns();

        panelMarkers.add(panelMarkersColumns, gbcA);
        gbcA.gridy++;

        gbcA.gridx = 0;
        panelMarkers.add(labelGeneralize, gbcA);
        gbcA.gridx++ ;
        panelMarkers.add(fieldGeneralize, gbcA);


    }

    private void createPanelMarkersColumns() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelMarkersColumns,
                "Colonnes");

        panelMarkersColumns.add(labelColumnDerived, gbcA);
        gbcA.gridx++ ;
        panelMarkersColumns.add(fieldColumnDerived, gbcA);

    }


    @Override
    protected void enabledContent() {

    }


    @Override
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
        Preferences preferences = (Preferences) mvccdElement;
        loadDatasWithSource(mvccdElement, preferences);
        /*
        fieldPKName.setText(preferences.getMDR_PK_NAME_FORMAT());
        fieldFKName.setText(preferences.getMDR_FK_NAME_FORMAT());

         */
    }


    protected void reInitDatas(MVCCDElement mvccdElement){
        Preferences preferences ;
        if (PreferencesManager.instance().getProfilePref() != null) {
            preferences = PreferencesManager.instance().getProfilePref();
        } else {
            preferences = PreferencesManager.instance().getApplicationPref();
        }
        loadDatasWithSource(mvccdElement, preferences);
    }

    public void loadDatasWithSource(MVCCDElement mvccdElement, Preferences preferences) {
        fieldPKName.setText(preferences.getMDR_PK_NAME_FORMAT());
        fieldPKNNName.setText(preferences.getMDR_PK_NN_NAME_FORMAT());
        fieldPKNNNameIndice.setText(preferences.getMDR_PK_NN_NAME_INDICE_FORMAT());
        fieldFKName.setText(preferences.getMDR_FK_NAME_FORMAT());
        fieldFKWithoutRoleName.setText(preferences.getMDR_FK_NAME_WITHOUT_ROLE_FORMAT());
        fieldColumnPKName.setText(preferences.getMDR_COLUMN_PK_NAME_FORMAT());
        fieldColumnAttrName.setText(preferences.getMDR_COLUMN_ATTR_NAME_FORMAT());
        fieldColumnAttrShortName.setText(preferences.getMDR_COLUMN_ATTR_SHORT_NAME_FORMAT());
        fieldColumnFKName.setText(preferences.getMDR_COLUMN_FK_NAME_FORMAT());
        fieldColumnFKNameOneAncestor.setText(preferences.getMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT());
        fieldTableName.setText(preferences.getMDR_TABLE_NAME_FORMAT());
        fieldTableNNName.setText(preferences.getMDR_TABLE_NN_NAME_FORMAT());
        fieldTableNNNameIndice.setText(preferences.getMDR_TABLE_NN_NAME_INDICE_FORMAT());
        fieldPathSep.setSelected(preferences.getMDR_PATH_SEP_FORMAT().equals(Preferences.MDR_SEPARATOR));
        fieldTableSep.setSelected(preferences.getMDR_TABLE_SEP_FORMAT().equals(Preferences.MDR_SEPARATOR));
        fieldRoleSep.setSelected(preferences.getMDR_ROLE_SEP_FORMAT().equals(Preferences.MDR_SEPARATOR));
        fieldFKIndSep.setSelected(preferences.getMDR_FKIND_SEP_FORMAT().equals(Preferences.MDR_SEPARATOR));
        fieldPEASep.setSelected(preferences.getMDR_PEA_SEP_FORMAT().equals(Preferences.MDR_SEPARATOR));
        fieldGeneralize.setText(preferences.getMDR_ROLE_GENERALIZE_MARKER());
        fieldColumnDerived.setText(preferences.getMDR_COLUMN_DERIVED_MARKER());
    }



    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldPKName.checkIfUpdated()){
            preferences.setMDR_PK_NAME_FORMAT(fieldPKName.getText());
        }
        if (fieldPKNNName.checkIfUpdated()){
            preferences.setMDR_PK_NN_NAME_FORMAT(fieldPKNNName.getText());
        }
        if (fieldPKNNNameIndice.checkIfUpdated()){
            preferences.setMDR_PK_NN_NAME_INDICE_FORMAT(fieldPKNNNameIndice.getText());
        }
        if (fieldFKName.checkIfUpdated()){
            preferences.setMDR_FK_NAME_FORMAT(fieldFKName.getText());
        }
        if (fieldFKWithoutRoleName.checkIfUpdated()){
            preferences.setMDR_FK_NAME_WITHOUT_ROLE_FORMAT(fieldFKWithoutRoleName.getText());
        }
        if (fieldColumnPKName.checkIfUpdated()){
            preferences.setMDR_COLUMN_PK_NAME_FORMAT(fieldColumnPKName.getText());
        }
        if (fieldColumnAttrName.checkIfUpdated()){
            preferences.setMDR_COLUMN_ATTR_NAME_FORMAT(fieldColumnAttrName.getText());
        }
        if (fieldColumnAttrShortName.checkIfUpdated()){
            preferences.setMDR_COLUMN_ATTR_SHORT_NAME_FORMAT(fieldColumnAttrShortName.getText());
        }
        if (fieldColumnFKName.checkIfUpdated()){
            preferences.setMDR_COLUMN_FK_NAME_FORMAT(fieldColumnFKName.getText());
        }
        if (fieldColumnFKNameOneAncestor.checkIfUpdated()){
            preferences.setMDR_COLUMN_FK_NAME_ONE_ANCESTOR_FORMAT(fieldColumnFKNameOneAncestor.getText());
        }
        if (fieldTableName.checkIfUpdated()){
            preferences.setMDR_TABLE_NAME_FORMAT(fieldTableName.getText());
        }
        if (fieldTableNNName.checkIfUpdated()){
            preferences.setMDR_TABLE_NN_NAME_FORMAT(fieldTableNNName.getText());
        }
        if (fieldTableNNNameIndice.checkIfUpdated()){
            preferences.setMDR_TABLE_NN_NAME_INDICE_FORMAT(fieldTableNNNameIndice.getText());
        }
        if (fieldPathSep.checkIfUpdated()){
            preferences.setMDR_PATH_SEP_FORMAT(fieldPathSep.isSelected() ? Preferences.MDR_SEPARATOR : "");
        }
        if (fieldTableSep.checkIfUpdated()){
            preferences.setMDR_TABLE_SEP_FORMAT(fieldTableSep.isSelected() ? Preferences.MDR_SEPARATOR : "");
        }
        if (fieldRoleSep.checkIfUpdated()){
            preferences.setMDR_ROLE_SEP_FORMAT(fieldRoleSep.isSelected() ? Preferences.MDR_SEPARATOR : "");
        }
        if (fieldFKIndSep.checkIfUpdated()){
            preferences.setMDR_FKIND_SEP_FORMAT(fieldFKIndSep.isSelected() ? Preferences.MDR_SEPARATOR : "");
        }
        if (fieldPEASep.checkIfUpdated()){
            preferences.setMDR_PEA_SEP_FORMAT(fieldPEASep.isSelected() ? Preferences.MDR_SEPARATOR : "");
        }
        if (fieldGeneralize.checkIfUpdated()){
            preferences.setMDR_ROLE_GENERALIZE_MARKER(fieldGeneralize.getText());
        }
        if (fieldColumnDerived.checkIfUpdated()){
            preferences.setMDR_COLUMN_DERIVED_MARKER(fieldColumnDerived.getText());
        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }



}

