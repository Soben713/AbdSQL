package queryParsers.utils;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;
import utils.Log;

/**
 * Because you need to create a lot of useless "visit" functions when using the visitor pattern
 * I created this dummyparser class that has an empty implementation for the needed functions.
 */
public class DummyVisitor implements ItemsListVisitor, ExpressionVisitor, SelectVisitor {
    /*
    All methods below are automatically created by Intellij, template is:

    Object arg = function;
    Log.error("visit", arg.getClass().getName(), "was called:", arg);
     */


    public void visit(NullValue nullValue) {
        Object arg = nullValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Function function) {
    /**/
        Object arg = function;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(SignedExpression signedExpression) {
    /**/
        Object arg = signedExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(JdbcParameter jdbcParameter) {
    /**/
        Object arg = jdbcParameter;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(JdbcNamedParameter jdbcNamedParameter) {
    /**/
        Object arg = jdbcNamedParameter;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(DoubleValue doubleValue) {
    /**/
        Object arg = doubleValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(LongValue longValue) {
    /**/
        Object arg = longValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(HexValue hexValue) {
    /**/
        Object arg = hexValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(DateValue dateValue) {
    /**/
        Object arg = dateValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(TimeValue timeValue) {
    /**/
        Object arg = timeValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(TimestampValue timestampValue) {
    /**/
        Object arg = timestampValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Parenthesis parenthesis) {
    /**/
        Object arg = parenthesis;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(StringValue stringValue) {
    /**/
        Object arg = stringValue;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Addition addition) {
    /**/
        Object arg = addition;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Division division) {
    /**/
        Object arg = division;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Multiplication multiplication) {
    /**/
        Object arg = multiplication;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Subtraction subtraction) {
    /**/
        Object arg = subtraction;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(AndExpression andExpression) {
    /**/
        Object arg = andExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(OrExpression orExpression) {
    /**/
        Object arg = orExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Between between) {
    /**/
        Object arg = between;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(EqualsTo equalsTo) {
    /**/
        Object arg = equalsTo;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(GreaterThan greaterThan) {
    /**/
        Object arg = greaterThan;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(GreaterThanEquals greaterThanEquals) {
    /**/
        Object arg = greaterThanEquals;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(InExpression inExpression) {
    /**/
        Object arg = inExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(IsNullExpression isNullExpression) {
    /**/
        Object arg = isNullExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(LikeExpression likeExpression) {
    /**/
        Object arg = likeExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(MinorThan minorThan) {
    /**/
        Object arg = minorThan;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(MinorThanEquals minorThanEquals) {
    /**/
        Object arg = minorThanEquals;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(NotEqualsTo notEqualsTo) {
    /**/
        Object arg = notEqualsTo;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Column column) {
    /**/
        Object arg = column;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(CaseExpression caseExpression) {
    /**/
        Object arg = caseExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(WhenClause whenClause) {
    /**/
        Object arg = whenClause;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(ExistsExpression existsExpression) {
    /**/
        Object arg = existsExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(AllComparisonExpression allComparisonExpression) {
    /**/
        Object arg = allComparisonExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {
    /**/
        Object arg = anyComparisonExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Concat concat) {
    /**/
        Object arg = concat;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Matches matches) {
    /**/
        Object arg = matches;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(BitwiseAnd bitwiseAnd) {
    /**/
        Object arg = bitwiseAnd;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(BitwiseOr bitwiseOr) {
    /**/
        Object arg = bitwiseOr;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(BitwiseXor bitwiseXor) {
    /**/
        Object arg = bitwiseXor;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(CastExpression castExpression) {
    /**/
        Object arg = castExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(Modulo modulo) {
    /**/
        Object arg = modulo;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(AnalyticExpression analyticExpression) {
    /**/
        Object arg = analyticExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(WithinGroupExpression withinGroupExpression) {
    /**/
        Object arg = withinGroupExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(ExtractExpression extractExpression) {
    /**/
        Object arg = extractExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(IntervalExpression intervalExpression) {
    /**/
        Object arg = intervalExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(OracleHierarchicalExpression oracleHierarchicalExpression) {
    /**/
        Object arg = oracleHierarchicalExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(RegExpMatchOperator regExpMatchOperator) {
    /**/
        Object arg = regExpMatchOperator;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(JsonExpression jsonExpression) {
    /**/
        Object arg = jsonExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
    /**/
        Object arg = regExpMySQLOperator;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(UserVariable userVariable) {
    /**/
        Object arg = userVariable;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(NumericBind numericBind) {
    /**/
        Object arg = numericBind;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(KeepExpression keepExpression) {
    /**/
        Object arg = keepExpression;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(MySQLGroupConcat mySQLGroupConcat) {
    /**/
        Object arg = mySQLGroupConcat;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(RowConstructor rowConstructor) {
    /**/
        Object arg = rowConstructor;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(SubSelect subSelect) {
    /**/
        Object arg = subSelect;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(ExpressionList expressionList) {
    /**/
        Object arg = expressionList;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(MultiExpressionList multiExpressionList) {
    /**/
        Object arg = multiExpressionList;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(PlainSelect plainSelect) {
    /**/
        Object arg = plainSelect;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(SetOperationList setOperationList) {
    /**/
        Object arg = setOperationList;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }

    public void visit(WithItem withItem) {
    /**/
        Object arg = withItem;
        Log.error("visit", arg.getClass().getName(), "was called:", arg);
    }
}
