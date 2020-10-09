package exceptions;

import conditions.BoolCondition;

public class UnrecognisedConditionException extends Exception {
    final BoolCondition condition;

    public UnrecognisedConditionException(BoolCondition condition) {
        this.condition = condition;
    }
}
