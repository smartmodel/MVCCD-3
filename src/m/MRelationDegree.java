package m;

public enum MRelationDegree {
    DEGREE_ONE_ONE ("1:1"),
    DEGREE_ONE_MANY ("1:n"),
    DEGREE_MANY_MANY ("n:n");

    private final String text;

    MRelationDegree(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
