package db;

/**
 * Created by user on 27/01/16 AD.
 */
public class FieldCell {
    private Field field;
    private Cell cell;

    public FieldCell(Field field, Cell cell) {
        this.field = field;
        this.cell = cell;
    }

    @Override
    public String toString() {
        return cell.toString();
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
