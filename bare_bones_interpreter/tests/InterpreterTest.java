package tests;

import exceptions.SubroutineNotDefinedException;
import exceptions.UnrecognisedConditionException;
import exceptions.UnrecognisedStatementException;
import exceptions.VariableNotFoundException;
import interpreter.Interpreter;
import org.junit.jupiter.api.Test;
import statements.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {
    final Interpreter interpreter = new Interpreter();

    @Test
    void increment() {
        final List<BBStatement> statements = List.of(new ClearStatement("X"),
                new IncrementStatement("X"));

        try {
            interpreter.interpret(statements);
        } catch (VariableNotFoundException e) {
            e.printStackTrace();
        } catch (UnrecognisedConditionException e) {
            e.printStackTrace();
        } catch (UnrecognisedStatementException e) {
            e.printStackTrace();
        } catch (SubroutineNotDefinedException e) {
            e.printStackTrace();
        }

        assertEquals(1, interpreter.getVariable("X"));
    }

    @Test
    void subroutine() {
        final List<BBStatement> statements = new ArrayList<BBStatement>();
        statements.add(new ClearStatement("X"));
        statements.add(
                new SubroutineDefinitionStatement(
                        "addToX",
                        List.of(new IncrementStatement("X"))
                )
        );
        statements.add(new SubroutineStatement("addToX"));

        try {
            interpreter.interpret(statements);
        } catch (VariableNotFoundException e) {
            e.printStackTrace();
        } catch (UnrecognisedConditionException e) {
            e.printStackTrace();
        } catch (UnrecognisedStatementException e) {
            e.printStackTrace();
        } catch (SubroutineNotDefinedException e) {
            e.printStackTrace();
        }

        final int expected = 1;
        final int actual = interpreter.getVariable("X");


        assertEquals(expected, actual);


    }
}