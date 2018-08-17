package demo1;

import java.util.HashSet;


public class GameLogic {

    private HashSet<GridStatus> survivedShips;
    public String username;
    private final static int BOARDSIZE = 10;
    //myboard is the board originally sent from client. Once set to targetboard don't use anymore.
    private GridStatus[][] myBoard = new GridStatus[BOARDSIZE][BOARDSIZE];
    private GridStatus[][] targetBoard = new GridStatus[BOARDSIZE][BOARDSIZE];
    private GameHandler.GameTurn turn;
//    private boolean[] survivedShipIds = new boolean[Ship.ShipType.values().length];
    // 1-5, shipId; 9, Guessed/hit attempt; 8, hit target; 7, miss target

    public GameLogic(String username, GameHandler.GameTurn gameTurn) {
        setUsername(username);
        setTurn(gameTurn);

        survivedShips = new HashSet<>();
        for (GridStatus st: GridStatus.values()) {
            if (st.isShip()) {
                survivedShips.add(st);
            }
        }
        for (GridStatus[] arr: targetBoard) {
            for (GridStatus gt: arr) {
                gt = GridStatus.EMPTY;
            }
        }
    }

    public GameLogic(String username) {
        setUsername(username);

        survivedShips = new HashSet<>();
        for (GridStatus st: GridStatus.values()) {
            survivedShips.add(st);
        }

        for (GridStatus[] arr: targetBoard) {
            for (GridStatus gt: arr) {
                gt = GridStatus.EMPTY;
            }
        }

    }


    public void sinkShip(int id) {
        survivedShips.remove(id);
    }


    /**
     * Game logic.
     * Handles guesses from players by row and column.
     * sets result[]
     * @param gs
     * @param row
     * @param col
     * @return
     */
    public GridStatus[] getHit(GridStatus gs, int row, int col) {
        GridStatus[] result = new GridStatus[3];

        //hit
        if (myBoard[row][col].isShip()) {
            myBoard[row][col] = GridStatus.HIT;
            result[0] = GridStatus.HIT;
            //miss
        } else  {
            if (myBoard[row][col].equals(GridStatus.EMPTY)) {
                myBoard[row][col] = GridStatus.MISS;
                result[0] = GridStatus.MISS;
            } else {
                sendDuplicateGuessMessage();
                //throw new IllegalStateException("Already attacked");
            }
        }

        result[1] = checkSurvivedShip();

        //if all ships have been sunk declare game over
        if (!checkSurvival()) {
            System.out.println("Game over");
            result[2] = GridStatus.EMPTY; //usually null if game is not over
        }

        return result; //returns the [0] guess status, [1] shipname that has been sunk (only if its been sunk), [2] is game over: empty if yes/null if null
    }

    public GridStatus[] sendDuplicateGuessMessage(){
        return null;
    }

