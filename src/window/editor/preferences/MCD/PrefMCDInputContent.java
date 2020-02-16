package window.editor.preferences.MCD;

import main.MVCCDElement;
import main.MVCCDManager;
import preferences.Preferences;
import project.Project;
import project.ProjectAdjustPref;
import utilities.window.SCheckBox;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMCDInputContent extends PanelInputContent {
    private JPanel panel = new JPanel();
    private SCheckBox mcdJournalization = new SCheckBox("Journalisation");
    private SCheckBox mcdJournalizationException = new SCheckBox("Exception autorisée");
    private SCheckBox mcdAudit = new SCheckBox("Audit");
    private SCheckBox mcdAuditException = new SCheckBox("Exception autorisée");

    public PrefMCDInputContent(PrefMCDInput prefMCDInput) {
        super(prefMCDInput);
        prefMCDInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }

    private void createContent() {

        mcdJournalization.setToolTipText("Journalisation des manipulations de l'entité");
        //entityName.getDocument().addDocumentListener(this);
        mcdJournalization.addChangeListener(this);
        mcdJournalization.addFocusListener(this);

        mcdJournalizationException.setToolTipText("Exception de journalisation autorisée");
        mcdJournalizationException.addChangeListener(this);
        mcdJournalizationException.addFocusListener(this);

        mcdAudit.setToolTipText("Audit des ajouts et modifications de l'entité");
        mcdAudit.addChangeListener(this);
        mcdAudit.addFocusListener(this);

        mcdAuditException.setToolTipText("Exception de l'audit autorisée");
        mcdAuditException.addChangeListener(this);
        mcdAuditException.addFocusListener(this);

        super.getsComponents().add(mcdJournalization);
        super.getsComponents().add(mcdJournalizationException);
        super.getsComponents().add(mcdAudit);
        super.getsComponents().add(mcdAuditException);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(mcdJournalization, gbc);
        gbc.gridx++ ;
        panel.add(mcdJournalizationException, gbc);

        gbc.gridx = 0;
        gbc.gridy++ ;
        panel.add(mcdAudit, gbc);
        gbc.gridx++ ;
        panel.add(mcdAuditException, gbc);

    }

    @Override
    protected boolean checkDatas() {
        return true;
    }

    @Override
    public boolean checkDatasPreSave() {
        return true;
    }

    @Override
    protected void changeField(DocumentEvent e) {

    }

    @Override
    protected void changeField(ChangeEvent e) {

    }

    @Override
    protected void changeField(ItemEvent e) {

    }

    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        mcdJournalization.setSelected(preferences.getMCD_JOURNALIZATION()); ;
        mcdJournalizationException.setSelected(preferences.getMCD_JOURNALIZATION_EXCEPTION()); ;
        mcdAudit.setSelected(preferences.getMCD_AUDIT()); ;
        mcdAuditException.setSelected(preferences.getMCD_AUDIT_EXCEPTION()); ;
    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;
        Project project = MVCCDManager.instance().getProject();
        ProjectAdjustPref projectAdjustPref = new ProjectAdjustPref(project);

        if (mcdJournalization.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION(mcdJournalization.isSelected());
            projectAdjustPref.mcdJournalisation(mcdJournalization.isSelected());
        }
        if (mcdJournalizationException.checkIfUpdated()){
            preferences.setMCD_JOURNALIZATION_EXCEPTION(mcdJournalizationException.isSelected());
        }
        if (mcdAudit.checkIfUpdated()){
            preferences.setMCD_AUDIT(mcdAudit.isSelected());
            projectAdjustPref.mcdAudit(mcdAudit.isSelected());
        }
        if (mcdAuditException.checkIfUpdated()){
            preferences.setMCD_AUDIT_EXCEPTION(mcdAuditException.isSelected());
        }
    }


}

