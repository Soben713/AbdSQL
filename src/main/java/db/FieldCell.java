package db;

/**
 * Created by user on 27/01/16 AD.
 */
public class FieldCell {
    Field field;
    Cell cell;

    public FieldCell(Field field, Cell cell) {
        this.field = field;
        this.cell = cell;
    }

    @Override
    public String toString() {
        return cell.toString();
    }
}
