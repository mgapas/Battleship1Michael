package demo1.jsonConverters;

import demo1.message.LoginMessage;
import demo1.message.Message;
import demo1.message.MessageFactory;

public class MessageWrapper {

    private Message message;
    private Message.MessageType type;


    public MessageWrapper(Message message) {
        this.message = message;
        this.type = message.getMessageType();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "message=" + message +
                ", type='" + type + '\'' +
                '}';
    }

    public static void main(String[] args) {
        MessageWrapper mw = new MessageWrapper(MessageFactory.getLoginMessage("a"));
        System.out.println(mw);
        System.out.println(mw.getMessage());
    }
}
