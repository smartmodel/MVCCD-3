package m;

public enum MRelEndMultiPart {
    MULTI_ZERO ("0"),
    MULTI_ONE ("1"),
    MULTI_MANY ("*");

    private final String text;

    MRelEndMultiPart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
