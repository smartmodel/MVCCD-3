package AttributesGrid;

public class Attribute {
    private int id;
    private String num;
    private final String nom;
    private final String type;
    private final boolean obligatoire;
    private boolean aid;

    public Attribute(int id, String nom, String type, boolean obligatoire, boolean aid) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.obligatoire = obligatoire;
        this.aid = aid;
        //Permet de changer la chaine de caractère contenant un numéro en aid
        setNum(aid);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if(this.aid)
            this.num = "aid";
        else
            this.num = "" + id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getNum() {
        return num;
    }

    public boolean isObligatoire() {
        return obligatoire;
    }

    public boolean isAid() {
        return aid;
    }

    public void setAid(boolean aid) {
        this.aid = aid;
        //Permet de changer la chaine de caractère contenant un numéro en aid
        setNum(aid);
    }

    public void setNum(boolean aid) {
        if(aid)
            this.num = "aid";
        else
            this.num = "" + this.id;
    }

    public void printAttribut() {
        System.out.println("Num : " + this.num +
                "  Nom : " + this.nom +
                "  Type : " + this.type);
    }
}