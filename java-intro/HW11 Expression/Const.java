package expression;

import java.util.Objects;

public class Const implements CommonExpression {
    private int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        return value == ((Const) object).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
