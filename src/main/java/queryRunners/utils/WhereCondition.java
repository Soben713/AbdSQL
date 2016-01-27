package queryRunners.utils;

import db.Record;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import queryParsers.utils.DummyVisitor;

/**
 * Created by user on 27/01/16 AD.
 */
public class WhereCondition {
    public static String TRUE = "(1 = 1)", FALSE = "(1 = 0)";
    Expression whereExpression;
    boolean result;

    public WhereCondition(Expression whereExpression) {
        this.whereExpression = whereExpression;
    }

    public boolean evaluate(Record record) {
        if (whereExpression.toString().equals(TRUE))
            return true;
        else if (whereExpression.toString().equals(FALSE))
            return false;
        else if(whereExpression.toString().substring(0, 3).equals("NOT")) {
            try {
                String newExpressionString = whereExpression.toString().substring(4);
                Expression newExpression =
                        ((Update)(CCJSqlParserUtil.parse("update x set x=y where " + newExpressionString))).getWhere();
                return !(new WhereCondition(newExpression).evaluate(record));
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        whereExpression.accept(new WhereConditionExpressionVisitor(record));
        return result;
    }

    private class WhereConditionExpressionVisitor extends DummyVisitor {
        Record record;

        public WhereConditionExpressionVisitor(Record record) {
            this.record = record;
        }

        @Override
        public void visit(EqualsTo equalsTo) {
            Object left = record.getCell(equalsTo.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(equalsTo.getRightExpression()).compute(record);
            result = left.equals(right);
        }

        @Override
        public void visit(MinorThan minorThan) {
            Object left = record.getCell(minorThan.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(minorThan.getRightExpression()).compute(record);
            if(left instanceof String)
                result = (((String) left).compareTo((String) right) < 0);
            else
                result = (Integer) left < (Integer) right;
        }

        @Override
        public void visit(MinorThanEquals minorThanEquals) {
            Object left = record.getCell(minorThanEquals.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(minorThanEquals.getRightExpression()).compute(record);
            if(left instanceof String) {
                int x = ((String) left).compareTo((String) right);
                result = (x < 0 || x == 0);
            }
            else
                result = (Integer) left <= (Integer) right;
        }

        @Override
        public void visit(GreaterThan greaterThan) {
            Object left = record.getCell(greaterThan.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(greaterThan.getRightExpression()).compute(record);
            if(left instanceof String)
                result = (((String) left).compareTo((String) right) > 0);
            else
                result = (Integer) left > (Integer) right;
        }

        @Override
        public void visit(GreaterThanEquals greaterThanEquals) {
            Object left = record.getCell(greaterThanEquals.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(greaterThanEquals.getRightExpression()).compute(record);
            if(left instanceof String) {
                int x = ((String) left).compareTo((String) right);
                result = (x > 0 || x == 0);
            }
            else
                result = (Integer) left >= (Integer) right;
        }

        @Override
        public void visit(AndExpression andExpression) {
            boolean left = new WhereCondition(andExpression.getLeftExpression()).evaluate(record);
            boolean right = new WhereCondition(andExpression.getRightExpression()).evaluate(record);
            result = (left && right);
        }

        @Override
        public void visit(OrExpression orExpression) {
            boolean left = new WhereCondition(orExpression.getLeftExpression()).evaluate(record);
            boolean right = new WhereCondition(orExpression.getRightExpression()).evaluate(record);
            result = (left || right);
        }

        @Override
        public void visit(Parenthesis parenthesis) {
            result = new WhereCondition(parenthesis.getExpression()).evaluate(record);
        }
    }
}
