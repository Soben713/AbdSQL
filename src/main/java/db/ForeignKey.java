package db;

import db.fieldType.FieldType;

/**
 * Created by user on 28/01/16 AD.
 */
public class ForeignKey extends Field {
    Table referenceTable;
    Action onDeleteAction = Action.RESTRICT;
    Action onUpdateAction = Action.RESTRICT;

    public ForeignKey(String name, FieldType fieldType, Table referenceTable, Action onDeleteAction, Action onUpdateAction) {
        super(name, fieldType);
        this.referenceTable = referenceTable;
        this.onDeleteAction = onDeleteAction;
        this.onUpdateAction = onUpdateAction;
    }

    public enum Action {
        CASCADE, RESTRICT
    }

    @Override
    public String toString() {
        return super.toString()+"[FK->"+referenceTable.getName()+"]";
    }
}
