package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer row;
    private final Integer column;

    public Position(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row.equals(position.row) && column.equals(position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }


    @Override
    public String toString() {
        return "Position(" +
                " " + row +
                ", " + column +
                ')';
    }
}
