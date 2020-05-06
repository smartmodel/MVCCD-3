package m;

public enum MRelEndMultiStr {

    MULTI_ZERO_TO_ONE ("0..1"),
    MULTI_ZERO_TO_MANY ("0..*"),
    MULTI_ZERO_TO_N ("0..n"),
    MULTI_MANY ("*"),
    MULTI_ONE ("1"),
    MULTI_ONE_ONE ("1..1"),
    MULTI_ONE_TO_MANY ("1..*"),
    MULTI_ONE_TO_N ("1..n");

    private final String text;

    MRelEndMultiStr(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static MRelEndMultiStr findByText(String pText){
        for (MRelEndMultiStr element: MRelEndMultiStr.values()){
            if (element.text.equals(pText)) {
                return element;
            }
        }
        return null;
    }


}
