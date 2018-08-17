package demo1.message;

public class ChatMessage extends Message {

    private String text;

    public ChatMessage(String username, String text) {
        super(username);
        this.text = text;
    }

    public ChatMessage(String text) {
        super(null);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CHAT;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
