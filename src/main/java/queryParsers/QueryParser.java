package queryParsers;

import net.sf.jsqlparser.statement.Statement;
import queryParsers.parsed.Parsed;

/**
 * Created by user on 27/01/16 AD.
 */
public abstract class QueryParser<S extends Statement>{
    public QueryParser() {
    }

    public abstract Parsed parse(S statement);
}
