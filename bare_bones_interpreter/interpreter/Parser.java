package interpreter;

import statements.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import conditions.BoolCondition;
import matchers.*;

public class Parser {
    // Converts the script into objects
    public List<BBStatement> parseFile(String scriptPath) {
        String rawScript = readFile(scriptPath);

        return parseString(rawScript);
    }

    public List<BBStatement> parseString(String script) {
        String rawScript = script;

        rawScript = removeComments(rawScript);

        List<String> scriptLines = new ArrayList<>(List.of(rawScript.split("\n")));

        scriptLines = removeEmptyLines(scriptLines);
        scriptLines = removeLeadingWhitespace(scriptLines);

        final ListIterator<String> iterator = scriptLines.listIterator();

        return parseLines(iterator);
    }

    private List<BBStatement> parseLines(ListIterator<String> iterator) {
        final List<BBStatement> statements = new ArrayList<BBStatement>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            statements.add(parseLine(line, iterator));
        }

        return statements;
    }

    private BBStatement parseLine(String line, ListIterator<String> iterator) {
        final IncrementMatcher incrementMatcher = new IncrementMatcher(line);
        final DecrementMatcher decrementMatcher = new DecrementMatcher(line);
        final ClearMatcher clearMatcher = new ClearMatcher(line);
        final WhileMatcher whileMatcher = new WhileMatcher(line);
        final SubroutineDefinitionMatcher subroutineDefinitionMatcher = new SubroutineDefinitionMatcher(line);
        final SubroutineMatcher subroutineMatcher = new SubroutineMatcher(line);

        if (clearMatcher.matches) {
            return clearMatcher.statement();

        } else if (incrementMatcher.matches) {
            return incrementMatcher.statement();

        } else if (decrementMatcher.matches) {
            return decrementMatcher.statement();

        } else if (whileMatcher.matchesStart) {
            return parseWhileLoop(whileMatcher.condition, iterator);

        } else if (subroutineDefinitionMatcher.matchesStart) {
            return parseSubroutine(subroutineDefinitionMatcher.name, iterator);

        } else if (subroutineMatcher.matches) {
            return subroutineMatcher.statement();

        } else {
            return null;
        }
    }

    private WhileStatement parseWhileLoop(BoolCondition condition, ListIterator<String> iterator) {
        System.out.println("Parsing while loop with condition: " + condition.variableName + " " + condition.target);
        final List<BBStatement> statements = new ArrayList<BBStatement>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            final WhileMatcher whileMatcher = new WhileMatcher(line);

            if (whileMatcher.matchesEnd) {
                break;
            } else {
                statements.add(parseLine(line, iterator));
            }

        }

        return new WhileStatement(condition, statements);
    }

    private SubroutineDefinitionStatement parseSubroutine(String name, ListIterator<String> iterator) {
        final List<BBStatement> statements = new ArrayList<BBStatement>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            final SubroutineDefinitionMatcher matcher = new SubroutineDefinitionMatcher(line);

            if (matcher.matchesEnd) {
                break;
            } else {
                statements.add(parseLine(line, iterator));
            }

        }

        return new SubroutineDefinitionStatement(name, statements);

    }

    private String removeComments(String script) {
        // (\/\*[^*/]*\*\/)|(\/\/.*)
        return script.replaceAll("(\\/\\*[^*/]*\\*\\/)|(\\/\\/.*)", "");
    }

    private List<String> removeEmptyLines(List<String> lines) {
        final List<String> newLines = new ArrayList<String>(lines);
        newLines.removeIf((String line) -> line.trim().isEmpty());
        return newLines;
    }

    private List<String> removeLeadingWhitespace(List<String> lines) {
        final List<String> newLines = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++) {
            newLines.add(lines.get(i).trim());
        }
        return newLines;
    }

    private String readFile(String filename) {
        // TODO use a relative path
        final File file = new File(
                "/Users/britannio/Software/Java/SpaceCadets/bare_bones_interpreter/scripts/" + filename);

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuffer buffer = new StringBuffer();

            while (true) {
                String nextLine;
                nextLine = reader.readLine();

                if (nextLine == null)
                    break;

                buffer.append(nextLine + '\n');
            }

            reader.close();

            return buffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
