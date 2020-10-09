package statements;

import java.util.List;

public class SubroutineDefinitionStatement implements BBStatement {
    public final String name;
    public final List<BBStatement> statements;

    public SubroutineDefinitionStatement(String name, List<BBStatement> statements) {
        this.name = name;
        this.statements = statements;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SubroutineDefinitionStatement) {
            final SubroutineDefinitionStatement other = (SubroutineDefinitionStatement) obj;
            return this.name.equals(other.name) && this.statements.equals(other.statements);
        } else {
            return false;
        }
    }
}
