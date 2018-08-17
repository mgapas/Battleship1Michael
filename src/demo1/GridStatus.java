package demo1;

public enum GridStatus {
    HIT(false), MISS(false), ATTEMPT(false), EMPTY(false),
    Destroyer(true), Submarine(true), Cruiser(true), Battleship(true), Carrier(true);

    private boolean isShip;

    GridStatus(boolean isShip) {
        this.isShip = isShip;
    }

    public boolean isShip() {
        return isShip;
    }
}
