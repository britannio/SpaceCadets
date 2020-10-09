package statements;

public class DecrementStatement implements BBStatement {
    public final String variableName;

    public DecrementStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof DecrementStatement) {
            final DecrementStatement other = (DecrementStatement) obj;
            return this.variableName.equals(other.variableName);
        } else {
            return false;
        }
    }

}