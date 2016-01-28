package queryParsers;

import db.ForeignKey;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import queryParsers.parsed.ParsedCreateTable;
import utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 27/01/16 AD.
 */
public class CreateTableParser extends QueryParser<CreateTable> {
    @Override
    public ParsedCreateTable parse(CreateTable createTable) {
        String tableName = createTable.getTable().getName();
        ParsedCreateTable parsed = new ParsedCreateTable(tableName);

        List<ParsedCreateTable.ParsedForeignKey> incompleteForeignKeys = new ArrayList<ParsedCreateTable.ParsedForeignKey>();
        List<String> options = (List<String>) createTable.getTableOptionsStrings();

        if(options != null)
            for(int i=0; i<options.size(); i++) {
                String option = options.get(i);
                if(option.equals("PRIMARY")){
                    parsed.setPrimaryKey(options.get(i+2));
                    i=i+2;
                } else if(option.equals("FOREIGN")) {
                    String fieldName = options.get(i+2);
                    String reference = options.get(i+4);
                    ForeignKey.Action onDelete;
                    ForeignKey.Action onUpdate;

                    if(options.get(i+6).equals("CASCADE"))
                        onDelete = ForeignKey.Action.CASCADE;
                    else
                        onDelete = ForeignKey.Action.RESTRICT;

                    if(options.get(i+8).equals("CASCADE"))
                        onUpdate = ForeignKey.Action.CASCADE;
                    else
                        onUpdate = ForeignKey.Action.RESTRICT;

                    incompleteForeignKeys.add(new ParsedCreateTable.ParsedForeignKey(fieldName,
                            null, reference, onDelete, onUpdate));
                    i+=8;
                }
            }

        ArrayList<ColumnDefinition> defs = (ArrayList<ColumnDefinition>) createTable.getColumnDefinitions();
        ArrayList<ParsedCreateTable.ParsedField> fields = new ArrayList<ParsedCreateTable.ParsedField>();

        for(ColumnDefinition colDef: defs) {
            String name = colDef.getColumnName();
            String type = colDef.getColDataType().getDataType();
            boolean isfk=false;

            for(ParsedCreateTable.ParsedForeignKey pfk: incompleteForeignKeys) {
                if(pfk.name.equals(name)) {
                    pfk.type = type;
                    fields.add(pfk);
                    isfk = true;
                }
            }

            if(!isfk)
                fields.add(new ParsedCreateTable.ParsedField(name, type));
        }

        parsed.setFields(fields);

        return parsed;
    }
}
