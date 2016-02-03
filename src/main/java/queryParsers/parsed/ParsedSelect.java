package queryParsers.parsed;

import queryRunners.utils.GroupbyCondition;
import queryRunners.utils.WhereCondition;

import java.util.ArrayList;

import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedSelect extends Parsed {
	private String tableName, joinedTable = null, cartesianTable = null;
	private ArrayList<SelectItem> selectItems;
	private WhereCondition whereCondition;
	private GroupbyCondition groupbyCondition;

	public ParsedSelect(String tableName, ArrayList<SelectItem> selectItems, WhereCondition whereCondition,
			GroupbyCondition groupbyCondition) {
		this.tableName = tableName;
		this.selectItems = selectItems;
		this.whereCondition = whereCondition;
		this.groupbyCondition = groupbyCondition;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public WhereCondition getWhereCondition() {
		return whereCondition;
	}

	public void setWhereCondition(WhereCondition whereCondition) {
		this.whereCondition = whereCondition;
	}

	public ArrayList<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(ArrayList<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public String getJoinedTable() {
		return joinedTable;
	}

	public void setJoinedTable(String joinedTable) {
		this.joinedTable = joinedTable;
	}

	public String getCartesianTable() {
		return cartesianTable;
	}

	public void setCartesianTable(String cartesianTable) {
		this.cartesianTable = cartesianTable;
	}

	public GroupbyCondition getGroupbyCondition() {
		return groupbyCondition;
	}

	public void setGroupbyCondition(GroupbyCondition groupbyCondition) {
		this.groupbyCondition = groupbyCondition;
	}
}
