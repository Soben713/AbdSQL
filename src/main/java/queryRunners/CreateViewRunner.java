package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Table;
import db.View;
import queryParsers.parsed.ParsedCreateView;
import queryParsers.parsed.ParsedSelect;
import utils.Log;

public class CreateViewRunner extends QueryRunner<ParsedCreateView> {

	@Override
	public void run(ParsedCreateView parsedQuery, boolean log) {
		ParsedSelect select = parsedQuery.parsedSelect;
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
		view.setUpdatable(getUpdatableStatus());
		result.setView(view);
		DB.getInstance().tables.put(viewName, result);
		view = result.getView();
		View rootView = view;
		while (view != null) {
			rootView = view;
			view = view.getParent();
		}
		rootView.getTable().addReferenceView(result.getView());
		if (log)
			Log.println("VIEW CREATED");
	}

	private boolean getUpdatableStatus() {
		// TODO Auto-generated method stub
		return true;
	}

}
