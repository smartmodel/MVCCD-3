package mcd.services;

import datatypes.MCDDatatype;
import datatypes.MDDatatypeService;
import main.MVCCDElementService;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.interfaces.IMCDTraceability;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;

import java.util.ArrayList;

public class MCDAdjustPref {

    private ProjectElement mcdContainer;

    public MCDAdjustPref(ProjectElement mcdContainer){
        this.mcdContainer = mcdContainer;
    }


    public void mcdAIDDatatype(String lienProg) {
        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(mcdContainer);
        for (MCDEntity mcdEntity : mcdEntities) {
            for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()) {
                if (mcdAttribute.isAid()) {
                    mcdAttribute.setDatatypeLienProg(lienProg);
                    MCDDatatype mcdDatatypeForAID = MDDatatypeService.getMCDDatatypeByLienProg(lienProg);
                    mcdAttribute.setSize(mcdDatatypeForAID.getSizeDefault());
                }
            }
        }
    }

    public void mcdJournalisation(boolean selected) {
        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(mcdContainer);
        for (MCDEntity mcdEntity : mcdEntities){
            mcdEntity.setJournal(selected);
        }
        ArrayList<IMCDTraceability> iMCDTraceabilities = MVCCDElementService.getAllITraceabilities(mcdContainer);
        for (IMCDTraceability imcdTraceability : iMCDTraceabilities){
            imcdTraceability.setMcdJournalization(selected);
        }
    }

    public void mcdAudit(boolean selected) {
        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(mcdContainer);
        for (MCDEntity mcdEntity : mcdEntities){
            mcdEntity.setAudit(selected);
        }
        ArrayList<IMCDTraceability> iMCDTraceabilities = MVCCDElementService.getAllITraceabilities(mcdContainer);
        for (IMCDTraceability imcdTraceability : iMCDTraceabilities){
            imcdTraceability.setMcdAudit(selected);
        }

    }

    public void changeProfile() {
        Preferences preferences = PreferencesManager.instance().preferences();
        mcdAIDDatatype(preferences.getMCD_AID_DATATYPE_LIENPROG());
        mcdJournalisation(preferences.getMCD_JOURNALIZATION());
        mcdAudit((preferences.getMCD_AUDIT()));
    }

    public void mcdAIDIndColumnName(String text) {
        // A rajouter le test si isAidDep est correct après consolidation

        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(mcdContainer);
        for (MCDEntity mcdEntity : mcdEntities){
            for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
                if (mcdAttribute.isAid()  && (! mcdAttribute.isAidDep())){
                    mcdAttribute.setName(text);
                }
            }

        }
    }

    public void mcdAIDDepColumnName(String text) {
        // A rajouter le test si isAidDep est correct après consolidation

        ArrayList<MCDEntity> mcdEntities = MVCCDElementService.getAllEntities(mcdContainer);
        for (MCDEntity mcdEntity : mcdEntities){
            for (MCDAttribute mcdAttribute : mcdEntity.getMCDAttributes()){
                if (mcdAttribute.isAid()  && mcdAttribute.isAidDep()){
                    mcdAttribute.setName(text);
                }
            }

        }
    }
}
