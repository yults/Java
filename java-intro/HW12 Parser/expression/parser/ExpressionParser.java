package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {

    public CommonExpression parse(String expression) {
        parsingStream = expression;
        indexStream = 0;
        return shifts();
    }

    private enum Token {
        ADD("+"), SUB("-"), MUL("*"), DIV("/"), VAR(""), CONST(""),
        SHL("<<"), SHR(">>"), OPENBRACKET("("),
        CLOSEBRACKET(")"), UNARYMINUS("-"), BEGIN(""), END("");

        public String stringRepresentation;

        Token(String stringArg) {
            stringRepresentation = stringArg;
        }
    }

    private String parsingStream;
    private int indexStream;
    private Token currentToken = Token.BEGIN;


    CommonExpression baseUnary() {
        currentToken = getNextToken();
        switch (currentToken) {
            case CONST:
                int c = Integer.parseInt(currentToken.stringRepresentation);
                currentToken = getNextToken();
                return new Const(c);
            case VAR:
                String var = currentToken.stringRepresentation;
                currentToken = getNextToken();
                return new Variable(var);
            case OPENBRACKET:
                CommonExpression termExpression = shifts();
                currentToken = getNextToken();
                return termExpression;
            default:
                return new Minus(baseUnary());
        }
    }

    CommonExpression mulDiv() {
        CommonExpression prevLevel = baseUnary(), right;
        CommonExpression tempExpression = prevLevel;
        while (true) {
            switch (currentToken) {
                case MUL:
                    right = baseUnary();
                    tempExpression = new Multiply(tempExpression, right);
                    break;
                case DIV:
                    right = baseUnary();
                    tempExpression = new Divide(tempExpression, right);
                    break;
                default:
                    return tempExpression;
            }
        }
    }

    CommonExpression addSub() {
        CommonExpression prevLevel = mulDiv(), right;
        CommonExpression tempExpression = prevLevel;
        while (true) {
            switch (currentToken) {
                case ADD:
                    right = mulDiv();
                    tempExpression = new Add(tempExpression, right);
                    break;
                case SUB:
                    right = mulDiv();
                    tempExpression = new Subtract(tempExpression, right);
                    break;
                default:
                    return tempExpression;
            }
        }
    }

    CommonExpression shifts() {
        CommonExpression prevLevel = addSub(), right;
        CommonExpression tempExpression = prevLevel;
        while (true) {
            switch (currentToken) {
                case SHL:
                    right = addSub();
                    tempExpression = new Shl(tempExpression, right);
                    break;
                case SHR:
                    right = addSub();
                    tempExpression = new Shr(tempExpression, right);
                    break;
                default:
                    return tempExpression;
            }
        }
    }


    private String getInt(String str, int indexBegin) {
        int indexEnd = indexBegin;
        if (str.charAt(indexBegin) == '-') indexEnd++;
        while (indexEnd < str.length() && Character.isDigit(str.charAt(indexEnd))) {
            indexEnd++;
        }
        return str.substring(indexBegin, indexEnd);
    }

    private Token getNextToken() {
        while (indexStream < parsingStream.length() &&
                Character.isWhitespace(parsingStream.charAt(indexStream))) {
            indexStream++;
        }
        Token nextToken;
        if (indexStream == parsingStream.length()) {
            nextToken = Token.END;
        } else {
            switch (parsingStream.charAt(indexStream)) {
                case '+':
                    nextToken = Token.ADD;
                    indexStream++;
                    break;
                case '*':
                    nextToken = Token.MUL;
                    indexStream++;
                    break;
                case '/':
                    nextToken = Token.DIV;
                    indexStream++;
                    break;
                case '<':
                    nextToken = Token.SHL;
                    indexStream += 2;
                    break;
                case '>':
                    nextToken = Token.SHR;
                    indexStream += 2;
                    break;
                case '(':
                    nextToken = Token.OPENBRACKET;
                    indexStream++;
                    break;
                case ')':
                    nextToken = Token.CLOSEBRACKET;
                    indexStream++;
                    break;
                case 'x':
                case 'y':
                case 'z':
                    nextToken = Token.VAR;
                    nextToken.stringRepresentation = parsingStream.substring(indexStream, indexStream + 1);
                    indexStream++;
                    break;
                case '-':
                    switch (currentToken) {
                        case CONST:
                        case VAR:
                        case CLOSEBRACKET:
                            nextToken = Token.SUB;
                            indexStream++;
                            break;
                        default:
                            if (Character.isDigit(parsingStream.charAt(indexStream + 1))) {
                                nextToken = Token.CONST;
                                nextToken.stringRepresentation = getInt(parsingStream, indexStream);
                                indexStream += nextToken.stringRepresentation.length();
                                break;
                            } else {
                                nextToken = Token.UNARYMINUS;
                                indexStream++;
                                break;
                            }
                    }
                    break;
                default:
                    nextToken = Token.CONST;
                    nextToken.stringRepresentation = getInt(parsingStream, indexStream);
                    indexStream += nextToken.stringRepresentation.length();
                    break;
            }
        }
        return nextToken;
    }

}
