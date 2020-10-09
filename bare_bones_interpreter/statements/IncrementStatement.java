package statements;

public class IncrementStatement implements BBStatement {
    public final String variableName;

    public IncrementStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof IncrementStatement) {
            final IncrementStatement other = (IncrementStatement) obj;
            return this.variableName.equals(other.variableName);
        } else {
            return false;
        }
    }
}
