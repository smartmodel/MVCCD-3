package AttributesGrid;

public class Attribute {
    private String num;
    private final String nom;
    private final String type;
    private final boolean obligatoire;

    public Attribute(String num, String nom, String type, boolean obligatoire) {
        this.num = num;
        this.nom = nom;
        this.type = type;
        this.obligatoire = obligatoire;
    }

    public String getNum() {
        return num;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public boolean isObligatoire() {
        return obligatoire;
    }

    public void setNum(String num) {
        this.num = num;
    }
}