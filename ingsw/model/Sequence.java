package it.polimi.ingsw.model;

public class Sequence {
        private final int row;
        private final int column;
        private final int length;

        public Sequence(int row, int column, int length) {
            this.row = row;
            this.column = column;
            this.length = length;
        }

        public int getLength() {
            return length;
        }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
        public String toString() {
            return "Sequence found at (" + row + ", " + column + ") with length " + length;
        }


}



