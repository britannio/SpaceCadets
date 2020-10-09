import java.util.List;

import exceptions.SubroutineNotDefinedException;
import exceptions.UnrecognisedConditionException;
import exceptions.UnrecognisedStatementException;
import exceptions.VariableNotFoundException;
import interpreter.Interpreter;
import interpreter.Parser;
import statements.*;

public class Main {
    public static void main(String[] args) {

        String scriptName = "test.bbo";

       if (args.length > 0 && args[0] != null) {
               scriptName = args[0];
       }

        final Parser parser = new Parser();
        final List<BBStatement> statements = parser.parseFile(scriptName);

        final Interpreter interpreter = new Interpreter();
        try {
            interpreter.interpret(statements);
        } catch (VariableNotFoundException | UnrecognisedConditionException | UnrecognisedStatementException | SubroutineNotDefinedException e) {
            e.printStackTrace();
        }
    }
}

 /* final BBStatement[] testScript = {
                new ClearStatement("X"),
                new IncrementStatement("X"),
                new IncrementStatement("X"),
                new IncrementStatement("X"),
                new WhileStatement(new NotCondition("X", 0),new ArrayList(new DecrementStatement("X")))

        };


        final BBStatement[] multiplicationScript = {
                new ClearStatement("X"),
                new IncrementStatement("X"),
                new IncrementStatement("X"),
                new ClearStatement("Y"),
                new IncrementStatement("Y"),
                new IncrementStatement("Y"),
                new IncrementStatement("Y"),
                new ClearStatement("Z"),
                new WhileStatement(new NotCondition("X", 0), new BBStatement[]{

                        new ClearStatement("W"),
                        new WhileStatement(new NotCondition("Y", 0), new BBStatement[]{

                                new IncrementStatement("Z"),
                                new IncrementStatement("W"),
                                new DecrementStatement("Y")
                        }),
                        new WhileStatement(new NotCondition("W", 0), new BBStatement[]{

                                new IncrementStatement("Y"),
                                new DecrementStatement("W")
                        }),
                        new DecrementStatement("X")

                })

        }; */