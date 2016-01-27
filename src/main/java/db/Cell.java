package db;


/**
 * Created by user on 27/01/16 AD.
 */
public class Cell {
    CellType type;
    Object value;

    public Cell(CellType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public enum CellType {
        INTEGER, VARCHAR, NULL
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
