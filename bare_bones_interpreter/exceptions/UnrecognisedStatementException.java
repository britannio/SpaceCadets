package exceptions;

import statements.BBStatement;

public class UnrecognisedStatementException extends Exception{
    final BBStatement statement;

    public UnrecognisedStatementException(BBStatement statement) {
        this.statement = statement;
    }
}
