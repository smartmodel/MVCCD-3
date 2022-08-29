package screens.project;

import messages.MessagesBuilder;
import preferences.Preferences;
import profile.ProfileManager;
import screens.IScreen;
import screens.fields.ScreenTextField;
import screens.panels.ErrorsPanel;
import screens.panels.InputError;
import screens.project.actions.buttons.ProjectCreationButtonsAction;
import screens.project.listeners.inputs.ProjectNameInputChangeListener;
import screens.project.validators.ProjectScreenInputsValidator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;


public class ProjectCreationScreen extends JFrame implements IScreen {
    private JPanel dialogPane;
    private JPanel informationsPanel;
    private JLabel title;
    private JLabel description;
    private JPanel fields;
    private JLabel projectNameLabel;
    private ScreenTextField projectNameInput;
    private JLabel profilLabel;
    private JComboBox<String> profilSelect;
    private JLabel multipleMCDModelsLabel;
    private JCheckBox multipleMCDModelsCheckbox;
    private JLabel multiplePackagesLabel;
    private JCheckBox multiplePackagesCheckbox;
    private JPanel buttons;
    private JButton cancelButton;
    private JButton createProjectButton;

    private ErrorsPanel errorsPanel;

    private ProjectScreenInputsValidator fieldsValidator;

    public ProjectCreationScreen() {
        this.initComponents();
        this.initButtonsActions();
        this.pack();
    }

