package mcd;

import exceptions.CodeApplException;
import m.IMCompliant;
import m.MRelationDegree;
import m.services.MRelationService;
import main.MVCCDElement;
import mcd.interfaces.IMCDParameter;
import mcd.services.MCDAssociationService;
import mcd.services.MCDElementService;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;

public class MCDAssociation extends MCDRelation implements IMCompliant, IMCDParameter {

    private  static final long serialVersionUID = 1000;

    public static final String CLASSSHORTNAMEUI = "Association";


    private MCDAssociationNature nature = MCDAssociationNature.NOID;
    private boolean frozen = false;
    private boolean deleteCascade = false;
    private Boolean oriented = null;

    public MCDAssociation(MCDElement parent) {

        super(parent);
    }


    public MCDAssociation(MCDElement parent, String name) {

        super(parent, name);
    }

    public MCDAssEnd getFrom() {

        return (MCDAssEnd) super.getA();
    }

    public void setFrom(MCDAssEnd from) {
        super.setA(from);
        from.setDrawingDirection(MCDAssEnd.FROM);
    }

    public MCDAssEnd getTo() {
        return (MCDAssEnd) super.getB();
    }

    public void setTo(MCDAssEnd to) {
        super.setB(to);
        to.setDrawingDirection(MCDAssEnd.TO);
    }

    public String getNameId(){
        return MCDAssociationService.buildNamingId(getFrom().getMcdEntity(), getTo().getMcdEntity(), this.getName());
    }

   public String getShortNameId() {
       return MCDAssociationService.buildNamingId(getFrom().getMcdEntity(), getTo().getMcdEntity(), this.getShortName());
    }

   public String getLongNameId() {
       return MCDAssociationService.buildNamingId(getFrom().getMcdEntity(), getTo().getMcdEntity(), this.getLongName());
    }

    @Override
    public String getNameTree(){
        String resultat = "";

        MCDEntity entityFrom = getFrom().getMcdEntity();
        MCDEntity entityTo = getTo().getMcdEntity();

        MVCCDElement containerAssociation = this.getParent().getParent();


        MVCCDElement containerEntityFrom = entityFrom.getParent().getParent();
        MVCCDElement containerEntityTo = entityTo.getParent().getParent();

        boolean c1a = containerEntityFrom == containerAssociation;
        boolean c1b = containerEntityTo == containerAssociation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameEntityFrom = "";
        String nameEntityTo = "";

        if (r1){
            nameEntityFrom = entityFrom.getName();
            nameEntityTo = entityTo.getName();
        }
        
        if (r2){
            nameEntityFrom = entityFrom.getShortNameSmart();
            nameEntityTo = entityTo.getShortNameSmart();
        }

        if (r3){
            nameEntityFrom = entityFrom.getNamePath(MCDElementService.PATHSHORTNAME);
            nameEntityTo = entityTo.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4){
           nameEntityFrom = entityFrom.getShortNameSmartPath();
           nameEntityTo = entityTo.getShortNameSmartPath();
        }

        String namingAssociation ;
        if (StringUtils.isNotEmpty(getFrom().getName())  && StringUtils.isNotEmpty(getTo().getName())){
            namingAssociation = Preferences.MCD_NAMING_ASSOCIATION_ARROW_RIGHT +
                    getFrom().getName() +
                    Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR  +
                    getTo().getName() +
                    Preferences.MCD_NAMING_ASSOCIATION_ARROW_LEFT;
        } else {
            namingAssociation = Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR +
                    this.getName() + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
        }
        resultat = nameEntityFrom + namingAssociation + nameEntityTo;
        return resultat;
    }

    public MCDAssEnd getMCDAssEndOpposite(MCDAssEnd mcdAssEnd) {
        if (this.getFrom() == mcdAssEnd){
            return this.getTo();
        }
        if (this.getTo() == mcdAssEnd){
            return this.getFrom();
        }

        throw new CodeApplException("L'extrémité d'association passée en paramètre n'existe pas pour cette association ");

    }

    public MCDAssociationNature getNature() {
        return nature;
    }

    public void setNature(MCDAssociationNature nature) {
        this.nature = nature;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isDeleteCascade() {
        return deleteCascade;
    }

    public void setDeleteCascade(boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
    }

    public Boolean getOriented() {
        return oriented;
    }

    public void setOriented(Boolean oriented) {
        this.oriented = oriented;
    }

    public boolean isNoId(){
        if (nature != null){
            return nature == MCDAssociationNature.NOID;
        }
        return false;
    }


    public boolean isIdNatural(){
        if (nature != null){
            return nature == MCDAssociationNature.IDNATURAL;
        }
        return false;
    }

    public boolean isIdComp(){
        if (nature != null){
            return nature == MCDAssociationNature.IDCOMP;
        }
        return false;
    }

    public boolean isCP(){
        if (nature != null){
            return nature == MCDAssociationNature.CP;
        }
        return false;
    }

    public MRelationDegree getDegree(){
        return MRelationService.computeDegree(getFrom().getMultiMaxStd(), getTo().getMultiMaxStd());
    }


    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }
}
