package guice.example6.module1;

public class Bar {

    private final String message;

    public Bar(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}