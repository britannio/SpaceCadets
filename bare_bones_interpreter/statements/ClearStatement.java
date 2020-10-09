package statements;

public class ClearStatement implements BBStatement {
    public final String variableName;

    public ClearStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ClearStatement) {
            final ClearStatement other = (ClearStatement) obj;
            return this.variableName.equals(other.variableName);
        } else {
            return false;
        }
    }
}

