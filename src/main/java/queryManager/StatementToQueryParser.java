package queryManager;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.insert.Insert;
import queryParsers.CreateTableParser;
import queryParsers.InsertParser;
import queryParsers.QueryParser;
import queryRunners.CreateTableRunner;
import queryRunners.InsertRunner;
import queryRunners.QueryRunner;

/**
 * Created by user on 27/01/16 AD.
 */
public class StatementToQueryParser {
    public ParserAndRunner getQueryParserAndRunner(Statement s) {
        if(s instanceof CreateTable)
            return new ParserAndRunner(new CreateTableRunner(), new CreateTableParser());
        else if(s instanceof Insert)
            return new ParserAndRunner(new InsertRunner(), new InsertParser());
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

}
