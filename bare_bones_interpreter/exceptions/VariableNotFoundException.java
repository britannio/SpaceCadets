package exceptions;

public class VariableNotFoundException extends Exception {
    final String name;

    public VariableNotFoundException(String name) {
        this.name = name;
    }
}
