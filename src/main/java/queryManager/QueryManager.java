package queryManager;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import queryParsers.*;
import queryRunners.*;
import queryRunners.utils.WhereCondition;

/**
 * Created by user on 27/01/16 AD.
 */
public class QueryManager {
    public ParserAndRunner getQueryParserAndRunner(Statement s) {
        if(s instanceof CreateTable)
            return new ParserAndRunner(new CreateTableRunner(), new CreateTableParser());
        else if(s instanceof Insert)
            return new ParserAndRunner(new InsertRunner(), new InsertParser());
        else if(s instanceof Update)
            return new ParserAndRunner(new UpdateRunner(), new UpdateParser());
        else if(s instanceof Delete)
            return new ParserAndRunner(new DeleteRunner(), new DeleteParser());
        else if(s instanceof Select)
            return new ParserAndRunner(new SelectRunner(), new SelectParser());
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
        par.runner.run(par.parser.parse(s));
    }

    public static String relax(String query) {
        query = query.replaceAll("TRUE", WhereCondition.TRUE);
        query = query.replaceAll("FALSE", WhereCondition.FALSE);
        return query;
    }
}
