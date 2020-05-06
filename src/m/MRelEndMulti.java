package m;

public enum MRelEndMulti {
    MULTI_ZERO_ONE ("0..1"),
    MULTI_ONE_ONE ("1..1"),
    MULTI_ZERO_MANY ("0..*"),
    MULTI_ONE_MANY ("1..*");

    private final String text;

    MRelEndMulti(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
