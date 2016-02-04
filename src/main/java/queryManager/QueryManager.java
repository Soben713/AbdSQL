package queryManager;

import Exceptions.NonUpdatableView;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import queryParsers.*;
import queryRunners.*;
import queryRunners.utils.WhereCondition;
import utils.Log;

/**
 * Created by user on 27/01/16 AD.
 */
public class QueryManager {
	public ParserAndRunner getQueryParserAndRunner(Statement s) {
		if (s instanceof CreateTable)
			return new ParserAndRunner(new CreateTableRunner(), new CreateTableParser());
		else if (s instanceof Insert)
			return new ParserAndRunner(new InsertRunner(), new InsertParser());
		else if (s instanceof Update)
			return new ParserAndRunner(new UpdateRunner(), new UpdateParser());
		else if (s instanceof Delete)
			return new ParserAndRunner(new DeleteRunner(), new DeleteParser());
		else if (s instanceof Select)
			return new ParserAndRunner(new SelectRunner(), new SelectParser());
		else if (s instanceof CreateIndex)
			return new ParserAndRunner(new CreateIndexRunner(), new CreateIndexParser());
		else if (s instanceof CreateView)
			return new ParserAndRunner(new CreateViewRunner(), new CreateViewParser());
		return null;
	}

	private class ParserAndRunner {
		QueryRunner runner;
		QueryParser parser;

		public ParserAndRunner(QueryRunner runner, QueryParser parser) {
			this.runner = runner;
			this.parser = parser;
		}
	}

	public void handleStatement(Statement s) {
		ParserAndRunner par = getQueryParserAndRunner(s);
		try {
			par.runner.run(par.parser.parse(s), true);
		} catch (NonUpdatableView e) {
			Log.error(e);
		}
	}

	public static String relax(String query) {
		query = query.replaceAll("TRUE", WhereCondition.TRUE);
		query = query.replaceAll("FALSE", WhereCondition.FALSE);
		query = query.replaceAll("ON UPDATE", "ONUPDATE");
		query = query.replaceAll("ON DELETE", "ONDELETE");
		return query;
	}

	public static Statement getStatement(String query) throws JSQLParserException {
		query = QueryManager.relax(query);
		Statement s = CCJSqlParserUtil.parse(query);
		return s;
	}
}