    @Override
    public void initUI() {
        this.setTitle(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.title"));
        this.setMinimumSize(new Dimension(450, 150));
        this.setResizable(false);
        this.setBackground(new Color(242, 242, 242));
    }

    @Override
    public void initComponents() {

        this.initUI();
        this.initDialogPanel();
        this.initInformationsPanel();
        this.initFieldsPanel();
        this.initButtonsPanel();
        this.initFieldsValidator();
        this.initFieldsListeners();
        this.fillProfilesComboBox();
        this.initErrorsPanel();
    }

    @Override
    public void initButtonsActions() {
        this.createProjectButton.addActionListener(new ProjectCreationButtonsAction(this));
    }

    @Override
    public void initFieldsValidator() {
    }

    private void initDialogPanel() {
        this.dialogPane = new JPanel();
        this.dialogPane.setLayout(new BorderLayout());
        this.getContentPane().add(this.dialogPane, BorderLayout.CENTER);

        // On crée le padding
        Border padding = BorderFactory.createEmptyBorder(Preferences.UI_SCREEN_PADDING, Preferences.UI_SCREEN_PADDING, Preferences.UI_SCREEN_PADDING, Preferences.UI_SCREEN_PADDING);
        this.dialogPane.setBorder(padding);
    }

    private void initErrorsPanel() {
        this.errorsPanel = new ErrorsPanel();
        this.getContentPane().add(this.errorsPanel, BorderLayout.SOUTH);
        this.errorsPanel.addError(new InputError("Mince, ça marche pas !"));
        this.errorsPanel.addError(new InputError("Mince, ça marche pas !"));
        this.errorsPanel.addError(new InputError("Mince, ça marche pas !"));
        this.errorsPanel.addError(new InputError("Mince, ça marche pas !"));
    }

    private void initButtonsPanel() {
        this.buttons = new JPanel();
        this.cancelButton = new JButton();
        this.createProjectButton = new JButton();

        this.buttons.setBorder(new EmptyBorder(12, 0, 0, 0));
        this.buttons.setLayout(new GridBagLayout());
        ((GridBagLayout) this.buttons.getLayout()).columnWidths = new int[]{0, 85, 85, 0};
        ((GridBagLayout) this.buttons.getLayout()).columnWeights = new double[]{1.0, 0.0, 0.0, 0.0};

        // Bouton "Annuler
        this.cancelButton.setText("Annuler");
        this.buttons.add(this.cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));

        // Bouton "Créer le projet"
        this.createProjectButton.setText("Cr\u00e9er le projet");
        this.createProjectButton.setForeground(Color.white);
        this.createProjectButton.setBackground(new Color(0, 138, 201));
        this.createProjectButton.setPreferredSize(new Dimension(108, 30));

        // Ajout des boutons au panneau
        this.buttons.add(this.createProjectButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.dialogPane.add(this.buttons, BorderLayout.PAGE_END);

    }

    private void initFieldsPanel() {
        this.fields = new JPanel();
        this.fields.setLayout(new GridLayout(0, 2, 0, 10));

        // Création des composants
        this.projectNameLabel = new JLabel();
        this.projectNameInput = new ScreenTextField();
        this.projectNameInput.setValidator(new ProjectScreenInputsValidator(this.projectNameInput));

        this.profilLabel = new JLabel();
        this.profilSelect = new JComboBox<>();
        this.multipleMCDModelsLabel = new JLabel();
        this.multipleMCDModelsCheckbox = new JCheckBox();
        this.multiplePackagesLabel = new JLabel();
        this.multiplePackagesCheckbox = new JCheckBox();

        // Label "Nom du projet"
        this.projectNameLabel.setText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.name.label"));
        this.projectNameLabel.setIcon(new ImageIcon(this.getClass().getResource(Preferences.UI_ICON_INFORMATIONS_PATH)));
        this.projectNameLabel.setToolTipText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.name.label.tooltip.text"));


        // Label "Profil"
        this.profilLabel.setText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.profil.label"));
        this.profilLabel.setIcon(new ImageIcon(this.getClass().getResource(Preferences.UI_ICON_INFORMATIONS_PATH)));
        this.profilLabel.setToolTipText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.profil.label.tooltip.text"));

        // Label "Multiples modèles MCD"
        this.multipleMCDModelsLabel.setText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.multiples.mcd.models.label"));

        // Label "Multiples packages autorisés"
        this.multiplePackagesLabel.setText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.mutliples.packages.allowed.label"));

        // Ajout des champs au panneau
        this.fields.add(this.projectNameLabel);
        this.fields.add(this.projectNameInput);
        this.fields.add(this.profilLabel);
        this.fields.add(this.profilSelect);
        this.fields.add(this.multipleMCDModelsLabel);
        this.fields.add(this.multipleMCDModelsCheckbox);
        this.fields.add(this.multiplePackagesLabel);
        this.fields.add(this.multiplePackagesCheckbox);

        this.dialogPane.add(this.fields, BorderLayout.CENTER);
    }

    private void initInformationsPanel() {
        // Création des composants
        this.informationsPanel = new JPanel();
        this.title = new JLabel();
        this.description = new JLabel();

        this.informationsPanel.setMinimumSize(new Dimension(260, 40));
        this.informationsPanel.setPreferredSize(new Dimension(260, 60));
        this.informationsPanel.setLayout(new BoxLayout(this.informationsPanel, BoxLayout.Y_AXIS));

        // Titre de la fenêtre
        this.title.setText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.title"));
        this.title.setFont(this.title.getFont().deriveFont(this.title.getFont().getStyle() | Font.BOLD, this.title.getFont().getSize() + 5f));
        this.informationsPanel.add(this.title);

        // Description de la fenêtre
        this.description.setText(MessagesBuilder.getMessagesProperty("ux.screen.project.creation.description"));

        this.informationsPanel.add(this.description);
        this.dialogPane.add(this.informationsPanel, BorderLayout.PAGE_START);
    }

    public JTextField getProjectNameInput() {
        return this.projectNameInput;
    }

    public void initFieldsListeners() {
        this.projectNameInput.getDocument().addDocumentListener(new ProjectNameInputChangeListener(this));
    }

    public ProjectScreenInputsValidator getFieldsValidator() {
        return this.fieldsValidator;
    }

    public void fillProfilesComboBox() {
        ArrayList<String> filesProfile = ProfileManager.instance().filesProfile();
        this.profilSelect.addItem(Preferences.UI_COMBO_BOX_PLACEHOLDER);
        for (String fileProfile : filesProfile) {
            this.profilSelect.addItem(fileProfile);
        }
    }

    public void revalidateProjectNameField() {
        if (this.projectNameInput.getValidator().isValid()) {
            this.projectNameInput.putClientProperty("JComponent.outline", "Color.grey");

        } else {
            this.projectNameInput.putClientProperty("JComponent.outline", "error");
        }
    }
}
