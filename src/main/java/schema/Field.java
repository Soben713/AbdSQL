package schema;

import schema.fieldType.FieldType;

/**
 * Created by user on 26/01/16 AD.
 */
public class Field {
    public String name;
    public FieldType fieldType;
    public Object value;

    public Field(String name, FieldType fieldType) {
        this.name = name;
        this.fieldType = fieldType;
    }
}
