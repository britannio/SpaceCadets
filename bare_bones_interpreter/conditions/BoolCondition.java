package conditions;

import statements.WhileStatement;

public abstract class BoolCondition {
    public final String variableName;
    public final int target;
    


    public BoolCondition(String variableName, int target) {
        this.variableName = variableName;
        this.target = target;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof BoolCondition) {
            final BoolCondition other = (BoolCondition) obj;
            return this.variableName.equals(other.variableName) && this.target == other.target;
        } else {
            return false;
        }
    }
}
