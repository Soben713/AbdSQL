package queryParsers;

import net.sf.jsqlparser.statement.update.Update;
import queryParsers.parsed.ParsedUpdate;
import queryRunners.utils.ComputableValue;
import queryRunners.utils.WhereCondition;

/**
 * Created by user on 27/01/16 AD.
 */
public class UpdateParser extends QueryParser<Update> {

	@Override
	public ParsedUpdate parse(Update update) {
		String tableName = update.getTables().get(0).getName();
		String columnName = update.getColumns().get(0).getColumnName();
		ComputableValue columnValue = new ComputableValue(update.getExpressions().get(0));
		WhereCondition where = new WhereCondition(update.getWhere());
		ParsedUpdate parsedUpdate = new ParsedUpdate(tableName, columnName, columnValue, where);
		return parsedUpdate;
	}
}
