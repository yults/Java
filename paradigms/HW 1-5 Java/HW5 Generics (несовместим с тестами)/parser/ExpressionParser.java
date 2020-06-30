package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.types.*;

public class ExpressionParser<T> implements Parser {

    public ExpressionParser(Type<T> mode) {
        this.type = mode;
    }

    public TripleExpression<T> parse(String expression) throws UnknownSymbolException, BracketExpectedException, OverflowException, UnaryExpectedException {
        parsingStream = expression;
        indexStream = 0;
        brackets = 0;
        TripleExpression<T> result = first();
        if (brackets != 0 && indexStream == parsingStream.length())
        {
            throw new BracketExpectedException("many brackets");
        }
        return  result;
    }

    private enum Token {
        ADD("+"), MUL("*"), DIV("/"), VAR(""), CONST(""),
        OPENBRACKET("("), CLOSEBRACKET(")"), MINUS("-"), BEGIN(""), END(""),
        MAX("max"), MIN ("min"), COUNT ("count");

        public String stringRepresentation;

        Token(String stringArg) {
            stringRepresentation = stringArg;
        }
    }

    private String parsingStream;
    private int indexStream;
    private Token currentToken = Token.BEGIN;
    private int brackets = 0;
    Type<T> type;
    private boolean flag = false;


    TripleExpression<T> baseUnary() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        currentToken = getNextToken();
        switch (currentToken) {
            case OPENBRACKET:
                TripleExpression<T> termExpression = first();
                currentToken = getNextToken();
                return termExpression;
            case CONST:
                T c;
                if (flag == true) {
                    TripleExpression minus = new Const(type.parseNum("-2147483648"));
                    flag = false;
                    if (currentToken.stringRepresentation.equals("2147483648")) return minus;
                    }
                try {
                    c = type.parseNum(currentToken.stringRepresentation);
                } catch (NumberFormatException e) {
                    throw new OverflowException();
                }
                currentToken = getNextToken();
                return new Const<T>(c);
            case VAR:
                String var = currentToken.stringRepresentation;
                currentToken = getNextToken();
                return new Variable<T>(var);
            case MINUS:
                flag = true;
                TripleExpression minn = baseUnary();
                if (minn.equals(new Const(-2147483648))) return new Const(-2147483648);
                else
                return new CheckedNegate(minn);
            case COUNT: {
                return new CheckedCount<T>(baseUnary());
            }
            default:
                throw new UnaryExpectedException(("unary expected"));
        }
    }

    TripleExpression<T> mulDiv() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        TripleExpression<T> right;
        TripleExpression<T> tempExpression = baseUnary();
        while (true) {
            switch (currentToken) {
                case MUL:
                    right = baseUnary();
                    tempExpression = new CheckedMultiply(tempExpression, right);
                    break;
                case DIV:
                    right = baseUnary();
                    tempExpression = new CheckedDivide(tempExpression, right);
                    break;
                default:
                    return tempExpression;
            }
        }
    }

    TripleExpression<T> addSub() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        TripleExpression<T> prevLevel = mulDiv(), right;
        TripleExpression<T> tempExpression = prevLevel;
        while (true) {
            switch (currentToken) {
                case ADD:
                    right = mulDiv();
                    tempExpression = new CheckedAdd(tempExpression, right);
                    break;
                case MINUS:
                    right = mulDiv();
                    tempExpression = new CheckedSubtract(tempExpression, right);
                    break;
                default:
                    return tempExpression;
            }
        }
    }

    TripleExpression<T> first() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        TripleExpression<T> prevLevel = addSub(), right;
        TripleExpression<T> tempExpression = prevLevel;
        while (true) {
            switch (currentToken) {
                case MAX:
                    right = addSub();
                    tempExpression = new CheckedMax(tempExpression, right);
                    break;
                case MIN:
                    right = addSub();
                    tempExpression = new CheckedMin(tempExpression, right);
                    break;
                case OPENBRACKET:
                    throw new BracketExpectedException("bracket");
                default:
                    return tempExpression;
            }
        }
    }



    private String getInt(String str, int indexBegin) {
        int indexEnd = indexBegin;
        if (str.charAt(indexBegin) == '-') {
            indexEnd++;

        }
        while (indexEnd < str.length() && Character.isDigit(str.charAt(indexEnd))) {
            indexEnd++;
        }
        return str.substring(indexBegin, indexEnd);
    }

    private Token getNextToken() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
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
                case '(':
                    brackets++;
                    nextToken = Token.OPENBRACKET;
                    indexStream += 1;
                    break;
                case ')':
                    if (brackets == 0)
                        throw new BracketExpectedException();
                    brackets--;
                    nextToken = Token.CLOSEBRACKET;
                    indexStream += 1;
                    break;
                case 'x':
                case 'y':
                case 'z':
                    nextToken = Token.VAR;
                    nextToken.stringRepresentation = parsingStream.substring(indexStream, indexStream + 1);
                    indexStream++;
                    break;
                case '-':
                    nextToken = Token.MINUS;
                    indexStream++;
                    break;
                case 'm':
                    indexStream++;
                    if (parsingStream.charAt(indexStream) == 'i') {
                        indexStream++;
                        if (parsingStream.charAt(indexStream) == 'n') {
                            nextToken = Token.MIN;
                        } else throw new UnknownSymbolException();
                    }
                    else {
                        if (parsingStream.charAt(indexStream) == 'a') {
                        indexStream++;
                        if (parsingStream.charAt(indexStream) == 'x') {
                            nextToken = Token.MAX;
                        } else throw new UnknownSymbolException();
                        }
                        else throw new UnknownSymbolException();
                    }
                    indexStream++;
                    break;
                case 'c':
                    indexStream++;
                    if (parsingStream.charAt(indexStream) == 'o') {
                        indexStream++;
                        if (parsingStream.charAt(indexStream) == 'u') {
                            indexStream++;
                            if (parsingStream.charAt(indexStream) == 'n') {
                                indexStream++;
                                if (parsingStream.charAt(indexStream) == 't') {
                                    nextToken = Token.COUNT;
                                    indexStream++;
                                }
                                else throw new UnknownSymbolException();
                            }
                            else throw new UnknownSymbolException();
                        }
                        else throw new UnknownSymbolException();
                    }
                    else throw new UnknownSymbolException();
                    break;
                default:
                    if (Character.isDigit(parsingStream.charAt(indexStream))) {
                        nextToken = Token.CONST;
                        nextToken.stringRepresentation = getInt(parsingStream, indexStream);
                        indexStream += nextToken.stringRepresentation.length();
                        while (indexStream < parsingStream.length() &&
                                Character.isWhitespace(parsingStream.charAt(indexStream))) {
                            indexStream++;
                        }
                        if (indexStream < parsingStream.length()) {
                        if (Character.isDigit(parsingStream.charAt(indexStream))) throw new UnaryExpectedException("two const");
                        }
                        break;
                    }
                    else throw new UnknownSymbolException();
            }
        }
        return nextToken;
    }

}
