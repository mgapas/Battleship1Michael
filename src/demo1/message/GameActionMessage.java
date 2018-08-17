package demo1.message;

import demo1.GridStatus;

public class GameActionMessage extends ResultMessage {

    private GridStatus[][] board;

    public GameActionMessage(String username, GridStatus[] gt, int row, int col, GridStatus[][] board) {
        super(username, gt, row, col);
        this.board = board;
    }

    public GridStatus[][] getBoard() {
        return board;
    }

    public void setBoard(GridStatus[][] board) {
        this.board = board;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_ACTION;
    }

    @Override
    public String toString() {
        return "GameActionMessage{" +
                "GuessMadeBy= " + username +
                ", hitMiss=" + hitMiss +
                ", sinkShip=" + sinkShip +
                ", isSurvival=" + isSurvival +
                '}';
    }
}
