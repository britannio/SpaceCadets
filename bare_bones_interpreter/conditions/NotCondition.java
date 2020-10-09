package conditions;

public class NotCondition extends BoolCondition {
    public static final String keyword = "not";
    public NotCondition(String variableName, int target) {
        super(variableName, target);
    }
}
