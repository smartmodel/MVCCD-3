package mcd;

public class MCDAssEnd extends MCDRelEnd{

    private static final long serialVersionUID = 1000;

    private MCDAssociation mcdAssociation;
    private MCDEntity mcdEntity ;



    public MCDAssEnd(MCDElement parent) {
        super(parent);
    }

    public MCDAssEnd(MCDElement parent, String name) {
        super(parent, name);
    }

    public MCDAssociation getMcdAssociation() {
        return (MCDAssociation) super.getMcdRelation() ;
    }

    public void setMcdAssociation(MCDAssociation mcdAssociation) {
        super.setMcdRelation(mcdAssociation);
    }

    public MCDEntity getMcdEntity() {
        return (MCDEntity) super.getMcdElement();
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        super.setMcdElement(mcdEntity);
    }

    @Override
    public String getNameTree() {
        return null;
    }
}
