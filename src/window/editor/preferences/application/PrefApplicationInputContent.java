package window.editor.preferences.application;

import connections.ConDBMode;
import console.WarningLevel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import main.MVCCDElement;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import preferences.PreferencesOfApplicationSaverXml;
import project.Project;
import utilities.window.DialogMessage;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.services.SComboBoxService;

public class PrefApplicationInputContent extends PanelInputContent {

  private static final long serialVersionUID = -226137854170886311L;
  //private JPanel panel = new JPanel();

  private SCheckBox debug = new SCheckBox(this);
  private JPanel debugSubPanel = new JPanel();

  private SCheckBox debugPrintMVCCDElement = new SCheckBox(this);
  private SCheckBox debugBackgroundPanel = new SCheckBox(this);
  private SCheckBox debugJTableShowHidden = new SCheckBox(this);
  private SCheckBox debugJTreeInspectObject = new SCheckBox(this);
  private SCheckBox debugEditorDatasChanged = new SCheckBox(this);

  private SCheckBox debugTDPrint = new SCheckBox(this);

  //TODO-1 Reporter la non-modification en cascade
  private JPanel debugTDPrintSubPanel = new JPanel();
  private SCheckBox debugTDUniquePrint = new SCheckBox(this);

  private SComboBox fieldWarningLevel = new SComboBox(this);
  private SCheckBox fieldRepMCDModelsMany = new SCheckBox(this);
  private SCheckBox fieldRepMCDPackagesAuthorizeds = new SCheckBox(this);
  private SCheckBox fieldShowGrid = new SCheckBox(this);
  private SCheckBox fieldDiagrammerUMLNotation = new SCheckBox(this);

  private SComboBox fieldConDBMode = new SComboBox(this);

  // Pendant la phase de développement
  private SCheckBox fieldInsteadofXML = new SCheckBox(this);

  public PrefApplicationInputContent(PrefApplicationInput prefApplicationInput) {
    super(prefApplicationInput);
  }

  @Override
  protected void enabledContent() {

  }

