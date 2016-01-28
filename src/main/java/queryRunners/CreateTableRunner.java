package queryRunners;

import db.DB;
import db.Table;
import db.fieldType.FieldType;
import queryParsers.CreateTableParser;
import queryParsers.parsed.ParsedCreateTable;
import utils.Log;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class CreateTableRunner extends QueryRunner<ParsedCreateTable> {

    @Override
    public void run(ParsedCreateTable parsed) {
        Table t = new Table(parsed.tableName, parsed.fields);
        DB.getInstance().tables.put(parsed.tableName, t);
        Log.println("TABLE CREATED");
        Log.error("Created:", t);
    }

}
