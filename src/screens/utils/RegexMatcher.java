package screens.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {

    public static boolean matchesRegex(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }


}
