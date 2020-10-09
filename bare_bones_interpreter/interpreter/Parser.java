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

        return parseLines(scriptLines);
    }

    private List<BBStatement> parseLines(List<String> lines) {
        final List<BBStatement> statements = new ArrayList<BBStatement>();

        final ListIterator<String> iterator = lines.listIterator();

        while (iterator.hasNext()) {
            String line = iterator.next();

            final IncrementMatcher incrementMatcher = new IncrementMatcher(line);
            final DecrementMatcher decrementMatcher = new DecrementMatcher(line);
            final ClearMatcher clearMatcher = new ClearMatcher(line);
            final WhileMatcher whileMatcher = new WhileMatcher(line);
            final SubroutineDefinitionMatcher subroutineDefinitionMatcher = new SubroutineDefinitionMatcher(line);
            final SubroutineMatcher subroutineMatcher = new SubroutineMatcher(line);

            if (clearMatcher.matches) {
                statements.add(clearMatcher.statement());

            } else if (incrementMatcher.matches) {
                statements.add(incrementMatcher.statement());

            } else if (decrementMatcher.matches) {
                statements.add(decrementMatcher.statement());

            } else if (whileMatcher.matchesStart) {
                statements.add(parseWhileLoop(whileMatcher.condition, iterator));

            } else if (subroutineDefinitionMatcher.matchesStart) {
                statements.add(parseSubroutine(subroutineDefinitionMatcher.name, iterator));

            } else if (subroutineMatcher.matches) {
                statements.add(subroutineMatcher.statement());
            } else {

            }
        }

        return statements;
    }

    private WhileStatement parseWhileLoop(BoolCondition condition, ListIterator<String> iterator) {
        System.out.println("Parsing while loop with condition: " + condition.variableName + " " + condition.target);
        final List<BBStatement> statements = new ArrayList<BBStatement>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            final WhileMatcher whileMatcher = new WhileMatcher(line);

            if (whileMatcher.matchesStart) {
                statements.add(parseWhileLoop(whileMatcher.condition, iterator));
            } else if (whileMatcher.matchesEnd) {
                break;
            } else {
                final IncrementMatcher incrementMatcher = new IncrementMatcher(line);
                final DecrementMatcher decrementMatcher = new DecrementMatcher(line);
                final ClearMatcher clearMatcher = new ClearMatcher(line);
                final SubroutineDefinitionMatcher subroutineDefinitionMatcher = new SubroutineDefinitionMatcher(line);
                final SubroutineMatcher subroutineMatcher = new SubroutineMatcher(line);

                if (clearMatcher.matches) {
                    statements.add(clearMatcher.statement());
                } else if (incrementMatcher.matches) {
                    statements.add(incrementMatcher.statement());
                } else if (decrementMatcher.matches) {
                    statements.add(decrementMatcher.statement());
                } else if (subroutineDefinitionMatcher.matchesStart) {
                    statements.add(parseSubroutine(subroutineDefinitionMatcher.name, iterator));
                } else if (subroutineMatcher.matches) {
                    statements.add(subroutineMatcher.statement());
                } else {

                }
            }

        }

        return new WhileStatement(condition, statements);
    }

    private SubroutineDefinitionStatement parseSubroutine(String name, ListIterator<String> iterator) {
        final List<BBStatement> statements = new ArrayList<BBStatement>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            final SubroutineDefinitionMatcher matcher = new SubroutineDefinitionMatcher(line);

            if (matcher.matchesStart) {
                statements.add(parseSubroutine(matcher.name, iterator));
            } else if (matcher.matchesEnd) {
                break;
            } else {
                final IncrementMatcher incrementMatcher = new IncrementMatcher(line);
                final DecrementMatcher decrementMatcher = new DecrementMatcher(line);
                final ClearMatcher clearMatcher = new ClearMatcher(line);
                final WhileMatcher whileMatcher = new WhileMatcher(line);
                final SubroutineMatcher subroutineMatcher = new SubroutineMatcher(line);


                if (clearMatcher.matches) {
                    statements.add(clearMatcher.statement());
                } else if (incrementMatcher.matches) {
                    statements.add(incrementMatcher.statement());
                } else if (decrementMatcher.matches) {
                    statements.add(decrementMatcher.statement());
                }else if (whileMatcher.matchesStart) {
                    statements.add(parseWhileLoop(whileMatcher.condition, iterator));
                } else if (subroutineMatcher.matches) {
                    statements.add(subroutineMatcher.statement());
                } else {

                }
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
        final File file = new File("/Users/britannio/Software/Java/SCChallengeBareBones/src/scripts/" + filename);

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
