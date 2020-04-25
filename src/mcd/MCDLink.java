package mcd;

public class MCDLink extends MCDRelation {

    private  static final long serialVersionUID = 1000;


    public MCDLink(MCDElement parent) {

        super(parent);
    }

    public MCDLink(MCDElement parent, String name) {

        super(parent, name);
    }

    public MCDRelEnd getEntity() {
        return  super.getA();
    }

    public void setEntity(MCDRelEnd entity) {
        super.setA(entity);
    }

    public MCDRelEnd getAssociation() {
        return (MCDRelEnd) super.getB();
    }

    public void setAssociation(MCDRelEnd association) {
        super.setB(association);
    }

    @Override
    public String getNameTree() {
        return null;
    }


}
