package window.editor.diagrammer.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeometryUtils {
    public static double roundDouble(double number, int nbDecimals){
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(nbDecimals, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
