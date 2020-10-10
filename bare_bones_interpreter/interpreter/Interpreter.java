package interpreter;

import conditions.BoolCondition;
import conditions.NotCondition;
import exceptions.SubroutineNotDefinedException;
import exceptions.UnrecognisedConditionException;
import exceptions.UnrecognisedStatementException;
import exceptions.VariableNotFoundException;
import statements.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
        Map<String, Integer> variables;
        Map<String, List<BBStatement>> subroutines;

    public void interpret(List<BBStatement> statements) throws VariableNotFoundException, UnrecognisedConditionException, UnrecognisedStatementException, SubroutineNotDefinedException {
        variables = new HashMap<String, Integer>();
        subroutines = new HashMap<String, List<BBStatement>>();

        System.out.println(statements);

        evaluateStatements(statements);

        outputVariables();
    }

    public Integer getVariable(String name) {
        return variables.get(name);
    }

    private void evaluateStatements(List<BBStatement> statements) throws VariableNotFoundException, UnrecognisedConditionException, UnrecognisedStatementException, SubroutineNotDefinedException {
        for (BBStatement statement: statements) {
            evaluateStatement(statement);
        }
    }

    private void evaluateStatement(BBStatement statement) throws VariableNotFoundException, UnrecognisedStatementException, UnrecognisedConditionException, SubroutineNotDefinedException {
        if (statement instanceof IncrementStatement) {
            evaluateIncrementStatement((IncrementStatement) statement);
        } else if (statement instanceof DecrementStatement) {
            evaluateDecrementStatement((DecrementStatement) statement);
        } else if (statement instanceof ClearStatement) {
            evaluateClearStatement((ClearStatement) statement);
        } else if (statement instanceof WhileStatement) {
            evaluateWhileStatement((WhileStatement) statement);
        } else if (statement instanceof SubroutineDefinitionStatement) {
            evaluateSubroutineDefinitionStatement((SubroutineDefinitionStatement) statement);
        } else if (statement instanceof SubroutineStatement) {
            evaluateSubroutineStatement((SubroutineStatement) statement);
        } else {
            throw new UnrecognisedStatementException(statement);
        }
    }


    private boolean evaluateCondition(BoolCondition condition) throws UnrecognisedConditionException {
        if (condition instanceof NotCondition) {
            final int value = getVariable(condition.variableName);
            return value != condition.target;
        } else {
            throw new UnrecognisedConditionException(condition);
        }
    }

    private void evaluateWhileStatement(WhileStatement statement) throws VariableNotFoundException, UnrecognisedConditionException, UnrecognisedStatementException, SubroutineNotDefinedException {
        final BoolCondition condition = statement.condition;
        while(evaluateCondition(condition)) {
            evaluateStatements(statement.statements);
        }
    }

    private void evaluateIncrementStatement(IncrementStatement statement) throws VariableNotFoundException {
        System.out.println("Incrementing " + statement.variableName + "");
        final Integer currentValue = getVariable(statement.variableName);

        if (currentValue == null) {
            throw new VariableNotFoundException(statement.variableName);
        } else {
            variables.replace(statement.variableName, currentValue + 1);
        }

        System.out.println(currentValue + " -> " + getVariable(statement.variableName));

    }

    private void evaluateDecrementStatement(DecrementStatement statement) throws VariableNotFoundException {
        System.out.println("Decrementing " + statement.variableName);
        final Integer currentValue = getVariable(statement.variableName);

        if (currentValue == null) {
            throw new VariableNotFoundException(statement.variableName);
        } else {
            variables.replace(statement.variableName, currentValue - 1);
        }

        System.out.println(currentValue + " -> " + getVariable(statement.variableName));
    }

    private void evaluateClearStatement(ClearStatement statement) {
        System.out.println("Zeroing " + statement.variableName);
        System.out.println(getVariable(statement.variableName) + " -> 0");

        variables.put(statement.variableName, 0);
    }

    private void evaluateSubroutineDefinitionStatement(SubroutineDefinitionStatement statement) {
        System.out.println("Defining subroutine: " + statement.name);

        subroutines.put(statement.name, statement.statements);
    }

    private void evaluateSubroutineStatement(SubroutineStatement statement) throws SubroutineNotDefinedException, UnrecognisedConditionException, VariableNotFoundException, UnrecognisedStatementException {
        System.out.println("Invoking subroutine: " + statement.name);

        final List<BBStatement> statements = subroutines.get(statement.name);

        if (statements == null) {
            throw new SubroutineNotDefinedException();
        }

        evaluateStatements(statements);
    }

    private void outputVariables() {
        System.out.println("┌───────────┐");
        System.out.println("│ Variables │");
        System.out.println("├─────┬─────┘");
        variables.forEach( (key,value) -> System.out.println("│ " + key + "=" + value + " │"));
    }

}
