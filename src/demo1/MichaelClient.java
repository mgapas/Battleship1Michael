package demo1;

import demo1.jsonConverters.JsonConverter;
import demo1.message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class MichaelClient {

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private BattleshipGUI gui;

    private Timer timerChat = new Timer();
    private Timer timerAction = new Timer();
    private String username;
    private boolean firstMsg = false;
    private final int BOARDSIZE = 10;
    GridStatus[][] gridStatus = new GridStatus[BOARDSIZE][BOARDSIZE];
    private GameLogic game;


    public static void main (String args[]) throws IOException {
        MichaelClient client = new MichaelClient();
        client.init();
    }

    private void init() throws IOException {
        String host = "127.0.0.1";
        InetAddress ia = InetAddress.getByName(host);
        socket = new Socket(ia, 8080);
        gui = new BattleshipGUI();
        username = gui.getUsername();
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readThread(socket, br);
        game = new GameLogic(username);
        login();

        while(socket.isConnected() && !socket.isClosed())
        {
            sendMsg();
            sendGameAction();
        }



    }

    private void readThread(Socket socket, BufferedReader br) {
        Thread receiveMessage = new Thread(() -> {
            try {
                while (!Thread.interrupted() && socket.isConnected() && !socket.isClosed()) {
                    readMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        receiveMessage.start();
    }

    private void readMessage() throws IOException {
        try {
            Message read = JsonConverter.readJson(jsonToString());
            System.out.println(read.toString());

            if(read != null)
            {
                if(read.getUsername() == null)
                {
                    gui.showMsgln("System message");
                }
                if (read instanceof ChatMessage)
                {
                    handleChat(read);
                }
                else if(read instanceof GameActionMessage)
                {
                    handleGridStatus((GameActionMessage) read);
                }
                else if(read instanceof ResultMessage)
                {
                    handleResultMessage((ResultMessage) read);
                }
                else
                {
                    gui.showMsgln("username: " + read.getUsername());
                    gui.showMsgln("type: " + ackDeny(read) + "\n");
                }
            }

        } catch (Exception e)
        {
            disConnect(socket, bw, br);
            System.exit(1);
        }
    }

    private String jsonToString() {
        String line = null;
        System.out.println("jsonToString");
        try {
            line = br.readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    public void sendMessage(Message message) throws IOException {
        // write and send Json
        bw.write(JsonConverter.writeJson(message));
        bw.newLine();
        bw.flush();
    }


    //Should be our GridStatus[][]
    public void printBoard(GridStatus[][] gt) {

    }

    private void login() throws IOException
    {
        this.sendMessage(MessageFactory.getLoginMessage(username));
        bw.flush();
    }

    private void writeMsg(String msg) throws IOException {
        this.sendMessage(MessageFactory.getChatMessage(username, msg));
        bw.flush();
    }

    private void writeHit() throws IOException
    {
        //Logic for pressing buttons to get row and col
        int[] cells;
        cells = gui.getArr();
        this.sendMessage(MessageFactory.getAttemptMessage(username, cells[0], cells[1]));
        bw.flush();
    }

    private void writeJoin() throws IOException
    {
        initBoard();
        this.sendMessage(MessageFactory.getReadyMessage(gui.getB()));
        bw.flush();
    }

    private void initBoard()
    {
        gui.getB();
        printBoard(gui.getB());
    }


    private void handleChat(Message read) {
        gui.showMsgln("username: " + read.getUsername());
        gui.showMsgln("type: " + read.getMessageType());
        gui.showMsgln("Text: " + ((ChatMessage) read).getText() + "\n");
    }

    private void handleGridStatus(GameActionMessage read) {
        gui.showMsgln(read.toString());
        gridStatus = read.getBoard();
        gui.showMsgln("Your board: ");
        printBoard(gridStatus);
    }

    private void handleResultMessage(ResultMessage read)
    {
        gui.showMsgln(read.toString());
    }

    private void sendMsg()
    {
        timerChat.schedule(new TimerTask() {

            @Override
            public void run() {
                String msg = gui.getMsg();

                if (msg != null && !msg.trim().equals("")) {
                    try {

                        writeMsg(msg);
                        gui.setMsg(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 500);
    }

    private void sendGameAction() {
        timerAction.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (gui.getGameAction() != null) {
                        switch (gui.getGameAction()) {
                            case HIT:
                                writeHit();
                                break;
                            case JOIN:
                                writeJoin();
                                break;
                            case EXIT:
                                disConnect(socket, bw, br);
                                break;
                        }

                        gui.setGameAction(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }


    private String ackDeny(Message read)
    {
        String type = read.getMessageType().toString();
        if (type.equals("ACK"))
        {
            type = "Positive.";
        }
        else if (type.equals("DENY"))
        {
            type = "Negative.";
        }
        return type;
    }

    private void disConnect(Socket s, BufferedWriter pw, BufferedReader br) throws IOException {
        pw.close();
        br.close();
        s.close();
        System.exit(0);
    }



}