  @Override
  public void createContentCustom() {

    this.debug.setSubPanel(this.debugSubPanel);
    this.debug.setRootSubPanel(true);
    this.debug.addItemListener(this);
    this.debug.addFocusListener(this);
    this.debugPrintMVCCDElement.addItemListener(this);
    this.debugPrintMVCCDElement.addFocusListener(this);
    this.debugBackgroundPanel.addItemListener(this);
    this.debugBackgroundPanel.addFocusListener(this);
    this.debugJTableShowHidden.addItemListener(this);
    this.debugJTableShowHidden.addFocusListener(this);
    this.debugJTreeInspectObject.addItemListener(this);
    this.debugJTreeInspectObject.addFocusListener(this);
    this.debugEditorDatasChanged.addItemListener(this);
    this.debugEditorDatasChanged.addFocusListener(this);

    this.debugTDPrint.addItemListener(this);
    this.debugTDPrint.addFocusListener(this);
    this.debugTDPrint.setSubPanel(this.debugTDPrintSubPanel);
    this.debugTDUniquePrint.addItemListener(this);
    this.debugTDUniquePrint.addFocusListener(this);

    this.fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DEVELOPMENT));
    this.fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DEBUG));
    this.fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DETAILS));
    this.fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_INFO));
    this.fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_WARNING));
    this.fieldWarningLevel.addItemListener(this);
    this.fieldWarningLevel.addFocusListener(this);

    this.fieldRepMCDModelsMany.addItemListener(this);
    this.fieldRepMCDModelsMany.addFocusListener(this);
    this.fieldRepMCDPackagesAuthorizeds.addItemListener(this);
    this.fieldRepMCDPackagesAuthorizeds.addFocusListener(this);

    this.fieldConDBMode.addItem(ConDBMode.CONNECTION.getText());
    this.fieldConDBMode.addItem(ConDBMode.CONNECTOR.getText());
    this.fieldConDBMode.addItemListener(this);
    this.fieldConDBMode.addFocusListener(this);

    this.fieldShowGrid.addItemListener(this);
    this.fieldShowGrid.addFocusListener(this);

    this.fieldDiagrammerUMLNotation.addItemListener(this);
    this.fieldDiagrammerUMLNotation.addFocusListener(this);

    this.fieldInsteadofXML.addItemListener(this);
    this.fieldInsteadofXML.addFocusListener(this);

    super.getSComponents().add(this.debug);
    super.getSComponents().add(this.debugPrintMVCCDElement);
    super.getSComponents().add(this.debugBackgroundPanel);
    super.getSComponents().add(this.debugJTableShowHidden);
    super.getSComponents().add(this.debugJTreeInspectObject);
    super.getSComponents().add(this.debugEditorDatasChanged);
    super.getSComponents().add(this.debugTDPrint);
    super.getSComponents().add(this.debugTDUniquePrint);
    super.getSComponents().add(this.fieldWarningLevel);
    super.getSComponents().add(this.fieldShowGrid);
    super.getSComponents().add(this.fieldDiagrammerUMLNotation);
    super.getSComponents().add(this.fieldRepMCDModelsMany);
    super.getSComponents().add(this.fieldRepMCDPackagesAuthorizeds);
    super.getSComponents().add(this.fieldConDBMode);
    super.getSComponents().add(this.fieldInsteadofXML);

    this.panelInputContentCustom.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    //gbc.anchor = GridBagConstraints.BELOW_BASELINE;
    gbc.insets = new Insets(10, 10, 0, 0);

    this.panelInputContentCustom.setAlignmentX(LEFT_ALIGNMENT);
    this.panelInputContentCustom.setAlignmentY(TOP_ALIGNMENT);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    this.panelInputContentCustom.add(new JLabel("Debug"));
    gbc.gridx++;
    this.panelInputContentCustom.add(this.debug, gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.debugSubPanel, gbc);

    this.debugSubPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    this.debugSubPanel.setAlignmentX(LEFT_ALIGNMENT);
    this.debugSubPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbcDebug = new GridBagConstraints();
    gbcDebug.gridx = 0;
    gbcDebug.gridy = 0;
    gbcDebug.gridwidth = 1;
    gbcDebug.gridheight = 1;
    gbcDebug.anchor = GridBagConstraints.WEST;
    gbcDebug.insets = new Insets(5, 5, 5, 5);

    this.debugSubPanel.add(new JLabel("MVCCD Element"), gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugPrintMVCCDElement, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    this.debugSubPanel.add(new JLabel("Background Panel"), gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugBackgroundPanel, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    this.debugSubPanel.add(new JLabel("Id et Order dans JTable"), gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugJTableShowHidden, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    this.debugSubPanel.add(new JLabel("Inspecteur objet dans JTree"), gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugJTreeInspectObject, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    this.debugSubPanel.add(new JLabel("SComposant chgt valeur"), gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugEditorDatasChanged, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    this.debugSubPanel.add(new JLabel("Table décision"), gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugTDPrint, gbcDebug);
    gbcDebug.gridx++;
    this.debugSubPanel.add(this.debugTDPrintSubPanel, gbcDebug);

    this.debugTDPrintSubPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    this.debugTDPrintSubPanel.setAlignmentX(LEFT_ALIGNMENT);
    this.debugTDPrintSubPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbcDebugTDInput = new GridBagConstraints();
    gbcDebugTDInput.gridx = 0;
    gbcDebugTDInput.gridy = 0;
    gbcDebugTDInput.gridwidth = 1;
    gbcDebugTDInput.gridheight = 1;
    gbcDebugTDInput.anchor = GridBagConstraints.WEST;
    gbcDebugTDInput.insets = new Insets(5, 5, 5, 5);

    this.debugTDPrintSubPanel.add(new JLabel("Unicité"), gbcDebugTDInput);
    gbcDebugTDInput.gridx++;
    this.debugTDPrintSubPanel.add(this.debugTDUniquePrint, gbcDebugTDInput);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Modèles multiples autorisés"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldRepMCDModelsMany, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Paquetages autorisés"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldRepMCDPackagesAuthorizeds, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Afficher la grille"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldShowGrid, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Notation UML du diagrammeur"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldDiagrammerUMLNotation, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Sauvegarde Sérialisée"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldInsteadofXML, gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(new JLabel("Vrai : sérialisation  - Faux : XML"), gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Niveau de messages console"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldWarningLevel, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    this.panelInputContentCustom.add(new JLabel("Mode de connexion base de données"), gbc);
    gbc.gridx++;
    this.panelInputContentCustom.add(this.fieldConDBMode, gbc);
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
    Preferences preferences = PreferencesManager.instance().getApplicationPref();
    Preferences preferencesDefault = PreferencesManager.instance().getDefaultPref();
    this.debug.setSelected(preferences.isDEBUG());
    this.debugPrintMVCCDElement.setSelected(preferences.isDEBUG_PRINT_MVCCDELEMENT());
    this.debugBackgroundPanel.setSelected(preferences.isDEBUG_BACKGROUND_PANEL());
    this.debugJTableShowHidden.setSelected(preferences.isDEBUG_SHOW_TABLE_COL_HIDDEN());
    this.debugJTreeInspectObject.setSelected(preferences.getDEBUG_INSPECT_OBJECT_IN_TREE());
    this.debugEditorDatasChanged.setSelected(preferences.getDEBUG_EDITOR_DATAS_CHANGED());
    this.debugTDPrint.setSelected(preferences.getDEBUG_TD_PRINT());
    this.debugTDUniquePrint.setSelected(preferences.getDEBUG_TD_UNICITY_PRINT());
    this.fieldRepMCDModelsMany.setSelected(preferences.getREPOSITORY_MCD_MODELS_MANY());
    SComboBoxService.selectByText(this.fieldWarningLevel, preferences.getWARNING_LEVEL().getText());
    this.fieldRepMCDPackagesAuthorizeds.setSelected(preferences.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
    this.fieldInsteadofXML.setSelected(preferences.isPERSISTENCE_SERIALISATION_INSTEADOF_XML());
    this.fieldShowGrid.setSelected(preferences.isDIAGRAMMER_SHOW_GRID());
    this.fieldDiagrammerUMLNotation.setSelected(preferences.isDIAGRAMMER_UML_NOTATION());
    SComboBoxService.selectByText(this.fieldConDBMode, preferences.getCON_DB_MODE().getText());
  }

  @Override
  protected void initDatas() {

  }

  @Override
  public void saveDatas(MVCCDElement mvccdElement) {
    boolean restart = false;
    boolean reloadProject = false;
    Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
    if (this.debug.checkIfUpdated()) {
      applicationPref.setDEBUG(this.debug.isSelected());
    }
    if (this.debugPrintMVCCDElement.checkIfUpdated()) {
      applicationPref.setDEBUG_PRINT_MVCCDELEMENT(this.debugPrintMVCCDElement.isSelected());
    }
    if (this.debugBackgroundPanel.checkIfUpdated()) {
      applicationPref.setDEBUG_BACKGROUND_PANEL(this.debugBackgroundPanel.isSelected());
      restart = true;
    }
    if (this.debugJTableShowHidden.checkIfUpdated()) {
      applicationPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(this.debugJTableShowHidden.isSelected());
    }
    if (this.debugJTreeInspectObject.checkIfUpdated()) {
      applicationPref.setDEBUG_INSPECT_OBJECT_IN_TREE(this.debugJTreeInspectObject.isSelected());
    }
    if (this.debugEditorDatasChanged.checkIfUpdated()) {
      applicationPref.setDEBUG_EDITOR_DATAS_CHANGED(this.debugEditorDatasChanged.isSelected());
    }
    if (this.debugTDPrint.checkIfUpdated()) {
      applicationPref.setDEBUG_TD_PRINT(this.debugTDPrint.isSelected());
    }
    if (this.debugTDUniquePrint.checkIfUpdated()) {
      applicationPref.setDEBUG_TD_UNICITY_PRINT(this.debugTDUniquePrint.isSelected());
    }

    if (this.fieldWarningLevel.checkIfUpdated()) {
      String text = (String) this.fieldWarningLevel.getSelectedItem();
      if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DEBUG))) {
        applicationPref.setWARNING_LEVEL(WarningLevel.DEBUG_MODE);
      }
      if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DETAILS))) {
        applicationPref.setWARNING_LEVEL(WarningLevel.DETAILS);
      }
      if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_INFO))) {
        applicationPref.setWARNING_LEVEL(WarningLevel.INFO);
      }
      if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_WARNING))) {
        applicationPref.setWARNING_LEVEL(WarningLevel.WARNING);
      }
    }

    if (this.fieldInsteadofXML.checkIfUpdated()) {
      applicationPref.setPERSISTENCE_SERIALISATION_INSTEADOF_XML(this.fieldInsteadofXML.isSelected());
    }

    if (this.fieldShowGrid.checkIfUpdated()) {
      applicationPref.setDIAGRAMMER_SHOW_GRID(this.fieldShowGrid.isSelected());
    }

    if (this.fieldDiagrammerUMLNotation.checkIfUpdated()) {
      applicationPref.setDIAGRAMMER_UML_NOTATION(this.fieldDiagrammerUMLNotation.isSelected());
    }

    if (this.fieldConDBMode.checkIfUpdated()) {
      String text = (String) this.fieldConDBMode.getSelectedItem();
      if (text.equals(ConDBMode.CONNECTION.getText())) {
        applicationPref.setCON_DB_MODE(ConDBMode.CONNECTION);
      }
      if (text.equals(ConDBMode.CONNECTOR.getText())) {
        applicationPref.setCON_DB_MODE(ConDBMode.CONNECTOR);
      }
    }

    // Copie dans les préférences de pojet
    if (PreferencesManager.instance().getProjectPref() != null) {
      PreferencesManager.instance().copyApplicationPref(Project.EXISTING);
    }

    if (this.fieldRepMCDModelsMany.checkIfUpdated()) {
      applicationPref.setREPOSITORY_MCD_MODELS_MANY(this.fieldRepMCDModelsMany.isSelected());
    }
    if (this.fieldRepMCDPackagesAuthorizeds.checkIfUpdated()) {
      applicationPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(this.fieldRepMCDPackagesAuthorizeds.isSelected());
      if (!restart) {
        reloadProject = true;
      }
    }

    // Sauvegarde (fichier) des préférences d'application
    new PreferencesOfApplicationSaverXml().createFileApplicationPref();
    //PreferencesSaver saver = new PreferencesSaver();
    //saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);

    String message = MessagesBuilder.getMessagesProperty("preferences.application.saved", new String[]{Preferences.APPLICATION_NAME});
    if (restart) {
      message = message + MessagesBuilder.getMessagesProperty("preferences.application.saved.restart");
    }
    if (reloadProject) {
      message = message + MessagesBuilder.getMessagesProperty("preferences.application.saved.reload.project");
    }

    new DialogMessage().showOk(this.getEditor(), message);
  }

  @Override
  public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

  }

}