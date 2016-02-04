package queryRunners;

import Exceptions.NoSuchTableException;
import Exceptions.NonUpdatableView;
import db.*;
import org.javatuples.Triplet;
import queryParsers.parsed.ParsedDelete;
import queryRunners.utils.WhereCondition;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class DeleteRunner extends QueryRunner<ParsedDelete> {
	View baseView;

	@Override
	public void run(ParsedDelete parsedQuery, boolean log) throws NonUpdatableView {
		try {
			Table table = DB.getInstance().getTable(parsedQuery.getTableName());
			baseView = table.getView();
			View view = table.getView();
			while (view != null) {
				if (!view.isUpdatable())
					throw new NonUpdatableView(view.getName());
				else
					view = view.getParent();
			}
			// now the update is legal!
			view = table.getView();
			View rootView = new View();
			while (view != null) {
				rootView = view;
				view = view.getParent();
			}
			delete(rootView, parsedQuery);
			deleteChildren(rootView.getChildrenViews(), parsedQuery);
		} catch (NoSuchTableException e) {
			e.printStackTrace();
		}
	}

	private void deleteChildren(ArrayList<View> childrenViews, ParsedDelete parsedQuery) {
		if (childrenViews == null)
			return;
		for (View child : childrenViews) {
			try {
				delete(child, parsedQuery);
			} catch (NonUpdatableView e) {
				Log.error(e);
				;
			}
			deleteChildren(child.getChildrenViews(), parsedQuery);
		}
	}

	public void delete(View view, ParsedDelete parsedDelete) throws NonUpdatableView {
		Table t = view.getTable();
		overRecords: for (int i = 0; i < t.getRecords().size(); i++) {
			Record r = t.getRecords().get(i);
			// by the second condition in this if, we check whether this view
			// accepts this record or not
			if (parsedDelete.getWhereCondition().evaluate(r)
					&& baseView.getParsedQuery().parsedSelect.getWhereCondition().evaluate(r)) {
				if (t.getPrimaryKey() != null) {
					// before deleting, check foreignkey restricts
					ArrayList<Triplet<Table, Record, FieldCell>> fkCells = DB.getAllForeignKeysToPK(t,
							r.getPrimaryFieldCell(t));

					for (Triplet<Table, Record, FieldCell> fkCell : fkCells) {
						ForeignKey fk = (ForeignKey) fkCell.getValue2().getField();
						if (fk.getOnDeleteAction().equals(ForeignKey.Action.RESTRICT)) {
							Log.println(Log.FK_RESTRICTS);
							break overRecords;
						}
					}

					// it's cascade, so deleteRecord them all
					for (Table t2 : DB.getInstance().tables.values()) {
						ForeignKey fk = t2.getForeignKeyTo(t);
						if (fk != null) {
							Object tpkValue = r.getPrimaryFieldCell(t).getCell().getValue();
							WhereCondition whereCondition = WhereCondition
									.stringToWhereCondition(fk.name + "=" + tpkValue.toString());
							ParsedDelete pd = new ParsedDelete(t2.getName(), whereCondition);
							delete(t2.getView(), pd);
						}
					}
				}

				t.deleteRecordByIndex(i);
				i = i - 1;
			}
		}

		t.updateIndexes();
		Log.error("Deleted", t);
	}
}