    /**
     * This returns the shipname if  ship has been sunk.
     * @return
     */
    public GridStatus checkSurvivedShip() {
        HashSet<GridStatus> localSurvivedShips = new HashSet<>();
        HashSet<GridStatus> shipSunkInThisMove = survivedShips;

        //find which ships remain
        for (int rows = 0; rows < BOARDSIZE; rows++) {
            for (int cols = 0; cols < BOARDSIZE; cols++) {
                if (myBoard[rows][cols].isShip()) {
                    localSurvivedShips.add((GridStatus) myBoard[rows][cols]);
                }
            }
        }

        //removes all common elements to see what's gone.
        shipSunkInThisMove.removeAll(localSurvivedShips);

        //if there is a sunk ship
        if (shipSunkInThisMove.size() == 1) {
            GridStatus sunkShipName = shipSunkInThisMove.iterator().next();
            survivedShips = localSurvivedShips;
            return sunkShipName;
        } else if (shipSunkInThisMove.size() > 1) {
            try {
                System.out.println("shipSunkInThisMove" + shipSunkInThisMove);
                System.out.println("localSurvivedShips: " + localSurvivedShips);
                throw new IllegalStateException("There're discrepancies between client side and server side.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        survivedShips = localSurvivedShips;
        return null;
    }

    /**
     * Is game over
     * returns true if the game is not over and there are ships remaining
     * else returns false if there are no ships remaining and game is over.
     * @return
     */
    public boolean checkSurvival() {

        return (!survivedShips.isEmpty());
    }

    public GridStatus[][] getTargetBoard() {
        return targetBoard;
    }

    public void hitOrMiss(GridStatus gs, int row, int col) {

        if (targetBoard[row][col] == GridStatus.ATTEMPT ) {
            this.targetBoard[row][col] = gs;
        } else {
            throw new IllegalArgumentException("Only Grids that are marked as ATTEMPT can be HIT or MISS.");
        }
    }

    public int[] hitEnemy(int row, int col) {
        if (targetBoard[row][col] == GridStatus.EMPTY || targetBoard[row][col] == null) {
            targetBoard[row][col] = GridStatus.ATTEMPT;
        }

        return new int[]{row, col};
    }
    public void setOpponentGridStatus(GridStatus gs, int row, int col) {
        targetBoard[row][col] = gs;
    }
    //Getter methods

    /**
     * Getter for turn
     * @return
     */
    public GameHandler.GameTurn getTurn() {
        return turn;
    }

    /**
     * Getter for the gameboard
     * @return
     */
    public GridStatus[][] getMyBoard() {
        return myBoard;
    }

    /**
     * Getter for the username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the survived ships
     * @return
     */
    public HashSet<GridStatus> getSurvivedShips() {
        return survivedShips;
    }

    //Setter methods
    public void setTargetBoard(GridStatus[][] targetBoard) {
        this.targetBoard = targetBoard;
    }

    /**
     * Sets the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the gameboard
     * @param myBoard - the gameboard
     */
    public void setMyBoard(GridStatus[][] myBoard) {
        this.myBoard = myBoard;
    }

    /**
     * Sets the grid status
     * @param gs
     * @param row
     * @param col
     */
    public void setGridStatus(GridStatus gs, int row, int col) {
        myBoard[row][col] = gs;
    }

    /**
     * Setter for who's turn it is
     * @param turn
     */
    public void setTurn(GameHandler.GameTurn turn) {
        this.turn = turn;
    }

    /**
     * ToString method to print the board to the console.
     * @param gt2d
     */
    public static void printBoard(GridStatus[][] gt2d) {

        if (gt2d != null) {
            for (int i = 0; i < BOARDSIZE; i++) {
                for (int j = 0; j < BOARDSIZE; j++) {
                    System.out.print(gt2d[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Null board");
        }
    }

    public static void main(String[] args) {
        GameLogic aa = new GameLogic("Bob the Builder", GameHandler.GameTurn.A); //player A and player B
       // System.out.println(aa.getSurvivedShips());
        GridStatus[][] gt = new GridStatus[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gt[i][j] = GridStatus.EMPTY;
            }
        }
        gt[2][5] = GridStatus.Cruiser;
        gt[1][2] = GridStatus.Battleship;
        gt[3][4] = GridStatus.Carrier;
        gt[3][5] = GridStatus.Carrier;
        gt[4][1] = GridStatus.Destroyer;
        gt[9][2] = GridStatus.Submarine;
        gt[1][3] = GridStatus.MISS;

        GameLogic.printBoard(gt);

        //Testing
        aa.setMyBoard(gt);
        aa.getHit(GridStatus.HIT, 1, 2);
        aa.getHit(GridStatus.HIT, 3, 4);
        aa.getHit(GridStatus.HIT, 2, 5);
        aa.getHit(GridStatus.HIT, 4, 1);
        aa.getHit(GridStatus.HIT, 9, 2);
        aa.getHit(GridStatus.HIT, 3, 5);

        GameLogic.printBoard(gt);
    }

}
