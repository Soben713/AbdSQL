package queryParsers.utils;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Because you need to create a lot of useless "visit" functions when using the visitor pattern
 * I created this dummyparser class that has an empty implementation for the needed functions.
 */
public class DummyVisitor implements ItemsListVisitor, ExpressionVisitor{
    /*
    All methods below are automatically created by Intellij, template is:

    Object arg = function;
    System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
     */


    public void visit(NullValue nullValue) {
        System.err.println();
    }

    public void visit(Function function) {
    /**/
        Object arg = function;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(SignedExpression signedExpression) {
    /**/
        Object arg = signedExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(JdbcParameter jdbcParameter) {
    /**/
        Object arg = jdbcParameter;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(JdbcNamedParameter jdbcNamedParameter) {
    /**/
        Object arg = jdbcNamedParameter;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(DoubleValue doubleValue) {
    /**/
        Object arg = doubleValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(LongValue longValue) {
    /**/
        Object arg = longValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(HexValue hexValue) {
    /**/
        Object arg = hexValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(DateValue dateValue) {
    /**/
        Object arg = dateValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(TimeValue timeValue) {
    /**/
        Object arg = timeValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(TimestampValue timestampValue) {
    /**/
        Object arg = timestampValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Parenthesis parenthesis) {
    /**/
        Object arg = parenthesis;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(StringValue stringValue) {
    /**/
        Object arg = stringValue;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Addition addition) {
    /**/
        Object arg = addition;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Division division) {
    /**/
        Object arg = division;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Multiplication multiplication) {
    /**/
        Object arg = multiplication;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Subtraction subtraction) {
    /**/
        Object arg = subtraction;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(AndExpression andExpression) {
    /**/
        Object arg = andExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(OrExpression orExpression) {
    /**/
        Object arg = orExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Between between) {
    /**/
        Object arg = between;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(EqualsTo equalsTo) {
    /**/
        Object arg = equalsTo;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(GreaterThan greaterThan) {
    /**/
        Object arg = greaterThan;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(GreaterThanEquals greaterThanEquals) {
    /**/
        Object arg = greaterThanEquals;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(InExpression inExpression) {
    /**/
        Object arg = inExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(IsNullExpression isNullExpression) {
    /**/
        Object arg = isNullExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(LikeExpression likeExpression) {
    /**/
        Object arg = likeExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(MinorThan minorThan) {
    /**/
        Object arg = minorThan;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(MinorThanEquals minorThanEquals) {
    /**/
        Object arg = minorThanEquals;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(NotEqualsTo notEqualsTo) {
    /**/
        Object arg = notEqualsTo;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Column column) {
    /**/
        Object arg = column;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(CaseExpression caseExpression) {
    /**/
        Object arg = caseExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(WhenClause whenClause) {
    /**/
        Object arg = whenClause;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(ExistsExpression existsExpression) {
    /**/
        Object arg = existsExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(AllComparisonExpression allComparisonExpression) {
    /**/
        Object arg = allComparisonExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {
    /**/
        Object arg = anyComparisonExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Concat concat) {
    /**/
        Object arg = concat;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Matches matches) {
    /**/
        Object arg = matches;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(BitwiseAnd bitwiseAnd) {
    /**/
        Object arg = bitwiseAnd;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(BitwiseOr bitwiseOr) {
    /**/
        Object arg = bitwiseOr;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(BitwiseXor bitwiseXor) {
    /**/
        Object arg = bitwiseXor;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(CastExpression castExpression) {
    /**/
        Object arg = castExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(Modulo modulo) {
    /**/
        Object arg = modulo;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(AnalyticExpression analyticExpression) {
    /**/
        Object arg = analyticExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(WithinGroupExpression withinGroupExpression) {
    /**/
        Object arg = withinGroupExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(ExtractExpression extractExpression) {
    /**/
        Object arg = extractExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(IntervalExpression intervalExpression) {
    /**/
        Object arg = intervalExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(OracleHierarchicalExpression oracleHierarchicalExpression) {
    /**/
        Object arg = oracleHierarchicalExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(RegExpMatchOperator regExpMatchOperator) {
    /**/
        Object arg = regExpMatchOperator;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(JsonExpression jsonExpression) {
    /**/
        Object arg = jsonExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
    /**/
        Object arg = regExpMySQLOperator;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(UserVariable userVariable) {
    /**/
        Object arg = userVariable;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(NumericBind numericBind) {
    /**/
        Object arg = numericBind;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(KeepExpression keepExpression) {
    /**/
        Object arg = keepExpression;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(MySQLGroupConcat mySQLGroupConcat) {
    /**/
        Object arg = mySQLGroupConcat;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(RowConstructor rowConstructor) {
    /**/
        Object arg = rowConstructor;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(SubSelect subSelect) {
    /**/
        Object arg = subSelect;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(ExpressionList expressionList) {
    /**/
        Object arg = expressionList;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }

    public void visit(MultiExpressionList multiExpressionList) {
    /**/
        Object arg = multiExpressionList;
        System.err.println("visit " + arg.getClass().getName() + " was called: " + arg.toString());
    }
}
