package expression;

import java.util.Objects;

public class Const implements CommonExpression {
    private int value;
    private double dobValue;
    private boolean isDouble;

    public Const (int value) {
        isDouble = false;
        this.value = value;
    }

    public Const(double dobValue) {
        isDouble = true;
        this.dobValue = dobValue;
    }
    public String toString() {
        if (isDouble){
            return String.valueOf(dobValue);
        }
        return String.valueOf(value);
    }
    public int hashCode() {
        if (isDouble){
            return Objects.hash(dobValue);
        }
        return Objects.hash(value);
    }
    public int evaluate(int x) {
        return value;
    }

    public double evaluate(double x) {
        if (isDouble)
            return dobValue;
        return value;
    }
    public boolean equals(Object object) {
        if (object != null && object.getClass() == this.getClass()) {
            return this.value == ((Const) object).value;
        }
        return false;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }
}
