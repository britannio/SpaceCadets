package matchers;

import statements.IncrementStatement;

public class IncrementMatcher extends VariableOperationMatcher {
    public IncrementMatcher(String line) {
        super("incr", line);
    }

    public IncrementStatement statement() {
        return new IncrementStatement(variableName);
    }
    
}
