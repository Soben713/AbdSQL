package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Table;
import db.View;
import queryParsers.parsed.ParsedCreateView;
import queryParsers.parsed.ParsedSelect;
import utils.Log;

public class CreateViewRunner extends QueryRunner<ParsedCreateView> {
	ParsedSelect select;

	@Override
	public void run(ParsedCreateView parsedQuery, boolean log) {
		select = parsedQuery.parsedSelect;
		String viewName = parsedQuery.viewName;
		View parent = null;
		try {
			parent = DB.getInstance().getTable(select.getTableName()).getView();
		} catch (NoSuchTableException e) {
			e.printStackTrace();
		}
		Table result = new SelectRunner().select(select);
		result.setName(viewName);
		View view = new View(result, parent, viewName, parsedQuery);
		parent.addChild(view);
		view.setUpdatable(getUpdatableStatus(view, parent));
		result.setView(view);
		DB.getInstance().tables.put(viewName, result);
		if (log)
			Log.println("VIEW CREATED");
	}

	private boolean getUpdatableStatus(View self, View parent) {
		if (select.getCartesianTable() != null)
			return false;
		if (select.getJoinedTable() != null)
			return false;
		if (select.getGroupbyCondition().getGroupbyExpressions() != null)
			return false;
		String primary = parent.getTable().getPrimaryKey().name;
		boolean hasPrimary = false;
		for (int i = 0; i < self.getTable().getFields().size(); i++) {
			if (self.getTable().getFields().get(i).name.equals(primary))
				hasPrimary = true;
		}
		if (!hasPrimary)
			return false;
		return true;
	}

}
