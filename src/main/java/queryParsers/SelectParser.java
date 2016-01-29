package queryParsers;

import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import queryParsers.parsed.ParsedSelect;
import queryParsers.utils.DummyVisitor;
import queryRunners.utils.WhereCondition;

import java.util.ArrayList;

/**
 * Created by user on 27/01/16 AD.
 */
public class SelectParser extends QueryParser<Select> {
    ParsedSelect parsedSelect;

    @Override
    public ParsedSelect parse(Select select) {
        select.getSelectBody().accept(new SelectParserVisitor());
        return parsedSelect;
    }

    private class SelectParserVisitor extends DummyVisitor {
        @Override
        public void visit(PlainSelect plainSelect) {
            String tableName = plainSelect.getFromItem().toString();
            WhereCondition whereCondition = new WhereCondition(plainSelect.getWhere());
            ArrayList<String> selectItems = new ArrayList<String>();

            for(SelectItem si: plainSelect.getSelectItems()) {
                selectItems.add(si.toString());
            }

            parsedSelect = new ParsedSelect(tableName, selectItems, whereCondition);

            if(plainSelect.getJoins()!=null){
                Join join = plainSelect.getJoins().get(0);
                if(join.isSimple())
                    parsedSelect.setCartesianTable(join.getRightItem().toString());
                else
                    parsedSelect.setJoinedTable(join.getRightItem().toString());
            }
        }
    }
}
