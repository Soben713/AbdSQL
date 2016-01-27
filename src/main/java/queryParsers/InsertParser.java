package queryParsers;

import db.Cell;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import queryParsers.parsed.ParsedInsert;
import queryParsers.utils.DummyVisitor;
import utils.Convertor;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class InsertParser extends QueryParser<Insert> {
    @Override
    public ParsedInsert parse(Insert insert) {
        ParsedInsert parsedInsert = new ParsedInsert();
        parsedInsert.tableName = insert.getTable().getName();
        insert.getItemsList().accept(new InsertVisitor(parsedInsert.cells));
        return parsedInsert;
    }

    private class InsertVisitor extends DummyVisitor {
        ArrayList<Cell> cells;

        public InsertVisitor(ArrayList<Cell> cells) {
            this.cells = cells;
        }

        @Override
        public void visit(ExpressionList expressionList) {
            ArrayList<Expression> expressions = (ArrayList<Expression>) expressionList.getExpressions();
            for(Expression expression: expressions) {
                expression.accept(this);
            }
        }

        @Override
        public void visit(LongValue longValue) {
            cells.add(new Cell(Cell.CellType.INTEGER, Convertor.longToInteger(longValue.getValue())));
        }

        @Override
        public void visit(NullValue nullValue) {
            cells.add(new Cell(Cell.CellType.NULL, null));
        }

        @Override
        public void visit(Column column) {
            /*
            JSQLParser stupidly parses string values into a Column
             */
            cells.add(new Cell(Cell.CellType.VARCHAR, column.getColumnName().replace("\"", "")));
        }
    }

}
