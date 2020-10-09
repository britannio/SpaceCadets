package matchers;

import statements.ClearStatement;

public class ClearMatcher extends VariableOperationMatcher {
    public ClearMatcher(String line) {
        super("clear", line);
    }

    public ClearStatement statement() {
        return new ClearStatement(variableName);
    }
    
}
