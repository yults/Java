package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.ArrayList;
import java.util.Stack;

public class ExpressionParser implements Parser {

    public CommonExpression parse(String expression) throws UnknownSymbolException, BracketExpectedException, OverflowException, UnaryExpectedException {
        parsingStream = expression;
        indexStream = 0;
        brackets = 0;
        CommonExpression result = shifts();
        if (brackets != 0 && indexStream == parsingStream.length())
        {
            throw new BracketExpectedException("many brackets");
        }
        return  result;
    }

    private enum Token {
        ADD("+"), MUL("*"), DIV("/"), VAR(""), CONST(""),
        SHL("<<"), SHR(">>"), OPENBRACKET("("),
        CLOSEBRACKET(")"), MINUS("-"), BEGIN(""), END("");

        public String stringRepresentation;

        Token(String stringArg) {
            stringRepresentation = stringArg;
        }
    }

    private String parsingStream;
    private int indexStream;
    private Token currentToken = Token.BEGIN;
    private int brackets = 0;
    private boolean flag = false;


    CommonExpression baseUnary() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        currentToken = getNextToken();
        switch (currentToken) {
            case OPENBRACKET:
                CommonExpression termExpression = shifts();
                if (currentToken != Token.CLOSEBRACKET) {
                    throw new BracketExpectedException("many brackets");
                }
                currentToken = getNextToken();
                return termExpression;
            case CONST:
                int c;
                if (flag == true) {
                    CommonExpression minus = new Const(-2147483648);
                    flag = false;
                if (currentToken.stringRepresentation.equals("2147483648")) return minus;
                }
                try {
                    c = Integer.parseInt(currentToken.stringRepresentation);
                } catch (NumberFormatException e) {
                    throw new OverflowException();
                }
                currentToken = getNextToken();
                return new Const(c);
            case VAR:
                String var = currentToken.stringRepresentation;
                currentToken = getNextToken();
                return new Variable(var);
            case MINUS:
                flag = true;
                CommonExpression minn = baseUnary();
                if (minn.equals(new Const(-2147483648))) return new Const(-2147483648);
                else
                return new CheckedNegate(minn);
            default:
                throw new UnaryExpectedException(("unary expected"));
        }
    }

    CommonExpression mulDiv() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        CommonExpression right;
        CommonExpression tempExpression = baseUnary();
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

    CommonExpression addSub() throws BracketExpectedException, UnknownSymbolException, OverflowException, UnaryExpectedException {
        CommonExpression prevLevel = mulDiv(), right;
        CommonExpression tempExpression = prevLevel;
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

    CommonExpression shifts() throws UnknownSymbolException, BracketExpectedException, OverflowException, UnaryExpectedException {
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
                case OPENBRACKET:
                    throw new BracketExpectedException("bracket");
                default:
                    return tempExpression;
            }
        }
    }



    private String getInt(String str, int indexBegin) throws OverflowException {
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
                case '<':
                    nextToken = Token.SHL;
                    indexStream += 1;
                    if (parsingStream.charAt(indexStream) != '<')
                        throw new UnknownSymbolException();
                    indexStream += 1;
                    break;
                case '>':
                    nextToken = Token.SHR;
                    indexStream += 1;
                    if (parsingStream.charAt(indexStream) != '>')
                        throw new UnknownSymbolException();
                    indexStream += 1;
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
