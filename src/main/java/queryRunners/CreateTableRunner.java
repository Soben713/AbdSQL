package queryRunners;

import Exceptions.NoSuchTableException;
import db.DB;
import db.Field;
import db.ForeignKey;
import db.Table;
import db.fieldType.FieldType;
import queryParsers.parsed.ParsedCreateTable;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class CreateTableRunner extends QueryRunner<ParsedCreateTable> {

    @Override
    public void run(ParsedCreateTable parsed) {
        ArrayList<Field> fields = new ArrayList<Field>();
        Field primaryKeyField = null;

        for(ParsedCreateTable.ParsedField pf: parsed.getFields()) {
            FieldType ft = FieldType.getFieldTypeClass(pf.type);
            if(pf instanceof ParsedCreateTable.ParsedForeignKey) {
                ParsedCreateTable.ParsedForeignKey pfk = ((ParsedCreateTable.ParsedForeignKey) pf);
                Table reference = null;
                try {
                    reference = DB.getInstance().getTable(pfk.reference);
                } catch (NoSuchTableException e) {
                    Log.error("Invalid table");
                }
                fields.add(new ForeignKey(pfk.name, ft, reference, pfk.onDelete, pfk.onUpdate));
            } else {
                fields.add(new Field(pf.name, ft));
            }
            if(pf.name.equals(parsed.getPrimaryKey()))
                primaryKeyField = fields.get(fields.size()-1);
        }

        Table t = new Table(parsed.getTableName(), fields, primaryKeyField);
        DB.getInstance().tables.put(parsed.getTableName(), t);

        Log.println("TABLE CREATED");
        Log.error("Created:", t);
    }

}
