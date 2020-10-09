package statements;

import java.util.List;

import conditions.BoolCondition;


public class WhileStatement implements BBStatement {
    public final List<BBStatement> statements;
    public final BoolCondition condition;


    public WhileStatement(BoolCondition condition, List<BBStatement> statements) {
        this.statements = statements;
        this.condition = condition;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof WhileStatement) {
            final WhileStatement other = (WhileStatement) obj;
            return this.condition.equals(other.condition) && this.statements.equals(other.statements);
        } else {
            return false;
        }
    }



}
