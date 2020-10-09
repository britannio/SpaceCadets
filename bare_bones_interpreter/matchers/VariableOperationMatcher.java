package matchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class VariableOperationMatcher {
    final Pattern pattern;
    public final boolean matches;
    public final String variableName;

    public VariableOperationMatcher(String keyword, String line) {
        this.pattern = Pattern.compile(keyword + " (\\w);");
        this.matches = matches(line);
        this.variableName = getVariableName(line);
    }

    private boolean matches(String line) {
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }


    private String getVariableName(String line) {
        final Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) return null;
        return matcher.group(1);

    }


}
