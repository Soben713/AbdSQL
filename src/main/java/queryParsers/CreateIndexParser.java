package queryParsers;

import net.sf.jsqlparser.statement.create.index.CreateIndex;
import queryParsers.parsed.ParsedCreateIndex;

/**
 * Created by user on 28/01/16 AD.
 */
public class CreateIndexParser extends QueryParser<CreateIndex> {
    @Override
    public ParsedCreateIndex parse(CreateIndex createIndex) {
        String tableName = createIndex.getTable().getName();
        String columnName = createIndex.getIndex().getColumnsNames().get(0);
        String name = createIndex.getIndex().getName();
        return new ParsedCreateIndex(name, tableName, columnName);
    }
}
