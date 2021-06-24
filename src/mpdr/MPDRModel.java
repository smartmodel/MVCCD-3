package mpdr;

import datatypes.MPDRDatatype;
import generatesql.MPDRGenerateSQL;
import main.MVCCDManager;
import mdr.MDRElement;
import mdr.MDRModel;
import mdr.interfaces.IMDRElementWithIteration;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRElement;
import mpdr.services.MPDRModelService;
import project.ProjectElement;
import resultat.Resultat;

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
                if (mdrElement instanceof MPDRModel) {
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
        return (IMPDRElement) MPDRModelService.getIMPDRElementByMLDRElementSource(this, imldrElement);
    }

    public Resultat treatGenerate() {
        MPDRGenerateSQL mpdrGenerateSQL = new MPDRGenerateSQL();
        return mpdrGenerateSQL.generateSQL(this);
    }


    //TODO-1 A suppimer si la solution du listner est possible
    // Voir MLDR (même problème)

    public void refreshTreeMPDR(){
        MVCCDManager.instance().getWinRepositoryContent().reload(this.getNode());
    }



}