package matchers;

import statements.DecrementStatement;

public class DecrementMatcher extends VariableOperationMatcher {
    public DecrementMatcher(String line) {
        super("decr", line);
    }

    public DecrementStatement statement() {
        return new DecrementStatement(variableName);
    }
    
}
