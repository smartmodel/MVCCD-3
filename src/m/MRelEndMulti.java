package m;

import exceptions.CodeApplException;
import utilities.Trace;

public enum MRelEndMulti {
    MULTI_ZERO_ONE("0..1"),
    MULTI_ONE_ONE("1..1"),
    MULTI_ZERO_MANY("0..*"),
    MULTI_ONE_MANY("1..*");

    private final String text;

    MRelEndMulti(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static MRelEndMulti findByTwoValues(MRelEndMultiPart min,
                                        MRelEndMultiPart max) {
        if (min == MRelEndMultiPart.MULTI_ZERO) {
            if (max == MRelEndMultiPart.MULTI_ONE) {
                return MULTI_ZERO_ONE;
            }
            if (max == MRelEndMultiPart.MULTI_MANY) {
                return MULTI_ZERO_MANY;
            }
        }
        if (min == MRelEndMultiPart.MULTI_ONE) {
            if (max == MRelEndMultiPart.MULTI_ONE) {
                return MULTI_ONE_ONE;
            }
            if (max == MRelEndMultiPart.MULTI_MANY) {
                return MULTI_ONE_MANY;
            }
        }
        throw new CodeApplException("Les 2 valeurs " + min.getText() + " et " + max.getText() + " passées pour créer trouver une instance de MRelEndMulti ne sont pas cohérentes");
    }

}
