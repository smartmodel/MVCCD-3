package project;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElementService;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;

public class ProjectAdjustPref {

    private Project project ;

    public ProjectAdjustPref (Project project){
        this.project = project;
    }


    public void mcdAIDDatatype(String lienProg) {
        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(project);
        for (MCDEntity mcdEntity : mcdEntities) {
            for (MCDAttribute mcdAttribute : mcdEntity.getMcdAttributes()) {
                if (mcdAttribute.isAid()) {
                    mcdAttribute.setDatatypeLienProg(lienProg);
                    MCDDatatype mcdDatatypeForAID = MDDatatypeService.getMCDDatatypeByLienProg(lienProg);
                    mcdAttribute.setSize(mcdDatatypeForAID.getSizeDefault());
                }
            }
        }
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
        mcdAIDDatatype(preferences.getMCD_AID_DATATYPE_LIENPROG());
        mcdJournalisation(preferences.getMCD_JOURNALIZATION());
        mcdAudit((preferences.getMCD_AUDIT()));
    }
}
