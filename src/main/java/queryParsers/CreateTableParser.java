package queryParsers;

import db.fieldType.FieldType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import queryParsers.parsed.ParsedCreateTable;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class CreateTableParser extends QueryParser<CreateTable> {
    @Override
    public ParsedCreateTable parse(CreateTable createTable) {
        String tableName = createTable.getTable().getName();
        ArrayList<ColumnDefinition> defs = (ArrayList<ColumnDefinition>) createTable.getColumnDefinitions();
        ArrayList<FieldType> fieldTypes = new ArrayList<FieldType>();
        for(ColumnDefinition colDef: defs) {
            FieldType ft = FieldType.getFieldTypeClass(colDef.getColDataType().getDataType());
            fieldTypes.add(ft);
        }

        ParsedCreateTable parsed = new ParsedCreateTable(tableName, fieldTypes);
        return parsed;
    }
}
