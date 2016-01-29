package db;

import db.fieldType.FieldType;

/**
 * Created by user on 28/01/16 AD.
 */
public class ForeignKey extends Field {
    private Table referenceTable;
    private Action onDeleteAction = Action.RESTRICT;
    private Action onUpdateAction = Action.RESTRICT;

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

    public Table getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(Table referenceTable) {
        this.referenceTable = referenceTable;
    }

    public Action getOnDeleteAction() {
        return onDeleteAction;
    }

    public void setOnDeleteAction(Action onDeleteAction) {
        this.onDeleteAction = onDeleteAction;
    }

    public Action getOnUpdateAction() {
        return onUpdateAction;
    }

    public void setOnUpdateAction(Action onUpdateAction) {
        this.onUpdateAction = onUpdateAction;
    }
}
