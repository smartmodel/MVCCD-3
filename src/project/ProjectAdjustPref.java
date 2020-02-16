package project;

import main.MVCCDElementService;
import mcd.MCDEntity;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class ProjectAdjustPref {

    private Project project ;

    public ProjectAdjustPref (Project project){
        this.project = project;
    }

    public void mcdJournalisation(boolean selected) {
        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(project);
        for (MCDEntity mcdEntity : mcdEntities){
            mcdEntity.setJournal(selected);
        }
    }

    public void mcdAudit(boolean selected) {
        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(project);
        for (MCDEntity mcdEntity : mcdEntities){
            mcdEntity.setAudit(selected);
        }
    }

    public void changeProfile() {
        Preferences preferences = PreferencesManager.instance().preferences();
        mcdJournalisation(preferences.getMCD_JOURNALIZATION());
        mcdAudit((preferences.getMCD_AUDIT()));
    }
}
