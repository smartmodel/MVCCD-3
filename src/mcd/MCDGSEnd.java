package mcd;

public class MCDGSEnd extends MCDRelEnd  {

    private static final long serialVersionUID = 1000;

    //private MCDGeneralization mcdGeneralization;
    //private MCDEntity mcdEntity ;
    private int drawingDirection ;


    public MCDGSEnd(MCDElement parent) {
        super(parent);
    }

    public MCDGSEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDGeneralization getMcdGeneralization() {
        return (MCDGeneralization) super.getMcdRelation();
    }

    public void setMcdGeneralization(MCDGeneralization mcdGeneralization) {
        super.setMcdRelation(mcdGeneralization);
    }

    public MCDEntity getMcdEntity() {
        return (MCDEntity) super.getMcdElement();
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        super.setMcdElement(mcdEntity);
    }

    public int getDrawingDirection() {
        return drawingDirection;
    }

    public void setDrawingDirection(int drawingDirection) {
        this.drawingDirection = drawingDirection;
    }


    @Override
    public String getNameTree() {

        String resultat = "";
/*
        MVCCDElement containerEntity = this.getMcdEntity().getParent().getParent();

        MCDAssociation mcdAssociation = getMcdAssociation();
        MVCCDElement containerAssociation = this.getMcdAssociation().getParent().getParent();

        MCDGSEnd mcdAssEndOpposite = mcdAssociation.getMCDAssEndOpposite(this);
        MCDEntity mcdEntityOpposite = mcdAssEndOpposite.getMcdEntity();
        MVCCDElement containerEntityOpposite = mcdEntityOpposite.getParent().getParent();

        boolean c1a = containerEntity == containerAssociation;
        boolean c1b = containerEntityOpposite == containerAssociation;
        boolean c1 = c1a && c1b;
        String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
        boolean c3 = treeNaming.equals(Preferences.MCD_NAMING_NAME);
        boolean c4 = treeNaming.equals(Preferences.MCD_NAMING_SHORT_NAME);

        boolean r1 = c1 && c3;
        boolean r2 = c1 && c4;
        boolean r3 = (!c1) && c3;
        boolean r4 = (!c1) && c4;

        String nameEntityOpposite = "";


        if (r1){
            nameEntityOpposite = mcdEntityOpposite.getName();
        }

        if (r2){
            nameEntityOpposite = mcdEntityOpposite.getShortNameSmart();
        }

        if (r3){
            nameEntityOpposite = mcdEntityOpposite.getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if (r4){
            nameEntityOpposite = mcdEntityOpposite.getShortNameSmartPath();
        }

        String namingAssociation ;
        if (StringUtils.isNotEmpty(this.getName()) && StringUtils.isNotEmpty(mcdAssEndOpposite.getName())){
            namingAssociation = this.getName();
            if (this.getDrawingDirection() == MCDGSEnd.FROM){
                namingAssociation = namingAssociation +
                                Preferences.MCD_NAMING_ASSOCIATION_ARROW_RIGHT ;

            } else {
                namingAssociation = namingAssociation  +
                        Preferences.MCD_NAMING_ASSOCIATION_ARROW_LEFT ;
             }
        } else {
            namingAssociation =
                    mcdAssociation.getName() + Preferences.MCD_NAMING_ASSOCIATION_SEPARATOR;
        }

        resultat = namingAssociation + nameEntityOpposite;
    */
        return resultat;
    }


    public MCDGSEnd getMCDGSEndOpposite() {
        MCDGeneralization mcdGeneralization = getMcdGeneralization();
        return mcdGeneralization.getMCDAssGSOpposite(this);
    }



}
