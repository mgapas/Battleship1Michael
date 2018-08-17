package demo1.message;

import demo1.GridStatus;

public class GridStatusMessage extends Message {

    private GridStatus gs;
    private int row;
    private int col;

    public static GridStatusMessage getHitMessage(String username, int row, int col) {
        return new GridStatusMessage(username, GridStatus.HIT, row, col);
    }

    public static GridStatusMessage getAttemptMessage(String username, int row, int col) {
        return new GridStatusMessage(username, GridStatus.ATTEMPT, row, col);
    }

    public static GridStatusMessage getMissMessage(String username, int row, int col) {
        return new GridStatusMessage(username, GridStatus.MISS, row, col);
    }

    public static GridStatusMessage getEmptyMessage(String username, int row, int col) {
        return new GridStatusMessage(username, GridStatus.EMPTY, row, col);
    }

    private GridStatusMessage(String username, GridStatus gs, int row, int col) {
        super(username);

        this.gs = gs;
        this.row = row;
        this.col = col;
    }

    private GridStatusMessage(GridStatus gs, int row, int col) {
        super(null);
        this.gs = gs;
    }


    public GridStatus getGs() {
        return gs;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_ACTION;
    }

    @Override
    public String toString() {
        return "GridStatusMessage{" +
                "gs=" + gs +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
