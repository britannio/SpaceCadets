package statements;

public class SubroutineStatement implements BBStatement {
    public final String name;

    public SubroutineStatement(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SubroutineStatement) {
            final SubroutineStatement other = (SubroutineStatement) obj;
            return this.name.equals(other.name);
        } else {
            return false;
        }
    }
}
