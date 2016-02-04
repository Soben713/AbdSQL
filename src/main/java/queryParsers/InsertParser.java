package queryParsers;

import db.Cell;
import db.DB;
import db.Field;
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
import java.util.List;

import Exceptions.NoSuchTableException;

/**
 * Created by user on 27/01/16 AD.
 */
public class InsertParser extends QueryParser<Insert> {
	ArrayList<Cell> cells;

	@Override
	public ParsedInsert parse(Insert insert) {
		insert.getItemsList().accept(new InsertVisitor());
		List<Field> selectedColumns = null;
		try {
			selectedColumns = DB.getInstance().getTable(insert.getTable().getName()).getFields();
		} catch (NoSuchTableException e) {
			e.printStackTrace();
		}
		ParsedInsert parsedInsert = new ParsedInsert(insert.getTable().getName(), cells, selectedColumns);
		return parsedInsert;
	}

	private class InsertVisitor extends DummyVisitor {

		public InsertVisitor() {
			cells = new ArrayList<Cell>();
		}

		@Override
		public void visit(ExpressionList expressionList) {
			ArrayList<Expression> expressions = (ArrayList<Expression>) expressionList.getExpressions();
			for (Expression expression : expressions) {
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
			 * JSQLParser stupidly parses string values into a Column
			 */
			cells.add(new Cell(Cell.CellType.VARCHAR, column.getColumnName().replace("\"", "")));
		}
	}
}
