package matchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import statements.SubroutineStatement;

public class SubroutineMatcher {
    public final boolean matches;
    public final String name;

    private final Pattern pattern = Pattern.compile("(\\w*)\\(\\);");

    public SubroutineMatcher(String line) {
        this.matches = matches(line);
        this.name = name(line);
    }

    private boolean matches(String line) {
        final Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    private String name(String line) {
        final Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);
    }

    public SubroutineStatement statement() {
        return new SubroutineStatement(name);
    }

}
