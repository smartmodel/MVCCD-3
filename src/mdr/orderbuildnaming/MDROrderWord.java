package mdr.orderbuildnaming;

public abstract class MDROrderWord {

    private String name ;
    private Integer lengthMax ;
    private String value = null ;

    public MDROrderWord(String name, Integer lengthMax) {
        this.name = name;
        this.lengthMax = lengthMax;
    }

    public String getName() {
        return name;
    }

    public Integer getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(Integer lengthMax) {
        this.lengthMax = lengthMax;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
