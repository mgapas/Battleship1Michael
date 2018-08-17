package demo1.jsonConverters;

import demo1.GridStatus;
import demo1.TestGUI;
import demo1.message.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonConverter {

    private static RuntimeTypeAdapterFactory<Message> messageAdapterFactory = RuntimeTypeAdapterFactory
            .of(Message.class, "type")
            .registerSubtype(LoginMessage.class, "Login")
            .registerSubtype(ResultMessage.class, "Result")
            .registerSubtype(GridStatusMessage.class, "GridStatus")
            .registerSubtype(GameActionMessage.class, "GameAction")
            .registerSubtype(ChatMessage.class, "Chat")
            .registerSubtype(SystemMessage.class, "System");

    private static Gson gson = new GsonBuilder().registerTypeAdapterFactory(messageAdapterFactory).create();

    public static String writeJson(Message message) {
        MessageWrapper mw = new MessageWrapper(message);

        String content = gson.toJson(mw);

        return content;
    }

    public static Message readJson(String jsonFile) {
        System.out.println(jsonFile);

        MessageWrapper mw;

        try {
            mw = gson.fromJson(jsonFile, MessageWrapper.class);
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Json data corrupted.");
        }
        System.out.println("Message in JSON" + mw.getMessage().toString());
        return mw.getMessage();
    }

    public static MessageWrapper readJson2(String jsonFile) {
        MessageWrapper mw;

        try {
            mw = gson.fromJson(jsonFile, MessageWrapper.class);
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Json data corrupted.");
        }

        return mw;
    }

    public static void main(String[] args) {
//        LoginMessage msg = MessageFactory.getLoginMessage("a");
//        System.out.println("Original message: " + msg);
//        System.out.println();
//        String msgStr = JsonConverter.writeJson(msg);
//        System.out.println("msgStr: " + msgStr);
//        MessageWrapper receive = JsonConverter.readJson2(msgStr);
//        System.out.println("Result: " + receive.toString());
//        System.out.println("Extract: " + receive.getMessage().toString());
//        System.out.println();
//
//        SystemMessage msg1 = MessageFactory.getAckMessage();
//        System.out.println("Original message: " + msg1);
//        System.out.println();
//        String msg1Str = JsonConverter.writeJson(msg1);
//        System.out.println("msgStr: " + msg1Str);
//        MessageWrapper receive1 = JsonConverter.readJson2(msg1Str);
//        System.out.println("Result: " + receive1.toString());
//        System.out.println("Extract: " + receive1.getMessage().toString());
//
//        System.out.println();
//
//        System.out.println("Original message: " + msg1);
//        System.out.println();
//        String msg1Str2 = JsonConverter.writeJson(msg1);
//        Message msg2 = JsonConverter.readJson(msg1Str2);
//        System.out.println("msgStr: " + msg2);



        GridStatus[][] gt = new GridStatus[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gt[i][j] = GridStatus.EMPTY;
            }
        }

        gt[2][5] = GridStatus.Cruiser;
        gt[2][6] = GridStatus.Cruiser;
        gt[1][2] = GridStatus.Battleship;
        gt[2][2] = GridStatus.Battleship;
        gt[3][4] = GridStatus.Carrier;
        gt[3][5] = GridStatus.Carrier;
        gt[3][6] = GridStatus.Carrier;
        gt[3][7] = GridStatus.Carrier;
        gt[3][8] = GridStatus.Carrier;
        gt[4][1] = GridStatus.Destroyer;
        gt[5][1] = GridStatus.Destroyer;
        gt[9][2] = GridStatus.Submarine;
        gt[9][3] = GridStatus.Submarine;

//        System.out.println(JsonConverter.writeJson(MessageFactory.getReadyMessage(gt)));
        System.out.println(JsonConverter.writeJson(MessageFactory.getChatMessage("aaa", "bbb")));
    }
}
