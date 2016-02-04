package queryParsers.parsed;

import java.util.ArrayList;
import java.util.List;

import db.Cell;
import db.Field;

/**
 * Created by user on 27/01/16 AD.
 */
public class ParsedInsert extends Parsed {
	public String tableName;
	public ArrayList<Cell> cells;
	public List<Field> selectedColumns;

	public ParsedInsert(String tableName, ArrayList<Cell> cells, List<Field> selectedColumns) {
		this.tableName = tableName;
		this.cells = cells;
		this.selectedColumns = selectedColumns;
	}
}
