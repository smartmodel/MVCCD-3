package mpdr;

import datatypes.MPDRDatatype;
import main.MVCCDElementFactory;
import generatesql.MPDRGenerateSQL;
import generatesql.window.GenerateSQLWindow;
import main.MVCCDManager;
import mdr.MDRElement;
import mdr.MDRModel;
import mdr.MDRRelFKEnd;
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
import utilities.Trace;
import resultat.Resultat;

import java.awt.*;
import java.util.ArrayList;

public abstract class MPDRModel extends MDRModel  implements IMPDRElement {
    private static final long serialVersionUID = 1000;
    private MPDRDB db = null;

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

    public IMPDRElement getIMPDRElementByMLDRElementSource(IMLDRElement imldrElement){
        return (IMPDRElement) MPDRModelService.getIMPDRElementByIMLDRElementSource(this, imldrElement);
    }

    public Resultat treatGenerate(GenerateSQLWindow owner) {
        MPDRGenerateSQL mpdrGenerateSQL = new MPDRGenerateSQL();
        return mpdrGenerateSQL.generateSQL(owner, this);
    }


    //TODO-1 A suppimer si la solution du listner est possible
    // Voir MLDR (même problème)

    public void refreshTreeMPDR(){
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


    public  IMPDRRelation createIMPDRRelation(IMLDRRelation imldrRelation){
        if ( imldrRelation instanceof MLDRRelationFK){
            MLDRRelationFK mldrRelationFK = (MLDRRelationFK) imldrRelation;
            MLDRFK mldrFK = (MLDRFK) mldrRelationFK.getMDRFK();
            MPDRTable mpdrtableAccueil = getMPDRTableByMLDRTableSource((MLDRTable) mldrFK.getMDRTableAccueil());
            MPDRFK mpdrFK = mpdrtableAccueil.getMPDRFKByMLDRFKSource(mldrFK);
            MPDRRelationFK newMPDRRelationFK = MVCCDElementFactory.instance().createMPDRRelationFK(
                    getMPDRContRelations(), mldrRelationFK, (MPDRTable) mpdrFK.getMDRTableAccueil(),
                    (MPDRTable) mpdrFK.getMdrPK().getMDRTableAccueil());

            return newMPDRRelationFK;
        }
        return null;
    }

}