package db;


/**
 * Created by user on 27/01/16 AD.
 */
public class Cell {
    private CellType type;
    private Object value;

    public Cell(CellType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public enum CellType {
        INTEGER, VARCHAR, NULL
    }

    @Override
    public String toString() {
        if(type == CellType.NULL)
            return "NULL";
        return ""+value;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
