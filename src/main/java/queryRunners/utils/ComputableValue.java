package queryRunners.utils;

import db.Cell;
import db.Record;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import queryParsers.utils.DummyVisitor;
import utils.Convertor;

/**
 * Created by user on 27/01/16 AD.
 */
public class ComputableValue {
    Expression computableExpression;
    Object result;

    public static String addParanthesis(String expression) {
        //to make it parse from left to right
        String result = "";
        for(int i=0; i<expression.length(); i++){
            if("+-/*".indexOf(expression.charAt(i)) >= 0)
                result = "(" + result + ")";
            result += expression.charAt(i);
        }
        return result;
    }

    public ComputableValue(Expression computableExpression) {
        String relaxed = addParanthesis(computableExpression.toString());
        try {
            this.computableExpression = CCJSqlParserUtil.parseExpression(relaxed);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public Object compute(Record record) {
        result = null;
        computableExpression.accept(new ComputableValueExpressionVisitor(record));
        return result;
    }

    private class ComputableValueExpressionVisitor extends DummyVisitor {
        Record record;

        public ComputableValueExpressionVisitor(Record record) {
            this.record = record;
        }

        @Override
        public void visit(LongValue longValue) {
            result = Convertor.longToInteger(longValue.getValue());
        }

        @Override
        public void visit(Column column) {
            String s = column.toString();
            if(s.charAt(0) == '\"') {
                //is a string
                result = s.replaceAll("\"", "");
            } else {
                //is a column
                Cell c = record.getCell(s);
                result = c.getValue();
            }
        }

        @Override
        public void visit(Addition addition) {
            Object left = new ComputableValue(addition.getLeftExpression()).compute(record);
            Object right = new ComputableValue(addition.getRightExpression()).compute(record);
            if (left instanceof Integer && right instanceof Integer)
                result = (Integer) left + (Integer) right;
            else {
                result = left.toString() + right.toString();
            }
        }

        @Override
        public void visit(Subtraction subtraction) {
            ComputableValue left = new ComputableValue(subtraction.getLeftExpression());
            ComputableValue right = new ComputableValue(subtraction.getRightExpression());
            result = (Integer) left.compute(record) - (Integer) right.compute(record);
        }

        @Override
        public void visit(Multiplication multiplication) {
            ComputableValue left = new ComputableValue(multiplication.getLeftExpression());
            ComputableValue right = new ComputableValue(multiplication.getRightExpression());
            result = (Integer) left.compute(record) * (Integer) right.compute(record);
        }

        @Override
        public void visit(Division division) {
            ComputableValue left = new ComputableValue(division.getLeftExpression());
            ComputableValue right = new ComputableValue(division.getRightExpression());
            result = (Integer) left.compute(record) / (Integer) right.compute(record);
        }

        @Override
        public void visit(Parenthesis parenthesis) {
            result = new ComputableValue(parenthesis.getExpression()).compute(record);
        }
    }
}
