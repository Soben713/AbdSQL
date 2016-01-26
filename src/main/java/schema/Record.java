package schema;

import schema.fieldType.FieldType;

import java.util.ArrayList;

/**
 * Created by user on 26/01/16 AD.
 */
public class Record {
    public ArrayList<Field> fields;

    public Record(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public boolean matchesFieldTypes(ArrayList<FieldType> fieldTypes) {
        if(fields.size()!=fieldTypes.size())
            return false;

        for(int i=0; i<fields.size(); i++) {
            Field f = fields.get(i);
            FieldType ft = fieldTypes.get(i);
            if(!f.fieldType.equals(ft))
                return false;
        }
        return true;
    }
}
