package queryRunners.utils;

import db.Record;
import db.TableIndex;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.update.Update;
import queryParsers.utils.DummyVisitor;

/**
 * Created by user on 27/01/16 AD.
 */
public class WhereCondition {
    public static String TRUE = "(1 = 1)", FALSE = "(1 = 0)";
    Expression whereExpression;
    boolean evalResult;

    public WhereCondition(Expression whereExpression) {
        this.whereExpression = whereExpression;
    }

    public boolean evaluate(Record record) {
        if (whereExpression.toString().equals(TRUE))
            return true;
        else if (whereExpression.toString().equals(FALSE))
            return false;
        else if(whereExpression.toString().substring(0, 3).equals("NOT")) {
            String newExpressionString = whereExpression.toString().substring(4);
            Expression newExpression = stringToWhereExpression("update x set x=y where " + newExpressionString);
            return !(new WhereCondition(newExpression).evaluate(record));
        }
        whereExpression.accept(new WhereConditionExpressionVisitor(record));
        return evalResult;
    }

    public static Expression stringToWhereExpression(String s) {
        try {
            return ((Update)(CCJSqlParserUtil.parse("update x set x=y where " + s))).getWhere();
        } catch (JSQLParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static WhereCondition stringToWhereCondition(String s) {
        return new WhereCondition(stringToWhereExpression(s));
    }

    @Override
    public String toString() {
        return this.whereExpression.toString();
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
            evalResult = left.equals(right);
        }

        @Override
        public void visit(MinorThan minorThan) {
            Object left = record.getCell(minorThan.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(minorThan.getRightExpression()).compute(record);
            if(left instanceof String)
                evalResult = (((String) left).compareTo((String) right) < 0);
            else
                evalResult = (Integer) left < (Integer) right;
        }

        @Override
        public void visit(MinorThanEquals minorThanEquals) {
            Object left = record.getCell(minorThanEquals.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(minorThanEquals.getRightExpression()).compute(record);
            if(left instanceof String) {
                int x = ((String) left).compareTo((String) right);
                evalResult = (x < 0 || x == 0);
            }
            else
                evalResult = (Integer) left <= (Integer) right;
        }

        @Override
        public void visit(GreaterThan greaterThan) {
            Object left = record.getCell(greaterThan.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(greaterThan.getRightExpression()).compute(record);
            if(left instanceof String)
                evalResult = (((String) left).compareTo((String) right) > 0);
            else
                evalResult = (Integer) left > (Integer) right;
        }

        @Override
        public void visit(GreaterThanEquals greaterThanEquals) {
            Object left = record.getCell(greaterThanEquals.getLeftExpression().toString()).getValue();
            Object right = new ComputableValue(greaterThanEquals.getRightExpression()).compute(record);
            if(left instanceof String) {
                int x = ((String) left).compareTo((String) right);
                evalResult = (x > 0 || x == 0);
            }
            else
                evalResult = (Integer) left >= (Integer) right;
        }

        @Override
        public void visit(AndExpression andExpression) {
            boolean left = new WhereCondition(andExpression.getLeftExpression()).evaluate(record);
            boolean right = new WhereCondition(andExpression.getRightExpression()).evaluate(record);
            evalResult = (left && right);
        }

        @Override
        public void visit(OrExpression orExpression) {
            boolean left = new WhereCondition(orExpression.getLeftExpression()).evaluate(record);
            boolean right = new WhereCondition(orExpression.getRightExpression()).evaluate(record);
            evalResult = (left || right);
        }

        @Override
        public void visit(Parenthesis parenthesis) {
            evalResult = new WhereCondition(parenthesis.getExpression()).evaluate(record);
        }
    }

    public IsHelpfulResult isTableIndexHelpful(TableIndex tableIndex) {
        IsHelpfulResult result = new IsHelpfulResult();
        whereExpression.accept(new IsTableIndexHelpfulViewer(tableIndex, result));
        return result;
    }

    public class IsHelpfulResult {
        boolean isHelpful;
        Object value;

        public IsHelpfulResult() {
            this.isHelpful = false;
        }

        public IsHelpfulResult(boolean isHelpful, Object value) {
            this.isHelpful = isHelpful;
            this.value = value;
        }

        public boolean isHelpful() {
            return isHelpful;
        }

        public void setIsHelpful(boolean isHelpful) {
            this.isHelpful = isHelpful;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    private class IsTableIndexHelpfulViewer extends DummyVisitor {
        TableIndex tableIndex;
        IsHelpfulResult result;

        public IsTableIndexHelpfulViewer(TableIndex index, IsHelpfulResult result) {
            this.tableIndex = index;
            this.result = result;
        }

        @Override
        public void visit(EqualsTo equalsTo) {
            if(equalsTo.getLeftExpression().toString().equals(tableIndex.getIndexedField().name)) {
                try {
                    result.value = new ComputableValue(equalsTo.getRightExpression()).compute(null);
                    result.isHelpful = true;
                } catch (NullPointerException e) {
                    //which means a column is used, and therefore it really isn't helpful
                }
            }
        }

        @Override
        public void visit(AndExpression andExpression) {
            IsHelpfulResult left = new WhereCondition(andExpression.getLeftExpression()).isTableIndexHelpful(tableIndex);
            if(left.isHelpful){
                result.isHelpful = true;
                result.value = left.value;
            }
            IsHelpfulResult right = new WhereCondition(andExpression.getRightExpression()).isTableIndexHelpful(tableIndex);
            if(right.isHelpful){
                result.isHelpful = true;
                result.value = right.value;
            }
        }

        @Override
        public void visit(Parenthesis parenthesis) {
            IsHelpfulResult res = new WhereCondition(parenthesis.getExpression()).isTableIndexHelpful(tableIndex);
            result.isHelpful = res.isHelpful;
            result.value = res.value;
        }
    }

}
