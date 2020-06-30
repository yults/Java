package expression.generic;

import expression.TripleExpression;
import expression.exceptions.*;
import expression.types.*;
import expression.parser.*;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return parsing(getType(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] parsing(Type<T> mode, String expression, int x1, int x2, int y1,
                                                    int y2, int z1, int z2) throws BracketExpectedException, OverflowException, UnaryExpectedException, UnknownSymbolException {
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpression<T> curExpression = new ExpressionParser<T>(mode).parse(expression);
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[i].length; j++) {
                for (int k = 0; k < ans[i][j].length; k++) {
                    try {
                        ans[i][j][k] = curExpression.evaluate(mode.parseNum(Integer.toString(i + x1)),
                                mode.parseNum(Integer.toString(j + y1)),
                                mode.parseNum(Integer.toString(k + z1)), mode);
                    } catch (OverflowException | DivisionByZeroException e) {
                        ans[i][j][k] = null;
                    }
                }
            }
        }
        return ans;
    }

    public Type<?> getType(String mode) throws UnknownModeException {
        switch (mode) {
            case "i":
                return new IntegerType();
            case "bi":
                return new BigIntegerType();
            case "d":
                return new DoubleType();
            default:
                throw new UnknownModeException(mode);
        }
    }
}
