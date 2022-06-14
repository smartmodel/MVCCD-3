package consolidationMpdrDb.ZelementASupprimer.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Tokenization {
    private static final Pattern PUNCTSPACE = Pattern.compile("[ \\p{Punct}]+");
    private static final Pattern TRANSITION = Pattern.compile(
            String.format("%s|%s|%s",
                    "(?<=[\\p{javaUpperCase}])(?=[\\p{javaUpperCase}][\\p{javaLowerCase}])",
                    "(?<=[^\\p{javaUpperCase}])(?=[\\p{javaUpperCase}])",
                    "(?<=[\\p{javaLowerCase}\\p{javaUpperCase}}])(?=[^\\p{javaLowerCase}\\p{javaUpperCase}])"
            )
    );

    public static List<String> split(String text) {
        List<String> result = new ArrayList<>();
        for (String word : PUNCTSPACE.split(text)) {
            if (word.isEmpty()) {
                continue;
            }
            Collections.addAll(result, TRANSITION.split(word));
        }
        return result.isEmpty() ? Collections.singletonList(text) : result;
    }

    public static List<String> lowercaseSplit(String text) {
        List<String> result = new ArrayList<>();
        for (String token : split(text)) {
            result.add(token.toLowerCase());
        }
        return result;
    }

    public static Boolean contains(List<String> lowerCaseTokens, List<String> keywords) {
        for (String keyword : keywords) {
            for (String token : lowerCaseTokens) {
                if (token.equals(keyword)) return true;
            }
        }
        return false;
    }
}
