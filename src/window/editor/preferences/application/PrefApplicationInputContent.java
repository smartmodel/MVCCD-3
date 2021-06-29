package window.editor.preferences.application;

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

    debug.setSubPanel(debugSubPanel);
    debug.setRootSubPanel(true);
    debug.addItemListener(this);
    debug.addFocusListener(this);
    debugPrintMVCCDElement.addItemListener(this);
    debugPrintMVCCDElement.addFocusListener(this);
    debugBackgroundPanel.addItemListener(this);
    debugBackgroundPanel.addFocusListener(this);
    debugJTableShowHidden.addItemListener(this);
    debugJTableShowHidden.addFocusListener(this);
    debugJTreeInspectObject.addItemListener(this);
    debugJTreeInspectObject.addFocusListener(this);
    debugEditorDatasChanged.addItemListener(this);
    debugEditorDatasChanged.addFocusListener(this);

    debugTDPrint.addItemListener(this);
    debugTDPrint.addFocusListener(this);
    debugTDPrint.setSubPanel(debugTDPrintSubPanel);
    debugTDUniquePrint.addItemListener(this);
    debugTDUniquePrint.addFocusListener(this);

    fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DEVELOPMENT));
    fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DEBUG));
    fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_DETAILS));
    fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_INFO));
    fieldWarningLevel.addItem(MessagesBuilder.getMessagesProperty(Preferences.WARNING_LEVEL_WARNING));
    fieldWarningLevel.addItemListener(this);
    fieldWarningLevel.addFocusListener(this);

    fieldRepMCDModelsMany.addItemListener(this);
    fieldRepMCDModelsMany.addFocusListener(this);
    fieldRepMCDPackagesAuthorizeds.addItemListener(this);
    fieldRepMCDPackagesAuthorizeds.addFocusListener(this);

    fieldShowGrid.addItemListener(this);
    fieldShowGrid.addFocusListener(this);

    fieldInsteadofXML.addItemListener(this);
    fieldInsteadofXML.addFocusListener(this);

    super.getSComponents().add(debug);
    super.getSComponents().add(debugPrintMVCCDElement);
    super.getSComponents().add(debugBackgroundPanel);
    super.getSComponents().add(debugJTableShowHidden);
    super.getSComponents().add(debugJTreeInspectObject);
    super.getSComponents().add(debugEditorDatasChanged);
    super.getSComponents().add(debugTDPrint);
    super.getSComponents().add(debugTDUniquePrint);
    super.getSComponents().add(fieldWarningLevel);
    super.getSComponents().add(fieldShowGrid);
    super.getSComponents().add(fieldRepMCDModelsMany);
    super.getSComponents().add(fieldRepMCDPackagesAuthorizeds);
    super.getSComponents().add(fieldInsteadofXML);

    panelInputContentCustom.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    //gbc.anchor = GridBagConstraints.BELOW_BASELINE;
    gbc.insets = new Insets(10, 10, 0, 0);

    panelInputContentCustom.setAlignmentX(LEFT_ALIGNMENT);
    panelInputContentCustom.setAlignmentY(TOP_ALIGNMENT);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    panelInputContentCustom.add(new JLabel("Debug"));
    gbc.gridx++;
    panelInputContentCustom.add(debug, gbc);
    gbc.gridx++;
    panelInputContentCustom.add(debugSubPanel, gbc);

    debugSubPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    debugSubPanel.setAlignmentX(LEFT_ALIGNMENT);
    debugSubPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbcDebug = new GridBagConstraints();
    gbcDebug.gridx = 0;
    gbcDebug.gridy = 0;
    gbcDebug.gridwidth = 1;
    gbcDebug.gridheight = 1;
    gbcDebug.anchor = GridBagConstraints.WEST;
    gbcDebug.insets = new Insets(5, 5, 5, 5);

    debugSubPanel.add(new JLabel("MVCCD Element"), gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugPrintMVCCDElement, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    debugSubPanel.add(new JLabel("Background Panel"), gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugBackgroundPanel, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    debugSubPanel.add(new JLabel("Id et Order dans JTable"), gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugJTableShowHidden, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    debugSubPanel.add(new JLabel("Inspecteur objet dans JTree"), gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugJTreeInspectObject, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    debugSubPanel.add(new JLabel("SComposant chgt valeur"), gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugEditorDatasChanged, gbcDebug);

    gbcDebug.gridy++;
    gbcDebug.gridx = 0;
    debugSubPanel.add(new JLabel("Table décision"), gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugTDPrint, gbcDebug);
    gbcDebug.gridx++;
    debugSubPanel.add(debugTDPrintSubPanel, gbcDebug);

    debugTDPrintSubPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    debugTDPrintSubPanel.setAlignmentX(LEFT_ALIGNMENT);
    debugTDPrintSubPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbcDebugTDInput = new GridBagConstraints();
    gbcDebugTDInput.gridx = 0;
    gbcDebugTDInput.gridy = 0;
    gbcDebugTDInput.gridwidth = 1;
    gbcDebugTDInput.gridheight = 1;
    gbcDebugTDInput.anchor = GridBagConstraints.WEST;
    gbcDebugTDInput.insets = new Insets(5, 5, 5, 5);

    debugTDPrintSubPanel.add(new JLabel("Unicité"), gbcDebugTDInput);
    gbcDebugTDInput.gridx++;
    debugTDPrintSubPanel.add(debugTDUniquePrint, gbcDebugTDInput);

    gbc.gridy++;
    gbc.gridx = 0;
    panelInputContentCustom.add(new JLabel("Modèles multiples autorisés"), gbc);
    gbc.gridx++;
    panelInputContentCustom.add(fieldRepMCDModelsMany, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    panelInputContentCustom.add(new JLabel("Paquetages autorisés"), gbc);
    gbc.gridx++;
    panelInputContentCustom.add(fieldRepMCDPackagesAuthorizeds, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    panelInputContentCustom.add(new JLabel("Afficher la grille"), gbc);
    gbc.gridx++;
    panelInputContentCustom.add(fieldShowGrid, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    panelInputContentCustom.add(new JLabel("Sauvegarde Sérialisée"), gbc);
    gbc.gridx++;
    panelInputContentCustom.add(fieldInsteadofXML, gbc);
    gbc.gridx++;
    panelInputContentCustom.add(new JLabel("Vrai : sérialisation  - Faux : XML"), gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    panelInputContentCustom.add(new JLabel("Niveau de messages console"), gbc);
    gbc.gridx++;
    panelInputContentCustom.add(fieldWarningLevel, gbc);
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
    debug.setSelected(preferences.isDEBUG());
    debugPrintMVCCDElement.setSelected(preferences.isDEBUG_PRINT_MVCCDELEMENT());
    debugBackgroundPanel.setSelected(preferences.isDEBUG_BACKGROUND_PANEL());
    debugJTableShowHidden.setSelected(preferences.isDEBUG_SHOW_TABLE_COL_HIDDEN());
    debugJTreeInspectObject.setSelected(preferences.getDEBUG_INSPECT_OBJECT_IN_TREE());
    debugEditorDatasChanged.setSelected(preferences.getDEBUG_EDITOR_DATAS_CHANGED());
    debugTDPrint.setSelected(preferences.getDEBUG_TD_PRINT());
    debugTDUniquePrint.setSelected(preferences.getDEBUG_TD_UNICITY_PRINT());
    fieldRepMCDModelsMany.setSelected(preferences.getREPOSITORY_MCD_MODELS_MANY());
    SComboBoxService.selectByText(fieldWarningLevel, preferences.getWARNING_LEVEL().getText());
    fieldRepMCDPackagesAuthorizeds.setSelected(preferences.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
    fieldInsteadofXML.setSelected(preferences.isPERSISTENCE_SERIALISATION_INSTEADOF_XML());
    fieldShowGrid.setSelected(preferences.isDIAGRAMMER_SHOW_GRID());
  }

  @Override
  protected void initDatas() {

  }

  @Override
  public void saveDatas(MVCCDElement mvccdElement) {
    boolean restart = false;
    boolean reloadProject = false;
    Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
    if (debug.checkIfUpdated()) {
      applicationPref.setDEBUG(debug.isSelected());
    }
    if (debugPrintMVCCDElement.checkIfUpdated()) {
      applicationPref.setDEBUG_PRINT_MVCCDELEMENT(debugPrintMVCCDElement.isSelected());
    }
    if (debugBackgroundPanel.checkIfUpdated()) {
      applicationPref.setDEBUG_BACKGROUND_PANEL(debugBackgroundPanel.isSelected());
      restart = true;
    }
    if (debugJTableShowHidden.checkIfUpdated()) {
      applicationPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(debugJTableShowHidden.isSelected());
    }
    if (debugJTreeInspectObject.checkIfUpdated()) {
      applicationPref.setDEBUG_INSPECT_OBJECT_IN_TREE(debugJTreeInspectObject.isSelected());
    }
    if (debugEditorDatasChanged.checkIfUpdated()) {
      applicationPref.setDEBUG_EDITOR_DATAS_CHANGED(debugEditorDatasChanged.isSelected());
    }
    if (debugTDPrint.checkIfUpdated()) {
      applicationPref.setDEBUG_TD_PRINT(debugTDPrint.isSelected());
    }
    if (debugTDUniquePrint.checkIfUpdated()) {
      applicationPref.setDEBUG_TD_UNICITY_PRINT(debugTDUniquePrint.isSelected());
    }

    if (fieldWarningLevel.checkIfUpdated()) {
      String text = (String) fieldWarningLevel.getSelectedItem();
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

    if (fieldInsteadofXML.checkIfUpdated()) {
      applicationPref.setPERSISTENCE_SERIALISATION_INSTEADOF_XML(fieldInsteadofXML.isSelected());
    }

    if (fieldShowGrid.checkIfUpdated()) {
      System.out.println("updatedddddddddd");
      applicationPref.setDIAGRAMMER_SHOW_GRID(fieldShowGrid.isSelected());
    }

    // Copie dans les préférences de pojet
    if (PreferencesManager.instance().getProjectPref() != null) {
      PreferencesManager.instance().copyApplicationPref(Project.EXISTING);
    }

    if (fieldRepMCDModelsMany.checkIfUpdated()) {
      applicationPref.setREPOSITORY_MCD_MODELS_MANY(fieldRepMCDModelsMany.isSelected());
    }
    if (fieldRepMCDPackagesAuthorizeds.checkIfUpdated()) {
      applicationPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(fieldRepMCDPackagesAuthorizeds.isSelected());
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

    new DialogMessage().showOk(getEditor(), message);
  }

  @Override
  public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

  }

}

