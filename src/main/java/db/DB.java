package db;

import Exceptions.NoSuchTableException;
import javafx.scene.control.Tab;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.javatuples.Triplet;
import queryParsers.parsed.ParsedSelect;
import queryRunners.SelectRunner;
import queryRunners.utils.WhereCondition;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 26/01/16 AD.
 */
public class DB {
    public HashMap<String, Table> tables = new HashMap<String, Table>();
    private static DB db = new DB();

    public static DB getInstance() {
        return db;
    }

    public Table getTable(String name) throws NoSuchTableException {
        if(tables.containsKey(name))
            return tables.get(name);
        else
            throw new NoSuchTableException();
    }

    public static ArrayList<Triplet<Table, Record, FieldCell>> getAllForeignKeysToPK(Table table, FieldCell pkCell) {
        if(pkCell==null || !table.getPrimaryKey().equals(pkCell.getField()))
            return null;

        ArrayList<Triplet<Table, Record, FieldCell>> foreignKeyCells = new ArrayList<Triplet<Table, Record, FieldCell>>();

        for(Table t: DB.getInstance().tables.values()) {
            ForeignKey fk = t.getForeignKeyTo(table);
            if(fk != null) {
                ArrayList<String> fields = new ArrayList<String>();
                fields.add(fk.name);
                String whereString = fk.name + "=" + pkCell.getCell().getValue();
                WhereCondition whereCondition = WhereCondition.stringToWhereCondition(whereString);
                ParsedSelect ps = new ParsedSelect(t.getName(), fields, whereCondition);
                Table selectResult = new SelectRunner().select(ps);
                for(Record r: selectResult.getRecords())
                    foreignKeyCells.add(new Triplet<Table, Record, FieldCell>(t, r, r.getFieldCell(fk.name)));
            }
        }

        return foreignKeyCells;
    }

    public static ArrayList<FieldCell> getAllForeignKeyCellsToPK(Table table, FieldCell pkCell) {
        ArrayList<Triplet<Table, Record, FieldCell>> triplets = getAllForeignKeysToPK(table, pkCell);
        ArrayList<FieldCell> fieldCells = new ArrayList<FieldCell>();

        for(Triplet<Table, Record, FieldCell> triplet: triplets)
            fieldCells.add(triplet.getValue2());

        return fieldCells;
    }
}
