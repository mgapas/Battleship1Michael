package demo1.message;

import demo1.GridStatus;

import java.util.Arrays;

public class ResultMessage extends Message {

    GridStatus hitMiss;
    GridStatus sinkShip;
    boolean isSurvival;

    public ResultMessage(String username, GridStatus[] result, int row, int col) {
        super(username);

        System.out.println(Arrays.toString(result));

        hitMiss = (GridStatus) result[0];
        sinkShip = (GridStatus) result[1];
        if (result[2] == null) {
            isSurvival = true;
        } else {
            isSurvival = false;
        }
    }

    public GridStatus getHitMiss() {
        return hitMiss;
    }

    public GridStatus getSinkShip() {
        return sinkShip;
    }

    public boolean isSurvival() {
        return isSurvival;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.RESULT;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "hitMiss=" + hitMiss +
                ", sinkShip=" + sinkShip +
                ", isSurvival=" + isSurvival +
                '}';
    }
}
