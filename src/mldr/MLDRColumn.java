package mldr;

import mcd.MCDAssociation;
import mcd.MCDAttribute;
import mcd.MCDElement;
import mcd.MCDEntity;
import md.MDElement;
import mdr.MDRColumn;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRElementWithSource;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import project.ProjectElement;

public class MLDRColumn extends MDRColumn implements IMLDRElement, IMLDRElementWithSource,
        IMLDRSourceMPDRCConstraintSpecifc {

    private static final long serialVersionUID = 1000;

    private MCDElement mcdElementSource;
    private boolean columnPKTI = false ; // Pour la création d'une colonne de PK indépendante lors d'une transformation en tables indépendantes
    private boolean columnSimPK = false ; // Colonne de simulation de PK pour les entitésnon indépendantes et transormées en tables indépendantes

    public MLDRColumn(ProjectElement parent, MCDElement mcdElementSource) {
        super(parent);
        this.mcdElementSource = mcdElementSource;
    }

    public MLDRColumn(ProjectElement parent, MCDElement mcdElementSource, int id) {
        super(parent, id);
        this.mcdElementSource = mcdElementSource;
    }

    public MLDRColumn(ProjectElement parent, MCDElement mcdElementSource, MLDRColumn mldrColumnPK) {
        super(parent, mldrColumnPK);
        this.mcdElementSource = mcdElementSource;
    }


    @Override
    public MCDElement getMcdElementSource() {
        return mcdElementSource;
    }

    @Override
    public void setMcdElementSource(MCDElement mcdElementSource) {
        this.mcdElementSource = mcdElementSource;
    }


    @Override
    public MDElement getMdElementSource() {
        return mcdElementSource;
    }

    @Override
    public MCDAttribute getMcdAttributeSource() {
        MCDElement mcdElementSource = getMcdElementSource();
        if (mcdElementSource instanceof MCDAttribute) {
            return (MCDAttribute) mcdElementSource;
        }
        return null;
    }

    public MCDEntity getEntityParentSource() {
        MLDRTable mldrTable = (MLDRTable) this.getMDRTableAccueil();
        return mldrTable.getEntityParentSource();
    }

    public boolean isColumnPKTI() {
        return columnPKTI;
    }

    public void setColumnPKTI(boolean columnPKTI) {
        this.columnPKTI = columnPKTI;
    }

    public boolean isColumnSimPK() {
        return columnSimPK;
    }

    public void setColumnSimPK(boolean columnSimPK) {
        this.columnSimPK = columnSimPK;
    }

    public MCDAssociation getAssNNParentSource() {
        MLDRTable mldrTable = (MLDRTable) this.getMDRTableAccueil();
        return mldrTable.getAssNNParentSource() ;
    }

    public boolean isPKForEntityIndependant() {
        MCDEntity mcdEntitySource = getEntityParentSource();
        if (mcdEntitySource != null) {
            return mcdEntitySource.isInd();
        }
        return false;
    }


    public MLDRTable getMLDRTableAccueil(){
        return (MLDRTable) getMDRTableAccueil();
    }

}

