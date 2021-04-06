package m.services;

import exceptions.TransformMCDException;
import m.MRelEndMulti;
import m.MRelEndMultiPart;
import preferences.Preferences;

public class MRelEndService {

    private static String messageErrorMulti(String multi){
        return "Le multiplicité >" + multi + "< ne pas être pas interprétée.";
    }

    private static String getMultiMin(String multi){
        String[] parts = multi.split(Preferences.MULTI_OCL_SP);
        if (parts.length > 0){
            return parts[0];
        } else {
            return multi ;
        }
    }

    private static String getMultiMax(String multi){
        String[] parts = multi.split(Preferences.MULTI_OCL_SP);
        if (parts.length > 1){
            return parts[1];
        } else {
            return multi;
        }
    }

    public static Integer computeMultiMinCustom(String multi){
        String multiMin = getMultiMin(multi);
        if (multiMin.equals(MRelEndMultiPart.MULTI_MANY.getText())){
            return null;
        } else {
            try {
                Integer valueMin = Integer.valueOf(multiMin);
                if (valueMin > 1){
                    return valueMin;
                }
                return null;
            } catch (Exception e ){
                throw new TransformMCDException(messageErrorMulti(multi) + " Minimum custom.");
            }
        }
    }

    public static MRelEndMultiPart computeMultiMinStd(String multi){
        String multiMin = getMultiMin(multi);
        if (multiMin.equals(MRelEndMultiPart.MULTI_MANY.getText())){
            return MRelEndMultiPart.MULTI_ZERO;
        } else {
            try {
                Integer valueMin = Integer.valueOf(multiMin);
                if (valueMin == 0){
                    return MRelEndMultiPart.MULTI_ZERO;
                }
                return MRelEndMultiPart.MULTI_ONE;
            } catch (Exception e ){
                throw new TransformMCDException(messageErrorMulti(multi) + " Minimum standard.");
            }
        }
    }


    public static Integer computeMultiMaxCustom(String multi){
        String multiMax = getMultiMax(multi);
        if (multiMax.equals(MRelEndMultiPart.MULTI_MANY.getText()) ||
                multiMax.equals(Preferences.MULTI_MERISE_N)){
            return null;
        } else {
            try {
                Integer valueMax = Integer.valueOf(multiMax);
                if (valueMax > 1){
                    return valueMax;
                }
                return null;
            } catch (Exception e ){
                throw new TransformMCDException(messageErrorMulti(multi) + " Maximum custom.");
            }
        }
    }

    public static MRelEndMultiPart computeMultiMaxStd(String multi){
        String multiMax = getMultiMax(multi);
        if (multiMax.equals(MRelEndMultiPart.MULTI_MANY.getText()) ||
                multiMax.equals(Preferences.MULTI_MERISE_N)){
            return MRelEndMultiPart.MULTI_MANY;
        } else {
            try {
                Integer valueMax = Integer.valueOf(multiMax);
                if (valueMax > 1){
                    return MRelEndMultiPart.MULTI_MANY;
                }
                return MRelEndMultiPart.MULTI_ONE;
            } catch (Exception e ){
                throw new TransformMCDException(messageErrorMulti(multi) + " Maximum standard.");
            }
        }
    }

    public static MRelEndMulti computeMultiStd (String multi){

        MRelEndMultiPart multiMinStd = computeMultiMinStd(multi);
        MRelEndMultiPart multiMaxStd = computeMultiMaxStd(multi);

        if (multiMinStd == MRelEndMultiPart.MULTI_ZERO){
            if (multiMaxStd == MRelEndMultiPart.MULTI_ONE){
                return MRelEndMulti.MULTI_ZERO_ONE;
            } else {
                return MRelEndMulti.MULTI_ZERO_MANY;
            }
        } else {
            if (multiMaxStd == MRelEndMultiPart.MULTI_ONE){
                return MRelEndMulti.MULTI_ONE_ONE;
            } else {
                return MRelEndMulti.MULTI_ONE_MANY;
            }
        }
    }


}
