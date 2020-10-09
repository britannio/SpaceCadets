package matchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import conditions.BoolCondition;
import conditions.NotCondition;

public class WhileMatcher {
    private final Pattern pattern = Pattern.compile("while (\\w) (\\w*) (\\d) do;");

    public final boolean matchesStart;
    public final BoolCondition condition;
	public final boolean matchesEnd;

    public WhileMatcher(String line) {
        this.matchesStart = matchesStart(line);
        this.condition = condition(line);
        this.matchesEnd = matchesEnd(line);
        
    }

    private boolean matchesStart(String line) {
        final Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    private boolean matchesEnd(String line) {
        Matcher matcher = Pattern.compile("end;").matcher(line);
        return matcher.find();
    }

    private BoolCondition condition(String line) {
        final Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            return null;
        }

        final String variableName = matcher.group(1);
        final String condition = matcher.group(2);
        final int target = Integer.valueOf(matcher.group(3));


        switch (condition) {
            case (NotCondition.keyword):
                return new NotCondition(variableName, target);
            default:
                return null;
        }

    }


    
}
