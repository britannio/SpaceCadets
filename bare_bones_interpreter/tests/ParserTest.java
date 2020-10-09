package tests;

import conditions.BoolCondition;
import conditions.NotCondition;
import statements.*;

import java.util.List;

import interpreter.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    final Parser parser = new Parser();

    @Test
    void incrementStatement() {
        final String script = "incr X;";

        final List<BBStatement> statements = parser.parseString(script);

        final IncrementStatement expected = new IncrementStatement("X");
        final IncrementStatement actual = (IncrementStatement) statements.get(0);

        assertEquals(expected, actual);
    }

    @Test
    void decrementStatement() {
        final String script = "decr X;";

        final List<BBStatement> statements = parser.parseString(script);

        final DecrementStatement expected = new DecrementStatement("X");
        final DecrementStatement actual = (DecrementStatement) statements.get(0);

        assertEquals(expected, actual);
    }

    @Test
    void clearStatement() {
        final String script = "clear X;";

        final List<BBStatement> statements = parser.parseString(script);

        final ClearStatement expected = new ClearStatement("X");
        final ClearStatement actual = (ClearStatement) statements.get(0);

        assertEquals(expected, actual);
    }


    @Test
    void whileStatement() {
        final String script = "while X not 0 do;\n" +
                "\tdecr X;\n" +
                "end;";

        System.out.println(script);

        final List<BBStatement> statements = parser.parseString(script);

        final WhileStatement expected = new WhileStatement(new NotCondition("X", 0),
                List.of(new DecrementStatement("X")));
        final WhileStatement actual = (WhileStatement) statements.get(0);

        assertEquals(expected, actual);
    }

    @Test
    void nestedWhileStatement() {
        final String script = "while X not 0 do;\n" +
                "\tdecr X;\n" +
                "\twhile Y not 1 do;\n" +
                "\t\tdecr Y;\n" +
                "\tend;\n" +
                "end;\n";

        System.out.println(script);

        final List<BBStatement> statements = parser.parseString(script);

        final WhileStatement expected = new WhileStatement(new NotCondition("X", 0),
                List.of(new DecrementStatement("X"),
                        new WhileStatement(new NotCondition("Y", 1),
                                List.of(new DecrementStatement("Y")))));
        final WhileStatement actual = (WhileStatement) statements.get(0);

        assertEquals(expected, actual);
    }

    @Test
    void singleLineComments() {
        final String script = "clear X;\n" + "// comment here \n" + "incr X;\n";

        System.out.println(script);

        final List<BBStatement> expected = List.of(
                new ClearStatement("X"),
                new IncrementStatement("X")
        );

        final List<BBStatement> actual = parser.parseString(script);


        assertEquals(expected, actual);
    }

    @Test
    void subroutine() {
        final String script = "addToX() do;\n" + "\tincr X;\n" + "\tincr X;\n" + "end;\n" + "addToX();";

        System.out.println(script);

        final List<BBStatement> expected = List.of(
                new SubroutineDefinitionStatement("addToX", List.of(new IncrementStatement("X"),
                        new IncrementStatement("X"))),
                new SubroutineStatement("addToX")
        );

        final List<BBStatement> actual = parser.parseString(script);


        assertEquals(expected, actual);
    }

}