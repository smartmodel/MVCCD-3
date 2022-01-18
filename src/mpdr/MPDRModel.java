package mpdr;

import datatypes.MPDRDatatype;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mdr.MDRElement;
import mdr.MDRModel;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.MLDRRelationFK;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRRelation;
import mpdr.interfaces.IMPDRElement;
import mpdr.interfaces.IMPDRRelation;
import mpdr.services.MPDRModelService;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MPDRModel extends MDRModel  implements IMPDRElement {
    private static final long serialVersionUID = 1000;
    private MPDRDB db = null;

    // Les 2 liens de Connection et Connector peuvent exister si la préférence d'application est changée
    // Par contre, lorsque Connector est utilisé l'instance de Connection est trouvée en passant par son parent!
    private String connectionLienProg = null;
    private String connectorLienProg = null;
    private MPDRDropBefore dropBeforeCreate = MPDRDropBefore.NOTHING;

    private MPDRDBPK mpdrDbPK ;
    private boolean tapis ;
    private String sequencePKNameFormat;
    private String triggerNameFormat;

    public MPDRModel(ProjectElement parent, String name, MPDRDB db) {
        super(parent, name);
        this.db = db;
    }


    public MPDRTable getMPDRTableByMLDRTableSource(MLDRTable mldrTable) {
        return MPDRModelService.getMPDRTableByMLDRTableSource(this, mldrTable);
    }

    public abstract MPDRTable createTable(MLDRTable mldrTable);

    public MPDRContTables getMPDRContTables() {
        return MPDRModelService.getMPDRContTables(this);
    }


    public MPDRDB getDb() {
        return db;
    }

    public abstract MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn);

    public ArrayList<IMDRElementWithIteration> getIMDRElementsWithIterationInScope() {
        ArrayList<IMDRElementWithIteration> resultat = new ArrayList<IMDRElementWithIteration>();
        for (MDRElement mdrElement : getMDRDescendants()) {
            if (mdrElement instanceof IMDRElementWithIteration) {
                //if (mdrElement instanceof MPDRModel) {
                if (mdrElement instanceof IMPDRElement) {
                    resultat.add((IMDRElementWithIteration) mdrElement);
                }
            }
        }
        return resultat;
    }

    public ArrayList<MPDRTable> getMPDRTables() {
        return MPDRModelService.getMPDRTables(this);
    }

    public IMPDRElement getIMPDRElementByMLDRElementSource(IMLDRElement imldrElement) {
        return (IMPDRElement) MPDRModelService.getIMPDRElementByIMLDRElementSource(this, imldrElement);
    }


    public abstract String treatGenerate() ;

    //TODO-1 A suppimer si la solution du listner est possible
    // Voir MLDR (même problème)

    public void refreshTreeMPDR() {
        MVCCDManager.instance().getWinRepositoryContent().reload(this.getNode());
    }


    public MPDRContRelations getMPDRContRelations() {
        return MPDRModelService.getMPDRContRelations(this);
    }

    public ArrayList<IMPDRRelation> getIMPDRRelations() {
        return MPDRModelService.getIMPDRRelations(this);
    }

    public IMPDRRelation getIMPDRRelationByIMLDRRelationSource(IMLDRRelation imldrRelation) {
        return MPDRModelService.getIMPDRRelationByIMLDRRelationSource(this, imldrRelation);
    }


    public IMPDRRelation createIMPDRRelation(IMLDRRelation imldrRelation) {
        if (imldrRelation instanceof MLDRRelationFK) {
            MLDRRelationFK mldrRelationFK = (MLDRRelationFK) imldrRelation;
            MLDRFK mldrFK = (MLDRFK) mldrRelationFK.getMDRFK();
            MPDRTable mpdrtableAccueil = getMPDRTableByMLDRTableSource((MLDRTable) mldrFK.getMDRTableAccueil());
            MPDRFK mpdrFK = mpdrtableAccueil.getMPDRFKByMLDRFKSource(mldrFK);
            MPDRRelationFK newMPDRRelationFK = MVCCDElementFactory.instance().createMPDRRelationFK(
                    getMPDRContRelations(),
                    mldrRelationFK,
                    (MPDRTable) mpdrFK.getMDRTableAccueil(),
                    (MPDRTable) mpdrFK.getMdrPK().getMDRTableAccueil());

            return newMPDRRelationFK;
        }
        return null;
    }

    public String getConnectionLienProg() {
        return connectionLienProg;
    }

    public void setConnectionLienProg(String connectionLienProg) {
        this.connectionLienProg = connectionLienProg;
    }

    public String getConnectorLienProg() {
        return connectorLienProg;
    }

    public void setConnectorLienProg(String connectorLienProg) {
        this.connectorLienProg = connectorLienProg;
    }

    public MPDRDropBefore getDropBeforeCreate() {
        return dropBeforeCreate;
    }

    public void setDropBeforeCreate(MPDRDropBefore dropBeforeCreate) {
        this.dropBeforeCreate = dropBeforeCreate;
    }

    public MPDRDBPK getMpdrDbPK() {
        return mpdrDbPK;
    }

    public void setMpdrDbPK(MPDRDBPK mpdrDbPK) {
        this.mpdrDbPK = mpdrDbPK;
    }

    public boolean isTapis() {
        return tapis;
    }

    public void setTapis(boolean tapis) {
        this.tapis = tapis;
    }

    public String getSequencePKNameFormat() {
        return sequencePKNameFormat;
    }

    public void setSequencePKNameFormat(String sequencePKNameFormat) {
        this.sequencePKNameFormat = sequencePKNameFormat;
    }

    public void setTriggerNameFormat(String triggerNameFormat) {
        this.triggerNameFormat = triggerNameFormat;
    }

    public String getTriggerNameFormat() {
        return triggerNameFormat;
    }

    public abstract Boolean getMPDR_TAPIs() ;
}