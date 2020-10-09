package matchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubroutineDefinitionMatcher {
    public final boolean matchesStart;
    public final boolean matchesEnd;
    public final String name;

    private final Pattern pattern = Pattern.compile("(\\w*)\\(\\) do;");
//    (\w*)\(\);

    public SubroutineDefinitionMatcher(String line) {
        this.matchesStart = matchesStart(line);
        this.matchesEnd = matchesEnd(line);
        this.name = name(line);
    }

    private boolean matchesStart(String line) {
        final Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    private boolean matchesEnd(String line) {
        Matcher matcher = Pattern.compile("end;").matcher(line);
        return matcher.find();
    }

    private String name(String line) {
        final Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);
    }

}
