package queryParsers;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import queryParsers.parsed.ParsedCreateView;
import queryParsers.parsed.ParsedSelect;
import queryParsers.utils.DummyVisitor;
import queryRunners.utils.GroupbyCondition;
import queryRunners.utils.WhereCondition;

public class CreateViewParser extends QueryParser<CreateView> {

	ParsedSelect parsedSelect;

	@Override
	public ParsedCreateView parse(CreateView statement) {
		SelectBody select = statement.getSelectBody();
		select.accept(new SelectParserVisitor());
		String viewName = statement.toString().trim().split(" ")[2];
		return new ParsedCreateView(parsedSelect, viewName);
	}

	private class SelectParserVisitor extends DummyVisitor {
		@Override
		public void visit(PlainSelect plainSelect) {
			String tableName = plainSelect.getFromItem().toString();
			WhereCondition whereCondition = new WhereCondition(plainSelect.getWhere());
			ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
			// groupby elements
			List<Expression> groupbyExpressions = plainSelect.getGroupByColumnReferences();
			Expression having = plainSelect.getHaving();
			GroupbyCondition groupbyCondition = new GroupbyCondition(groupbyExpressions, having);

			for (SelectItem si : plainSelect.getSelectItems()) {
				selectItems.add(si);
			}

			parsedSelect = new ParsedSelect(tableName, selectItems, whereCondition, groupbyCondition);

			if (plainSelect.getJoins() != null) {
				Join join = plainSelect.getJoins().get(0);
				if (join.isSimple())
					parsedSelect.setCartesianTable(join.getRightItem().toString());
				else
					parsedSelect.setJoinedTable(join.getRightItem().toString());
			}
		}
	}
}
