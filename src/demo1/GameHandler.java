package demo1;

import demo1.message.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class GameHandler implements Runnable{

    public enum GameState {NOT_PLAYING, JOIN_PHASE, RUNNING}
    public enum GameTurn {A, B}
    private GameState currentState = GameState.JOIN_PHASE;
    private static GameTurn currentTurn  = GameTurn.A;
    private static ConcurrentHashMap<MessageHandler, GameLogic> players = new ConcurrentHashMap<>();
    private final int BOARDSIZE = 10;
    private static GridStatus[][] playerBoardA;
    private static GridStatus[][] playerBoardB;
    private static final int GAMESIZE = 2;
    private static GameHandler game = new GameHandler();

//    public void joinGame() {
//        currentState = GameState.JOIN_PHASE;
//    }

    public static void shutdown() {
        game = new GameHandler();
    }

    public void startGame() {
        currentState = GameState.RUNNING;
        broadcast(MessageFactory.getStartMessage());
//        setPlayerBoardA(playerBoardA);
//        setPlayerBoardB(playerBoardB);
        new Thread(this).start();
    }

    public static void handleSystemMessage(SystemMessage sysMsg, MessageHandler mh) {
        if (sysMsg.getSystemResponse().equals(SystemMessage.SystemResponse.READY)) {
            if (players.size() < GAMESIZE) {
                game.join(mh);

                if (currentTurn == GameTurn.A) {
                    playerBoardA = sysMsg.getGt();
                    GameLogic.printBoard(playerBoardA);
                    players.get(mh).setMyBoard(playerBoardA);

                } else {
                    playerBoardB = sysMsg.getGt();
                    GameLogic.printBoard(playerBoardB);
                    players.get(mh).setMyBoard(playerBoardB);
                }

                nextGameTurn();

                if (players.size() == GAMESIZE) {
                    game.startGame();
                }
            } else {
                throw new IllegalStateException("Discrepancy! Only 2 persons are allowed in the one game.");
            }
        }
    }

    private static void nextGameTurn() {
        currentTurn = (currentTurn == GameTurn.A)? GameTurn.B: GameTurn.A;
        System.out.println("current game turn: " + currentTurn);
        System.out.println();
    }

    public static void handleActionMessage(GridStatusMessage msg, MessageHandler mh) {
        if (players.size() != GAMESIZE) {
            throw new IllegalStateException("Two, and only two players. Number of players registered: " + players.size());
        }

        // messageSender
        if (players.get(mh).getTurn().equals(currentTurn)) {

            GameLogic messageSender = players.get(mh);
            ConcurrentHashMap<MessageHandler, GameLogic> temp = new ConcurrentHashMap<>(players);
            temp.remove(mh);
            MessageHandler opponentsMessageHandler = temp.entrySet().iterator().next().getKey();
            GameLogic opponentsGameLogic = temp.get(opponentsMessageHandler);

            if (msg.getGs().equals(GridStatus.ATTEMPT)) {
                System.out.println(msg.toString());
                GridStatus[] result = opponentsGameLogic.getHit(msg.getGs(), msg.getRow(), msg.getCol());

                //send duplicate guess message
                if(result[0] == null){
                    mh.sendMessage(MessageFactory.getDuplicateGuessMessage());
                }
                else {
                    nextGameTurn();
                    // brief results send to the attacker
                    mh.sendMessage(MessageFactory.getResultMessage(mh.getUsername(), result, msg.getRow(), msg.getCol()));
                    // results with updated board send to the attackee
                    mh.sendMessage(MessageFactory.getGameActionMessage(opponentsGameLogic.getUsername(), result, msg.getRow(), msg.getCol(),
                            players.get(mh).getMyBoard()));

                    if (result[2] == GridStatus.EMPTY) {
                        game.currentState = GameState.NOT_PLAYING;
                        opponentsMessageHandler.sendMessage(MessageFactory.getLoserGameOverMessage());
                        mh.sendMessage(MessageFactory.getWinnerGameOverMessage());
                        terminateGame();

                    }
                    sendBeginTurnMessageToOtherPlayer(opponentsMessageHandler);
                }
            }
        } else {
            mh.sendMessage(MessageFactory.getDenyMessage());
        }
    }

    public static void sendBeginTurnMessageToOtherPlayer(MessageHandler opponentsMessageHandler){
        opponentsMessageHandler.sendMessage(MessageFactory.getBeginTurn());
        System.out.println("Send new turn message to other player");
    }

    public static void terminateGame(){
        MessageHandler.shutdown();
    }

    public void join(MessageHandler handler) {
        if (!currentState.equals(GameState.JOIN_PHASE)) {
            throw new IllegalStateException("It's " + currentState + ", not READY phase.");
        }

        players.put(handler, new GameLogic(handler.getUsername(), currentTurn));
        System.out.println(handler.getUsername() + " " + currentTurn);
        System.out.println(currentState);
        if (players.size() > GAMESIZE) {
            throw new IllegalStateException("Discrepancy! Only 2 persons are allowed in the one game.");
        }
    }

    public String[] getPlayerName() {
        String[] names = new String[players.size()];

        int index = 0;
        for (MessageHandler player: players.keySet()) {
            names[index++] = player.getUsername();
        }

        return names;
    }

    private void broadcast(Message msg) {
        broadcast(msg, null);
    }

    private void broadcast(Message msg, String username) {
        for (MessageHandler player: players.keySet()) {
            if (username == null || !player.getUsername().equals(username)) {
                player.sendMessage(msg);
            }
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public GameTurn getCurrentTurn() {
        return currentTurn;
    }

    public GridStatus[][] getPlayerBoardA() {
        return playerBoardA;
    }

    public void setPlayerBoardA(GridStatus[][] playerBoardA) {
        this.playerBoardA = playerBoardA;
    }

    public GridStatus[][] getPlayerBoardB() {
        return playerBoardB;
    }

    public void setPlayerBoardB(GridStatus[][] playerBoardB) {
        this.playerBoardB = playerBoardB;
    }

    @Override
    public void run() {
        if (players.size() >= GAMESIZE) {
            broadcast(MessageFactory.getStartMessage());
        } else {
            currentState = GameState.NOT_PLAYING;
            players.clear();
        }
    }

    public static void main(String[] args) {
        HashMap<String, String> demo = new HashMap<>();
        demo.put("1", "one");
        demo.put("2", "one");

        HashMap<String, String> temp = (HashMap<String, String>) demo.clone();

        System.out.println(demo.get("1"));
        System.out.println(temp.remove("1"));
        System.out.println(demo.keySet());
    }
}
